package Menu.src;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import wiiMoteInput.PlayerMote;

import jmegraphic.Game;

public class MainMenu extends JFrame {
	
	Dimension size;
	DisplayMode currentDM;
	GraphicsDevice device;
	LinkedHashMap<String, DisplayMode> displayModes;
	
	PlayerMote playMote;
	
	public int WIDTH = 1280;
	public int HEIGHT = 800;
	boolean fullscreen = true;
	
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
		
		playMote = new PlayerMote();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		MainPanel panel = new MainPanel(
				new ImageIcon("src/Menu/data/mainMenu2.jpg").getImage(),
				new ImageIcon("src/Menu/data/newGame.png").getImage(),
				new ImageIcon("src/Menu/data/multiplayer.png").getImage(),
				new ImageIcon("src/Menu/data/options.png").getImage(),
				new ImageIcon("src/Menu/data/credits.png").getImage(),
				new ImageIcon("src/Menu/data/exit.png").getImage(),
				new ImageIcon("src/Menu/data/selectNewGame.png").getImage(),
				new ImageIcon("src/Menu/data/selectMultiplayer.png").getImage(),
				new ImageIcon("src/Menu/data/selectOptions.png").getImage(),
				new ImageIcon("src/Menu/data/selectCredits.png").getImage(),
				new ImageIcon("src/Menu/data/selectExit.png").getImage(),
				new Game(playMote), this);
	    
		switchTo( panel );
		size = Toolkit.getDefaultToolkit().getScreenSize();
		
		setPreferredSize(size);
		setSize(size);
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
}

