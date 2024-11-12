package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class NotUpdateException extends SQLException{

	public NotUpdateException() {
	}

	public NotUpdateException(String message) {
		super(message);
	}
}
