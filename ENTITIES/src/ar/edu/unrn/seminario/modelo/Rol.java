package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.dto.RolDTO;

public class Rol {
	private Integer codigo;
	private String nombre;
	private String descripcion;
	private boolean activo;
	
	public Rol() {

	}

	public Rol(Integer codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public Rol(Integer codigo, String nombre, boolean activo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.activo = activo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	// Método de conversión de RolDTO a Rol
    public static Rol fromDTO(RolDTO rolDTO) {
        Rol rol = new Rol();
        rol.setCodigo(rolDTO.getCodigo());
        rol.setNombre(rolDTO.getNombre());
        rol.setDescripcion(rolDTO.getDescripcion());
        rol.setActivo(rolDTO.isActivo());
        return rol;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rol other = (Rol) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rol [codigo=" + codigo + ", nombre=" + nombre + ", activo=" + "]";
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
