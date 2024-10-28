package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDao;
import ar.edu.unrn.seminario.accesos.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.UsuarioDao;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.modelo.Rol;
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
					u.getRol().getNombre(), u.isActivo(), u.obtenerEstado()));
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

}
