package controller.dialogWindow.group;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import controller.UtilityClass;
import controller.centerPane.ImagePaneController;
import enums.Controllers;
import exceptions.NotYetImplementedException;
import interfaces.IController;
import model.ImagePanelModel;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.dialogWindow.OptionsWindow;
import view.dialogWindow.options.groups.GroupsOptionPane;
import view.dialogWindow.options.groups.sub.EditGroupPane;

public class GroupsOptionPaneController implements IController, ActionListener {

	private GroupsOptionPane view;
	
	public GroupsOptionPaneController(GroupsOptionPane view) {
		this.view = view;
	}
	
	@Override
	public void notifyController() {
		MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
		this.view.getGroupsPane().initComponents();
		this.view.revalidate();
		this.view.updateUI();
	}

	@Override
	public void notifyAllControllers() {
		// TODO Auto-generated method stub

	}

	@Override
	public Controllers getType() {
		return Controllers.GROUPS_OPTION_PANE_CTRL;
	}

	@Override
	public Object getView() {
		return this.view;
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setModel(Object model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "add":
			add();
			break;
		case "create":
			create();
			notifyController();
			break;
		case "assign":
			assign();
			notifyController();
			break;
		default: 
			try {
				throw new NotYetImplementedException("akce nebyla implementovana: " + e.getActionCommand());
			} catch (NotYetImplementedException e1) {
				System.err.println(e1.getMessage());
				e1.printStackTrace();
			}
			break;
		}
	}

	private void assign() {
		// TODO Auto-generated method stub
		
	}

	private void add() {
		try {
			addGroup();
		} catch (Exception e1) {
			UtilityClass.showAlertNotification(e1.getMessage());
		}
	}

	private void create() {
		ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
		
		if(ipm.getGroups() != null){
			if(ipm.getGroups().size() != 0){
				String count_ret = JOptionPane.showInputDialog((MainWindow) MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView(), 
						"Počet skupin");
				int count = -1;
				try {
					count = Integer.parseInt(count_ret);
				} catch (NumberFormatException e2) {
					UtilityClass.showAlertNotification("Musíte zadat celočíselnou hodnotu!");
					return;
				}
				if(count > 0){
					if(ipm != null){
						ipm.createGroups(count);
					}
				}
			}
			else
				UtilityClass.showAlertNotification("Nelze vytvořit skupiny, protože nejsou načteny žádné body.");
		}
		else
			UtilityClass.showAlertNotification("Nelze vytvořit skupiny, protože nejsou načteny žádné body.");
	}

	private void addGroup() throws Exception {
		ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
		if(ipm.getMriDicom() != null){
			if(ipm.getMriDicom().size() != 0){
				GroupModel newGroup = new GroupModel("skupina " + ipm.getGroups().size()); 
				ipm.getGroups().add(newGroup);
				new EditGroupPane(newGroup);
			}
			else{
				throw new Exception("Nelze přidávat skupiny dokud není vybrán zdroj MRI smínků");
			}
		}
		else{
			throw new Exception("Nelze přidávat skupiny dokud není vybrán zdroj MRI smínků");
		}
	}

}
