package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;
import java.util.Objects;

public class Evento {
	private LocalDateTime fecha;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	private String descripcion;
	
	public Evento(LocalDateTime fecha, LocalDateTime inicio, LocalDateTime fin, String descipcion) {
		this.fecha = fecha;
		this.inicio = inicio;
		this.fin = fin;
		this.descripcion = descipcion;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descripcion, fecha, fin, inicio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(fin, other.fin) && Objects.equals(inicio, other.inicio);
	}
	
}
