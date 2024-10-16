package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;

public class Miembro {
    
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaBaja;
    private String codigo; 
    
    public Miembro(String codigo) {
        this.codigo = codigo; 
        this.fechaAlta = LocalDateTime.now();
    }

    // Getters
    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }

    public String getCodigo() {
        return codigo;
    }

    // Setters
    public void setFechaBaja(LocalDateTime fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; 
        if (!(obj instanceof Miembro)) return false;
        Miembro other = (Miembro) obj;
        return codigo.equals(other.codigo); 
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
        int result = 1; 
        result = prime * result + (codigo == null ? 0 : codigo.hashCode()); 
        return result; 
    }
}

