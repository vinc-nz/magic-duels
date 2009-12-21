package core;

/*
 * MAGIA D'ATTACCO ( è un thread )
 */

public class SpellAttack extends SpellInstance {
	PlayingCharacter enemy; // nemico
	Spell spell; // magia
	Fight fight; // partita
	
	float x;	// Sono le coordinate della posizione attuale della magia.
	float y;	// "	"	"	"	"	"	"	"	"	"	"	"	
	
	float speed; //la velocita' della magia
	float speedX;	// le componeti del vettore velocita'
	float speedY;	// le componeti del vettore velocita'
	int attackPoints;	// Sono i punti di attacco della magia
	boolean hitten;		// E' true se la magia si e' fermata poiche' 
						// ha colpito l'avversario
		
	static final long REFRESH_TIME = 10; // tempo di refresh (in milisecondi) del thread
	
	public SpellAttack(int attackPoints, Spell spell, PlayingCharacter owner)
	{
		super(spell, owner);
		this.x = owner.x;
		this.y = owner.y;	
		this.attackPoints = attackPoints;
		this.speed = spell.speed;
		this.hitten = false;
		this.enemy=null;
		this.fight=null;
	}
	 
	 // Dato in input un puntatore al player avversatrio e la velocità della magia,
	 // calcola i dati per lo spostamento.
	public void setTarget(PlayingCharacter enemy, Fight fight){
		this.enemy=enemy;
		float[] trajectory = World.calculateTrajectory(x, y, enemy.x, enemy.y, speed);
		this.speedX=trajectory[0];
		this.speedY=trajectory[1];
		this.fight=fight;
	}
	
	// true se si deve fermare
	protected boolean hasToStop(){
		if(enemyHitten() || !World.validPosition(this.x, this.y))
			return true;
		
		//TODO Controlla che la magia non si debba fermare per qualche altro motivo
		return false;
	}
	
	public void run(){
		super.run();
		while(!hasToStop()){ // se non si e' ferma incremento le posizioni			
			x += speedX;
			y += speedY;
		
			try {
				sleep(REFRESH_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (hitten)
			fight.applyDamage(this);
	}
	
	//true se il nemico è stato colpito
	public boolean enemyHitten() {
		if (hitten || (Math.abs(this.x-this.enemy.x)<5 && Math.abs(this.y-this.enemy.y)<5)) {
			this.hitten=true;
			return true;
		}
		return false;
	}

	public float[] getTrajectory() {
		float[] t = {speedX, speedY};
		return t;
	}

	public float[] getPosition() {
		float[] p = {x ,y};
		return p;
	}
	
	
}
