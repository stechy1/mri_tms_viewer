package model;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import model.dialogWindow.group.GroupModel;
import controller.Configuration;

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
	public boolean isCellEditable(int row, int col){
		return col>=1;
	}

	@Override
	public void setValueAt(Object value,int row,int col){
		try{
			double val = Double.valueOf((String)value);
			switch(col){
				case 1: model.get(row).setX(val/Configuration.pixelSpace); break;
				case 2: model.get(row).setY(val/Configuration.pixelSpace); break;
				case 3: model.get(row).setZ(val/Configuration.sliceThickness); break;
				default:model.get(row).getResponse().getData().put(columnName[col-3],val); break;
			}
		}catch(Exception e){}
		fireTableCellUpdated(row,col);
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
		switch(column){
			case 0: return columnName[0];
			case 1: return "X";
			case 2: return "Y";
			case 3: return "Z";
			default:return columnName[column-3];
		}
	}

	public Object customFilter(double value) {
		if(Double.isNaN(value)){
			return "-";
		}
		return String.format("%.2f",value);
	}
	@Override
	public Object getValueAt(int row, int column) {
		try{
			switch(column){
				case 0: return customFilter(model.get(row).getResponse().getData().get(columnName[0]));
				case 1: return customFilter(model.get(row).getRealX()*Configuration.pixelSpace);
				case 2: return customFilter(model.get(row).getRealY()*Configuration.pixelSpace);
				case 3: return customFilter(model.get(row).getRealZ()*Configuration.sliceThickness);
				default:return customFilter(model.get(row).getResponse().getData().get(columnName[column-3]));
			}
		}catch(Exception e){}
		return "???";
	}

}
