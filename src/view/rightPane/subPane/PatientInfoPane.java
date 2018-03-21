package view.rightPane.subPane;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Configuration;
import controller.rightPane.PatientInfoPaneController;
import view.MainWindow;

public class PatientInfoPane extends JPanel {

    private JLabel lblInformaceOPacientovi;
    private JPanel infoPanel;
    private JLabel lblName;
    private JTextField tfName;
    private JLabel lblDateMri;
    private JTextField tfDateMri;
    private JLabel lblDateTMS;
    private JTextField tfDateTMS;

    /**
     * Create the panel.
     */
    public PatientInfoPane() {

        initComponents();
    }

    private void initComponents() {

        PatientInfoPaneController controller = new PatientInfoPaneController(this);
        MainWindow.addController(controller);

        setBorder(new EmptyBorder(5, 5, 5, 5));

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        this.lblInformaceOPacientovi = new JLabel("Informace o pacientovi");
        GridBagConstraints gbc_lblInformaceOPacientovi = new GridBagConstraints();
        gbc_lblInformaceOPacientovi.anchor = GridBagConstraints.WEST;
        gbc_lblInformaceOPacientovi.insets = new Insets(0, 0, 5, 0);
        gbc_lblInformaceOPacientovi.gridx = 0;
        gbc_lblInformaceOPacientovi.gridy = 0;
        add(this.lblInformaceOPacientovi, gbc_lblInformaceOPacientovi);

        this.infoPanel = new JPanel();
        GridBagConstraints gbc_infoPanel = new GridBagConstraints();
        gbc_infoPanel.fill = GridBagConstraints.BOTH;
        gbc_infoPanel.gridx = 0;
        gbc_infoPanel.gridy = 1;
        add(this.infoPanel, gbc_infoPanel);
        GridBagLayout gbl_infoPanel = new GridBagLayout();
        gbl_infoPanel.columnWidths = new int[]{0, 0, 0};
        gbl_infoPanel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_infoPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_infoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        this.infoPanel.setLayout(gbl_infoPanel);

        this.lblName = new JLabel("Jm\u00E9no");
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.insets = new Insets(0, 0, 5, 5);
        gbc_lblName.anchor = GridBagConstraints.WEST;
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 0;
        this.infoPanel.add(this.lblName, gbc_lblName);

        this.tfName = new JTextField();
        this.tfName.setEditable(false);
        GridBagConstraints gbc_tfName = new GridBagConstraints();
        gbc_tfName.insets = new Insets(0, 0, 5, 0);
        gbc_tfName.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfName.gridx = 1;
        gbc_tfName.gridy = 0;
        this.infoPanel.add(this.tfName, gbc_tfName);
        this.tfName.setColumns(10);

        this.lblDateMri = new JLabel("Datum m\u011B\u0159en\u00ED MRI");
        GridBagConstraints gbc_lblDateMri = new GridBagConstraints();
        gbc_lblDateMri.anchor = GridBagConstraints.WEST;
        gbc_lblDateMri.insets = new Insets(0, 0, 5, 5);
        gbc_lblDateMri.gridx = 0;
        gbc_lblDateMri.gridy = 1;
        this.infoPanel.add(this.lblDateMri, gbc_lblDateMri);

        this.tfDateMri = new JTextField();
        this.tfDateMri.setEditable(false);
        GridBagConstraints gbc_tfDateMri = new GridBagConstraints();
        gbc_tfDateMri.insets = new Insets(0, 0, 5, 0);
        gbc_tfDateMri.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfDateMri.gridx = 1;
        gbc_tfDateMri.gridy = 1;
        this.infoPanel.add(this.tfDateMri, gbc_tfDateMri);
        this.tfDateMri.setColumns(10);

        this.lblDateTMS = new JLabel("Datum m\u011B\u0159en\u00ED TMS");
        GridBagConstraints gbc_lblDateTMS = new GridBagConstraints();
        gbc_lblDateTMS.anchor = GridBagConstraints.EAST;
        gbc_lblDateTMS.insets = new Insets(0, 0, 0, 5);
        gbc_lblDateTMS.gridx = 0;
        gbc_lblDateTMS.gridy = 2;
        this.infoPanel.add(this.lblDateTMS, gbc_lblDateTMS);

        this.tfDateTMS = new JTextField();
        this.tfDateTMS.setEditable(false);
        GridBagConstraints gbc_tfDateTMS = new GridBagConstraints();
        gbc_tfDateTMS.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfDateTMS.gridx = 1;
        gbc_tfDateTMS.gridy = 2;
        this.infoPanel.add(this.tfDateTMS, gbc_tfDateTMS);
        this.tfDateTMS.setColumns(10);
    }

    public JTextField getTfDateMri() {
        return tfDateMri;
    }

    public JTextField getTfDateTMS() {
        return tfDateTMS;
    }

    public JTextField getTfName() {
        return tfName;
    }
}
