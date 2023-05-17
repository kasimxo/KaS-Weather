package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.JOptionPane;

import main.Main;

public class ConfigFileHandler {
	
	
	
	public static String readDefaultMun() {	
		try {
			if(Main.configFile==null) {
				return null;
			}
			List<String> configFileData = Files.readAllLines(Main.configFile.toPath());
			String defaultMun = configFileData.get(0).split("=")[1];
			if(defaultMun.compareTo("null")==0) {
				return "";
			}
			return defaultMun;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ha surgido un error leyendo el municipio predeterminado","KaS-Weather", JOptionPane.ERROR_MESSAGE);
			Main.OL.outputText("No se ha podido leer el municipio predeterminado");
			e.printStackTrace();
		}
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
			JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo config","KaS-Weather", JOptionPane.ERROR_MESSAGE);
			Main.OL.outputText("No se ha encontrado el archivo config");
			System.out.println("No se ha encontrado el archivo config");
			return null;
		}
	}
	
	
	
	/**
	 * This method will create the config file if it cannot be found/user chooses to.
	 */
	public static File createConfigFile() {
		String path = new File("").getAbsolutePath() + "/src/main/java/Config.txt";
		path = OsPaths.cleanPath(path);
		File configFile = new File(path);
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
				setConfigFile(configFile);
				return configFile;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "No se ha podido crear el archivo config","KaS-Weather", JOptionPane.ERROR_MESSAGE);
				System.out.println("No se ha podido crear el archivo config");
				e.printStackTrace();
				return null;
			}
		} 
		return null;
		
	}

	public static void setMunInConfigFile(File configFile, String mun) {
		try {
			FileWriter output = new FileWriter(configFile,true);
			setConfigFile(configFile);
			output.append(mun);
			output.flush();
		}catch (IOException e) {
			
		}
	}

	/**
	 * This method will change the value in the config file.
	 * @param configFile
	 */
	public static void setConfigFile(File configFile) {
		try {
			FileWriter output = new FileWriter(configFile);
			output.write("Municipio predeterminado=");
			output.flush();
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido configurar el archivo config","KaS-Weather", JOptionPane.ERROR_MESSAGE);
			System.out.println("No se ha podido configurar el archivo config");
			e.printStackTrace();
		}
	}
}
