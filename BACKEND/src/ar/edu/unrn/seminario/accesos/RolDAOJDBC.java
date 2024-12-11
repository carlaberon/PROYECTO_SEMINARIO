package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.modelo.Rol;
//import ar.edu.unrn.seminario.modelo.Usuario;

public class RolDAOJDBC implements RolDao {

	@Override
	public void create(Rol rol) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Rol rol) {
		// TODO Auto-generated method stub

//		if (e instanceof SQLIntegrityConstraintViolationException) {
//	        // Duplicate entry
//	    } else {
//	        // Other SQL Exception
//	    }

	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Rol rol) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rol find(String username, int id_proyecto) throws DataBaseConnectionException {
		Rol rol = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT r.codigo, r.nombre, r.activo\r\n"
							+ "FROM roles r JOIN proyectos_usuarios_roles pur ON r.codigo = pur.codigo_rol\r\n"
							+ "WHERE pur.nombre_usuario = ? AND pur.id_proyecto = ?");

			statement.setString(1, username);
			statement.setInt(2, id_proyecto);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				rol = new Rol(rs.getInt("r.codigo"), rs.getString("r.nombre"), rs.getBoolean("r.activo"));
			}

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getCause().getMessage(), e.getMessage());
		} finally {
			try {
				ConnectionManager.disconnect();
			} catch (SQLException e) {
				throw new DataBaseConnectionException("exceptionDAO.disconnect");
			}
		}

		return rol;
	}

	@Override
	public List<Rol> findAll() throws DataBaseConnectionException {
		List<Rol> listado = new ArrayList<Rol>();
		Statement sentencia = null;
		ResultSet resultado = null;
		try {
			sentencia = ConnectionManager.getConnection().createStatement();
			resultado = sentencia.executeQuery("select r.nombre, r.codigo, r.activo from roles r ");

			while (resultado.next()) {
				Rol rol = new Rol();
				rol.setNombre(resultado.getString(1));
				rol.setCodigo(resultado.getInt(2));
				rol.setActivo(resultado.getBoolean(3));

				listado.add(rol);
			}
		} catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.disconnect();
			} catch (SQLException e) {
				throw new DataBaseConnectionException("exceptionDAO.disconnect");
			}
		}

		return listado;
	}

}
