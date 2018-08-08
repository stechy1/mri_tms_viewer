package view.dialogWindow.options.groups.sub;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Configuration;
import controller.UtilityClass;
import controller.centerPane.ImagePaneController;
import enums.Controllers;
import model.ImagePanelModel;
import model.MyResponsePoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupItemPane extends JPanel implements ActionListener{

	private JTextField tfGroup;
	private JButton btnEdit;
//	private JButton btnDelete;
	
	private GroupModel model;
	
	
	public GroupItemPane(GroupModel model) {
		this.model = model;
		initComponents();
	}
	
	private void initComponents() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		this.tfGroup = new JTextField(this.model.getName());
		this.tfGroup.setBackground(this.model.getLayerColor());
		this.tfGroup.setEditable(false);
		GridBagConstraints gbc_tfGroup = new GridBagConstraints();
		gbc_tfGroup.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfGroup.insets = new Insets(0, 0, 0, 5);
		gbc_tfGroup.gridx = 0;
		gbc_tfGroup.gridy = 0;
		add(this.tfGroup, gbc_tfGroup);
		
		this.btnEdit = new JButton("Upravit");
		this.btnEdit.setEnabled(!this.model.getName().equals("ignorovan\u00e9 body")
			&& !this.model.getName().equals("nep\u0159i\u0159azen\u00e9 body"));
		this.btnEdit.setActionCommand("edit");
		this.btnEdit.addActionListener(this);
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(0, 0, 0, 5);
		gbc_btnEdit.gridx = 1;
		gbc_btnEdit.gridy = 0;
		add(this.btnEdit, gbc_btnEdit);
		
//		this.btnDelete = new JButton("Smazat");
//		this.btnDelete.setActionCommand("delete");
//		this.btnDelete.addActionListener(this);
//		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
//		gbc_btnDelete.gridx = 2;
//		gbc_btnDelete.gridy = 0;
//		add(this.btnDelete, gbc_btnDelete);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "edit":
			new EditGroupPane(this.model);
			break;
		case "delete":
			deleteGroup(this.model);
			UtilityClass.showInfoNotification("Skupina byla smazána");
			break;
		}
	}

	private void deleteGroup(GroupModel model) {
		ImagePaneController ipc = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
		GroupModel unassign = ipc.getModel().getGroup(Configuration.UNASSIGN_GROUP);
		for (MyResponsePoint point : model.getPoints()) {
			point.setGroup(unassign);
			unassign.getPoints().add(point);
		}
		
		ipc.getModel().getGroups().remove(model);
	}
}
