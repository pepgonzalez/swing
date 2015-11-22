package com.fime.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fime.dto.Medicine;

public class DBManager {
	
public DBManager(){
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c= DriverManager.getConnection("jdbc:sqlite:inventario.db");
			
			String table = this.existTable();
			System.out.println("tabla obtenida: " + table);
			if (table == null || table.length() <= 0){
				createTable();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Connection getConnection() throws Exception{
		Connection c = null;
		Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:inventario.db");
	    return c;
	}
	
	private void createTable()throws Exception{
		StringBuilder q = new StringBuilder();
		q.append("CREATE TABLE \"main\".\"INVENTARIO\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR NOT NULL , \"CANTIDAD\" INTEGER NOT NULL , \"PRECIO\" FLOAT NOT NULL , \"CADUCIDAD\" VARCHAR NOT NULL, \"ALTAS\" INTEGER NOT NULL, \"BAJAS\" INTEGER NOT NULL );");
		
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
	
		System.out.println(q.toString());

		query.executeUpdate(q.toString());
		
	    query.close();
	    connection.close();		
	    System.out.println("tabla creada");
	}
	
	private String existTable()throws Exception{
		String next = "";
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
		ResultSet result = query.executeQuery( "SELECT NAME FROM sqlite_master WHERE type='table' AND name='INVENTARIO';" );

	    while ( result.next() ) {
	    	next = result.getString("NAME");
	    }
	    result.close();
	    query.close();
	    connection.close();
	    
	    return next;
	}
	
	public List<Medicine> getMedicineList() throws Exception{
		final List<Medicine> medicineList = new ArrayList<Medicine>();
	
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
		ResultSet result = query.executeQuery( "SELECT * FROM INVENTARIO;" );
	    while ( result.next() ) {
	    	
	    	Medicine medicine = new Medicine();
	    	medicine.setId(result.getLong("ID"));
	    	medicine.setName(result.getString("NOMBRE"));
	    	medicine.setTotal(result.getInt("CANTIDAD"));
	    	medicine.setPrice(result.getFloat("PRECIO"));
	    	medicine.setExpiration(result.getString("CADUCIDAD"));
	    	medicine.setAltas(result.getInt("ALTAS"));
	    	medicine.setBajas(result.getInt("BAJAS"));
	    	medicineList.add(medicine);
	    }
	    result.close();
	    query.close();
	    connection.close();
		
		return medicineList;
	}
	
	public List<Medicine> getFilteredMedicineList(String filter) throws Exception{
		final List<Medicine> medicineList = new ArrayList<Medicine>();
	
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
		ResultSet result = query.executeQuery( "SELECT * FROM INVENTARIO WHERE NOMBRE LIKE '%" + filter.toUpperCase() + "%';" );
	    while ( result.next() ) {
	    	
	    	Medicine medicine = new Medicine();
	    	medicine.setId(result.getLong("ID"));
	    	medicine.setName(result.getString("NOMBRE"));
	    	medicine.setTotal(result.getInt("CANTIDAD"));
	    	medicine.setPrice(result.getFloat("PRECIO"));
	    	medicine.setExpiration(result.getString("CADUCIDAD"));
	    	medicine.setAltas(result.getInt("ALTAS"));
	    	medicine.setBajas(result.getInt("BAJAS"));
	    	medicineList.add(medicine);
	    }
	    result.close();
	    query.close();
	    connection.close();
		
		return medicineList;
	}
	
	private Integer getNextId()throws Exception{
		Integer next = 0;
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
		ResultSet result = query.executeQuery( "SELECT IFNULL(MAX(ID) + 1, 1) AS CONSECUTIVE FROM INVENTARIO;" );

	    while ( result.next() ) {
	    	next = result.getInt("CONSECUTIVE");
	    }
	    result.close();
	    query.close();
	    connection.close();
	    
	    return next;
	}
	
	public Integer getAvailableMedicine(String medicineName)throws Exception{
		Integer total = 0;
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
		ResultSet result = query.executeQuery( "SELECT CANTIDAD FROM INVENTARIO WHERE NOMBRE = '" + medicineName + "';" );

	    while ( result.next() ) {
	    	total = result.getInt("CANTIDAD");
	    }
	    result.close();
	    query.close();
	    connection.close();
	    
	    return total;
	}
	
	public void insertMedicine(Medicine medicine) throws Exception{
		
		StringBuilder q = new StringBuilder();
		q.append("INSERT INTO INVENTARIO(ID,NOMBRE,CANTIDAD,PRECIO,CADUCIDAD,ALTAS,BAJAS) VALUES(");
		q.append(this.getNextId());
		q.append(",'").append(medicine.getName().toUpperCase()).append("',");
		q.append(medicine.getTotal()).append(",");
		q.append(medicine.getPrice()).append(",");
		q.append("'").append(medicine.getExpiration()).append("',");
		q.append(0).append(",").append(0).append(");");
		
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
	
		System.out.println(q.toString());

		query.executeUpdate(q.toString());
		
	    query.close();
	    connection.close();		
	}
	
	public void updateMedicineTotal(String medicine, Integer total) throws Exception{
		StringBuilder q = new StringBuilder();
		
		q.append("UPDATE INVENTARIO SET CANTIDAD = ");
		q.append(total);
		q.append(" WHERE NOMBRE = '").append(medicine).append("';");
		
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
	
		System.out.println(q.toString());

		query.executeUpdate(q.toString());
		
	    query.close();
	    connection.close();	
	}
	
	public Integer getUpMovements(String medicineName)throws Exception{
		Integer total = 0;
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
		ResultSet result = query.executeQuery( "SELECT ALTAS FROM INVENTARIO WHERE NOMBRE = '" + medicineName + "';" );

	    while ( result.next() ) {
	    	total = result.getInt("ALTAS");
	    }
	    result.close();
	    query.close();
	    connection.close();
	    
	    return total;
	}
	
	public void updateUpMovements(String medicine, Integer total) throws Exception{
		StringBuilder q = new StringBuilder();
		
		q.append("UPDATE INVENTARIO SET ALTAS = ");
		q.append(total);
		q.append(" WHERE NOMBRE = '").append(medicine).append("';");
		
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
	
		System.out.println(q.toString());

		query.executeUpdate(q.toString());
		
	    query.close();
	    connection.close();	
	}
	
	public void addUpMovement(String medicine) throws Exception{
		int actual = this.getUpMovements(medicine);
		this.updateUpMovements(medicine, actual + 1);
	}

	
	public Integer getDownMovements(String medicineName)throws Exception{
		Integer total = 0;
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
		ResultSet result = query.executeQuery( "SELECT BAJAS FROM INVENTARIO WHERE NOMBRE = '" + medicineName + "';" );

	    while ( result.next() ) {
	    	total = result.getInt("BAJAS");
	    }
	    result.close();
	    query.close();
	    connection.close();
	    
	    return total;
	}
	
	public void updateDownMovements(String medicine, Integer total) throws Exception{
		StringBuilder q = new StringBuilder();
		
		q.append("UPDATE INVENTARIO SET BAJAS = ");
		q.append(total);
		q.append(" WHERE NOMBRE = '").append(medicine).append("';");
		
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
	
		System.out.println(q.toString());

		query.executeUpdate(q.toString());
		
	    query.close();
	    connection.close();	
	}
	
	public void addDownMovement(String medicine) throws Exception{
		int actual = this.getDownMovements(medicine);
		this.updateDownMovements(medicine, actual + 1);
	}

	
}
