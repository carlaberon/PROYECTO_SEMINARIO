package ar.edu.unrn.seminario.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDao;
import ar.edu.unrn.seminario.accesos.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.UsuarioDao;
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

	public PersistenceApi() {
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
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
	public UsuarioDTO obtenerUsuario(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarUsuario(String username) {
		// TODO Auto-generated method stub

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
	public void crearProyecto(String nombre, String string, boolean estado, String descripcion, String prioridad)
			throws NotNullException, DataEmptyException {
		// TODO Auto-generated method stub
		
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

	@Override
	public UsuarioDTO getUsuarioActual() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUsuarioActual(String nombreUsuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarTarea(String nomb, String nombreProyecto, String nuevoNombre, String nuevaPrioridad,
			String nombre, Boolean estado, String nuevaDescripcion, LocalDateTime inicio, LocalDateTime fin)
			throws NotNullException, DataEmptyException {
		// TODO Auto-generated method stub
		
	}

}
