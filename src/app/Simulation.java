package app;

import gestion.DataManager;

public class Simulation {
	public static void main(String[] args) {
		System.out.println("Bonjour");
		DataManager dm = new DataManager("2013-11-28_tromso_tottenham.csv");
		dm.findEnregistrements();
	}
}
