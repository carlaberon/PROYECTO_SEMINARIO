package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDao;
import ar.edu.unrn.seminario.accesos.TareaDAOJDBC;
import ar.edu.unrn.seminario.accesos.TareaDao;
import ar.edu.unrn.seminario.accesos.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.UsuarioDao;
import ar.edu.unrn.seminario.accesos.NotificacionDAOJBDC;
import ar.edu.unrn.seminario.accesos.NotificacionDao;
import ar.edu.unrn.seminario.accesos.ProyectoDAOJDBC;
import ar.edu.unrn.seminario.accesos.ProyectoDao;
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
import ar.edu.unrn.seminario.modelo.Notificacion;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Tarea;
import ar.edu.unrn.seminario.modelo.Usuario;

public class PersistenceApi implements IApi {

	private RolDao rolDao;
	private UsuarioDao usuarioDao;
	private ProyectoDao proyectoDao;
	private Usuario usuarioActual;
	private Proyecto proyectoActual;
	private Tarea tareaActual;
	private TareaDao tareaDao;
	private NotificacionDao notificacionDao;
	
	public PersistenceApi() {
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
		proyectoDao = new ProyectoDAOJDBC();
		tareaDao = new TareaDAOJDBC();
		notificacionDao = new NotificacionDAOJBDC();
	}
	
	@Override
	public List<TareaDTO> obtenerTareas() throws NotNullException, InvalidDateException, DataEmptyException, DataBaseConnectionException {
	    List<Tarea> tareas = tareaDao.findByProject(proyectoActual.getId());
	    return tareas.stream().map(this::convertirEnTareaDTO).collect(Collectors.toList());  
	}
	
	@Override
	public List<UsuarioDTO> obtenerUsuarios(String username) throws DataBaseConnectionException {
	    List<Usuario> usuarios = usuarioDao.findAll();
	    return usuarios.stream().filter(u -> !username.equals(u.getUsername())).map(this::convertirEnUsuarioDTO).collect(Collectors.toList());                
	}
	
	@Override
	public List<RolDTO> obtenerRoles() throws DataBaseConnectionException {
	    return rolDao.findAll().stream().map(this::convertirEnRolDTO).collect(Collectors.toList());
	}

	@Override
	public void crearProyecto(String nombre, String username, String estado, String descripcion, String prioridad)
			throws NotNullException, DataEmptyException, DataBaseInsertionException, DataBaseConnectionException, DataBaseFoundException {
		
		Usuario propietario = usuarioDao.find(username);
	    Proyecto nuevoProyecto = new Proyecto(0, nombre, propietario, estado, descripcion, prioridad);
	    proyectoDao.create(nuevoProyecto);
	}
	

