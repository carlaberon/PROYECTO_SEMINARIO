package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Statement;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Tarea;

public class TareaDAOJDBC implements TareaDao{

	@Override
	public void create(Tarea tarea) {
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
	public void update(Tarea tarea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Tarea rol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Tarea find(Integer codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tarea> findAll() throws DataEmptyException, NotNullException, InvalidDateException {
		List<Tarea>tareas = new ArrayList<Tarea>();
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT t.nombre, t.proyecto, t.prioridad, t.usuario, t.estado, t.descripcion, t.fecha_inicio, t.fecha_fin "+"FROM tareas t");
			
			while (rs.next()) {
				Tarea tarea = new Tarea(rs.getString("nombre"), rs.getString("proyecto"), rs.getString("prioridad"), rs.getString("usuario"), rs.getBoolean("estado"),rs.getString("descripcion"), rs.getDate("fecha_inicio").toLocalDate().atStartOfDay(), rs.getDate("fecha_fin").toLocalDate().atStartOfDay());
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
}
