package Menu.src.multiplayer;

import java.awt.Dimension;
import java.awt.GridBagLayout;

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
	
	public MultiplayerMenuTabs(MainMenu mainMenu)
	{
		super.setOpaque(false);
		super.setLayout(new GridBagLayout());
		super.setBorder(LobbyUtilsFactory.createPanelBorder());

		this.mainMenu = mainMenu;
		
		this.serverIp = new JTextField("127.0.0.1");
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.serverIp, "IP Server:");
			
		//System.out.println("SIZE " + (int)this.mainMenu.panel.mouseAdapter.multiplayerMenu.getSize().getWidth());
		
		this.serverPort = new JTextField("7000");
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.serverPort, "Porta Server:");
		
		this.connect = new JButton("Connettiti al Server!");
		
		this.checkPanel();		
	}
	
	public void showWarning(String warning) { JOptionPane.showMessageDialog(mainMenu, warning, "Magic Duels Game", JOptionPane.WARNING_MESSAGE); }
	
	public abstract void checkPanel();

}