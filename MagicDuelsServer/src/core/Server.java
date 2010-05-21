package core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class Server extends Thread{

	ServerSocket serverSocket;
	//List<Connection> players;
	HashMap<String, Connection> players;
	HashMap<String, HostedGame> hostedGames;
	
	public Server(int porta) {
	
		try {
			this.serverSocket = new ServerSocket(porta);
			System.out.println("SERVER IN ASCOLTO SULLA PORTA: " + porta);
		} catch (IOException e) {
			System.out.println("Impossibile aprire una connessione!");
		}
		
		//this.players = new LinkedList<Connection>();
		this.players = new HashMap<String, Connection>();
		this.hostedGames = new HashMap<String, HostedGame>();
		
		this.start();
	}
	
	public void sendChatMessage(String sender, String msg)
	{	
		for (Iterator iterator = this.players.keySet().iterator(); iterator.hasNext();) 
		{
			Connection connection = this.players.get(iterator.next());	
			connection.sendMessage(Messages.CHAT + sender.length() + ";" + sender + msg);
			
		}	
	}

	public void removePlayer(Connection player)
	{
		if(player.hostedGame != null)
			this.hostedGames.remove(player.hostedGame.gameName);
		
		this.players.remove(player.utente.getNome());
		
		// BISOGNA AVVISARE GLI ALTRI DELLA NUOVA LISTA DI GAMES E DI PLAYERS
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
				this.players.put(connection.getLocalAddress().toString(), new Connection(connection, this));

			} catch (IOException e) {
				System.out.println("Impossibile accettare la connessione!");
			}
			
		}
	}
	
	public static String getClientList(Server server)
	{
		HashMap<String, Connection> clients = server.players;
		
		String clientList = Messages.CLIENTLIST;
		
		for (Iterator iterator = clients.keySet().iterator(); iterator.hasNext();)
			clientList = clientList + clients.get(iterator.next()).utente.getNome() + ";";
			
		return clientList;
	}
	
	public static String getGameList(Server server)
	{
		HashMap<String, HostedGame> games = server.hostedGames;
		
		String gameList = Messages.GAMELIST;
		
		if(games.isEmpty())
			return gameList;
		
		HostedGame game;
		for (Iterator iterator = games.keySet().iterator(); iterator.hasNext();)
		{
			game = games.get(iterator.next());
			
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