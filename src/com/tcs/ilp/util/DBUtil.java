package com.tcs.ilp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	
	private static final String drivername= "oracle.jdbc.driver.OracleDriver";
	private static final String url= "jdbc:oracle:thin:@intvmoradb04:1521:ORAJAVADB"; 
	private static final String username= "TVM1617_TVM38_TJA233_DEV";
	private static final String password= "tcstvm"; 
	
	public static Connection getConnection(){
		
		Connection conn = null;
		
		try {
			// Load Driver
			Class.forName(drivername);
			
			// Create Connection
			conn = DriverManager.getConnection(url, username, password);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	
	public static void  closeConnection(Connection conn){
		
		try{
			if(conn != null){
				conn.close();
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void  closeStatement(PreparedStatement stmt){
		
		try{
			if(stmt != null){
				stmt.close();
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void  closeResultSet(ResultSet rs){
		
		try{
			if(rs != null){
				rs.close();
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
