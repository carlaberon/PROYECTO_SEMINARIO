package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Proyecto;

public interface ProyectoDao {
	void create(Proyecto Usuario);

	void update(Proyecto Usuario);

	void remove(Long id);

	void remove(Proyecto Usuario);

	Proyecto find(String username);

	List<Proyecto> findAll();
}
