package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;



public class Db_Connection {
public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		//Loading the required JDBC Driver class
		Class.forName("com.mysql.jdbc.Driver");	
		
		//Creating a connection to the database
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:4928/mobiledoc","ecwDbUser","Pwd4db@114");
		
		//Executing SQL query and fetching the result
		Statement st = conn.createStatement();
		try
		{
		String sqlStr = "SELECT * FROM users";
		ResultSet rs = st.executeQuery(sqlStr);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		String name = rsmd.getColumnName(1);
		
		while (rs.next()) {
			String uid = rs.getString(1);
			String temp =rs.getString("ufname");
				System.out.println("UID"+":"+uid+"ufname"+":"+temp);
			// Print one row
			/*for (int i = 1; i <= columnsNumber; i++) {
					System.out.print(rsmd.getSchemaName(i) + " "+ rs.getString(i) + " ");*/
			//} 
	}
		}catch(SQLException sq)
		{
			System.out.println(sq);
		}
}
}
