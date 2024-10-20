package ar.edu.unrn.seminario.api;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.unrn.seminario.dto.EventoDTO;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TareaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Evento;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Tarea;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface IApi {

	void registrarUsuario(String username, String password, String email, String nombre, Integer rol);

	UsuarioDTO obtenerUsuario(String username);

	void eliminarUsuario(String username);

	List<RolDTO> obtenerRoles();

	List<RolDTO> obtenerRolesActivos();

	void guardarRol(Integer codigo, String descripcion, boolean estado); // crear el objeto de dominio �Rol�

	RolDTO obtenerRolPorCodigo(Integer codigo); // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	List<UsuarioDTO> obtenerUsuarios(); // recuperar todos los usuarios

	void activarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	void registrarTarea(String name, String project, UsuarioDTO name1, UsuarioDTO user, boolean estado, String descripcion, LocalDateTime inicio, LocalDateTime fin)throws DataEmptyException, NotNullException;
	
	List<TareaDTO> obtenerTareas();
	
	List<ProyectoDTO> obtenerProyectos();

	
	void crearEvento(LocalDateTime fecha, LocalDateTime inicio, LocalDateTime fin, String descripcion);
	
	List<EventoDTO> obtenerEventos();
	
	void asignarPrioridad(String nombreProyecto, String prioridad);
	
    public int compare(Proyecto p1, Proyecto p2);
    
    void crearPlan(String nombre, Proyecto pertenece);

	void eliminarProyecto(String nombreProyecto);
	
	void modificarProyecto(String nombreProyecto, String nuevoNombre, String nuevaPrioridad, String nuevaDescripcion);

	List<TareaDTO> obtenerTareasPorProyecto(String nombreProyecto);
	
	public int obtenerValorPrioridad(String prioridad);
	
	void añadirTareaAProyecto(String proyecto, Tarea tarea);




	void crearProyecto(String nombre, String string, boolean estado, String descripcion, String prioridad)
			throws NotNullException, DataEmptyException;


	
	
}
