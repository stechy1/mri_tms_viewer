package view.rightPane.subPane;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
	private JPanel valuesPanel;

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
		
		this.valuesPanel = new JPanel();
		valuesPanel.setLayout(new GridLayout(0,2,2,2));
		GridBagConstraints gbc_valuesPanel = new GridBagConstraints();
		gbc_valuesPanel.anchor = GridBagConstraints.WEST;
		gbc_valuesPanel.insets = new Insets(0, 0, 5, 5);
		gbc_valuesPanel.gridx = 0;
		gbc_valuesPanel.gridy = 1;
		gbc_valuesPanel.gridwidth = 2;
		this.controlPanel.add(this.valuesPanel, gbc_valuesPanel);
	}

	public JPanel getValues() {
		return valuesPanel;
	}
	public JComboBox<GroupModel> getCbGroup() {
		return cbGroup;
	}
}
