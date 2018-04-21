/*
 * Decompiled with CFR 0_123.
 */
package model;

import enums.Controllers;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.MyDicom;
import model.MyPoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

public class ImagePanelModel {
    private List<MyDicom> mriDicom = new ArrayList<>();
    private List<MyDicom> tmsDicom = new ArrayList<>();
    private ArrayList<GroupModel> groups = new ArrayList<>();
    public static String mriPath;
    public static String tmsPath;
    private int actualSnapshot = -1;
    private int brightness = 50;
    private int contrast = 50;
    private float minBrightness = 0.0f;
    private float maxBrightness = 2.0f;
    private float minContrast = 0.0f;
    private float maxContrast = 256.0f;

    public List<MyDicom> getMriDicom() {
        return this.mriDicom;
    }

    public void initMriDicomList() {
        this.mriDicom = new ArrayList<MyDicom>();
        mriPath = null;
    }

    public void initTmsDicomList() {
        this.tmsDicom = new ArrayList<MyDicom>();
        tmsPath = null;
    }

    public List<MyDicom> getTmsDicom() {
        return this.tmsDicom;
    }

    public ArrayList<GroupModel> getGroups() {
        return this.groups;
    }

    public void setGroups(ArrayList<GroupModel> groups) {
        this.groups = groups;
    }

    public int getActualSnapshot() {
        return this.actualSnapshot;
    }

    public int getBrightness() {
        return this.brightness;
    }

    public int getContrast() {
        return this.contrast;
    }

    public void setActualSnapshot(int actualSnapshot) {
        if (this.mriDicom != null && this.mriDicom.size() != 0) {
            this.actualSnapshot = actualSnapshot <= 0 ? 0 : (actualSnapshot >= this.mriDicom.size() ? this.mriDicom.size() - 1 : actualSnapshot);
            MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
            MainWindow.getController(Controllers.SNAPSHOT_PANE_CTRL).notifyController();
            MainWindow.getController(Controllers.PATIENT_INFO_PANE_CTRL).notifyController();
            MainWindow.getController(Controllers.SETTING_SNAPSHOT_PANE_CTRL).notifyController();
        }
    }

    public void setBrightness(int brightness) {
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
            MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
            MainWindow.getController(Controllers.ENHANCE_IMAGE_PANE_CTRL).notifyController();
        }
        this.brightness = brightness;
    }

    public void setContrast(int contrast) {
        if (contrast >= 0 && contrast <= 100) {
            this.contrast = contrast;
            MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
            MainWindow.getController(Controllers.ENHANCE_IMAGE_PANE_CTRL).notifyController();
        }
    }

    public float convertContrast() {
        return (this.maxContrast - this.minContrast) / 100.0f * (float)this.contrast;
    }

    public float convertBrightness() {
        return (this.maxBrightness - this.minBrightness) / 100.0f * (float)this.brightness;
    }

    public void createGroups(int count) {
        ArrayList<MyPoint> listOfPoint = new ArrayList<MyPoint>();
        for (GroupModel group : this.groups) {
            if (group.getName().equals("ignorovan\u00e9 body")) continue;
            for (MyPoint myPoint : group.getPoints()) {
                listOfPoint.add(myPoint);
            }
            if (!group.getName().equals("nep\u0159i\u0159azen\u00e9 body")) continue;
            group.getPoints().clear();
        }
        int i = 0;
        while (i < this.groups.size()) {
            if (!this.groups.get(i).getName().equals("ignorovan\u00e9 body") && !this.groups.get(i).getName().equals("nep\u0159i\u0159azen\u00e9 body")) {
                this.groups.remove(i);
                --i;
            }
            ++i;
        }
        if (listOfPoint != null && listOfPoint.size() != 0) {
            i = 0;
            while (i < count) {
                Random ran = new Random();
                int index = ran.nextInt(999) * (i + 11) % listOfPoint.size();
                MyPoint newCentroid = listOfPoint.get(index);
                GroupModel newGroup = new GroupModel("Skupina " + (i + 1), newCentroid);
                this.groups.add(newGroup);
                ++i;
            }
            this.assignPointToGroups(listOfPoint);
        }
        System.out.println();
    }

    public GroupModel getGroup(String retGroup) {
        for (GroupModel group : this.groups) {
            if (!group.getName().equals(retGroup)) continue;
            return group;
        }
        return null;
    }

    private void assignPointToGroups(ArrayList<MyPoint> list) {
        int countOfChanges = Integer.MAX_VALUE;
        while (countOfChanges != 0) {
            countOfChanges = 0;
            if (this.groups != null) {
                for (GroupModel group : this.groups) {
                    group.computeCentroids();
                    group.getPoints().clear();
                }
            }
            System.out.println();
            for (MyPoint myPoint : list) {
                double minDist = Double.MAX_VALUE;
                GroupModel oldGroup = myPoint.getGroup();
                GroupModel newGroup = myPoint.getGroup();
                for (GroupModel group : this.groups) {
                    double dist;
                    if (group.getName().equals("nep\u0159i\u0159azen\u00e9 body") || group.getName().equals("ignorovan\u00e9 body") || (dist = group.getCentroid().distance(myPoint)) >= minDist) continue;
                    minDist = dist;
                    newGroup = group;
                }
                if (!oldGroup.equals(newGroup)) {
                    ++countOfChanges;
                }
                myPoint.setGroup(newGroup);
                newGroup.getPoints().add(myPoint);
            }
        }
        System.out.println("skupiny vytvoreny");
    }
}

