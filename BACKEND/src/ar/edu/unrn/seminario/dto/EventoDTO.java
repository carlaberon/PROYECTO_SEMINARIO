package ar.edu.unrn.seminario.dto;

import java.time.LocalDateTime;

public class EventoDTO {
	private LocalDateTime fecha;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private String descripcion;
    
    public EventoDTO(LocalDateTime fecha, LocalDateTime inicio, LocalDateTime fin, String descripcion) {
    	super();
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.descripcion = descripcion;
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
    
    
}
