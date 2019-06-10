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

import app.Enregistrement;
import app.Joueur;

public class DataManager {
	private List<String[]> raw_data;
	private ArrayList<Enregistrement> enregisrements = new ArrayList<Enregistrement>();
	private HashSet<Joueur> joueurs = new HashSet<Joueur>(); //Liste des joueurs dans leur état initial
	
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
		
		while(start.isBefore(dates.get(dates.size() - 1 ))) {
			
			if(dates.get(i).isEqual(start) || (dates.get(i).isAfter(start) && dates.get(i).isBefore(end))) {
				Joueur j = rawDataToJoueur(raw_data.get(i));
				rec.add(j);
				joueurs.add(j);
				if(dates.get(i).isEqual(start))
				i++;
			}
			else {
				enregisrements.add(rec);
				rec = new Enregistrement();
				start = end;
				end = start.plus(intrevalleTemps, ChronoField.MILLI_OF_DAY.getBaseUnit()); 
			}	
			
		}
		
		/*System.out.println("intervalle = "+ intrevalleTemps + " ms");
		System.out.println("Nombre d'entrées: " + raw_data.size());
		System.out.println("Nombre d'enregistrements: " + enregisrements.size());
		double stats = 0;
		for(Enregistrement e : enregisrements)
			stats += e.size();
		System.out.println("Nombre moyen de déplacements par échelle de temps = " + (stats/enregisrements.size()));
		*/
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
