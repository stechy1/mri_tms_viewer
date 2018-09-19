package controller.dialogWindow;
import model.PointsDataTable;
import view.MainWindow;
import enums.Controllers;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import view.dialogWindow.ShowDicomTags;
import interfaces.IController;
import model.MyResponsePoint;
import controller.centerPane.ImagePaneController;
public class PointsDataTableController extends MouseAdapter implements IController{
	private ShowDicomTags view;
	public PointsDataTableController(ShowDicomTags view){
		this.view = view;
	}
	@Override
	public void mouseClicked(MouseEvent e){
		PointsDataTable table = view.getPointsDataTable();
		int row = table.getSelectedRow();
		MyResponsePoint mrp = table.getModel().getPointAt(row);
		MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
		ImagePaneController ctrl = (ImagePaneController)MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
		ctrl.setActive(mrp);
	}
	@Override
	public void notifyController() {}
	@Override
	public void notifyAllControllers() {}
	@Override
	public Controllers getType() {
		return Controllers.TABLE_POINTS_CTRL;
	}
	@Override
	public Object getView() {
		return this.view;
	}
	@Override
	public Object getModel() {
		return null;
	}
	@Override
	public void setModel(Object model) {}
}
