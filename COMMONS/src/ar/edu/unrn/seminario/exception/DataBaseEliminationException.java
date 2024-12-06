package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class DataBaseEliminationException extends SQLException{
	
	public DataBaseEliminationException() {
		
	}

	public DataBaseEliminationException(String message) {
		super(message);
	}
}
