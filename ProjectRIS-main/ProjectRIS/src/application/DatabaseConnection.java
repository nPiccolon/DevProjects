package application;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
	
	
	public static Connection getConnection() throws SQLException {
		
	    String username = "root";
	    String password = "roscojack1997";
	    String url = "jdbc:mysql://localhost:3306/db_ris";

		Connection con = DriverManager.getConnection(url, username, password);
		
		return con;
	}
}