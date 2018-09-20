package model;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import model.dialogWindow.group.GroupModel;

public class PointsDataTable extends JTable{
	private PointsDataTableModel model;
	public PointsDataTable(ArrayList<GroupModel> tableData) {
		this.model = new PointsDataTableModel(tableData);
		this.setModel(model);
	}

	public PointsDataTableModel getModel(){
		return model;
	}	

	public PointsDataTable() {
		this(null);
	}

}
