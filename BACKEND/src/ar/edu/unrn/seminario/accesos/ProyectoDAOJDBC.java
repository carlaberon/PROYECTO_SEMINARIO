package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataBaseEliminationException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.DataBaseUpdateException;
import ar.edu.unrn.seminario.exception.DataBaseInsertionException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Usuario;

public class ProyectoDAOJDBC implements ProyectoDao{

	@Override
	public void create(Proyecto proyecto) throws DataBaseInsertionException, DataBaseConnectionException {
		PreparedStatement statement;
		Connection conn;
		try {
			conn = ConnectionManager.getConnection();
			
			statement = conn
					.prepareStatement("INSERT INTO `proyectos` (`nombre`,`usuario_propietario`,`estado`,`descripcion`, `prioridad`) " + "VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, proyecto.getNombre());
			statement.setObject(2, proyecto.getUsuarioPropietario().getUsername());
			statement.setString(3, proyecto.getEstado());
			statement.setString(4, proyecto.getDescripcion());
			statement.setString(5, proyecto.getPrioridad());
			
			int cant = statement.executeUpdate();
		
			if (cant > 0) {
				// Obtener el ID generado
	            ResultSet generatedKeys = statement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int proyectoId = generatedKeys.getInt(1);

	                // Insertar el usuario propietario como miembro en `proyectos_miembros`
	                PreparedStatement memberStatement = conn.prepareStatement(
	                		"INSERT INTO `proyectos_usuarios_roles` (`codigo_rol`,`nombre_usuario`,`id_proyecto`) " + "VALUES(1, ?, ?)"
	                );
	              
	                memberStatement.setString(1, proyecto.getUsuarioPropietario().getUsername());
	                memberStatement.setInt(2, proyectoId);
	                
	                int miembroInsertado = memberStatement.executeUpdate();
	                if(miembroInsertado == 0) {
	                	//Si no se crea la relacion
	                	throw new DataBaseInsertionException("exceptionDAO.create");
	                }
	                memberStatement.close();
	            }
			} else {
				throw new DataBaseInsertionException("exceptionDAO.create");
			}
				
		} catch (SQLException e2) {
			throw new DataBaseConnectionException("exceptionDAO.conecction");
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
	public void update(Proyecto proyecto) throws DataBaseConnectionException  {
		try {
			   Connection conn = ConnectionManager.getConnection();
			   PreparedStatement statement = conn.prepareStatement("UPDATE proyectos SET nombre=?, prioridad=?, descripcion=? WHERE id = ?");
			        
			   // Establecer los valores de los nuevos datos del proyecto
			   statement.setString(1, proyecto.getNombre());
			   statement.setString(2, proyecto.getPrioridad());
			   statement.setString(3, proyecto.getDescripcion());
			   
			   //para identificar el proyecto a actualizar
			   statement.setInt(4, proyecto.getId()); //El proyecto pasado por parametro deberia contener la id del proyecto a actualizar
	  
			   int verificacion = statement.executeUpdate();
			   
			        
			   if (verificacion == 0) 
			            throw new DataBaseUpdateException("el proyecto" + proyecto.getNombre());
			    
			} catch (SQLException e2) {
				throw new DataBaseConnectionException("exceptionDAO.conecction");
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
	public void remove(int idProyecto) throws DataBaseConnectionException  {
		Connection conn = null;
	    PreparedStatement stmtTareas = null;
	    PreparedStatement stmtRelaciones = null;
	    PreparedStatement stmtProyecto = null;

	    try {
	    	conn = ConnectionManager.getConnection();
	        
	        //Eliminar tareas asociadas
	        String deleteTareas = "DELETE FROM tareas WHERE id_proyecto = ?";
	        stmtTareas = conn.prepareStatement(deleteTareas);
	        stmtTareas.setInt(1, idProyecto);
	        stmtTareas.executeUpdate();

	        //Eliminar relaciones en proyectos_roles_usuarios
	        String deleteRelaciones = "DELETE FROM proyectos_usuarios_roles WHERE id_proyecto = ?";
	        stmtRelaciones = conn.prepareStatement(deleteRelaciones);
	        stmtRelaciones.setInt(1, idProyecto);
	        stmtRelaciones.executeUpdate();

	        //Eliminar el proyecto
	        String deleteProyecto = "DELETE FROM proyectos WHERE id = ?";
	        stmtProyecto = conn.prepareStatement(deleteProyecto);
	        stmtProyecto.setInt(1, idProyecto);
	        stmtProyecto.executeUpdate();

	        

	    } catch (SQLException e2) {
			throw new DataBaseConnectionException("exceptionDAO.conecction");
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
	public void deleteMember(String username, int idProyecto) throws DataBaseConnectionException  {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("DELETE FROM proyectos_usuarios_roles WHERE id_proyecto = ? and "
					+ "nombre_usuario = ?\r\n");
			
			// Establecer los valores de los nuevos datos del proyecto
			statement.setInt(1, idProyecto);
			statement.setString(2, username);
			
			
			int verificacion = statement.executeUpdate();
			
			
			if (verificacion == 0) 
				throw new DataBaseUpdateException("el usuario" + username);
			
		} catch (SQLException e2) {
			throw new DataBaseConnectionException("exceptionDAO.conecction");
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
	public Proyecto find(int idProyecto) throws NotNullException, DataEmptyException, DataBaseConnectionException {
		Proyecto encontrarProyecto = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT p.id, p.nombre, p.usuario_propietario, p.estado, p.descripcion, p.prioridad, u.usuario, u.contrasena, u.nombre, u.email, u.activo\r\n" 
					+ "FROM proyectos p\r\n"
					+ "JOIN proyectos_usuarios_roles pur ON p.id = pur.id_proyecto\r\n"
					+ "JOIN usuarios u ON pur.nombre_usuario = u.usuario\r\n"
					+ "WHERE p.id = ? and p.usuario_propietario = u.usuario\r\n");
			statement.setInt(1, idProyecto);
			
			ResultSet rs = statement.executeQuery();
			// Si no se obtiene ninguna fila
	        if (!rs.next()) {
	            // Lanzamos una excepción personalizada cuando no se encuentra el proyecto
	            throw new DataBaseFoundException("Proyecto no encontrado para el ID: " + idProyecto);
	        }
	        
	        // Si se encuentra una fila se crea el objeto Proyecto por completo
			Usuario usuarioPropietario = new Usuario(rs.getString("u.usuario"), rs.getString("u.contrasena"), rs.getString("u.nombre"), rs.getString("u.email"), rs.getBoolean("u.activo"));
			encontrarProyecto = new Proyecto(rs.getInt("p.id"),rs.getString("p.nombre"), usuarioPropietario, rs.getString("estado"), rs.getString("p.descripcion"), rs.getString("p.prioridad"));
				
		} catch (SQLException e2) {
			throw new DataBaseConnectionException("exceptionDAO.conecction");
		}
		 finally {
				try {
					ConnectionManager.disconnect();
				} catch (SQLException e) {
					throw new DataBaseConnectionException("exceptionDAO.disconnect");
				}
		}
		return encontrarProyecto;
	}

	@Override
	public List<Proyecto> findAll(String usuario) throws NotNullException, DataEmptyException, DataBaseConnectionException {
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		Proyecto unProyecto = null;
			
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT p.id, p.nombre, p.usuario_propietario, p.estado, p.descripcion, p.prioridad\r\n" 
						+ "FROM proyectos p\r\n"
						+ "JOIN proyectos_usuarios_roles pur ON p.id = pur.id_proyecto\r\n"
						+ "WHERE pur.nombre_usuario = ?\r\n");
				
				statement.setString(1, usuario);
				
				ResultSet rs = statement.executeQuery();
				
				while(rs.next()) {
					PreparedStatement statement2 = conn.prepareStatement("SELECT u.usuario, u.contrasena, u.nombre, u.email, u.activo\r\n" 
							+ "FROM usuarios u\r\n"
							+ "WHERE u.usuario = ?\r\n");
					
					statement2.setString(1, rs.getString("p.usuario_propietario"));
					ResultSet rs2 = statement2.executeQuery(); //Busqueda propietario
					rs2.next();
					
					Usuario usuarioPropietario = new Usuario(rs2.getString("u.usuario"), rs2.getString("u.contrasena"), rs2.getString("u.nombre"), rs2.getString("u.email"));
					unProyecto = new Proyecto(rs.getInt("p.id"), rs.getString("p.nombre"), usuarioPropietario, rs.getString("estado"), rs.getString("p.descripcion"), rs.getString("p.prioridad"));
					
					proyectos.add(unProyecto);
				}
				
			} catch (SQLException e2) {
				throw new DataBaseConnectionException("exceptionDAO.conecction");
			}
			 finally {
					try {
						ConnectionManager.disconnect();
					} catch (SQLException e) {
						throw new DataBaseConnectionException("exceptionDAO.disconnect");
					}
			}
			return proyectos;
	}

	@Override
	public void inviteMember(String username, int idProyecto, int codigoRol) throws DataBaseConnectionException  {
		try {
			   Connection conn = ConnectionManager.getConnection();
			   PreparedStatement statement = conn.prepareStatement("INSERT INTO `proyectos_usuarios_roles` (`codigo_rol`,`nombre_usuario`,`id_proyecto`) " + "VALUES(?, ?, ?)" );
			        
			   // Establecer los valores de los nuevos datos del proyecto
			   statement.setInt(1, codigoRol);
			   statement.setString(2, username);
			   statement.setInt(3, idProyecto);
			   
	  
			   int verificacion = statement.executeUpdate();
			   
			        
			   if (verificacion == 0) 
			            throw new DataBaseUpdateException("No se pudo realizar la invitación a: " + username);
			    
			} catch (SQLException e2) {
				throw new DataBaseConnectionException("exceptionDAO.conecction");
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
	public List<Usuario> findAllMembers(int proyectoId) throws DataBaseConnectionException {
		List<Usuario> miembros = new ArrayList<Usuario>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT u.usuario, u.contrasena, u.nombre, u.email, u.activo\r\n" 
					+ "FROM usuarios u\r\n"
					+ "JOIN proyectos_usuarios_roles pur ON pur.nombre_usuario = u.usuario\r\n"
					+ "WHERE pur.id_proyecto = ?\r\n");
			
			statement.setInt(1, proyectoId);
			//statement.setString(2, usuario);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Usuario miembro = new Usuario(rs.getString("u.usuario"), rs.getString("u.contrasena"), rs.getString("u.nombre"), rs.getString("u.email"));
				
				miembros.add(miembro);
			}
			
			if (miembros.isEmpty()) {
	            throw new DataBaseFoundException("El proyecto no tiene miembros.");
	        }
		} catch (SQLException e2) {
			throw new DataBaseConnectionException("exceptionDAO.conecction");
		}
		 finally {
				try {
					ConnectionManager.disconnect();
				} catch (SQLException e) {
					throw new DataBaseConnectionException("exceptionDAO.disconnect");
				}
		}
		return miembros;
	}

	
}


