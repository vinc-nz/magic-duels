package Menu.src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WiiMoteButtonListener implements ActionListener {

	MainMenu mainMenu;
	Options options;
	
	public WiiMoteButtonListener(MainMenu mainMenu, Options options)
	{
		this.mainMenu = mainMenu;
		this.options = options;
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		if(mainMenu.playMote.getMote() == null)
		{
			this.mainMenu.playMote.findMote();
			
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {

					while(WiiMoteButtonListener.this.mainMenu.playMote.playerMoteFinder == null)
						try { Thread.sleep(101); } catch (InterruptedException e1) { System.out.println("WiiMoteButtonListener Sleep Exception!"); }
				
					if(WiiMoteButtonListener.this.mainMenu.playMote.playerMoteFinder.exception)
					{
						WiiMoteButtonListener.this.mainMenu.refreshPlayerMote();
						return;
					}
					
					WiiMoteButtonListener.this.options.startFlashing();
					
					while(WiiMoteButtonListener.this.mainMenu.playMote.getMote() == null)
					{
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
					WiiMoteButtonListener.this.mainMenu.playMote.getMote().rumble(1000);
					
					WiiMoteButtonListener.this.options.stopFlashing();
				}
			};
			
			Thread thread = new Thread(runnable);
			thread.start();

		}
		else
		{
			
			this.mainMenu.playMote.disconnectMote();
			this.mainMenu.refreshPlayerMote();

		}
    }
}