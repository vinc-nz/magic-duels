package ia;

import java.util.Random;

import core.spells.FanBalls;
import core.spells.Fireball;
import input.CharacterController;

/**
 * Class Ia
 * Gestisce l'intelligenza artificiale dei maghi
 * @author Luigi Marino
 *
 */
public class Ia extends Thread{
	
	CharacterController characterController;
	Random rand;
	
	/**
	 * 
	 * @param characterController
	 */
	public Ia(CharacterController characterController) {
		super();
		this.characterController = characterController;
		rand = new Random();
	}
	
	/**
	 * Muove velocemente l'avversario
	 */
	public void moveSpeed(){
		for(int i=0; i<40; i++){
			characterController.move("right");
			try {
				sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Muove lentamente l'avversario
	 */
	public void moveSlow(){
		for(int i=0; i<40; i++){
			characterController.move("left");
			try {
				sleep(60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Permette all'avversario di lanciare le varie magie
	 */
	public void combo(){
		if(rand.nextInt(10) == 2)
			characterController.castSpell(Fireball.class);
		else if(rand.nextInt(10) == 3)
			characterController.castSpell(FanBalls.class);
		else{
			characterController.castSpell(Fireball.class);
			characterController.castSpell(Fireball.class);
			characterController.castSpell(Fireball.class);
		}
	}
	
	/**
	 * Il mago gestito dall'Ia, si muove in base alla magia ricevuta 
	 */
	public void intelligence(){
		
		if(characterController.getFight().prepSpeel(1, FanBalls.class)){
			this.moveSpeed();
		}
		
		if(characterController.getFight().prepSpeel(1, Fireball.class)){
			this.moveSlow();
		}	
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		while(!characterController.getFight().finished){
			
			if(rand.nextInt(20) == 3)
				combo();
			else
				intelligence();
		}
	}
}
