/*
 * Decompiled with CFR 0_123.
 */
package view.rightPane;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import view.rightPane.subPane.EnhanceImagePane;
import view.rightPane.subPane.PatientInfoPane;
import view.rightPane.subPane.SettingSnapshotPane;
import view.rightPane.subPane.SnapshotPane;

public class RightControlPanel
extends JPanel {
    private SnapshotPane snapshotPane;
    private EnhanceImagePane enhanceImagePane;
    private SettingSnapshotPane setttingSnapshotPane;
    private PatientInfoPane patientInfoPane;

    public RightControlPanel() {
        this.initComponents();
    }

    private void initComponents() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[1];
        gridBagLayout.rowHeights = new int[4];
        gridBagLayout.columnWeights = new double[]{1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0};
        this.setLayout(gridBagLayout);
        this.snapshotPane = new SnapshotPane();
        GridBagConstraints gbc_snapshotPane = new GridBagConstraints();
        gbc_snapshotPane.insets = new Insets(0, 0, 5, 0);
        gbc_snapshotPane.fill = 1;
        gbc_snapshotPane.gridx = 0;
        gbc_snapshotPane.gridy = 0;
        this.add((Component)this.snapshotPane, gbc_snapshotPane);
        this.enhanceImagePane = new EnhanceImagePane();
        GridBagConstraints gbc_brightnessPane = new GridBagConstraints();
        gbc_brightnessPane.insets = new Insets(0, 0, 5, 0);
        gbc_brightnessPane.fill = 1;
        gbc_brightnessPane.gridx = 0;
        gbc_brightnessPane.gridy = 1;
        this.add((Component)this.enhanceImagePane, gbc_brightnessPane);
        this.setttingSnapshotPane = new SettingSnapshotPane();
        GridBagConstraints gbc_setttingSnapshotPane = new GridBagConstraints();
        gbc_setttingSnapshotPane.insets = new Insets(0, 0, 5, 0);
        gbc_setttingSnapshotPane.fill = 1;
        gbc_setttingSnapshotPane.gridx = 0;
        gbc_setttingSnapshotPane.gridy = 2;
        this.add((Component)this.setttingSnapshotPane, gbc_setttingSnapshotPane);
        this.patientInfoPane = new PatientInfoPane();
        GridBagConstraints gbc_patientInfoPane = new GridBagConstraints();
        gbc_patientInfoPane.anchor = 11;
        gbc_patientInfoPane.insets = new Insets(0, 0, 5, 0);
        gbc_patientInfoPane.fill = 2;
        gbc_patientInfoPane.gridx = 0;
        gbc_patientInfoPane.gridy = 3;
        this.add((Component)this.patientInfoPane, gbc_patientInfoPane);
    }
}

