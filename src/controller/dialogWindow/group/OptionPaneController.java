package controller.dialogWindow.group;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.UtilityClass;
import controller.Configuration;
import controller.centerPane.ImagePaneController;
import enums.Controllers;
import exceptions.NotYetImplementedException;
import interfaces.IController;
import model.ImagePanelModel;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.dialogWindow.OptionsWindow;
import view.dialogWindow.options.groups.OptionPane;
import view.dialogWindow.options.groups.sub.EditGroupPane;

public class OptionPaneController implements IController, ActionListener {

	private OptionPane view;
	
	public OptionPaneController(OptionPane view) {
		this.view = view;
	}
	
	@Override
	public void notifyController() {
		MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
		this.view.revalidate();
		this.view.updateUI();
	}

	@Override
	public void notifyAllControllers() {}

	@Override
	public Controllers getType() {
		return Controllers.GROUPS_OPTION_PANE_CTRL;
	}

	@Override
	public Object getView() {
		return this.view;
	}

	@Override
	public Object getModel() {return null;}

	@Override
	public void setModel(Object model) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.view.getRulers()){
			Configuration.drawRulers = this.view.getRulers().isSelected();
		}else if(e.getSource() == this.view.getCoords()){
			Configuration.showCoords = this.view.getCoords().isSelected();
		}else if(e.getSource() == this.view.getThreshold()){
			Configuration.threshold = this.view.getThreshold().isSelected();
		}
		notifyController();
	}

}
