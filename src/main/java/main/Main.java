package main;

import java.awt.Dimension;
import java.awt.Toolkit;
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
import controller.Request;
import kong.unirest.GetRequest;
import kong.unirest.Header;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import view.Delete_Mun_Window;
import view.Insert_Mun_Window;
import view.Main_Window;
import view.Preferences_Window;
import utilities.CSVreader;
import utilities.ConfigFileHandler;
import utilities.Format;
import utilities.JsonHandler;
import utilities.OsPaths;
import utilities.OutputLog;

public class Main {
	
	public static Main_Window mW;
	public static Delete_Mun_Window delMW;
	public static Insert_Mun_Window insMW;
	public static Preferences_Window pW;
	public static OutputLog OL;
	public static File configFile;
	public static String defaultMun;
	
	/**
	 * Some parameters from system for screen display
	 */
	public static Dimension screenSize;
	public static double width;
	public static double height;
	
	/**
	 * La Base de Datos almacena los datos de todas las consultas que realizamos en un determinado d�a 
	 * para evitar realizar consultas duplicadas.
	 */
	public static File dataBase;
	public static ManejaDB mDB;
	public static String buffer;
	
	public static void main(String[] args) {
		OL = new OutputLog();
		readSys();
		
		configFile = ConfigFileHandler.checkConfigFile();

		
		cargarDataBase();
		
		mW = new Main_Window();
		
		delMW = new Delete_Mun_Window();
		
		insMW = new Insert_Mun_Window();
		
		pW = new Preferences_Window();
		
		loadDefaultMun(defaultMun);
		
		mW.setVisible(true);

		
		if(configFile==null) {
			pW.setVisible(true);
		}
		
		if(buffer!=null) {
			mW.setScreen(buffer);
		}
		
	}

	/**
	 * This method will read some system properties
	 */
	private static void readSys() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth();
		height = screenSize.getHeight();
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
	
public static void loadDefaultMun(String mun) {
		int munCode = -1;
		try {
			munCode = CSVreader.munCode(mun);
		} catch (Exception e1) {
			Main.mW.setlbl_Output("No se ha encontrado la ciudad " + mun);
			e1.printStackTrace();
		}
		if(munCode!=-1) {
			String url = Request.getUrlConsulta(munCode);
			String s = Request.getRawData(url);
			if(s!=null) {
				try {
					List<String> formato = Format.rawDataToList(s);
					for (String string : formato) {
						System.out.print(string);
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			} else {
				Main.mW.setlbl_Output("Estos datos no estan disponbles en este momento.");
			}
		}
		
		JsonHandler.toDataBase();
		mW.actualizarMun();
		mW.mostrarView("TEMPERATURA", mun);
	}
	

}
