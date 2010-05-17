package Menu.src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import lobby.LobbyClient;

public class Lobby extends JPanel {

	Image background;

	LobbyClient lobbyClient;
	
	JTextArea chatArea;
	StyledDocument chat;
	Style playerChatMessageStyle;
	Style chatMessageStyle;
	
	public Lobby(LobbyClient lobbyClient) {
		
		this.lobbyClient = lobbyClient;
		this.lobbyClient.setGraphicLobby(this);
		this.background = new ImageIcon("src/Menu/data/sfida.jpg").getImage();
		
		super.setLayout(new BorderLayout());
		super.add(this.getTopPanel(), BorderLayout.NORTH);
		super.add(this.getChatAreaPanel(), BorderLayout.CENTER);
		super.add(this.getPlayerList(), BorderLayout.EAST);
		super.add(this.getWriteChatAreaPanel(), BorderLayout.SOUTH);
		
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
	
	public JScrollPane getPlayerList()
	{
		DefaultListModel model = new DefaultListModel();

		List players = this.lobbyClient.getPlayers();
		
		for (Iterator iterator = players.iterator(); iterator.hasNext();)
			model.addElement((String)iterator.next());
		
		JList list = new JList(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setBackground(Color.green);
		
		JScrollPane scrollPane = new JScrollPane(list);
		
		scrollPane.setPreferredSize(new Dimension(100, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		
		return scrollPane;
		
	}
	
	public JScrollPane getChatAreaPanel()
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
		
		JScrollPane scrollPane = new JScrollPane(editorPane);
		
		scrollPane.setPreferredSize(new Dimension(chatWidth, chatHeight));
		
		return scrollPane; 
	}
	
	public JPanel getWriteChatAreaPanel()
	{
		JPanel pane = new JPanel();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		int textWidth = (int) screenSize.getWidth() / 5 * 4;
		int textHeight = (int) screenSize.getHeight() / 5 * 1;
		
		pane.setPreferredSize(new Dimension(textWidth, textHeight));
		pane.setBackground(Color.red);
		
		this.chatArea = new JTextArea();
		this.chatArea.setBackground(Color.red);
		this.chatArea.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.chatArea.setPreferredSize(new Dimension(pane.getPreferredSize()));
		this.chatArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {}		
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					Lobby.this.lobbyClient.sendChatMessage(Lobby.this.chatArea.getText());
					Lobby.this.chatArea.setText(null);
				}
			}
		});
		this.initChat();
		
		pane.add(this.chatArea);
	
		return pane;
	}
	
	public void writeChatMessage(String player, String msg)
	{   
		try {
						
			this.chat.insertString( this.chat.getLength(), player + " dice: ", this.playerChatMessageStyle );
			this.chat.insertString( this.chat.getLength(), msg + "\n", this.chatMessageStyle );
			
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
	
}