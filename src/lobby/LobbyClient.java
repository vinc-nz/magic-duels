package lobby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.ClientGame;
import net.ServerGame;
import wiiMoteInput.PlayerMote;
import Menu.src.MainMenu;
import Menu.src.lobby.Lobby;

public class LobbyClient extends Thread {
	
	Socket connection;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	
 	List<String> players;
 	List<HostedGameInfo> hostedGameList;
 	
 	LobbyHostedGame hostedGame;
 	LobbyJoinedGame joinedGame;
 	
 	// Grafica della Lobby
 	Lobby graphicLobby;
 	
 	ServerGame serverGame;
 	String name;
	int humanPlayers;
	int comPlayers;
	int port;
	int result;
	public static short SERVER_STARTED = 0;
	public static short SERVER_FAILED = 1;
	
	public LobbyClient() {
		this.hostedGame = null;
		this.joinedGame = null;
	}
	
	public void setGraphicLobby(Lobby graphicLobby) {
		this.graphicLobby = graphicLobby;
	}

	public List<String> getPlayers() {
		return players;
	}

	public List<HostedGameInfo> getHostedGameList() {
		return hostedGameList;
	}

	public LobbyHostedGame getHostedGame() {
		return hostedGame;
	}
	
	public LobbyJoinedGame getJoinedGame() {
		return joinedGame;
	}

	public boolean connect(String ip, int port)
	{
		try {
			
			this.connection = new Socket(ip, port);
			
			this.out = new ObjectOutputStream(this.connection.getOutputStream());
			this.out.flush();
			this.in = new ObjectInputStream(this.connection.getInputStream());
			
			if(this.readMessage().equals(Messages.WELCOME))
				return true;
			else
				return false;
			
		} catch (UnknownHostException e) {
			System.out.println("L'host è sconosciuto");
		} catch (IOException e) {
			System.out.println("Errore durante la connessione all'Host");
			e.printStackTrace();
		}		
		
		return false;
	}
	
