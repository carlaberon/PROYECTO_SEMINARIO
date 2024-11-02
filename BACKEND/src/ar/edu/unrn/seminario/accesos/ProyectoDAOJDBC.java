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
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Tarea;
import ar.edu.unrn.seminario.modelo.Usuario;

public class ProyectoDAOJDBC implements ProyectoDao{

	@Override
	public void create(Proyecto proyecto) {
		PreparedStatement statement;
		Connection conn;
		try {
			conn = ConnectionManager.getConnection();
			
			statement = conn
					.prepareStatement("INSERT INTO `proyectos` (`nombre`,`usuario_propietario`,`estado`,`descripcion`, `prioridad`,`proyecto`) " + "VALUES(?, ?, ?, ?, ?, ?)");
		
			statement.setString(1, proyecto.getNombre());
			statement.setObject(2, proyecto.getUsuarioPropietario().getUsername());
			statement.setBoolean(3, proyecto.getEstado());
			statement.setString(4, proyecto.getDescripcion());
			statement.setString(5, proyecto.getPrioridad1());
			statement.setNull(6, java.sql.Types.VARCHAR);
			
			
			int cant = statement.executeUpdate();
		
			if ( cant > 0) {
			
				System.out.println("Proyecto principal insertado.");
			
			// TODO: disparar Exception propia
			} else {
				System.out.println("Error al actualizar");
			}
			
			//Se inserta cada proyecto
			for(Proyecto subProyecto : proyecto.getProyectos()) {
				statement = conn.
						prepareStatement("INSERT INTO `proyectos` (`nombre`, `usuario_propietario`, `estado`, `descripcion`, `prioridad`, `proyecto`) " + "VALUES (?, ?, ?, ?, ?, ?)");
				
				statement.setString(1, subProyecto.getNombre());
				statement.setString(2, subProyecto.getUsuarioPropietario().getUsername());
				statement.setBoolean(3, subProyecto.getEstado());
				statement.setString(4, subProyecto.getDescripcion());
				statement.setString(5, subProyecto.getPrioridad1());
				statement.setString(6, proyecto.getNombre()); //se vincula al proyecto principal
				
				int subCant = statement.executeUpdate();
				if (subCant > 0) {
					System.out.println("Subproyecto insertado: " + subProyecto.getNombre());
				}
			}
			
		}
		catch (SQLException e) {
			System.out.println("Error al actualizar");	
			// TODO: disparar Exception propia
			}
		catch (Exception e) {
			System.out.println("Error al insertar el proyecto");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void update(Proyecto proyecto) {
		try {
			   Connection conn = ConnectionManager.getConnection();
			   PreparedStatement statement = conn.prepareStatement("UPDATE proyectos SET nombre=?, prioridad=?, usuario_propietario=?, estado=?, descripcion=? WHERE nombre=? AND usuario_propietario=?");
			        
			   // Establecer los valores de los nuevos datos del proyecto
			   statement.setString(1, proyecto.getNombre());
			   statement.setString(2, proyecto.getPrioridad1());
			   statement.setString(3, proyecto.getUsuarioPropietario().getUsername());
			   statement.setBoolean(4, proyecto.getEstado());
			   statement.setString(5, proyecto.getDescripcion());
			   
			   //para identificar el proyecto a actualizar
			   statement.setString(6, proyecto.getNombre());
			   statement.setString(7, proyecto.getUsuarioPropietario().getUsername());
			        
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
	public void remove(String nombre, String usuario_propietario) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("DELETE FROM proyectos WHERE nombre = ? AND usuario_propietario = ?");
			statement.setString(1, nombre);
			statement.setString(2, usuario_propietario);
			
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
			PreparedStatement statement = conn.prepareStatement("DELETE FROM proyectos WHERE nombre = ? AND usuario_propietario = ?");
			statement.setString(1, proyecto.getNombre());
			statement.setString(2, proyecto.getUsuarioPropietario().getUsername());
			
			int verificacion = statement.executeUpdate();		
			if(verificacion == 1) {
				System.out.println("Se elimino el proyecto.");
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
	public Proyecto find(String nombre, String usuario_propietario) throws NotNullException, DataEmptyException {
		Proyecto encontrarProyecto = null;
		UsuarioDao usuarioDao = new UsuarioDAOJDBC();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT nombre, usuario_propietario, estado, descripcion, prioridad FROM proyectos WHERE nombre = ? AND usuario_propietario = ?");
			statement.setString(1, nombre);
			statement.setString(2, usuario_propietario);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				String nombreUsuarioPropietario = rs.getString("usuario_propietario");
				Usuario usuarioPropietario = usuarioDao.find(nombreUsuarioPropietario);
				encontrarProyecto = new Proyecto(rs.getString("nombre"), usuarioPropietario, "FINALIZADO".equals(rs.getString("estado")), rs.getString("descripcion"), rs.getString("prioridad"));
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
	public List<Proyecto> findAll() throws NotNullException, DataEmptyException{
		List<Proyecto>proyectos = new ArrayList<Proyecto>();
		UsuarioDao usuarioDao = new UsuarioDAOJDBC();
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT p.nombre, p.usuario_propietario, p.estado, p.descripcion, p.prioridad, p.proyecto FROM proyectos p");
			
			while (rs.next()) {
				String nombreUsuarioPropietario = rs.getString("usuario_propietario");
				
				Usuario usuarioPropietario = usuarioDao.find(nombreUsuarioPropietario);
				
				Proyecto proyecto;
				proyecto = new Proyecto(rs.getString("nombre"), usuarioPropietario, rs.getBoolean("estado"), rs.getString("descripcion"), rs.getString("prioridad"));
				proyectos.add(proyecto);
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

	@Override
	public List<Proyecto> findAll(String usuario) throws NotNullException, DataEmptyException {
		UsuarioDao usuarioDao = new UsuarioDAOJDBC();
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		Proyecto unProyecto = null;
			
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT p.nombre, p.usuario_propietario, p.estado, p.descripcion, p.prioridad, p.proyecto FROM proyectos p WHERE p.usuario_propietario = ?");
				statement.setString(1, usuario);

				ResultSet rs = statement.executeQuery();
				while(rs.next()) {
					String nombreUsuarioPropietario = rs.getString("usuario_propietario");
					
					Usuario usuarioPropietario = usuarioDao.find(nombreUsuarioPropietario);
					
					unProyecto = new Proyecto(rs.getString("nombre"), usuarioPropietario, rs.getBoolean("estado"), rs.getString("descripcion"), rs.getString("prioridad"));
					
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
