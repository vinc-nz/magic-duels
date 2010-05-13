package core.spells;

import core.fight.Character;
import core.objects.Spell;
import core.space.Direction;

public class FanBalls implements Spell {
	
	private static final int N = 5;
	private static final float ANGLE_OFFSET = (float) Math.PI/6;
	
	Fireball[] balls = new Fireball[N];
	
	
	public FanBalls() {
		for (int i = 0; i < balls.length; i++) {
			balls[i] = new Fireball();
		}
	}
	
	

	@Override
	public int getManaCost() {
		
		return balls[0].getManaCost()*N;
	}

	@Override
	public void launch() {
		
		for (int i = 0; i < balls.length; i++) {
			balls[i].materialize();
		}

	}

	@Override
	public void setOwner(Character owner) {
		int j=0;
		for (int i = 0; i < balls.length; i++) {
			balls[i].setOwner(owner);
			j++;
			if (i==N/2) j=-j+1;
			Direction d = owner.getDirection().rotate(ANGLE_OFFSET*j);
			balls[i].setDirection(d);
		}

	}

}
