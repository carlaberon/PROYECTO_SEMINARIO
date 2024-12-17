package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class DataBaseUpdateException extends Exception{

	public DataBaseUpdateException() {
	}

	public DataBaseUpdateException(String message) {
		super(message);
	}
}
