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
    private Set<Miembro> miembros = new HashSet<>();
    private String descripcion;
    private String prioridad;//Alta, Media, Baja
    private Set<Proyecto> proyectos = new HashSet<>();
    private Plan plan;

    public Proyecto(String nombre, Usuario usuarioPropietario) {
        this.nombre = nombre; 
        this.usuarioPropietario = usuarioPropietario;
    }

    public Proyecto(String nombre, Usuario usuarioPropietario, boolean estado) {
        this.nombre = nombre; 
        this.usuarioPropietario = usuarioPropietario;
        this.estado = estado;
    }


    public Proyecto(String nombre, Usuario usuarioPropietario, boolean estado, String descripcion, String prioridad) throws NotNullException, DataEmptyException{
        this.nombre = nombre; 
        this.usuarioPropietario = usuarioPropietario;
        this.estado = estado;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    public Proyecto() {
    	
    }

    public Proyecto(String nombre2, String string) {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Usuario getUsuarioPropietario() {
        return usuarioPropietario;
    }
    
    public void setUsuarioPropietario(Usuario usuarioPropietario) {
        this.usuarioPropietario = usuarioPropietario;
    }

    public Set<Miembro> getMiembros() {
        return miembros;
    }

    public void setMiembros(Set<Miembro> miembros) {
        this.miembros = miembros; 
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getPrioridad1() {
        return prioridad;
    }

    public void setPrioridad1(String prioridad) {
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
    
	
	public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
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
}

