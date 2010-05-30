package core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;

import entities.Utente;

public class Connection extends Thread {
	
	Utente utente;
	
	Socket player;
	Server server;
	
	ObjectOutputStream out;
	ObjectInputStream in;

	HostedGame hostedGame;
	HostedGame joinedGame;
	
	public Connection(Socket player, Server server) {

		this.utente = null;
		
		this.player = player;
		this.server = server;
		
		this.hostedGame = null;
				
		try {
			
			this.out = new ObjectOutputStream(player.getOutputStream());
			this.out.flush();
			this.in = new ObjectInputStream(player.getInputStream());
			
			this.sendMessage(Messages.WELCOME);
			
			this.start();
			
		} catch (IOException e) {
			System.out.println("Server>Errore durante la creazione degli IOStream");
		}
		
	}
	
	@Override
	public void run() {
		
		String message = null;
		
		do
		{
			message = this.readMessage();
			
			if(message.startsWith(Messages.LOGIN))
				login(message);
			
			if(message.startsWith(Messages.NEWUSER))
				if(Server.newUser(message, this.player.getInetAddress()))
					this.sendMessage(Messages.NEWUSEROK);
				else
					this.sendMessage(Messages.NEWUSERFAILED);
			
		} while(!message.equals(Messages.CLOSE) && !message.equals(Messages.KILL) && this.utente == null);
				
		if(this.utente != null)
		{
			
			while(!message.equals(Messages.CLOSE) && !message.equals(Messages.KILL))
			{				
				message = this.readMessage();
				System.out.println("client>" + message);
				
				if (message.equals(Messages.CLOSE))
					this.sendMessage(Messages.CLOSE);		
			
				else if(message.startsWith(Messages.CHAT))
					this.server.sendChatMessage(this.utente.getNome(), message.substring(Messages.CHAT.length()));
				
				else if(message.equals(Messages.REFRESHLIST))
					this.initLobby();
				
				else if(message.startsWith(Messages.CREATE))
					this.newGame(message);
				
				else if(message.startsWith(Messages.JOIN))
					this.joinGame(message);
				
				else if(message.equals(Messages.LEAVESLOT))
					this.leaveSlot();
				
				else if(message.startsWith(Messages.GAMEKILLED))
				{
					this.server.removeHostedGame(this.hostedGame);
					this.hostedGame = null;
				}
				
				else if(message.startsWith(Messages.CHANGESLOTTYPE))
					this.hostedGame.changeSlotType(message);
				
				else if(message.startsWith(Messages.READYTOSTART))
					this.hostedGame.startServerGame();
			
				else if(message.startsWith(Messages.SERVERGAMESTARTED))
					this.hostedGame.startClientGame();
				
			}
		}
		
		this.closeConnection();
		this.server.removePlayer(this);
		
		System.out.println("FINE THREAD " +  this.utente.getNome());
	}

	public void login(String msg)
	{
		String []logInfo = (msg.substring(Messages.LOGIN.length())).split(";");
		
		if(this.server.isLogged(logInfo[0]))
		{
			this.sendMessage(Messages.LOGINFAILED);
			return;
		}
		
		this.utente = DBFunctions.logIn(logInfo[0], logInfo[1], this.player.getInetAddress());
		if(this.utente == null)
			this.sendMessage(Messages.LOGINFAILED);
		else
		{
			this.server.players.remove(this.player.getInetAddress().toString());			
			this.server.players.put(utente.getNome(), this);
			DBFunctions.newConnectionLog(this.player.getInetAddress(), this.utente);
			this.sendMessage(Messages.LOGINOK);
		}
	}
	
	public void sendMessage(String msg)
	{
		try{
			this.out.writeObject(msg);
			this.out.flush();
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			System.out.println("Server>Errore durante l'invio del messaggio al client");
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
			return Messages.KILL;		
		} catch (ClassNotFoundException e) {
			System.out.println("Classe non trovata!");
			return Messages.KILL;		
		}
	}
	
	protected void closeConnection()
	{
		try {
			this.in.close();
			this.out.close();
			this.player.close();
		} catch (IOException e) {
			System.out.println("Errore nella chiusura della connessione");
		}
		
	}	
	
	public void initLobby()
	{
		this.sendMessage(Server.getClientList(this.server));
		this.sendMessage(Server.getGameList(this.server));
	}

	public void newGame(String msg)
	{
		String []gameParameter = msg.substring(Messages.CREATE.length()).split(";");
		
		if(this.hostedGame != null){ this.sendMessage(Messages.CREATEFAILED); return; }
		
		this.hostedGame = new HostedGame(this, Integer.parseInt(gameParameter[2]), gameParameter[0], Integer.parseInt(gameParameter[1]));
		this.server.hostedGames.put(this.hostedGame.gameName, this.hostedGame);
		
		this.sendMessage(Messages.CREATEOK);
	}
	
	public void joinGame(String msg)
	{
		String gameName = msg.substring(Messages.JOIN.length());
		
		HostedGame hostedGame = this.server.hostedGames.get(gameName);
		if(hostedGame == null){ this.sendMessage(Messages.JOINFAILED); return; }
		int slotIndex = hostedGame.joinGame(this);
		if(slotIndex == -1){ this.sendMessage(Messages.JOINFAILED); return; }
		
		String message = Messages.JOINOK;
		message += hostedGame.getSlotMessageInfo();
		this.sendMessage(message);

		message = Messages.JOINOK;
		message += slotIndex + ";";
		message += this.utente.getNome() + ";";
		hostedGame.host.sendMessage(message);
		
		for (Iterator iterator = hostedGame.slots.iterator(); iterator.hasNext();) {
			HostedGameSlot slot = (HostedGameSlot) iterator.next();
			
			if(slot.isHuman())
			{
				message = Messages.CHANGESLOTTYPE;
				//message += String.valueOf(hostedGame.slots.indexOf(slot)) + ";";
				message += String.valueOf(slotIndex) + ";";
				message += this.utente.getNome();
				
				slot.human.sendMessage(message);
			}
			
		}
		
		this.joinedGame = hostedGame;
	}
	
	public void leaveSlot()
	{
		this.joinedGame.leaveGame(this.utente.getNome());
	}
	
}