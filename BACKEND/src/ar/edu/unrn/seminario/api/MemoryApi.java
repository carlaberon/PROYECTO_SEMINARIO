package ar.edu.unrn.seminario.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.dto.EventoDTO;
import ar.edu.unrn.seminario.dto.MiembroDTO;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TareaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Evento;
import ar.edu.unrn.seminario.modelo.Miembro;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Tarea;
import ar.edu.unrn.seminario.modelo.Usuario;

public class MemoryApi implements IApi {

	private Set<Rol> roles = new HashSet<>();
	private Set<Usuario> usuarios = new HashSet<>();
	private List<Tarea> tareas = new ArrayList<>();
	private Set<Proyecto> proyectos = new HashSet<>();
	private Set<Evento> eventos = new HashSet<>();
	//private Set<Plan> planeSet = new HashSet<>();
	private List<TareaDTO> tareasDTO;
	private Map<String, List<TareaDTO>> tareasPorProyecto = new HashMap<>();



	public MemoryApi() throws NotNullException, DataEmptyException {
		//Set<Proyecto> proyectos
		// datos iniciales
		this.roles.add(new Rol(1, "PROPIETARIO"));
		this.roles.add(new Rol(2, "OBSERVADOR"));
		this.roles.add(new Rol(3, "COLABORADOR"));
		inicializarUsuarios();
		inicializarProyecto();

		
	}
	

	private void inicializarProyecto() throws NotNullException, DataEmptyException{
	    // Crear algunos usuarios para asignar a los proyectos
	    Usuario user1 = new Usuario("HernanPro", "12", "Hernan", "eze@gmail.com", this.buscarRol(2)); // Observador
	    Usuario user2 = new Usuario("bjgorosito", "1234", "Bruno", "bjgorosito@unrn.edu.ar", this.buscarRol(3)); // Colaborador
	    Usuario user3 = new Usuario("Tomas", "12345", "Pepe", "admin@unrn.edu.ar", this.buscarRol(1)); // Propietario

	    crearProyecto("Sistema de Gestión de Tareas", user1, true,"Sistema para gestionar tareas en equipo.", "media");

	    LocalDateTime inicio = LocalDateTime.now();
	    Tarea unaTarea = new Tarea("Ordenar tareas","Sistema de Gestión de Tareas","alta", user1.getNombre(), false, "descripcion", inicio, inicio); 
	    añadirTareaAProyecto("Sistema de Gestión de Tareas", unaTarea);
	    
	    crearProyecto("Aplicación de votos", user2, false, "Aplicación para contar los votos de la municipalidad","alta");
	    LocalDateTime inicio2 = LocalDateTime.now();
	    Tarea otraTarea = new Tarea("Contar votos","Aplicación de votos","alta", user1.getNombre(), false, "Contar los votos disponibles", inicio2, inicio2);
	    añadirTareaAProyecto("Aplicación de votos", otraTarea);
	    
	    crearProyecto("Gestion de eventos", user3, true, "Proyecto para desarrollar gestion de los eventos de ","baja");
	    LocalDateTime inicio3 = LocalDateTime.now();
	    Tarea tarea_ = new Tarea("Ordenar eventos","Gestion de eventos","alta", user1.getNombre(), false, "Ordenar eventos por prioridad", inicio2, inicio3);
	    añadirTareaAProyecto("Gestion de eventos", tarea_);
	    
	    
	    crearProyecto("Parciales", user1, false, "Informacion sobre como completar la informacion de los parciales de la carrera","media");
	    LocalDateTime inicio4 = LocalDateTime.now();
	    Tarea tarea_1 = new Tarea("Denifir plan de estudio","Parciales","alta", user1.getNombre(), false, "Definir plan de estudio", inicio2, inicio4);
	    añadirTareaAProyecto("Parciales", tarea_1);


	}


	private void inicializarUsuarios() {
		registrarUsuario("admin", "1234", "admin@unrn.edu.ar", "Admin", 1);
		registrarUsuario("ldifabio", "4", "ldifabio@unrn.edu.ar", "Lucas", 2);
		registrarUsuario("bjgorosito", "1234", "bjgorosito@unrn.edu.ar", "Bruno", 3);

	}

	@Override
	public void registrarUsuario(String username, String password, String email, String nombre, Integer rol) {

		Rol role = this.buscarRol(rol);
		Usuario usuario = new Usuario(username, password, nombre, email, role);
		this.usuarios.add(usuario);

	}
	


