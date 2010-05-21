package net;

import game.Game;

import java.io.IOException;

import wiiMoteInput.PlayerMote;
import Menu.src.MainMenu;



public abstract class NetGame extends Game {
	int localId;
	String name;
	
	
	
	public NetGame(PlayerMote playerMote, MainMenu mainMenu) {
		super(playerMote, mainMenu);
		// TODO Auto-generated constructor stub
		
	}



	public void init(String name) throws IOException {
		this.name = name;
	}

	public abstract void forward(String trigger) throws IOException;
	

	
//	@Override
//	protected void initGame() {
//		super.initGame();
//		try {
//			this.sayReady();
//			this.waitOthers();
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.exit(1);
//		}
//		this.startFight();
//	}
	
	@Override
	protected void cleanup() {
		this.leave();
		super.cleanup();
	}
	
	public void leave() {
		try {
			this.forward(Message.getLeaveMessage(localId));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void notifyLeaving(int leavingId) {
		this.showMessage(getFight().getPlayer(leavingId).getName()+" ha abbandonato la partita");
	}
	
}
