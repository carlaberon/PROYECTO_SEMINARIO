package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.TaskNotUpdatedException;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Rol;
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
		
			if ( cant > 0) {
			
			System.out.println("Modificando " + cant + " registros");
			
			// TODO: disparar Exception propia #!
			}
		else {
			System.out.println("Error al actualizar$");
		}
			
		}
		catch (SQLException e) {
			System.out.println("Error al actualizar: " + e.getMessage() );	
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
	public void update(Tarea tarea) throws TaskNotUpdatedException {
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
		        
		   if (verificacion == 0) {
			   throw new TaskNotUpdatedException("No se encontro la tarea para actualizar.");
		   }
		        
		   } catch (SQLException e) {
		       System.out.println("No se pudo actualizar la tarea. " + e.toString());
		       //TODO hacer exception propia
		   } finally {
		       ConnectionManager.disconnect();
		   }
	}
	
	@Override
	public List<Tarea> findByProject(int id_project) throws DataEmptyException, NotNullException, InvalidDateException {
		List<Tarea>tareas = new ArrayList<Tarea>();
		Rol unRol = null;
		Usuario unUsuario = null;
		Proyecto unProyecto = null;
		Tarea unaTarea = null;
			
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT t.id, t.nombre, t.prioridad, t.usuario, t.estado, t.descripcion, t.fecha_inicio, t.fecha_fin, p.id, p.nombre, p.usuario_propietario, p.estado, p.descripcion, p.prioridad, u.usuario, u.contrasena, u.nombre, u.email, u.activo, u.rol, r.codigo, r.nombre, r.activo\r\n"
						+ "FROM tareas t join proyectos p on t.id_proyecto = p.id\r\n"
						+ "join proyectos_miembros pm on pm.id_proyecto = p.id\r\n"
						+ "join usuarios u on pm.usuario_miembro = u.usuario\r\n"
						+ "join usuario_rol ur on ur.nombre_usuario = u.usuario\r\n"
						+ "join roles r on r.codigo = ur.codigo_rol\r\n"
						+ "WHERE t.id_proyecto = ? AND t.nombre NOT LIKE '#%' AND u.usuario = p.usuario_propietario");
				statement.setInt(1, id_project);
				ResultSet rs = statement.executeQuery();
				while(rs.next()) {
					unRol = new Rol(rs.getInt("r.codigo"), rs.getString("r.nombre"), rs.getBoolean("r.activo"));
					unUsuario = new Usuario(rs.getString("u.usuario"), rs.getString("u.contrasena"), rs.getString("u.nombre"), rs.getString("u.email"), unRol, rs.getBoolean("u.activo"));
					unProyecto = new Proyecto(rs.getInt("p.id"), rs.getString("p.nombre"), unUsuario, rs.getString("estado"), rs.getString("p.descripcion"), rs.getString("p.prioridad"));
					unaTarea = new Tarea(rs.getInt("t.id"), rs.getString("t.nombre"), unProyecto,rs.getString("t.prioridad"), rs.getString("t.usuario"), rs.getString("t.estado"),
							rs.getString("t.descripcion"), rs.getDate("t.fecha_inicio").toLocalDate(), rs.getDate("t.fecha_fin").toLocalDate());
					
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
		
	

	@Override
	public void remove(int id) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("UPDATE tareas SET nombre = CONCAT('#', nombre) WHERE id = ?");
			sent.setInt(1, id);

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
	
	public Tarea find(int id) throws NotNullException, DataEmptyException, InvalidDateException {
		Rol unRol = null;
		Usuario unUsuario = null;
		Proyecto unProyecto = null;
		Tarea unaTarea = null;
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT t.id, t.nombre, t.prioridad, t.usuario, t.estado, t.descripcion, t.fecha_inicio, t.fecha_fin, p.id, p.nombre, p.usuario_propietario, p.estado, p.descripcion, p.prioridad, u.usuario, u.contrasena, u.nombre, u.email, u.activo, u.rol, r.codigo, r.nombre, r.activo\r\n"
					+ "FROM tareas t join proyectos p on t.id_proyecto = p.id\r\n"
					+ "join proyectos_miembros pm on pm.id_proyecto = p.id\r\n"
					+ "join usuarios u on pm.usuario_miembro = u.usuario\r\n"
					+ "join usuario_rol ur on ur.nombre_usuario = u.usuario\r\n"
					+ "join roles r on r.codigo = ur.codigo_rol\r\n"
					+ "WHERE t.id = ?");
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				unRol = new Rol(rs.getInt("r.codigo"), rs.getString("r.nombre"), rs.getBoolean("r.activo"));
				unUsuario = new Usuario(rs.getString("u.usuario"), rs.getString("u.contrasena"), rs.getString("u.nombre"), rs.getString("u.email"), unRol, rs.getBoolean("u.activo"));
				unProyecto = new Proyecto(rs.getInt("p.id"), rs.getString("p.nombre"), unUsuario, rs.getString("estado"), rs.getString("p.descripcion"), rs.getString("p.prioridad"));
				unaTarea = new Tarea(rs.getInt("id"), rs.getString("nombre"), unProyecto, rs.getString("prioridad"), rs.getString("usuario"), rs.getString("estado"),rs.getString("descripcion"), rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate());
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
		return unaTarea;
	}
	
	
/*
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
*/

/*	@Override
	public List<Tarea> findAll() throws DataEmptyException, NotNullException, InvalidDateException {
		List<Tarea>tareas = new ArrayList<Tarea>();
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT t.id, t.nombre, t.proyecto, t.usuario_propietario, t.prioridad, t.usuario, t.estado, t.descripcion, t.fecha_inicio, t.fecha_fin "+"FROM tareas t");
			
			while (rs.next()) {
				//Tarea tarea = new Tarea(rs.getString("nombre"), rs.getString("proyecto"),rs.getString("usuario_propietario"), rs.getString("prioridad"), rs.getString("usuario"), rs.getBoolean("estado"),rs.getString("descripcion"), rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate());
				//tareas.add(tarea);
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
		
	
	}*/




}
