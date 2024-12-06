package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;

public class Tarea {
	
	
    private int id;
	private String nombre;
    private Proyecto proyecto; 
    private String prioridad;
    private String usuario;
    private String estado; // FINALIZADO O EN CURSO
    private String descripcion;
    private LocalDate fechaInicio; 
    private LocalDate fechaFin;


    public Tarea(int id, String nombre, Proyecto proyecto, String prioridad, String username, String estado, String descripcion, LocalDate inicio, LocalDate fin) throws DataEmptyException, NotNullException, InvalidDateException {
    
 
    	if (esDatoNulo(nombre))
			throw new NotNullException("validacion.nombre");
    	if (esDatoNulo(username))
    		throw new NotNullException("validacion.usuarioAsignado");
    	if (esDatoNulo(prioridad))
			throw new NotNullException("validacion.prioridad");
    	if (esDatoNulo(descripcion))
			throw new NotNullException("validacion.descripcion");
    	
    	
		if (esDatoVacio(nombre))
			throw new DataEmptyException("validacion.nombre");
		if (esDatoVacio(username))
			throw new DataEmptyException("validacion.usuarioAsignado");
		if (esDatoVacio(prioridad))
			throw new DataEmptyException("validacion.prioridad");
		if (esFechaNula(inicio)) 
			throw new NotNullException("validacion.fechaInicio");
		if (esFechaNula(fin)) 
			throw new NotNullException("validacion.fechaFin");
		if (esDatoVacio(descripcion))
			throw new DataEmptyException("validacion.descripcion");

		if (fin.isBefore(inicio)) {
			throw new InvalidDateException("mensaje.fechasNoValidas");
		}
		
		this.id = id;
    	this.nombre = nombre;
        this.proyecto = proyecto;
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

    public String getUsuario() {
        return usuario;
    }

    public String getEstado() {
        return estado; 
    }
   
    public String obtenerEstado() {
        return estado;
    }

    public void finalizarTarea() {
        if (estado != "FINALIZADO")
        	estado = "FINALIZADO";
    }

    public void tareaEnCurso() {
        if (estado != "EN CURSO")
            this.estado = "EN CURSO";
    }

    public String getDescripcion() {
        return descripcion;
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
    public void setPrioridad(String prioridad) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(prioridad)) {
			throw new NotNullException("prioridad");
    	}
    	if (esDatoVacio(prioridad)) {
			throw new DataEmptyException("prioridad");
    	}
        this.prioridad = prioridad; 
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


    public void setUsuario(String usuario) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(usuario)) {
			throw new NotNullException("usuario");
    	}
    	if (esDatoVacio(usuario)) {
			throw new DataEmptyException("usuario");
    	}
        this.usuario = usuario;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public void setInicio(LocalDate inicio) throws InvalidDateException {
		 if (this.fechaFin.isBefore(inicio)) {
				throw new InvalidDateException("La fecha de inicio debe ser anterior a la fecha de finalizacion");
			}
        this.fechaInicio = inicio;
    }

    public void setFin(LocalDate fin) throws InvalidDateException {
		 if (fin.isBefore(this.fechaInicio)) {
				throw new InvalidDateException("La fecha de inicio debe ser anterior a la fecha de finalizacion");
			}
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

	private boolean esDatoVacio(String dato) {
		return dato.equals("");
	}

	private boolean esDatoNulo(String dato) {
		return dato == null;
	}
	
	private boolean esFechaNula(LocalDate dato) {
		return dato == null;
	}
	
	public int getId() {
		return id;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Override
	public String toString() {
		return "Tarea [id=" + id + ", nombre=" + nombre + ", proyecto=" + proyecto + ", prioridad=" + prioridad
				+ ", usuario=" + usuario + ", estado=" + estado + ", descripcion=" + descripcion + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + "]";
	}
	

}

