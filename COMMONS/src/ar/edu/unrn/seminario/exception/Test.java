package ar.edu.unrn.seminario.exception;


public class Test {
    
    public static void main(String[] args) throws Exception {
		
		try {
		    throw new Exception2();
		  
		}
		catch (Exception2 e2) {
		    System.out.println("Se capturo la Excepción2");
		    
		}
		catch (Exception1 e1) {
			
			System.out.println("Se capturo la Excepción1");
		}
	}
}