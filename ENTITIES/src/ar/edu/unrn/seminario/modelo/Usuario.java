
package ar.edu.unrn.seminario.modelo;

import java.util.Objects;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.StateChangeException;

public class Usuario{
	private String username;
	private String contrasena;
	private String nombre;
	private String email;
	private Rol rol;
	private Boolean activo;

	
	public Usuario(String usuario, String contrasena, String nombre, String email, Rol rol, Boolean activo) throws NotNullException, DataEmptyException {

    	if (esDatoNulo(usuario))
			throw new NotNullException("usuario");
    	if (esDatoNulo(contrasena))
			throw new NotNullException("contrasena");
    	if (esDatoNulo(nombre))
			throw new NotNullException("nombre");
    	if (esDatoNulo(email))
			throw new NotNullException("email");
    	
		if (esDatoVacio(usuario))
			throw new DataEmptyException("usuario");
		if (esDatoVacio(contrasena))
			throw new DataEmptyException("contrasena");
		if (esDatoVacio(nombre))
			throw new DataEmptyException("nombre");
		if (esDatoVacio(email))
			throw new DataEmptyException("email");
    	
		this.username = usuario;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.email = email;
		this.rol = rol;
		this.activo = activo;
	}
	
	public Usuario(String username, String password, String nombre, String email, Rol rol) {
        this.username = username;
        this.contrasena = password;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.activo = true;  // Puedes ajustar este valor según sea necesario
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String usuario) throws NotNullException, DataEmptyException {
		if (esDatoNulo(usuario))
			throw new NotNullException("usuario");
    	
		if (esDatoVacio(usuario))
			throw new DataEmptyException("usuario");
		this.username = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) throws NotNullException, DataEmptyException {
		if (esDatoNulo(contrasena))
			throw new NotNullException("contrasena");
    	
		if (esDatoVacio(contrasena))
			throw new DataEmptyException("contrasena");
		
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) throws NotNullException, DataEmptyException {
		if (esDatoNulo(nombre))
			throw new NotNullException("nombre");
    	
		if (esDatoVacio(nombre))
			throw new DataEmptyException("nombre");
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws NotNullException, DataEmptyException {
		if (esDatoNulo(email))
			throw new NotNullException("email");
    	
		if (esDatoVacio(email))
			throw new DataEmptyException("email");
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
	private boolean esDatoVacio(String dato) {
		return dato.equals("");
	}

	private boolean esDatoNulo(String dato) {
		return dato == null;
	}
	

	// Sobrescrir equals() para comparar usuarios por el username
	// Sobrescribimos equals() para comparar usuarios por el username
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Si es el mismo objeto, son iguales
        if (obj == null || getClass() != obj.getClass()) return false;  // Verificamos que sean de la misma clase
        Usuario usuario = (Usuario) obj;  // Hacer cast seguro
        return Objects.equals(username, usuario.username);  // Comparar por username
    }
    
    // Sobrescrir hashCode() usando el username
    @Override
    public int hashCode() {
        return Objects.hash(username);  // Generar un hash basado solo en el username
    }
    @Override
    public String toString() {
        return "Usuario{" +
               "username='" + username + '\'' +
               ", contrasena='" + contrasena + '\'' +
               ", nombre='" + nombre + '\'' +
               ", email='" + email + '\'' +
               ", rol=" + rol +
               ", activo=" + activo +
               '}';
    }
    
}

