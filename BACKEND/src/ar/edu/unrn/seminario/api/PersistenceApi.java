package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import ar.edu.unrn.seminario.exception.TaskNotUpdatedException;
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
		Rol rol = rolDao.find(codigoRol);
		Usuario usuario = new Usuario(username, password, nombre, email, rol);
		this.usuarioDao.create(usuario);
	}
	
	@Override
	public List<TareaDTO> obtenerTareas() throws NotNullException, InvalidDateException, DataEmptyException {
		List<TareaDTO> tareasDTO = new ArrayList<>();
		List<Tarea> tareas = null;
		try {
			tareas = tareaDao.findTareas(proyectoActual.getNombre(), usuarioActual.getUsername());
		} catch (DataEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotNullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		Rol rol = rolDao.find(codigo);
		RolDTO rolDTO = new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo());
		return rolDTO;
	}
	@Override
	public void crearProyecto(String nombre, String string, boolean estado, String descripcion, String prioridad)
			throws NotNullException, DataEmptyException {
		
		Usuario propietario = usuarioDao.find(string);
	    Proyecto nuevoProyecto = new Proyecto(nombre, propietario, estado, descripcion, prioridad);
	    proyectoDao.create(nuevoProyecto);
	}
	

	private RolDTO convertirEnRolDTO(Rol rol) {
		RolDTO rolDto = new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo());
		return null;
	}

	@Override
	public void registrarTarea(int id, String name, String project, String usuarioPropietario, String priority, String user, boolean estado,
			String descripcion, LocalDate inicio, LocalDate fin)
			throws DataEmptyException, NotNullException, InvalidDateException {
		Tarea tarea = new Tarea(id, name, project, usuarioPropietario, priority, user, estado, descripcion, inicio, fin);
		tareaDao.create(tarea);
	}

	@Override
	public void añadirTareaAProyecto(String proyecto, Tarea tarea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarTarea(String nombreTarea, String proyecto, String usuarioPropietario) {
		tareaDao.remove(nombreTarea, proyecto, usuarioPropietario);
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
	public void eliminarProyecto(int id) {
		proyectoDao.remove(id);
	}
	
	@Override
	public void modificarProyecto(String nombreProyecto, String usuarioPropietario,String nuevoNombre, String nuevaPrioridad,
			String nuevaDescripcion) throws NotNullException, DataEmptyException {
		Proyecto proyectoExistente = proyectoDao.find(nombreProyecto, usuarioPropietario);
	    if (proyectoExistente == null) {
	        throw new DataEmptyException("El proyecto no existe.");
	    }
	    String nombreOriginal = proyectoExistente.getNombre();
		if(nuevoNombre != null) 
			proyectoExistente.setNombre(nuevoNombre);
		if(nuevaPrioridad != null)
			 proyectoExistente.setPrioridad1(nuevaPrioridad);
		if(nuevaDescripcion != null)
			 proyectoExistente.setDescripcion(nuevaDescripcion);
		
		proyectoDao.update(proyectoExistente,nombreOriginal);
		}
		
		
	
	@Override
	public void asignarPrioridad(String nombreProyecto, String prioridad) throws NotNullException, DataEmptyException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compare(Proyecto p1, Proyecto p2) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<TareaDTO> obtenerTareasPorProyecto(String nombreProyecto, String usuarioPropietario) throws InvalidDateException, NotNullException, DataEmptyException {
		List<TareaDTO> tareasDTO = new ArrayList<>();
		List<Tarea> tareas = null;
		try {
			tareas = tareaDao.findTareas(nombreProyecto, usuarioPropietario);
		} catch (DataEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotNullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    for (Tarea t : tareas) {  
		        tareasDTO.add(convertirEnTareaDTO(t));
	    }

	    return tareasDTO;
	}

	@Override
	public int obtenerValorPrioridad(String prioridad) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public ProyectoDTO getProyectoActual() throws NotNullException, DataEmptyException {
		return convertirEnProyectoDTO(proyectoActual);
	}

	@Override
	public void setProyectoActual(String nombreProyecto) throws NotNullException, DataEmptyException {
			String usuarioActual = getUsuarioActual().getUsername();
			if (! usuarioActual.isEmpty()) {
				this.proyectoActual = proyectoDao.find(nombreProyecto, usuarioActual);
			}
			else {
				throw new NullPointerException();
			}
			
		
	}
   /*
	@Override
	public UsuarioDTO getUsuarioActual() {
		Usuario aux = usuarioDao.find(string);
		return convertirEnUsuarioDTO(aux);
	};*/

	@Override
	public void setUsuarioActual(String nombreUsuario) {
	    Usuario usuario = usuarioDao.find(nombreUsuario);
	    if (usuario != null) {
	        this.usuarioActual = usuario; // Asigna el usuario encontrado
	    } else {
	        throw new IllegalArgumentException("Usuario no encontrado: " + nombreUsuario);
	    }
	}

	@Override
	public void modificarTarea(int id, String nombreProyecto, String nuevoNombre, String nuevaPrioridad, String nombreUsuario, Boolean estado, String nuevaDescripcion, LocalDate inicio, LocalDate fin) throws NotNullException, DataEmptyException, InvalidDateException, TaskNotUpdatedException {
		Tarea tareaExistente = tareaDao.find(id);
		
		if (tareaExistente != null) {
			if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
				tareaExistente.setNombre(nuevoNombre);
			}
			
		    // Validar y actualizar la prioridad
		    if (nuevaPrioridad != null && !nuevaPrioridad.isEmpty()) {
		        tareaExistente.setPrioridad(nuevaPrioridad);
		    }
		    
		    // Validar y actualizar el usuario
	        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
	            tareaExistente.setUsuario(nombreUsuario);
	        }
	        
	        // Validar y actualizar la descripción
	        if (nuevaDescripcion != null && !nuevaDescripcion.isEmpty()) {
	            tareaExistente.setDescripcion(nuevaDescripcion);
	        }

	        // Validar y actualizar la fecha de inicio
	        if (inicio != null) {
	            tareaExistente.setInicio(inicio);
	        }

	        // Validar y actualizar la fecha de fin
	        if (fin != null) {
	            tareaExistente.setFin(fin);
	        }
	        
	        if (estado != null) {
	        	tareaExistente.setEstado(estado);
	        }
	        
	        tareaDao.update(tareaExistente, id);
	        System.out.println("Tarea modificada exitosamente.");
	    } else {
	        System.out.println("No se encontró la tarea para modificar.");
	    }

	    /*// Lanzar excepción si la tarea no se encontró
	    if (tareaExistente == null) {
	        throw new DataEmptyException("No se encontró la tarea con el nombre especificado.");
	    }*/
		
	// cosas que creo que funcionan
}
	
	@Override
	public int obtenerPrioridad(String prioridad) {
	    switch (prioridad) {
	        case "alta":
	            return 1;
	        case "media":
	            return 2;
	        case "baja":
	            return 3;
	        default:
	            return 0; // En caso de prioridad desconocida
	    }
	}
	
	
	//ACA PONDRE LOS MODULOS QUE CONSIDERO QUE NO SON NECESARIOS:
	

	@Override
	public UsuarioDTO obtenerUsuario(String username) throws NotNullException, DataEmptyException {
		Usuario usuario = usuarioDao.find(username);
		if (usuario != null){
			 UsuarioDTO userDTO = new UsuarioDTO(
			            usuario.getUsername(),
			            usuario.getContrasena(),
			            usuario.getNombre(),
			            usuario.getEmail(),
			            convertirEnRolDTO(usuario.getRol()), // Asegúrate de que este método existe y convierte el Rol a RolDTO
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


	private UsuarioDTO convertirEnUsuarioDTO(Usuario usuario) throws NotNullException, DataEmptyException {
		UsuarioDTO usuarioDto = new UsuarioDTO(usuario.getUsername(), usuario.getContrasena(), usuario.getNombre(), 
				usuario.getEmail(), convertirEnRolDTO(usuario.getRol()), usuario.isActivo());
		return usuarioDto;
	}
	
	private TareaDTO convertirEnTareaDTO(Tarea tarea) throws NotNullException, InvalidDateException, DataEmptyException {
		TareaDTO tareaDto = new TareaDTO(tarea.getId(), tarea.getNombre(), tarea.getProyecto(), tarea.getUsuarioPropietario(),
				tarea.getPrioridad(), tarea.getUsuario(), tarea.isEstado(), tarea.getDescripcion(), tarea.getInicio(), tarea.getFin());
		return tareaDto;
	}
	
	private ProyectoDTO convertirEnProyectoDTO(Proyecto proyecto) throws NotNullException, DataEmptyException {
		ProyectoDTO proyectoDto = null;
		if(proyecto != null)
			proyectoDto = new ProyectoDTO(proyecto.getNombre(), convertirEnUsuarioDTO(proyecto.getUsuarioPropietario()), proyecto.getEstado(), proyecto.getPrioridad1(), proyecto.getDescripcion());
			proyectoDto.setId(proyecto.getId());
		return proyectoDto;
	}
	
	public UsuarioDTO getUsuarioActual() throws NotNullException, DataEmptyException {
	    if (usuarioActual == null) {
	        throw new IllegalStateException("El usuario actual no ha sido establecido.");
	    }
	    return convertirEnUsuarioDTO(usuarioActual);
	}
	
}