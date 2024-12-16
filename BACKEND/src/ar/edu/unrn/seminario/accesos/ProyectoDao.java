package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataBaseInsertionException;
import ar.edu.unrn.seminario.exception.DataBaseUpdateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface ProyectoDao {
	
	void create(Proyecto proyecto) throws DataBaseInsertionException, DataBaseConnectionException;

	void update(Proyecto proyecto) throws DataBaseConnectionException, DataBaseUpdateException;
	
	void inviteMember(String username, int idProyecto, int codigoRol) throws DataBaseConnectionException, DataBaseUpdateException;
	
	void remove(int idProyecto) throws DataBaseConnectionException;

	Proyecto find(int idProyecto) throws NotNullException, DataEmptyException, DataBaseConnectionException, DataBaseFoundException;

	List<Proyecto> findAll(String usuario) throws NotNullException, DataEmptyException, DataBaseConnectionException;
	
	List<Usuario> findAllMembers(int proyectoId) throws DataBaseConnectionException;
}
