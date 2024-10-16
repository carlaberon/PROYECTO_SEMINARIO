package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;
import java.util.Objects;

public class Notificacion {
	private String nombre;
	private String descripcion;
	private LocalDateTime fecha;
	private boolean visto;
	
	
	public Notificacion(String nombre, String descripcion, LocalDateTime fecha, boolean visto) {
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
	public int hashCode() {
		return Objects.hash(descripcion, fecha, nombre, visto);
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
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(nombre, other.nombre) && visto == other.visto;
	}
}
