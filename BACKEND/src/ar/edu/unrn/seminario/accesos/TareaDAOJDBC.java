package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.sql.SQLException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Tarea;
import ar.edu.unrn.seminario.modelo.Usuario;

public class TareaDAOJDBC implements TareaDao{

	@Override
	public void create(Tarea tarea) {
		PreparedStatement statement;
		Connection conn;
		try {
			conn = ConnectionManager.getConnection();
			
			statement = conn
					.prepareStatement("INSERT INTO tareas(nombre, id_proyecto, prioridad, usuario, estado, descripcion, fecha_inicio, fecha_fin)" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
		
			statement.setString(1, tarea.getNombre());
			statement.setInt(2, tarea.getProyecto().getId());
			statement.setString(3, tarea.getPrioridad());
			statement.setString(4, tarea.getUsuario());
			statement.setString(5,tarea.getEstado());
			statement.setString(6, tarea.getDescripcion());
			statement.setDate(7, java.sql.Date.valueOf(tarea.getInicio()));
			statement.setDate(8, java.sql.Date.valueOf(tarea.getFin()));
			
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
	public void update(Tarea tarea) {
		try {
		   Connection conn = ConnectionManager.getConnection();
		   PreparedStatement statement = conn.prepareStatement("UPDATE tareas SET nombre = ?, prioridad = ?, usuario = ?, estado = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ? WHERE id = ? ");
		        
		   // Establecer los valores de los nuevos datos de la tarea
		   statement.setString(1, tarea.getNombre());
		   statement.setString(2, tarea.getPrioridad());
		   statement.setString(3, tarea.getUsuario());
		   statement.setString(4, tarea.getEstado());
		   statement.setString(5, tarea.getDescripcion());
		   statement.setDate(6, Date.valueOf(tarea.getInicio()));
	       statement.setDate(7, Date.valueOf(tarea.getFin()));
		   
	       // Parametros de busqueda en la base de datos
	        statement.setInt(8, tarea.getId());
	       
		   int verificacion = statement.executeUpdate();
		        
		   if (verificacion <= 0) {
		   }
		        
		   } catch (SQLException e1) { 
			   JOptionPane.showMessageDialog(null, e1.getMessage() + "No se pudo actualizar la tarea", "Error", JOptionPane.ERROR_MESSAGE);
		   } finally {
		       ConnectionManager.disconnect();
		   }
	}
	
	@Override
	public List<Tarea> findByProject(int id_project) throws DataEmptyException, NotNullException, InvalidDateException{
		List<Tarea>tareas = new ArrayList<Tarea>();
		Usuario unUsuario = null;
		Proyecto unProyecto = null;
		Tarea unaTarea = null;
			
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT t.id, t.nombre, t.prioridad, t.usuario, t.estado, t.descripcion, t.fecha_inicio, t.fecha_fin, p.id, p.nombre, p.usuario_propietario, p.estado, p.descripcion, p.prioridad, u.usuario, u.contrasena, u.nombre, u.email, u.activo\r\n"
						+ "FROM tareas t join proyectos p on t.id_proyecto = p.id\r\n"
						+ "JOIN proyectos_usuarios_roles pur on pur.id_proyecto = p.id\r\n"
						+ "join usuarios u on pur.nombre_usuario = u.usuario\r\n"
						+ "WHERE t.id_proyecto = ? and u.usuario = p.usuario_propietario");
				statement.setInt(1, id_project);
				ResultSet rs = statement.executeQuery();
				while(rs.next()) {
					unUsuario = new Usuario(rs.getString("u.usuario"), rs.getString("u.contrasena"), rs.getString("u.nombre"), rs.getString("u.email"), rs.getBoolean("u.activo"));
					unProyecto = new Proyecto(rs.getInt("p.id"), rs.getString("p.nombre"), unUsuario, rs.getString("estado"), rs.getString("p.descripcion"), rs.getString("p.prioridad"));
					unaTarea = new Tarea(rs.getInt("t.id"), rs.getString("t.nombre"), unProyecto,rs.getString("t.prioridad"), rs.getString("t.usuario"), rs.getString("t.estado"),
							rs.getString("t.descripcion"), rs.getDate("t.fecha_inicio").toLocalDate(), rs.getDate("t.fecha_fin").toLocalDate());
					
					tareas.add(unaTarea);
				}
			
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage() + "No se pudo consultar las tareas", "Error", JOptionPane.ERROR_MESSAGE);
			}
			 finally {
				ConnectionManager.disconnect();
			}
			return tareas;
		}
		
	

	@Override
	public void remove(int idTarea) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("DELETE FROM tareas WHERE id = ?");
			sent.setInt(1, idTarea);

			int verificacion = sent.executeUpdate();		
			if(verificacion <= 0) {
				
			}
		} catch (SQLException e) {

		} finally {
		ConnectionManager.disconnect();
		}
	}
	

	public Tarea find(int idTarea) throws NotNullException, DataEmptyException, InvalidDateException {

		Usuario unUsuario = null;
		Proyecto unProyecto = null;
		Tarea unaTarea = null;
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT t.id, t.nombre, t.prioridad, t.usuario, t.estado, t.descripcion, t.fecha_inicio, t.fecha_fin, p.id, p.nombre, p.usuario_propietario, p.estado, p.descripcion, p.prioridad, u.usuario, u.contrasena, u.nombre, u.email, u.activo\r\n"
					+ "FROM tareas t join proyectos p on t.id_proyecto = p.id\r\n"
					+ "JOIN proyectos_usuarios_roles pur on pur.id_proyecto = p.id\r\n"
					+ "join usuarios u on pur.nombre_usuario = u.usuario\r\n"
					+ "WHERE t.id = ? AND t.estado NOT LIKE '#%'");
			statement.setInt(1, idTarea);
			
			ResultSet rs = statement.executeQuery();

			if(rs.next()) {
				unUsuario = new Usuario(rs.getString("u.usuario"), rs.getString("u.contrasena"), rs.getString("u.nombre"), rs.getString("u.email"), rs.getBoolean("u.activo"));
				unProyecto = new Proyecto(rs.getInt("p.id"), rs.getString("p.nombre"), unUsuario, rs.getString("estado"), rs.getString("p.descripcion"), rs.getString("p.prioridad"));
				unaTarea = new Tarea(rs.getInt("id"), rs.getString("nombre"), unProyecto, rs.getString("prioridad"), rs.getString("usuario"), rs.getString("estado"),rs.getString("descripcion"), rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate());
			}else {
				 JOptionPane.showMessageDialog(null, "No se encontró la tarea con ID: " + idTarea, "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage() + "No se pudo crear la tarea", "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally {
			ConnectionManager.disconnect();
		}
		return unaTarea;
	}
}
