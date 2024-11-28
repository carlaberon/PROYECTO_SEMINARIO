package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Rol;

public interface RolDao {
	void create(Rol rol);

	void update(Rol rol);

	void remove(Long id);

	void remove(Rol rol);

	Rol find(String username, int id_proyecto);

	List<Rol> findAll();

}
