package ar.edu.unrn.seminario.exception;

public class TaskNotFoundException extends Exception{
	public TaskNotFoundException(String mensaje) {
		super(mensaje);
	}
	
	public TaskNotFoundException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
