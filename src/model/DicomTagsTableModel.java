package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import enums.DicomTags;
import ij.plugin.DICOM;
import ij.util.DicomTools;

public class DicomTagsTableModel extends AbstractTableModel {

	protected String [] columnName = {"Skupina", "Element", "Popis", "VR", "Hodnota"};
	protected ArrayList<DicomTag> data;
	
	private DICOM model;

	public DicomTagsTableModel(DICOM model) {
		this.model = model;
		this.setData(DicomTags.getDicomTags());
	}
	
	@Override
	public int getColumnCount() {
		return this.columnName.length;
	}

	@Override
	public int getRowCount() {
		return this.data.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}

	@Override
	public Object getValueAt(int row, int column) {
		DicomTag tag = data.get(row);
		switch(column){
		case 0:
			return tag.getGroup();
		case 1:
			return tag.getElement();
		case 2:
			return tag.getDescription();
		case 3:
			return tag.getVr();
		case 4:
			if(model==null){
				return "???";
			}else{
				return DicomTools.getTag(model, tag.getGroup() + "," + tag.getElement());
			}
		default:
			return null;
		}
	}
	
	public void setData(ArrayList<DicomTag> data){
		this.data = data;
		fireTableRowsInserted(0, data.size()-1);
	}

}
