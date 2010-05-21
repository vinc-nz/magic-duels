package Menu.src.lobby;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneLayout;
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
		super.setBackground(Color.blue);
		super.setPreferredSize(new Dimension(400, 400));
		this.initChatAreaPanel();
		
		super.repaint();
		super.revalidate();
		
	}
	
	public void initChatAreaPanel()
	{
		/*
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int chatWidth = (int) screenSize.getWidth() / 5 * 4;
		int chatHeight = (int) screenSize.getHeight() / 5 * 3;
		 */
		
		JTextPane editorPane = new JTextPane();
		editorPane.setBackground(Color.blue);
		editorPane.setEditable(false);
		editorPane.setPreferredSize(new Dimension(400, 400));
		editorPane.setVisible(true);
		
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
		
		//super.setLayout(new FlowLayout());
		super.setLayout(new ScrollPaneLayout());
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
