package com.fime.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.fime.db.DBManager;
import com.fime.dto.Medicine;

public class InvTableModel extends AbstractTableModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String[]> rows;
	
	String[] columnNames = {"ID", "Nombre", "Cantidad", "Precio", "Caducidad", "Altas", "Bajas"};

	@Override
	public String getColumnName(int index) {
	    return columnNames[index];
	}

    public InvTableModel() {
        rows = new ArrayList<String[]>();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
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
        row[5] = medicine.getAltas().toString();
        row[6] = medicine.getBajas().toString();
                
        rows.add(row);
        fireTableRowsInserted(rowCount, rowCount);
    }   
    
    public void reloadModel(){
    	rows.clear();
    	int rowCount = getRowCount();
    	fireTableRowsInserted(rowCount, rowCount);
    	
    	DBManager db = new DBManager();
    	
    	try {
    		List<Medicine> medicineList = db.getMedicineList();
			for(Medicine medicine : medicineList){
				
				String[] row = new String[getColumnCount()];
		        
		        row[0] = medicine.getId().toString();
		        row[1] = medicine.getName();
		        row[2] = medicine.getTotal().toString();
		        row[3] = medicine.getPrice().toString();
		        row[4] = medicine.getExpiration();
		        row[5] = medicine.getAltas().toString();
		        row[6] = medicine.getBajas().toString();
		                
		        rows.add(row);
		        fireTableRowsInserted(rowCount, rowCount);
			}
		} catch (Exception e) {
			System.out.println("Error al recargar el listado de medicamentos");
			e.printStackTrace();
		}
    }
    
    public void reloadModel(String filter){
    	rows.clear();
    	int rowCount = getRowCount();
    	fireTableRowsInserted(rowCount, rowCount);
    	
    	DBManager db = new DBManager();
    	
    	try {
    		List<Medicine> medicineList = db.getFilteredMedicineList(filter);
			for(Medicine medicine : medicineList){
				
				String[] row = new String[getColumnCount()];
		        
		        row[0] = medicine.getId().toString();
		        row[1] = medicine.getName();
		        row[2] = medicine.getTotal().toString();
		        row[3] = medicine.getPrice().toString();
		        row[4] = medicine.getExpiration();
		        row[5] = medicine.getAltas().toString();
		        row[6] = medicine.getBajas().toString();
		                
		        rows.add(row);
		        fireTableRowsInserted(rowCount, rowCount);
			}
		} catch (Exception e) {
			System.out.println("Error al recargar el listado de medicamentos");
			e.printStackTrace();
		}
    }
}
