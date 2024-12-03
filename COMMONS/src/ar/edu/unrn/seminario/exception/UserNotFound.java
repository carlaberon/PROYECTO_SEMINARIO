package ar.edu.unrn.seminario.exception;

public class UserNotFound extends Exception{
	
	public UserNotFound() {
	}

	public UserNotFound(String message) {
		super(message);
	}
}
