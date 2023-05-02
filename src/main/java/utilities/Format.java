package utilities;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * This class gives a format to raw data stracted from AEMET API
 * @author andres
 *
 */
public class Format {
	
	public static List<String> rawDataToList(String rawData) throws FileNotFoundException {
		
		String[] splited = rawData.split(",");
		
		File test = new File(new File("").getAbsolutePath()+"/src/main/java/test.txt" );
		PrintStream stream = new PrintStream(test);
		System.setOut(stream);
		
		
		
		List<String> output = new ArrayList<String>();

		for (String string : splited) {
			output.add(string);
		}
		
		for (String string : output) {
			System.out.println(string);
		}
		stream.close();
		return output;
	}
	
}
