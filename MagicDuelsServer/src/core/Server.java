package core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;

public class Server extends Thread{

	ServerSocket serverSocket;
	
	HashMap<String, Connection> players;
	HashMap<String, HostedGame> hostedGames;
	
	ListRefreshingThread listRefreshingThread;
	
	public Server(int porta) throws UnknownHostException {
	
		try {
			this.serverSocket = new ServerSocket(porta);
			System.out.println("SERVER IN ASCOLTO SULLA PORTA: " + porta);
		} catch (IOException e) {
			System.out.println("Impossibile aprire una connessione!");
			return;
		}
		
		System.out.println("ADDRESS: " + InetAddress.getLocalHost().getHostAddress());
		
		this.players = new HashMap<String, Connection>();
		this.hostedGames = new HashMap<String, HostedGame>();
		
		this.start();
		
		this.listRefreshingThread = new ListRefreshingThread(this);
		this.listRefreshingThread.start();
	}
	
	public void sendChatMessage(String sender, String msg)
	{	
		for (Iterator iterator = this.players.keySet().iterator(); iterator.hasNext();) 
		{
			Connection connection = this.players.get(iterator.next());	
			connection.sendMessage(Messages.CHAT + sender.length() + ";" + sender + msg);
		}	
	}

	public synchronized void removePlayer(Connection player)
	{
		this.removeHostedGame(player.hostedGame);
		Connection conn = this.players.remove(player.utente.getNome());
		if(conn == null) this.players.remove(player.player.getInetAddress().toString());
		
		this.refreshLists();
	}
	
	public void removeHostedGame(HostedGame hostedGame)
	{
		if(hostedGame != null)
		{
			for (Iterator iterator = this.hostedGames.keySet().iterator(); iterator.hasNext();) {
				HostedGame host = this.hostedGames.get(iterator.next());
				for (Iterator iterator2 = host.slots.iterator(); iterator2.hasNext();) {
					HostedGameSlot slot = (HostedGameSlot) iterator2.next();
					if(slot.isHuman())
						slot.human.sendMessage(Messages.GAMEKILLED);
				}
			}
			this.hostedGames.remove(hostedGame.gameName);
		
			this.refreshGameList();

		}		
	}
	
	@Override
	public void run() {

		Socket connection;
		
		while(true)
		{
			try {
				System.out.println("ATTENDO NUOVA CONNESSIONE");
				connection = this.serverSocket.accept();
				System.out.println("NEW CONNECTION: " + connection.getInetAddress());
				this.players.put(connection.getInetAddress().toString(), new Connection(connection, this));

			} catch (IOException e) {
				System.out.println("Impossibile accettare la connessione!");
			}	
		}
	}

	public void refreshLists()
	{
		for (Iterator iterator = this.players.keySet().iterator(); iterator.hasNext();)
		{
			Connection conn = this.players.get((String) iterator.next());
			conn.sendMessage(Server.getClientList(this));
			conn.sendMessage(Server.getGameList(this));
		}
	}
	
	public void refreshGameList()
	{
		for (Iterator iterator = this.players.keySet().iterator(); iterator.hasNext();)
		{
			Connection conn = this.players.get((String) iterator.next());
			conn.sendMessage(Server.getGameList(this));
		}	
	}
	
	public void refreshClientList()
	{
		for (Iterator iterator = this.players.keySet().iterator(); iterator.hasNext();)
		{
			Connection conn = this.players.get((String) iterator.next());
			conn.sendMessage(Server.getClientList(this));
		}	
	}
	
	public boolean isLogged(String nomeUtente){ return this.players.containsKey(nomeUtente); }
	
	public static String getClientList(Server server)
	{
		HashMap<String, Connection> clients = server.players;
		
		String clientList = Messages.CLIENTLIST;
		
		for(Iterator iterator = clients.keySet().iterator(); iterator.hasNext();)
		{
			/*
			Connection client = clients.get(iterator.next()); 
			if(client == null)
				System.out.println("NULL POINTER!");
			clientList = clientList + client.utente.getNome() + ";";
			*/
			clientList = clientList + clients.get(iterator.next()).utente.getNome() + ";";
		}
		
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
			gameList = gameList + game.getHost().player.getInetAddress() + ";";
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