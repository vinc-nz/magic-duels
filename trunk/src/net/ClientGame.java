package net;

import input.CharacterController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import wiiMoteInput.PlayerMote;
import Menu.src.MainMenu;

public class ClientGame extends NetGame {
	Socket channel;
	NetListener listener;
	DataOutputStream out;
	boolean serverReady = false;
	
	
	
	public ClientGame(PlayerMote playerMote, MainMenu mainMenu) {
		super(playerMote, mainMenu);
		// TODO Auto-generated constructor stub
	}

	public void init(String name, String address, int port) throws UnknownHostException, IOException {
		super.init(name);
		channel = new Socket(address, port);
		out = new DataOutputStream(channel.getOutputStream());
		out.writeBytes(Message.getJoinClientMessage(name)+"\n");
		this.initFight(this.waitResponse());
		this.buildListening();
	}
	
	private int waitResponse() throws IOException {
		InputStreamReader reader = new InputStreamReader(channel.getInputStream());
		BufferedReader in = new BufferedReader(reader);
		String message = in.readLine();
		this.localId = Message.clientAccepted(message);
		return Message.getNumberOfPlayers(message);
	}

	@Override
	protected CharacterController buildCharacterController() {
		return new NetForwarderController(localId, getFight(), this);
	}
	
	protected void buildListening() throws IOException {
		NetCharacterController[] controllers = new NetCharacterController[this.getFight().numberOfPlayers()];
		for (int i = 0; i < controllers.length; i++) {
			int clientId = i+1;
			if (clientId!=localId)
				controllers[i] = new NetCharacterController(clientId);
		}
		this.listener = new ClientListener(controllers, channel.getInputStream(), this);
		this.listener.start();
	}
	
	public void sayReady() throws IOException {
		this.forward(Message.getClientReadyMessage());
	}

	public synchronized void waitServer() throws IOException {
		if (!serverReady)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public synchronized void serverReady(String[] names) {
		serverReady = true;
		this.getFight().setNames(names);
		notify();
	}
	
	@Override
	protected void cleanup() {
		super.cleanup();
		try {
			out.close();
			channel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void forward(String trigger) throws IOException {
		out.writeBytes(trigger+"\n");
	}
	
	@Override
	protected void initGame() {
		super.initGame();
		try {
			this.sayReady();
			this.waitServer();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.startFight();
	}
	
	
	public static void main(String[] args) {
		ClientGame game = new ClientGame(null, null);
		try {
			game.init("spax", "localhost", 6000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.start();
	}
	
}
