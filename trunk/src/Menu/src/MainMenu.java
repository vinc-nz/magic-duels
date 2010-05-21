package Menu.src;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;
import lobby.LobbyClient;
import wiiMoteInput.PlayerMote;

public class MainMenu extends JFrame {
	
	Dimension size;
	DisplayMode currentDM;
	GraphicsDevice device;
	LinkedHashMap<String, DisplayMode> displayModes;
	
	public LobbyClient lobbyClient;
	PlayerMote playMote;
	MP3 sound;
	MP3 colonna;
	public MP3 ok;
	
	public MainPanel panel;
	public int WIDTH;
	public int HEIGHT;
	public boolean fullscreen = true;
	
	public static void main(String[] args) {
		new MainMenu();
	}

	public MainMenu() {
		super();
		
		setUndecorated(true);  
	    setResizable(false);
	    
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = env.getScreenDevices()[0];
	    
	    displayModes = new LinkedHashMap<String, DisplayMode>();
		for( DisplayMode dm : device.getDisplayModes() ) {
			if( dm.getWidth() >= 800 && dm.getHeight() >= 600 ) {
				String key = dm.getWidth() + "x" + dm.getHeight();
				if( !displayModes.containsKey(key) ) {
					displayModes.put( key, dm );
				}
			}
		}
		
		DisplayMode dm = device.getDisplayModes()[0];
		
		WIDTH = dm.getWidth();
		HEIGHT = dm.getHeight();
		
		fullscreen( displayModes.get( WIDTH + "x" + HEIGHT ) );
		
		this.lobbyClient = new LobbyClient();
		this.playMote = new PlayerMote();
	
		sound = new MP3("src/Menu/data/Click.mp3");
		colonna = new MP3("src/Menu/data/colonna.mp3");
		ok = new MP3("src/Menu/data/ok.mp3");
		//colonna.play();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new MainPanel(
				new ImageIcon("src/Menu/data/mainMenu2.jpg").getImage(),
				new ImageIcon("src/Menu/data/imageNewGame.png").getImage(),
				new ImageIcon("src/Menu/data/imageMultiplayer.png").getImage(),
				new ImageIcon("src/Menu/data/imageOptions.png").getImage(),
				new ImageIcon("src/Menu/data/imageCredits.png").getImage(),
				new ImageIcon("src/Menu/data/imageExit.png").getImage(),
				new ImageIcon("src/Menu/data/imageNewGameS.png").getImage(),
				new ImageIcon("src/Menu/data/imageMultiplayerS.png").getImage(),
				new ImageIcon("src/Menu/data/imageOptionsS.png").getImage(),
				new ImageIcon("src/Menu/data/imageCreditsS.png").getImage(),
				new ImageIcon("src/Menu/data/imageExitS.png").getImage(),
				playMote, this);
	    
		switchTo( panel );
		//size = Toolkit.getDefaultToolkit().getScreenSize();
		//setPreferredSize(size);
		//setSize(size);
		setVisible(true);
	}

	
	public void close() {
		this.dispose();
	}
	
	public void switchTo( JPanel p ) {
		getContentPane().removeAll();
		repaint();
		getContentPane().add(p);
	    validate();
	    p.requestFocus();
	}
	
	public void fullscreen( DisplayMode displayMode ) {
		device.setFullScreenWindow(this);
		device.setDisplayMode( displayMode );
		currentDM = displayMode;
		size = new Dimension( displayMode.getWidth(), 
				displayMode.getHeight() );
		setSize(size);
	}
}

