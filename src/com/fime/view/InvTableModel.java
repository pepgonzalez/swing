package com.fime.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.fime.dto.Medicine;

public class InvTableModel extends AbstractTableModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String[]> rows;

    public InvTableModel() {
        rows = new ArrayList<String[]>();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] row = rows.get(rowIndex);
        return row[columnIndex];
    }

    public void addRow(Medicine medicine) {
        int rowCount = getRowCount();
        
        String[] row = new String[getColumnCount()];
        
        row[0] = medicine.getId().toString();
        row[1] = medicine.getName();
        row[2] = medicine.getTotal().toString();
        row[3] = medicine.getPrice().toString();
        row[4] = medicine.getExpiration();
        
        
        rows.add(row);
        fireTableRowsInserted(rowCount, rowCount);
    }            
}