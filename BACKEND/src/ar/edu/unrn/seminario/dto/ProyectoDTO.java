package ar.edu.unrn.seminario.dto;





public class ProyectoDTO {
	

	private int id;
	private String nombre;
    private UsuarioDTO usuarioPropietario;
    private String prioridad; //Alta, Media, Baja
    private String estado; //ACTIVO= false; FINALIZADO = true
    private String descripcion;
    
    public ProyectoDTO(int id, String nombreProyecto, UsuarioDTO usuarioPropietario, String estado, String prioridad, String descripcion) {
    	
	    this.id = id;
    	this.nombre = nombreProyecto;
        this.usuarioPropietario = usuarioPropietario;
        this.prioridad = prioridad;
        this.estado = estado;
        this.descripcion = descripcion;
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

    public String isEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {

        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return "ProyectoDTO{" +
                "nombre='" + nombre + '\'' +
                ", usuarioPropietario='" + usuarioPropietario + '\'' +
                ", estado=" + estado +
                ", prioridad='" + prioridad + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

	public void agregarTarea(TareaDTO nuevaTarea) {
		// TODO Auto-generated method stub
		
	}
	

	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
