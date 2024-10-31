package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ar.edu.unrn.seminario.modelo.Proyecto;
import ar.edu.unrn.seminario.modelo.Usuario;

public class TareaDTO {

    private String name;
    private String project_name;
    private String usuarioPropietario;
    private String priority;
    private String user;
    private boolean estado; // FINALIZADO: TRUE, NOFINALIZADO: FALSE
    private String description;
    private LocalDate inicio; 
    private LocalDate fin;
    
    public TareaDTO(String name, String nameProject, String usuarioPropietario, String priority, String user, boolean estado, String descripcion, LocalDate inicio, LocalDate fin) {
        super();
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

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project_name;
    }

    public void setProject(String project) {
        this.project_name = project;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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



	public void setUsuarioPropietario(String usuarioPropietario) {
		this.usuarioPropietario = usuarioPropietario;
	}
}

