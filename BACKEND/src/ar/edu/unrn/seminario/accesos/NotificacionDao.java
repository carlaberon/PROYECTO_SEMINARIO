package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Notificacion;


public interface NotificacionDao {
	
	void create(Notificacion notificacion);
	
	void remove(int idProyecto, String username);
	
	List<Notificacion> findAll(String username) throws NotNullException, DataEmptyException;
	
	int existNotification(int idProyecto, String username, int rol);
	
}
