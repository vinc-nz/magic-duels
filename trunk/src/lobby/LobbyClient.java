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
import Menu.src.multiplayer.lobby.Lobby;

/**
 * The class manages all the communication between client and server 
 * @author Neb
 *
 */
public class LobbyClient extends Thread {
	
	public String playerName;
	
	Socket connection;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	
 	List<String> players;
 	List<HostedGameInfo> hostedGameList;
 	
 	public LobbyHostedGame hostedGame;
 	public LobbyJoinedGame joinedGame;
 	
 	Lobby graphicLobby;
 	
 	ServerGame serverGame;
	
 	int humanPlayers;
	int comPlayers;
	int port;
	int result;
	boolean isFinished;
	public static short SERVER_STARTED = 0;
	public static short SERVER_FAILED = 1;
	
	public LobbyClient() {
		
		this.playerName = null;
		
		this.hostedGame = null;
		this.joinedGame = null;
		
		this.graphicLobby = null;
	}
	
	/**
	 * Sets the Lobby variable
	 * @param graphicLobby
	 */
	public void setGraphicLobby(Lobby graphicLobby) {
		this.graphicLobby = graphicLobby;
	}

	/**
	 * @return a list of the player names logged to the server
	 */
	public List<String> getPlayers() {
		return players;
	}

	/** 
	 * @return a list of the hosted games given by the server
	 */
	public List<HostedGameInfo> getHostedGameList() {
		return hostedGameList;
	}

	/**
	 * @return the game hosted by the player
	 */
	public LobbyHostedGame getHostedGame() {
		return hostedGame;
	}
	
	/**
	 * @return the game joined by the player
	 */
	public LobbyJoinedGame getJoinedGame() {
		return joinedGame;
	}

	/**
	 * Connect the game to the server whose connection parameters are specified by the user
	 * @param server ip
	 * @param server port
	 * @return true if connected, false otherwise
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public boolean connect(String ip, int port) throws UnknownHostException, IOException
	{		
		this.connection = new Socket(ip, port);
		
		this.out = new ObjectOutputStream(this.connection.getOutputStream());
		this.out.flush();
		this.in = new ObjectInputStream(this.connection.getInputStream());
		
		if(this.readMessage().equals(Messages.WELCOME))
			return true;
		else
			return false;			
	}
	
	/**
	 * @return true if the game is connected to a server, dalse otherwise
	 */
	public boolean isConnected() { return (this.connection != null && this.connection.isConnected()); }
	
	/**
	 * Sends a message to the server 
	 * @param msg
	 */
	public void sendMessage(String msg)
	{
		try{
			this.out.writeObject(msg);
			this.out.flush();
			System.out.println("MANDO " + msg);
		}
		catch(IOException ioException)
		{
			System.out.println("Errore durante l'invio del messaggio al client");
			if(this.connection != null) this.closeConnection();
			this.graphicLobby.showWarning("La connessione è stata interrotta!");
			this.graphicLobby.mainMenu.switchTo(this.graphicLobby.mainMenu.panel);
		}
	}

	/**
	 * Reads a message from the server 
	 * @return the string received from the server
	 */
	public synchronized String readMessage()
	{
		try{
			
			String message;
			message = (String)this.in.readObject();
			
			return message;
			
		} catch(IOException e) {
			if(this.connection != null) this.closeConnection();
			this.graphicLobby.showWarning("La connessione è stata interrotta!");
			this.graphicLobby.mainMenu.switchTo(this.graphicLobby.mainMenu.panel);
		} catch (ClassNotFoundException e) {
			if(this.connection != null) this.closeConnection();
			this.graphicLobby.showWarning("La connessione è stata interrotta!");
			this.graphicLobby.mainMenu.switchTo(this.graphicLobby.mainMenu.panel);
		}
		
		return null;
	}
	
