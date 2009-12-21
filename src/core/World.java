package core;

/*
 * SITUAZIONE GENERALE DEL QUADRO
 * 
 *  un piano cartesiano ortogonale sugli assi x e y
 *  
 *  +funzioni di utilità
 */

public abstract class World {
	public static final int centerDistance=500; // massima distanza dal centro degli assi
	
	// true se le coordinate passategli rientrano nel piano
	public static boolean validPosition(float x,float y) {
		return (Math.abs(x)<centerDistance && Math.abs(y)<centerDistance);
	}
	
	// Calcola e restituisce il vettore traiettoria tra due punti 
	public static float[] calculateTrajectory(float x1, float y1, float x2, float y2, float module) {
		float angle = angleBetween(x1, y1, x2, y2);
		float[] trajectory = {module*(float)Math.cos(angle), 
								module*(float)Math.sin(angle)};
		return trajectory;
	}
	
	//restituisce l'angolo della retta tra due punti
	public static float angleBetween(float x1, float y1, float x2, float y2) {
		float m = (y2-y1)/(x2-x1); //coefficiente angolare della retta tra i due punti
		double angle = Math.abs(Math.atan(m));
		//fix dell'angolo in base alla posizione dei punti
		if (y1>y2)
			angle = -angle;
		if (x1>x2)
			angle = Math.PI - angle;
		
		return (float)angle;
	}
	
}