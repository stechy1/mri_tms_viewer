/*
 * Decompiled with CFR 0_123.
 */
package view.leftPane;

import controller.leftPane.LeftControlPanelController;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import view.MainWindow;

public class LeftControlPanel
extends JPanel {
    private JButton btnLoadMri;
    private JButton btnLoadTms;
    private JButton btnSnapshotInfo;
    private JButton btnSaveCurrentSnapshot;
    private JButton btnSaveAllSnapshots;
    private JButton btnSettings;
    private JButton btnSaveGroup;

    public LeftControlPanel() {
        this.initComponents();
    }

    private void initComponents() {
        LeftControlPanelController controller = new LeftControlPanelController(this);
        MainWindow.addController(controller);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.rowHeights = new int[8];
        gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        this.setLayout(gridBagLayout);
        this.btnLoadMri = new JButton("Nacti MRI");
        this.btnLoadMri.setActionCommand("loadMri");
        this.btnLoadMri.addActionListener(controller);
        GridBagConstraints gbc_btnLoadMri = new GridBagConstraints();
        gbc_btnLoadMri.fill = 2;
        gbc_btnLoadMri.insets = new Insets(0, 0, 5, 0);
        gbc_btnLoadMri.gridx = 0;
        gbc_btnLoadMri.gridy = 0;
        this.add((Component)this.btnLoadMri, gbc_btnLoadMri);
        this.btnLoadTms = new JButton("Nacti TMS");
        this.btnLoadTms.setActionCommand("loadTms");
        this.btnLoadTms.addActionListener(controller);
        GridBagConstraints gbc_btnLoadTms = new GridBagConstraints();
        gbc_btnLoadTms.fill = 2;
        gbc_btnLoadTms.insets = new Insets(0, 0, 5, 0);
        gbc_btnLoadTms.gridx = 0;
        gbc_btnLoadTms.gridy = 1;
        this.add((Component)this.btnLoadTms, gbc_btnLoadTms);
        this.btnSnapshotInfo = new JButton("Informace o snimku");
        this.btnSnapshotInfo.setActionCommand("snapshotInfo");
        this.btnSnapshotInfo.addActionListener(controller);
        GridBagConstraints gbc_btnSnapshotInfo = new GridBagConstraints();
        gbc_btnSnapshotInfo.insets = new Insets(0, 0, 5, 0);
        gbc_btnSnapshotInfo.fill = 1;
        gbc_btnSnapshotInfo.gridx = 0;
        gbc_btnSnapshotInfo.gridy = 2;
        this.add((Component)this.btnSnapshotInfo, gbc_btnSnapshotInfo);
        this.btnSaveCurrentSnapshot = new JButton("Ulo\u017eit aktu\u00e1ln\u00ed obr\u00e1zek");
        this.btnSaveCurrentSnapshot.setActionCommand("saveCurrent");
        this.btnSaveCurrentSnapshot.addActionListener(controller);
        GridBagConstraints gbc_btnSaveCurrentSnapshot = new GridBagConstraints();
        gbc_btnSaveCurrentSnapshot.insets = new Insets(0, 0, 5, 0);
        gbc_btnSaveCurrentSnapshot.fill = 2;
        gbc_btnSaveCurrentSnapshot.gridx = 0;
        gbc_btnSaveCurrentSnapshot.gridy = 3;
        this.add((Component)this.btnSaveCurrentSnapshot, gbc_btnSaveCurrentSnapshot);
        this.btnSaveAllSnapshots = new JButton("Ulo\u017eit v\u0161echny obr\u00e1zky");
        this.btnSaveAllSnapshots.setActionCommand("saveAll");
        this.btnSaveAllSnapshots.addActionListener(controller);
        GridBagConstraints gbc_btnSaveAllSnapshots = new GridBagConstraints();
        gbc_btnSaveAllSnapshots.insets = new Insets(0, 0, 5, 0);
        gbc_btnSaveAllSnapshots.gridx = 0;
        gbc_btnSaveAllSnapshots.gridy = 4;
        this.add((Component)this.btnSaveAllSnapshots, gbc_btnSaveAllSnapshots);
        this.btnSaveGroup = new JButton("Ulo\u017e skupiny");
        this.btnSaveGroup.setActionCommand("saveGroup");
        this.btnSaveGroup.addActionListener(controller);
        GridBagConstraints gbc_btnSaveGroup = new GridBagConstraints();
        gbc_btnSaveGroup.fill = 1;
        gbc_btnSaveGroup.insets = new Insets(0, 0, 5, 0);
        gbc_btnSaveGroup.gridx = 0;
        gbc_btnSaveGroup.gridy = 5;
        this.add((Component)this.btnSaveGroup, gbc_btnSaveGroup);
        this.btnSettings = new JButton("Nastaven\u00ed");
        this.btnSettings.setActionCommand("settings");
        this.btnSettings.addActionListener(controller);
        GridBagConstraints gbc_btnSettings = new GridBagConstraints();
        gbc_btnSettings.anchor = 15;
        gbc_btnSettings.fill = 2;
        gbc_btnSettings.gridx = 0;
        gbc_btnSettings.gridy = 6;
        this.add((Component)this.btnSettings, gbc_btnSettings);
    }
}

