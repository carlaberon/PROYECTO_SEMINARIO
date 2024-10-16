package ar.edu.unrn.seminario.exception;

//demostración del mecanismo de manejo de excepciones
//try .. catch .. finally

public class UsoDeExcepciones 
{
	public static void main (String args[] )
	{
		try
		{
			lanzaExcepcion(); //llama al método lanzaExcepción
		} //fin de try
		catch (Exception excepción ) //excepción lanzada por lanzaExcepcion
		{
			System.err.println("La excepcion se manejo en main");
		} //fin de catch

		noLanzaExcepcion();
	} //fin de main
	
	//demuestra los bloques try..catch..finally
	public static void lanzaExcepcion() throws Exception
	{
		try //lanza una excepción y la atrapa de inmediato
		{
			System.out.println("Método lanzaExcepcion");
			throw new Exception(); //genera la excepción
		} //fin try
		catch (Exception excepción ) //atrapa la excepción lanzada en el bloque try
		{
			System.err.println("La excepción se manejo en el método lanzaExcepcion");
			throw excepción; //vuelve a lanzar para procesarla mas adelante

			//no se llegaría al código que se coloque aquí, la excepción se vuelve a lanzar en el bloque catch

		} //fin de catch
		finally //se ejecuta sin importar lo que ocurra en los bloques try..catch
		{
			System.err.println("Se ejecuto finally en lanzaExcepcion");
		} //fin finally

		//no se llega al código que se coloque aquí, la excepción se vuelve a lanzar en el bloque catch
	} //fin del método lanzaExcepcion
	
	//demuestra el uso de finally cuando no ocurre una excepción 

	public static void noLanzaExcepcion()
	{
		try //el bloque try no lanza una excepción
		{
			System.out.println("Método noLanzaExcepcion");

		}//fin de try
		catch (Exception excepción ) //no se ejecuta
		{
			System.err.println (excepción);
		} //fin de catch
		finally //se ejecuta sin importan lo que ocurra en los bloques try..catch
		{
			System.err.println ("Se ejecuta finally en noLanzaExcepcion");
		} //fin del bloque finally

		
		System.out.println("Fin del método noLanzaExcepcion");

	} //fin del método noLanzaExcepcion
} //fin de la clase UsoDeExcepciones