	@Override
	public List<UsuarioDTO> obtenerUsuarios() {
		List<UsuarioDTO> dtos = new ArrayList<>();
		for (Usuario u : this.usuarios) {
			dtos.add(new UsuarioDTO(u.getUsuario(),u.getContrasena(), u.getNombre(), u.getEmail(), u.getRol(), u.isActivo()));
		}
		return dtos;
	}

	@Override
	public UsuarioDTO obtenerUsuario(String username) {
		for(Usuario u: this.usuarios) {
			if (u.getUsuario().equals(username)) {
				
				UsuarioDTO user = new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getNombre(), u.getEmail(), u.getRol(), u.isActivo());
				return user;
			}
		}
		
		return null;
	}

	@Override
	public void eliminarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RolDTO> obtenerRoles() {
		List<RolDTO> dtos = new ArrayList<>();
		for (Rol r : this.roles) {
			dtos.add(new RolDTO(r.getCodigo(), r.getNombre()));
		}
		return dtos;
	}

	@Override
	public List<RolDTO> obtenerRolesActivos() {
		List<RolDTO> dtos = new ArrayList<>();
		for (Rol r : this.roles) {
			if (r.isActivo())
				dtos.add(new RolDTO(r.getCodigo(), r.getNombre()));
		}
		return dtos;
	}

	@Override
	public void guardarRol(Integer codigo, String descripcion, boolean estado) {
		// TODO Auto-generated method stub
		Rol rol = new Rol(codigo, descripcion);
		this.roles.add(rol);
	}

	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) {
		// TODO Auto-generated method stub
		return null;
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
	public void activarUsuario(String usuario) {
		Usuario user = this.buscarUsuario(usuario);
		user.activar();
	}

	@Override
	public void desactivarUsuario(String usuario) {
		// TODO: desactivar usuario
		Usuario user = this.buscarUsuario(usuario);
		user.desactivar();
	}

	private Rol buscarRol(Integer codigo) {
		for (Rol rol : roles) {
			if (rol.getCodigo().equals(codigo))
				return rol;
		}
		return null;
	}

	private Usuario buscarUsuario(String usuario) {
		for (Usuario user : usuarios) {
			if (user.getUsuario().equals(usuario))
				return user;
		}
		return null;
	}

	public void registrarTarea(String name, String project, String priority, Usuario user, boolean estado, String descripcion, LocalDateTime inicio, LocalDateTime fin) throws DataEmptyException, NotNullException {
	    Tarea tarea = new Tarea(name, project, priority, user.getNombre(), estado, descripcion, inicio, fin);
	    this.tareas.add(tarea); // Agrega la tarea a la lista de tareas
	}
	
	public List<TareaDTO> obtenerTareas() {
	    List<TareaDTO> tareasDTO = new ArrayList<>();
	    for (Tarea t : this.tareas) {  // Asegúrate de que `this.tareas` tiene elementos
	        tareasDTO.add(new TareaDTO(t.getNombre(), t.getProyecto(), t.getPrioridad(), t.getUsuario(), t.isEstado(), t.getDescripcion(), null, null));
	    }
	    return tareasDTO;
	}
	
	public void añadirTareaAProyecto(String proyecto, Tarea unaTarea) {

		TareaDTO tarea = new TareaDTO(
				unaTarea.getNombre(), 
				unaTarea.getProyecto(), 
				unaTarea.getPrioridad(), 
				unaTarea.getUsuario(), 
				unaTarea.isEstado(), 
				unaTarea.getDescripcion(), 
				unaTarea.getInicio(), 
				unaTarea.getFin());

		tareasPorProyecto.putIfAbsent(proyecto, new ArrayList<>());
		tareasPorProyecto.get(proyecto).add(tarea);
	}
	
	public List<TareaDTO> obtenerTareasPorProyecto(String nombreProyecto) throws RuntimeException {

		return tareasPorProyecto.getOrDefault(nombreProyecto, new ArrayList<>());
    }
    
	@Override
	public void crearEvento(LocalDateTime fecha, LocalDateTime inicio, LocalDateTime fin, String descripcion) {
		// TODO Auto-generated method stub
		Evento evento = new Evento(fecha, inicio, fin, descripcion);
		this.eventos.add(evento);
	}


	@Override
	public List<EventoDTO> obtenerEventos() {
		List<EventoDTO> eventosDTO = new ArrayList<>();
		for (Evento evento : this.eventos) {
	        // Convertir cada Evento en EventoDTO
	        EventoDTO eventoDTO = new EventoDTO(
	            evento.getFecha(),
	            evento.getInicio(),
	            evento.getFin(),
	            evento.getDescripcion()
	        );
	        
	        eventosDTO.add(eventoDTO); // Agregar a la lista de DTOs
	    }
		return eventosDTO;
	}
	
	// Implementación del método para obtener la lista de proyectos como DTO
    @Override
    public List<ProyectoDTO> obtenerProyectos() {
        List<ProyectoDTO> dtos = new ArrayList<>();
        for (Proyecto p : this.proyectos) {
            dtos.add(new ProyectoDTO(p.getNombre(), new UsuarioDTO(p.getUsuarioPropietario().getUsuario(), p.getUsuarioPropietario().getContrasena(), p.getUsuarioPropietario().getNombre(), p.getUsuarioPropietario().getEmail(), p.getUsuarioPropietario().getRol(), p.getUsuarioPropietario().isActivo()), p.getEstado(), p.getPrioridad1(), p.getDescripcion()));
        }
        return dtos;
    }
	
    // Implementación del método para asignar prioridad a un proyecto
    @Override
    public void asignarPrioridad(String nombreProyecto, String prioridad) {
        for (Proyecto p : this.proyectos) {
            if (p.getNombre().equals(nombreProyecto)) {
                p.setPrioridad1(prioridad);
                break;
            }
        }
    }
    @Override
    public int compare(Proyecto p1, Proyecto p2) {
        // Ordenar por prioridad (alta, media, baja)
        List<String> prioridades = Arrays.asList("alta", "media", "baja");
        return Integer.compare(prioridades.indexOf(p1.getPrioridad1()), prioridades.indexOf(p2.getPrioridad1()));
    }

	@Override
	public void crearPlan(String nombre, Proyecto pertenece) {
		// Verificar si el proyecto ya tiene un plan asignado
		
		// Crear una nueva instancia de Plan con el nombre y el proyecto
	    Plan nuevoPlan = new Plan(nombre, pertenece);
	    pertenece.setPlan(nuevoPlan);
	}

	@Override
	
	public void crearProyecto(String nombre, Usuario usuarioPropietario, boolean estado, String descripcion, String prioridad) throws NotNullException, DataEmptyException {
	    // Validar que los campos no sean nulos
	    if (esDatoNulo(nombre)) {
	        throw new NotNullException("nombre");
	    }
	    if (esDatoNulo(descripcion)) {
	        throw new NotNullException("descripcion");
	    }
	    if (esDatoNulo(prioridad)) {
	        throw new NotNullException("prioridad");
	    }

	    // Validar que los campos no estén vacíos
	    if (esDatoVacio(nombre)) {
	        throw new DataEmptyException("nombre");
	    }
	    if (esDatoVacio(descripcion)) {
	        throw new DataEmptyException("descripcion");
	    }
	    if (esDatoVacio(prioridad)) {
	        throw new DataEmptyException("prioridad");
	    }
		// Crear un nuevo proyecto con los parámetros recibidos
	    Proyecto nuevoProyecto = new Proyecto(nombre, usuarioPropietario, estado, descripcion, prioridad);

	    
	    // Agregar el proyecto a la colección de proyectos
	    this.proyectos.add(nuevoProyecto);
	}
    
	@Override
	public void eliminarProyecto(String nombreProyecto) {
	    Proyecto proyectoAEliminar = null;

	    // Buscar el proyecto por nombre
	    for (Proyecto proyecto : this.proyectos) {
	        if (proyecto.getNombre().equals(nombreProyecto)) {
	            proyectoAEliminar = proyecto;
	            break;
	        }
	    }

	    // Si se encuentra el proyecto, eliminarlo
	    if (proyectoAEliminar != null) {
	        this.proyectos.remove(proyectoAEliminar);
	        System.out.println("El proyecto '" + nombreProyecto + "' ha sido eliminado.");
	    }	 
	}

	@Override
	public void modificarProyecto(String nombreProyecto, String nuevoNombre, String nuevaPrioridad, String nuevaDescripcion) {
	    Proyecto proyectoExistente;
	    
	    for (Proyecto p : proyectos) {
			if(nombreProyecto == p.getNombre()) {
				proyectoExistente = p;
				if(nuevoNombre != null) 
				proyectoExistente.setNombre(nuevoNombre);
				if(nuevaPrioridad != null)
			    proyectoExistente.setPrioridad1(nuevaPrioridad);
				if(nuevaDescripcion != null)
			    proyectoExistente.setDescripcion(nuevaDescripcion);
				break;
			}
		}
	}
		
	private boolean esDatoVacio(String dato) {
		return dato.equals("");
	}

	private boolean esDatoNulo(String dato) {
		return dato == null;
	}


	@Override
	public void crearProyecto(String nombreProyecto, Usuario usuarioPropietario, boolean b, String string) {
		// TODO Auto-generated method stub
		
	}
	public int obtenerValorPrioridad(String prioridad) {
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
}

