package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.ExistNotification;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.UserNotFound;
import ar.edu.unrn.seminario.modelo.Notificacion;


public interface NotificacionDao {
	
	void create(Notificacion notificacion) throws DataBaseConnectionException, DataBaseFoundException;
	
	void remove(int idProyecto, String username) throws DataBaseConnectionException;
	
	List<Notificacion> findAll(String username) throws NotNullException, DataEmptyException, DataBaseConnectionException;
	
	int existNotification(int idProyecto, String username) throws DataBaseConnectionException, ExistNotification;
	
}
