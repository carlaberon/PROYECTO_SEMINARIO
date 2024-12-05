package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class NotificacionDTO {
	private int idProyecto;
	private String username;
	private int codigoRol;
	private String descripcion;
	private LocalDate fecha;
	
	public NotificacionDTO(int idProyecto, String username, int codigoRol,String descripcion, LocalDate fecha) {
		this.idProyecto = idProyecto;
		this.username = username;
		this.codigoRol = codigoRol;
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
		return "NotificacionDTO [idProyecto=" + idProyecto + ", username=" + username + ", codigoRol=" + codigoRol
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
