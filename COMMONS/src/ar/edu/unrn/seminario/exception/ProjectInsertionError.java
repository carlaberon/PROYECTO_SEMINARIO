package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class ProjectInsertionError extends SQLException{
	
	public ProjectInsertionError() {
		
	}

	public ProjectInsertionError(String message) {
		super(message);
	}
}
