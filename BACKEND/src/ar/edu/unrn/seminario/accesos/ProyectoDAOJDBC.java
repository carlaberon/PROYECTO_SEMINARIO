package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Proyecto;

public class ProyectoDAOJDBC implements ProyectoDao{

	@Override
	public void create(Proyecto Usuario) {
		PreparedStatement statement;
		Connection conn;
		try {
			conn = ConnectionManager.getConnection();
			
			statement = conn
					.prepareStatement("INSERT INTO tareas(nombre, proyecto, prioridad, usuario, estado, descripcion, fecha_inicio, fecha_fin)" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
		
			statement.setString(1, tarea.getNombre());
			statement.setString(2, tarea.getProyecto());
			statement.setString(3, tarea.getPrioridad());
			statement.setString(4, tarea.getUsuario());
			statement.setBoolean(5, tarea.isEstado());
			statement.setString(6, tarea.getDescripcion());
			statement.setDate(7, java.sql.Date.valueOf(tarea.getInicio().toLocalDate()));
			statement.setDate(8, java.sql.Date.valueOf(tarea.getFin().toLocalDate()));
			
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
