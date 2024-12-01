package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.TaskNotCreatedException;
import ar.edu.unrn.seminario.exception.TaskNotFoundException;
import ar.edu.unrn.seminario.exception.TaskNotUpdatedException;
import ar.edu.unrn.seminario.exception.TaskQueryException;
import ar.edu.unrn.seminario.modelo.Tarea;

public interface TareaDao {

	void create(Tarea tarea);
	
	public List<Tarea> findByProject(int id_project) throws DataEmptyException, NotNullException, InvalidDateException;

	void update(Tarea tarea);
	
	void remove(int id);
	
	public Tarea find(int idTarea) throws DataEmptyException, NotNullException, InvalidDateException;
}
