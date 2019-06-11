package app;

public class Joueur implements Stats{

	private int id;
	private int [][] presenceTerrain;
	private float x_pos ;
	private float y_pos; 
	private float heading; 
	private float direction; 
	private float energy; 
	private float speed; 
	private float total_distance;
	private int nbPosition;
	
	
	
	@Override
	public double[][] calculerStats() {
		
		double[][] stats = null;
		for (int i=0; i<presenceTerrain.length;i++)
		{
			for(int j = 0 ; j<presenceTerrain.length;j++)
			{
				
				stats[i][j] = (presenceTerrain[i][j] / nbPosition)*100 ; 
				
			}
		}

		return stats;
	}
	@Override
	public void ajouterPresence() {
		presenceTerrain[(int)x_pos][(int)y_pos] =  presenceTerrain[(int)x_pos][(int)y_pos]++;
		this.nbPosition ++;
		
	} 

	
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

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Joueur))
			return false;
		Joueur other = (Joueur) obj;
		if (id != other.id)
			return false;
		return true;
	} 
		
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Integer.toString(id);
	}
	
	
}
