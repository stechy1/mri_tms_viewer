package controller.centerPane;

import controller.Configuration;
import controller.UtilityClass;
import controller.rightPane.SettingSnapshotPaneController;
import enums.Controllers;
import interfaces.IController;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import model.ImagePanelModel;
import model.MyResponsePoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.centerPane.ImagePanel;
import static controller.Configuration.SELECTION_TRESSHOLD;

public class ImagePaneController implements IController, MouseWheelListener, MouseListener, MouseMotionListener{


	private ImagePanel view;
	private ImagePanelModel model; 	

	private MyResponsePoint active;

	public ImagePaneController(ImagePanel view, ImagePanelModel model) {
		this.view = view;
		this.model = model;
	}

	public ImagePaneController(ImagePanel view) {
		this(view, null);
	}


	@Override
	public ImagePanelModel getModel() {
		return model;
	}

	@Override
	public ImagePanel getView() {
		return view;
	}

	@Override
	public void setModel(Object model) {
		this.model = (ImagePanelModel) model;
		notifyController();
	}

	/*----------------*
	 * Ostatni metody *
	 *----------------*/
	public void exportGroupsToFile(){
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		if(this.model != null){
			if(this.model.getGroups() != null){
				if(this.model.getGroups().size() != 0){
					try {
						fos = new FileOutputStream(ImagePanelModel.tmsPath + File.separator + Configuration.GROUPS);
						oos = new ObjectOutputStream(fos);
						oos.writeObject(this.model.getGroups());
						oos.flush();
						UtilityClass.showInfoNotification("Skupiny byly exportovány");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					finally {
						if(oos != null){
							try {
								oos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				else{
					UtilityClass.showInfoNotification("Skupiny pro export neexistují!");
				}
			}
			else{
				UtilityClass.showInfoNotification("Skupiny pro export neexistují!");
			}
		}
		else{
			UtilityClass.showInfoNotification("Skupiny pro export neexistují!");
		}
	}
	/*-----------------------*
	 * Implementovane metody *
	 *-----------------------*/
	@Override
	public void notifyController() {
		MainWindow.getController(Controllers.LEFT_CONTROL_PANE_CTRL).notifyController();
		MainWindow.getController(Controllers.SNAPSHOT_PANE_CTRL).notifyController();
		this.view.repaint();
	}

	@Override 
	public void notifyAllControllers() {

	}

	@Override
	public Controllers getType() {
		return Controllers.IMAGE_PANE_CTRL;
	}

	public void changeSide(int side){
		this.getModel().setType(side);
		notifyController();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int steps = e.getWheelRotation();
		steps = steps>=0?1:-1;
		if(e.isControlDown()){
			int types = this.getModel().getTypes();
			changeSide((this.getModel().getType()+steps+types)%types);
			notifyController();
		}else{
			int move = this.model.getActualSnapshot() + steps;
			this.model.setActualSnapshot(move<0?0:move);
		}
	}

	public boolean contains(MyResponsePoint point,double x,double y){
		double cx = point.getCenterX();
		double cy = point.getCenterY();
		double w2 = point.getWidth()/2;
		double h2 = point.getHeight()/2;
		return x<cx+w2 && x>cx-w2 && y<cy+h2 && y>cy-h2;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		//		System.out.println("click: " + this.view.getPaneInfo() + ";; X: " + (e.getX() - this.view.getX_offset()) + 
		//				", Y: " + (e.getY() - this.view.getY_offset()));
		if(this.getModel().getActualSnapshot() != -1){

			if(this.getModel().getGroups() != null){
				if(this.getModel().getGroups().size() != 0){

					for (GroupModel group : this.getModel().getGroups()) {
						for(MyResponsePoint point : group.getPointFromLayer(this.model.getActualSnapshot())){
							if(contains(point,(e.getX() - this.view.getX_offset())/this.view.getRatio(), 
									(this.view.getHeight() - e.getY() - this.view.getY_offset())/this.view.getRatio())){
								SettingSnapshotPaneController ctrl = (SettingSnapshotPaneController) MainWindow.getController(Controllers.SETTING_SNAPSHOT_PANE_CTRL);
								if(active!=null) {
									active.setActive(false);
								}
								if(point!=active){
									point.setActive(true);
									this.active=point;
									ctrl.setModel(point);
									this.getModel().remember(point);
								}else{
									this.active=null;
									ctrl.setModel(null);
								}
								this.view.repaint();
							}
						}
					}
				}
			}
		}
	}
	@Override
	public void mouseMoved(MouseEvent e){
		this.view.setCursorPosition(e.getX(),e.getY());
	}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseDragged(MouseEvent e) {}
}
