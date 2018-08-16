package model;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import model.dialogWindow.group.GroupModel;

import javax.swing.table.AbstractTableModel;

public class PointsDataTableModel extends AbstractTableModel {

	private ArrayList<MyResponsePoint> model;
	private String[] columnName;

	public PointsDataTableModel(ArrayList<GroupModel> model) {
		Set<String> set = new TreeSet<>();
		this.model = new ArrayList<>();
		for(GroupModel mod: model){
			for(MyResponsePoint mrp: mod.getPoints()){
				this.model.add(mrp);	
				set.addAll(mrp.getResponse().getData().keySet());
			}
		}
		this.columnName = set.toArray(new String[set.size()]);
		fireTableRowsInserted(0, model.size()-1);
	}
	
	@Override
	public int getColumnCount() {
		return this.columnName.length;
	}

	@Override
	public int getRowCount() {
		return this.model.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}

	@Override
	public Object getValueAt(int row, int column) {
		try{
			return model.get(row).getResponse().getData().get(columnName[column]);
		}catch(Exception e){}
		return "???";
	}

}
