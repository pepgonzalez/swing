package com.fime.db;

public class DBManager {
	
public DBManager(){
		
	}
	
	private Connection getConnection() throws Exception{
		Connection c = null;
		Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:test.db");
	    return c;
	}
	
	public List<MedicineDTO> getMedicineList() throws Exception{
		final List<MedicineDTO> medicineList = new ArrayList<MedicineDTO>();
	
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
		ResultSet result = query.executeQuery( "SELECT * FROM INVENTARIO;" );
	    while ( result.next() ) {
	    	
	    	MedicineDTO medicine = new MedicineDTO();
	    	medicine.setId(result.getLong("ID"));
	    	medicine.setName(result.getString("NOMBRE"));
	    	medicine.setTotal(result.getInt("CANTIDAD"));
	    	medicine.setPrice(result.getFloat("PRECIO"));
	    	medicine.setExpiration(result.getString("CADUCIDAD"));
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
	
	public void insertMedicine(MedicineDTO medicine) throws Exception{
		
		StringBuilder q = new StringBuilder();
		q.append("INSERT INTO INVENTARIO(ID,NOMBRE,CANTIDAD,PRECIO,CADUCIDAD) VALUES(");
		q.append(this.getNextId());
		q.append(",'").append(medicine.getName().toUpperCase()).append("',");
		q.append(medicine.getTotal()).append(",");
		q.append(medicine.getPrice()).append(",");
		q.append("'").append(medicine.getExpiration()).append("');");
		
		Connection connection = this.getConnection();
		Statement query = connection.createStatement();
	
		System.out.println(q.toString());

		query.executeUpdate(q.toString());
		
	    query.close();
	    connection.close();
			
	}

}
