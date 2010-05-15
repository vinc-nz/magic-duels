package Menu.src;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class WiiMoteIcon extends Thread implements Icon {

	Options options;
	Image unConnectedMote;
	Image connectedMote;
	
	Thread flashingThread;
	
	public WiiMoteIcon(Options options) {
		
		this.options = options;
		
		this.unConnectedMote = new ImageIcon("src/Menu/data/unConnectedMote.jpg").getImage();
		this.connectedMote = new ImageIcon("src/Menu/data/connectedMote.jpg").getImage();
		
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		
		if(!this.options.wiiMoteFlashing)
			if(this.options.mainMenu.playMote.getMote() == null)
			{
				g.drawImage(this.unConnectedMote, 0,0, this.unConnectedMote.getWidth(null), this.unConnectedMote.getHeight(null), null);
				System.out.println("IMG 2");
			}
			else
			{
				g.drawImage(this.connectedMote, 0,0, this.connectedMote.getWidth(null), this.connectedMote.getHeight(null), null);
				System.out.println("IMG 3");
			}
	}
	
	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
