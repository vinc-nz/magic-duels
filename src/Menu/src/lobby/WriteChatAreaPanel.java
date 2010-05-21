package Menu.src.lobby;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class WriteChatAreaPanel extends JPanel {

	Lobby graphicLobby;
	
	JTextArea chatArea;
	
	public WriteChatAreaPanel(Lobby lobby) {

		super();
		super.setBackground(Color.red);
		super.setPreferredSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 150));
		
		this.graphicLobby = lobby;
		this.initWriteChatAreaPanel();
	
	}
	
	
	public void initWriteChatAreaPanel()
	{
		
		this.chatArea = new JTextArea();
		this.chatArea.setBackground(Color.red);
		this.chatArea.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.chatArea.setPreferredSize(new Dimension(super.getPreferredSize()));
		
		this.chatArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {}		
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(!WriteChatAreaPanel.this.chatArea.getText().trim().equals(""))
						WriteChatAreaPanel.this.graphicLobby.lobbyClient.sendChatMessage(WriteChatAreaPanel.this.chatArea.getText());
					
					WriteChatAreaPanel.this.chatArea.setText(null);		
				}
			}
		});
				
		super.add(this.chatArea);
	
		super.repaint();
		super.revalidate();
	}
	
}
