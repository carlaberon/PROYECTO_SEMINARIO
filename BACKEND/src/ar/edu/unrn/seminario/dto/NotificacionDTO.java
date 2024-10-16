package ar.edu.unrn.seminario.dto;

import java.time.LocalDateTime;


public class NotificacionDTO {
	private String nombre;
	private String descripcion;
	private LocalDateTime fecha;
	private boolean visto;
	
	public NotificacionDTO(String nombre, String descripcion, LocalDateTime fecha, boolean visto) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.visto = visto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public boolean isVisto() {
		return visto;
	}

	public void setVisto(boolean visto) {
		this.visto = visto;
	}

	@Override
	public String toString() {
		return "NotificacionDTO [nombre=" + nombre + ", descripcion=" + descripcion + ", fecha=" + fecha + ", visto="
				+ visto + "]";
	}
	
}
