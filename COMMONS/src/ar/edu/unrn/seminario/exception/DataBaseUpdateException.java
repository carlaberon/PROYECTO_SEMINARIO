package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class DataBaseUpdateException extends SQLException{

	public DataBaseUpdateException() {
	}

	public DataBaseUpdateException(String message) {
		super(message);
	}
}
