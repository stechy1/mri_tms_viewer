package model;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import model.dialogWindow.group.GroupModel;

public class PointsDataTable extends JTable {

	public PointsDataTable(ArrayList<GroupModel> tableData) {
		super();
		this.setModel(new PointsDataTableModel(tableData));
	}
	
	public PointsDataTable() {
		this(null);
	}
}
