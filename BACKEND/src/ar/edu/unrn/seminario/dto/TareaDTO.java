package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Usuario;

public class TareaDTO {

	private int id;
    private String name;
    private String project_name;
    private String usuarioPropietario;
    private String priority;
    private String user;
    private boolean estado; // FINALIZADO: TRUE, NOFINALIZADO: FALSE
    private String description;
    private LocalDate inicio; 
    private LocalDate fin;
    
    public TareaDTO(int id, String name, String nameProject, String usuarioPropietario, String priority, String user, boolean estado, String descripcion, LocalDate inicio, LocalDate fin) throws NotNullException, InvalidDateException, DataEmptyException {
    	super();
    	
    	if (esDatoNulo(name))
			throw new NotNullException("nombre");
    	if (esDatoNulo(nameProject))
			throw new NotNullException("nombre de proyecto");
    	if (esDatoNulo(usuarioPropietario))
			throw new NotNullException("nombre de usuario propietario");
    	if (esDatoNulo(priority))
			throw new NotNullException("prioridad");
    	if (esDatoNulo(user))
			throw new NotNullException("usuario asignado");
    	if (esDatoNulo(descripcion))
			throw new NotNullException("descripcion");
    	
		if (esDatoVacio(name))
			throw new DataEmptyException("nombre");
		if (esDatoVacio(nameProject))
			throw new DataEmptyException("nombre de proyecto");
		if (esDatoVacio(usuarioPropietario))
			throw new DataEmptyException("usuario propietario");
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
        this.project_name = nameProject;
        this.setUsuarioPropietario(usuarioPropietario);
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

    public String getProject() {
        return project_name;
    }

    public void setProject(String project) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(project))
			throw new NotNullException("proyecto");
    	
		if (esDatoVacio(project))
			throw new DataEmptyException("proyecto");
       
        this.project_name = project;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
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



	public String getUsuarioPropietario() {
		return usuarioPropietario;
	}



	public void setUsuarioPropietario(String usuarioPropietario) throws NotNullException, DataEmptyException {
    	if (esDatoNulo(usuarioPropietario))
			throw new NotNullException("usuario propietario");
    	
		if (esDatoVacio(usuarioPropietario))
			throw new DataEmptyException("usuario propietario");
		this.usuarioPropietario = usuarioPropietario;
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



	public void setId(int id) {
		this.id = id;
	}
}

