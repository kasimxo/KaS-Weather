package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonObject;

import controller.ManejaDB;
import kong.unirest.GetRequest;
import kong.unirest.Header;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import view.Main_Window;
import utilities.CSVreader;
import utilities.OsPaths;

public class Main {
	
	public static Main_Window mW;
	/**
	 * La Base de Datos almacena los datos de todas las consultas que realizamos en un determinado día 
	 * para evitar realizar consultas duplicadas.
	 */
	public static File dataBase;
	public static ManejaDB mDB;
	public static String buffer;
	
	public static void main(String[] args) {
		
		changeSystemOut();
		
		
		cargarDataBase();
		
		mW = new Main_Window();
		mW.setVisible(true);
		
		if(buffer!=null) {
			mW.setScreen(buffer);
		}
		
		mDB.showSchema("");
		
		System.out.println("nepe");
	}

	public static void changeSystemOut() {
		String path = new File("").getAbsolutePath()+"/src/main/java/log.txt";
		path = OsPaths.cleanPath(path);
		File test = new File(path);
		PrintStream stream;
		try {
			stream = new PrintStream(test);
			System.setOut(stream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * This method loads (or creates) an actual data base. 
	 * It will also delete old data bases.
	 */
	private static void cargarDataBase() {
		try {
			mDB = new ManejaDB();
		} catch (IOException e) {
			System.err.println("No se ha podido crear la base de datos.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("No se ha podido conectar con la base de datos.");
			e.printStackTrace();
		}		
	}

}
