package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;

public class Notificacion {
	private String descripcion;
	private LocalDate fecha;

	
	
	public Notificacion(String nombreProyecto,LocalDate fechaGenerada) throws NotNullException, DataEmptyException {
		
  	    if (esDatoNulo(nombreProyecto)) {
	    	throw new NotNullException("menu.nombreProyecto");
	    }
	    
	    if (esDatoVacio(nombreProyecto)) {
	    	throw new DataEmptyException("menu.nombreProyecto");
	    }
	    
	    this.descripcion = "Te invitaron al proyecto: " + nombreProyecto;
		this.fecha = fechaGenerada;
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
		return "Notificacion [descripcion=" + descripcion + ", fecha=" + fecha + "]";
	}



}
