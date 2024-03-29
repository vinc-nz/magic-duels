package Menu.src;

import game.Game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import wiiMoteInput.PlayerMote;

/**
 * Class MainPanel
 * Pannello principale del menù
 * da esso si può decidere dove switchare:
 * Single Game, Multiplayer, Options, Credits, Exit
 * @author Luigi Marino
 *
 */
public class MainPanel extends JPanel{
	
	public Image fantasy, newGame, multiplayer, options, credits, exit,
			     selectNewGame, selecMultiplayer, selectOptions, selectCredits, selectExit;
	
	public boolean sNewGame, sMultiplayer, sOptions, sCredits, sExit;

	public int position[];
	
	final int SCREEN_WIDTH = 1280;
	final int SCREEN_HEIGHT = 800;
	
	final int IMAGE_WIDTH = 211;
	final int IMAGE_HEIGTH = 122;
	
	public Mouse mouseAdapter;
	
	Game game;
	MainMenu mainMenu;
	PlayerMote playerMote;
	
	/**
	 * @param fantasy
	 * @param newGame
	 * @param multiplayer
	 * @param options
	 * @param credits
	 * @param exit
	 * @param selectNewGame
	 * @param selecMultiplayer
	 * @param selectOptions
	 * @param selectCredits
	 * @param selectExit
	 * @param playerMote
	 * @param mainMenu
	 */
	public MainPanel(Image fantasy, Image newGame, Image multiplayer, Image options, 
			Image credits, Image exit, Image selectNewGame,
			Image selecMultiplayer, Image selectOptions,
			Image selectCredits, Image selectExit, PlayerMote playerMote, MainMenu mainMenu){
	
		
		
		this.playerMote = playerMote;
		this.mainMenu = mainMenu;

		setOpaque(false);
		
		this.fantasy = fantasy;
		this.newGame = newGame;
		this.multiplayer = multiplayer;
		this.options = options;
		this.credits = credits;
		this.exit = exit;

		this.selectNewGame = selectNewGame; 
		this.selecMultiplayer = selecMultiplayer;
		this.selectCredits = selectCredits;
		this.selectOptions = selectOptions;
		this.selectExit = selectExit;

		sNewGame = false;
		sMultiplayer = false;
		sCredits = false;
		sOptions = false;
		sExit = false;

		position = new int[12];
		
		Dimension size = new Dimension(fantasy.getWidth(null), fantasy.getHeight(null));

		this.mouseAdapter = new Mouse(position, fantasy, newGame, multiplayer, credits, options, exit, playerMote, 
				mainMenu, this);
		
		this.addMouseListener(this.mouseAdapter);
		this.addMouseMotionListener(new Mouse(position, fantasy, newGame, multiplayer, credits, 
											  options, exit, playerMote, mainMenu, this));

		setPreferredSize(size);
		setSize(size);
		
	}
	
	/**
	 * Disegna l'immagine di sfondo e le immagini per la scelta dello switch
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(fantasy, 0, 0, this.getWidth(), this.getHeight(), null);
		
		if(sNewGame)
			g.drawImage(selectNewGame, konst2(200), konst(100), imageGetWidth(), imageGetHeight(), null);
			
		else{
			
			g.drawImage(newGame, konst2(200), konst(100), imageGetWidth(), imageGetHeight(), null);
			position[0]=konst2(200); position[1]=konst(100);
		}

		if(sMultiplayer)
			g.drawImage(selecMultiplayer, konst2(200), konst(200), imageGetWidth(), imageGetHeight(), null);
		else{
			g.drawImage(multiplayer, konst2(200), konst(200), imageGetWidth(), imageGetHeight(), null);
			position[2]=konst2(200); position[3]=konst(200);
		}

		if(sOptions)
			g.drawImage(selectOptions, konst2(200), konst(300), imageGetWidth(), imageGetHeight(), null);
		else{
			g.drawImage(options, konst2(200), konst(300), imageGetWidth(), imageGetHeight(), null);
			position[4]=konst2(200); position[5]=konst(300);
		}

		if(sCredits)
			g.drawImage(selectCredits, konst2(200), konst(400), imageGetWidth(), imageGetHeight(), null);
		else{
			g.drawImage(credits, konst2(200), konst(400), imageGetWidth(), imageGetHeight(), null);
			position[6]=konst2(200); position[7]=konst(400);
		}

		if(sExit)
			g.drawImage(selectExit, konst2(200), konst(500), imageGetWidth(), imageGetHeight(), null);
		else{
			g.drawImage(exit, konst2(200), konst(500), imageGetWidth(), imageGetHeight(), null);
			position[8]=konst2(200); position[9]=konst(500);
		}

	}

	/**
	 * Funzione che serve per il ridimensionamento delle immagini
	 * @return
	 */
	public int imageGetWidth(){
		return (IMAGE_WIDTH * this.getWidth()) / SCREEN_WIDTH;
	}
	
	/**
	 * Funzione che serve per il ridimensionamento delle immagini
	 * @return
	 */
	public int imageGetHeight(){
		return (IMAGE_HEIGTH * this.getHeight()) / SCREEN_HEIGHT;
	}
	
	/**
	 * Funzione che serve per il ridimensionamento delle immagini
	 * @return
	 */
	public int konst(int i){
		return (i * this.getHeight()) / SCREEN_HEIGHT; 
	}

	/**
	 * Funzione che serve per il ridimensionamento delle immagini
	 * @return
	 */
	public int konst2(int i){
		return (i * this.getWidth()) / SCREEN_WIDTH; 
	}
	
}