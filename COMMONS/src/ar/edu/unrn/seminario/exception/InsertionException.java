package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class InsertionException extends SQLException{
	
	public InsertionException() {
		
	}

	public InsertionException(String message) {
		super(message);
	}
}
