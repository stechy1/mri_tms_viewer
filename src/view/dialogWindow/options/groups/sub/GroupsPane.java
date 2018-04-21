/*
 * Decompiled with CFR 0_123.
 */
package view.dialogWindow.options.groups.sub;

import enums.Controllers;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import javax.swing.JPanel;
import model.ImagePanelModel;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.dialogWindow.options.groups.sub.GroupItemPane;

public class GroupsPane
extends JPanel {
    public GroupsPane() {
        this.initComponents();
    }

    public void initComponents() {
        this.removeAll();
        this.updateUI();
        ImagePanelModel ipm = (ImagePanelModel)MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
        if (ipm != null) {
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[2];
            gridBagLayout.rowHeights = this.getRowHeights(ipm.getGroups().size());
            gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = this.getRowWeights(ipm.getGroups().size());
            this.setLayout(gridBagLayout);
            GridBagConstraints gbc_groupItem = new GridBagConstraints();
            gbc_groupItem.fill = 1;
            gbc_groupItem.gridx = 0;
            gbc_groupItem.gridy = 0;
            if (ipm.getGroups() != null) {
                for (GroupModel group : ipm.getGroups()) {
                    GroupItemPane item = new GroupItemPane(group);
                    this.add((Component)item, gbc_groupItem);
                    ++gbc_groupItem.gridy;
                }
            }
            this.validate();
            this.updateUI();
        }
    }

    private double[] getRowWeights(int count) {
        double[] rowsWeights = new double[count + 1];
        int i = 0;
        while (i < count) {
            rowsWeights[i] = 0.0;
            ++i;
        }
        rowsWeights[rowsWeights.length - 1] = 1.0;
        return rowsWeights;
    }

    private int[] getRowHeights(int count) {
        int[] rowHeights = new int[count + 1];
        int i = 0;
        while (i < count) {
            rowHeights[i] = 0;
            ++i;
        }
        rowHeights[count] = 1;
        return rowHeights;
    }
}

