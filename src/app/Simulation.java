package app;

import java.util.ArrayList;

import gestion.DataManager;

public class Simulation {
	public static void main(String[] args) {
		System.out.println("Bonjour");
		DataManager dm = new DataManager("sample.csv");
		dm.findEnregistrements();
		System.out.println("fin chargement données");
		System.out.println("tests");
		ArrayList<Enregistrement> E = dm.getEnregisrements();
		
	}
}
