package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import ar.edu.unrn.seminario.exception.DataBaseConfigurationException;

public class ConnectionManager {

	protected static Connection conn = null;
	protected static Properties prop = null;


	private static Properties getProperties() throws DataBaseConfigurationException {

		Properties prop = new Properties();
		try {
			ResourceBundle infoDataBase = ResourceBundle.getBundle("database");
			prop.setProperty("connection", infoDataBase.getString("db.url"));
			prop.setProperty("username", infoDataBase.getString("db.user"));
			prop.setProperty("password", infoDataBase.getString("db.password"));

		} catch (Exception e1) {
			throw new DataBaseConfigurationException("exceptionConnectionManager.error");
		}
		return prop;

	}

	public static void connect() throws SQLException {
			prop = getProperties();
			conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
					prop.getProperty("password"));
			
	}

	public static void disconnect() throws SQLException  {
		if (conn != null) {		
					conn.close();			
				conn = null;
		}
	}

	public static void reconnect() throws SQLException {
		disconnect();
		connect();
	}

	public static Connection getConnection() throws SQLException  {
		if (conn == null) {
				connect();	
		}
		return conn;
	}

}
