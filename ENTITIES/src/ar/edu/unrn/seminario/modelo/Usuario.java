
package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.StateChangeException;

public class Usuario{
	private String usuario;
	private String contrasena;
	private String nombre;
	private String email;
	private Rol rol;
	private Boolean activo;


	public Usuario(String usuario, String contrasena, String nombre, String email, Rol rol, Boolean activo) {

		this.usuario = usuario;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.email = email;
		this.rol = rol;
		this.activo = activo;
	}
	
	public Usuario(String username, String password, String nombre, String email, Rol rol) {
        this.usuario = username;
        this.contrasena = password;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.activo = true;  // Puedes ajustar este valor según sea necesario
    }

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public String obtenerEstado() {
		return isActivo() ? "ACTIVO" : "INACTIVO";
	}

	public void activar() throws RuntimeException{
		if (isActivo()) {
			
			throw new StateChangeException("El usuario ya está activado");
			
		}
		if (!isActivo())
			this.activo = true;
		
	}

	public void desactivar() throws RuntimeException{
		
		if (!isActivo()) {
			throw new StateChangeException("El usuario ya está desactivado");
		}
		
		if (isActivo())
			this.activo = false;
	
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}

