package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Proyecto;

public interface ProyectoDao {
	void create(Proyecto proyecto);

	void update(Proyecto proyecto);
	
	void update(String username, int idProyecto, int codigoRol);

	void remove(int id);

	Proyecto find(int id) throws NotNullException, DataEmptyException;

	List<Proyecto> findAll(String usuario) throws NotNullException, DataEmptyException;
}
