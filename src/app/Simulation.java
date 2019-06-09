package app;

import gestion.DataManager;

public class Simulation {
	public static void main(String[] args) {
		System.out.println("Bonjour");
		DataManager dm = new DataManager("sample.csv");
		dm.findEnregistrements();
	}
}
