package core;

/*
 * MAGO IN GIOCO
 */

public class PlayingCharacter extends Character {
	float x; // posizione iniziale sulle x
	float y; // posizione iniziale sulle y
	
	float angle; //angolo di rotazione
	
	int magicProtection; // valore da applicare alla difesa 
						 //in caso sia attiva una magia di protezione
	
	boolean spellCastPosition;	//se il mago assume la posizione per il lancio degli incantesimi
	boolean spellInWaiting;		//se c'è una magia che è stata richiesta ed è 
								//in attesa di essere lanciata
	
	
	
	public PlayingCharacter(String name, int lifePoints, int energyPoints, int attack,	int defence, float speed) {
		super(name,lifePoints,energyPoints,attack,defence,speed);
		
		this.x = 0;
		this.y = 0;
		this.angle=0;
		
		this.spellCastPosition = false;
		this.spellInWaiting = false;
		
		this.magicProtection = 0;
	}
	
	// Setta la posizione del personaggio
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float[] getPosition() {
		float[] p = {x, y};
		return p;
	}
	
	// Sposta la posizione del personaggio dei valori dati.
	public void move(float x, float y)
	{
		if(!spellCastPosition && World.validPosition(this.x + x, this.y + y)){
			this.x += x;
			this.y += y;
		}
	}
	
	//modifica l'angolo in base al parametro dato
	public void changeAngle(float offset) {
		if (!spellCastPosition) {
			angle+=offset;
			if (Math.abs(angle)>2*Math.PI)
				angle=(float)(angle-(2*Math.PI*Math.signum(angle)));
		}
	}
	
	//restituisce un array di due elementi rappresentati il versore della direzione
	//verso cui il personaggio è rivolto
	public float[] getDirection() {
		float[] direction = new float[2];
		direction[0] = (float)Math.cos((double)angle);
		direction[1] = (float)Math.sin((double)angle);
		return direction;
	}
	
	/*
	 *  Prende in input il valore di attacco della magia avversaria;
	 *  calcola  la differenza tra tali punti, la difesa ed un'eventuale protezione
	 *  e se necessario decrementa i lifepoints.
	 *  
	 *  RESTITUISCE IL DANNO SUBITO.
	 */
	public int gotSpell(int points) {
		points = points - defense - magicProtection;
		if( points > 0 ){
			lifePoints -= points;
			return points;
		}
		return 0;
	}

	/*
	 * restituisce l'istanza della magia chiamata in base al nome passato
	 */
	public SpellInstance castSpell(String spellName){
		this.spellInWaiting = true;
		if (energyPoints < spells.get(spellName).cost)
			return new ManaAdvice(spells.get(spellName), this); // se non c'è abbastanza mana.
		
		energyPoints -= spells.get(spellName).cost;
		
		if(spells.get(spellName).defensive){
			magicProtection += spells.get(spellName).value;
			return new SpellDefence(spells.get(spellName), this); //difesa
		} 
		return new SpellAttack(spells.get(spellName).value + attack, spells.get(spellName), this);
	}

	
	// elimina la barriera
	public void clearProtection() {
		this.magicProtection = 0;
	}
	
	// true se è morto
	public boolean isDead() {
		return this.lifePoints<=0;
	}

	//restituisce i punti mana
	public int getMana() {
		return energyPoints;
	}

	//restituisce i punti vitali
	public int getLife() {
		if (lifePoints>=0)
			return lifePoints;
		return 0;
	}
	
	public boolean isInSpellCastPosition() {
		return spellCastPosition;
	}
	
	//true se il personaggio può lanciare la magia
	public boolean canCast(String spellName) {
		return this.hasSpell(spellName)
				&& this.spellCastPosition
				&& !this.spellInWaiting;
	}
	
}
