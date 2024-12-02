package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ar.edu.unrn.seminario.modelo.Usuario;

public class UsuarioDAOJDBC implements UsuarioDao {

	@Override
	public void create(Usuario usuario) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO usuarios(usuario, contrasena, nombre, email, activo) "
							+ "VALUES (?, ?, ?, ?, ?)");

			statement.setString(1, usuario.getUsername());
			statement.setString(2, usuario.getContrasena());
			statement.setString(3, usuario.getNombre());
			statement.setString(4, usuario.getEmail());
			statement.setBoolean(5, usuario.isActivo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			// TODO: disparar Exception propia
		} catch (Exception e) {
			System.out.println("Error al insertar un usuario");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}

	}

	public void update(Usuario usuario) {
	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement statement = conn.prepareStatement(
	            "UPDATE usuarios SET contrasena=?, nombre=?, email=?, activo=? WHERE usuario=?");
	        
	        statement.setString(1, usuario.getContrasena());
	        statement.setString(2, usuario.getNombre());
	        statement.setString(3, usuario.getEmail());
	        statement.setBoolean(4, usuario.isActivo());
	        statement.setString(5, usuario.getUsername());
	        
	        int rowsUpdated = statement.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Usuario actualizado exitosamente.");
	        } else {
	            System.out.println("No se encontró el usuario para actualizar.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar usuario. " + e.toString());
	        // TODO: disparar Exception propia
	    } finally {
	        ConnectionManager.disconnect();
	    }
	}
	
	public void remove(String username) {
	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement statement = conn.prepareStatement("DELETE FROM usuarios WHERE usuario = ?");
	        
	        statement.setString(1, username);
	        
	        int rowsDeleted = statement.executeUpdate();
	        if (rowsDeleted > 0) {
	            System.out.println("Usuario eliminado exitosamente.");
	        } else {
	            System.out.println("No se encontró el usuario para eliminar.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al eliminar usuario. " + e.toString());
	        // TODO: disparar Exception propia
	    } finally {
	        ConnectionManager.disconnect();
	    }
	}

	@Override
	public void remove(Usuario usuario) {
	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement statement = conn.prepareStatement("DELETE FROM usuarios WHERE usuario = ?");
	        
	        statement.setString(1, usuario.getUsername());
	        
	        int rowsDeleted = statement.executeUpdate();
	        if (rowsDeleted > 0) {
	            System.out.println("Usuario eliminado exitosamente.");
	        } else {
	            System.out.println("No se encontró el usuario para eliminar.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al eliminar usuario. " + e.toString());
	        // TODO: disparar Exception propia
	    } finally {
	        ConnectionManager.disconnect();
	    }
	}

	@Override
	public Usuario find(String username) {
		Usuario usuario = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT u.usuario,  u.contrasena, u.nombre, u.email"
							+ " FROM usuarios u" + " WHERE u.usuario = ?");

			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				usuario = new Usuario(rs.getString("usuario"), rs.getString("contrasena"), rs.getString("nombre"),
						rs.getString("email"));
			}
		
		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getCause().getMessage(), e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}

		return usuario;
	}

	@Override
	public List<Usuario> findAll() {
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
			ConnectionManager.disconnect();
		}

		return usuarios;
	}

}
