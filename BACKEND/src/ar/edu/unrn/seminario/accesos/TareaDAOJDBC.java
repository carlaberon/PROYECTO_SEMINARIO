package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Statement;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.TaskNotUpdatedException;
import ar.edu.unrn.seminario.modelo.Tarea;

public class TareaDAOJDBC implements TareaDao{

	@Override
	public void create(Tarea tarea) {
		PreparedStatement statement;
		Connection conn;
		try {
			conn = ConnectionManager.getConnection();
			
			statement = conn
					.prepareStatement("INSERT INTO tareas(nombre, proyecto,usuario_propietario, prioridad, usuario, estado, descripcion, fecha_inicio, fecha_fin)" + "VALUES(?, ?, ?,?, ?, ?, ?, ?, ?)");
		
			statement.setString(1, tarea.getNombre());
			statement.setString(2, tarea.getProyecto());
			statement.setString(3, tarea.getUsuarioPropietario());
			statement.setString(4, tarea.getPrioridad());
			statement.setString(5, tarea.getUsuario());
			statement.setBoolean(6, tarea.isEstado());
			statement.setString(7, tarea.getDescripcion());
			statement.setDate(8, java.sql.Date.valueOf(tarea.getInicio()));
			statement.setDate(9, java.sql.Date.valueOf(tarea.getFin()));
			
			int cant = statement.executeUpdate();
		
			if ( cant > 0) {
			
			System.out.println("Modificando " + cant + " registros");
			
			// TODO: disparar Exception propia
			}
		else {
			System.out.println("Error al actualizar");
		}
			
		}
		catch (SQLException e) {
			System.out.println("Error al actualizar");	
			// TODO: disparar Exception propia
			}
		catch (Exception e) {
			System.out.println("Error al insertar un usuario");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}

		
	}
		


	@Override
	public void update(Tarea tarea, int idProyectoOriginal) throws TaskNotUpdatedException {
		try {
		   Connection conn = ConnectionManager.getConnection();
		   PreparedStatement statement = conn.prepareStatement("UPDATE tareas SET nombre = ?, prioridad = ?, usuario = ?, estado = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ? " +
		           "WHERE id = ? AND id_proyecto = ?");
		        
		   // Establecer los valores de los nuevos datos de la tarea
		   statement.setString(1, tarea.getNombre());
		   statement.setString(2, tarea.getPrioridad());
		   statement.setString(3, tarea.getUsuario());
		   statement.setBoolean(4, tarea.isEstado());
		   statement.setString(5, tarea.getDescripcion());
		   statement.setDate(6, Date.valueOf(tarea.getInicio()));
	       statement.setDate(7, Date.valueOf(tarea.getFin()));
		   
	       // Parametros de busqueda en la base de datos
	        statement.setInt(8, tarea.getId());
	        statement.setInt(9, idProyectoOriginal);
	       
		   int verificacion = statement.executeUpdate();
		        
		   if (verificacion == 0) {
			   throw new TaskNotUpdatedException("No se encontro la tarea para actualizar.");
		   }
		        
		   } catch (SQLException e) {
		       System.out.println("No se pudo actualizar la tarea. " + e.toString());
		   } finally {
		       ConnectionManager.disconnect();
		   }
	}
		
	

	@Override
	public void remove(String nombre, String proyecto, String usuario_propietario) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("DELETE FROM tareas WHERE nombre=? AND proyecto=? AND usuario_propietario=?");
			sent.setString(1, nombre);
			sent.setString(2, proyecto);
			sent.setString(3, usuario_propietario);
			
			int verificacion = sent.executeUpdate();		
			if(verificacion == 1) {
				System.out.println("Se elimino la tarea.");
			}
		} catch (SQLException e) {
			System.out.println("No se pudo eliminar la tarea. " + e.toString());
		} finally {
		ConnectionManager.disconnect();
		}
	}

	@Override
	public void remove(Tarea tarea) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("DELETE FROM tareas WHERE nombre=? AND proyecto=? AND usuario_propietario=?");
			sent.setString(1, tarea.getNombre());
			sent.setString(2, tarea.getProyecto());
			sent.setString(3, tarea.getUsuarioPropietario());
			
			int verificacion = sent.executeUpdate();		
			if(verificacion == 1) {
				System.out.println("Se elimino la tarea.");
			}
		} catch (SQLException e) {
			System.out.println("No se pudo eliminar la tarea. " + e.toString());
		} finally {
		ConnectionManager.disconnect();
		}
	}			

	@Override
	public Tarea find(int id) throws DataEmptyException, NotNullException, InvalidDateException {
		Tarea encontrarTarea = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT * FROM tareas WHERE id = ?");
			
			sent.setInt(1, id);
			
			ResultSet result = sent.executeQuery();
			while(result.next()) {
				encontrarTarea = new Tarea(result.getString("nombre"), result.getString("proyecto"), 
				result.getString("usuario_propietario"), result.getString("prioridad"), result.getString("usuario"), result.getBoolean("estado"), 
				result.getString("descripcion"), result.getDate("fecha_inicio").toLocalDate(), result.getDate("fecha_fin").toLocalDate());
			}
		} catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
			// TODO: disparar Exception propia
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
		return encontrarTarea;
	}

	@Override
	public List<Tarea> findAll() throws DataEmptyException, NotNullException, InvalidDateException {
		List<Tarea>tareas = new ArrayList<Tarea>();
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT t.id, t.nombre, t.proyecto, t.usuario_propietario, t.prioridad, t.usuario, t.estado, t.descripcion, t.fecha_inicio, t.fecha_fin "+"FROM tareas t");
			
			while (rs.next()) {
				Tarea tarea = new Tarea(rs.getString("nombre"), rs.getString("proyecto"),rs.getString("usuario_propietario"), rs.getString("prioridad"), rs.getString("usuario"), rs.getBoolean("estado"),rs.getString("descripcion"), rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate());
				tareas.add(tarea);
			}
		} catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
			// TODO: disparar Exception propia
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}

		return tareas;
		
	
	}



	@Override
	public List<Tarea> findTareas(String proyecto, String usuario_propietario)
			throws DataEmptyException, NotNullException, InvalidDateException {
		List<Tarea>tareas = new ArrayList<Tarea>();
		Tarea unaTarea = null;
			
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT nombre, proyecto, usuario_propietario, prioridad, usuario, estado, descripcion, fecha_inicio, fecha_fin FROM tareas WHERE proyecto = ? AND usuario_propietario = ?");
				statement.setString(1, proyecto);
				statement.setString(2, usuario_propietario);
				ResultSet rs = statement.executeQuery();
				while(rs.next()) {
					unaTarea = new Tarea(rs.getString("nombre"), rs.getString("proyecto"),rs.getString("usuario_propietario"), rs.getString("prioridad"), rs.getString("usuario"), rs.getBoolean("estado"),rs.getString("descripcion"), rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate());
					tareas.add(unaTarea);
				}
			} catch (SQLException e) {
				System.out.println("Error de mySql\n" + e.toString());
				// TODO: disparar Exception propia
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				// TODO: disparar Exception propia
			} finally {
				ConnectionManager.disconnect();
			}
			return tareas;
		}
}
