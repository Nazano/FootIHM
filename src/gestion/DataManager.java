package gestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {
	List<String[]> data;
	ArrayList<Enregistrement> enregisrements;
	
	public DataManager(String path) {
		try {
			data = CsvUtils.readCSV(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Trouve l'ensemble des enregistrements contenu dans les données
	 */
	public void findEnregistrements() {
		
	}
	
	/**
	 * Récupérer le nombre d’enregistrements (informations de tous les joueurs à une date et un temps fixe).
	 * @return
	 */
	public Enregistrement getEnregistrement(Date date) {
		
	}
	
	/**
	 * Récupérer les informations d’un enregistrement en fonction de son index (position de
	 * l’enregistrements dans la séquence des enregistrements).
	 * @param index
	 * @return
	 */
	public Enregistrement getEnregistrement(int index) {
			
	}
	
}
