package Menu.src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WiiMoteButton implements ActionListener {

	MainMenu mainMenu;
	Options options;
	
	public WiiMoteButton(MainMenu mainMenu, Options options) {

		this.mainMenu = mainMenu;
		this.options = options;
		
	}

	@Override
    public void actionPerformed(ActionEvent e) {
		if(mainMenu.playMote.getMote() == null)
		{
			this.mainMenu.playMote.findMote();
			this.mainMenu.playMote.getMote().rumble(1000);
			this.options.wiiSelected = true;
			//this.options.repaint();
		}
		else
		{
			this.mainMenu.playMote.disconnectMote();
			this.options.wiiSelected = false;
			//this.options.repaint();

		}
    }
}