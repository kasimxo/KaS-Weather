package controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;

import main.Main;

public class ManejaDB {
	
	private String dataBaseName;
	private Connection c;
	
	public ManejaDB() throws IOException, SQLException {
		checkActualDataBase();
		this.c = DriverManager.getConnection("jdbc:sqlite:D:\\sqlite\\" + dataBaseName);
	}
	
	
	/**
	 * This method checks if a database exists with today (sysdate).
	 * If it does <b>NOT</b> exists, it calls a method to create it.
	 * @return
	 * 		<b>File</b> -> The actual data base file<br/>
	 * @throws IOException 
	 */
	public File checkActualDataBase() throws IOException {
		String[] splited = Calendar.getInstance().getTime().toString().split(" ");
		String fileName = splited[0]+splited[2]+splited[5]+".db";
		File dir = new File( new File("").getAbsoluteFile()+"/src/main/java/dataBase");
		//Create a File Filter for .db files
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".db");
		    };
		};
		//Create a list of files with a specific filter
		File[] arrayDirs = dir.listFiles(filter);
		File output=null;
		//We delete old data base files.
		File delete=null;
		for (File file : arrayDirs) {
			if(delete!=null) {
				String oldName = delete.getName();
				if(delete.delete()) {
					System.out.println("Se ha eliminado el archivo "+oldName+".");
				} else {
					System.out.println("No se ha podido eliminar el archivo "+oldName+".");
				}
			}
			
			if(file.getName().compareTo(fileName)==0) {
				System.out.println("Se ha cargado la base de datos con éxito.");
				output=file;
			}
			delete=file;
		}
		if(output!=null) {
			this.dataBaseName=output.getName();
			return output;
		}
		
		System.err.println("No se ha encontrado la base de datos.");
		Main.buffer="Hoy no has realizado ninguna consulta.";
		return createDataBase(dir);
	}
	
	/**
	 * This method creates a data base file ".db" with strict name format
	 * <b>DayMMYYYY.db</b>
	 * @return
	 * 	<b>File</b> -> The data base file created
	 * @throws IOException 
	 */
	public File createDataBase(File dir) throws IOException {
		String[] splited = Calendar.getInstance().getTime().toString().split(" ");
		String fileName = splited[0]+splited[2]+splited[5]+".db";
		File file = new File(dir+"/"+fileName);
		file.createNewFile();
		System.out.println("Se ha creado la base de datos.");
		this.dataBaseName=file.getName();
		return file;
	}
}
