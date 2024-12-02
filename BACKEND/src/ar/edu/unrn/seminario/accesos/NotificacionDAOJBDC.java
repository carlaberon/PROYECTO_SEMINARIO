package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Notificacion;


public class NotificacionDAOJBDC implements NotificacionDao{

	@Override
	public void create(Notificacion notificacion, String username, int idProyecto) {
			PreparedStatement statement;
			Connection conn;
			try {
				conn = ConnectionManager.getConnection();
				
				statement = conn
						.prepareStatement("INSERT INTO notificaciones(nombre_usuario, id_proyecto, fecha, descripcion)" + "VALUES(?, ?, ?, ?)");
			
				statement.setString(1, username);
				statement.setInt(2, idProyecto);
				statement.setDate(3, java.sql.Date.valueOf(notificacion.getFecha()));
				statement.setString(4, notificacion.getDescripcion());
				
				int cant = statement.executeUpdate();
			
				if ( cant <= 0) {
				}
			}
			catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage() + "No se pudo crear la tarea", "Error", JOptionPane.ERROR_MESSAGE);
			}
			finally {
				ConnectionManager.disconnect();
			}

		
	}

	@Override
	public List<Notificacion> findAll(String username) throws NotNullException, DataEmptyException {
		List<Notificacion>notificaciones = new ArrayList<Notificacion>();
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT n.descripcion, n.fecha\r\n"
					+ "FROM notificaciones n\r\n"
					+ "WHERE n.nombre_usuario = ?");
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Notificacion unaNotificacion = new Notificacion(rs.getString("n.descripcion"), rs.getDate("n.fecha").toLocalDate());
				
				notificaciones.add(unaNotificacion);
			}
		
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage() + "No se pudo consultar las tareas", "Error", JOptionPane.ERROR_MESSAGE);
		}
		 finally {
			ConnectionManager.disconnect();
		}
		return notificaciones;
	}

}
