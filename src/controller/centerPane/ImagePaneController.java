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
import java.util.List;
import java.util.ArrayList;
import model.ImagePanelModel;
import model.Response;
import model.MyResponsePoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.centerPane.ImagePanel;
import static controller.Configuration.SELECTION_TRESSHOLD;
import static model.ImagePanelModel.*;

public class ImagePaneController implements IController, MouseWheelListener, MouseListener, MouseMotionListener{

	private ImagePanel view;
	private ImagePanelModel model; 	

	private MyResponsePoint active;
	private List<MyResponsePoint> involved = new ArrayList<MyResponsePoint>();

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

	public void implode(){
		if(active==null){
			return;
		}
		if(involved.size()>=2){
			for(MyResponsePoint r:involved){
				r.restoreCoords();
			}
		}
		involved.clear();
	}
	public void explode(){
		if(active==null){
			return;
		}
		active.backupCoords();
		List<MyResponsePoint> list = getView().getVisiblePoints();
		for(MyResponsePoint mrp:list){
			double dx = mrp.getX()-active.getX();
			double dy = mrp.getY()-active.getY();
			double dist = Math.sqrt(dx*dx+dy*dy);
			if(dist<Configuration.MIN_ALLOWED_DISTANCE){
				involved.add(mrp);
			}
		}
		int size = involved.size();
		if(size<2){
			return;
		}
		double[] center_of_mass = new double[2];
		for(MyResponsePoint mrp:involved){
			center_of_mass[0] += mrp.getX();
			center_of_mass[1] += mrp.getY();
		}
		center_of_mass[0]/=size;
		center_of_mass[1]/=size;
		//explodeDirection(center_of_mass);
		explodeCircle(center_of_mass);
	}
	public void explodeDirection(double[] center_of_mass){
		for(MyResponsePoint mrp:involved){
			double dx = center_of_mass[0]-mrp.getX();
			double dy = center_of_mass[1]-mrp.getY();
			double dist = Math.sqrt(dx*dx+dy*dy);
			double ratio = Configuration.MIN_ALLOWED_DISTANCE/dist;
			mrp.backupCoords();
			mrp.setX(center_of_mass[0]-dx*ratio);
			mrp.setY(center_of_mass[1]-dy*ratio);
		}
	}
	public void explodeCircle(double[] center_of_mass){
		int len = involved.size();
		for(int a=0; a<len; a++){
			MyResponsePoint mrp = involved.get(a);
			double cx = Configuration.MIN_ALLOWED_DISTANCE*Math.cos(2*a*Math.PI/len);
			double cy = Configuration.MIN_ALLOWED_DISTANCE*Math.sin(2*a*Math.PI/len);
			mrp.backupCoords();
			mrp.setX(center_of_mass[0]+cx);
			mrp.setY(center_of_mass[1]+cy);
		}
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
		}else{
			int move = this.model.getActualSnapshot() + steps;
			this.model.setActualSnapshot(move<0?0:move);
		}
	}

	public boolean containsPixel(MyResponsePoint point,int x,int y){
		double px = (x - this.view.getX_offset())/this.view.getRatio();
		double py = (this.view.getHeight()-y-this.view.getY_offset())/this.view.getRatio();
		return contains(point,px,py);
	}
	public boolean contains(MyResponsePoint point,double x,double y){
		double cx = point.getCenterX();
		double cy = point.getCenterY();
		double w2 = point.getWidth()/2+SELECTION_TRESSHOLD/this.view.getRatio();
		double h2 = point.getHeight()/2+SELECTION_TRESSHOLD/this.view.getRatio();
		return x<cx+w2 && x>cx-w2 && y<cy+h2 && y>cy-h2;
	}
	public void setPoint(MyResponsePoint point){
		implode();
		this.active = point;
		if(Configuration.explode){
			explode();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2){
			createNewPoint((e.getX() - this.view.getX_offset())/this.view.getRatio(), 
				(this.view.getHeight() - e.getY() - this.view.getY_offset())/this.view.getRatio());
			this.view.repaint();
		} else if(this.getModel().getActualSnapshot() != -1){

			if(this.getModel().getGroups() != null){
				if(this.getModel().getGroups().size() != 0){

					for (GroupModel group : this.getModel().getGroups()) {
						for(MyResponsePoint point : group.getPointFromLayer(this.model.getActualSnapshot())){
							if(containsPixel(point,e.getX(),e.getY())){
								SettingSnapshotPaneController ctrl = (SettingSnapshotPaneController) MainWindow.getController(Controllers.SETTING_SNAPSHOT_PANE_CTRL);
								if(active!=null) {
									active.setActive(false);
								}
								if(point!=active){
									point.setActive(true);
									setPoint(point);
									ctrl.setModel(point);
									this.getModel().remember(point);
								}else{
									setPoint(null);
									ctrl.setModel(null);
								}
								notifyController();
								return;
							}
						}
					}
				}
			}
			implode();
			notifyController();
		}
	}
	public void createNewPoint(double x,double y){
		double z = model.getActualSnapshot();
		double tmp = z;
		switch(model.getType()){
			case DICOM + AXIS_X: z=x; x=tmp; break;
			case DICOM + AXIS_Y: z=y; y=tmp; break;
			case TMS   + AXIS_X: z=x; x=tmp; break;
			case TMS   + AXIS_Y: z=y; y=tmp; break;
		}
		for (GroupModel group : this.getModel().getGroups()) {
			if(group.getName().equals(Configuration.UNASSIGN_GROUP)){
				Response r = new Response();
				// #######   ###   ###     ###
				//    #     #   #  #  #   #   #
				//    #    #     # #   # #     #
				//    #    #     # #   # #     #
				//    #     #   #  #  #   #   #
				//    #      ###   ###     ###
				// do response pridat nejake informace / pridat moznost v GUI pridat
				// 	informaci runce
				MyResponsePoint point = new MyResponsePoint(x,y,Configuration.RADIUS,r);
				point.setRealZ(z);
				point.setGroup(group);
				group.getPoints().add(point);
				System.out.println("Bod: ["+x+";"+y+";"+z+"] vytvoren");
				for(MyResponsePoint mrp: group.getPoints()){
					System.out.println("Bod: ["+mrp.getRealX()+";"+mrp.getRealY()+";"+mrp.getRealZ()+"]");
				}
				break;
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