	public boolean isConnected()
	{
		return (this.connection != null && this.connection.isConnected());
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

	public synchronized String readMessage()
	{
		try{
			
			String message;
			message = (String)this.in.readObject();
			
			return message;
			
		} catch(IOException e) {
			System.out.println("Errore durante la ricezione del messaggio");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Classe non trovata!");
		}
		
		return null;
	}
	
	public boolean logIn(String nome, String password)
	{
		this.sendMessage(Messages.LOGIN + nome + ";" + password);
		if(this.readMessage().equals(Messages.LOGINOK))
		{
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
		
		while(this.graphicLobby == null ||this.graphicLobby.multiplayerGame == null)
			try {
				Thread.sleep(501);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		String message = null;
		
		this.sendMessage(Messages.REFRESHLIST);
		
		while(message == null || !message.equals(Messages.CLOSE))
		{					
			
			message = this.readMessage();
			
			if(message.startsWith(Messages.CLIENTLIST))
				this.setClientList(message);
			
			else if(message.startsWith(Messages.GAMELIST))
				this.setGameList(message);
			
			else if(message.startsWith(Messages.CHAT))
				this.writeChatMessage(message);
			
			else if(message.startsWith(Messages.JOINOK))
				this.joinGame(message);
				
			else if(message.equals(Messages.CREATEOK))
				this.graphicLobby.hostAGame();
			
			else if(message.equals(Messages.CREATEFAILED))
				System.out.println("NON CREATA");
				
			else if(message.startsWith(Messages.CHANGESLOTTYPE))
				this.changeJoinedGameSlotType(message);
			
			else if(message.startsWith(Messages.STARTSERVERGAME))
				this.startServerGame(message);
			
			else if(message.equals(Messages.STARTCLIENTGAME))
				this.startClientGame();
			
			else if(message.startsWith(Messages.GAMEKILLED))
				this.killGame(message);
			
			else if(message.startsWith(Messages.PLAYERKICKED))
				System.out.println("SEI STATO BANNATO!!");
			
			System.out.println("RICEVO " + message);

		}
		
		this.closeConnection();
	}

	public void sendChatMessage(String message)
	{
		this.sendMessage(Messages.CHAT + message);
		System.out.println("INVIO " + Messages.CHAT + message);
	}
	
	public void writeChatMessage(String message)
	{
		
		String []msg = message.substring(Messages.CHAT.length()).split(";", 2);
		
		this.graphicLobby.writeChatMessage(msg[1].substring(0, Integer.parseInt(msg[0])), msg[1].substring(Integer.parseInt(msg[0])));
	}
	
	public void setClientList(String message)
	{
		this.players = LobbyClient.createClientList(message);
		this.graphicLobby.refreshPlayerListPanel();
	}
	
	public void setGameList(String message)
	{
		this.hostedGameList = LobbyClient.createGameList(message);
		this.graphicLobby.refreshGameListPanel();
	}
	
	public boolean newUser(String utente, String password, String mail)
	{
		this.sendMessage(Messages.NEWUSER + utente + ";" + password + ";" + mail);
		if(this.readMessage().equals(Messages.NEWUSEROK))
			return true;
		else
			return false;
	}
	
	public static List createClientList(String message)
	{
		String list = message.substring(Messages.CLIENTLIST.length());
		String []clients = list.split(";");
		
		List<String> clientList = new ArrayList<String>();
		
		for (int i = 0; i < clients.length; i++)
			clientList.add(clients[i]);
		
		return clientList;
	}
	
	public static List createGameList(String message)
	{
		String list = message.substring(Messages.GAMELIST.length());

		if(list.length() == 0)
			return new ArrayList();
		
		String []games = list.split(";");
		
		List<HostedGameInfo> gameList = new ArrayList<HostedGameInfo>();
		
		//TODO: ATTENZIONE POTREBBE FINIRE CON UN ; E LO SPLIT POTREBBE CREARE UN POSTO IN PIU'
		for (int i = 0; i < games.length; i+=4)
			gameList.add(new HostedGameInfo(games[i], games[i+1], games[i+2], Integer.parseInt(games[i+3])));
		
		return gameList;
	}
	
	public void requestListRefresh()
	{
		this.sendMessage(Messages.REFRESHLIST);
	}
	
	public void createGame(String gameName, int numSlots, int numPorta)
	{
		String msg = Messages.CREATE;
		msg += gameName + ";";
		msg += String.valueOf(numSlots) + ";";
		msg += String.valueOf(numPorta) + ";";

		this.hostedGame = new LobbyHostedGame(this, gameName, numPorta, numSlots);

		this.sendMessage(msg);
	}
	
	public void tryJoiningGame(String gameName, String ip, int porta)
	{
		this.joinedGame = new LobbyJoinedGame(gameName, ip, porta);

		String msg = Messages.JOIN;
		msg += gameName;
		
		this.sendMessage(msg);
	}
	
	public void joinGame(String msg)
	{
		String []parameter = msg.substring(Messages.JOINOK.length()).split(";");
		
		for (int i = 0; i < parameter.length; i++)
			this.joinedGame.addSlot(parameter[i]);
		
		this.graphicLobby.joinAGame();
	}
	
	public void changeJoinedGameSlotType(String msg)
	{
		if(this.joinedGame == null) return;
		
		this.joinedGame.changeSlotType(msg);
		this.graphicLobby.joinAGame();
	}
	
	public void killGame(String msg)
	{
		this.hostedGame = null;
		this.joinedGame = null;
		
		this.graphicLobby.showWarning("La partita è stata distrutta!");
		this.graphicLobby.multiplayerGame();
	}
	
	public void startServerGame(String msg)
	{
		String []parameter = msg.substring(Messages.STARTSERVERGAME.length()).split(";");
		
		this.humanPlayers = Integer.parseInt(parameter[0]);
		this.comPlayers = Integer.parseInt(parameter[1]);
		
		PlayerMote playerMote = this.graphicLobby.mainMenu.playMote;
		MainMenu mainMenu = this.graphicLobby.mainMenu;
		
		this.name = this.hostedGame.getGameName();
		this.port = this.hostedGame.getPorta();
		
		this.serverGame = new ServerGame(playerMote, mainMenu);
		
		System.out.println(name + " - " + humanPlayers + " - " + comPlayers + " - " + port);
					
		Runnable init = new Runnable() {
			@Override
			public void run() {	
				LobbyClient.this.result = LobbyClient.SERVER_STARTED;
				try {
					LobbyClient.this.serverGame.init(LobbyClient.this.name, LobbyClient.this.humanPlayers,
							LobbyClient.this.comPlayers, LobbyClient.this.port);
					LobbyClient.this.serverGame.start();
				} catch (IOException e) {
					LobbyClient.this.result = LobbyClient.SERVER_FAILED;
				}
			}
		};
		
		Thread thread = new Thread(init);
		thread.start();
		
		try {
				Thread.sleep(101);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		if(this.result == LobbyClient.SERVER_STARTED)
			this.sendMessage(Messages.SERVERGAMESTARTED);
		else
			System.out.println("ERRORE SERVER!");

	}
	
	public void startClientGame()
	{
		PlayerMote playerMote = this.graphicLobby.mainMenu.playMote;
		MainMenu mainMenu = this.graphicLobby.mainMenu;
		
		ClientGame game = new ClientGame(playerMote, mainMenu);
		
		String name = this.joinedGame.gameName;
		String address = this.joinedGame.ip;
		if(address.startsWith("/")) address = address.substring(1);
		int port = this.joinedGame.porta;
		
		System.out.println(name + " - " + address + " - " + port);
		
		try {
			game.init(name, address, port);
			game.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}