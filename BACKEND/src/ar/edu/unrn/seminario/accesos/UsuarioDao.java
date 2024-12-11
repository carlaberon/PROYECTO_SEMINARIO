package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface UsuarioDao {
	void create(Usuario Usuario) throws DataBaseConnectionException;

	void update(Usuario Usuario) throws DataBaseConnectionException;

	void remove(String userNombre) throws DataBaseConnectionException;

	void remove(Usuario Usuario) throws DataBaseConnectionException;

	Usuario find(String username) throws DataBaseConnectionException, DataBaseFoundException ;

	List<Usuario> findAll() throws DataBaseConnectionException;

}
