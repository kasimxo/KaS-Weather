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
import view.Delete_Mun_Window;
import view.Insert_Mun_Window;
import view.Main_Window;
import utilities.CSVreader;
import utilities.OsPaths;
import utilities.OutputLog;

public class Main {
	
	public static Main_Window mW;
	public static Delete_Mun_Window delMW;
	public static Insert_Mun_Window insMW;
	public static OutputLog OL;
	/**
	 * La Base de Datos almacena los datos de todas las consultas que realizamos en un determinado d�a 
	 * para evitar realizar consultas duplicadas.
	 */
	public static File dataBase;
	public static ManejaDB mDB;
	public static String buffer;
	
	public static void main(String[] args) {
		OL = new OutputLog();
		
		cargarDataBase();
		
		mW = new Main_Window();
		mW.setVisible(true);
		
		delMW = new Delete_Mun_Window();
		delMW.setVisible(false);
		
		insMW = new Insert_Mun_Window();
		insMW.setVisible(false);
		
		if(buffer!=null) {
			mW.setScreen(buffer);
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
			OL.outputText("No se ha podido crear la base de datos.");
			System.err.println("No se ha podido crear la base de datos.");
			e.printStackTrace();
		} catch (SQLException e) {
			OL.outputText("No se ha podido conectar con la base de datos.");
			System.err.println("No se ha podido conectar con la base de datos.");
			e.printStackTrace();
		}		
	}

}
