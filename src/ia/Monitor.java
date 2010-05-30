package ia;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class Monitor
 * Gestisce i lock, fa si che un thread alla volta acceda ai metodi della
 * classe Fight
 * @author Luigi Marino
 *
 */
public class Monitor {
	
	boolean start;
	boolean move;
	Lock lock;
	
	public Monitor(){
		start = false;
		move = false;
		lock = new ReentrantLock();
	}
	
	public void startSpell(){
		lock.lock();
		  try {
		         start = true;
		     } finally {
		         lock.unlock();
		     }
	}
	
	public void moveCharacter(){
		lock.lock();
		  try {
		         move = true;
		     } finally {
		         lock.unlock();
		     }
	}
	
	public void startSpellFalse(){
		lock.lock();
		  try {
		         start = false;
		     } finally {
		         lock.unlock();
		     }
	}
	
	public void moveCharacterFalse(){
		lock.lock();
		  try {
		         move = false;
		     } finally {
		         lock.unlock();
		     }
	}
	
	public boolean start(){
		lock.lock();
		  try {
		         if(start) 
		        	 return true;
		         else
		        	 return false;
		     } finally {
		         lock.unlock();
		     }
	}
	
	public boolean move(){
		lock.lock();
		  try {
		         if(move) 
		        	 return true;
		         else
		        	 return false;
		     } finally {
		         lock.unlock();
		     }
	}

}
