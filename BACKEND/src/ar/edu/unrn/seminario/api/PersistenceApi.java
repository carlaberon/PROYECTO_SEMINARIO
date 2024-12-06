package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.util.ArrayList;
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
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.ExistNotification;
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
	public List<TareaDTO> obtenerTareas() throws NotNullException, InvalidDateException, DataEmptyException {
	    List<Tarea> tareas = tareaDao.findByProject(proyectoActual.getId());
	    return tareas.stream()
	            .map(this::convertirEnTareaDTO) 
	            .collect(Collectors.toList());  
	}
	
	@Override
	public List<UsuarioDTO> obtenerUsuarios(String username) {
	    List<Usuario> usuarios = usuarioDao.findAll();

	    // Filtra los usuarios cuyo username no coincida con el proporcionado y conviértelos a UsuarioDTO
	    return usuarios.stream()
	                   .filter(u -> !username.equals(u.getUsername())) // Excluir el usuario actual
	                   .map(this::convertirEnUsuarioDTO)              // Convertir a UsuarioDTO
	                   .collect(Collectors.toList());                // Recoger como una lista
	}
	
	@Override
	public List<RolDTO> obtenerRoles() {
	    return rolDao.findAll().stream().map(this::convertirEnRolDTO).collect(Collectors.toList());
	}

	@Override
	public void crearProyecto(String nombre, String string, String estado, String descripcion, String prioridad)
			throws NotNullException, DataEmptyException {
		
		Usuario propietario = usuarioDao.find(string);
	    Proyecto nuevoProyecto = new Proyecto(0, nombre, propietario, estado, descripcion, prioridad);
	    proyectoDao.create(nuevoProyecto);
	}
	

	private RolDTO convertirEnRolDTO(Rol rol) {
		RolDTO rolDto = new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo());
		return rolDto;
	}

	@Override
	public void registrarTarea(String name,int id_proyecto, String priority, String user, String estado,
			String descripcion, LocalDate inicio, LocalDate fin)
			throws DataEmptyException, NotNullException, InvalidDateException {
		
		Tarea tarea = new Tarea(0, name, proyectoDao.find(id_proyecto), priority, user, estado, descripcion, inicio, fin);
		tareaDao.create(tarea);
	}

	
	@Override
	public void eliminarTarea(int idTarea) {
		tareaDao.remove(idTarea);
	}


	@Override
	public List<ProyectoDTO> obtenerProyectos(String username) throws NotNullException, DataEmptyException {
	    return proyectoDao.findAll(username).stream().map(this::convertirEnProyectoDTO).collect(Collectors.toList());
	}
	@Override
	public void eliminarProyecto(int id) {
		proyectoDao.remove(id);
	}
	
	@Override
	public void modificarProyecto(int idProyecto, String nuevoNombre, String nuevaPrioridad,
			String nuevaDescripcion) throws NotNullException, DataEmptyException {
	    Proyecto proyectoExistente = new Proyecto(idProyecto, nuevoNombre, null, null, nuevaDescripcion, nuevaPrioridad);
		
		proyectoDao.update(proyectoExistente);
		}
		
	@Override
	public List<TareaDTO> obtenerTareasPorProyecto(int id) throws DataEmptyException, NotNullException, InvalidDateException {
	    return tareaDao.findByProject(id).stream().map(this::convertirEnTareaDTO).collect(Collectors.toList()); 
	}
	
	@Override
	public ProyectoDTO getProyectoActual() {
		return convertirEnProyectoDTO(proyectoActual);
	}
	@Override
	public void setProyectoActual(int id) throws NotNullException, DataEmptyException {
			String usuarioActual = getUsuarioActual().getUsername();
			if (! usuarioActual.isEmpty()) {
				this.proyectoActual = proyectoDao.find(id);
			}			
	}
	
	public void setTareaActual(int idTarea) throws DataEmptyException, NotNullException, InvalidDateException {
		this.tareaActual = tareaDao.find(idTarea);
	}
	
	public TareaDTO getTareaActual() {
		return convertirEnTareaDTO(tareaActual);
	}

	@Override
	public void setUsuarioActual(String nombreUsuario) {
	    Usuario usuario = usuarioDao.find(nombreUsuario);
	    if (usuario != null) {
	        this.usuarioActual = usuario; 
	    } 
	}

	@Override
	public void modificarTarea(int idTarea, String nuevoNombre, String nuevaPrioridad, String nombreUsuario, String estado, String nuevaDescripcion, LocalDate inicio, LocalDate fin) throws NotNullException, DataEmptyException, InvalidDateException {
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
	public UsuarioDTO obtenerUsuario(String username) {
		Usuario usuario = usuarioDao.find(username);
		if (usuario != null){
			 UsuarioDTO userDTO = new UsuarioDTO(
			            usuario.getUsername(),
			            usuario.getContrasena(),
			            usuario.getNombre(),
			            usuario.getEmail(),
			            usuario.isActivo()
			        );
			        return userDTO; // Retorna el UsuarioDTO
			    }
			    
			    return null; // Retorna null si no se encuentra el usuario
			}

	@Override
	public void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol) {
		/*Rol rol = rolDao.find(codigoRol);
		Usuario usuario = new Usuario(username, password, nombre, email);
		this.usuarioDao.create(usuario);*/
	}
	@Override
	public List<RolDTO> obtenerRolesActivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardarRol(Integer codigo, String descripcion, boolean estado) {
		// TODO Auto-generated method stub

	}

	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) {
		/*//Rol rol = rolDao.find(codigo);
		RolDTO rolDTO = new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo());
		return rolDTO;*/
		return null;
	}
	@Override
	public void eliminarUsuario(String username) {
		usuarioDao.remove(username);
	}

	@Override
	public void activarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarUsuario(String username) {
		// TODO Auto-generated method stub

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
		
		System.out.println(notificacionDto);
		return notificacionDto;
	}
	
	public UsuarioDTO getUsuarioActual() { 
	    return convertirEnUsuarioDTO(usuarioActual);
	}
	
	public RolDTO getRol(String username, int idProyecto) {
		Rol rol = rolDao.find(username, idProyecto);
		if (rol != null) {
			return convertirEnRolDTO(rol);
		}
		return null;
	}
   
	@Override
	public void invitarMiembro(String username, int idProyecto, int codigoRol) {
		proyectoDao.inviteMember(username, idProyecto, codigoRol);
	}

	@Override
	public List<UsuarioDTO> obtenerMiembrosDeUnProyecto(int proyectoId) {
	    return proyectoDao.findAllMembers(proyectoId).stream()
	                      .map(this::convertirEnUsuarioDTO) // Convierte cada Usuario a UsuarioDTO
	                      .collect(Collectors.toList());   // Recoge el resultado como una lista
	}

	@Override
	public int existeMiembro(String username, int idProyecto) throws UserIsAlreadyMember {
	    List<UsuarioDTO> miembrosDTO = obtenerMiembrosDeUnProyecto(idProyecto);
	    if (miembrosDTO.stream().anyMatch(miembro -> username.equals(miembro.getUsername()))) {
	        throw new UserIsAlreadyMember("mensaje.esMiembro");
	    }
	    return 0; // No existe
	}

	@Override
	public void crearNotificacion(int idProyecto, String username, int codigoRol, String nombreProyecto, LocalDate fecha) throws NotNullException, DataEmptyException {
		String descripcion = "Te invitaron al proyecto: " + nombreProyecto;
		Notificacion notificacion = new Notificacion(idProyecto, username, codigoRol, descripcion, fecha);
		notificacionDao.create(notificacion);
	}

	@Override
	public List<NotificacionDTO> obtenerNotificaciones(String username) throws NotNullException, DataEmptyException {
		List<NotificacionDTO> notificacionesDTO = new ArrayList<>();
		List<Notificacion> notificacion = null;
		notificacion = notificacionDao.findAll(username);
		
		for (Notificacion notificacion2 : notificacion) {
			notificacionesDTO.add(convertirEnNotificacionDTO(notificacion2));
		}
		
		return notificacionesDTO;
	}

	@Override
	public void eliminarNotificacion(int idProyecto, String username) {
		notificacionDao.remove(idProyecto, username);
	}

	@Override
	public int existeNotificacion(int idProyecto, String username, int rol) throws ExistNotification {
		int existe = notificacionDao.existNotification(idProyecto, username, rol);
		if(existe == 1)
			throw new ExistNotification("mensaje.usuarioYaInvitado");
		return 0;
	}

	@Override
	public void eliminarMiembro(String username, int idProyecto) {
		proyectoDao.deleteMember(username, idProyecto);
	}


	
}