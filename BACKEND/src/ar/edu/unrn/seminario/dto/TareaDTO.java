package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;

public class TareaDTO {

	private int id;
	private String name;
	private ProyectoDTO project;
    private String priority;
    private String user;
    private String estado; // // FINALIZADO O EN CURSO
    private String description;
    private LocalDate inicio; 
    private LocalDate fin;
    
    public TareaDTO(int id, String name, ProyectoDTO project,String priority, String user, String estado, String descripcion, LocalDate inicio, LocalDate fin) throws NotNullException, InvalidDateException, DataEmptyException {
    	super();
    	
    	if (esDatoNulo(name))
			throw new NotNullException("nombre");
    	if (esDatoNulo(priority))
			throw new NotNullException("prioridad");
    	if (esDatoNulo(user))
			throw new NotNullException("usuario asignado");
    	if (esDatoNulo(descripcion))
			throw new NotNullException("descripcion");
    	
		if (esDatoVacio(name))
			throw new DataEmptyException("nombre");
		if (esDatoVacio(priority))
			throw new DataEmptyException("prioridad");
		if (esDatoVacio(user))
			throw new DataEmptyException("usuario asignado");
		if (esDatoVacio(descripcion))
			throw new DataEmptyException("descripcion");
    	
		 if (fin.isBefore(inicio)) {
				throw new InvalidDateException("La fecha de inicio debe ser anterior a la fecha de finalizacion");
			}
    	
		this.id = id;
        this.name = name;
        this.project = project;
        this.priority = priority;
        this.user = user;
        this.estado = estado;
        this.description = descripcion;
        this.inicio = inicio;
        this.fin = fin;
    }



	// Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(name))
			throw new NotNullException("nombre");
    	
		if (esDatoVacio(name))
			throw new DataEmptyException("nombre");
       
		this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(priority))
			throw new NotNullException("prioridad");
    	
		if (esDatoVacio(priority))
			throw new DataEmptyException("prioridad");
        this.priority = priority;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(user))
			throw new NotNullException("usuario");
    	
		if (esDatoVacio(user))
			throw new DataEmptyException("usuario");
        this.user = user;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        
    	this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

	private boolean esDatoVacio(String dato) {
		return dato.equals("");
	}

	private boolean esDatoNulo(String dato) {
		return dato == null;
	}

	public int getId() {
		return id;
	}

	public ProyectoDTO getProject() {
		return project;
	}

	public void setProject(ProyectoDTO project) {
		this.project = project;
	}
}

