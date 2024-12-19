package ar.edu.unrn.seminario.exception;

public class UserIsAlreadyMember extends Exception{
	
	public UserIsAlreadyMember() {
	}

	public UserIsAlreadyMember(String message) {
		super(message);
	}
}
