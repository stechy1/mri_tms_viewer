package controller.rightPane;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import enums.Controllers;
import interfaces.IController;
import model.ImagePanelModel;
import view.MainWindow;
import view.rightPane.subPane.EnhanceImagePane;

public class EnhanceImagePaneController implements IController, ChangeListener, MouseWheelListener{

	private EnhanceImagePane view;

	public EnhanceImagePaneController(EnhanceImagePane view) {
		this.view = view;
	}

	@Override
	public void notifyController() {
		ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
		this.view.getBrightnessSlider().setValue(ipm.getBrightness());
		this.view.getBrightnessLblValue().setText(ipm.getBrightness() + "%");

		this.view.getContrastSlider().setValue(ipm.getContrast());
		this.view.getContrastLblValue().setText(ipm.getContrast() + "%");
	}

	@Override
	public void notifyAllControllers() {
		// TODO Auto-generated method stub

	}

	@Override
	public Controllers getType() {
		return Controllers.ENHANCE_IMAGE_PANE_CTRL;
	}

	@Override
	public Object getView() {
		return this.view;
	}

	@Override
	public Object getModel() {
		ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
		if(ipm != null){
			return ipm.getMriDicom().get(ipm.getActualSnapshot());
		}
		return null;
	}

	@Deprecated
	@Override
	public void setModel(Object model) {

	}



	@Override
	public void stateChanged(ChangeEvent e) {

		JSlider source = (JSlider) e.getSource();

		if (source.getValueIsAdjusting()) {
			ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();

			if(source == this.view.getBrightnessSlider()){
				ipm.setBrightness(this.view.getBrightnessSlider().getValue());
			}
			else if(source == this.view.getContrastSlider()){
				ipm.setContrast(this.view.getContrastSlider().getValue());
			}
		}    
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		JSlider source = (JSlider) e.getSource();
		int steps = e.getWheelRotation();
		ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
		
		if(source == this.view.getBrightnessSlider()){
			ipm.setBrightness(ipm.getBrightness() + steps);
		}
		else if(source == this.view.getContrastSlider()){
			ipm.setContrast(ipm.getContrast() + steps);
		}
		
	}

}
