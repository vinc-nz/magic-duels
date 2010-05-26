package Menu.src.multiplayer;

import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Menu.src.MainMenu;
import Menu.src.multiplayer.lobby.LobbyUtilsFactory;

public abstract class MultiplayerMenuTabs extends JPanel{

	MainMenu mainMenu;
	
	JTextField serverIp;
	JTextField serverPort;
	
	JButton connect;
	JButton back;

	public MultiplayerMenuTabs(MainMenu mainMenu)
	{
		super.setOpaque(false);
		super.setLayout(new GridBagLayout());
		super.setBorder(LobbyUtilsFactory.createPanelBorder());

		this.mainMenu = mainMenu;
		
		this.serverIp = new JTextField("127.0.0.1");
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.serverIp, "IP Server:");
		
		this.serverPort = new JTextField("7000");
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.serverPort, "Porta Server:");
		
		this.connect = LobbyUtilsFactory.createAnimatedButton("src/Menu/data/multiplayer/connect1.gif", "src/Menu/data/multiplayer/connect2.gif");
		this.back = LobbyUtilsFactory.createAnimatedButton("src/Menu/data/multiplayer/back1.gif", "src/Menu/data/multiplayer/back2.gif");
		this.back.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				MultiplayerMenuTabs.this.mainMenu.ok.play();
				MultiplayerMenuTabs.this.mainMenu.switchTo(MultiplayerMenuTabs.this.mainMenu.panel);
			
			}
		});
		
		
		this.checkPanel();		
	}
	
	public void showWarning(String warning) { JOptionPane.showMessageDialog(mainMenu, warning, "Magic Duels Game", JOptionPane.WARNING_MESSAGE); }
	
	public abstract void checkPanel();

}