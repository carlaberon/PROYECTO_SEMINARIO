package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;

public class Tarea {

    private String nombre;
    private String proyecto;  
    private String prioridad;
    private String usuario;
    private boolean estado; // FINALIZADO: TRUE, NOFINALIZADO: FALSE
    private String descripcion;
    private LocalDateTime inicio; 
    private LocalDateTime fin;

    public Tarea(String nombretarea, String proyecto, String prioridad, String user1, boolean estado, String descripcion, LocalDateTime inicio, LocalDateTime fin) throws DataEmptyException, NotNullException {
        
    	if (esDatoNulo(nombretarea))
			throw new NotNullException("nombre");
    	if (esDatoNulo(proyecto))
			throw new NotNullException("nombre de proyecto");
    	if (esDatoNulo(prioridad))
			throw new NotNullException("prioridad");
    	if (esDatoNulo(user1))
			throw new NotNullException("usuario asignado");
    	if (esDatoNulo(descripcion))
			throw new NotNullException("descripcion");
    	
		if (esDatoVacio(nombretarea))
			throw new DataEmptyException("nombre");
		if (esDatoVacio(proyecto))
			throw new DataEmptyException("nombre de proyecto");
		if (esDatoVacio(prioridad))
			throw new DataEmptyException("prioridad");
		if (esDatoVacio(user1))
			throw new DataEmptyException("usuario asignado");
		if (esDatoVacio(descripcion))
			throw new DataEmptyException("descripcion");

    	
    	this.nombre = nombretarea;
        this.proyecto = proyecto;
        this.prioridad = prioridad;
        this.usuario = user1;
        this.estado = estado;
        this.descripcion = descripcion; 
        this.inicio = inicio;
        this.fin = fin; 
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getProyecto() { // Método correcto para obtener el proyecto
        return proyecto;
    }

    public String getUsuario() {
        return usuario;
    }

    public boolean isEstado() {
        return estado; // Devuelve si la tarea está realizada
    }
   
    public String obtenerEstado() {
        return isEstado() ? "REALIZADA" : "ENCURSO";
    }

    public void finalizarTarea() {
        if (!isEstado())
            this.estado = true;
    }

    public void tareaEnCurso() {
        if (isEstado())
            this.estado = false;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }
    
    public String getPrioridad() {
        return this.prioridad;
    }
    
    // Setters
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad; 
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProyecto(String proyecto) {  // Mantener como String
        this.proyecto = proyecto;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; 
        if (!(obj instanceof Tarea)) return false; 
        Tarea other = (Tarea) obj; 
        return nombre.equals(other.nombre) && usuario.equals(other.usuario); 
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (nombre == null ? 0 : nombre.hashCode());
        result = prime * result + (usuario == null ? 0 : usuario.hashCode()); 
        return result; 
    }

	public Object getProjecto() {
		return null;
	}
	
	private boolean esDatoVacio(String dato) {
		return dato.equals("");
	}

	private boolean esDatoNulo(String dato) {
		return dato == null;
	}

    // Eliminar o corregir getProject()
    // public Object getProject() {
    //     return null;
    // }
}

