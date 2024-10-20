package ar.edu.unrn.seminario.dto;

import ar.edu.unrn.seminario.modelo.Rol;

public class UsuarioDTO {
	private String username;
	private String password;
	private static String nombre;
	private String email;
	private RolDTO rol;
	private boolean activo;


	public UsuarioDTO(String username, String password, String nombre, String email, RolDTO rol2, boolean activo) {
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.email = email;
		
		this.rol = rol2;
		this.activo = activo;
	
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static String getNombre() {
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

	public RolDTO getRol() {
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public static void add(UsuarioDTO user1) {
		// TODO Auto-generated method stub
		
	}



}
