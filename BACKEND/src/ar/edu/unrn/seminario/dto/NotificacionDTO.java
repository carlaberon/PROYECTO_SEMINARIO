package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class NotificacionDTO {
	private String descripcion;
	private LocalDate fecha;

	
	public NotificacionDTO(String descripcion, LocalDate fecha) {
		this.descripcion = descripcion;
		this.fecha = fecha;
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
	public String toString() {
		return "NotificacionDTO [descripcion=" + descripcion + ", fecha=" + fecha + "]";
	}



	
	
}
