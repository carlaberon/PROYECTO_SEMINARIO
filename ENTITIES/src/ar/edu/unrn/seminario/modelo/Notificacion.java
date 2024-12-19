package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.util.Objects;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;

public class Notificacion {
	private int idProyecto;
	private String username;
	private int codigoRol;
	private String descripcion;
	private LocalDate fecha;

	
	
	public Notificacion(int idProyecto, String username, int codigoRol,String descripcion, LocalDate fechaGenerada) throws NotNullException, DataEmptyException {
		
		if (esDatoNulo(username)) {
		    throw new NotNullException("validacion.usuario");
		}
		    
		if (esDatoVacio(username)) {
		    throw new DataEmptyException("validacion.usuario");
		}
		
		if (codigoRol == 0) {
	    	throw new DataEmptyException("menu.rol");
	    }
	    
	    this.descripcion = descripcion;
		this.fecha = fechaGenerada;
		this.idProyecto = idProyecto;
		this.username = username;
		this.codigoRol = codigoRol;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(descripcion, fecha);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notificacion other = (Notificacion) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(fecha, other.fecha);
	}
	private boolean esDatoVacio(String dato) {
		return dato.equals("");
	}

	private boolean esDatoNulo(String dato) {
		return dato == null;
	}

	@Override
	public String toString() {
		return "Notificacion [idProyecto=" + idProyecto + ", username=" + username + ", codigoRol=" + codigoRol
				+ ", descripcion=" + descripcion + ", fecha=" + fecha + "]";
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public int getIdProyecto() {
		return idProyecto;
	}


	public void setIdProyecto(int idProyecto) {
		this.idProyecto = idProyecto;
	}


	public int getCodigoRol() {
		return codigoRol;
	}


	public void setCodigoRol(int codigoRol) {
		this.codigoRol = codigoRol;
	}



}
