package ar.edu.unrn.seminario.exception;

public class TaskQueryException extends Exception{
	public TaskQueryException(String mensaje) {
		super(mensaje);
	}
	
	public TaskQueryException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
