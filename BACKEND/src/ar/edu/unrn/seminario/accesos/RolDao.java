package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.modelo.Rol;

public interface RolDao {
	Rol find(String username, int id_proyecto) throws DataBaseConnectionException, DataBaseFoundException;

	List<Rol> findAll() throws DataBaseConnectionException;

}
