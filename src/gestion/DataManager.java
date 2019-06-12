package gestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import app.Enregistrement;
import app.Joueur;

public class DataManager {
	private List<String[]> raw_data;
	private ArrayList<Enregistrement> enregisrements = new ArrayList<Enregistrement>();
	private HashSet<Joueur> joueurs = new HashSet<Joueur>(); //Liste des différents joueurs avec leur état en début de partie.

	
	public ArrayList<Enregistrement> getEnregisrements() {
		return enregisrements;
	}

	public DataManager(String path) {
		try {
			raw_data = CsvUtils.readCSV(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Trouve l'ensemble des enregistrements contenu dans les donnÃ©es
	 */
	public void findEnregistrements() {
		Enregistrement rec = new Enregistrement();
		String currentDate = raw_data.get(0)[0];
		
		for(String[] l : raw_data) {
			
			//Si on est dans le même intervalle de temps 
			if(l[0].equals(currentDate)) {
				Joueur j = rawDataToJoueur(l);
				rec.add(j);
				joueurs.add(j);
			}
			else { //Sinon change d'intervalle et ajoute l'enregistrement à la liste 
				currentDate = l[0];
				enregisrements.add(rec);
				rec = new Enregistrement();
			}
			
		}

		enregisrements.add(rec);
		
		System.out.println("intervalle = 50 ms");
		System.out.println("Nombre d'entrées: " + raw_data.size());
		System.out.println("Nombre d'enregistrements: " + enregisrements.size());
		double stats = 0;
		for(Enregistrement e : enregisrements)
			stats += e.size();
		System.out.println("Nombre moyen de déplacements par échelle de temps = " + (stats/enregisrements.size()));
		
	}
	
	/**
	 * Crée un joueur à partir d'une ligne du jeu de donnée
	 * @param data Le jeu de donné doit avoir la forme indiqué dans le papier
	 * @return
	 */
	private Joueur rawDataToJoueur(String[] data) {
		return new Joueur(
				Integer.parseInt(data[1]),
				Float.parseFloat(data[2]),
				Float.parseFloat(data[3]),
				Float.parseFloat(data[4]),
				Float.parseFloat(data[5]),
				Float.parseFloat(data[6]),
				Float.parseFloat(data[7]),
				Float.parseFloat(data[8])
				);
	}
	
}
