package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseEliminationException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.exception.DataBaseInsertionException;
import ar.edu.unrn.seminario.exception.DataBaseUpdateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Tarea;

public interface TareaDao {

	void create(Tarea tarea) throws DataBaseInsertionException, DataBaseConnectionException;
	
	public List<Tarea> findByProject(int id_project) throws DataEmptyException, NotNullException, InvalidDateException, DataBaseConnectionException, DataBaseFoundException;

	void update(Tarea tarea) throws DataBaseConnectionException, DataBaseUpdateException;
	
	void remove(int id) throws DataBaseConnectionException, DataBaseEliminationException;
	
	public Tarea find(int idTarea) throws DataEmptyException, NotNullException, InvalidDateException, DataBaseConnectionException, DataBaseFoundException;
}
