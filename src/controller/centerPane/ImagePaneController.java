package controller.centerPane;

import controller.Configuration;
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
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import model.ImagePanelModel;
import model.MyDicom;
import model.MyPoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.centerPane.ImagePanel;
import view.dialogWindow.MyLoader;

public class ImagePaneController implements IController, MouseWheelListener, MouseListener {


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
        return model;
    }

    @Override
    public ImagePanel getView() {
        return view;
    }

    @Override
    public void setModel(Object model) {
        this.model = (ImagePanelModel) model;
        notifyController();
    }

    /*----------------*
     * Ostatni metody *
     *----------------*/


    public void loadMriFiles(File folder) {

        if (folder != null) {

            new Thread() {
                public void run() {
                    loader = new MyLoader();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                ;
            }.start();

            this.model.initMriDicomList();
            this.model.initTmsDicomList();

            ImagePanelModel.mriPath = folder.getPath();

            for (String fileName : folder.list()) {
                if (fileName.endsWith(".dcm")) {
                    this.model.getMriDicom()
                        .add(loadDicom(folder.getAbsolutePath() + "\\" + fileName));

                }
            }
            //Collections.sort(this.model.getMriDicom());
            this.model.setActualSnapshot(0);
            notifyController();

            loader.dispose();
        }
    }

    public void loadTmsFiles(File folder) {
        if (folder != null) {
            MyLoader loader = new MyLoader();

            this.model.initTmsDicomList();
            ImagePanelModel.tmsPath = folder.getPath();

            for (String fileName : folder.list()) {
                if (fileName.endsWith(".dcm")) {
                    this.model.getTmsDicom()
                        .add(loadDicom(folder.getAbsolutePath() + "\\" + fileName));
                    //loader.incProgress();
                }
            }
            Collections.sort(this.model.getTmsDicom());

            //File f = new File(ImagePanelModel.tmsPath + "//" + Configuration.GROUPS);
            File f = new File(ImagePanelModel.tmsPath, Configuration.GROUPS);

            if (f.exists()) {
                if (UtilityClass.showConfirmDialog("Chcete načíst data ze souboru")) {
                    importGroupsFromFile(f);
                } else {
                    calculateTMSPoints();
                }
            } else {
                calculateTMSPoints();
            }

            notifyController();
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

        GroupModel group = new GroupModel(Configuration.IGNORE_GROUP);
        this.model.getGroups().add(group);

        group = new GroupModel(Configuration.UNASSIGN_GROUP);
        this.model.getGroups().add(group);

        for (int i = 0; i < this.model.getTmsDicom().size(); i++) {
            for (MyPoint myPoint : convertTMSDicomToAreaPoints(this.model.getTmsDicom().get(i))) {
                myPoint.setZ(i);
                myPoint.setGroup(group);
                group.getPoints().add(myPoint);
            }
        }


    }

    //Metoda, ktera z DICOM snimku vrati seznam bodu, ktere se na snimku vyskytuji
    private ArrayList<MyPoint> convertTMSDicomToAreaPoints(MyDicom dcm) {

        ArrayList<MyPoint> area = new ArrayList<MyPoint>();
        BufferedImage img = dcm.getBufferedImage();

        //Krok jedna: ziskat vsechny svetle body
        area = getArrayOfPixels(img, area);

        //krok dva: sousedni body spojit do jednoho
        area = getArrayOfPoints(area);

        return area;
    }

    private ArrayList<MyPoint> getArrayOfPoints(ArrayList<MyPoint> area) {
        ArrayList<MyPoint> retArea = new ArrayList<MyPoint>();

        while (area.size() != 0) {
            int x1 = 384, x2 = 0, y1 = 384, y2 = 0;
            Stack<MyPoint> stack = new Stack<MyPoint>();
            stack.push(area.remove(0));
            //zde dochazi k vytvoreni jednoho bodu ze sady pixelu
            while (!stack.isEmpty()) {
                MyPoint out = stack.pop();
                if (out.getX() < x1) {
                    x1 = (int) out.getX();
                }
                if (out.getX() > x2) {
                    x2 = (int) out.getX();
                }
                if (out.getY() < y1) {
                    y1 = (int) out.getY();
                }
                if (out.getY() > y2) {
                    y2 = (int) out.getY();
                }
                addNeightbourToStack(stack, area, out);
            }
            double diameter = getDiameter(x2 - x1, y2 - y1);
            retArea.add(new MyPoint(x1, y1, diameter)); //x2-x1, y2-y1
        }
        return retArea;
    }

    private double getDiameter(int i, int j) {
        return (i + j) / 2;
    }

    private void addNeightbourToStack(Stack<MyPoint> stack, ArrayList<MyPoint> area, MyPoint out) {

        double outX = out.getX();
        double outY = out.getY();

        int i = 0;

        while (i < area.size()) {
            MyPoint myPoint = area.get(i);
            if (
                ((myPoint.getX() == outX - 1) || (myPoint.getX() == outX) || (myPoint.getX()
                    == outX + 1)) &&
                    ((myPoint.getY() == outY - 1) || (myPoint.getY() == outY) || (myPoint.getY()
                        == outY + 1))
                ) {
                stack.push(area.remove(i));
            } else {
                i++;
            }
        }
    }

    private ArrayList<MyPoint> getArrayOfPixels(BufferedImage img, ArrayList<MyPoint> area) {

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int color = img.getRGB(x, y);

                int red = (color & 0x00ff0000) >> 16;
                int green = (color & 0x0000ff00) >> 8;
                int blue = color & 0x000000ff;

                int total = (int) (red + green + blue) / 3;
                if (total > Configuration.whitePixelTresshold) {
                    area.add(new MyPoint(x, y, 1));
                }
            }
        }
        return area;
    }

    public void exportGroupsToFile() {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        if (this.model != null) {
            if (this.model.getGroups() != null) {
                if (this.model.getGroups().size() != 0) {
                    try {
                        //fos = new FileOutputStream(ImagePanelModel.tmsPath + "//" + Configuration.GROUPS);
                        fos = new FileOutputStream(
                            new File(ImagePanelModel.tmsPath, Configuration.GROUPS));
                        oos = new ObjectOutputStream(fos);
                        oos.writeObject(this.model.getGroups());
                        oos.flush();
                        UtilityClass.showInfoNotification("Skupiny byly exportovány");
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        if (oos != null) {
                            try {
                                oos.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    UtilityClass.showInfoNotification("Skupiny pro export neexistují!");
                }
            } else {
                UtilityClass.showInfoNotification("Skupiny pro export neexistují!");
            }
        } else {
            UtilityClass.showInfoNotification("Skupiny pro export neexistují!");
        }

//		if(this.getModel() != null){
//			if(this.getModel().getGroups() != null){
//				if(this.getModel().getGroups().size() != 0){
//					
//					StringBuilder sb = new StringBuilder();
//					
//					for (GroupModel group : this.model.getGroups()) {
//						sb.append(group.exportGroup() + "\n");
//					}
//					
//					File f = new File(ImagePanelModel.tmsPath + "//" + Configuration.GROUPS);
//					if(f.exists()){
//						f.delete();
//					}
//					
//					try {
//						PrintWriter pw = new PrintWriter(f);
//						pw.print(sb.toString());
//						pw.close();
//						UtilityClass.showInfoNotification("Skupiny byly exportovány");
//					} catch (FileNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				else 
//					UtilityClass.showInfoNotification("Skupiny pro export neexistují!");
//			}
//			else
//				UtilityClass.showInfoNotification("Skupiny pro export neexistují!");			
//		}
    }

    private void importGroupsFromFile(File f) {
        ObjectInputStream objectinputstream = null;
        if (this.getModel().getGroups() != null) {

            InputStream is = null;
            ObjectInputStream ois = null;
            try {
                is = new FileInputStream(f);
                ois = new ObjectInputStream(is);
                this.getModel().setGroups((ArrayList<GroupModel>) ois.readObject());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (objectinputstream != null) {
                    try {
                        is.close();
                        ois.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

//	private void importGroupsFromFile(File f) {
//		
//		if(this.getModel() != null){
//			ArrayList<GroupModel> loadedGroup = new ArrayList<GroupModel>();
//			try {
//				BufferedReader br = new BufferedReader(new FileReader(f));
//				String line = br.readLine();
//				while (line != null){
//					
//					GroupModel group = new GroupModel();
//					group.importGroup(line);
//					loadedGroup.add(group);
//					
//					line = br.readLine();
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//	}

    /*-----------------------*
     * Implementovane metody *
     *-----------------------*/


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
//		System.out.println("click: " + this.view.getPaneInfo() + ";; X: " + (e.getX() - this.view.getX_offset()) + 
//				", Y: " + (e.getY() - this.view.getY_offset()));
        if (this.getModel().getActualSnapshot() != -1) {

            if (this.getModel().getGroups() != null) {
                if (this.getModel().getGroups().size() != 0) {

                    for (GroupModel group : this.getModel().getGroups()) {
                        for (MyPoint point : group
                            .getPointFromLayer(this.model.getActualSnapshot())) {
                            if (point.contains(
                                (e.getX() - this.view.getX_offset()) / this.view.getRatio(),
                                (e.getY() - this.view.getY_offset()) / this.view.getRatio())) {
                                //TODO dodelat interakci
                                System.out.println("interakce");
                                SettingSnapshotPaneController ctrl = (SettingSnapshotPaneController) MainWindow
                                    .getController(Controllers.SETTING_SNAPSHOT_PANE_CTRL);
                                ctrl.setModel(point);
                            }
                        }
                    }
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


}
