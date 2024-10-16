package ar.edu.unrn.seminario.dto;

public class RolDTO {

	private Integer codigo;
	private String nombre;
	private String descripcion;
	private boolean activo;
	
	public RolDTO(Integer codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public RolDTO(Integer codigo, String nombre, boolean activo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
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
