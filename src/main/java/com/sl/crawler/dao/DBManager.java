package com.sl.crawler.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

	private static DBManager manager = new DBManager();

	private Connection connection;

	private DBManager() {
		initConnection();
	}

	public static DBManager getInstance() {
		return manager;
	}

	public Connection getConnection() {
		if (connection == null)
			initConnection();

		return connection;
	}

	public  void closeConn(){
		if(connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	private void initConnection() {
		Properties pro = new Properties();
		try {
			pro.load(getClass().getResourceAsStream("/jdbc.properties"));
			String driver = pro.getProperty("jdbc.driverClass");
			String url = pro.getProperty("jdbc.url");
			String user = pro.getProperty("jdbc.username");
			String password = pro.getProperty("jdbc.password");
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url, user, password);
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void main(String[] args) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		System.out.println(con);
		con.close();
	}
	
	
}
