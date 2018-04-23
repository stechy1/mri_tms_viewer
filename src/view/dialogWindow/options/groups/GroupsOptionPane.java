package view.dialogWindow.options.groups;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.dialogWindow.group.GroupsOptionPaneController;
import view.MainWindow;
import view.dialogWindow.options.groups.sub.GroupsPane;

public class GroupsOptionPane extends JPanel {
	
	private GroupsPane groupsPane;

	private JPanel controlPane;
	
//	private JButton btnAdd;
	private JButton btnCreate;
//	private JButton btnAssignPoints;

	/**
	 * Create the panel.
	 */
	public GroupsOptionPane() {

		initComponents();
	}
	private void initComponents() {
		setLayout(new BorderLayout(0, 0));
		
		GroupsOptionPaneController controller = new GroupsOptionPaneController(this);
		MainWindow.addController(controller);
		
		this.groupsPane = new GroupsPane();
		add(this.groupsPane, BorderLayout.CENTER);
		
		this.controlPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) this.controlPane.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(this.controlPane, BorderLayout.SOUTH);
		
//		this.btnAdd = new JButton("Přidej skupinu");
//		this.btnAdd.setActionCommand("add");
//		this.btnAdd.addActionListener(controller);
		
		this.btnCreate = new JButton("Vygeneruj skupiny");
		this.btnCreate.setActionCommand("create");
		this.btnCreate.addActionListener(controller);
		
//		this.btnAssignPoints = new JButton("Přiřaď body ke skupinám");
//		this.btnAssignPoints.setActionCommand("assign");
//		this.btnAssignPoints.addActionListener(controller);
		
		this.controlPane.add(this.btnCreate);
//		this.controlPane.add(this.btnAssignPoints);
//		this.controlPane.add(this.btnAdd);
	}
	
	public GroupsPane getGroupsPane() {
		return groupsPane;
	}
}
