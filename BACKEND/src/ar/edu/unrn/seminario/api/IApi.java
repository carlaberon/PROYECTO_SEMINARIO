package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ar.edu.unrn.seminario.dto.EventoDTO;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TareaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.TaskNotUpdatedException;
import ar.edu.unrn.seminario.modelo.Evento;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Tarea;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface IApi {
	void registrarUsuario(String username, String password, String email, String nombre, Integer rol);

	UsuarioDTO obtenerUsuario(String username) throws NotNullException, DataEmptyException;

	void eliminarUsuario(String username);

	List<RolDTO> obtenerRoles();

	List<RolDTO> obtenerRolesActivos();

	void guardarRol(Integer codigo, String descripcion, boolean estado); // crear el objeto de dominio �Rol�

	RolDTO obtenerRolPorCodigo(Integer codigo); // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	List<UsuarioDTO> obtenerUsuarios() throws NotNullException, DataEmptyException; // recuperar todos los usuarios

	void activarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	public void registrarTarea(String name, String project, String usuarioPropietario, String priority, String user, boolean estado, String descripcion, LocalDate inicio, LocalDate fin) throws DataEmptyException, NotNullException, InvalidDateException;
	
	public int obtenerPrioridad(String prioridad);
	
	List<TareaDTO> obtenerTareas() throws NotNullException, InvalidDateException, DataEmptyException;
	
	void añadirTareaAProyecto(String proyecto, Tarea tarea);
	
	public void eliminarTarea(int idTarea);
	
	List<ProyectoDTO> obtenerProyectos(String username) throws NotNullException, DataEmptyException;
		
	void asignarPrioridad(String nombreProyecto, String prioridad) throws NotNullException, DataEmptyException;
	
	int obtenerValorPrioridad(String prioridad); 
	
    public int compare(Proyecto p1, Proyecto p2);
    
	void eliminarProyecto(int id);
	
	void modificarProyecto(int idProyecto, String nuevoNombre, String nuevaPrioridad, String nuevaDescripcion)throws NotNullException, DataEmptyException;

	List<TareaDTO> obtenerTareasPorProyecto(String nombreProyecto,String usuarioPropietario) throws InvalidDateException, NotNullException, DataEmptyException;
	
	
	void crearProyecto(String nombre, String string, boolean estado, String descripcion, String prioridad)
			throws NotNullException, DataEmptyException;
	
	public ProyectoDTO getProyectoActual() throws NotNullException, DataEmptyException;//Recuperar proyecto actual	PRUEBAS

	public void setProyectoActual(int id) throws NotNullException, DataEmptyException;	//Setear proyecto actual PRUEBAS
	
	public UsuarioDTO getUsuarioActual() throws NotNullException, DataEmptyException; //Recuperar usuario actual PRUEBAS

	public void setUsuarioActual(String nombreUsuario);	//Setear usuario actual PRUEBAS

	void modificarTarea(int id, String nombreProyecto, String nuevoNombre, String nuevaPrioridad,String nombreUsuario,Boolean estado, String nuevaDescripcion,LocalDate inicio, LocalDate fin)throws NotNullException, DataEmptyException, InvalidDateException, TaskNotUpdatedException;
	
	//void crearPlan(String nombre, Proyecto pertenece);
	//void crearEvento(LocalDateTime fecha, LocalDateTime inicio, LocalDateTime fin, String descripcion);
	//List<EventoDTO> obtenerEventos();
}
