package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConnectionManager {

	protected static Connection conn = null;
	protected static Properties prop = null;


	private static Properties getProperties() throws RuntimeException {

		Properties prop = new Properties();
		try {
			ResourceBundle infoDataBase = ResourceBundle.getBundle("database");
			prop.setProperty("connection", infoDataBase.getString("db.url"));
			prop.setProperty("username", infoDataBase.getString("db.user"));
			prop.setProperty("password", infoDataBase.getString("db.password"));

		} catch (Exception e1) {
			throw new RuntimeException("Ocurrio un error al leer la configuracion desde el archivo");
			// TODO: disparar Exception propia
		}
		return prop;

	}

	public static void connect() throws SQLException {
			prop = getProperties();
			conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
					prop.getProperty("password"));
			
	}

	public static void disconnect() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void reconnect() throws SQLException {
		disconnect();
		connect();
	}

	public static Connection getConnection() throws SQLException {

		if (conn == null) {
			connect();
		}
		return conn;
	}

}
