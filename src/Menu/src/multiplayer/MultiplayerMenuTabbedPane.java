package Menu.src.multiplayer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import Menu.src.multiplayer.lobby.LobbyUtilsFactory;

public class MultiplayerMenuTabbedPane extends JTabbedPane {

	Image background;
	
	public MultiplayerMenuTabbedPane()
	{
		super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		super.setBorder(LobbyUtilsFactory.createPanelBorder());
		super.setPreferredSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 * 2, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3 * 2));
		
		this.background = new ImageIcon("src/Menu/data/multiplayer/lobby.jpg").getImage();
		
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}