package app;

public class Joueur {

	private int id;
	private int [][] presenceTerrain;
	private float x_pos ;
	private float y_pos; 
	private float heading; 
	private float direction; 
	private float energy; 
	private float speed; 
	private float total_distance;
	
	public Joueur(int id, float x_pos, float y_pos, float heading, float direction, float energy, float speed,
			float total_distance) {
		super();
		this.id = id;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.heading = heading;
		this.direction = direction;
		this.energy = energy;
		this.speed = speed;
		this.total_distance = total_distance;
	} 
		
		
	
}
