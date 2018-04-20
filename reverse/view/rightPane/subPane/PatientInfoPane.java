/*
 * Decompiled with CFR 0_123.
 */
package view.rightPane.subPane;

import controller.rightPane.PatientInfoPaneController;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import view.MainWindow;

public class PatientInfoPane
extends JPanel {
    private JLabel lblInformaceOPacientovi;
    private JPanel infoPanel;
    private JLabel lblName;
    private JTextField tfName;
    private JLabel lblDateMri;
    private JTextField tfDateMri;
    private JLabel lblDateTMS;
    private JTextField tfDateTMS;

    public PatientInfoPane() {
        this.initComponents();
    }

    private void initComponents() {
        PatientInfoPaneController controller = new PatientInfoPaneController(this);
        MainWindow.addController(controller);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.rowHeights = new int[3];
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gridBagLayout);
        this.lblInformaceOPacientovi = new JLabel("Informace o pacientovi");
        GridBagConstraints gbc_lblInformaceOPacientovi = new GridBagConstraints();
        gbc_lblInformaceOPacientovi.anchor = 17;
        gbc_lblInformaceOPacientovi.insets = new Insets(0, 0, 5, 0);
        gbc_lblInformaceOPacientovi.gridx = 0;
        gbc_lblInformaceOPacientovi.gridy = 0;
        this.add((Component)this.lblInformaceOPacientovi, gbc_lblInformaceOPacientovi);
        this.infoPanel = new JPanel();
        GridBagConstraints gbc_infoPanel = new GridBagConstraints();
        gbc_infoPanel.fill = 1;
        gbc_infoPanel.gridx = 0;
        gbc_infoPanel.gridy = 1;
        this.add((Component)this.infoPanel, gbc_infoPanel);
        GridBagLayout gbl_infoPanel = new GridBagLayout();
        gbl_infoPanel.columnWidths = new int[3];
        gbl_infoPanel.rowHeights = new int[4];
        gbl_infoPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_infoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        this.infoPanel.setLayout(gbl_infoPanel);
        this.lblName = new JLabel("Jm\u00e9no");
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.insets = new Insets(0, 0, 5, 5);
        gbc_lblName.anchor = 17;
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 0;
        this.infoPanel.add((Component)this.lblName, gbc_lblName);
        this.tfName = new JTextField();
        this.tfName.setEditable(false);
        GridBagConstraints gbc_tfName = new GridBagConstraints();
        gbc_tfName.insets = new Insets(0, 0, 5, 0);
        gbc_tfName.fill = 2;
        gbc_tfName.gridx = 1;
        gbc_tfName.gridy = 0;
        this.infoPanel.add((Component)this.tfName, gbc_tfName);
        this.tfName.setColumns(10);
        this.lblDateMri = new JLabel("Datum m\u011b\u0159en\u00ed MRI");
        GridBagConstraints gbc_lblDateMri = new GridBagConstraints();
        gbc_lblDateMri.anchor = 17;
        gbc_lblDateMri.insets = new Insets(0, 0, 5, 5);
        gbc_lblDateMri.gridx = 0;
        gbc_lblDateMri.gridy = 1;
        this.infoPanel.add((Component)this.lblDateMri, gbc_lblDateMri);
        this.tfDateMri = new JTextField();
        this.tfDateMri.setEditable(false);
        GridBagConstraints gbc_tfDateMri = new GridBagConstraints();
        gbc_tfDateMri.insets = new Insets(0, 0, 5, 0);
        gbc_tfDateMri.fill = 2;
        gbc_tfDateMri.gridx = 1;
        gbc_tfDateMri.gridy = 1;
        this.infoPanel.add((Component)this.tfDateMri, gbc_tfDateMri);
        this.tfDateMri.setColumns(10);
        this.lblDateTMS = new JLabel("Datum m\u011b\u0159en\u00ed TMS");
        GridBagConstraints gbc_lblDateTMS = new GridBagConstraints();
        gbc_lblDateTMS.anchor = 13;
        gbc_lblDateTMS.insets = new Insets(0, 0, 0, 5);
        gbc_lblDateTMS.gridx = 0;
        gbc_lblDateTMS.gridy = 2;
        this.infoPanel.add((Component)this.lblDateTMS, gbc_lblDateTMS);
        this.tfDateTMS = new JTextField();
        this.tfDateTMS.setEditable(false);
        GridBagConstraints gbc_tfDateTMS = new GridBagConstraints();
        gbc_tfDateTMS.fill = 2;
        gbc_tfDateTMS.gridx = 1;
        gbc_tfDateTMS.gridy = 2;
        this.infoPanel.add((Component)this.tfDateTMS, gbc_tfDateTMS);
        this.tfDateTMS.setColumns(10);
    }

    public JTextField getTfDateMri() {
        return this.tfDateMri;
    }

    public JTextField getTfDateTMS() {
        return this.tfDateTMS;
    }

    public JTextField getTfName() {
        return this.tfName;
    }
}

