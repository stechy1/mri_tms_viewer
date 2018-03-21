package view.rightPane.subPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

import controller.Configuration;
import controller.rightPane.EnhanceImagePaneController;
import view.MainWindow;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class EnhanceImagePane extends JPanel {

    private JLabel lblJas;
    private JPanel brightnessPane;
    private JSlider BrightnessSlider;
    private JLabel lblBrightnessValue;
    private JLabel lblpravaZobrazen;
    private JPanel ContrastPane;
    private JLabel lblContrast;
    private JSlider ContrastSlider;
    private JLabel lblContrastValue;

    /**
     * Create the panel.
     */
    public EnhanceImagePane() {

        initComponents();
    }

    private void initComponents() {

        EnhanceImagePaneController controller = new EnhanceImagePaneController(this);
        MainWindow.addController(controller);

        setBorder(new EmptyBorder(5, 5, 5, 5));

        //Hlavn� layout
        GridBagLayout gblMain = new GridBagLayout();
        gblMain.columnWidths = new int[]{0, 0};
        gblMain.rowHeights = new int[]{0, 0, 0};
        gblMain.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblMain.rowWeights = new double[]{0.0, 0.0, 0.0};
        this.setLayout(gblMain);

        //Omezeni pro header
        GridBagConstraints gbcHeader = new GridBagConstraints();
        gbcHeader.anchor = GridBagConstraints.WEST;
        gbcHeader.insets = new Insets(0, 0, 5, 0);
        gbcHeader.gridx = 0;
        gbcHeader.gridy = 0;
        this.lblpravaZobrazen = new JLabel("Úprava zobrazení");
        add(this.lblpravaZobrazen, gbcHeader);

        //Layout pro jas
        GridBagLayout gbl_brightnessPane = new GridBagLayout();
        gbl_brightnessPane.columnWidths = new int[]{0, 0, 0};
        gbl_brightnessPane.rowHeights = new int[]{0};
        gbl_brightnessPane.columnWeights = new double[]{0.0, 1.0, 0};
        gbl_brightnessPane.rowWeights = new double[]{0.0};

        //Nastaveni panelu pro jas
        GridBagConstraints gbc_brightnessPane = new GridBagConstraints();
        gbc_brightnessPane.insets = new Insets(0, 0, 5, 0);
        gbc_brightnessPane.fill = GridBagConstraints.BOTH;
        gbc_brightnessPane.gridx = 0;
        gbc_brightnessPane.gridy = 1;

        this.brightnessPane = new JPanel(gbl_brightnessPane);
        add(this.brightnessPane, gbc_brightnessPane);

        this.lblJas = new JLabel("Jas");
        GridBagConstraints gbc_lblBrightness = new GridBagConstraints();
        gbc_lblBrightness.insets = new Insets(0, 0, 0, 5);
        gbc_lblBrightness.gridx = 0;
        gbc_lblBrightness.gridy = 0;
        this.brightnessPane.add(this.lblJas, gbc_lblBrightness);

        this.BrightnessSlider = new JSlider();
        this.BrightnessSlider.setPaintTicks(true);
        this.BrightnessSlider.addChangeListener(controller);
        this.BrightnessSlider.addMouseWheelListener(controller);
        this.BrightnessSlider.setMaximum(100);
        this.BrightnessSlider.setMinimum(0);
        this.BrightnessSlider.setValue(30);
        this.BrightnessSlider.setBorder(new EmptyBorder(5, 0, 0, 5));
        GridBagConstraints gbc_slider = new GridBagConstraints();
        gbc_slider.fill = GridBagConstraints.HORIZONTAL;
        gbc_slider.insets = new Insets(0, 0, 0, 5);
        gbc_slider.gridx = 1;
        gbc_slider.gridy = 0;
        this.brightnessPane.add(this.BrightnessSlider, gbc_slider);

        this.lblBrightnessValue = new JLabel(this.BrightnessSlider.getValue() + "%");
        GridBagConstraints gbc_lblValue = new GridBagConstraints();
        gbc_lblValue.fill = GridBagConstraints.VERTICAL;
        gbc_lblValue.gridx = 2;
        gbc_lblValue.gridy = 0;
        this.brightnessPane.add(this.lblBrightnessValue, gbc_lblValue);

        //Nastaveni panelu pro kontrast
        GridBagLayout gbl_ContrastPane = new GridBagLayout();
        gbl_ContrastPane.columnWidths = new int[]{0, 0, 0};
        gbl_ContrastPane.rowHeights = new int[]{0};
        gbl_ContrastPane.columnWeights = new double[]{0.0, 1.0, 0.0};
        gbl_ContrastPane.rowWeights = new double[]{0.0};

        this.ContrastPane = new JPanel(gbl_ContrastPane);
        GridBagConstraints gbc_ContrastPane = new GridBagConstraints();
        gbc_ContrastPane.fill = GridBagConstraints.BOTH;
        gbc_ContrastPane.gridx = 0;
        gbc_ContrastPane.gridy = 2;
        add(this.ContrastPane, gbc_ContrastPane);

        this.lblContrast = new JLabel("Kontrast");
        GridBagConstraints gbc_lblContrast = new GridBagConstraints();
        gbc_lblContrast.insets = new Insets(0, 0, 0, 5);
        gbc_lblContrast.anchor = GridBagConstraints.WEST;
        gbc_lblContrast.gridx = 0;
        gbc_lblContrast.gridy = 0;
        this.ContrastPane.add(this.lblContrast, gbc_lblContrast);

        this.ContrastSlider = new JSlider();
        this.ContrastSlider.setPaintTicks(true);
        this.ContrastSlider.addChangeListener(controller);
        this.ContrastSlider.addMouseWheelListener(controller);
        this.ContrastSlider.setMinimum(0);
        this.ContrastSlider.setMaximum(100);
        this.ContrastSlider.setValue(30);
        GridBagConstraints gbc_contrastSlider = new GridBagConstraints();
        gbc_contrastSlider.fill = GridBagConstraints.HORIZONTAL;
        gbc_contrastSlider.insets = new Insets(0, 0, 0, 5);
        gbc_contrastSlider.gridx = 1;
        gbc_contrastSlider.gridy = 0;
        this.ContrastPane.add(this.ContrastSlider, gbc_contrastSlider);

        this.lblContrastValue = new JLabel(this.ContrastSlider.getValue() + "%");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.gridx = 2;
        gbc_lblNewLabel.gridy = 0;
        this.ContrastPane.add(this.lblContrastValue, gbc_lblNewLabel);
    }

    public JSlider getContrastSlider() {
        return ContrastSlider;
    }

    public JLabel getContrastLblValue() {
        return lblContrastValue;
    }

    public JLabel getBrightnessLblValue() {
        return lblBrightnessValue;
    }

    public JSlider getBrightnessSlider() {
        return BrightnessSlider;
    }
}
