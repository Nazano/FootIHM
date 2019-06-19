package app.utils;

import java.security.InvalidParameterException;

public class Joueur implements Stats{

	protected int id;
	protected int [][] presenceTerrain;
	protected float x_pos ;
	protected float y_pos; 
	protected float heading; 
	protected float direction; 
	protected float energy; 
	protected float speed; 
	protected float total_distance;
	protected int nbPosition;
	
	
	
	public int[][] getPresenceTerrain() {
		return presenceTerrain;
	}
	
	public void setPresenceTerrain(int[][] presenceTerrain) {
		this.presenceTerrain = presenceTerrain;
	}
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
		int X = (int)x_pos;
		int Y =(int)y_pos;
		if (X >0 && Y>0 ) 
			presenceTerrain[X][Y] =  presenceTerrain[X][Y]++;
		this.nbPosition ++;
		
	} 
	
	@Override
	public void ajouterPresence(double x, double y) {
		if ((x < 0) || (y < 0))
			return;
		int intX = (int) x;
		int intY = (int) y;
		
		presenceTerrain[intX][intY]++;
		this.nbPosition ++;
		
	} 

	
	public Joueur(int id, float x_pos, float y_pos, float heading, float direction, float energy, float speed,
			float total_distance) {
		this.id = id;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.heading = heading;
		this.direction = direction;
		this.energy = energy;
		this.speed = speed;
		this.total_distance = total_distance;

	}
	
	public Joueur(Joueur j) {
		this.id = j.id;
		this.x_pos = j.x_pos;
		this.y_pos = j.y_pos;
		this.heading = j.heading;
		this.direction = j.direction;
		this.energy = j.energy;
		this.speed = j.speed;
		this.total_distance = j.total_distance;
	}

	public void initialiserTerrain() {
		this.presenceTerrain = new int [150][150];	
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
		StringBuilder sb = new StringBuilder();
		sb.append("Joueur" + id +"\n");
		sb.append("\tPosition(" + x_pos + "," + y_pos + ")\n");
		sb.append("\tVitesse = " + speed + " m/s\n");
		sb.append("\t Distance parourue = " + total_distance + " m\n");
		sb.append("\tEnergie = " + energy + "\n");
		return sb.toString();
	}

	public float getX_pos() {
		return x_pos;
	}

	public float getY_pos() {
		return y_pos;
	}

	public float getDirection() {
		return direction;
	}
	
	protected void updateInfos(Joueur j) {
		if(this.id != j.id) 
			throw(new InvalidParameterException("Joueurs différents source_id=" + id + " parameter_id=" + "id"));
		else {
			this.x_pos = j.x_pos;
			this.y_pos = j.y_pos;
			this.heading = j.heading;
			this.direction = j.direction;
			this.energy = j.energy;
			this.speed = j.speed;
			this.total_distance = j.total_distance;
		}
	}
	
	
}
