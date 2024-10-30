package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;

public class Tarea {
    private String nombre;
    private String proyecto;  
    private String prioridad;
	private String usuarioP;
    private String usuario;
    private boolean estado; // FINALIZADO: TRUE, NOFINALIZADO: FALSE
    private String descripcion;
    private LocalDate fechaInicio; 
    private LocalDate fechaFin;

    public Tarea(String nombre, String proyecto, String usuarioPropietario, String prioridad, String username, boolean estado, String descripcion, LocalDate inicio, LocalDate fin) throws DataEmptyException, NotNullException, InvalidDateException {
    
 
    	if (esDatoNulo(nombre))
			throw new NotNullException("nombre");
    	if (esDatoNulo(proyecto))
			throw new NotNullException("nombre de proyecto");
    	if (esDatoNulo(usuarioPropietario))
			throw new NotNullException("usuario propietario");
    	if (esDatoNulo(prioridad))
			throw new NotNullException("prioridad");
    	if (esDatoNulo(username))
			throw new NotNullException("usuario asignado");
    	if (esDatoNulo(descripcion))
			throw new NotNullException("descripcion");
    	
		if (esDatoVacio(nombre))
			throw new DataEmptyException("nombre");
		if (esDatoVacio(proyecto))
			throw new DataEmptyException("nombre de proyecto");
		if (esDatoVacio(usuarioPropietario))
			throw new DataEmptyException("usuario propietario");
		if (esDatoVacio(prioridad))
			throw new DataEmptyException("prioridad");
		if (esDatoVacio(username))
			throw new DataEmptyException("usuario asignado");
		if (esDatoVacio(descripcion))
			throw new DataEmptyException("descripcion");

		 if (fin.isBefore(inicio)) {
				throw new InvalidDateException("La fecha de inicio debe ser anterior a la fecha de finalizacion");
			}
		
    	this.nombre = nombre;
        this.proyecto = proyecto;
        this.usuarioP= usuarioPropietario;
        this.prioridad = prioridad;
        this.usuario = username;
        this.estado = estado;
        this.descripcion = descripcion; 
        this.fechaInicio = inicio;
        this.fechaFin = fin; 
        
       
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getProyecto() { 
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
    public String getUsuarioPropietario() {
        return usuarioP;
    }
    public LocalDate getInicio() {
        return fechaInicio;
    }

    public LocalDate getFin() {
        return fechaFin;
    }
    
    public String getPrioridad() {
        return this.prioridad;
    }
    
    // Setters
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad; 
    }
    public void setUsuarioPropietario(String usuarioPropietario) {
    	this.usuarioP=usuarioPropietario;
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

    public void setInicio(LocalDate inicio) {
        this.fechaInicio = inicio;
    }

    public void setFin(LocalDate fin) {
        this.fechaFin = fin;
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

	@Override
	public String toString() {
		return "Tarea [nombre=" + nombre + ", proyecto=" + proyecto + ", prioridad=" + prioridad + ", usuarioP="
				+ usuarioP + ", usuario=" + usuario + ", estado=" + estado + ", descripcion=" + descripcion
				+ ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + "]";
	}
}

