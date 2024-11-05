package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.TaskNotUpdatedException;
import ar.edu.unrn.seminario.exception.TaskUpdatedSuccessfullyException;
import ar.edu.unrn.seminario.modelo.Tarea;

public interface TareaDao {

	void create(Tarea tarea);

	void update(Tarea tarea, String nombreOriginal) throws TaskNotUpdatedException, TaskUpdatedSuccessfullyException;

	void remove(String nombre, String proyecto, String usuario_propietario);

	void remove(Tarea tarea);

	Tarea find(String nombreTarea, String proyecto, String usuario_propietario) throws DataEmptyException, NotNullException, InvalidDateException;
	
	List<Tarea> findTareas(String proyecto, String usuario_propietario) throws DataEmptyException, NotNullException, InvalidDateException; //buscar tareas por proyecto
	
	List<Tarea> findAll() throws DataEmptyException, NotNullException, InvalidDateException;
}
