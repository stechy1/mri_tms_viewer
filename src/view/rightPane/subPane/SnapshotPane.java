/*
 * Decompiled with CFR 0_123.
 */
package view.rightPane.subPane;

import controller.rightPane.SnapshotPaneController;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseWheelListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import view.MainWindow;

public class SnapshotPane
extends JPanel {
    private JLabel lblSnapshot;
    private JPanel panel;
    private JSlider slider;
    private JLabel lblValue;

    public SnapshotPane() {
        this.initComponents();
    }

    private void initComponents() {
        SnapshotPaneController controller = new SnapshotPaneController(this);
        MainWindow.addController(controller);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.rowHeights = new int[3];
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gridBagLayout);
        this.lblSnapshot = new JLabel("Sn\u00edmek");
        GridBagConstraints gbc_lblSnapshot = new GridBagConstraints();
        gbc_lblSnapshot.anchor = 17;
        gbc_lblSnapshot.insets = new Insets(0, 0, 5, 0);
        gbc_lblSnapshot.gridx = 0;
        gbc_lblSnapshot.gridy = 0;
        this.add((Component)this.lblSnapshot, gbc_lblSnapshot);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[3];
        gbl_panel.rowHeights = new int[2];
        gbl_panel.columnWeights = new double[]{0.8, 0.2, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        this.panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = 1;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 1;
        this.panel.setLayout(gbl_panel);
        this.add((Component)this.panel, gbc_panel);
        this.slider = new JSlider();
        this.slider.setValue(0);
        this.slider.setPaintTicks(true);
        GridBagConstraints gbc_slider = new GridBagConstraints();
        gbc_slider.fill = 2;
        gbc_slider.insets = new Insets(0, 0, 0, 5);
        gbc_slider.gridx = 0;
        gbc_slider.gridy = 0;
        this.panel.add((Component)this.slider, gbc_slider);
        this.slider.addChangeListener(controller);
        this.slider.addMouseWheelListener(controller);
        this.slider.setMaximum(255);
        this.lblValue = new JLabel(String.valueOf(this.getSlider().getValue()));
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.fill = 3;
        gbc_label.gridx = 1;
        gbc_label.gridy = 0;
        this.panel.add((Component)this.lblValue, gbc_label);
    }

    public JSlider getSlider() {
        return this.slider;
    }

    public JLabel getLblValue() {
        return this.lblValue;
    }
}

