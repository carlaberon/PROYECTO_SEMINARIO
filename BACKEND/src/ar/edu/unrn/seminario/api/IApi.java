package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.util.List;

import ar.edu.unrn.seminario.dto.NotificacionDTO;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TareaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseEliminationException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.ExistNotification;
import ar.edu.unrn.seminario.exception.DataBaseInsertionException;
import ar.edu.unrn.seminario.exception.DataBaseUpdateException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.UserIsAlreadyMember;
import ar.edu.unrn.seminario.exception.UserNotFound;


public interface IApi {
	//Metodos para los usuarios
	void registrarUsuario(String username, String password, String email, String nombre, Integer rol);

	UsuarioDTO obtenerUsuario(String username) throws DataBaseFoundException, DataBaseConnectionException;

	void eliminarUsuario(String username) throws DataBaseConnectionException;

	void activarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	List<UsuarioDTO> obtenerUsuarios(String username) throws DataBaseConnectionException; // recuperar todos los usuarios
	
	void desactivarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	//Metodos para Roles
	List<RolDTO> obtenerRoles() throws DataBaseConnectionException;

	List<RolDTO> obtenerRolesActivos();

	void guardarRol(Integer codigo, String descripcion, boolean estado); // crear el objeto de dominio �Rol�

	RolDTO obtenerRolPorCodigo(Integer codigo); // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.
	
	public RolDTO getRol(String username, int idProyecto) throws DataBaseConnectionException;

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp
	
	//Metodos para las Tareas
	public void registrarTarea(String name, int id_proyecto, String priority, String user, String estado, String descripcion, LocalDate inicio, LocalDate fin) throws DataEmptyException, NotNullException, InvalidDateException, DataBaseConnectionException, DataBaseInsertionException;

	
	List<TareaDTO> obtenerTareas() throws NotNullException, InvalidDateException, DataEmptyException, DataBaseFoundException, DataBaseConnectionException;
	
	public void eliminarTarea(int idTarea) throws DataBaseEliminationException, DataBaseConnectionException;
	
	void modificarTarea(int idTarea, String nuevoNombre, String nuevaPrioridad,String nombreUsuario, String estado, String nuevaDescripcion,LocalDate inicio, LocalDate fin)throws NotNullException, DataEmptyException, InvalidDateException, DataBaseConnectionException, DataBaseUpdateException;
	
	//Metodos para los proyectos
	List<ProyectoDTO> obtenerProyectos(String username) throws NotNullException, DataEmptyException, DataBaseConnectionException;
		    
	public void eliminarProyecto(int id) throws DataBaseConnectionException;
	
	void modificarProyecto(int idProyecto, String nuevoNombre, String nuevaPrioridad, String nuevaDescripcion) throws NotNullException, DataEmptyException, DataBaseConnectionException;

	List<TareaDTO> obtenerTareasPorProyecto(int id_project) throws InvalidDateException, NotNullException, DataEmptyException, DataBaseFoundException, DataBaseConnectionException;
	
	void crearProyecto(String nombre, String string, String estado, String descripcion, String prioridad) throws NotNullException, DataEmptyException, DataBaseInsertionException, DataBaseConnectionException, DataBaseFoundException;
	
	public void invitarMiembro(String username, int idProyecto, int codigoRol) throws DataBaseConnectionException;
	
	public List<UsuarioDTO> obtenerMiembrosDeUnProyecto(int proyectoId) throws DataBaseConnectionException;
	
	public int existeMiembro(String username, int idProyecto) throws UserIsAlreadyMember, DataBaseConnectionException;
	
	public void eliminarMiembro(String username, int idProyecto) throws DataBaseConnectionException;
	
	//Metodos para las notificaciones
	public void crearNotificacion(int idProyecto, String username, int codigoRol, String nombreProyecto, LocalDate fecha) throws NotNullException, DataEmptyException, DataBaseConnectionException, UserNotFound;
	
	public List<NotificacionDTO> obtenerNotificaciones(String username) throws NotNullException, DataEmptyException, DataBaseConnectionException;
	
	public void eliminarNotificacion(int idProyecto, String username) throws DataBaseConnectionException;
	
	public int existeNotificacion(int idProyecto, String username) throws ExistNotification, DataBaseConnectionException;
	
	//Metodos para proposito general
	public void setTareaActual(int idTarea) throws DataEmptyException, NotNullException, InvalidDateException, DataBaseFoundException, DataBaseConnectionException;
	
	public TareaDTO getTareaActual();
	
	public UsuarioDTO getUsuarioActual(); //Recuperar usuario actual PRUEBAS
	
	public void setUsuarioActual(String nombreUsuario) throws DataBaseFoundException, DataBaseConnectionException;	//Setear usuario actual PRUEBAS
	
	public int obtenerPrioridad(String prioridad);
	
	public String traducirPrioridad(String prioridad);
	
	public String obtenerPrioridadPorIndex(int indice);
	
	public String obtenerRolPorIndex(int indice);
	
	public String traducirRol(String rol);
	
	public ProyectoDTO getProyectoActual();//Recuperar proyecto actual	PRUEBAS
	
	public void setProyectoActual(int id) throws NotNullException, DataEmptyException, DataBaseConnectionException;	//Setear proyecto actual PRUEBAS
	
}
