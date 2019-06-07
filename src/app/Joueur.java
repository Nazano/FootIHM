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
		
		
	
}
