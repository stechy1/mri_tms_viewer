package view.rightPane;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JSeparator;

import view.rightPane.subPane.EnhanceImagePane;
import view.rightPane.subPane.PatientInfoPane;
import view.rightPane.subPane.SettingSnapshotPane;
import view.rightPane.subPane.SnapshotPane;

import java.awt.Color;

import javax.swing.JScrollPane;

public class RightControlPanel extends JPanel {

    private SnapshotPane snapshotPane;
    private EnhanceImagePane enhanceImagePane;
    private SettingSnapshotPane setttingSnapshotPane;
    private PatientInfoPane patientInfoPane;

    /**
     * Create the panel.
     */
    public RightControlPanel() {

        initComponents();
    }

    private void initComponents() {

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0};
        setLayout(gridBagLayout);

        this.snapshotPane = new SnapshotPane();
        GridBagConstraints gbc_snapshotPane = new GridBagConstraints();
        gbc_snapshotPane.insets = new Insets(0, 0, 5, 0);
        gbc_snapshotPane.fill = GridBagConstraints.BOTH;
        gbc_snapshotPane.gridx = 0;
        gbc_snapshotPane.gridy = 0;
        add(this.snapshotPane, gbc_snapshotPane);

        this.enhanceImagePane = new EnhanceImagePane();
        GridBagConstraints gbc_brightnessPane = new GridBagConstraints();
        gbc_brightnessPane.insets = new Insets(0, 0, 5, 0);
        gbc_brightnessPane.fill = GridBagConstraints.BOTH;
        gbc_brightnessPane.gridx = 0;
        gbc_brightnessPane.gridy = 1;
        add(this.enhanceImagePane, gbc_brightnessPane);

        this.setttingSnapshotPane = new SettingSnapshotPane();
        GridBagConstraints gbc_setttingSnapshotPane = new GridBagConstraints();
        gbc_setttingSnapshotPane.insets = new Insets(0, 0, 5, 0);
        gbc_setttingSnapshotPane.fill = GridBagConstraints.BOTH;
        gbc_setttingSnapshotPane.gridx = 0;
        gbc_setttingSnapshotPane.gridy = 2;
        add(this.setttingSnapshotPane, gbc_setttingSnapshotPane);

        this.patientInfoPane = new PatientInfoPane();
        GridBagConstraints gbc_patientInfoPane = new GridBagConstraints();
        gbc_patientInfoPane.anchor = GridBagConstraints.NORTH;
        gbc_patientInfoPane.insets = new Insets(0, 0, 5, 0);
        gbc_patientInfoPane.fill = GridBagConstraints.HORIZONTAL;
        gbc_patientInfoPane.gridx = 0;
        gbc_patientInfoPane.gridy = 3;
        add(this.patientInfoPane, gbc_patientInfoPane);
    }

}
