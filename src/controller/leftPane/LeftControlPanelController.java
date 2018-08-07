package controller.leftPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

import controller.UtilityClass;
import model.dialogWindow.LoaderTask;
import controller.centerPane.ImagePaneController;
import enums.Controllers;
import exceptions.NotYetImplementedException;
import ij.util.DicomTools;
import interfaces.IController;
import model.ImagePanelModel;
import view.MainWindow;
import view.centerPane.ImagePanel;
import view.dialogWindow.OptionsWindow;
import view.dialogWindow.ShowDicomTags;
import view.leftPane.LeftControlPanel;
import view.dialogWindow.MyLoader;

public class LeftControlPanelController implements IController, ActionListener {

	
	private LeftControlPanel view;
	
	public LeftControlPanelController(LeftControlPanel view) {
		this.view = view;
	}
	
	
	@Override
	public Object getView() {
		return this.view;
	}

	@Override
	public Object getModel() {
		return MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
	}

	
	@Override
	public void setModel(Object model) {
		MainWindow.getController(Controllers.IMAGE_PANE_CTRL).setModel(model);
	}
	
	
	@Override
	public void notifyController() {
		ImagePanelModel ipm = (ImagePanelModel)MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
		boolean enabled = ipm.getActualSnapshot()>=0;
		this.view.getBtnSaveCurrentSnapshot().setEnabled(enabled);		
		this.view.getBtnSaveAllSnapshots().setEnabled(enabled);		
		this.view.getBtnSnapshotInfo().setEnabled(enabled);		
		enabled = ipm.getTmsDicom().size()>0;
		this.view.getBtnSaveGroup().setEnabled(enabled);		
	}

	@Override
	public void notifyAllControllers() {		
	}

	@Override
	public Controllers getType() {
		return Controllers.LEFT_CONTROL_PANE_CTRL;
	}	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand()) {
		case "loadMri":
			loadMri();
			break;
		case "loadTms":
			loadTms();
			break;
		case "snapshotInfo":
			if(checkIfExistData()){
				new ShowDicomTags();
			}
			break;
		case "settings":
			new OptionsWindow();
			break;
		case "saveCurrent":
			if(checkIfExistData()){
				try {
					ImagePaneController ipc = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
					ipc.getView().saveImg();
					UtilityClass.showInfoNotification("Obrazek ulozen");
				} catch (FileNotFoundException e) {
					UtilityClass.showInfoNotification("Obrázek nelze uložit, protože nejsou načtena žádná data z TMS");
					//e.printStackTrace();
				} catch (Exception e) {
					UtilityClass.showInfoNotification("Obrazek nebyl ulozen");
				}
			}
			break;
		case "saveAll":
			if(checkIfExistData()){
				
					ImagePaneController ipc = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
					try{
						ipc.getView().saveImages();
						UtilityClass.showInfoNotification("Obrazek ulozen");
					}catch(Exception e){
						UtilityClass.showInfoNotification("Obrázky nebyly uloženy");
						//e.printStackTrace();
					}
				
			}
			break;
		case "saveGroup":
			ImagePaneController ipc = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
			if(ipc != null){
				ipc.exportGroupsToFile();
			}
			break;
		default:
			try {
				throw new NotYetImplementedException("akce: " + ae.getActionCommand() + " neni dosud definovana");
			} catch (NotYetImplementedException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	private boolean checkIfExistData(){
		ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
		
		if(ipm.getActualSnapshot() == -1){
			UtilityClass.showAlertNotification("Nelze provést požadovanou operaci, protože nejsou načtena žádná data!", "Chyba");
			return false;
		}
		else
			return true;
	}
	
	private void loadMri() {
		File file = chooseDirectory("Vyberte MRI adresar");
		if(file != null){
			ImagePaneController ctrl = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
			if(ctrl != null){
				LoaderTask lt = new LoaderTask(file,ctrl.getModel(),true);
				MyLoader ml = new MyLoader();
				lt.linkToProgressBar(ml.getProgressBar());
				lt.linkToJFrame(ml);
				lt.execute();
				//imageCtrl.loadMriFiles(file);
			}
		}	
	}
	
	private void loadTms() {
		File file = chooseDirectory("Vyberte TMS adresar");
		if(file != null){
			ImagePaneController ctrl = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
			if(ctrl != null){
				LoaderTask lt = new LoaderTask(file,ctrl.getModel(),false);
				MyLoader ml = new MyLoader();
				lt.linkToProgressBar(ml.getProgressBar());
				lt.linkToJFrame(ml);
				lt.execute();
				//ctrl.loadTmsFiles(file);
			}
		}
	}
	
	private File chooseDirectory(String title){
		//TODO mozna poupravit nacitani souboru, aby to bralo jen dicomy
		JFileChooser chooser = new JFileChooser(".");
		chooser.setDialogTitle(title);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			File f = chooser.getSelectedFile();
			return f;
		}
		return null;
	}
}
