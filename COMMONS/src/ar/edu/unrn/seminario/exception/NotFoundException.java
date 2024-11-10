package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class NotFoundException extends SQLException{
	
	public NotFoundException() {
		
	}

	public NotFoundException(String message) {
		super(message);
	}
}
