package core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection extends Thread {

	public static String WELCOME = "WLC";	
	public static String CLOSE = "BYE";
	
	public static String CHAT = "CHAT>";
	
	Socket player;
	ObjectOutputStream out;
	ObjectInputStream in;

	public Connection(Socket player) {

		this.player = player;
		try {
			this.out = new ObjectOutputStream(player.getOutputStream());
			this.out.flush();
			this.in = new ObjectInputStream(player.getInputStream());
			
			this.sendMessage(Connection.WELCOME);
		
		} catch (IOException e) {
			System.out.println("Errore durante la creazione degli IOStream");
		}

		this.start();
		
	}
	
	void sendMessage(String msg)
	{
		try{
			this.out.writeObject(msg);
			this.out.flush();
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			System.out.println("Errore durante l'invio del messaggio al client");
		}
	}

	
	@Override
	public void run() {
		
		String message = null;
		
		do{
			
			try{
			
				message = (String)this.in.readObject();
				System.out.println("client>" + message);
				
				if (message.equals(Connection.CLOSE))
					sendMessage(Connection.CLOSE);
			
			}
			
			catch(ClassNotFoundException classnot){
				System.err.println("Formato messaggio non riconosciuto");
			} catch (IOException e) {
				System.out.println("Errore durante la lettura del messaggio del client");
			}
			
		} while(!message.equals(Connection.CLOSE));

		
	}
	
}
