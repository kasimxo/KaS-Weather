package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigFileHandler {
	
	
	
	public static String readDefaultMun() {	
		return null;
	}
	
	/**
	 * This method will look for the config file and return it if found
	 * @return
	 */
	public static File checkConfigFile() {
		String path = new File("").getAbsolutePath() + "/src/main/java/Config.txt";
		path = OsPaths.cleanPath(path);
		File configFile = new File(path);
		if(configFile.exists()) {
			return configFile;
		} else {
			return null;
		}
	}
	
	
	
	/**
	 * This method will create the config file if it cannot be found/user chooses to.
	 */
	private static void createConfigFile() {
		String path = new File("").getAbsolutePath() + "/src/main/java/Config.txt";
		path = OsPaths.cleanPath(path);
		File configFile = new File(path);
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
				setConfigFile(configFile);
			} catch (IOException e) {
				System.out.println("No se ha podido crear el archivo config");
				e.printStackTrace();
			}
		} 
		
	}




	private static void setConfigFile(File configFile) {
		try {
			FileWriter output = new FileWriter(configFile);
			output.write("Municipio predeterminado=");
			output.flush();
			
			
		} catch (IOException e) {
			System.out.println("No se ha podido configurar el archivo config");
			e.printStackTrace();
		}
	}
}
