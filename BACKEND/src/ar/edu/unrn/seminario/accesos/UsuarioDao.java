package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface UsuarioDao {
	Usuario find(String username) throws DataBaseConnectionException, DataBaseFoundException ;

	List<Usuario> findAll() throws DataBaseConnectionException;

}
