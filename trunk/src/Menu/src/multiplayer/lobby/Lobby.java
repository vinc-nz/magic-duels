package Menu.src.multiplayer.lobby;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lobby.LobbyClient;
import Menu.src.MainMenu;

public class Lobby extends JPanel {

	Image background;

	public MainMenu mainMenu;
	LobbyClient lobbyClient;

	TopPanel topPanel;
	
	PlayerListPanel playerListPanel;
	GameListPanel gameListPanel;
	ChatAreaPanel chatAreaPanel;
	WriteChatAreaPanel writeChatAreaPanel;
	
	public JPanel centerPanel;
	public JPanel rightPanel;
	public JPanel multiplayerGame;
	
	public Lobby(MainMenu mainMenu) {
		
		this.mainMenu = mainMenu;
		this.lobbyClient = mainMenu.lobbyClient;
		
		this.lobbyClient.setGraphicLobby(this);
		this.background = new ImageIcon("src/Menu/data/multiplayer/lobby.jpg").getImage();
		
		this.topPanel = new TopPanel();
		
		super.setLayout(new BorderLayout());
		super.add(this.topPanel, BorderLayout.NORTH);
		
		this.playerListPanel = new PlayerListPanel(this);
		this.gameListPanel = new GameListPanel(this);
				
		this.chatAreaPanel = new ChatAreaPanel();
		this.writeChatAreaPanel = new WriteChatAreaPanel(this);
		
		this.multiplayerGame = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.multiplayerGame.setOpaque(false);
		
		this.multiplayerGame.add(new MultiplayerGamePanel(this));
		
		this.centerPanel = new JPanel(new GridLayout(1, 2));
		this.centerPanel.setOpaque(false);
		
		this.rightPanel = new JPanel(new GridLayout(2, 1));
		
		this.rightPanel.setBorder(LobbyUtilsFactory.createPanelBorder());
		this.rightPanel.setOpaque(false);
	
		this.rightPanel.add(this.gameListPanel);
		this.rightPanel.add(this.multiplayerGame);
		
		this.centerPanel.add(this.chatAreaPanel);
		this.centerPanel.add(this.rightPanel);
		super.add(this.centerPanel, BorderLayout.CENTER);
		super.add(this.playerListPanel, BorderLayout.EAST);
		super.add(this.writeChatAreaPanel, BorderLayout.SOUTH);
		
		super.validate();
		super.setVisible(true);
				
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	public void refreshPlayerListPanel() { this.playerListPanel.refreshPlayerList(); }
	public void refreshGameListPanel() { this.gameListPanel.refreshGameListArePanel(); }
	
	public void multiplayerGame()
	{
		this.rightPanel.removeAll();
		this.rightPanel.setLayout(new GridLayout(2, 2));		
		this.rightPanel.add(this.gameListPanel);
		this.rightPanel.add(this.multiplayerGame);
		this.rightPanel.repaint();
		this.rightPanel.revalidate();
	}
	
	public void hostAGame() 
	{ 
		this.rightPanel.removeAll();
		this.rightPanel.setLayout(new GridLayout(1, 1));
		this.rightPanel.add(new HostedGamePanel(this));
		this.rightPanel.repaint();
		this.rightPanel.revalidate();
		/*
		this.multiplayerGame.removeAll();
		this.multiplayerGame.setLayout(new FlowLayout());
		this.multiplayerGame.add(new HostedGamePanel(this));
		this.multiplayerGame.repaint();
		this.multiplayerGame.revalidate();
		*/
	}

	public void joinAGame()
	{
		this.rightPanel.removeAll();
		this.rightPanel.setLayout(new GridLayout(1, 1));
		this.rightPanel.add(new JoinedGamePanel(this));
		this.rightPanel.repaint();
		this.rightPanel.revalidate();
		
		/*
		this.multiplayerGame.removeAll();
		this.multiplayerGame.setLayout(new FlowLayout());
		this.multiplayerGame.add(new JoinedGamePanel(this));
		this.multiplayerGame.repaint();
		this.multiplayerGame.revalidate();*/
	}
	
	public void writeChatMessage(String player, String msg) { this.chatAreaPanel.writeChatMessage(player, msg); }
	
    public void showWarning(String warning) { JOptionPane.showMessageDialog(mainMenu, warning, "Magic Duels Game", JOptionPane.WARNING_MESSAGE); }
	
}