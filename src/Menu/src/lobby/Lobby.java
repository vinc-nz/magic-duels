package Menu.src.lobby;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import lobby.LobbyClient;
import Menu.src.MainMenu;

public class Lobby extends JPanel {

	Image background;

	public MainMenu mainMenu;
	LobbyClient lobbyClient;
	
	PlayerListPanel playerListPanel;
	GameListPanel gameListPanel;
	ChatAreaPanel chatAreaPanel;
	WriteChatAreaPanel writeChatAreaPanel;
	
	JPanel multiplayerGame;
	
	public Lobby(MainMenu mainMenu) {
		
		this.mainMenu = mainMenu;
		this.lobbyClient = mainMenu.lobbyClient;
		
		this.lobbyClient.setGraphicLobby(this);
		this.background = new ImageIcon("src/Menu/data/lala.jpg").getImage();
		
		super.setLayout(new BorderLayout());
		super.add(this.getTopPanel(), BorderLayout.NORTH);
		
		this.playerListPanel = new PlayerListPanel(this);
		this.gameListPanel = new GameListPanel(this);
		
		this.chatAreaPanel = new ChatAreaPanel();
		this.writeChatAreaPanel = new WriteChatAreaPanel(this);
		
		this.multiplayerGame = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.multiplayerGame.setOpaque(false);
		
		this.multiplayerGame.add(new MultiplayerGamePanel(this));
		
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		centerPanel.setOpaque(false);
		
		JPanel rightPanel = new JPanel(new GridLayout(2, 1));
		
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		
		rightPanel.setBorder(compound);
		rightPanel.setOpaque(false);
	
		rightPanel.add(this.gameListPanel);
		rightPanel.add(this.multiplayerGame);
		
		centerPanel.add(this.chatAreaPanel);
		centerPanel.add(rightPanel);
		super.add(centerPanel, BorderLayout.CENTER);
		super.add(this.playerListPanel, BorderLayout.EAST);
		super.add(this.writeChatAreaPanel, BorderLayout.SOUTH);
		
		super.validate();
		super.setVisible(true);
				
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	public JPanel getTopPanel()
	{
		JPanel pane = new JPanel(){
			public void paintComponent(Graphics g) {
				g.drawImage( new ImageIcon("src/Menu/data/lala2.gif").getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
			}			
		};
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int textWidth = (int) screenSize.getWidth();
		int textHeight = (int) screenSize.getHeight() / 6 * 1;
		
		pane.setPreferredSize(new Dimension(textWidth, textHeight));
		pane.setOpaque(false);

		JLabel titolo = new JLabel("WELCOME TO THE LOBBY!");		
		
		return pane;
	}
	
	public void refreshGameListPanel() { this.gameListPanel.refreshGameListArePanel(); }
	
	public void multiplayerGame()
	{
		this.multiplayerGame.removeAll();
		this.multiplayerGame.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.multiplayerGame.add(new MultiplayerGamePanel(this));
		this.multiplayerGame.setOpaque(false);
		this.multiplayerGame.repaint();
		this.multiplayerGame.revalidate();		
	}
	
	public void hostAGame() 
	{ 
		this.multiplayerGame.removeAll();
		this.multiplayerGame.setLayout(new FlowLayout());
		this.multiplayerGame.add(new HostedGamePanel(this));
		this.multiplayerGame.repaint();
		this.multiplayerGame.revalidate();
	}

	public void joinAGame()
	{
		this.multiplayerGame.removeAll();
		this.multiplayerGame.setLayout(new FlowLayout());
		this.multiplayerGame.add(new JoinedGamePanel(this));
		this.multiplayerGame.repaint();
		this.multiplayerGame.revalidate();
	}
	
	public void writeChatMessage(String player, String msg) { this.chatAreaPanel.writeChatMessage(player, msg); }
	
    public void showWarning(String warning) { JOptionPane.showMessageDialog(mainMenu, warning, "Magic Duels Game", JOptionPane.WARNING_MESSAGE); }
	
}