package lobby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class LobbyClient extends Thread {
	
	Socket connection;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	
 	OnlinePlayer onlinePlayer;
 	
 	List<String> players;
 	List<HostedGameInfo> hostedGameList;
 	
	public LobbyClient() {
	
		this.onlinePlayer = null;		
		
	}
	
	public boolean connect(String ip, int port)
	{
		try {
			
			this.connection = new Socket(ip, port);
			
			this.out = new ObjectOutputStream(this.connection.getOutputStream());
			this.out.flush();
			this.in = new ObjectInputStream(this.connection.getInputStream());
			
			if(this.readMessage() == Messages.WELCOME)
				return true;
			else
				return false;
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
		
		return false;
	}
	
	public void sendMessage(String msg)
	{
		try{
			this.out.writeObject(msg);
			this.out.flush();
		}
		catch(IOException ioException){
			System.out.println("Errore durante l'invio del messaggio al client");
		}
	}

	public String readMessage()
	{
		try{
			
			String message;
			message = (String)this.in.readObject();
			
			return message;
			
		} catch(IOException e) {
			System.out.println("Errore durante la ricezione del messaggio");
		} catch (ClassNotFoundException e) {
			System.out.println("Classe non trovata!");
		}
		
		return null;
	}
	
	public boolean logIn(String nome, String password)
	{
		this.sendMessage(Messages.LOGIN + nome + ";" + password);
		if(this.readMessage() == Messages.LOGINOK)
		{
			this.onlinePlayer = new OnlinePlayer(nome);
			this.start();
			return true;
		} else {
			return false;
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
		
		this.sendMessage(Messages.LOBBYSTARTED);
		
		while(!message.equals(Messages.CLOSE))
		{					
			
			message = this.readMessage();
			
			if(message.startsWith(Messages.CLIENTLIST))
				this.refreshClientList(message);
			else if(message.startsWith(Messages.GAMELIST))
				this.refreshGameList(message);
			else if(message.startsWith(Messages.CHAT))
				System.out.println(message);
			
		}
		
		this.closeConnection();
		
	}

	public void refreshClientList(String message)
	{
		this.players = LobbyClient.getClientList(message);
	}
	
	public void refreshGameList(String message)
	{
		this.hostedGameList = LobbyClient.getGameList(message);
	}
	
	public static List getClientList(String message)
	{
		String list = message.substring(Messages.CLIENTLIST.length());
		String []clients = list.split(";");
		
		List<String> clientList = new ArrayList<String>();
		
		for (int i = 0; i < clients.length; i++)
			clientList.add(clients[i]);
		
		return clientList;
	}
	
	public static List getGameList(String message)
	{
		String list = message.substring(Messages.GAMELIST.length());
		String []games = list.split(";");
		
		List<HostedGameInfo> gameList = new ArrayList<HostedGameInfo>();
		
		//TODO: ATTENZIONE POTREBBE FINIRE CON UN ; E LO SPLIT POTREBBE CREARE UN POSTO IN PIU'
		for (int i = 0; i < games.length; i+=4)
			gameList.add(new HostedGameInfo(games[i], games[i+1], games[i+2], Integer.parseInt(games[i+3])));
		
		return gameList;
	}
	
	public static void main(String[] args) {

		LobbyClient lobby = new LobbyClient();
	
	}
	
}