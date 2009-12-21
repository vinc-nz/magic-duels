package core;

/*
 *  MAGIA
 */

public class Spell  {

	String name; // nome magia
	int cost;	//costo in mana
	int value; // valore magia
	boolean defensive; // se true è una magia difensiva
	
	int duration; //la durata della magia difensiva (in secondi)
	
	float speed; //la velocità della magia difensiva
	
	public Spell(String name, int cost, int value, boolean defensive, int duration, float speed){
		this.name = name;
		this.cost = cost;
		this.value = value;
		this.defensive = defensive;
		this.duration = duration;
		this.speed = speed;
	}
	
	public String toString(){	
		return name;
	}
	
	

	
}