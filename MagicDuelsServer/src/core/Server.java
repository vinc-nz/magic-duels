package core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Server extends Thread{

	ServerSocket serverSocket;
	List<Connection> players;
	List<HostedGame> hostedGames;
	
	public Server(int porta) {
	
		try {
			this.serverSocket = new ServerSocket(porta);
			System.out.println("SERVER IN ASCOLTO SULLA PORTA: " + porta);
		} catch (IOException e) {
			System.out.println("Impossibile aprire una connessione!");
		}
		
		this.players = new LinkedList<Connection>();
		this.hostedGames = new LinkedList<HostedGame>();
		
		this.start();
	}
	
	public void sendChatMessage(String sender, String msg)
	{	
		for (Iterator iterator = this.players.iterator(); iterator.hasNext();) 
		{
			Connection connection = (Connection) iterator.next();	
			connection.sendMessage(Messages.CHAT + sender.length() + ";" + sender + msg);
			
		}	
	}

	public void removePlayer(Connection player)
	{
		this.players.remove(player);
	}
	
	@Override
	public void run() {

		Socket connection;
		
		while(true)
		{
			try {
				System.out.println("ATTENDO NUOVA CONNESSIONE");
				connection = this.serverSocket.accept();
				System.out.println("NEW CONNECTION: " + connection.getLocalAddress());
				this.players.add(new Connection(connection, this));

			} catch (IOException e) {
				System.out.println("Impossibile accettare la connessione!");
			}
			
		}
	}
	
	public static String getClientList(Server server)
	{
		List<Connection> clients = server.players;
		
		String clientList = Messages.CLIENTLIST;
		
		for (Iterator iterator = clients.iterator(); iterator.hasNext();)
			clientList = clientList + ((Connection)iterator.next()).utente.getNome() + ";";
			
		return clientList;
	}
	
	public static String getGameList(Server server)
	{
		List<HostedGame> games = server.hostedGames;
		
		String gameList = Messages.GAMELIST;
		
		if(games.isEmpty())
			return gameList;
		
		HostedGame game;
		for (Iterator iterator = games.iterator(); iterator.hasNext();)
		{
			game = (HostedGame)iterator.next();
			
			gameList = gameList + game.getHost().utente.getNome() + ";";
			gameList = gameList + game.getGameName() + ";";
			gameList = gameList + game.getHost().player.getLocalAddress() + ";";
			gameList = gameList + game.getPorta() + ";";
		}
		
		return gameList;
	}
	
	public static boolean newUser(String msg, InetAddress ip)
	{
		String []values = msg.substring(Messages.NEWUSER.length()).split(";");
		if(DBFunctions.iscrivi(values[0], values[1], values[2], ip))
			return true;
		else
			return false;
	}
	
}