	private RolDTO convertirEnRolDTO(Rol rol) {
		RolDTO rolDto = new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo());
		return rolDto;
	}
	
	@Override
	public void registrarTarea(String name,int id_proyecto, String priority, String user, String estado,
			String descripcion, LocalDate inicio, LocalDate fin) throws DataEmptyException, NotNullException, InvalidDateException, DataBaseConnectionException, DataBaseInsertionException, DataBaseFoundException {

		
		Tarea tarea = new Tarea(0, name, proyectoDao.find(id_proyecto), priority, user, estado, descripcion, inicio, fin);
		tareaDao.create(tarea);
	}

	
	@Override
	public void eliminarTarea(int idTarea) throws DataBaseEliminationException, DataBaseConnectionException {
			tareaDao.remove(idTarea);
	}


	@Override
	public List<ProyectoDTO> obtenerProyectos(String username) throws NotNullException, DataEmptyException, DataBaseConnectionException {
	    return proyectoDao.findAll(username).stream().map(this::convertirEnProyectoDTO).collect(Collectors.toList());
	}
	@Override
	public void eliminarProyecto(int id) throws DataBaseConnectionException {
		proyectoDao.remove(id);
	}
	
	@Override
	public void modificarProyecto(int idProyecto, String nuevoNombre, String nuevaPrioridad,
			String nuevaDescripcion) throws NotNullException, DataEmptyException, DataBaseConnectionException, DataBaseUpdateException {
	    Proyecto proyectoExistente = new Proyecto(idProyecto, nuevoNombre, null, null, nuevaDescripcion, nuevaPrioridad);
		
		proyectoDao.update(proyectoExistente);
		}
		
	@Override
	public List<TareaDTO> obtenerTareasPorProyecto(int id) throws DataEmptyException, NotNullException, InvalidDateException, DataBaseConnectionException {
	    return tareaDao.findByProject(id).stream().map(this::convertirEnTareaDTO).collect(Collectors.toList()); 
	}
	
	@Override
	public ProyectoDTO getProyectoActual() {
		return convertirEnProyectoDTO(proyectoActual);
	}
	@Override
	public void setProyectoActual(int id) throws NotNullException, DataEmptyException, DataBaseConnectionException, DataBaseFoundException {
			String usuarioActual = getUsuarioActual().getUsername();
			if (! usuarioActual.isEmpty()) {
				this.proyectoActual = proyectoDao.find(id);
			}			
	}
	
	public void setTareaActual(int idTarea) throws DataEmptyException, NotNullException, InvalidDateException, DataBaseFoundException, DataBaseConnectionException {
		this.tareaActual = tareaDao.find(idTarea);
	}
	
	public TareaDTO getTareaActual() {
		return convertirEnTareaDTO(tareaActual);
	}

	@Override
	public void setUsuarioActual(String nombreUsuario) throws DataBaseFoundException, DataBaseConnectionException {
	    Usuario usuario = usuarioDao.find(nombreUsuario);
	    if (usuario != null) {
	        this.usuarioActual = usuario; 
	    } 
	}

	@Override
	public void modificarTarea(int idTarea, String nuevoNombre, String nuevaPrioridad, String nombreUsuario, String estado, String nuevaDescripcion, LocalDate inicio, LocalDate fin) throws NotNullException, DataEmptyException, InvalidDateException, DataBaseConnectionException, DataBaseUpdateException {
		Tarea tarea = new Tarea(idTarea, nuevoNombre, proyectoActual, nuevaPrioridad, nombreUsuario, estado, nuevaDescripcion, inicio, fin);
		tareaDao.update(tarea);
	}
	
	@Override
	public int obtenerPrioridad(String prioridad) {
	    switch (prioridad) {
	        case "Alta":
	            return 1;
	        case "Media":
	            return 2;
	        case "Baja":
	            return 3;
	        default:
	            return 0; 
	    }
	}
	
	@Override
	public String obtenerPrioridadPorIndex(int indice) {
		switch (indice) {
		case 1:
			return "Alta";
		case 2:
			return "Media";
		case 3:
			return "Baja";
		default:
			return "";
		}
	}

	@Override
	public String traducirPrioridad(String prioridad) {
		switch (prioridad) {
		case "Alta":
			return "prioridad.alta";
		case "Media":
			return "prioridad.media";
		default:
			return "prioridad.baja";
		}
	}
	
	@Override
	public String traducirRol(String rol) {
		switch (rol) {
		case "Administrador":
			return "rol.Admin";
		case "Colaborador":
			return "rol.Colaborador";
		default:
			return "rol.Observador";
		}
	}
	
	@Override
	public String obtenerRolPorIndex(int indice) {
		switch (indice) {
		case 1:
			return "Administrador";
		case 2:
			return "Colaborador";
		case 3:
			return "Observador";
		default:
			return "";
		}
	}
	
	@Override
	public UsuarioDTO obtenerUsuario(String username) throws DataBaseFoundException, DataBaseConnectionException {
		Usuario usuario = usuarioDao.find(username);
		if (usuario != null){
			 UsuarioDTO userDTO = new UsuarioDTO(
			            usuario.getUsername(),
			            usuario.getContrasena(),
			            usuario.getNombre(),
			            usuario.getEmail(),
			            usuario.isActivo()
			        );
			        return userDTO; 
			    }
			    
			    return null; 
			}


	private UsuarioDTO convertirEnUsuarioDTO(Usuario usuario)  {
		UsuarioDTO usuarioDto = new UsuarioDTO(usuario.getUsername(), usuario.getContrasena(), usuario.getNombre(), 
				usuario.getEmail(), usuario.isActivo());
		return usuarioDto;
	}
	
	private TareaDTO convertirEnTareaDTO(Tarea tarea) {
		TareaDTO tareaDto = new TareaDTO(tarea.getId(), tarea.getNombre(), convertirEnProyectoDTO(tarea.getProyecto()), tarea.getPrioridad(), tarea.getUsuario(), tarea.getEstado(), tarea.getDescripcion(), tarea.getInicio(), tarea.getFin());
		return tareaDto;
	}
	
	private ProyectoDTO convertirEnProyectoDTO(Proyecto proyecto) {
		ProyectoDTO proyectoDto = null;
		if(proyecto != null)
			proyectoDto = new ProyectoDTO(proyecto.getId(),proyecto.getNombre(), convertirEnUsuarioDTO(proyecto.getUsuarioPropietario()), proyecto.getEstado(), proyecto.getPrioridad(), proyecto.getDescripcion());

		proyectoDto.setId(proyecto.getId());
		return proyectoDto;
	}
	
	private NotificacionDTO convertirEnNotificacionDTO(Notificacion notificacion) {
		NotificacionDTO notificacionDto = null;
		if(notificacion != null)
			notificacionDto = new NotificacionDTO(notificacion.getIdProyecto(),notificacion.getUsername(),
					notificacion.getCodigoRol(),notificacion.getDescripcion(),notificacion.getFecha());
		return notificacionDto;
	}
	
	public UsuarioDTO getUsuarioActual() { 
	    return convertirEnUsuarioDTO(usuarioActual);
	}
	
	public RolDTO getRol(String username, int idProyecto) throws DataBaseConnectionException, DataBaseFoundException {
		Rol rol = rolDao.find(username, idProyecto);
		if (rol != null) {
			return convertirEnRolDTO(rol);
		}
		return null;
	}
   
	@Override
	public void invitarMiembro(String username, int idProyecto, int codigoRol) throws DataBaseConnectionException, DataBaseUpdateException {
		proyectoDao.inviteMember(username, idProyecto, codigoRol);
	}

	@Override
	public List<UsuarioDTO> obtenerMiembrosDeUnProyecto(int proyectoId) throws DataBaseConnectionException, DataBaseFoundException {
	    return proyectoDao.findAllMembers(proyectoId).stream().map(this::convertirEnUsuarioDTO) .collect(Collectors.toList());  
	}

	@Override
	public int existeMiembro(String username, int idProyecto) throws UserIsAlreadyMember, DataBaseConnectionException, DataBaseFoundException {
	    List<UsuarioDTO> miembrosDTO = obtenerMiembrosDeUnProyecto(idProyecto);
	    if (miembrosDTO.stream().anyMatch(miembro -> username.equals(miembro.getUsername()))) {
	        throw new UserIsAlreadyMember("mensaje.esMiembro");
	    }
	    return 0; 
	}

	@Override
	public void crearNotificacion(int idProyecto, String username, int codigoRol, String nombreProyecto, LocalDate fecha) throws NotNullException, DataEmptyException, DataBaseConnectionException, DataBaseFoundException {
		String descripcion = "Te invitaron al proyecto: " + nombreProyecto;
		Notificacion notificacion = new Notificacion(idProyecto, username, codigoRol, descripcion, fecha);
		notificacionDao.create(notificacion);
	}

	@Override
	public List<NotificacionDTO> obtenerNotificaciones(String username) throws NotNullException, DataEmptyException, DataBaseConnectionException {
		return notificacionDao.findAll(username).stream().map(this::convertirEnNotificacionDTO).collect(Collectors.toList());
	}

	@Override
	public void eliminarNotificacion(int idProyecto, String username) throws DataBaseConnectionException, DataBaseUpdateException {
		notificacionDao.remove(idProyecto, username);
	}

	@Override
	public void existeNotificacion(int idProyecto, String username) throws ExistNotification, DataBaseConnectionException {
		notificacionDao.existNotification(idProyecto, username);
		
	}

}