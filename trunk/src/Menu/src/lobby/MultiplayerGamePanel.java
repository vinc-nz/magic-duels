package Menu.src.lobby;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class MultiplayerGamePanel extends JPanel{
	
	Lobby graphicLobby;
	
	JTextField newGameName;
	JTextField newGamePortNumber;
	JComboBox newGameSlotNumber;
	
	JButton createNewGame;
	JButton joinGame;
	JButton refreshLists;
	
	public MultiplayerGamePanel(Lobby graphicLobby) {
		super(new FlowLayout(FlowLayout.CENTER));
		super.setOpaque(false);
		super.setBorder(BorderFactory.createEmptyBorder(30, 5, 5, 5));
		
		this.graphicLobby = graphicLobby;

		this.newGameName = new JTextField();
		this.newGamePortNumber = new JTextField();
		this.newGameName.setDo
		String []slots = {"1", "2", "3", "4", "5", "6", "7", "8"};
		this.newGameSlotNumber = new JComboBox(slots);
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		
		this.createNewGame = new JButton("Crea una Partita");
		this.createNewGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				String gameName = MultiplayerGamePanel.this.newGameName.getText();
				int numSlot = Integer.parseInt((String)MultiplayerGamePanel.this.newGameSlotNumber.getSelectedItem());
				int numPorta = Integer.parseInt(MultiplayerGamePanel.this.newGamePortNumber.getText());
				MultiplayerGamePanel.this.graphicLobby.lobbyClient.createGame(gameName, numSlot, numPorta);
			}
				
		});
		
		this.joinGame = new JButton("Unisciti alla Partita");
		this.joinGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				if(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow == -1) return;
				
				String gameName = (String)MultiplayerGamePanel.this.graphicLobby.gameListPanel.gameTable.getValueAt(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow, 0);
				String ip = (String)MultiplayerGamePanel.this.graphicLobby.gameListPanel.gameTable.getValueAt(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow, 2);
				int porta = (Integer)MultiplayerGamePanel.this.graphicLobby.gameListPanel.gameTable.getValueAt(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow, 3);
				
				MultiplayerGamePanel.this.graphicLobby.lobbyClient.tryJoiningGame(gameName, ip, porta);
			}
				
		});

		this.refreshLists = new JButton("Aggiorna Liste");
		this.refreshLists.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

			}
				
		});
		
		this.newGameName.setBorder(MultiplayerGamePanel.createTitledBorder("Nome Partita"));
		this.newGameName.setOpaque(false);
		
		this.newGameSlotNumber.setBorder(MultiplayerGamePanel.createTitledBorder("Numero Giocatori"));
		this.newGameSlotNumber.setOpaque(false);
		
		this.newGamePortNumber.setBorder(MultiplayerGamePanel.createTitledBorder("Numero Porta"));
		this.newGamePortNumber.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		c.ipadx = 5;
		c.ipady = 5;
		
		c.gridx = 0;
		c.gridy = 0;
		panel.add(this.createNewGame, c);
		
		c.gridx = 1;
		c.gridy = 0;		
		panel.add(this.joinGame, c);
		
		c.gridx = 2;
		c.gridy = 0;		
		panel.add(this.refreshLists, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		panel.add(this.newGameName, c);
		
		c.gridx = 0;
		c.gridy = 2;
		panel.add(this.newGameSlotNumber, c);
		
		c.gridx = 0;
		c.gridy = 3;
		panel.add(this.newGamePortNumber, c);
				
		super.add(panel);
	}
	
	public static Border createTitledBorder(String title)
	{
		Border border = BorderFactory.createLineBorder(Color.blue, 2);
		TitledBorder borderTitle = BorderFactory.createTitledBorder(border, title);
		borderTitle.setTitleColor(Color.black);
		borderTitle.setTitleFont( new Font("Arial", Font.BOLD, 18));
		
		return borderTitle;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		Image img = new ImageIcon("src/Menu/data/lala3.gif").getImage(); 
		
		g.drawImage(img , 0, 0, img.getWidth(null), img.getHeight(null), null);
	}
	
}
