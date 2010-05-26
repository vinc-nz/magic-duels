package Menu.src.multiplayer.lobby;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


public class ChatAreaPanel extends JScrollPane {

	StyledDocument chat;
	Style playerChatMessageStyle;
	Style chatMessageStyle;
	
	public ChatAreaPanel() {
	
		super();
		super.setLayout(new ScrollPaneLayout());
		super.setOpaque(false);
		super.getViewport().setOpaque(false);
		super.setPreferredSize(new Dimension(400, 400));
		super.setBorder(LobbyUtilsFactory.createPanelBorder());

		this.initChatAreaPanel();
		
		super.repaint();
		super.revalidate();
		
	}
	
	public void initChatAreaPanel()
	{
		
		JTextPane editorPane = new JTextPane();
		editorPane.setEditable(false);
		editorPane.setPreferredSize(new Dimension(400, 400));
		editorPane.setOpaque(false);
		editorPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 30, 10));
		
		this.chat = editorPane.getStyledDocument();
		
	    Style def = StyleContext.getDefaultStyleContext().getStyle( StyleContext.DEFAULT_STYLE );
	    Style welcomeMessage = this.chat.addStyle( "welcome", def );
	    StyleConstants.setSuperscript( welcomeMessage, true );
	    StyleConstants.setForeground(welcomeMessage, Color.black);
	    StyleConstants.setFontSize( welcomeMessage, 35 );
	    StyleConstants.setBold( welcomeMessage, true );
		try {
			this.chat.insertString( 0, "Benvenuto su Magic Duels Online!!\n", welcomeMessage );
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		//super.setLayout(new FlowLayout());
		super.getViewport().add(editorPane);
		
		//this.chatAreaScrollPane.setAutoscrolls(true);
		//this.chatAreaScrollPane.setPreferredSize(new Dimension(chatWidth, chatHeight));
		
		this.initChat();
		
	}
	
	public void writeChatMessage(String player, String msg)
	{   
		try {
						
			this.chat.insertString( this.chat.getLength(), player + " dice: ", this.playerChatMessageStyle );
			this.chat.insertString( this.chat.getLength(), msg.trim() + "\n", this.chatMessageStyle );
			
			JScrollBar scrollBar = super.getVerticalScrollBar();
			scrollBar.setValue(scrollBar.getMaximum());
			scrollBar.revalidate();
			scrollBar.repaint();

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void initChat()
	{
		Style def = StyleContext.getDefaultStyleContext().getStyle( StyleContext.DEFAULT_STYLE );
		
	    this.chatMessageStyle = this.chat.addStyle( "chatMessage", def );
	    StyleConstants.setSuperscript( this.chatMessageStyle, true );
	    StyleConstants.setFontFamily(this.chatMessageStyle, "Comic Sans MS");
	    StyleConstants.setFontSize( this.chatMessageStyle, 30 );
	    StyleConstants.setForeground(this.chatMessageStyle, Color.black);
	    StyleConstants.setBold( this.chatMessageStyle, false );
	    
	    this.playerChatMessageStyle = this.chat.addStyle( "playerChatMessageStyle", def );
	    StyleConstants.setSuperscript( this.playerChatMessageStyle, true );
	    StyleConstants.setFontFamily(this.playerChatMessageStyle, "Comic Sans MS");
	    StyleConstants.setFontSize( this.playerChatMessageStyle, 30 );
	    StyleConstants.setForeground(this.playerChatMessageStyle, Color.black);
	    StyleConstants.setBold( this.playerChatMessageStyle, true );
	}
	
	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
	}
}
