package Menu.src.multiplayer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Menu.src.MainMenu;

/**
 * 
 * @author Neb
 *
 */
public class DirectConnectionServerTab extends MultiplayerMenuTabs {

	JTextField numPlayers;
	JTextField porta;
	
	JButton create;
	
	public DirectConnectionServerTab(MainMenu mainMenu) {
		super(mainMenu);
		
		this.numPlayers = new JTextField();
		this.numPlayers.setOpaque(false);
		this.porta = new JTextField();
		this.porta.setOpaque(false);
		
		this.create = new JButton("Crea Partita");
		
		this.display();
	}

	public void display()
	{
		super.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel(new GridLayout(2, 2));
		
		centerPanel.add(new JLabel("Numero Giocatori:"));
		centerPanel.add(this.numPlayers);
		centerPanel.add(new JLabel("Porta"));
		centerPanel.add(this.porta);
		
		this.create.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

			}
				
			});	
		
		super.add(centerPanel, BorderLayout.CENTER);
		super.add(this.create, BorderLayout.SOUTH);
		
		super.setOpaque(false);
		super.repaint();
		super.revalidate();
		
	}
	
	
	@Override
	public void checkPanel() {}

}
