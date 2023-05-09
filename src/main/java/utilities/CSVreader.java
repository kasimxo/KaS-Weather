package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
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
		
		String s = new File("").getAbsolutePath() +"/src/main/java/dataBase/mun_codes.csv";
		s = OsPaths.cleanPath(s);
		Path inputPath = Paths.get(s);
		List<String> inputData = Files.readAllLines(inputPath,StandardCharsets.ISO_8859_1);
		System.out.println("hemos pasado el char encoding");
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
	
	public static String getProv(String mun) throws Exception {
		String prov = "";
		String s = new File("").getAbsolutePath() +"/src/main/java/dataBase/mun_codes.csv";
		s = OsPaths.cleanPath(s);
		Path inputPath = Paths.get(s);
		List<String> inputData = Files.readAllLines(inputPath,StandardCharsets.ISO_8859_1);
		System.out.println("hemos pasado el char encoding");
		for (String linea : inputData) {
			String[] splited = linea.split(";");
			if(splited[0].compareTo("CODAUTO")!=0 && splited[4].compareTo(mun)==0) {
				return splited[5];
			}
		}
		return prov;
	}
	
}
