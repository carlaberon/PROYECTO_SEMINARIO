package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.modelo.Usuario;

public class UsuarioDAOJDBC implements UsuarioDao {
	@Override
	public Usuario find(String username) throws DataBaseConnectionException, DataBaseFoundException {
		Usuario usuario = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT u.usuario,  u.contrasena, u.nombre, u.email"
							+ " FROM usuarios u" + " WHERE u.usuario = ?");

			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				usuario = new Usuario(rs.getString("usuario"), rs.getString("contrasena"), rs.getString("nombre"), rs.getString("email"));
			} else {
				throw new DataBaseFoundException("exceptionUsuarioDAO.find");
			}
		
		} catch (SQLException e) {
	        throw new DataBaseConnectionException("exceptionDAO.conecction");
		} 
		finally {
			try {
				ConnectionManager.disconnect();
			} catch (SQLException e) {
				throw new DataBaseConnectionException("exceptionDAO.disconnect");
			}
		}

		return usuario;
	}

	@Override
	public List<Usuario> findAll() throws DataBaseConnectionException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT u.usuario,  u.contrasena, u.nombre, u.email "
							+ "FROM usuarios u");

			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("usuario"), rs.getString("contrasena"),
						rs.getString("nombre"), rs.getString("email"));
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
			// TODO: disparar Exception propia
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			// TODO: disparar Exception propia
		} finally {
			try {
				ConnectionManager.disconnect();
			} catch (SQLException e) {
				throw new DataBaseConnectionException("exceptionDAO.disconnect");
			}
		}

		return usuarios;
	}

}
