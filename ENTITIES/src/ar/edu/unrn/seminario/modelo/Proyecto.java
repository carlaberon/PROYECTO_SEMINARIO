package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;

public class Proyecto {

	private int id;
    private String nombre;
    private Usuario usuarioPropietario;
    private String estado; 
    private String descripcion;
    private String prioridad;//Alta, Media, Baja
    
    public Proyecto(int id, String nombre, Usuario usuarioPropietario, String estado, String descripcion, String prioridad) throws NotNullException, DataEmptyException{
    	    // Validar que los campos no sean nulos
    	    if (esDatoNulo(nombre)) {
    	        throw new NotNullException("validacion.nombre");
    	    }
    	    if (esDatoNulo(descripcion)) {
    	        throw new NotNullException("validacion.descripcion");
    	    }
    	    if (esDatoNulo(prioridad)) {
    	        throw new NotNullException("validacion.prioridad");
    	    }

    	    // Validar que los campos no estén vacíos
    	    if (esDatoVacio(nombre)) {
    	        throw new DataEmptyException("validacion.nombre");
    	    }
    	    if (esDatoVacio(descripcion)) {
    	        throw new DataEmptyException("validacion.descripcion");
    	    }
    	    if (esDatoVacio(prioridad)) {
    	        throw new DataEmptyException("validacion.prioridad");
    	    }
    	
    	this.id = id;
    	this.nombre = nombre; 
        this.usuarioPropietario = usuarioPropietario;
        this.estado = estado;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }


	public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(nombre)) {
            throw new NotNullException("nombre");
        }
        if (esDatoVacio(nombre)) {
            throw new DataEmptyException("nombre");
        }
        this.nombre = nombre;
    }
    
    public Usuario getUsuarioPropietario() {
        return usuarioPropietario;
    }
    
    public void setUsuarioPropietario(Usuario usuarioPropietario) {
        this.usuarioPropietario = usuarioPropietario;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(descripcion)) {
            throw new NotNullException("descripcion");
        }
        if (esDatoVacio(descripcion)) {
            throw new DataEmptyException("descripcion");
        }
        this.descripcion = descripcion;
    }
    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(prioridad)) {
            throw new NotNullException("prioridad");
        }
        if (esDatoVacio(prioridad)) {
            throw new DataEmptyException("prioridad");
        }
        this.prioridad = prioridad;
    }
  
    public String getEstado() {
        return estado; // ACTIVO: FALSE, FINALIZADO: TRUE
    }
    
    public void setEstado(String estado) {
        this.estado = estado; // ACTIVO: FALSE, FINALIZADO: TRUE
    }
    
    public String isFinished() {
        return estado; // ACTIVO: FALSE, FINALIZADO: TRUE
    }

    public String obtenerEstado() {
        return estado;
    }

    public void finalizarProyecto() {
        if (estado != "FINALIZADO")
            this.estado = "FINALIZADO";
    }

//    public void activarProyecto() {
//        if (isFinished())
//            this.estado = false;
//    }
    
	private boolean esDatoVacio(String dato) {
		return dato.equals("");
	}

	private boolean esDatoNulo(String dato) {
		return dato == null;
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Proyecto)) return false;
        Proyecto other = (Proyecto) obj;
        return nombre.equals(other.nombre) && usuarioPropietario.equals(other.usuarioPropietario);
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
        int result = 1;
        result = prime * result + (nombre == null ? 0 : nombre.hashCode());
        result = prime * result + (usuarioPropietario == null ? 0 : usuarioPropietario.hashCode());
        return result;
    }

	@Override
	public String toString() {
		return "Proyecto [nombre=" + nombre + ", usuarioPropietario=" + usuarioPropietario + ", estado=" + estado
				+ ", miembros=" + ", descripcion=" + descripcion + ", prioridad=" + prioridad
				;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
}

