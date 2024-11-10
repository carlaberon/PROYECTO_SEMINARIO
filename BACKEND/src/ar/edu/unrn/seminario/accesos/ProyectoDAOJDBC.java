package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.EliminationException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.NotUpdateException;
import ar.edu.unrn.seminario.exception.TaskNotUpdatedException;
import ar.edu.unrn.seminario.exception.InsertionException;
import ar.edu.unrn.seminario.exception.NotFoundException;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class ProyectoDAOJDBC implements ProyectoDao{

	@Override
	public void create(Proyecto proyecto) {
		PreparedStatement statement;
		Connection conn;
		try {
			conn = ConnectionManager.getConnection();
			
			statement = conn
					.prepareStatement("INSERT INTO `proyectos` (`nombre`,`usuario_propietario`,`estado`,`descripcion`, `prioridad`,`proyecto`) " + "VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, proyecto.getNombre());
			statement.setObject(2, proyecto.getUsuarioPropietario().getUsername());
			statement.setString(3, proyecto.getEstado());
			statement.setString(4, proyecto.getDescripcion());
			statement.setString(5, proyecto.getPrioridad());
			statement.setNull(6, java.sql.Types.VARCHAR);
			
			
			int cant = statement.executeUpdate();
		
			if (cant > 0) {
				// Obtener el ID generado
	            ResultSet generatedKeys = statement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int proyectoId = generatedKeys.getInt(1);

	                // Insertar el usuario propietario como miembro en `proyectos_miembros`
	                PreparedStatement memberStatement = conn.prepareStatement(
	                    "INSERT INTO `proyectos_miembros` (`id_proyecto`, `usuario_miembro`) VALUES (?, ?)"
	                );
	                memberStatement.setInt(1, proyectoId);
	                memberStatement.setString(2, proyecto.getUsuarioPropietario().getUsername());
	                
	                int miembroInsertado = memberStatement.executeUpdate();
	                if(miembroInsertado == 0) {
	                	throw new InsertionException("La relacion miembro-proyecto");
	                }
	                memberStatement.close();
	            }
			} else {
				throw new InsertionException("El proyecto" + proyecto.getNombre());
			}
			
		}
		catch (InsertionException e1){
			JOptionPane.showMessageDialog(null, e1.getMessage() + " no se creó.", "Error", JOptionPane.ERROR_MESSAGE);	
		}
		catch (SQLException e2) {
			JOptionPane.showMessageDialog(null,"Error de SQL: " + e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		 finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void update(Proyecto proyecto) {
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
			            throw new NotUpdateException("el proyecto" + proyecto.getNombre());
			    
		       } catch (NotUpdateException e1) {
			   	   JOptionPane.showMessageDialog(null, e1.getMessage() + "no se actualizo.", "Error", JOptionPane.ERROR_MESSAGE);
			   } catch (SQLException e2) {
				   JOptionPane.showMessageDialog(null,"Error de SQL: " + e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			   } finally {
			       ConnectionManager.disconnect();
			   }
	}
	

	@Override
	public void remove(int id) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("UPDATE proyectos SET estado = CONCAT('#', estado) WHERE id = ?");
			statement.setInt(1, id);
			
			
			int verificacion = statement.executeUpdate();		
			if(verificacion == 0) 
				throw new EliminationException("No se pudo eliminar el proyecto.");
		} catch (EliminationException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null,"Error de SQL: " + e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public Proyecto find(int id) throws NotNullException, DataEmptyException {
		Proyecto encontrarProyecto = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT p.id, p.nombre, p.usuario_propietario, p.estado, p.descripcion, p.prioridad, u.usuario, u.contrasena, u.nombre, u.email, u.activo, u.rol, r.codigo, r.nombre, r.activo\r\n" 
					+ "FROM proyectos p\r\n"
					+ "JOIN proyectos_miembros pm ON p.id = pm.id_proyecto\r\n"
					+ "JOIN usuarios u ON pm.usuario_miembro = u.usuario\r\n"
					+ "JOIN usuario_rol ur ON u.rol = ur.codigo_rol\r\n"
					+ "JOIN roles r ON ur.codigo_rol = r.codigo\r\n"
					+ "WHERE p.id = ? and p.estado NOT LIKE '#%' AND p.usuario_propietario = u.usuario\r\n");
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			// Si no se obtiene ninguna fila
	        if (!rs.next()) {
	            // Lanzamos una excepción personalizada cuando no se encuentra el proyecto
	            throw new NotFoundException("Proyecto no encontrado para el ID: " + id);
	        }
	        
	        // Si se encuentra una fila se crea el objeto Proyecto por completo
			Rol unRol = new Rol(rs.getInt("r.codigo"), rs.getString("r.nombre"), rs.getBoolean("r.activo"));
			Usuario usuarioPropietario = new Usuario(rs.getString("u.usuario"), rs.getString("u.contrasena"), rs.getString("u.nombre"), rs.getString("u.email"), unRol, rs.getBoolean("u.activo"));
			encontrarProyecto = new Proyecto(rs.getInt("p.id"),rs.getString("p.nombre"), usuarioPropietario, rs.getString("estado"), rs.getString("p.descripcion"), rs.getString("p.prioridad"));
				
		} catch(NotFoundException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null,"Error de SQL: " + e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConnectionManager.disconnect();
		}
		return encontrarProyecto;
	}

	@Override
	public List<Proyecto> findAll(String usuario) throws NotNullException, DataEmptyException {
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		Proyecto unProyecto = null;
			
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT p.id, p.nombre, p.usuario_propietario, p.estado, p.descripcion, p.prioridad, u.usuario, u.contrasena, u.nombre, u.email, u.activo, u.rol, r.codigo, r.nombre, r.activo\r\n" 
						+ "FROM proyectos p\r\n"
						+ "JOIN proyectos_miembros pm ON p.id = pm.id_proyecto\r\n"
						+ "JOIN usuarios u ON pm.usuario_miembro = u.usuario\r\n"
						+ "JOIN usuario_rol ur ON u.rol = ur.codigo_rol\r\n"
						+ "JOIN roles r ON ur.codigo_rol = r.codigo\r\n"
						+ "WHERE p.estado NOT LIKE '#%' AND p.usuario_propietario = ? and u.usuario = ? \r\n");
				
				statement.setString(1, usuario);
				statement.setString(2, usuario);
				ResultSet rs = statement.executeQuery();
				while(rs.next()) {
					Rol unRol = new Rol(rs.getInt("r.codigo"), rs.getString("r.nombre"), rs.getBoolean("r.activo"));
					Usuario usuarioPropietario = new Usuario(rs.getString("u.usuario"), rs.getString("u.contrasena"), rs.getString("u.nombre"), rs.getString("u.email"), unRol, rs.getBoolean("u.activo"));
					unProyecto = new Proyecto(rs.getInt("p.id"), rs.getString("p.nombre"), usuarioPropietario, rs.getString("estado"), rs.getString("p.descripcion"), rs.getString("p.prioridad"));
					
					proyectos.add(unProyecto);
				}
				
				if (proyectos.isEmpty()) {
		            throw new NotFoundException("No se encontraron proyectos para el usuario: ");
		        }
			} catch(NotFoundException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e2) {
				JOptionPane.showMessageDialog(null,"Error de SQL: " + e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				ConnectionManager.disconnect();
			}
			return proyectos;
	}
	
}
