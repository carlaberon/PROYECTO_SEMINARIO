package ar.edu.unrn.seminario.dto;

import java.util.HashSet;
import java.util.Set;

import ar.edu.unrn.seminario.modelo.Miembro;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Tarea;
import ar.edu.unrn.seminario.modelo.Usuario;

public class ProyectoDTO implements Comparable<ProyectoDTO>{

    private String nombre;
    private UsuarioDTO usuarioPropietario;
    private String prioridad; //Alta, Media, Baja
    private boolean estado; //ACTIVO= false; FINALIZADO = true
    private Set<String> miembros = new HashSet<>();
    private String descripcion; 
    private Set<String> proyectos = new HashSet<>();
    private Set<String> tareas = new HashSet<>();
    
    public ProyectoDTO(String nombreProyecto, UsuarioDTO usuarioPropietario, boolean estado, String prioridad, String descripcion) {
        this.nombre = nombreProyecto;
        this.usuarioPropietario = usuarioPropietario;
        this.prioridad = prioridad;
        this.estado = estado;
        this.descripcion = descripcion;
    }
    
    public ProyectoDTO(String nombre2, String descripcion2) {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }
    
    public UsuarioDTO getUsuarioPropietario() {
        return usuarioPropietario;
    }

    public void setUsuarioPropietario(UsuarioDTO usuarioPropietario) {
        this.usuarioPropietario = usuarioPropietario;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Set<String> getMiembros() {
        return miembros;
    }

    public void setMiembros(Set<String> miembros) {
        this.miembros = miembros;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<String> getProyectos() {
        return proyectos;
    }

    public void setProyectos(Set<String> proyectos) {
        this.proyectos = proyectos;
    }

    public Set<String> getTareas() {
        return tareas;
    }

    public void setTareas(Set<String> tareas) {
        this.tareas = tareas;
    }
    
    @Override
    public String toString() {
        return "ProyectoDTO{" +
                "nombre='" + nombre + '\'' +
                ", usuarioPropietario='" + usuarioPropietario + '\'' +
                ", estado=" + (estado ? "FINALIZADO" : "ACTIVO") +
                ", prioridad='" + prioridad + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

	public void agregarTarea(TareaDTO nuevaTarea) {
		// TODO Auto-generated method stub
		
	}
	
	public int obtenerValorPrioridad(String prioridad) {
        switch (prioridad) {
            case "alta":
                return 3;
            case "media":
                return 2;
            case "baja":
                return 1;
            default:
                return 0; // En caso de prioridad desconocida
        }
    }
	
	@Override
    public int compareTo(ProyectoDTO otroProyecto) {
        // Ordenar por prioridad de mayor a menor
        // Asumimos que las prioridades son "Alta", "Media", "Baja"
        int prioridadActual = obtenerValorPrioridad(this.prioridad);
        int prioridadOtro = obtenerValorPrioridad(otroProyecto.getPrioridad());

        // Si prioridadActual > prioridadOtro, el proyecto actual será mayor
        return Integer.compare(prioridadOtro, prioridadActual); // Mayor a menor
    }
}
