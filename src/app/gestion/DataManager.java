package gestion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import app.Enregistrement;
import app.Joueur;

public class DataManager {
	
	private ArrayList<Enregistrement> enregistrements = new ArrayList<Enregistrement>();
	private HashSet<Joueur> joueurs = new HashSet<Joueur>(); //Liste des différents joueurs avec leur état en début de partie.
	private File dataFile;
	private String titre;
	
	public ArrayList<Enregistrement> getEnregisrements() {
		return enregistrements;
	}
	
	public DataManager() {
	}
	
	public DataManager(File file) {
		dataFile = file;
		titre = file.getName();
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
					enregistrements .add(rec);
					rec = new Enregistrement();
				}

			}

			enregistrements .add(rec);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fin chargement enregistrements");
	}
	
	public void loadStats() {
		joueurs.stream().forEach(j -> j.initialiserTerrain());
		enregistrements.stream().forEach(e -> e.stream().forEach(j -> getJoueur(j.getId()).ajouterPresence(j.getX_pos(), j.getY_pos())));
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
	
	/**
	 * 
	 * @param id Id du joueur à chercher
	 * @return Retourne l'instance du joueur associé à l'id, null si inexistant 
	 */
	public Joueur getJoueur(int id) {
		return joueurs.stream().filter(j -> Objects.equals(j.getId(), new Integer(id))).findFirst().orElse(null);
	}
	
	public Joueur lastDataJoueur(int joueurId)
	{
		ListIterator<Enregistrement> iterator = enregistrements.listIterator(enregistrements.size()); // On précise la position initiale de l'iterator. Ici on le place à la fin de la liste
		while(iterator.hasPrevious()){
			
		   Enregistrement item = iterator.previous();
		   ListIterator<Joueur> iterator2 = item.listIterator(item.size());
		   
		   while(iterator2.hasPrevious()){
			   
			   Joueur j = iterator2.previous();
			   if (j.getId() == joueurId)
				   return j;
			 
		   }
		} return null;		
				
	}
	
	public Integer getRecordNumber()
	{
		return enregistrements.size();
	}

	public Float getPos(int indexEnregistrement, int idJoueur) {
		/*Float value = null;
		Enregistrement E = this.enregistrements.get(indexEnregistrement);	
		for (Joueur j : E)
		{
			if (j.getId() == idJoueur) value = j.getX_pos();
		}*/
		return this.enregistrements.get(indexEnregistrement).stream().filter(j -> Objects.equals(j.getId(), idJoueur)).reduce((first, second) -> second).get().getX_pos();
		//return value ;
	}
	
	/**
	 * Trouve la zone du terrain sur lequel un joueur à été le plus présent
	 * @param idJoueur
	 * @return
	 */
	public Integer getPresenceMax(int idJoueur) {

		Joueur j = getJoueur(14);
		int[][] terrain = j.getPresenceTerrain();
		Integer max = 0;
		
		for (int x = 0; x < terrain.length; x++) {
			for (int y = 0; y < terrain.length; y++) {

				if (max < terrain[x][y])
					max = terrain[x][y];

			}
		}
		return max;
	}

	public String getTitreMatch() {
		return titre;
	}
	
	
}
