package view.leftPane;
import javax.swing.JPanel;

import controller.leftPane.LeftControlPanelController;
import view.MainWindow;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class LeftControlPanel extends JPanel {
	private JButton btnLoadMri;
	private JButton btnLoadTms;
	private JButton btnSnapshotInfo;
	private JButton btnSaveCurrentSnapshot;
	private JButton btnSaveAllSnapshots;
	private JButton btnSettings;
	private JButton btnSaveGroup;

	/**
	 * Create the panel.
	 */
	public LeftControlPanel() {
		initComponents();
	}
	
	private void initComponents() {
	
		LeftControlPanelController controller = new LeftControlPanelController(this);
		MainWindow.addController(controller);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		this.btnLoadMri = new JButton("Nacti MRI");
		this.btnLoadMri.setActionCommand("loadMri");
		this.btnLoadMri.addActionListener(controller);
		GridBagConstraints gbc_btnLoadMri = new GridBagConstraints();
		gbc_btnLoadMri.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoadMri.insets = new Insets(0, 0, 5, 0);
		gbc_btnLoadMri.gridx = 0;
		gbc_btnLoadMri.gridy = 0;
		add(this.btnLoadMri, gbc_btnLoadMri);
		
		this.btnLoadTms = new JButton("Nacti TMS");
		this.btnLoadTms.setActionCommand("loadTms");
		this.btnLoadTms.addActionListener(controller);
		GridBagConstraints gbc_btnLoadTms = new GridBagConstraints();
		gbc_btnLoadTms.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoadTms.insets = new Insets(0, 0, 5, 0);
		gbc_btnLoadTms.gridx = 0;
		gbc_btnLoadTms.gridy = 1;
		add(this.btnLoadTms, gbc_btnLoadTms);
		
		this.btnSnapshotInfo = new JButton("Informace o snimku");
		this.btnSnapshotInfo.setActionCommand("snapshotInfo");
		this.btnSnapshotInfo.addActionListener(controller);
		this.btnSnapshotInfo.setEnabled(false);
		GridBagConstraints gbc_btnSnapshotInfo = new GridBagConstraints();
		gbc_btnSnapshotInfo.insets = new Insets(0, 0, 5, 0);
		gbc_btnSnapshotInfo.fill = GridBagConstraints.BOTH;
		gbc_btnSnapshotInfo.gridx = 0;
		gbc_btnSnapshotInfo.gridy = 2;
		add(this.btnSnapshotInfo, gbc_btnSnapshotInfo);
		
		this.btnSaveCurrentSnapshot = new JButton("Uložit aktuální obrázek");
		this.btnSaveCurrentSnapshot.setActionCommand("saveCurrent");
		this.btnSaveCurrentSnapshot.addActionListener(controller);
		this.btnSaveCurrentSnapshot.setEnabled(false);
		GridBagConstraints gbc_btnSaveCurrentSnapshot = new GridBagConstraints();
		gbc_btnSaveCurrentSnapshot.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveCurrentSnapshot.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSaveCurrentSnapshot.gridx = 0;
		gbc_btnSaveCurrentSnapshot.gridy = 3;
		add(this.btnSaveCurrentSnapshot, gbc_btnSaveCurrentSnapshot);
		
		this.btnSaveAllSnapshots = new JButton("Uložit všechny obrázky");
		this.btnSaveAllSnapshots.setActionCommand("saveAll");
		this.btnSaveAllSnapshots.addActionListener(controller);
		this.btnSaveAllSnapshots.setEnabled(false);
		GridBagConstraints gbc_btnSaveAllSnapshots = new GridBagConstraints();
		gbc_btnSaveAllSnapshots.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveAllSnapshots.gridx = 0;
		gbc_btnSaveAllSnapshots.gridy = 4;
		add(this.btnSaveAllSnapshots, gbc_btnSaveAllSnapshots);
		
		this.btnSaveGroup = new JButton("Ulož skupiny");
		this.btnSaveGroup.setActionCommand("saveGroup");
		this.btnSaveGroup.addActionListener(controller);
		this.btnSaveGroup.setEnabled(false);
		GridBagConstraints gbc_btnSaveGroup = new GridBagConstraints();
		gbc_btnSaveGroup.fill = GridBagConstraints.BOTH;
		gbc_btnSaveGroup.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveGroup.gridx = 0;
		gbc_btnSaveGroup.gridy = 5;
		add(this.btnSaveGroup, gbc_btnSaveGroup);
		
		this.btnSettings = new JButton("Nastavení");
		this.btnSettings.setActionCommand("settings");
		this.btnSettings.addActionListener(controller);
		
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.anchor = GridBagConstraints.SOUTH;
		gbc_btnSettings.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSettings.gridx = 0;
		gbc_btnSettings.gridy = 6;
		add(this.btnSettings, gbc_btnSettings);
	}

	public JButton getBtnSaveCurrentSnapshot(){
		return this.btnSaveCurrentSnapshot;
	}

	public JButton getBtnSaveAllSnapshots(){
		return this.btnSaveAllSnapshots;
	}

	public JButton getBtnSnapshotInfo(){
		return this.btnSnapshotInfo;
	}

	public JButton getBtnSaveGroup(){
		return this.btnSaveGroup;
	}

}
