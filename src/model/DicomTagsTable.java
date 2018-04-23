package model;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import ij.plugin.DICOM;

public class DicomTagsTable extends JTable {

	public DicomTagsTable(DICOM tableData) {
		super();
		this.setModel(new DicomTagsTableModel(tableData));
	}
	
	public DicomTagsTable() {
		this(null);
	}
}
