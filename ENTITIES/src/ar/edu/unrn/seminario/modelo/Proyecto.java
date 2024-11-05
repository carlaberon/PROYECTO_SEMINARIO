package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;

public class Proyecto {

    private String nombre;
    private Usuario usuarioPropietario;
    private boolean estado; // ACTIVO = false, FINALIZADO = true
    private Set<Usuario> miembros = new HashSet<>();
    private String descripcion;
    private String prioridad;//Alta, Media, Baja
    private Set<Proyecto> proyectos = new HashSet<>();

    public Proyecto(String nombre, Usuario usuarioPropietario) throws NotNullException {
	    if (esDatoNulo(nombre)) {
	        throw new NotNullException("nombre");
	    }
        this.nombre = nombre; 
        this.usuarioPropietario = usuarioPropietario;
    }

    public Proyecto(String nombre, Usuario usuarioPropietario, boolean estado) throws NotNullException {
	    if (esDatoNulo(nombre)) {
	        throw new NotNullException("nombre");
	    }
        this.nombre = nombre; 
        this.usuarioPropietario = usuarioPropietario;
        this.estado = estado;
    }
    
    public Proyecto(String nombre, Usuario usuarioPropietario, boolean estado, String descripcion, String prioridad) throws NotNullException, DataEmptyException{
    	    // Validar que los campos no sean nulos
    	    if (esDatoNulo(nombre)) {
    	        throw new NotNullException("nombre");
    	    }
    	    if (esDatoNulo(descripcion)) {
    	        throw new NotNullException("descripcion");
    	    }
    	    if (esDatoNulo(prioridad)) {
    	        throw new NotNullException("prioridad");
    	    }

    	    // Validar que los campos no estén vacíos
    	    if (esDatoVacio(nombre)) {
    	        throw new DataEmptyException("nombre");
    	    }
    	    if (esDatoVacio(descripcion)) {
    	        throw new DataEmptyException("descripcion");
    	    }
    	    if (esDatoVacio(prioridad)) {
    	        throw new DataEmptyException("prioridad");
    	    }
    	
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

    public Set<Usuario> getMiembros() {
        return miembros;
    }

    public void setMiembros(Set<Usuario> miembros) {
        this.miembros = miembros; 
    }
    public void agregarMiembro(Usuario usuario) {
    	miembros.add(usuario);
    }
    
    public boolean existeMiembro(Usuario usuario) {
    	if(miembros.contains(usuario))
    		return true;
    	
    	
    	return false;
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
    public String getPrioridad1() {
        return prioridad;
    }

    public void setPrioridad1(String prioridad) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(prioridad)) {
            throw new NotNullException("prioridad");
        }
        if (esDatoVacio(prioridad)) {
            throw new DataEmptyException("prioridad");
        }
        this.prioridad = prioridad;
    }
    public Set<Proyecto> getProyectos() {
        return proyectos;
    }
    
    public void setProyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
    
    
    public boolean getEstado() {
        return estado; // ACTIVO: FALSE, FINALIZADO: TRUE
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado; // ACTIVO: FALSE, FINALIZADO: TRUE
    }
    
    public boolean isFinished() {
        return estado; // ACTIVO: FALSE, FINALIZADO: TRUE
    }

    public String obtenerEstado() {
        return isFinished() ? "FINALIZADO" : "ACTIVO";
    }

    public void finalizarProyecto() {
        if (!isFinished())
            this.estado = true;
    }

    public void activarProyecto() {
        if (isFinished())
            this.estado = false;
    }
    
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
				+ ", miembros=" + miembros + ", descripcion=" + descripcion + ", prioridad=" + prioridad
				+ ", proyectos=" + proyectos + "]";
	}
    
}

