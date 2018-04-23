package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import enums.Controllers;
import exceptions.NotYetImplementedException;
import interfaces.IController;
import view.MainWindow;

public class MainWindowController implements IController, WindowListener {

	
	private MainWindow view;
	
	public MainWindowController(MainWindow view) {
		this.view = view;
	}
	
	/**
	 * Metoda, ktera cely program ukonci
	 */
	public void exitProgram(){
		int retVal = JOptionPane.showConfirmDialog(this.view, "Opravdu chcete ukoncit tento program", "Uzavrit aplikaci", JOptionPane.OK_CANCEL_OPTION);
		if(retVal == JOptionPane.OK_OPTION){
			System.exit(0);
		}
	}
	
	@Override
	public void notifyController() {
		try {
			throw new NotYetImplementedException();
		} catch (NotYetImplementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void notifyAllControllers() {
		try {
			throw new NotYetImplementedException();
		} catch (NotYetImplementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Controllers getType() {
		return Controllers.MAIN_WINDOW_CTRL;
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
	public void windowClosing(WindowEvent arg0) {
		exitProgram();
	}
	
	
	@Override public void windowActivated(WindowEvent arg0) {}
	@Override public void windowClosed(WindowEvent arg0) {}
	@Override public void windowDeactivated(WindowEvent arg0) {}
	@Override public void windowDeiconified(WindowEvent arg0) {}
	@Override public void windowIconified(WindowEvent arg0) {}
	@Override public void windowOpened(WindowEvent arg0) {}
}
