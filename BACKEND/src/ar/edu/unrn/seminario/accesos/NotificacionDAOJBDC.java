package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Notificacion;


public class NotificacionDAOJBDC implements NotificacionDao{

	@Override
	public void create(Notificacion notificacion) throws DataBaseConnectionException {
			PreparedStatement statement;
			Connection conn;
			try {
				conn = ConnectionManager.getConnection();
				
				statement = conn
						.prepareStatement("INSERT INTO notificaciones(id_proyecto, nombre_usuario, rol_invitado, descripcion, fecha)" + "VALUES(?, ?, ?, ?, ?)");
			
				statement.setInt(1, notificacion.getIdProyecto());
				statement.setString(2, notificacion.getUsername());
				statement.setInt(3, notificacion.getCodigoRol());
				statement.setString(4, notificacion.getDescripcion());
				statement.setDate(5, java.sql.Date.valueOf(notificacion.getFecha()));
				
				int cant = statement.executeUpdate();
			
				if ( cant <= 0) {
				}
			}
			catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage() + "No se pudo invitar al usuario", "Error", JOptionPane.ERROR_MESSAGE);
			}
			finally {
				try {
					ConnectionManager.disconnect();
				} catch (SQLException e) {
					throw new DataBaseConnectionException("exceptionDAO.disconnect");
				}
			}

		
	}

	@Override
	public void remove(int idProyecto, String username) throws DataBaseConnectionException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("DELETE FROM notificaciones\r\n"
					+ "WHERE nombre_usuario = ? and id_proyecto = ?");
			
			statement.setString(1, username);
			statement.setInt(2, idProyecto);
			
			int verificacion = statement.executeUpdate();
			
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage() + "No se pudo consultar las tareas", "Error", JOptionPane.ERROR_MESSAGE);
		}
		 finally {
			try {
				ConnectionManager.disconnect();
			} catch (SQLException e) {
				throw new DataBaseConnectionException("exceptionDAO.disconnect");
			}
		}
		
	}
		
	
	
	@Override
	public List<Notificacion> findAll(String username) throws NotNullException, DataEmptyException, DataBaseConnectionException {
		List<Notificacion>notificaciones = new ArrayList<Notificacion>();
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT n.id_proyecto, n.nombre_usuario, n.rol_invitado, n.descripcion, n.fecha\r\n"
					+ "FROM notificaciones n\r\n"
					+ "WHERE n.nombre_usuario = ?");
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Notificacion unaNotificacion = new Notificacion(rs.getInt("n.id_proyecto"), rs.getString("n.nombre_usuario"), 
						rs.getInt("n.rol_invitado"),rs.getString("n.descripcion"), rs.getDate("n.fecha").toLocalDate());
				
				notificaciones.add(unaNotificacion);
			}
		
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage() + "Error en la consulta", "Error", JOptionPane.ERROR_MESSAGE);
		}
		 finally {
			try {
				ConnectionManager.disconnect();
			} catch (SQLException e) {
				throw new DataBaseConnectionException("exceptionDAO.disconnect");
			}
		}
		return notificaciones;
	}

	@Override
	public int existNotification(int idProyecto, String username) throws DataBaseConnectionException {
		int existe = 0;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT n.id_proyecto, n.nombre_usuario\r\n"
					+ "FROM notificaciones n\r\n"
					+ "WHERE n.nombre_usuario = ? and id_proyecto = ?");
			statement.setString(1, username);
			statement.setInt(2, idProyecto);
			
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
	            existe = 1; // Si hay un registro, existe se convierte en 1
	        }
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage() + "Error en la consulta", "Error", JOptionPane.ERROR_MESSAGE);
		}
		 finally {
			try {
				ConnectionManager.disconnect();
			} catch (SQLException e) {
				throw new DataBaseConnectionException("exceptionDAO.disconnect");
			}
		}
		
		return existe;
	}

}
