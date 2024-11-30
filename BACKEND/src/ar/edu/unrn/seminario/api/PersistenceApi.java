package ar.edu.unrn.seminario.api;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;


import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDao;
import ar.edu.unrn.seminario.accesos.TareaDAOJDBC;
import ar.edu.unrn.seminario.accesos.TareaDao;
import ar.edu.unrn.seminario.accesos.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.UsuarioDao;
import ar.edu.unrn.seminario.accesos.ProyectoDAOJDBC;
import ar.edu.unrn.seminario.accesos.ProyectoDao;
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
	//private Set<Proyecto> proyectos = new HashSet<>();
	public PersistenceApi() {
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
		proyectoDao = new ProyectoDAOJDBC();
		tareaDao = new TareaDAOJDBC();
	}

	@Override
	public void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol) {
		/*Rol rol = rolDao.find(codigoRol);
		Usuario usuario = new Usuario(username, password, nombre, email);
		this.usuarioDao.create(usuario);*/
	}
	
	@Override
	public List<TareaDTO> obtenerTareas() throws NotNullException, InvalidDateException, DataEmptyException, TaskQueryException {
		List<TareaDTO> tareasDTO = new ArrayList<>();
		List<Tarea> tareas = null;

		tareas = tareaDao.findByProject(proyectoActual.getId());
		
		for (Tarea t : tareas) {  
			tareasDTO.add(convertirEnTareaDTO(t));
		}
	    return tareasDTO;
	}
	
	@Override
	public List<UsuarioDTO> obtenerUsuarios() throws NotNullException, DataEmptyException {
		List<UsuarioDTO> dtos = new ArrayList<>();
		List<Usuario> usuarios = usuarioDao.findAll();
		for (Usuario u : usuarios) {
			dtos.add(convertirEnUsuarioDTO(u));
		}
		return dtos;
	}
	
	@Override
	public List<RolDTO> obtenerRoles() {
		List<Rol> roles = rolDao.findAll();
		List<RolDTO> rolesDTO = new ArrayList<>(0);
		for (Rol rol : roles) {
			rolesDTO.add(new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo()));
		}
		return rolesDTO;
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
			throws DataEmptyException, NotNullException, InvalidDateException, TaskNotCreatedException {
		
		Tarea tarea = new Tarea(0, name, proyectoDao.find(id_proyecto), priority, user, estado, descripcion, inicio, fin);
		tareaDao.create(tarea);
	}

	
	@Override
	public void eliminarTarea(int id) throws TaskNotFoundException {
		tareaDao.remove(id);
	}

	@Override
	public List<ProyectoDTO> obtenerProyectos(String username) throws NotNullException, DataEmptyException {
		List<ProyectoDTO> proyectoDTO = new ArrayList<>();
		List<Proyecto> proyectos = null;
		try {
			proyectos = proyectoDao.findAll(username);
		} catch (DataEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotNullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Asegúrate de que `proyectos` no sea null antes de iterar
	    if (proyectos != null) {
	        for (Proyecto p : proyectos) {  
	            if (p != null) {
	            	ProyectoDTO dto = convertirEnProyectoDTO(p);
	  
	                if (dto != null) { // Solo agregar si dto no es nulo
	                    proyectoDTO.add(dto);
	                }
	            }
	        }
	    }

	    return proyectoDTO;
	}

	@Override
	public void eliminarProyecto(int id) throws TaskNotFoundException, DataEmptyException, NotNullException, InvalidDateException, TaskQueryException {
		proyectoDao.remove(id);
		List<Tarea> tareas = tareaDao.findByProject(id);
		for (Tarea tareas1 : tareas) {
			tareaDao.remove(tareas1.getId());		}
	}
	
	@Override
	public void modificarProyecto(int idProyecto, String nuevoNombre, String nuevaPrioridad,
			String nuevaDescripcion) throws NotNullException, DataEmptyException {
	    Proyecto proyectoExistente = new Proyecto(idProyecto, nuevoNombre, null, null, nuevaDescripcion, nuevaPrioridad);

		if(!nuevoNombre.isEmpty()) 
			proyectoExistente.setNombre(nuevoNombre);
		if(!nuevaPrioridad.isEmpty())
			 proyectoExistente.setPrioridad(nuevaPrioridad);
		if(!nuevaDescripcion.isEmpty())
			 proyectoExistente.setDescripcion(nuevaDescripcion);
		
		proyectoDao.update(proyectoExistente);
		}
		

	@Override
	public List<TareaDTO> obtenerTareasPorProyecto(int id) throws InvalidDateException, NotNullException, DataEmptyException, TaskQueryException {
		List<TareaDTO> tareasDTO = new ArrayList<>();
		List<Tarea> tareas = null;
		tareas = tareaDao.findByProject(id);

		for (Tarea t : tareas) {  
			tareasDTO.add(convertirEnTareaDTO(t));
	    }
	    return tareasDTO;
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
	
	public TareaDTO getTareaActual() throws NotNullException, DataEmptyException, InvalidDateException {
		return convertirEnTareaDTO(tareaActual);
	}

	@Override
	public void setUsuarioActual(String nombreUsuario) {
	    Usuario usuario = usuarioDao.find(nombreUsuario);
	    if (usuario != null) {
	        this.usuarioActual = usuario; // Asigna el usuario encontrado
	    } 
	}

	@Override
	public void modificarTarea(int id, String nuevoNombre, String nuevaPrioridad, String nombreUsuario, String estado, String nuevaDescripcion, LocalDate inicio, LocalDate fin) throws NotNullException, DataEmptyException, InvalidDateException, TaskNotUpdatedException, TaskQueryException {
		
		Tarea tarea = tareaDao.find(id);
		
		//en los setters de tareas están programadas las excepciones que verifican que éstos datos no sean null o empty
		tarea.setNombre(nuevoNombre);
		tarea.setPrioridad(nuevaPrioridad);
		tarea.setUsuario(nombreUsuario);
		tarea.setEstado(estado);
		tarea.setDescripcion(nuevaDescripcion);
		tarea.setInicio(inicio);
		tarea.setFin(fin);
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
	            return 0; // En caso de prioridad desconocida
	    }
	}

	@Override
	public UsuarioDTO obtenerUsuario(String username) throws NotNullException, DataEmptyException {
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
   
	
	public void invitarMiembro(String username, int idProyecto, int codigoRol) {
		proyectoDao.update(username, idProyecto, codigoRol);
	}

	@Override
	public List<UsuarioDTO> obtenerMiembrosDeUnProyecto(int proyectoId) {
		List<Usuario> miembros = proyectoDao.findAllMembers(proyectoId);
		List<UsuarioDTO> miembrosDTO = new ArrayList<UsuarioDTO>();
		for (Usuario usuario : miembros) {
			miembrosDTO.add(convertirEnUsuarioDTO(usuario));
		}
		
		return miembrosDTO;
	}

	@Override
	public int existeMiembro(String username, int idProyecto) throws UserIsAlreadyMember {
		int existe = 0; //indica que NO existe
		List<UsuarioDTO> miembrosDTO = obtenerMiembrosDeUnProyecto(idProyecto);
		for (UsuarioDTO miembro : miembrosDTO) {
			if(username.equals(miembro.getUsername())){
				existe = 1;
				throw new UserIsAlreadyMember("El usuario ya es miembro del proyecto.");
			}
		}
		return existe;
	}


}