package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class DataBaseEliminationException extends Exception{
	
	public DataBaseEliminationException() {
		
	}

	public DataBaseEliminationException(String message) {
		super(message);
	}
}
