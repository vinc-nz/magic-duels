package Menu.src.multiplayer;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Menu.src.MainMenu;

public class MultiplayerMenu extends JPanel {

	MainMenu mainMenu;
	
	JTabbedPane tabbedPane;
	Image background;
	
	LogingTab logingTab;
	SigningTab signingTab;
	DirectConnectionServerTab directConnectionServerTab;
	
	public static String LOGIN = "Login to the Lobby";
	public static String NEWACCOUNT = "New Lobby Account";
	public static String DIRECTCONNECTION = "Direct Connection";
	
	public MultiplayerMenu(final MainMenu mainMenu) {
		
		super.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.mainMenu = mainMenu;
		
		this.background = new ImageIcon("src/Menu/data/sfida.jpg").getImage();
		
		this.logingTab = new LogingTab(this.mainMenu);
		this.signingTab = new SigningTab(this.mainMenu);
		this.directConnectionServerTab = new DirectConnectionServerTab(this.mainMenu);
		
		this.tabbedPane = new MultiplayerMenuTabbedPane();
		this.tabbedPane.addTab(MultiplayerMenu.LOGIN, this.logingTab);
		this.tabbedPane.addTab(MultiplayerMenu.NEWACCOUNT, this.signingTab);
		this.tabbedPane.addTab(MultiplayerMenu.DIRECTCONNECTION, this.directConnectionServerTab);
				
		this.tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				mainMenu.ok.play();
			    JTabbedPane tabSource = (JTabbedPane) e.getSource();
			    String tab = tabSource.getTitleAt(tabSource.getSelectedIndex());
			    if(tab.equals(MultiplayerMenu.LOGIN))
			    	MultiplayerMenu.this.logingTab.checkPanel();
			    else if(tab.equals(MultiplayerMenu.NEWACCOUNT))
			    	MultiplayerMenu.this.signingTab.checkPanel();
			
			}
		});
		
		JButton buttonBack = new JButton("BACK");
		this.add(buttonBack);
		
		
		// cancel return to main panel
		buttonBack.addActionListener(
		    new ActionListener() {
				@Override
		        public void actionPerformed(ActionEvent e) {
					mainMenu.ok.play();
		            mainMenu.switchTo(mainMenu.panel);	
		        }
		    }
		);
		
		super.add(this.tabbedPane);

		super.setOpaque(false);
		super.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
}
