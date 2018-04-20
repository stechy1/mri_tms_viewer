/*
 * Decompiled with CFR 0_123.
 */
package model;

import controller.Configuration;
import controller.QuickHull;
import enums.Controllers;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

public class GroupVolume
implements Serializable {
    private List<Slice> areas = new ArrayList<Slice>();
    private double volume = 0.0;

    public void updateArea(int index, double value) {
        for (Slice slice : this.areas) {
            if (slice.getSliceIndex() != index) continue;
            slice.setArea(value);
            return;
        }
        this.areas.add(new Slice(index, value));
        Collections.sort(this.areas);
    }

    public void calculateAreas(GroupModel group) {
        this.areas = new ArrayList<Slice>();
        ImagePanelModel ipm = (ImagePanelModel)MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
        int i = 0;
        while (i < ipm.getMriDicom().size()) {
            ArrayList<MyPoint> hullPoint = new QuickHull().quickHull(group.getPointFromLayer(i));
            group.setArea(i, hullPoint);
            ++i;
        }
        Collections.sort(this.areas);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void updateVolume() {
        int start = 0;
        int steps = 1;
        int end = 0;

        double volume;
        for(volume = 0.0D; start != this.areas.size() - 1; start = end) {
            while(((Slice)this.areas.get(start)).getArea() == 0.0D) {
                ++start;
            }

            for(end = start + 1; ((Slice)this.areas.get(end)).getArea() == 0.0D; ++steps) {
                ;
            }

            double st = ((Slice)this.areas.get(start)).getArea();
            double en = ((Slice)this.areas.get(end)).getArea();
            volume += (st + en) / 2.0D * (double)steps * Configuration.sliceThickness;
            steps = 1;
        }

        this.volume = volume;
    }

    public List<Slice> getAreas() {
        return this.areas;
    }

    public double getVolume() {
        this.updateVolume();
        return this.volume;
    }
}

