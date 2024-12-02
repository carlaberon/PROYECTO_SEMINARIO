package ar.edu.unrn.seminario.dto;

import java.time.LocalDateTime;


public class NotificacionDTO {
	private String descripcion;
	private LocalDateTime fecha;

	
	public NotificacionDTO(String descripcion, LocalDateTime fecha) {
	
		this.descripcion = descripcion;
		this.fecha = fecha;

	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "NotificacionDTO [descripcion=" + descripcion + ", fecha=" + fecha + "]";
	}



	
	
}
