package app;

import java.util.ArrayList;

import gestion.DataManager;

public class Simulation {
	public static void main(String[] args) {
		System.out.println("Bonjour");
		DataManager dm = new DataManager("2013-11-28_tromso_tottenham.csv");
		dm.findEnregistrements();
		System.out.println("fin chargement données");
		System.out.println("tests");
		ArrayList<Enregistrement> E = dm.getEnregisrements();
		
	}
}
