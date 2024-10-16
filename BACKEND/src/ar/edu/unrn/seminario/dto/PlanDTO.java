package ar.edu.unrn.seminario.dto;

import java.util.Set;

public class PlanDTO {
	private String nombre;
	private String pertenece;
	private Set<String> eventos;
	
	public PlanDTO(String nombre, String pertenece, Set<String> eventos) {
		super();
        this.nombre = nombre;
        this.pertenece = pertenece;
        this.eventos = eventos;
    }
	public PlanDTO() {
    }
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPertenece() {
		return pertenece;
	}
	public void setPertenece(String pertenece) {
		this.pertenece = pertenece;
	}
	public Set<String> getEventos() {
		return eventos;
	}
	public void setEventos(Set<String> eventos) {
		this.eventos = eventos;
	}
	@Override
	public String toString() {
		return "PlanDTO [nombre=" + nombre + ", pertenece=" + pertenece + ", eventos=" + eventos + "]";
	}
	
}