	/**
	 * Sends a login request to the server
	 * @param nome
	 * @param password
	 * @return true if logged, false otherwise
	 */
	public boolean logIn(String nome, String password)
	{
		
		this.sendMessage(Messages.LOGIN + nome + ";" + password);
		
		String message = this.readMessage(); 
		
		System.out.println("RICEVO>" + message);
		
		if(message.equals(Messages.LOGINOK))
		{
			this.playerName = nome;
			this.start();
			return true;
		} else if(message.equals(Messages.LOGINFAILED)) {
			return false;
		}
		return false;
	}
	
	/**
	 * Sends to the server server a message to advise it that the client is closing the connection
	 */
	public void sendByeMessage()
	{
		this.sendMessage(Messages.CLOSE);
	}
	
	/**
	 * Closes the connection with the server
	 */
	public void closeConnection()
	{
		try {
		
			//this.sendMessage(Messages.CLOSE);
			this.in.close();
			this.out.close();
			
			this.connection.close();
			this.connection = null;
			this.playerName = "NOMEEEEEEEEEEEEEEEEEEEEEE";
			
			this.graphicLobby.showWarning("A Presto!");
			this.graphicLobby.mainMenu.switchTo(this.graphicLobby.mainMenu.panel);
			
		} catch (IOException e) {
			this.graphicLobby.showWarning("Errore nella chiusura della connessione");
		}
	}
	
