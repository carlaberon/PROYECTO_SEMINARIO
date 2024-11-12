package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class EliminationException extends SQLException{
	
	public EliminationException() {
		
	}

	public EliminationException(String message) {
		super(message);
	}
}
