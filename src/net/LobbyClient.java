package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class LobbyClient extends Thread {

	public static String WELCOME = "WLC";	
	public static String CLOSE = "BYE";
	
	public static String CHAT = "CHAT>";
	
	Socket connection;
	ObjectOutputStream out;
 	ObjectInputStream in; 	
 	
	public LobbyClient() {
	
		try {
			
			this.connection = new Socket("127.0.0.1", 7777);
			
			this.out = new ObjectOutputStream(this.connection.getOutputStream());
			this.out.flush();
			this.in = new ObjectInputStream(this.connection.getInputStream());
			
			/*
			try {
				
				if( ((String)this.in.readObject()).equals(LobbyClient.WELCOME) )
					System.out.println("CONNESSIONE STABILITA");
				
			} catch (ClassNotFoundException e) {
				System.out.println("Formato messaggio non riconosciuto");
			}*/
			
		} catch (UnknownHostException e) {
			System.out.println("L'host è sconosciuto");
		} catch (IOException e) {
			System.out.println("Errore durante la connessione all'Host");
			e.printStackTrace();
		}
		
		this.start();
		
	}
	
	public void sendMessage(String msg)
	{
		try{
			this.out.writeObject(msg);
			this.out.flush();
			//System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			System.out.println("Errore durante l'invio del messaggio al client");
		}
	}

	protected void closeConnection()
	{
		try {
		
			this.in.close();
			this.out.close();
			
			this.connection.close();
			
		} catch (IOException e) {
			System.out.println("Errore nella chiusura della connessione");
		}
		
	}	
	
	@Override
	public void run() {
		
		String message = null;
		
		do{
			
			try{
			
				message = (String)this.in.readObject();
				System.out.println("client>" + message);
				
				if (message.equals(LobbyClient.CLOSE))
					sendMessage(LobbyClient.CLOSE);
			
				this.sendMessage(CHAT + "ciao");
				
				message = (String)this.in.readObject();
				System.out.println("client>" + message);
				
				break;
			}
			
			catch(ClassNotFoundException classnot){
				System.err.println("Formato messaggio non riconosciuto");
			} catch (IOException e) {
				System.out.println("Errore durante la lettura del messaggio del client");
			}
			
		} while(!message.equals(LobbyClient.CLOSE));
		
		this.closeConnection();
		
	}

	public static void main(String[] args) {

		LobbyClient lobby = new LobbyClient();
	
	}
	
}