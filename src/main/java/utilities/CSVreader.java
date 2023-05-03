package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * This class handles CSV files.
 * @author andres
 *
 */
public class CSVreader {
	
	/**
	 * This method returns the city code.
	 * @param mun
	 * @return
	 * @throws Exception
	 */
	public static int munCode(String mun) throws Exception {
		
		String s = new File("").getAbsolutePath() +"/src/main/java/resources/mun_codes.csv";
		s = OsPaths.cleanPath(s);
		Path inputPath = Paths.get(s);
		List<String> inputData = Files.readAllLines(inputPath);
		HashMap<String,Integer> mapeado = new HashMap<String,Integer>(); 
		for (String linea : inputData) {
			String[] splited = linea.split(";");
			if(splited[0].compareTo("CODAUTO")!=0) {
				String cod = splited[1] + splited[2];
				mapeado.put(splited[4], Integer.parseInt(cod));
			}
		}
		int output = -1;
		output = mapeado.get(mun);
		return output;
	}
	
	
}