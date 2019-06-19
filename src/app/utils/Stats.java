package app.utils;

public interface Stats {

	public abstract double[][] calculerStats();
	
	public void ajouterPresence();
	public void ajouterPresence(double x, double y) throws IllegalArgumentException;
}
