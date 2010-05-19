package Menu.src.lobby;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import lobby.HostedGameInfo;
import lobby.LobbyClient;
import lobby.LobbyHostedGame;

public class Lobby extends JPanel {

	Image background;

	LobbyClient lobbyClient;
	
	JScrollPane playerList;
	JScrollPane gameList;
	JScrollPane chatAreaScrollPane;
	JPanel WriteChatAreaPanel;
	JPanel gamePanel;
	
	JTable gameTable;
	JTextArea chatArea;

	JTextField newGameName;
	JTextField newGamePortNumber;
	JComboBox newGameSlotNumber;
	JButton createNewGame;
	
	StyledDocument chat;
	Style playerChatMessageStyle;
	Style chatMessageStyle;
	
	public Lobby(LobbyClient lobbyClient) {
		
		this.lobbyClient = lobbyClient;
		this.lobbyClient.setGraphicLobby(this);
		this.background = new ImageIcon("src/Menu/data/sfida.jpg").getImage();
		
		super.setLayout(new BorderLayout());
		super.add(this.getTopPanel(), BorderLayout.NORTH);
		
		this.initChatAreaPanel();
		this.initWriteChatAreaPanel();
		this.initPlayerList();
		this.initGameListArePanel();
		this.initGamePanel();
		
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		
		JPanel rightPanel = new JPanel(new GridLayout(2, 1));
		rightPanel.add(this.gameList);
		rightPanel.add(this.gamePanel);
		
		centerPanel.add(this.chatAreaScrollPane);
		centerPanel.add(rightPanel);
		
		super.add(centerPanel, BorderLayout.CENTER);
		super.add(this.playerList, BorderLayout.EAST);
		super.add(this.WriteChatAreaPanel, BorderLayout.SOUTH);
		
		super.validate();
		super.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	public JPanel getTopPanel()
	{
		JPanel pane = new JPanel();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		int textWidth = (int) screenSize.getWidth() / 5 * 4;
		int textHeight = (int) screenSize.getHeight() / 5 * 1;
		
		pane.setPreferredSize(new Dimension(textWidth, textHeight));
		pane.setOpaque(false);

		JLabel titolo = new JLabel("WELCOME TO THE LOBBY!");
		
		return pane;
	}
	
	public void initPlayerList()
	{		
		List players = this.lobbyClient.getPlayers();
		
		this.playerList = new JScrollPane();

		if(players != null)
			this.refreshPlayerList();
		
	}
	
	public void initChatAreaPanel()
	{
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int chatWidth = (int) screenSize.getWidth() / 5 * 4;
		int chatHeight = (int) screenSize.getHeight() / 5 * 3;
	
		JTextPane editorPane = new JTextPane();
		editorPane.setBackground(Color.blue);
		editorPane.setEditable(false);
		
		this.chat = editorPane.getStyledDocument();
		
	    Style def = StyleContext.getDefaultStyleContext().getStyle( StyleContext.DEFAULT_STYLE );
	    Style welcomeMessage = this.chat.addStyle( "welcome", def );
	    StyleConstants.setSuperscript( welcomeMessage, true );
	    StyleConstants.setFontSize( welcomeMessage, 35 );
	    StyleConstants.setBold( welcomeMessage, true );
		try {
			this.chat.insertString( 0, "Benvenuto su Magic Duels Online!!\n", welcomeMessage );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.chatAreaScrollPane = new JScrollPane(editorPane);
		
		//this.chatAreaScrollPane.setAutoscrolls(true);
		//this.chatAreaScrollPane.setPreferredSize(new Dimension(chatWidth, chatHeight));
		
	}
	
	public void initGameListArePanel()
	{
		List<HostedGameInfo> gameList = this.lobbyClient.getHostedGameList();

		if(gameList != null)
			this.refreshGameListArePanel();
		else
		{
			Vector<String> header = new Vector<String>();
			
			header.add("Nome Partita");
			header.add("Nome Host");
			header.add("Ip Host");
					
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(header);
			
			this.gameTable = new JTable(model);
			
			this.gameList = new JScrollPane(this.gameTable);
		}
	}
	
	public void initGamePanel()
	{
		this.newGameName = new JTextField();
		this.newGamePortNumber = new JTextField();
		
		String []slots = {"1", "2", "3", "4", "5", "6", "7", "8"};
		this.newGameSlotNumber = new JComboBox(slots);
		
		JPanel panel = new JPanel(new GridLayout(4, 4, 50, 50));

		this.createNewGame = new JButton("Crea una Partita");
		this.createNewGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				String gameName = Lobby.this.newGameName.getText();
				int numSlot = Integer.parseInt((String)Lobby.this.newGameSlotNumber.getSelectedItem());
				int numPorta = Integer.parseInt(Lobby.this.newGamePortNumber.getText());
				Lobby.this.lobbyClient.createGame(gameName, numSlot, numPorta);
			}
				
			});
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(20, 20, 10, 20);
		c.gridwidth = 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		
		panel.add(new JLabel("Nome Partita:"));
		panel.add(this.newGameName);
		panel.add(new JLabel("Numero Slots"));
		panel.add(this.newGameSlotNumber);
		panel.add(new JLabel("Numero Porta"));
		panel.add(this.newGamePortNumber);
		panel.add(this.createNewGame, c);
		
