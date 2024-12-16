package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class DataBaseFoundException extends Exception{
	
	public DataBaseFoundException() {
		
	}

	public DataBaseFoundException(String message) {
		super(message);
	}
}
