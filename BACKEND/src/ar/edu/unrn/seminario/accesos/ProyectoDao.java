package ar.edu.unrn.seminario.accesos;

import java.util.List;


import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface ProyectoDao {
	void create(Proyecto proyecto) {

	void update(Proyecto proyecto);
	
	void inviteMember(String username, int idProyecto, int codigoRol);
	
	void deleteMember(String username, int idProyecto);
	
	void remove(int idProyecto);

	Proyecto find(int idProyecto) throws NotNullException, DataEmptyException;

	List<Proyecto> findAll(String usuario) throws NotNullException, DataEmptyException;
	
	List<Usuario> findAllMembers(int proyectoId);
}
