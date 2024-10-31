package ar.edu.unrn.seminario.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDao;
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
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Tarea;
import ar.edu.unrn.seminario.modelo.Usuario;

public class PersistenceApi implements IApi {

	private RolDao rolDao;
	private UsuarioDao usuarioDao;
	private ProyectoDao proyectoDao;
	private Usuario usuarioActual;
	public PersistenceApi() {
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
		proyectoDao = new ProyectoDAOJDBC();
	}

	@Override
	public void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol) {
		Rol rol = rolDao.find(codigoRol);
		Usuario usuario = new Usuario(username, password, nombre, email, rol);
		this.usuarioDao.create(usuario);
	}

	@Override
	public List<UsuarioDTO> obtenerUsuarios() {
		List<UsuarioDTO> dtos = new ArrayList<>();
		List<Usuario> usuarios = usuarioDao.findAll();
		for (Usuario u : usuarios) {
			dtos.add(new UsuarioDTO(u.getUsername(), u.getContrasena(), u.getNombre(), u.getEmail(),
					convertirEnRolDTO(u.getRol()), u.isActivo()));
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
	public void registrarTarea(String name, String project, String priority, String user, boolean estado,
			String descripcion, LocalDateTime inicio, LocalDateTime fin)
			throws DataEmptyException, NotNullException, InvalidDateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TareaDTO> obtenerTareas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void añadirTareaAProyecto(String proyecto, Tarea tarea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarTarea(String nombreTarea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProyectoDTO> obtenerProyectos(String username) {
		// TODO Auto-generated method stub
		return null;
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
	public void eliminarProyecto(String nombreProyecto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarProyecto(String nombreProyecto, String nuevoNombre, String nuevaPrioridad,
			String nuevaDescripcion) throws NotNullException, DataEmptyException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TareaDTO> obtenerTareasPorProyecto(String nombreProyecto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int obtenerValorPrioridad(String prioridad) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public ProyectoDTO getProyectoActual() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProyectoActual(String nombreProyecto) {
		// TODO Auto-generated method stub
		
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
	public void modificarTarea(String nomb, String nombreProyecto, String nuevoNombre, String nuevaPrioridad,
			String nombre, Boolean estado, String nuevaDescripcion, LocalDateTime inicio, LocalDateTime fin)
			throws NotNullException, DataEmptyException {
		// TODO Auto-generated method stub
		
	}
	// cosas que creo que funcionan
	
	
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
	public UsuarioDTO obtenerUsuario(String username) {
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
		// TODO Auto-generated method stub

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


	private UsuarioDTO convertirEnUsuarioDTO(Usuario usuario) {
		UsuarioDTO usuarioDto = new UsuarioDTO(usuario.getUsername(), usuario.getContrasena(), usuario.getNombre(), usuario.getEmail(), convertirEnRolDTO(usuario.getRol()), usuario.isActivo());
		return usuarioDto;
	}
	public UsuarioDTO getUsuarioActual() {
	    if (usuarioActual == null) {
	        throw new IllegalStateException("El usuario actual no ha sido establecido.");
	    }
	    return convertirEnUsuarioDTO(usuarioActual);


}
}