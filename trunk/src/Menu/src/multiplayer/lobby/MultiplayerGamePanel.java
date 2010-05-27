package Menu.src.multiplayer.lobby;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		
		this.graphicLobby = graphicLobby;

		this.newGameName = new JTextField();
		this.newGamePortNumber = new JTextField();
		String []slots = {"2", "3", "4", "5", "6", "7", "8"};
		this.newGameSlotNumber = new JComboBox(slots);
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		
		this.createNewGame = new JButton("Crea una Partita");
		this.createNewGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				String gameName = MultiplayerGamePanel.this.newGameName.getText();
				if(gameName.trim().equals("")){ MultiplayerGamePanel.this.graphicLobby.showWarning("Non hai inserito un nome per la partita!"); return; }
				try{
					int numSlot = Integer.parseInt((String)MultiplayerGamePanel.this.newGameSlotNumber.getSelectedItem());
					int numPorta = Integer.parseInt(MultiplayerGamePanel.this.newGamePortNumber.getText());
					MultiplayerGamePanel.this.graphicLobby.lobbyClient.createGame(gameName, numSlot, numPorta);
				} catch (NumberFormatException exp) {
					MultiplayerGamePanel.this.graphicLobby.showWarning("Il Numero porta non è valido!"); return;
				}
				
			}
				
		});
		
		this.joinGame = new JButton("Unisciti alla Partita");
		this.joinGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				if(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow == -1) return;
				
				try{
					String gameName = (String)MultiplayerGamePanel.this.graphicLobby.gameListPanel.gameTable.getValueAt(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow, 0);
					String ip = (String)MultiplayerGamePanel.this.graphicLobby.gameListPanel.gameTable.getValueAt(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow, 2);
					int porta = (Integer)MultiplayerGamePanel.this.graphicLobby.gameListPanel.gameTable.getValueAt(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow, 3);
	
					MultiplayerGamePanel.this.graphicLobby.lobbyClient.tryJoiningGame(gameName, ip, porta);
				} catch(ArrayIndexOutOfBoundsException exc)
				{
					MultiplayerGamePanel.this.graphicLobby.showWarning("Non hai selezionato una partita!");
				}
			}
				
		});

		this.refreshLists = new JButton("Aggiorna Liste");
		this.refreshLists.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				MultiplayerGamePanel.this.graphicLobby.lobbyClient.requestListRefresh();																
			}
				
		});
		
		this.newGameName.setBorder(LobbyUtilsFactory.createTitledBorder("Nome Partita"));
		this.newGameName.setOpaque(false);
		this.newGameName.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		this.newGameName.setForeground(Color.BLACK);
		
		this.newGameSlotNumber.setBorder(LobbyUtilsFactory.createTitledBorder("Numero Giocatori"));
		this.newGameSlotNumber.setOpaque(false);
		this.newGameSlotNumber.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		this.newGameSlotNumber.setForeground(Color.BLACK);
		
		this.newGamePortNumber.setBorder(LobbyUtilsFactory.createTitledBorder("Numero Porta"));
		this.newGamePortNumber.setOpaque(false);
		this.newGamePortNumber.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		this.newGamePortNumber.setForeground(Color.BLACK);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
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
	
}
