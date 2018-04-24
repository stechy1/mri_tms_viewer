package view.rightPane.subPane;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Configuration;
import controller.rightPane.SettingSnapshotPaneController;
import view.MainWindow;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.dialogWindow.group.GroupModel;

public class SettingSnapshotPane extends JPanel {
	private JLabel lblActivePoint;
	private JPanel controlPanel;
	private JLabel lblGroup;
	private JComboBox<GroupModel> cbGroup;
	private JLabel lblAmplitude;
	private JTextField tfAmplitude;
	private JLabel lblLatency;
	private JTextField tfLatency;

	/**
	 * Create the panel.
	 */
	public SettingSnapshotPane() {

		initComponents();
	}
	private void initComponents() {
		
		SettingSnapshotPaneController controller = new SettingSnapshotPaneController(this);
		MainWindow.addController(controller);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		this.lblActivePoint = new JLabel("Aktivní bod");
		GridBagConstraints gbc_lblActivePoint = new GridBagConstraints();
		gbc_lblActivePoint.anchor = GridBagConstraints.WEST;
		gbc_lblActivePoint.insets = new Insets(0, 0, 5, 0);
		gbc_lblActivePoint.gridx = 0;
		gbc_lblActivePoint.gridy = 0;
		add(this.lblActivePoint, gbc_lblActivePoint);
		
		this.controlPanel = new JPanel();
		GridBagConstraints gbc_controlPanel = new GridBagConstraints();
		gbc_controlPanel.fill = GridBagConstraints.BOTH;
		gbc_controlPanel.gridx = 0;
		gbc_controlPanel.gridy = 1;
		add(this.controlPanel, gbc_controlPanel);
		GridBagLayout gbl_controlPanel = new GridBagLayout();
		gbl_controlPanel.columnWidths = new int[]{0, 0, 0};
		gbl_controlPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_controlPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_controlPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		this.controlPanel.setLayout(gbl_controlPanel);
		
		this.lblGroup = new JLabel("Skupina");
		GridBagConstraints gbc_lblGroup = new GridBagConstraints();
		gbc_lblGroup.insets = new Insets(0, 0, 5, 5);
		gbc_lblGroup.anchor = GridBagConstraints.EAST;
		gbc_lblGroup.gridx = 0;
		gbc_lblGroup.gridy = 0;
		this.controlPanel.add(this.lblGroup, gbc_lblGroup);
		
		this.cbGroup = new JComboBox<GroupModel>();
		this.cbGroup.addItemListener(controller);
		GridBagConstraints gbc_cbGroup = new GridBagConstraints();
		gbc_cbGroup.insets = new Insets(0, 0, 5, 0);
		gbc_cbGroup.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbGroup.gridx = 1;
		gbc_cbGroup.gridy = 0;
		this.controlPanel.add(this.cbGroup, gbc_cbGroup);
		
		this.lblAmplitude = new JLabel("Amplituda");
		GridBagConstraints gbc_lblAmplitude = new GridBagConstraints();
		gbc_lblAmplitude.anchor = GridBagConstraints.EAST;
		gbc_lblAmplitude.insets = new Insets(0, 0, 5, 5);
		gbc_lblAmplitude.gridx = 0;
		gbc_lblAmplitude.gridy = 1;
		this.controlPanel.add(this.lblAmplitude, gbc_lblAmplitude);
		
		this.tfAmplitude = new JTextField();
		this.tfAmplitude.addKeyListener(controller);
		
		GridBagConstraints gbc_tfAmplitude = new GridBagConstraints();
		gbc_tfAmplitude.insets = new Insets(0, 0, 5, 0);
		gbc_tfAmplitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfAmplitude.gridx = 1;
		gbc_tfAmplitude.gridy = 1;
		this.controlPanel.add(this.tfAmplitude, gbc_tfAmplitude);
		this.tfAmplitude.setColumns(10);
		
		this.lblLatency = new JLabel("Latence");
		GridBagConstraints gbc_lblLatency = new GridBagConstraints();
		gbc_lblLatency.anchor = GridBagConstraints.EAST;
		gbc_lblLatency.insets = new Insets(0, 0, 0, 5);
		gbc_lblLatency.gridx = 0;
		gbc_lblLatency.gridy = 2;
		this.controlPanel.add(this.lblLatency, gbc_lblLatency);
		
		this.tfLatency = new JTextField();
		this.tfLatency.addKeyListener(controller);
		
		GridBagConstraints gbc_tfLatency = new GridBagConstraints();
		gbc_tfLatency.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfLatency.gridx = 1;
		gbc_tfLatency.gridy = 2;
		this.controlPanel.add(this.tfLatency, gbc_tfLatency);
		this.tfLatency.setColumns(10);
	}

	public JComboBox<GroupModel> getCbGroup() {
		return cbGroup;
	}
	
	public JTextField getTfAmplitude() {
		return tfAmplitude;
	}
	
	public JTextField getTfLatency() {
		return tfLatency;
	}
}
