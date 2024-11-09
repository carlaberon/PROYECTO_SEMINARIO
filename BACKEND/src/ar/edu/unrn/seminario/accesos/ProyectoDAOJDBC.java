package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.ProjectInsertionError;
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
		
			if ( cant > 0) {
				System.out.println("Proyecto principal insertado."); //lanzar excepcion positivas??
				
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
	                if(miembroInsertado > 0) {
	                	//lanzar excepcion positivas??
	                }
	                memberStatement.close();
	            }
			// TODO: disparar Exception propia
			} else {
				throw new ProjectInsertionError(proyecto.getNombre());
			}
			
		}
		catch (ProjectInsertionError e){
			System.out.println("No se pudo insertar el proyecto" + e.getMessage());	
		}
		catch (SQLException e) {
			System.out.println("Error al actualizar");	
			// TODO: disparar Exception propia
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
			   
			        
			   if (verificacion > 0) {
			            System.out.println("Proyecto actualizado correctamente");
			   } else {
			            System.out.println("No se encontro el proyecto a actualizar");
			   }
			        
			   } catch (SQLException e) {
			       System.out.println("No se pudo actualizar el proyecto. " + e.toString());
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
			if(verificacion == 1) {
				System.out.println("Se elimino el proyecto.");
			} else {
				System.out.println("No se encontro el proyecto para eliminar.");
			}
		} catch (SQLException e) {
			System.out.println("No se pudo eliminar el proyecto. " + e.toString());
		} finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void remove(Proyecto proyecto) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("UPDATE proyectos SET estado = CONCAT('#', estado) WHERE id = ?");
			statement.setInt(1, proyecto.getId());
			
			int verificacion = statement.executeUpdate();		
			if(verificacion == 1) {
				System.out.println("Se elimino el proyecto."); //lanzar excepcion positivas??
			} else {
				System.out.println("No se encontro el proyecto a eliminar");
			}
			
		} catch (SQLException e) {
			System.out.println("No se pudo eliminar el proyecto " + e.toString());
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
			while(rs.next()) {
				Rol unRol = new Rol(rs.getInt("r.codigo"), rs.getString("r.nombre"), rs.getBoolean("r.activo"));
				Usuario usuarioPropietario = new Usuario(rs.getString("u.usuario"), rs.getString("u.contrasena"), rs.getString("u.nombre"), rs.getString("u.email"), unRol, rs.getBoolean("u.activo"));
				encontrarProyecto = new Proyecto(rs.getInt("p.id"),rs.getString("p.nombre"), usuarioPropietario, rs.getString("estado"), rs.getString("p.descripcion"), rs.getString("p.prioridad"));
				
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
			} catch (SQLException e) {
				System.out.println("Error de mySql\n" + e.toString());
				// TODO: disparar Exception propia
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				// TODO: disparar Exception propia
			} finally {
				ConnectionManager.disconnect();
			}
			return proyectos;
	}
	
}
