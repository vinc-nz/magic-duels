package Menu.src;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import jmegraphic.Game;

public class Mouse extends MouseAdapter{
	
	Image fantasy;
	Image newGame;
	Image multiplayer;
	Image credits;
	Image options;
	Image exit;
	int position[];
	ImagePanel test;
	Sound s;
	Game game;
	MenuMultiplayer menuMultiplayer;
	
	public Mouse(int initialPosition[], Image fantasy, Image newGame, Image multiplayer, Image credits, Image options,
			     Image exit, Sound s, Game game, ImagePanel test){
		
		this.game = game;
		this.s = s;
		this.fantasy = fantasy;
		this.newGame = newGame;
		this.multiplayer = multiplayer;
		this.credits = credits;
		this.options = options;
		this.exit = exit;
		this.test = test;
		this.position = initialPosition;
	}
	
	public void mouseMoved(MouseEvent e){
		super.mouseMoved(e);		
		
		if(e.getX() >= position[0] && e.getX() <= position[0]*2 
			&& e.getY() >= position[1] && e.getY() <= position[1]+position[1]){
			test.sNewGame = true;
		}
		else
			test.sNewGame=false;
		
		if(e.getX() >= position[2] && e.getX() <= position[2]*2 
		     && e.getY() >= position[3] && e.getY() <= position[3]+position[1]){
				test.sMultiplayer = true;
			}
			else
				test.sMultiplayer=false;
		
		if(e.getX() >= position[4] && e.getX() <= position[4]*2 
			     && e.getY() >= position[5] && e.getY() <= position[5]+position[1]){
				test.sOptions = true;
			}
			else
				test.sOptions = false;
		
		if(e.getX() >= position[6] && e.getX() <= position[6]*2 
			     && e.getY() >= position[7] && e.getY() <= position[7]+position[1]){
				test.sCredits = true;
			}
			else
				test.sCredits = false;
		
		if(e.getX() >= position[8] && e.getX() <= position[8]*2 
			     && e.getY() >= position[9] && e.getY() <= position[9]+position[1]){
				test.sExit = true;
			}
			else
				test.sExit = false;
		
		
		e.getComponent().repaint();		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
		if(test.sExit){
			System.exit(0);
		}
		
		if(e.getX() >= position[10] && e.getX() <= position[10]*2 
			     && e.getY() >= position[11] && e.getY() <= position[11]+position[1]){
				if(test.volume == false){
					test.volume = true;
					e.getComponent().repaint();		
					s.stop();
				}
				else{
					test.volume = false;
					e.getComponent().repaint();		
					s.start();
				}
			}
		
		if(test.sNewGame){
			s.stop();
			game.initSingleGame();
			game.start();
		}
		
		if(test.sMultiplayer){
			menuMultiplayer = new MenuMultiplayer(game);
		}
		
		if(test.sOptions){
			game.videoSettings();
		}
	}
}
