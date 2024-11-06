package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.TaskNotUpdatedException;
import ar.edu.unrn.seminario.modelo.Tarea;

public interface TareaDao {

	void create(Tarea tarea);
	
	public List<Tarea> findByProject(int id_project, String usuario_propietario) throws DataEmptyException, NotNullException, InvalidDateException;

	void update(Tarea tarea, int id) throws TaskNotUpdatedException;
	
	void remove(int id);
	

	//public Tarea find(int id, String usuario_propietario) throws DataEmptyException, NotNullException, InvalidDateException;
	
	List<Tarea> findAll() throws DataEmptyException, NotNullException, InvalidDateException;
}
