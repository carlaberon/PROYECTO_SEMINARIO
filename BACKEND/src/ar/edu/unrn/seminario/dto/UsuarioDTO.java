package ar.edu.unrn.seminario.dto;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
//import ar.edu.unrn.seminario.modelo.Rol;

public class UsuarioDTO {
	private String username;
	private String password;
	private String nombre;
	private String email;
	private RolDTO rol;
	private boolean activo;


	public UsuarioDTO(String username, String password, String nombre, String email, RolDTO rol2, boolean activo) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(username))
			throw new NotNullException("nombre de usuario");
    	if (esDatoNulo(password))
			throw new NotNullException("contrasenia");
    	if (esDatoNulo(nombre))
			throw new NotNullException("nombre");
    	if (esDatoNulo(email))
			throw new NotNullException("email");
    	
    	if (esDatoVacio(username))
			throw new NotNullException("nombre de usuario");
    	if (esDatoVacio(password))
			throw new NotNullException("contrasenia");
    	if (esDatoVacio(nombre))
			throw new NotNullException("nombre");
    	if (esDatoVacio(email))
			throw new NotNullException("email");
		
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

	public void setUsername(String username) throws NotNullException {
    	if (esDatoNulo(username))
			throw new NotNullException("nombre de usuario");
    	
    	if (esDatoVacio(username))
			throw new NotNullException("nombre de usuario");
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws NotNullException {
    	if (esDatoNulo(password))
			throw new NotNullException("contrasenia");
    	
    	if (esDatoVacio(password))
			throw new NotNullException("contrasenia");
	
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) throws NotNullException {
    	if (esDatoNulo(nombre))
			throw new NotNullException("nombre");
    	
    	if (esDatoVacio(nombre))
			throw new NotNullException("nombre");
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws NotNullException {
    	if (esDatoNulo(email))
			throw new NotNullException("email");
    	
    	if (esDatoVacio(email))
			throw new NotNullException("email");
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
	private boolean esDatoVacio(String dato) {
		return dato.equals("");
	}

	private boolean esDatoNulo(String dato) {
		return dato == null;
	}



}
