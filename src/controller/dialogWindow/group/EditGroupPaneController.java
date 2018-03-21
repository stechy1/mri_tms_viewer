package controller.dialogWindow.group;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import enums.Controllers;
import interfaces.IController;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.dialogWindow.options.groups.sub.EditGroupPane;

public class EditGroupPaneController implements IController, ActionListener {

	private EditGroupPane view;
	private GroupModel model;
		
	public EditGroupPaneController(EditGroupPane view, GroupModel model) {
		this.view = view;
		this.model = model;
		notifyController();
	}
	
	

	@Override
	public void notifyController() {
		if(this.model != null){
			this.view.getTfTitle().setText(this.model.getName());
			this.view.getBtnChangeColor().setForeground(this.model.getColor());
		}
		this.view.getComponentPane().revalidate();
		this.view.getComponentPane().updateUI();
	}

	@Override
	public void notifyAllControllers() {
	}

	@Override
	public Controllers getType() {
		return null;
	}

	@Override
	public Object getView() {
		return this.view;
	}

	@Override
	public Object getModel() {
		return this.model;
	}

	@Override
	public void setModel(Object model) {
		this.model = (GroupModel) model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "changeColor":
			Color newColor = JColorChooser.showDialog((MainWindow) MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView(), 
					"Vyberte barvu skupiny", this.model.getColor());
			if(newColor != null){
				this.model.setColor(newColor);
			}
			notifyController();
			break;
		case "save":
			if(this.model != null && this.view != null){
				this.model.setName(this.view.getTfTitle().getText());
			}
			this.view.dispose();
			break;
		}
	}
}
