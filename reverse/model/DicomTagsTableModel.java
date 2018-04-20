/*
 * Decompiled with CFR 0_123.
 */
package model;

import enums.DicomTags;
import ij.plugin.DICOM;
import ij.util.DicomTools;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import model.DicomTag;

public class DicomTagsTableModel
extends AbstractTableModel {
    protected String[] columnName = new String[]{"Skupina", "Element", "Popis", "VR", "Hodnota"};
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
        return this.columnName[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        DicomTag tag = this.data.get(row);
        switch (column) {
            case 0: {
                return tag.getGroup();
            }
            case 1: {
                return tag.getElement();
            }
            case 2: {
                return tag.getDescription();
            }
            case 3: {
                return tag.getVr();
            }
            case 4: {
                return DicomTools.getTag(this.model, String.valueOf(tag.getGroup()) + "," + tag.getElement());
            }
        }
        return null;
    }

    public void setData(ArrayList<DicomTag> data) {
        this.data = data;
        this.fireTableRowsInserted(0, data.size() - 1);
    }
}

