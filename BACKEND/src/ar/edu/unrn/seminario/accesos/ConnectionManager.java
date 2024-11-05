package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL_DB = "jdbc:mysql://localhost:3306/";
	protected static String DB = "seminario_2024_1_2";
	protected static String user = "root";
	protected static String pass = "hernan123";

	protected static Connection conn = null;

	public static void connect() {
		try {
			conn = DriverManager.getConnection(URL_DB + DB, user, pass);
		} catch (SQLException sqlEx) {
			System.out.println("No se ha podido conectar a " + URL_DB + DB + ". " + sqlEx.getMessage());
			System.out.println("Error al cargar el driver");
		}
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

	public static void reconnect() {
		disconnect();
		connect();
	}

	public static Connection getConnection() {
		try {
	        return DriverManager.getConnection(URL_DB + DB, user, pass);
	    } catch (SQLException sqlEx) {
	        System.out.println("No se ha podido conectar a " + URL_DB + DB + ". " + sqlEx.getMessage());
	        return null;
	    }
	}

}
