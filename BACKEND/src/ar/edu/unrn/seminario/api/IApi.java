package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.util.List;


import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TareaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.TaskNotCreatedException;
import ar.edu.unrn.seminario.exception.TaskNotFoundException;
import ar.edu.unrn.seminario.exception.TaskNotUpdatedException;
import ar.edu.unrn.seminario.exception.TaskQueryException;
import ar.edu.unrn.seminario.exception.UserIsAlreadyMember;


public interface IApi {
	//Metodos para los usuarios
	void registrarUsuario(String username, String password, String email, String nombre, Integer rol);

	UsuarioDTO obtenerUsuario(String username) throws NotNullException, DataEmptyException;

	void eliminarUsuario(String username);

	void activarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	List<UsuarioDTO> obtenerUsuarios(String username) throws NotNullException, DataEmptyException; // recuperar todos los usuarios
	
	void desactivarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	//Metodos para Roles
	List<RolDTO> obtenerRoles();

	List<RolDTO> obtenerRolesActivos();

	void guardarRol(Integer codigo, String descripcion, boolean estado); // crear el objeto de dominio �Rol�

	RolDTO obtenerRolPorCodigo(Integer codigo); // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.
	
	public RolDTO getRol(String username, int idProyecto);

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp
	
	//Metodos para las Tareas
	public void registrarTarea(String name,int id_proyecto, String priority, String user, String estado, String descripcion, LocalDate inicio, LocalDate fin) throws DataEmptyException, NotNullException, InvalidDateException, TaskNotCreatedException;
	
	List<TareaDTO> obtenerTareas() throws NotNullException, InvalidDateException, DataEmptyException, TaskQueryException;
	
	public void eliminarTarea(int idTarea) throws TaskNotFoundException;
	
	void modificarTarea(int id, String nuevoNombre, String nuevaPrioridad,String nombreUsuario, String estado, String nuevaDescripcion,LocalDate inicio, LocalDate fin)throws NotNullException, DataEmptyException, InvalidDateException, TaskNotUpdatedException, TaskQueryException;
	
	//Metodos para los proyectos
	List<ProyectoDTO> obtenerProyectos(String username) throws NotNullException, DataEmptyException;
		    
	public void eliminarProyecto(int id) throws TaskNotFoundException, DataEmptyException, NotNullException, InvalidDateException, TaskQueryException;
	
	void modificarProyecto(int idProyecto, String nuevoNombre, String nuevaPrioridad, String nuevaDescripcion)throws NotNullException, DataEmptyException;

	List<TareaDTO> obtenerTareasPorProyecto(int id_project) throws InvalidDateException, NotNullException, DataEmptyException, TaskQueryException;
	
	void crearProyecto(String nombre, String string, String estado, String descripcion, String prioridad)
			throws NotNullException, DataEmptyException;
	
	public void invitarMiembro(String username, int idProyecto, int codigoRol) throws DataEmptyException;
	
	public List<UsuarioDTO> obtenerMiembrosDeUnProyecto(int proyectoId);
	
	public int existeMiembro(String username, int idProyecto) throws UserIsAlreadyMember;
	
	//Metodos para proposito general
	public void setTareaActual(int idTarea) throws DataEmptyException, NotNullException, InvalidDateException;
	
	public TareaDTO getTareaActual() throws NotNullException, DataEmptyException, InvalidDateException;
	
	public UsuarioDTO getUsuarioActual() throws NotNullException, DataEmptyException; //Recuperar usuario actual PRUEBAS
	
	public void setUsuarioActual(String nombreUsuario);	//Setear usuario actual PRUEBAS
	
	public int obtenerPrioridad(String prioridad);
	
	public ProyectoDTO getProyectoActual() throws NotNullException, DataEmptyException;//Recuperar proyecto actual	PRUEBAS
	
	public void setProyectoActual(int id) throws NotNullException, DataEmptyException;	//Setear proyecto actual PRUEBAS
	
}
