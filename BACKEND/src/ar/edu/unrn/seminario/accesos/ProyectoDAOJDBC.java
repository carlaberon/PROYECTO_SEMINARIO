package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Proyecto;

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
			System.out.println("Error al insertar un usuario");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void update(Proyecto Usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Proyecto Usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Proyecto find(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Proyecto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
