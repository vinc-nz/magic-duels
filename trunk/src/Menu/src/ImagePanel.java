package Menu.src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import jmegraphic.Game;

class ImagePanel extends JPanel implements Runnable{

  public Image fantasy, newGame, multiplayer, options, credits, exit,
  			   selectNewGame, selecMultiplayer, selectOptions, selectCredits, selectExit,
  			   volume_on, volume_off;
  public boolean sNewGame, sMultiplayer, sOptions, sCredits, sExit, volume;
  
  public int position[];
  
  Game game;
  Sound s;
 
  public ImagePanel(Image fantasy, Image newGame, Image multiplayer, Image options, 
		  			Image credits, Image exit, Image selectNewGame,
		  			Image selecMultiplayer, Image selectOptions,
		  			Image selectCredits, Image selectExit, Image volume_on,
		  			Image volume_off, Game game, Sound s){
	this.game = game;
	this.s = s;
	
	this.fantasy = fantasy;
    this.newGame = newGame;
    this.multiplayer = multiplayer;
    this.options = options;
    this.credits = credits;
    this.exit = exit;
    
    this.volume_on = volume_on;
    this.volume_off = volume_off;
    
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
    volume = false;
    
    position = new int[12];
   
    Dimension size = new Dimension(fantasy.getWidth(null), fantasy.getHeight(null));
    
    this.addMouseListener(new Mouse(position, fantasy, newGame, multiplayer, credits, options, exit, s, game, this));
    this.addMouseMotionListener(new Mouse(position, fantasy, newGame, multiplayer, credits, options, exit, s, game, this));
  
    s.loop();
    
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setSize(size);
    setLayout(null);
  }

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
	  
	  if(volume)
		  g.drawImage(volume_off, konst2(1000), konst(600), imageGetWidth(), imageGetHeight(), null);
	  else{
		  g.drawImage(volume_on, konst2(1000), konst(600), imageGetWidth(), imageGetHeight(), null);
		  position[10]=konst2(1000); position[11]=konst(600);
	  }
  	}
  
  public void run() {
		while(true){
			try{
				Thread.sleep(50);
			}catch(Exception e){}		
			this.repaint();
		}
	}
  
  public int imageGetWidth(){
	  return (211 * this.getWidth()) / fantasy.getWidth(null);
  }
  
  public int imageGetHeight(){
	  return (122 * this.getHeight()) / fantasy.getHeight(null);
  }
  
  public int konst(int i){
	  return (i * this.getHeight()) / fantasy.getHeight(null); 
  }
  
  public int konst2(int i){
	  return (i * this.getWidth()) / fantasy.getWidth(null); 
  }

}