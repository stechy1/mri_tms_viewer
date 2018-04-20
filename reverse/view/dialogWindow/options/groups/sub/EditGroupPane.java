/*
 * Decompiled with CFR 0_123.
 */
package view.dialogWindow.options.groups.sub;

import controller.dialogWindow.group.EditGroupPaneController;
import enums.Controllers;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

public class EditGroupPane
extends JFrame {
    private JLabel lblTitle;
    private JTextField tfTitle;
    private JLabel lblColor;
    private JButton btnChangeColor;
    private JPanel componentPane;
    private JPanel controlPanel;
    private JButton btnSave;

    public EditGroupPane(GroupModel model) {
        this.initComponents(model);
        this.pack();
        this.setVisible(true);
    }

    private void initComponents(GroupModel model) {
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(2);
        this.setTitle("Uprava skupiny");
        this.setLocationRelativeTo((MainWindow)MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getModel());
        this.setPreferredSize(new Dimension(320, 240));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.rowHeights = new int[3];
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gridBagLayout);
        this.componentPane = new JPanel();
        GridBagConstraints gbc_componentPane = new GridBagConstraints();
        gbc_componentPane.insets = new Insets(0, 0, 5, 0);
        gbc_componentPane.fill = 1;
        gbc_componentPane.gridx = 0;
        gbc_componentPane.gridy = 0;
        this.add((Component)this.componentPane, gbc_componentPane);
        GridBagLayout gbl_componentPane = new GridBagLayout();
        gbl_componentPane.columnWidths = new int[3];
        gbl_componentPane.rowHeights = new int[3];
        gbl_componentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_componentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        this.componentPane.setLayout(gbl_componentPane);
        this.lblTitle = new JLabel("N\u00e1zev");
        GridBagConstraints gbc_lblTitle = new GridBagConstraints();
        gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
        gbc_lblTitle.gridx = 0;
        gbc_lblTitle.gridy = 0;
        this.componentPane.add((Component)this.lblTitle, gbc_lblTitle);
        this.tfTitle = new JTextField();
        GridBagConstraints gbc_tfTitle = new GridBagConstraints();
        gbc_tfTitle.fill = 2;
        gbc_tfTitle.insets = new Insets(0, 0, 5, 0);
        gbc_tfTitle.gridx = 1;
        gbc_tfTitle.gridy = 0;
        this.componentPane.add((Component)this.tfTitle, gbc_tfTitle);
        this.tfTitle.setColumns(10);
        this.lblColor = new JLabel("Barva");
        GridBagConstraints gbc_lblColor = new GridBagConstraints();
        gbc_lblColor.insets = new Insets(0, 0, 0, 5);
        gbc_lblColor.gridx = 0;
        gbc_lblColor.gridy = 1;
        this.componentPane.add((Component)this.lblColor, gbc_lblColor);
        this.btnChangeColor = new JButton("Zm\u011bna barvy");
        this.btnChangeColor.setActionCommand("changeColor");
        GridBagConstraints gbc_btnChangeColor = new GridBagConstraints();
        gbc_btnChangeColor.anchor = 17;
        gbc_btnChangeColor.gridx = 1;
        gbc_btnChangeColor.gridy = 1;
        this.componentPane.add((Component)this.btnChangeColor, gbc_btnChangeColor);
        this.controlPanel = new JPanel();
        GridBagConstraints gbc_controlPanel = new GridBagConstraints();
        gbc_controlPanel.fill = 1;
        gbc_controlPanel.gridx = 0;
        gbc_controlPanel.gridy = 1;
        this.add((Component)this.controlPanel, gbc_controlPanel);
        this.btnSave = new JButton("Ulo\u017eit");
        this.btnSave.setActionCommand("save");
        this.controlPanel.add(this.btnSave);
        EditGroupPaneController controller = new EditGroupPaneController(this, model);
        this.btnSave.addActionListener(controller);
        this.btnChangeColor.addActionListener(controller);
    }

    public JTextField getTfTitle() {
        return this.tfTitle;
    }

    public JButton getBtnChangeColor() {
        return this.btnChangeColor;
    }

    public JPanel getComponentPane() {
        return this.componentPane;
    }
}

