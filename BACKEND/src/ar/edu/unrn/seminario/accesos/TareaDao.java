package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.TaskNotCreatedException;
import ar.edu.unrn.seminario.exception.NotUpdatedException;
import ar.edu.unrn.seminario.modelo.Tarea;

public interface TareaDao {

	void create(Tarea tarea) throws TaskNotCreatedException;
	
	public List<Tarea> findByProject(int id_project) throws DataEmptyException, NotNullException, InvalidDateException;

	void update(Tarea tarea) throws NotUpdatedException;
	
	void remove(int id);
	
	public Tarea find(int id) throws DataEmptyException, NotNullException, InvalidDateException;
}
