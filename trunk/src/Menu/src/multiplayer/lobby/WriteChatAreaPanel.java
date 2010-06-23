package Menu.src.multiplayer.lobby;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WriteChatAreaPanel extends JPanel {

	Lobby graphicLobby;
	
	JTextField chatArea;
	JButton back;
	
	public WriteChatAreaPanel(Lobby lobby)
	{
		super(new GridLayout(1, 1));
		super.setOpaque(false);
		super.setPreferredSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 150));		
		super.setBorder(LobbyUtilsFactory.createPanelBorder());
		
		this.graphicLobby = lobby;
		this.initWriteChatAreaPanel();
	}
	
	/**
	 * Initializes the panel in which to write chat message
	 */
	public void initWriteChatAreaPanel()
	{	
		this.chatArea = new JTextField();
		this.chatArea.setBorder(null);
		this.chatArea.setOpaque(false);
		
		this.chatArea.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		this.chatArea.setForeground(Color.BLACK);
		
		this.chatArea.setPreferredSize(new Dimension((int)super.getPreferredSize().getWidth()-300, 100));

		this.chatArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {}		
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent evt)
			{
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(!WriteChatAreaPanel.this.chatArea.getText().trim().equals(""))
						WriteChatAreaPanel.this.graphicLobby.lobbyClient.sendChatMessage(WriteChatAreaPanel.this.chatArea.getText());
					
					WriteChatAreaPanel.this.chatArea.setText(null);
					WriteChatAreaPanel.this.chatArea.requestFocus();
				}
			}
		});

		this.back = LobbyUtilsFactory.createAnimatedButton("src/Menu/data/multiplayer/back1.gif", "src/Menu/data/multiplayer/back2.gif");
		this.back.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e)
			{				
				WriteChatAreaPanel.this.graphicLobby.lobbyClient.sendByeMessage();
				WriteChatAreaPanel.this.graphicLobby.mainMenu.ok.play();			
			}
		});
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension((int)super.getPreferredSize().getWidth(), 150));
		//panel.setBorder(BorderFactory.createEmptyBorder(30, 5, 20, 5));
		panel.add(this.chatArea);
		panel.add(this.back);
		
		super.add(panel);
		
		super.repaint();
		super.revalidate();
	}
	
}