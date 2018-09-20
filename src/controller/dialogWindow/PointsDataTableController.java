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
import java.awt.Color;
import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
public class PointsDataTableController extends MouseAdapter implements IController{
	private ShowDicomTags view;
	public PointsDataTableController(ShowDicomTags view){
		this.view = view;
		view.getPointsDataTable().setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){
			@Override
			public Component getTableCellRendererComponent(
				JTable table,
				Object value,
				boolean isSelected,
				boolean hasFocus,
				int rw,
				int column
			){
				final Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,rw,column);
				if(view.getPointsDataTable().getModel().getPointAt(rw).isActive()){
					c.setBackground(Color.GREEN);
				}else{
					c.setBackground(Color.WHITE);
				}
				return c;
			}
		});
	}
	@Override
	public void mouseClicked(MouseEvent e){
		PointsDataTable table = view.getPointsDataTable();
		final int row = table.getSelectedRow();
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
		return this.view.getPointsDataTable();
	}
	@Override
	public void setModel(Object model) {}
}
