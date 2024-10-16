package ar.edu.unrn.seminario.exception;

public class Test1 {
	public int unMetodo(){
		try {
		
		System.out.println("Va a retornar 1");
		return 1;
		} 
		
		finally {
		
		System.out.println("Va a retornar 2");
		return 2;
		}
}
	public static void main(String[] args) {
		
		Test1 res = new Test1();
		
		System.out.println(res.unMetodo());
}
	
}