	/**
	 * The thread is started after the connection with the server.
	 * It manages the dialog among client and server
	 */
	@Override
	public void run() {
		
		while(this.graphicLobby == null ||this.graphicLobby.multiplayerGame == null)
			try {
				if(this.graphicLobby == null) System.out.println("GRAPHICLOBBY NULL");
				System.out.println("Nome Player: " + this.playerName);
				Thread.sleep(501);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		String message = null;
		
		this.sendMessage(Messages.REFRESHLIST);
		
		System.out.println("STEP 1");
		
		while(message == null || !message.equals(Messages.CLOSE))
		{					
			
			message = this.readMessage();
			
			System.out.println("RICEVO " + message);
			
			if(message.startsWith(Messages.CLIENTLIST))
				this.setClientList(message);
			
			else if(message.startsWith(Messages.GAMELIST))
				this.setGameList(message);
			
			else if(message.startsWith(Messages.CHAT))
				this.writeChatMessage(message);
			
			else if(message.startsWith(Messages.JOINOK))
				if(this.hostedGame == null)
					this.joinGame(message);
				else
					this.joinSlot(message);
			
			else if(message.equals(Messages.CREATEOK))
				this.graphicLobby.hostAGame();
			
			else if(message.equals(Messages.CREATEFAILED))
			{
				this.graphicLobby.showWarning("Impossibile creare la partita!");
				this.hostedGame = null;
			}
			
			else if(message.startsWith(Messages.CHANGESLOTTYPE))
				this.changeJoinedGameSlotType(message);
			
			else if(message.startsWith(Messages.SLOTLEFT))
				this.slotLeft(message);
			
			else if(message.startsWith(Messages.STARTSERVERGAME))
				this.startServerGame(message);
			
			else if(message.equals(Messages.STARTCLIENTGAME))
				this.startClientGame();
			
			else if(message.startsWith(Messages.GAMEKILLED))
				this.killGame();
			
			else if(message.startsWith(Messages.PLAYERKICKED))
				this.playerKicked();

			System.out.println("STEP 2");
			
		}
		
		System.out.println("STEP 3");

		
		this.closeConnection();
	}

	/**
	 * Sends the chat message to the server
	 * @param message the chat message
	 */
	public void sendChatMessage(String message)
	{
		this.sendMessage(Messages.CHAT + message);
	}
	
	/**
	 * Shows to the player the chat message
	 * @param message the message received from the server containing the chat message, preceded by protocol information
	 */
	public void writeChatMessage(String message)
	{	
		String []msg = message.substring(Messages.CHAT.length()).split(";", 2);
		this.graphicLobby.writeChatMessage(msg[1].substring(0, Integer.parseInt(msg[0])), msg[1].substring(Integer.parseInt(msg[0])));
	}
	
	/**
	 * Sets the player list and refreshes the graphic
	 * @param message the message received from the server containing the player list, preceded by protocol information
	 */
	public void setClientList(String message)
	{
		this.players = LobbyClient.createClientList(message);
		this.graphicLobby.refreshPlayerListPanel();
	}
	
	/**
	 * Sets the hosted game list and refreshes the graphic
	 * @param message the message received from the server containing the hosted game list, preceded by protocol information
	 */
	public void setGameList(String message)
	{
		this.hostedGameList = LobbyClient.createGameList(message);
		this.graphicLobby.refreshGameListPanel();
	}
	
	/**
	 * Asks the server to sign a new user to the dbms
	 * @param utente the user name
	 * @param password the user password
	 * @param mail the user mail
	 * @return true if signed, false otherwise
	 */
	public boolean newUser(String utente, String password, String mail)
	{
		this.sendMessage(Messages.NEWUSER + utente + ";" + password + ";" + mail);
		if(this.readMessage().equals(Messages.NEWUSEROK))
			return true;
		else
			return false;
	}
	
	/**
	 * Turns a string into a list by cutting protocol information and splitting it
	 * @param message the message sent by the server, containing protocol information
	 * @return a list of string containing the player logged to the server
	 */
	public static List createClientList(String message)
	{
		String list = message.substring(Messages.CLIENTLIST.length());
		String []clients = list.split(";");
		
		List<String> clientList = new ArrayList<String>();
		
		for (int i = 0; i < clients.length; i++)
			clientList.add(clients[i]);
		
		return clientList;
	}
	
	/**
	 * Turns a string into a list by cutting protocol information and splitting it
	 * @param message the message sent by the server, containing protocol information
	 * @return a list of string containing the hosted game contained into the server
	 */
	public static List createGameList(String message)
	{
		String list = message.substring(Messages.GAMELIST.length());

		if(list.length() == 0)
			return new ArrayList();
		
		String []games = list.split(";");
		
		List<HostedGameInfo> gameList = new ArrayList<HostedGameInfo>();
		
		for (int i = 0; i < games.length; i+=4)
			gameList.add(new HostedGameInfo(games[i], games[i+1], games[i+2], Integer.parseInt(games[i+3])));
		
		return gameList;
	}
	
	/**
	 * Sends a message to the server to ask both the player and game lists
	 */
	public void requestListRefresh()
	{
		this.sendMessage(Messages.REFRESHLIST);
	}
	
	/**
	 * Creates a new LobbyHostedGame object and advice the server
	 * @param gameName the name of the hosted game
	 * @param numSlots the number of slot of the hosted game
	 * @param numPorta the port number of the hosted game
	 */
	public void createGame(String gameName, int numSlots, int numPorta)
	{
		this.hostedGame = new LobbyHostedGame(this, gameName, numPorta, numSlots);
		
		String msg = Messages.CREATE;
		msg += gameName + ";";
		msg += String.valueOf(numSlots) + ";";
		msg += String.valueOf(numPorta) + ";";
		
		this.sendMessage(msg);
	}
	
	/**
	 * Requests to the server to join a game
	 * @param gameName the name of the game that you want to join to
	 * @param ip the ip of the game that you want to join to
	 * @param porta the port of the game that you want to join to
	 */
	public void tryJoiningGame(String gameName, String ip, int porta)
	{
		this.joinedGame = new LobbyJoinedGame(gameName, ip, porta);

		String msg = Messages.JOIN;
		msg += gameName;
		
		this.sendMessage(msg);
	}
	
	/**
	 * Adds the joined game information and refreshes the graphic
	 */
	public void joinGame(String msg)
	{
		String []parameter = msg.substring(Messages.JOINOK.length()).split(";");
		
		for (int i = 0; i < parameter.length; i++)
			this.joinedGame.addSlot(parameter[i]);
		
		this.graphicLobby.joinAGame();
	}
	
	/**
	 * Changes the joined game information and refreshes the graphic
	 * @param msg the slot information sent by the server. contains protocol information
	 */
	public void changeJoinedGameSlotType(String msg)
	{
		if(this.joinedGame == null) return;
		
		this.joinedGame.changeSlotType(msg);
		this.graphicLobby.joinAGame();
	}
	
	/**
	 * Changes the hosted game information and refreshes the graphic
	 * @param msg the slot information sent by the server. contains protocol information
	 */
	public void slotLeft(String msg)
	{
		int slotIndex = Integer.parseInt(msg.substring(Messages.SLOTLEFT.length()));
		
		this.hostedGame.changeSlotState(slotIndex, Messages.OPEN);	
		this.graphicLobby.hostAGame();
	}
	
	/**
	 * Changes the hosted game information and refreshes the graphic
	 * @param msg the slot information sent by the server. contains protocol information
	 */
	public void joinSlot(String msg)
	{
		String []parameter = msg.substring(Messages.JOINOK.length()).split(";");

		this.hostedGame.joinSlot(Integer.parseInt(parameter[0]), parameter[1]);
	}
	
	/**
	 * Communicates to the server that the client has left the game
	 */
	public void leaveSlot()
	{
		this.sendMessage(Messages.LEAVESLOT);
	}
	
	/**
	 * Communicates to the player that he's been kicked by the host and refreshes the graphic
	 */
	public void playerKicked()
	{
		this.graphicLobby.showWarning("Sei Stato Cacciato dalla Partita!");
		this.graphicLobby.multiplayerGame();
	}
	
	/**
	 * Communicates the client that the game has been destroyed and refreshes the graphic
	 * @param msg
	 */
	public void killGame()
	{
		this.hostedGame = null;
		this.joinedGame = null;
		
		this.graphicLobby.showWarning("La partita è stata distrutta!");
		this.graphicLobby.multiplayerGame();
	}
	
	/**
	 * Starts a new server game
	 * @param msg the message sent by the server containing game and protocol information 
	 */
	public synchronized void startServerGame(String msg)
	{
		String []parameter = msg.substring(Messages.STARTSERVERGAME.length()).split(";");
		
		this.humanPlayers = Integer.parseInt(parameter[0]);
		this.comPlayers = Integer.parseInt(parameter[1]);
		
		PlayerMote playerMote = this.graphicLobby.mainMenu.playMote;
		MainMenu mainMenu = this.graphicLobby.mainMenu;
		
		this.port = this.hostedGame.getPorta();
		
		this.serverGame = new ServerGame(playerMote, mainMenu);
				
		this.isFinished = false;
		
		Runnable init = new Runnable() {
			@Override
			public void run() {	
				LobbyClient.this.result = LobbyClient.SERVER_STARTED;
				try {
					LobbyClient.this.serverGame.init(LobbyClient.this.playerName, LobbyClient.this.humanPlayers,
					LobbyClient.this.comPlayers, LobbyClient.this.port);
					LobbyClient.this.serverGame.start();
					LobbyClient.this.isFinished = true;
					synchronized (this) {
						LobbyClient.this.notify_all();		
					}
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
		{
			this.sendMessage(Messages.SERVERGAMESTARTED);
			while(!this.isFinished)
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			this.graphicLobby.multiplayerGame();
		} else
			this.graphicLobby.showWarning("Attenzione!\nSembra vi sia un errore che impedisca l'inizio della partita!");
			
	}
	
	/**
	 * Starts a new client game
	 */
	public void startClientGame()
	{
		PlayerMote playerMote = this.graphicLobby.mainMenu.playMote;
		MainMenu mainMenu = this.graphicLobby.mainMenu;
		
		ClientGame game = new ClientGame(playerMote, mainMenu);
		
		String name = this.joinedGame.gameName;
		String address = this.joinedGame.ip;
		if(address.startsWith("/")) address = address.substring(1);
		int port = this.joinedGame.porta;
				
		try {
			game.init(name, address, port);
			game.start();
			this.graphicLobby.multiplayerGame();
		} catch (UnknownHostException e) {
			this.graphicLobby .showWarning("Errore durante la connessione all'Host!");
			this.graphicLobby.multiplayerGame();
		} catch (IOException e) {
			this.graphicLobby .showWarning("Errore durante la connessione all'Host!");
			this.graphicLobby.multiplayerGame();
		}
	}
	
	public synchronized void notify_all()
	{
		this.notifyAll();
	}
}