		this.gamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		this.gamePanel.add(panel);
		//this.gamePanel.add(this.createGame);
	}
	
	public void initHostedGamePanel()
	{
		this.gamePanel.removeAll();
		this.gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		LobbyHostedGame hostedGame = this.lobbyClient.getHostedGame();

		JPanel panel = new JPanel(new GridLayout(hostedGame.getNumSlots(), 2));

		SlotComboBox slot;
		int giocatore;
		ArrayList<SlotComboBox> slots = new ArrayList<SlotComboBox>();
		for (int i = 0; i < hostedGame.getNumSlots(); i++) {
			giocatore = i + 1;
			slot = new SlotComboBox(i);
			slots.add(slot);
			panel.add(new JLabel("Giocatore " + giocatore));
			panel.add(slot);
		}
		
		this.gamePanel.add(panel);
		this.gamePanel.repaint();
		this.gamePanel.revalidate();
		
	}
	
	public void initWriteChatAreaPanel()
	{
		this.WriteChatAreaPanel = new JPanel();
		
		/*
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int textWidth = (int) screenSize.getWidth() / 5 * 4;
		int textHeight = (int) screenSize.getHeight() / 5 * 1;
		this.WriteChatAreaPanel.setPreferredSize(new Dimension(textWidth, textHeight));
		*/
		
		this.WriteChatAreaPanel.setBackground(Color.red);
		
		this.chatArea = new JTextArea();
		this.chatArea.setBackground(Color.red);
		this.chatArea.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.chatArea.setPreferredSize(new Dimension(this.WriteChatAreaPanel.getPreferredSize()));
		this.chatArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {}		
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(!Lobby.this.chatArea.getText().trim().equals(""))
						Lobby.this.lobbyClient.sendChatMessage(Lobby.this.chatArea.getText());
					
					Lobby.this.chatArea.setText(null);		
				}
			}
		});
		
		this.initChat();
		
		this.WriteChatAreaPanel.add(this.chatArea);
	
	}
	
	public void writeChatMessage(String player, String msg)
	{   
		try {
						
			this.chat.insertString( this.chat.getLength(), player + " dice: ", this.playerChatMessageStyle );
			this.chat.insertString( this.chat.getLength(), msg.trim() + "\n", this.chatMessageStyle );
			
			JScrollBar scrollBar = this.chatAreaScrollPane.getVerticalScrollBar();
			scrollBar.setValue(scrollBar.getMaximum());

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void initChat()
	{
		Style def = StyleContext.getDefaultStyleContext().getStyle( StyleContext.DEFAULT_STYLE );
		
	    this.chatMessageStyle = this.chat.addStyle( "chatMessage", def );
	    StyleConstants.setSuperscript( this.chatMessageStyle, true );
	    StyleConstants.setFontSize( this.chatMessageStyle, 25 );
	    StyleConstants.setBold( this.chatMessageStyle, false );
	    
	    this.playerChatMessageStyle = this.chat.addStyle( "playerChatMessageStyle", def );
	    StyleConstants.setSuperscript( this.playerChatMessageStyle, true );
	    StyleConstants.setFontSize( this.playerChatMessageStyle, 25 );
	    StyleConstants.setBold( this.playerChatMessageStyle, true );
	}
	
	public void refreshPlayerList()
	{
		DefaultListModel model = new DefaultListModel();

		List players = this.lobbyClient.getPlayers();
		
		if(players != null)
			for (Iterator iterator = players.iterator(); iterator.hasNext();)
				model.addElement((String)iterator.next());
		
		JList list = new JList(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setBackground(Color.green);
		
		this.playerList.removeAll();
		this.playerList = new JScrollPane(list);
		//this.playerList.setViewportView(list);
		//this.playerList.add(list);
		this.playerList.setPreferredSize(new Dimension(100, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		
		this.repaint();
		this.revalidate();
	}
	
	public void refreshGameListArePanel()
	{
		
		Vector<String> header = new Vector<String>();
		
		header.add("Nome Partita");
		header.add("Nome Host");
		header.add("Ip Host");
				
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(header);
		
		List<HostedGameInfo> gameList = this.lobbyClient.getHostedGameList();
			
		if(gameList != null)
			for (Iterator iterator = gameList.iterator(); iterator.hasNext();)
			{
				HostedGameInfo info = (HostedGameInfo) iterator.next();
				model.addRow(new Object[]{info.getGameName(), info.getHostName(), ""});
			}
		
		this.gameTable = new JTable(model);
		
		if(this.gameList != null)
			this.gameList.removeAll();
		this.gameList = new JScrollPane(this.gameTable);

		this.repaint();
		this.revalidate();
	}
	
}