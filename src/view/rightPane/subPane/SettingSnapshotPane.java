/*
 * Decompiled with CFR 0_123.
 */
package view.rightPane.subPane;

import controller.rightPane.SettingSnapshotPaneController;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import view.MainWindow;

public class SettingSnapshotPane
extends JPanel {
    private JLabel lblActivePoint;
    private JPanel controlPanel;
    private JLabel lblGroup;
    private JComboBox cbGroup;
    private JLabel lblAmplitude;
    private JTextField tfAmplitude;
    private JLabel lblLatency;
    private JTextField tfLatency;

    public SettingSnapshotPane() {
        this.initComponents();
    }

    private void initComponents() {
        SettingSnapshotPaneController controller = new SettingSnapshotPaneController(this);
        MainWindow.addController(controller);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.rowHeights = new int[3];
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gridBagLayout);
        this.lblActivePoint = new JLabel("Aktivn\u00ed bod");
        GridBagConstraints gbc_lblActivePoint = new GridBagConstraints();
        gbc_lblActivePoint.anchor = 17;
        gbc_lblActivePoint.insets = new Insets(0, 0, 5, 0);
        gbc_lblActivePoint.gridx = 0;
        gbc_lblActivePoint.gridy = 0;
        this.add((Component)this.lblActivePoint, gbc_lblActivePoint);
        this.controlPanel = new JPanel();
        GridBagConstraints gbc_controlPanel = new GridBagConstraints();
        gbc_controlPanel.fill = 1;
        gbc_controlPanel.gridx = 0;
        gbc_controlPanel.gridy = 1;
        this.add((Component)this.controlPanel, gbc_controlPanel);
        GridBagLayout gbl_controlPanel = new GridBagLayout();
        gbl_controlPanel.columnWidths = new int[3];
        gbl_controlPanel.rowHeights = new int[4];
        gbl_controlPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_controlPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        this.controlPanel.setLayout(gbl_controlPanel);
        this.lblGroup = new JLabel("Skupina");
        GridBagConstraints gbc_lblGroup = new GridBagConstraints();
        gbc_lblGroup.insets = new Insets(0, 0, 5, 5);
        gbc_lblGroup.anchor = 13;
        gbc_lblGroup.gridx = 0;
        gbc_lblGroup.gridy = 0;
        this.controlPanel.add((Component)this.lblGroup, gbc_lblGroup);
        this.cbGroup = new JComboBox();
        this.cbGroup.addItemListener(controller);
        GridBagConstraints gbc_cbGroup = new GridBagConstraints();
        gbc_cbGroup.insets = new Insets(0, 0, 5, 0);
        gbc_cbGroup.fill = 2;
        gbc_cbGroup.gridx = 1;
        gbc_cbGroup.gridy = 0;
        this.controlPanel.add((Component)this.cbGroup, gbc_cbGroup);
        this.lblAmplitude = new JLabel("Amplituda");
        GridBagConstraints gbc_lblAmplitude = new GridBagConstraints();
        gbc_lblAmplitude.anchor = 13;
        gbc_lblAmplitude.insets = new Insets(0, 0, 5, 5);
        gbc_lblAmplitude.gridx = 0;
        gbc_lblAmplitude.gridy = 1;
        this.controlPanel.add((Component)this.lblAmplitude, gbc_lblAmplitude);
        this.tfAmplitude = new JTextField();
        this.tfAmplitude.addKeyListener(controller);
        GridBagConstraints gbc_tfAmplitude = new GridBagConstraints();
        gbc_tfAmplitude.insets = new Insets(0, 0, 5, 0);
        gbc_tfAmplitude.fill = 2;
        gbc_tfAmplitude.gridx = 1;
        gbc_tfAmplitude.gridy = 1;
        this.controlPanel.add((Component)this.tfAmplitude, gbc_tfAmplitude);
        this.tfAmplitude.setColumns(10);
        this.lblLatency = new JLabel("Latence");
        GridBagConstraints gbc_lblLatency = new GridBagConstraints();
        gbc_lblLatency.anchor = 13;
        gbc_lblLatency.insets = new Insets(0, 0, 0, 5);
        gbc_lblLatency.gridx = 0;
        gbc_lblLatency.gridy = 2;
        this.controlPanel.add((Component)this.lblLatency, gbc_lblLatency);
        this.tfLatency = new JTextField();
        this.tfLatency.addKeyListener(controller);
        GridBagConstraints gbc_tfLatency = new GridBagConstraints();
        gbc_tfLatency.fill = 2;
        gbc_tfLatency.gridx = 1;
        gbc_tfLatency.gridy = 2;
        this.controlPanel.add((Component)this.tfLatency, gbc_tfLatency);
        this.tfLatency.setColumns(10);
    }

    public JComboBox getCbGroup() {
        return this.cbGroup;
    }

    public JTextField getTfAmplitude() {
        return this.tfAmplitude;
    }

    public JTextField getTfLatency() {
        return this.tfLatency;
    }
}

