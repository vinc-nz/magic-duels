package Menu.src;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainMulti extends JPanel {

	MainMenu mainMenu;
	
	Image background;
	
	public MainMulti(MainMenu mainMenu) {
		
		this.mainMenu = mainMenu;
		
		this.background = new ImageIcon("src/Menu/data/sfida.jpg").getImage();
		
		JTabbedPane jt = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		jt.addTab("Login to the Lobby", new LobbyLogin(this.mainMenu));
		jt.addTab("New Lobby Account", new LobbyLogin(this.mainMenu));
		jt.addTab("Direct Connection", new LobbyLogin(this.mainMenu));
		
		jt.setSize(400, 400);
		super.add(jt);
		super.setSize(400, 400);

		setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
}
