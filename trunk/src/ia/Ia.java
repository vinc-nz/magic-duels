package ia;

import java.util.Random;

import core.spells.FanBalls;
import core.spells.Fireball;
import input.CharacterController;

public class Ia extends Thread{
	
	CharacterController characterController;
	Random rand;
	
	public Ia(CharacterController characterController) {
		super();
		this.characterController = characterController;
		rand = new Random();
	}
	
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
	
	public void rambo(){
		characterController.castSpell(FanBalls.class);
	}
	
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
			
			//if(rand.nextInt(10) == 3)
				//intelligence();
				this.moveSpeed();
				//characterController.move("left");
			//else
				//rambo();
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				characterController.castSpell(FanBalls.class);
				
				
			
		}
	}
}
