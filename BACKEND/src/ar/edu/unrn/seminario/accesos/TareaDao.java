package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Tarea;

public interface TareaDao {

	void create(Tarea tarea);

	void update(Tarea tarea);

	void remove(Long id);

	void remove(Tarea rol);

	Tarea find(Integer codigo);

	List<Tarea> findAll() throws DataEmptyException, NotNullException, InvalidDateException;
}
