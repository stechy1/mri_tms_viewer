package view.dialogWindow.options.groups.sub;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import controller.dialogWindow.group.EditGroupPaneController;
import enums.Controllers;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

import java.awt.Insets;
import javax.swing.JButton;

public class EditGroupPane extends JFrame {

    private JLabel lblTitle;
    private JTextField tfTitle;
    private JLabel lblColor;
    private JButton btnChangeColor;
    private JPanel componentPane;
    private JPanel controlPanel;
    private JButton btnSave;

    /**
     * Create the panel.
     */
    public EditGroupPane(GroupModel model) {

        initComponents(model);
        this.pack();
        this.setVisible(true);
    }

    private void initComponents(GroupModel model) {

        this.setAlwaysOnTop(true);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Uprava skupiny");
        this.setLocationRelativeTo(
            (MainWindow) MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getModel());

        this.setPreferredSize(new Dimension(320, 240));

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        this.componentPane = new JPanel();
        GridBagConstraints gbc_componentPane = new GridBagConstraints();
        gbc_componentPane.insets = new Insets(0, 0, 5, 0);
        gbc_componentPane.fill = GridBagConstraints.BOTH;
        gbc_componentPane.gridx = 0;
        gbc_componentPane.gridy = 0;
        add(this.componentPane, gbc_componentPane);
        GridBagLayout gbl_componentPane = new GridBagLayout();
        gbl_componentPane.columnWidths = new int[]{0, 0, 0};
        gbl_componentPane.rowHeights = new int[]{0, 0, 0};
        gbl_componentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_componentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        this.componentPane.setLayout(gbl_componentPane);

        this.lblTitle = new JLabel("Název");
        GridBagConstraints gbc_lblTitle = new GridBagConstraints();
        gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
        gbc_lblTitle.gridx = 0;
        gbc_lblTitle.gridy = 0;
        this.componentPane.add(this.lblTitle, gbc_lblTitle);

        this.tfTitle = new JTextField();
        GridBagConstraints gbc_tfTitle = new GridBagConstraints();
        gbc_tfTitle.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfTitle.insets = new Insets(0, 0, 5, 0);
        gbc_tfTitle.gridx = 1;
        gbc_tfTitle.gridy = 0;
        this.componentPane.add(this.tfTitle, gbc_tfTitle);
        this.tfTitle.setColumns(10);

        this.lblColor = new JLabel("Barva");
        GridBagConstraints gbc_lblColor = new GridBagConstraints();
        gbc_lblColor.insets = new Insets(0, 0, 0, 5);
        gbc_lblColor.gridx = 0;
        gbc_lblColor.gridy = 1;
        this.componentPane.add(this.lblColor, gbc_lblColor);

        this.btnChangeColor = new JButton("Změna barvy");
        this.btnChangeColor.setActionCommand("changeColor");
        GridBagConstraints gbc_btnChangeColor = new GridBagConstraints();
        gbc_btnChangeColor.anchor = GridBagConstraints.WEST;
        gbc_btnChangeColor.gridx = 1;
        gbc_btnChangeColor.gridy = 1;
        this.componentPane.add(this.btnChangeColor, gbc_btnChangeColor);

        this.controlPanel = new JPanel();
        GridBagConstraints gbc_controlPanel = new GridBagConstraints();
        gbc_controlPanel.fill = GridBagConstraints.BOTH;
        gbc_controlPanel.gridx = 0;
        gbc_controlPanel.gridy = 1;
        add(this.controlPanel, gbc_controlPanel);

        this.btnSave = new JButton("Uložit");
        this.btnSave.setActionCommand("save");
        this.controlPanel.add(this.btnSave);

        EditGroupPaneController controller = new EditGroupPaneController(this, model);

        this.btnSave.addActionListener(controller);
        this.btnChangeColor.addActionListener(controller);
    }

    public JTextField getTfTitle() {
        return tfTitle;
    }

    public JButton getBtnChangeColor() {
        return btnChangeColor;
    }

    public JPanel getComponentPane() {
        return componentPane;
    }


}
