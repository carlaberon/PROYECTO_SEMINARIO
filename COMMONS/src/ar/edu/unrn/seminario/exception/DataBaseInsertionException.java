package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class DataBaseInsertionException extends Exception{
	
	public DataBaseInsertionException() {
		
	}

	public DataBaseInsertionException(String message) {
		super(message);
	}
}
