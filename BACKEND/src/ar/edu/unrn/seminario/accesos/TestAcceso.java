package ar.edu.unrn.seminario.accesos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Tarea;
import ar.edu.unrn.seminario.modelo.Usuario;

public class TestAcceso {

	public static void main(String[] args) throws DataEmptyException, NotNullException, InvalidDateException {
//		RolDao rolDao = new RolDAOJDBC();
//		List<Rol> roles = rolDao.findAll();
//
//		for (Rol rol : roles) {
//			System.out.println(rol);
//		}
//
//		UsuarioDao usuarioDao = new UsuarioDAOJDBC();
//--------------------------------------------------------------------------------------------------------------------------------------		
		//Pruebas con tarea
		TareaDao tareaDao = new TareaDAOJDBC();
		Tarea tarea = null;
		//Alta de tarea
//		tarea = new Tarea("plan de estudio", "Aplicacion de votos","ldifabio","alta","ldifabio", false, "plan de estudios para cerrar el cuatrimestre", LocalDate.now(), LocalDate.now().plusDays(20));
//		tareaDao.create(tarea);
		
		//Recuperacion de todas las tareas
		List<Tarea> tareas = tareaDao.findAll();
		for (Tarea tarea2 : tareas) {
			System.out.println(tarea2);
		}
		//Recuperacion de una tarea en especifico
		tarea = tareaDao.find("Definir plan de estudio", "Aplicacion de votos", "ldifabio");
		System.out.println(tarea);
		
		//Eliminar una tarea en particular atraves del objeto Tarea
		tareaDao.remove(tarea);
		tareas = tareaDao.findAll();
		for (Tarea tarea2 : tareas) {
			System.out.println(tarea2);
		}
		
		//Modificar tarea
		
		//--------------------------------------------------------------------------------------------------------------------------------------
		
		//Usuario usuario = new Usuario("ldifabio", "1234", "Lucas", "ldifabio@unrn.edu.ar", new Rol(1, ""));
		
		//usuarioDao.create(usuario);
		
//		List<Usuario> usuarios = usuarioDao.findAll();
//			for (Usuario u: usuarios) {
//			System.out.println(u);
//	}
	
//			System.out.println(usuarioDao.find("ldifabio"));
	}

}
