package swingtest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.SliderUI;

import core.fight.Fight;
import core.space.Direction;
import core.space.Position;
import core.spells.Fireball;

public class Battlefiled extends JPanel {
	
	public Fight fight = new Fight();
	
	Fireball ball = null;
	
	public Battlefiled() {
		fight.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 500, 500);
		
		fight.update();
		g.setColor(Color.red);
		
		this.paintPlayer(0,g);
		this.paintPlayer(1, g);
		
		if (ball!=null)
			g.fillOval(250+(int)ball.getPosition().getX(), 250+(int)ball.getPosition().getY(), 5, 5);
	}

	private void paintPlayer(int i, Graphics g) {
		Position p = fight.getPlayer(i).getPosition();
		Direction d = fight.getPlayer(i).getDirection();
		
		int[] x = {
				250+(int) (p.getX()+d.getX()*10),
				250+(int) (p.getX()+d.rotate((float) (Math.PI*2/3)).getX()*10),
				250+(int) (p.getX()+d.rotate((float) -(Math.PI*2/3)).getX()*10)
		};
		
		
		
		int[] y = {
				250+(int) (p.getY()+d.getY()*10),
				250+(int) (p.getY()+d.rotate((float) (Math.PI*2/3)).getY()*10),
				250+(int) (p.getY()+d.rotate((float) -(Math.PI*2/3)).getY()*10)
		};
		
		g.fillPolygon(x, y, 3);
		
	}
	
	
	
	public static void main(String[] args) {
		JFrame win = new JFrame();
		win.setSize(500,500);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.requestFocus();
		Battlefiled b = new Battlefiled();
		win.setContentPane(b);
		b.addListener();
		
		//win.pack();
		win.setVisible(true);
		b.requestFocus();
		
		new Updater(b).start();
	}
	
	public void update() {
		fight.update();
		if (ball==null) {
			ball = (Fireball) fight.getPlayer(0).castSpell();
			if (ball!=null) {
				System.out.println("launch");
				ball.setSpeed(10);
				ball.launch();
			}
		}
		else {
			ball.update();
			if (!ball.isInGame())
				ball=null;
		}
			
		
		this.repaint();
	}

	private void addListener() {
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				if (e.getKeyChar() == 'f') {
					
					Battlefiled.this.fight.prepareSpell(0, Fireball.class);
				}
				
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					Battlefiled.this.fight.moveCharacter(0, "forward");
					break;
					
				case KeyEvent.VK_LEFT:
					Battlefiled.this.fight.moveCharacter(0, "left");
					break;
					
				case KeyEvent.VK_RIGHT:
					Battlefiled.this.fight.moveCharacter(0, "right");
					break;
					
				case KeyEvent.VK_DOWN:
					Battlefiled.this.fight.moveCharacter(0, "backward");
					break;
				
				

				case KeyEvent.VK_ESCAPE:
					System.out.println("stop");
					Battlefiled.this.fight.running = false;
					break;
				}
				
				
			}
		});
		
	}

}
