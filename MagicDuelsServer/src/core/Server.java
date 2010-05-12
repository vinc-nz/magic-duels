package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Server extends Thread{

	ServerSocket serverSocket;
	List<Connection> players;
	
	public Server(int porta) {
	
		try {
			this.serverSocket = new ServerSocket(porta);
		} catch (IOException e) {
			System.out.println("Impossibile aprire una connessione!");
		}
		
		this.players = new LinkedList<Connection>();
		
	}
	
	public void sendChatMessage(String msg)
	{	
		for (Iterator iterator = this.players.iterator(); iterator.hasNext();) 
		{
			Connection connection = (Connection) iterator.next();	
			connection.sendMessage(Connection.CHAT + msg);
		}	
	}
	
	@Override
	public void run() {

		Socket connection;
		
		while(true)
		{
			try {

				connection = this.serverSocket.accept();
				this.players.add(new Connection(connection));

			} catch (IOException e) {
				System.out.println("Impossibile accettare la connessione!");
			}
			
		}
	}
	
}
