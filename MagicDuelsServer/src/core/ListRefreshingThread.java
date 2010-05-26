package core;

import java.util.Iterator;

public class ListRefreshingThread extends Thread {

	Server server;
	
	public ListRefreshingThread(Server server) {
		super();
		this.server = server;
	}

	@Override
	public void run() {
		
		while(true)
		{
			try {
				this.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(!this.server.players.isEmpty())
				for (Iterator iterator = this.server.players.keySet().iterator(); iterator.hasNext();)
				{
					Connection player = this.server.players.get((String)iterator.next());
					
					player.initLobby();
				}
		}
	}
	
}
