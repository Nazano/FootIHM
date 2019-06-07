package gestion;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CsvUtils {

	private static final char DEFAULT_SEPARATOR = ';';
	
	public static List<String[]> readCSV(String path) throws IOException {
		File file = new File(path);
		FileReader fr = new FileReader(file);
		CSVParser csvp = new CSVParserBuilder().withSeparator(DEFAULT_SEPARATOR).build();
		CSVReader csvR = new CSVReaderBuilder(fr).withCSVParser(csvp).build();
		
		List<String[] > data = new ArrayList<String[] >();
		String[] nextLine = null;
		while ((nextLine = csvR.readNext()) != null) {
		    int size = nextLine.length;

		    // ligne vide
		    if (size == 0) {
		        continue;
		    }
		    String debut = nextLine[0].trim();
		    if (debut.length() == 0 && size == 1) {
		        continue;
		    }

		    // ligne de commentaire
		    if (debut.startsWith("#")) {
		        continue;
		    }
		    data.add(nextLine);
		}
		data.remove(0); //Supprime l'entête
		return data;
	}
}
