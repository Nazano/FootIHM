package gestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.Enregistrement;

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
	 * Trouve l'ensemble des enregistrements contenu dans les donnÃ©es
	 */
	public void findEnregistrements() {
		
	}
	
	/**
	 * ReÌ�cupeÌ�rer le nombre dâ€™enregistrements (informations de tous les joueurs aÌ€ une date et un temps fixe).
	 * @return
	 */
	public Enregistrement getEnregistrement(Date date) {
		return null;
	}
	
	/**
	 * ReÌ�cupeÌ�rer les informations dâ€™un enregistrement en fonction de son index (position de
	 * lâ€™enregistrements dans la seÌ�quence des enregistrements).
	 * @param index
	 * @return
	 */
	public Enregistrement getEnregistrement(int index) {
		 return null;
	}
	
}
