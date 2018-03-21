package controller.dialogWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.UtilityClass;
import enums.Controllers;
import exceptions.NotYetImplementedException;
import interfaces.IController;
import model.ImagePanelModel;
import view.MainWindow;
import view.dialogWindow.ShowDicomTags;

public class ShowDicomTagsController implements IController, ActionListener {

	private ShowDicomTags view;
	
	public ShowDicomTagsController(ShowDicomTags view) {
		this.view = view;
	}
	
	
	@Override
	public void notifyController() {
		
	}

	@Override
	public void notifyAllControllers() {
		
	}

	@Override
	public Controllers getType() {
		return Controllers.SHOW_DICOM_TAGS_CTRL;
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
		case "ok":
			((MainWindow) MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView()).setEnabled(true);
			this.view.dispose();
			break;
		default:
			try {
				throw new NotYetImplementedException("nenadefinovana akce: " + e.getActionCommand());
			} catch (NotYetImplementedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		}
	}
	
	

}
