package ar.edu.unrn.seminario.dto;

import java.time.LocalDateTime;

public class MiembroDTO {
	private LocalDateTime fechaAlta;
    private LocalDateTime fechaBaja;
    private String codigo;
    
    public MiembroDTO(LocalDateTime alta, LocalDateTime baja, String codigo) {
    	this.fechaAlta = alta;
    	this.fechaBaja = baja;
    	this.codigo = codigo;
    }

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDateTime getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDateTime fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return "MiembroDTO [fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja + ", codigo=" + codigo + "]";
	}
    
    
}
