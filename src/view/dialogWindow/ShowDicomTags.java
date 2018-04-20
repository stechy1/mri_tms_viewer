/*
 * Decompiled with CFR 0_123.
 */
package view.dialogWindow;

import controller.dialogWindow.ShowDicomTagsController;
import enums.Controllers;
import ij.plugin.DICOM;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import model.DicomTagsTable;
import model.ImagePanelModel;
import model.MyDicom;
import view.MainWindow;

public class ShowDicomTags
extends JFrame {
    private DicomTagsTable dicomTableMRI;
    private DicomTagsTable dicomTableTMS;
    private JPanel contentPane;
    private JTabbedPane tabs;
    private JScrollPane scrollPaneMri;
    private JScrollPane scrollPaneTms;
    private JPanel controlPanel;
    private JButton btnOk;

    public ShowDicomTags() {
        this.initComponents();
        this.pack();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(2);
        this.setTitle("Informace o souboru");
        this.setLocationRelativeTo(null);
        this.setBounds(100, 100, 450, 300);
        ShowDicomTagsController controller = new ShowDicomTagsController(this);
        MainWindow.addController(controller);
        this.contentPane = new JPanel();
        GridBagLayout gbl_contentPane = new GridBagLayout();
        int[] arrn = new int[2];
        arrn[0] = 116;
        gbl_contentPane.columnWidths = arrn;
        int[] arrn2 = new int[3];
        arrn2[0] = 33;
        arrn2[1] = 33;
        gbl_contentPane.rowHeights = arrn2;
        gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        this.contentPane.setLayout(gbl_contentPane);
        this.setContentPane(this.contentPane);
        this.tabs = new JTabbedPane();
        GridBagConstraints gbc_tabs = new GridBagConstraints();
        gbc_tabs.fill = 1;
        gbc_tabs.insets = new Insets(0, 0, 5, 0);
        gbc_tabs.gridx = 0;
        gbc_tabs.gridy = 0;
        this.contentPane.add((Component)this.tabs, gbc_tabs);
        this.controlPanel = new JPanel();
        GridBagConstraints gbc_controlPanel = new GridBagConstraints();
        gbc_controlPanel.fill = 1;
        gbc_controlPanel.gridx = 0;
        gbc_controlPanel.gridy = 1;
        this.contentPane.add((Component)this.controlPanel, gbc_controlPanel);
        this.btnOk = new JButton("Ok");
        this.btnOk.setActionCommand("ok");
        this.btnOk.addActionListener(controller);
        this.controlPanel.add(this.btnOk);
        ImagePanelModel ipm = (ImagePanelModel)MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
        this.dicomTableMRI = new DicomTagsTable(ipm.getMriDicom().get(ipm.getActualSnapshot()));
        this.scrollPaneMri = new JScrollPane(this.dicomTableMRI);
        this.scrollPaneMri.setHorizontalScrollBarPolicy(30);
        this.scrollPaneMri.setVerticalScrollBarPolicy(20);
        this.tabs.addTab("MRI info", null, this.scrollPaneMri, null);
    }

    public DicomTagsTable getDicomTableMRI() {
        return this.dicomTableMRI;
    }

    public DicomTagsTable getDicomTableTMS() {
        return this.dicomTableTMS;
    }
}

