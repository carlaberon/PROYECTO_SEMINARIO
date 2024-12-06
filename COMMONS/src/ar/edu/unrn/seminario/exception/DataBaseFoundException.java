package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class DataBaseFoundException extends SQLException{
	
	public DataBaseFoundException() {
		
	}

	public DataBaseFoundException(String message) {
		super(message);
	}
}
