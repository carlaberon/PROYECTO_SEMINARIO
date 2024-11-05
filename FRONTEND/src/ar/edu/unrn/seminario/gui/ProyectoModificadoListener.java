package ar.edu.unrn.seminario.gui;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;

public interface ProyectoModificadoListener {
	
	void proyectoEliminado() throws NotNullException, DataEmptyException; //Creado para notificar a Inicio que se elimino un Proyecto

    
}
