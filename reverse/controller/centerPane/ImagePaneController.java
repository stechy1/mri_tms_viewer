/*
 * Decompiled with CFR 0_123.
 */
package controller.centerPane;

import controller.UtilityClass;
import controller.rightPane.SettingSnapshotPaneController;
import enums.Controllers;
import interfaces.IController;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import model.ImagePanelModel;
import model.MyDicom;
import model.MyPoint;
import model.TriggerMarkers;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.centerPane.ImagePanel;
import view.dialogWindow.MyLoader;

public class ImagePaneController
implements IController,
MouseWheelListener,
MouseListener {
    private ImagePanel view;
    private ImagePanelModel model;
    private MyLoader loader;

    public ImagePaneController(ImagePanel view, ImagePanelModel model) {
        this.view = view;
        this.model = model;
    }

    public ImagePaneController(ImagePanel view) {
        this(view, null);
    }

    @Override
    public ImagePanelModel getModel() {
        return this.model;
    }

    @Override
    public ImagePanel getView() {
        return this.view;
    }

    @Override
    public void setModel(Object model) {
        this.model = (ImagePanelModel)model;
        this.notifyController();
    }

    public void loadMriFiles(File folder) {
        if (folder != null) {
            new Thread(){

                @Override
                public void run() {
                    ImagePaneController.access$2(ImagePaneController.this, new MyLoader());
                    try {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            this.model.initMriDicomList();
            this.model.initTmsDicomList();
            ImagePanelModel.mriPath = folder.getPath();
            String[] arrstring = folder.list();
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String fileName = arrstring[n2];
                if (fileName.endsWith(".dcm")) {
                    this.model.getMriDicom().add(this.loadDicom(String.valueOf(folder.getAbsolutePath()) + "\\" + fileName));
                }
                ++n2;
            }
            Collections.sort(this.model.getMriDicom());
            this.model.setActualSnapshot(0);
            this.notifyController();
            this.loader.dispose();
        }
    }

    public void loadTmsFiles(File folder) {
        if (folder != null) {
            MyLoader loader = new MyLoader();
            this.model.initTmsDicomList();
            ImagePanelModel.tmsPath = folder.getPath();
            String[] arrstring = folder.list();
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String fileName = arrstring[n2];
                if (fileName.endsWith(".dcm")) {
                    this.model.getTmsDicom().add(this.loadDicom(String.valueOf(folder.getAbsolutePath()) + "\\" + fileName));
                }
                ++n2;
            }
            Collections.sort(this.model.getTmsDicom());
            File f = new File(String.valueOf(ImagePanelModel.tmsPath) + "//" + "groups.out");
            if (f.exists()) {
                if (UtilityClass.showConfirmDialog("Chcete na\u010d\u00edst data ze souboru")) {
                    this.importGroupsFromFile(f);
                } else {
                    this.calculateTMSPoints();
                }
            } else {
                this.calculateTMSPoints();
            }
            this.notifyController();
            loader.dispose();
        }
    }

    public MyDicom loadDicom(String filePath) {
        MyDicom dcm = new MyDicom();
        dcm.open(filePath);
        return dcm;
    }

    private void calculateTMSPoints() {
        this.model.setGroups(new ArrayList<GroupModel>());
        GroupModel group = new GroupModel("ignorovan\u00e9 body");
        this.model.getGroups().add(group);
        group = new GroupModel("nep\u0159i\u0159azen\u00e9 body");
        this.model.getGroups().add(group);
        int i = 0;
        while (i < this.model.getTmsDicom().size()) {
            for (MyPoint myPoint : this.convertTMSDicomToAreaPoints(this.model.getTmsDicom().get(i))) {
                myPoint.setZ(i);
                myPoint.setGroup(group);
                group.getPoints().add(myPoint);
            }
            ++i;
        }
        this.AssignAmplitudesToPoints(group.getPoints());
    }

    private void AssignAmplitudesToPoints(ArrayList<MyPoint> points) {
        int minValue = 255;
        int maxValue = 0;
        for (MyPoint myPoint : points) {
            if (myPoint.getPixelValue() < minValue) {
                minValue = myPoint.getPixelValue();
            }
            if (myPoint.getPixelValue() <= maxValue) continue;
            maxValue = myPoint.getPixelValue();
        }
        TriggerMarkers markers = new TriggerMarkers();
        int minResponse = (int)markers.getMinValue();
        int maxResponse = (int)markers.getMaxValue();
        for (MyPoint myPoint : points) {
            myPoint.calculateAmplitude(maxValue, minValue, minResponse, maxResponse);
        }
    }

    private ArrayList<MyPoint> convertTMSDicomToAreaPoints(MyDicom dcm) {
        BufferedImage img = dcm.getBufferedImage();
        ArrayList<MyPoint> area = this.getArrayOfPixels(img);
        area = this.getArrayOfPoints(area);
        return area;
    }

    private ArrayList<MyPoint> getArrayOfPixels(BufferedImage img) {
        ArrayList<MyPoint> area = new ArrayList<MyPoint>();
        int x = 0;
        while (x < img.getWidth()) {
            int y = 0;
            while (y < img.getHeight()) {
                int blue;
                int green;
                int color = img.getRGB(x, y);
                int red = (color & 16711680) >> 16;
                int total = (red + (green = (color & 65280) >> 8) + (blue = color & 255)) / 3;
                if (total > 10) {
                    area.add(new MyPoint(x, y, 1.0, total));
                }
                ++y;
            }
            ++x;
        }
        return area;
    }

    private ArrayList<MyPoint> getArrayOfPoints(ArrayList<MyPoint> area) {
        ArrayList<MyPoint> retArea = new ArrayList<MyPoint>();
        while (area.size() != 0) {
            retArea.add(this.createPoint(area));
        }
        return retArea;
    }

    private MyPoint createPoint(ArrayList<MyPoint> area) {
        ArrayList<MyPoint> pointsInPoint = new ArrayList<MyPoint>();
        Stack<MyPoint> stack = new Stack<MyPoint>();
        stack.push(area.remove(0));
        while (!stack.isEmpty()) {
            MyPoint refPoint = (MyPoint)stack.pop();
            if (!pointsInPoint.contains(refPoint)) {
                pointsInPoint.add(refPoint);
            }
            int i = 0;
            while (i < area.size()) {
                int distX = Math.abs((int)(refPoint.getCenterX() - area.get(i).getCenterX()));
                int distY = Math.abs((int)(refPoint.getCenterY() - area.get(i).getCenterY()));
                if (distX <= 1 && distY <= 1) {
                    stack.add(area.remove(i));
                }
                ++i;
            }
        }
        int x1 = 384;
        int x2 = 0;
        int y1 = 384;
        int y2 = 0;
        int totalPixelValue = 0;
        int i = 0;
        while (i < pointsInPoint.size()) {
            MyPoint out = (MyPoint)pointsInPoint.get(i);
            totalPixelValue += out.getPixelValue();
            double outX = out.getCenterX();
            double outY = out.getCenterY();
            if (outX < (double)x1) {
                x1 = (int)outX;
            }
            if (outX > (double)x2) {
                x2 = (int)outX;
            }
            if (outY < (double)y1) {
                y1 = (int)outY;
            }
            if (outY > (double)y2) {
                y2 = (int)outY;
            }
            ++i;
        }
        double diameter = this.getDiameter(x2 - x1, y2 - y1);
        int pixelValue = 0;
        if (pointsInPoint.size() != 0) {
            pixelValue = totalPixelValue / pointsInPoint.size();
        }
        return new MyPoint(x1, y1, diameter, pixelValue);
    }

    private double getDiameter(int i, int j) {
        return (i + j) / 2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void exportGroupsToFile() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        if (this.model != null) {
            if (this.model.getGroups() != null) {
                if (this.model.getGroups().size() != 0) {
                    try {
                        try {
                            fos = new FileOutputStream(String.valueOf(ImagePanelModel.tmsPath) + "//" + "groups.out");
                            oos = new ObjectOutputStream(fos);
                            oos.writeObject(this.model.getGroups());
                            oos.flush();
                            UtilityClass.showInfoNotification("Skupiny byly exportov\u00e1ny");
                            return;
                        }
                        catch (FileNotFoundException e) {
                            e.printStackTrace();
                            if (oos == null) return;
                            try {
                                oos.close();
                                return;
                            }
                            catch (IOException e2) {
                                e2.printStackTrace();
                            }
                            return;
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                            if (oos == null) return;
                            try {
                                oos.close();
                                return;
                            }
                            catch (IOException e3) {
                                e3.printStackTrace();
                            }
                            return;
                        }
                    }
                    finally {
                        if (oos != null) {
                            try {
                                oos.close();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                UtilityClass.showInfoNotification("Skupiny pro export neexistuj\u00ed!");
                return;
            }
            UtilityClass.showInfoNotification("Skupiny pro export neexistuj\u00ed!");
            return;
        }
        UtilityClass.showInfoNotification("Skupiny pro export neexistuj\u00ed!");
    }

    private void importGroupsFromFile(File f) {
        block13 : {
            Object objectinputstream = null;
            if (this.getModel().getGroups() != null) {
                FileInputStream is = null;
                ObjectInputStream ois = null;
                try {
                    try {
                        is = new FileInputStream(f);
                        ois = new ObjectInputStream(is);
                        this.getModel().setGroups((ArrayList)ois.readObject());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        if (objectinputstream == null) break block13;
                        try {
                            is.close();
                            ois.close();
                        }
                        catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                finally {
                    if (objectinputstream != null) {
                        try {
                            is.close();
                            ois.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void notifyController() {
        this.view.repaint();
    }

    @Override
    public void notifyAllControllers() {
    }

    @Override
    public Controllers getType() {
        return Controllers.IMAGE_PANE_CTRL;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int steps = e.getWheelRotation();
        this.model.setActualSnapshot(this.model.getActualSnapshot() + steps);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.getModel().getActualSnapshot() != -1 && this.getModel().getGroups() != null && this.getModel().getGroups().size() != 0) {
            for (GroupModel group : this.getModel().getGroups()) {
                for (MyPoint point : group.getPointFromLayer(this.model.getActualSnapshot())) {
                    if (!point.contains((double)(e.getX() - this.view.getX_offset()) / this.view.getRatio(), (double)(e.getY() - this.view.getY_offset()) / this.view.getRatio())) continue;
                    System.out.println("interakce");
                    SettingSnapshotPaneController ctrl = (SettingSnapshotPaneController)MainWindow.getController(Controllers.SETTING_SNAPSHOT_PANE_CTRL);
                    ctrl.setModel(point);
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    static /* synthetic */ void access$2(ImagePaneController imagePaneController, MyLoader myLoader) {
        imagePaneController.loader = myLoader;
    }

}

