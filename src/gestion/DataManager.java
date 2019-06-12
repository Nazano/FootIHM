package gestion;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
		List<String> formats = Arrays.asList("yyyy-MM-dd HH:mm:ss.SS", "yyyy-MM-dd HH:mm:ss");
		List<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		
		for(String[] l : raw_data) {
			
			//Rajoute un zero aux dizaines de milisecondes
			if(l[1].contains(".")) {
				String[] splitted = l[0].split("\\.");
				if(splitted.length == 2 && splitted[1].length() == 1) l[0] = l[0] + "0";
			}
			
			//Récupère toutes les dates
			for (String format : formats) {
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
					dates.add(LocalDateTime.parse(l[0], formatter));
					break;
				} catch (DateTimeParseException e) {

				}
			}
		}
		
		//Crée tous les enregisrements
		Enregistrement rec = new Enregistrement();
		int intrevalleTemps = 50;
		LocalDateTime start = dates.get(0);
		LocalDateTime end = start.plus(intrevalleTemps, ChronoField.MILLI_OF_DAY.getBaseUnit());
		int i = 0;
		while(start.isBefore(dates.get(dates.size() - 1))){
			
			if(dates.get(i).isEqual(start) || (dates.get(i).isAfter(start) && dates.get(i).isBefore(end))) {
				Joueur j = rawDataToJoueur(raw_data.get(i));
				rec.add(j);
				/// ajouter stats 
				Joueur j2 ;
				if ((j2 = lastDataJoueur(j.getId()))!= null)
				{	
					j.setPresenceTerrain(j2.getPresenceTerrain());
				}
				j.ajouterPresence();
				joueurs.add(j);
				i++;
			}
			else {
				enregistrements.add(rec);
				rec = new Enregistrement();
				start = end;
				end = start.plus(intrevalleTemps, ChronoField.MILLI_OF_DAY.getBaseUnit()); 
			}	
			
		}
		/** Debug Code **/
		
		int cont=0;
		
		for(Enregistrement e : enregistrements)
		{	
			cont++;
			for (Joueur j : e)
			{
				if (j.getX_pos() == 65.577209) System.out.println(cont);
			}
			
		}
		/*
		System.out.println(enregistrements.get(10000));
		System.out.println(enregistrements.get(10000).get(5).getX_pos());
		System.out.println(enregistrements.get(10000).get(5));*/
		System.out.println("intervalle = "+ intrevalleTemps + " ms");
		System.out.println("Nombre d'entrées: " + raw_data.size());
		System.out.println("Nombre d'enregistrements: " + enregistrements.size());
		double stats = 0;
		for(Enregistrement e : enregistrements)
			stats += e.size();
		System.out.println("Nombre moyen de déplacements par échelle de temps = " + (stats/enregistrements.size()));
		 /**  **/ 
		
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
