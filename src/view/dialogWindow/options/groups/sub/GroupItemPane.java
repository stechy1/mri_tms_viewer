/*
 * Decompiled with CFR 0_123.
 */
package view.dialogWindow.options.groups.sub;

import controller.UtilityClass;
import controller.centerPane.ImagePaneController;
import enums.Controllers;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.ImagePanelModel;
import model.MyPoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.dialogWindow.options.groups.sub.EditGroupPane;

public class GroupItemPane
extends JPanel
implements ActionListener {
    private JTextField tfGroup;
    private JButton btnEdit;
    private GroupModel model;

    public GroupItemPane(GroupModel model) {
        this.model = model;
        this.initComponents();
    }

    private void initComponents() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[4];
        gridBagLayout.rowHeights = new int[2];
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        this.setLayout(gridBagLayout);
        this.tfGroup = new JTextField(this.model.getName());
        this.tfGroup.setBackground(this.model.getLayerColor());
        this.tfGroup.setEditable(false);
        GridBagConstraints gbc_tfGroup = new GridBagConstraints();
        gbc_tfGroup.fill = 2;
        gbc_tfGroup.insets = new Insets(0, 0, 0, 5);
        gbc_tfGroup.gridx = 0;
        gbc_tfGroup.gridy = 0;
        this.add((Component)this.tfGroup, gbc_tfGroup);
        this.btnEdit = new JButton("Upravit");
        this.btnEdit.setEnabled(!this.model.getName().equals("ignorovan\u00e9 body")
            && !this.model.getName().equals("nep\u0159i\u0159azen\u00e9 body"));
        this.btnEdit.setActionCommand("edit");
        this.btnEdit.addActionListener(this);
        GridBagConstraints gbc_btnEdit = new GridBagConstraints();
        gbc_btnEdit.insets = new Insets(0, 0, 0, 5);
        gbc_btnEdit.gridx = 1;
        gbc_btnEdit.gridy = 0;
        this.add((Component)this.btnEdit, gbc_btnEdit);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String string = e.getActionCommand();
        switch (string.hashCode()) {
            case -1335458389: {
                if (string.equals("delete")) break;
                return;
            }
            case 3108362: {
                if (!string.equals("edit")) return;
                new view.dialogWindow.options.groups.sub.EditGroupPane(this.model);
                return;
            }
        }
        this.deleteGroup(this.model);
        UtilityClass.showInfoNotification("Skupina byla smaz\u00e1na");
    }

    private void deleteGroup(GroupModel model) {
        ImagePaneController ipc = (ImagePaneController)MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
        GroupModel unassign = ipc.getModel().getGroup("nep\u0159i\u0159azen\u00e9 body");
        for (MyPoint point : model.getPoints()) {
            point.setGroup(unassign);
            unassign.getPoints().add(point);
        }
        ipc.getModel().getGroups().remove(model);
    }
}

