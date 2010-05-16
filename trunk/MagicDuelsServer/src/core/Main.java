package core;

public class Main {

	public static void main(String[] args) {
		
		try{
			
			int porta = Integer.parseInt(args[0]);
			Server server = new Server(porta);

		} catch (ArrayIndexOutOfBoundsException e) {

			System.out.println("Deve essere inserita una porta!");
			return;
		
		}
		
	}

}