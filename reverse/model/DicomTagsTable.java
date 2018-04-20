/*
 * Decompiled with CFR 0_123.
 */
package model;

import ij.plugin.DICOM;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import model.DicomTagsTableModel;

public class DicomTagsTable
extends JTable {
    public DicomTagsTable(DICOM tableData) {
        this.setModel(new DicomTagsTableModel(tableData));
    }

    public DicomTagsTable() {
        this(null);
    }
}

