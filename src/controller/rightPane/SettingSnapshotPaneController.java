package controller.rightPane;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;

import enums.Controllers;
import interfaces.IController;
import model.ImagePanelModel;
import model.MyPoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.rightPane.subPane.SettingSnapshotPane;

public class SettingSnapshotPaneController implements IController, ItemListener, FocusListener, KeyListener {

	private SettingSnapshotPane view;
	private MyPoint model;
	
	public SettingSnapshotPaneController(SettingSnapshotPane view) {
		this.view = view;
	}
	
	@Override
	public void notifyController() {
		if(this.model != null){
			ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
			this.view.getCbGroup().setModel(new DefaultComboBoxModel<>(ipm.getGroups().toArray()));
			this.view.getCbGroup().setSelectedItem(this.model.getGroup());
			this.view.getTfAmplitude().setText(this.model.getAmplitude() + "");
			this.view.getTfLatency().setText(this.model.getLatency() + "");
		}
	}

	@Override
	public void notifyAllControllers() {
		// TODO Auto-generated method stub

	}

	@Override
	public Controllers getType() {
		return Controllers.SETTING_SNAPSHOT_PANE_CTRL;
	}

	@Override
	public Object getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getModel() {
		return this.model;
	}

	@Override
	public void setModel(Object model) {
		if(model != null){
			this.model = (MyPoint) model;
			notifyController();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if(e.getStateChange() == ItemEvent.DESELECTED){
			GroupModel group = (GroupModel) e.getItem();
			group.getPoints().remove(this.model);
		}
		if(e.getStateChange() == ItemEvent.SELECTED){
			GroupModel group = (GroupModel) e.getItem();
			this.model.setGroup(group);
			if(!group.getPoints().contains(this.model)){
				group.getPoints().add(this.model);
			}
		}
		MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void focusLost(FocusEvent fe) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		if(ke.getSource().equals(this.view.getTfAmplitude())) {
			if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
				try {
					double newAmplitude = Double.parseDouble(this.view.getTfAmplitude().getText().trim());
					this.model.setAmplitude(newAmplitude);
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
			}
		}
		if(ke.getSource().equals(this.view.getTfLatency())) {
			if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
				try {
					double newLatency = Double.parseDouble(this.view.getTfLatency().getText().trim());
					this.model.setLatency(newLatency);
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
