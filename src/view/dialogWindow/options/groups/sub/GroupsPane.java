package view.dialogWindow.options.groups.sub;

import javax.swing.JPanel;

import enums.Controllers;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import model.ImagePanelModel;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

public class GroupsPane extends JPanel {

    /**
     * Create the panel.
     */
    public GroupsPane() {

        initComponents();
    }

    private void initComponents() {

        this.removeAll();

        this.updateUI();

        ImagePanelModel ipm = (ImagePanelModel) MainWindow
            .getController(Controllers.IMAGE_PANE_CTRL).getModel();

        if (ipm != null) {

            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{0, 0};
            gridBagLayout.rowHeights = getRowHeights(ipm.getGroups().size());
            gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = getRowWeights(ipm.getGroups().size());
            setLayout(gridBagLayout);

            GridBagConstraints gbc_groupItem = new GridBagConstraints();
            gbc_groupItem.fill = GridBagConstraints.BOTH;
            gbc_groupItem.gridx = 0;
            gbc_groupItem.gridy = 0;

            if (ipm.getGroups() != null) {
                for (GroupModel group : ipm.getGroups()) {
                    GroupItemPane item = new GroupItemPane(group);
                    add(item, gbc_groupItem);
                    gbc_groupItem.gridy++;
                }
            }

            this.validate();
            this.updateUI();
        }

    }

    private double[] getRowWeights(int count) {
        double[] rowsWeights = new double[count + 1];
        for (int i = 0; i < count; i++) {
            rowsWeights[i] = 0.0;
        }
        rowsWeights[rowsWeights.length - 1] = 1.0;
        return rowsWeights;
    }

    private int[] getRowHeights(int count) {
        int[] rowHeights = new int[count + 1];
        for (int i = 0; i < count; i++) {
            rowHeights[i] = 0;
        }
        rowHeights[count] = 1;
        return rowHeights;
    }

}
