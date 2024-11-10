package ar.edu.unrn.seminario.exception;

public class TaskNotRemovedException extends Exception{
	public TaskNotRemovedException(String mensaje) {
		super(mensaje);
	}
	
	public TaskNotRemovedException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
