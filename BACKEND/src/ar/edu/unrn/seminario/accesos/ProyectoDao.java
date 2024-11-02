package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Proyecto;

public interface ProyectoDao {
	void create(Proyecto proyecto);

	void update(Proyecto proyecto);

	void remove(String nombre, String usuario_propietario);

	void remove(Proyecto proyecto);

	Proyecto find(String nombre, String usuarioPropietario) throws NotNullException, DataEmptyException;

	List<Proyecto> findAll() throws NotNullException, DataEmptyException;
	
	List<Proyecto> findAll(String usuario) throws NotNullException, DataEmptyException;
}
