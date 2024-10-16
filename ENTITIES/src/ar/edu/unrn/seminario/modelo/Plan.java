package ar.edu.unrn.seminario.modelo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Plan {
	private String nombre;
	private Proyecto pertenece;
	private Set<Evento> eventos = new HashSet<>();
	
	public Plan(String nombre, Proyecto pertenece){
		this.nombre = nombre;
		this.pertenece = pertenece;
	}
	public Plan() {
		
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Proyecto getPertenece() {
		return pertenece;
	}
	public void setPertenece(Proyecto pertenece) {
		this.pertenece = pertenece;
	}
	public Set<Evento> getEventos() {
		return eventos;
	}
	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}
	@Override
	public int hashCode() {
		return Objects.hash(eventos, nombre, pertenece);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plan other = (Plan) obj;
		return Objects.equals(eventos, other.eventos) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(pertenece, other.pertenece);
	}
	
}
