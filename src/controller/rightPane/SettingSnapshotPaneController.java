package controller.rightPane;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.util.Map;

import enums.Controllers;
import interfaces.IController;
import model.ImagePanelModel;
import model.MyPoint;
import model.Response;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.rightPane.subPane.SettingSnapshotPane;

public class SettingSnapshotPaneController implements IController, ItemListener, FocusListener{

	private SettingSnapshotPane view;
	private MyPoint<Response> model;
	
	public SettingSnapshotPaneController(SettingSnapshotPane view) {
		this.view = view;
	}
	
	@Override
	public void notifyController() {
		ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
		if(this.model != null){
			this.view.getCbGroup().setModel(new DefaultComboBoxModel<>(ipm.getGroups().toArray(new GroupModel[ipm.getGroups().size()])));
			this.view.getCbGroup().setSelectedItem(this.model.getGroup());
			this.view.getValues().removeAll();
			for(Map.Entry<String,Double> entry: this.model.getPixelValue().getData().entrySet()){
				this.view.getValues().add(new JLabel(entry.getKey()));
				JTextField jtf = new JTextField(entry.getValue().toString());
				this.view.getValues().add(jtf);
				jtf.addKeyListener(new KeyAdapter(){
					public void keyReleased(KeyEvent e){
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							try{
								model.getPixelValue().getData().put(entry.getKey(),Double.valueOf(jtf.getText()));
							}catch(Exception ex){
								//TODO dodelat dialog
								ex.printStackTrace();
							}
						}
					}
				});
			}
		}else{
			this.view.getCbGroup().setModel(new DefaultComboBoxModel<>());
			this.view.getValues().removeAll();
		}
	}

	@Override
	public void notifyAllControllers() {

	}

	@Override
	public Controllers getType() {
		return Controllers.SETTING_SNAPSHOT_PANE_CTRL;
	}

	@Override
	public Object getView() {
		return null;
	}

	@Override
	public Object getModel() {
		return this.model;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setModel(Object model) {
		this.model = (MyPoint<Response>) model;
		notifyController();
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
}
