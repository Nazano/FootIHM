package gestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

import app.Enregistrement;
import app.Joueur;

public class DataManager {
	private List<String[]> raw_data;
	private ArrayList<Enregistrement> enregistrements = new ArrayList<Enregistrement>();
	private HashSet<Joueur> joueurs = new HashSet<Joueur>(); //Liste des différents joueurs avec leur état en début de partie.

	
	public ArrayList<Enregistrement> getEnregisrements() {
		return enregistrements;
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
				/// ajouter stats 
				Joueur j2 ;
				if ((j2 = lastDataJoueur(j.getId()))!= null)
				{	
					j.setPresenceTerrain(j2.getPresenceTerrain());
				}
				j.ajouterPresence();
				joueurs.add(j);
			}
			else { //Sinon change d'intervalle et ajoute l'enregistrement à la liste 
				currentDate = l[0];
				enregistrements.add(rec);
				rec = new Enregistrement();
			}
			
		}

		enregistrements.add(rec);
		
		System.out.println("intervalle = 50 ms");
		System.out.println("Nombre d'entrées: " + raw_data.size());
		System.out.println("Nombre d'enregistrements: " + enregistrements.size());
		double stats = 0;
		for(Enregistrement e : enregistrements)
			stats += e.size();
		System.out.println("Nombre moyen de déplacements par échelle de temps = " + (stats/enregistrements.size()));
		
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
	
	private Joueur lastDataJoueur(int joueurId)
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
		Float value = null;
		Enregistrement E = this.enregistrements.get(indexEnregistrement);	
		for (Joueur j : E)
		{
			if (j.getId() == idJoueur) value = j.getX_pos();
		}
		
		return value ;
	}

	public Integer testMaxPos(int idJoueur) {
		
		 Joueur J = lastDataJoueur(idJoueur);
		 int [][] terrain = J.getPresenceTerrain();
		 Integer max = 0 ;
		 for (int i=0; i<terrain.length ;i++)
			{
				for(int j = 0 ; j<terrain.length;j++)
				{
					
					if (max <  terrain[i][j]) max =terrain[i][j] ;
					
				}
			}
		 return max;
	}

	public Integer MapCorner(int idJoueur) {
		
		 Joueur J = lastDataJoueur(idJoueur);
		 int [][] terrain = J.getPresenceTerrain();
		return terrain[0][0];
	}
	
	
	
	
	
}
