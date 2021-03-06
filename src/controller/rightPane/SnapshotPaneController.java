package controller.rightPane;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.centerPane.ImagePaneController;
import enums.Controllers;
import interfaces.IController;
import model.ImagePanelModel;
import view.MainWindow;
import view.rightPane.subPane.SnapshotPane;

public class SnapshotPaneController implements IController, ChangeListener, MouseWheelListener{

	private SnapshotPane view;
	
	
	public SnapshotPaneController(SnapshotPane view) {
		this.view = view;
	}
	
	public SnapshotPane getView() {
		return view;
	}
	
	@Override
	public void notifyController() {
		ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
		this.view.getSlider().setValue(ipm.getActualSnapshot());
		this.view.getSlider().setMaximum(ipm.getNumberOfImages());
		if(ipm.getActualSnapshot()>=0){
			this.view.getLblValue().setText(ipm.getZAxis()+": "+ipm.getActualSnapshot() + " (" + 
				String.format("%.2f",ipm.getActualSnapshot()*ipm.getZSpacing())+ "mm)");
		}else{
			this.view.getLblValue().setText(ipm.getZAxis()+": 0 (0mm)");
		}
	}

	@Override
	public Controllers getType() {
		return Controllers.SNAPSHOT_PANE_CTRL;
	}

	

	@Override
	public void notifyAllControllers() {
		
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
	public void stateChanged(ChangeEvent e) {
		
		JSlider source = (JSlider) e.getSource();
		
		if (source.getValueIsAdjusting()) {
			ImagePaneController ctrl = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
			ctrl.getModel().setActualSnapshot(this.view.getSlider().getValue());
		}    
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		JSlider source = (JSlider) e.getSource();
		ImagePaneController ctrl = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
		int steps = e.getWheelRotation();
		ImagePanelModel ipm = ctrl.getModel();
		ipm.setActualSnapshot(ipm.getActualSnapshot() + steps);
	}

}
