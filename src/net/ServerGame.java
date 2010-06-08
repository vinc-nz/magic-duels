package net;

import ia.Ia;
import input.CharacterController;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import wiiMoteInput.PlayerMote;
import Menu.src.MainMenu;

public class ServerGame extends NetGame {
	Socket[] channels;
	NetListener[] listeners;
	DataOutputStream[] out;
	String[] names;
	int playersJoined = 0;
	int clientsReady = 0;
	
	

	public ServerGame(PlayerMote playerMote, MainMenu mainMenu) {
		super(playerMote, mainMenu);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLoadingSteps() {
		return super.getLoadingSteps() + 2;
	}
	

	public void init(String name, int humanPlayers, int comPlayers, int port)
			throws IOException {
		super.init(name);
		int numberOfPlayers = humanPlayers + comPlayers;
		this.localId = 1;
		this.initFight(numberOfPlayers);
		names = new String[numberOfPlayers];
		names[0] = this.name;
		channels = new Socket[humanPlayers-1];
		ServerSocket s = new ServerSocket(port);
		for (int i=0;i<channels.length;i++) {
			channels[i] = s.accept();
			this.acceptClient(channels[i], i);
		}
		for (int i=1;i<=comPlayers;i++) {
			int id = humanPlayers + i;
			NetForwarderController c = new NetForwarderController(id, getFight(), this);
			new Ia(c).start();
		}
	}

	private void acceptClient(Socket socket, int index) throws IOException {
		int clientId = index + 2;
		NetForwarderController controller = 
			new NetForwarderController(clientId, getFight(), this);
		listeners = new ServerListener[channels.length];
		listeners[index] = new ServerListener(controller, socket.getInputStream(), this);
		listeners[index].start();
		String message = Message.getClientAcceptedMessage(clientId, getFight().numberOfPlayers());
		out = new DataOutputStream[channels.length];
		out[index] = new DataOutputStream(socket.getOutputStream());
		out[index].writeBytes(message+"\n");
	}



//	@Override
//	public void buildListening() throws IOException {
//		listeners = new NetListener[channels.length];
//		for (int i = 0; i < listeners.length; i++) {
//			NetForwarderController controller = 
//				new ClientController(i+2, this.getFight(), channels[i].getOutputStream());
//			listeners[i] = new ServerListener(controller, channels[i].getInputStream(), this);
//			listeners[i].start();
//		}
//	}
//	
//	@Override
//	public void buildForwarding() throws IOException {
//		out = new DataOutputStream[channels.length];
//		for (int i = 0; i < out.length; i++) {
//			out[i] = new DataOutputStream(channels[i].getOutputStream());
//		}
//	}

	@Override
	protected CharacterController buildCharacterController() {
		return new NetForwarderController(localId, getFight(), this);
	}


	@Override
	public synchronized void forward(String message) throws IOException {
		for (int i = 0; i < out.length; i++) {
			out[i].writeBytes(message + "\n");
			System.out.println(message);
		}
	}


	public synchronized void waitClients() throws IOException {
		if (clientsReady<channels.length)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		String message = Message.getServerReadyMessage(names);
		this.forward(message);
	}

	public synchronized void clientReady() {
		clientsReady++;
		if (clientsReady==channels.length)
			notify();
	}
	
	@Override
	protected void cleanup() {
		super.cleanup();
		for (int i = 0; i < channels.length; i++) {
			try {
				out[i].close();
				channels[i].close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void initScene() {
		super.initScene();
		try {
			this.getLoading().increment("waiting for other players");
			this.waitClients();
			this.getLoading().increment();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.getFight().setNames(names);
	}



	public synchronized void joinClient(String clientName) {
		names[++playersJoined] = clientName;
	}
	
	public void joinIA(String name) {
		//TODO
	}
	
	public static void main(String[] args) {
		PlayerMote playerMote = new PlayerMote();
		playerMote.findMote();
		ServerGame game = new ServerGame(playerMote, null);
		try {
			game.init("spax", 2,0, 6000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.start();
	}

}
