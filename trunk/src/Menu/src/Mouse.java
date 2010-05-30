package Menu.src;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import wiiMoteInput.PlayerMote;
import Menu.src.multiplayer.MultiplayerMenu;

/**
 * Class Mouse
 * Vengono gestite le coordinate del mouse
 * @author Luigi Marino
 *
 */
public class Mouse extends MouseAdapter{
	
	Image fantasy;
	Image newGame;
	Image multiplayer;
	Image credits;
	Image options;
	Image exit;
	
	int position[];
	
	MainPanel mainPanel;
	InitSingleGame initSingleGame;
	PlayerMote playerMote;
	
	public MultiplayerMenu multiplayerMenu;
	
	MainMenu mainMenu;
	Options optionsPanel;
	String sSound;
	
	public static String NEWGAME = "newgame";
	public static String MULTI = "multi";
	public static String OPT = "opt";
	public static String CRED = "cred";
	public static String EXIT = "exit";
	
	
	/**
	 * 
	 * @param initialPosition
	 * @param fantasy
	 * @param newGame
	 * @param multiplayer
	 * @param credits
	 * @param options
	 * @param exit
	 * @param playerMote
	 * @param mainMenu
	 * @param mainPanel
	 */
	public Mouse(int initialPosition[], Image fantasy, Image newGame, Image multiplayer, Image credits, Image options,
			     Image exit, PlayerMote playerMote, MainMenu mainMenu, MainPanel mainPanel ){
		
		this.playerMote = playerMote;
		this.mainMenu = mainMenu;
		this.fantasy = fantasy;
		this.newGame = newGame;
		this.multiplayer = multiplayer;
		this.credits = credits;
		this.options = options;
		this.exit = exit;
		this.position = initialPosition;
		
		this.mainPanel = mainPanel;
		sSound = null;
		
		this.optionsPanel = new Options(this.mainMenu, this.mainPanel);
		this.multiplayerMenu = new MultiplayerMenu(this.mainMenu);
		
		this.initSingleGame = new InitSingleGame(this.mainMenu, this.mainPanel, this.playerMote);
	}
	
	/**
	 * Quando il mouse passa sulle immagini allora viene avviato l'audio
	 */
	public void mouseMoved(MouseEvent e){
		super.mouseMoved(e);		

		if(e.getX() >= position[0] && e.getX() <= position[0]*2 
			&& e.getY() >= position[1] && e.getY() <= position[1]+position[1]){
			mainPanel.sNewGame = true;
			if((sSound == null) || (!sSound.equals(Mouse.NEWGAME))){
				sSound = Mouse.NEWGAME;
				mainMenu.sound.play();
			}
			mainPanel.repaint();
		}
		else {
			if( mainPanel.sNewGame == true ) {
				mainPanel.sNewGame=false;
				sSound = null;
				mainPanel.repaint(); 
			}
		}
		if(e.getX() >= position[2] && e.getX() <= position[2]*2 
		     && e.getY() >= position[3] && e.getY() <= position[3]+position[1]){
				mainPanel.sMultiplayer = true;
				if((sSound == null) || (!sSound.equals(Mouse.MULTI))){
					sSound = Mouse.MULTI;
					mainMenu.sound.play();
				}
				mainPanel.repaint();
			}
			else
				if( mainPanel.sMultiplayer == true ){
					mainPanel.sMultiplayer=false;
					sSound = null;
					mainPanel.repaint();
				}
			
		if(e.getX() >= position[4] && e.getX() <= position[4]*2 
			     && e.getY() >= position[5] && e.getY() <= position[5]+position[1]){
				mainPanel.sOptions = true;
				if((sSound == null) || (!sSound.equals(Mouse.OPT))){
					sSound = Mouse.OPT;
					mainMenu.sound.play();
				}
				mainPanel.repaint();
			}
			else
				if( mainPanel.sOptions == true ){
					mainPanel.sOptions = false;
					sSound = null;
					mainPanel.repaint();
				}		
		
		if(e.getX() >= position[6] && e.getX() <= position[6]*2 
			     && e.getY() >= position[7] && e.getY() <= position[7]+position[1]){
				mainPanel.sCredits = true;
				if((sSound == null) || (!sSound.equals(Mouse.CRED))){
					sSound = Mouse.CRED;
					mainMenu.sound.play();
				}
				mainPanel.repaint();
			}
			else
				if( mainPanel.sCredits == true ){
					mainPanel.sCredits = false;
					sSound = null;
					mainPanel.repaint();
				}
				
		
		if(e.getX() >= position[8] && e.getX() <= position[8]*2 
			     && e.getY() >= position[9] && e.getY() <= position[9]+position[1]){
				mainPanel.sExit = true;
				if((sSound == null) || (!sSound.equals(Mouse.EXIT))){
					sSound = Mouse.EXIT;
					mainMenu.sound.play();
				}
				mainPanel.repaint();
			}
			else
				if( mainPanel.sExit == true ){
					mainPanel.sExit = false;
					sSound = null;
					mainPanel.repaint();
				}
						
	}
	
	/**
	 * Quando si clicca su una immagine viene eseguito lo switch ad un'altro
	 * pannello
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
		if(mainPanel.sExit){
			//TODO kill wii
			mainMenu.ok.play();
			mainMenu.colonna.close();
			playerMote.disconnectMote();
			mainMenu.close();
		}
		
		if(mainPanel.sNewGame){
			if( mainMenu.playMote.getMote() == null){
				mainMenu.error.play();
				 String message = "                 Wiimote not connected\n" +
				 		"Please go to options and connect the Wiimote\n";

				    JOptionPane.showMessageDialog(new JFrame(), message, "Error",
				        JOptionPane.ERROR_MESSAGE);
				  }
			else{
				mainMenu.ok.play();
				//mainMenu.setVisible(false);
				mainMenu.switchTo(this.initSingleGame);
			}
		}
		
		if(mainPanel.sMultiplayer){
			mainMenu.ok.play();
			this.mainMenu.refreshLobbyClient();
			this.multiplayerMenu.logingTab.checkPanel();
			mainMenu.switchTo(this.multiplayerMenu);
		}
		
		if(mainPanel.sOptions){
			mainMenu.ok.play();
			mainMenu.switchTo(this.optionsPanel);
		}
		
		if(mainPanel.sCredits){
			mainMenu.ok.play();
			mainMenu.switchTo( new Credits(mainMenu, mainPanel));
		}
	}
}
