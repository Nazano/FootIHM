package gestion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import app.Enregistrement;
import app.Joueur;

public class DataManager {
	
	private ArrayList<Enregistrement> enregisrements = new ArrayList<Enregistrement>();
	private HashSet<Joueur> joueurs = new HashSet<Joueur>(); //Liste des différents joueurs avec leur état en début de partie.
	private File dataFile;


	public DataManager(File file) {
			dataFile = file;
	}
	
	/**
	 * Trouve l'ensemble des enregistrements contenu dans les donnÃ©es
	 */
	public void findEnregistrements() {
		Enregistrement rec = new Enregistrement();
		List<String[]> raw_data;
		try {
			raw_data = CsvUtils.readCSV(dataFile.getAbsolutePath());

			String currentDate = raw_data.get(0)[0];

			for (String[] l : raw_data) {

				// Si on est dans le même intervalle de temps
				if (l[0].equals(currentDate)) {
					Joueur j = rawDataToJoueur(l);
					rec.add(j);
					joueurs.add(j);
				} else { // Sinon change d'intervalle et ajoute l'enregistrement à la liste
					currentDate = l[0];
					enregisrements.add(rec);
					rec = new Enregistrement();
				}

			}

			enregisrements.add(rec);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fin chargement enregistrements");
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
		
	public ArrayList<Enregistrement> getEnregisrements() {
		return enregisrements;
	}
	
}
