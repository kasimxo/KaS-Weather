package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import main.Main;
import utilities.CSVreader;
import utilities.ConfigFileHandler;
import utilities.OsPaths;

/**
 * This class handles all interaction with the data base.
 * @author Kasimxo
 *
 */
public class ManejaDB {
	
	private String dataBaseName;
	private Connection c;
	private Statement sent;
	private boolean newDataBase;
	
	public ManejaDB() throws IOException, SQLException {
		this.newDataBase=false;
		checkActualDataBase();
		String pathDataBase = new File("").getAbsolutePath()+"/src/main/java/dataBase/"+dataBaseName;
		pathDataBase = OsPaths.cleanPath(pathDataBase);
		this.c = DriverManager.getConnection("jdbc:sqlite:" + pathDataBase);
		this.sent = c.createStatement();
		if(newDataBase) {
			createDataBaseSchema();
		}
		Main.defaultMun = ConfigFileHandler.readDefaultMun();
		
	}
	/**
	 * This method will insert the values given in a String into the specified table.
	 * If the String does not match the correct structure or violates a restriction, it will fail.
	 * @param tableName
	 * @throws SQLException 
	 */
	public void insertValues(String tableName, String values) throws SQLException {
		Statement sentencia = c.createStatement();
		String sql = "Insert into " +tableName+" values (" +values+");";
		if(sentencia.execute(sql)) {
			System.out.println("Se han introducido los datos: "+values+" con éxito en la tabla: "+tableName+".");
			Main.OL.outputText("Se han introducido los datos: "+values+" con éxito en la tabla: "+tableName+".");
		}
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
		System.out.println("Buscamos archivo: " + fileName);
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
		List<File> deletes = new ArrayList<File>();
		for (File file : arrayDirs) {
			if(file.getName().compareTo(fileName)==0) {
				Main.OL.outputText("Se ha cargado la base de datos con exito.");
				System.out.println("Se ha cargado la base de datos con exito.");
				output=file;
			} else {
				deletes.add(file);
			}
		}
		for (File file : deletes) {
			String oldName = file.getName();
			if(file.delete()) {
				Main.OL.outputText("Se ha eliminado el archivo "+oldName+".");
				System.out.println("Se ha eliminado el archivo "+oldName+".");
			} else {
				Main.OL.outputText("No se ha podido eliminar el archivo "+oldName+".");
				System.out.println("No se ha podido eliminar el archivo "+oldName+".");
				JOptionPane.showMessageDialog(null, "No se ha podido eliminar el archivo "+oldName,"KaS-Weather", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(output!=null) {
			this.dataBaseName=output.getName();
			return output;
		}
		Main.OL.outputText("No se ha encontrado la base de datos.");
		System.err.println("No se ha encontrado la base de datos.");
		Main.buffer="Hoy no has realizado ninguna consulta.";
		newDataBase=true;
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
		Main.OL.outputText("Se ha creado la base de datos.");
		System.out.println("Se ha creado la base de datos.");
		this.dataBaseName=file.getName();
		return file;
	}

	/**
	 * This method will create all the tables inside the data base.
	 * If you want to see the structure of the data base, check the schema.
	 */
	private void createDataBaseSchema() {
		File sqlSchema = new File(new File("").getAbsolutePath()+ "/src/main/java/dataBase/DataBaseSchema.sql");
		String buffer = "";
		try {
			Scanner sc = new Scanner(sqlSchema);
			
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				if(line.length()<1||line.charAt(0)=='-'&&line.charAt(1)=='-') {
					//Here we ignore empty lines or comments
				} else {
					buffer+=line;
					if(line.charAt(line.length()-1)==';') {
						sent.execute(buffer);
						buffer="";
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			Main.OL.outputText("No se ha podido encontrar el esquema de la base de datos.");
			System.err.println("No se ha podido encontrar el esquema de la base de datos.");
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ha surgido un error durante la creacion de la estructura de la base de datos","KaS-Weather", JOptionPane.ERROR_MESSAGE);
			Main.OL.outputText("Ha surgido un error durante la creacion de la estructura de la base de datos.");
			Main.OL.outputText("La ultima sentencia ha sido:\n"+buffer);
			System.out.println("Ha surgido un error durante la creacion de la estructura de la base de datos.");
			System.out.println("La ultima sentencia ha sido:\n"+buffer);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This function will check the data base for tables.
	 * @return
	 * 	<ul><li>A list composed of table names</li></ul>
	 */
	public List<String> showAllTables() {
		List<String> tablas = new ArrayList<String>();
		
		try {
			System.out.println(c.getClientInfo(dataBaseName));
			Statement sentencia = c.createStatement();
			String sql = "SELECT * FROM sqlite_master where type = \"table\";";
			ResultSet result = sentencia.executeQuery(sql);
			while (result.next()) {
				tablas.add(result.getString(2));
				System.out.println(result.getString(2));
			}
		} catch (SQLException SqlE) {
			tablas.add("No hay tablas para mostar");
		}
		return tablas;
	}
	
	/**
	 * This function will check the data base for views.
	 * @return
	 * 	<ul><li>A list composed of views names</li></ul>
	 */
	public List<String> showAllViews() {
		List<String> tablas = new ArrayList<String>();
		
		try {
			System.out.println(c.getClientInfo(dataBaseName));
			Statement sentencia = c.createStatement();
			String sql = "SELECT * FROM sqlite_master where type = \"view\";";
			ResultSet result = sentencia.executeQuery(sql);
			while (result.next()) {
				tablas.add(result.getString(2).replace('_', ' '));
				System.out.println(result.getString(2));
			}
		} catch (SQLException SqlE) {
			tablas.add("No hay tablas para mostar");
		}
		return tablas;
	}
	
	/**
	 * This method will return all the view content including column headers.
	 * If a cell is empty, it will show <b>'null'</b>.
	 * @param
	 * 	viewName -> The name of the table to show.
	 * @return
	 * 	List<String> -> A list where every String is a full row separated by <b>';'</b>
	 */
	public List<String> showViewContent(String viewName) {
		return showTableContent(viewName);
	}
	
	/**
	 * This method will return all the table content including column headers.
	 * If a cell is empty, it will show <b>'null'</b>.
	 * @param
	 * 	tableName -> The name of the table to show.
	 * @return
	 * 	List<String> -> A list where every String is a full row separated by <b>';'</b>
	 */
	public List<String> showTableContent(String tableName) {
		List<String> table = new ArrayList<String>();
		try {
			System.out.println(tableName);
			
			Statement sentencia = c.createStatement();
			String sql = "Select * from "+tableName+";";
			ResultSet result = sentencia.executeQuery(sql);
			ResultSetMetaData rsmd = result.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			String labels = "";
			for(int i = 1; i<=columnsNumber; i++)  {
				String label = rsmd.getColumnLabel(i)+" ";
				labels+=label;
			}
			table.add(labels);
			
			//A partir de aquí dará error si la tabla está vacía.
			while(result.next()) {
				String linea = "";
				for(int i = 1; i<=columnsNumber; i++)  {
					String resultado = result.getObject(i)+" ";
					linea +=resultado;
				}		

				table.add(linea);
			}
			
		} catch (SQLException SqlE) {
			Main.OL.outputText("La tabla "+tableName+" esta vacía.");
			System.out.println("La tabla "+tableName+" esta vacía.");
		}
		return table;
	}
	
	public List<String> showFromMun(String selectedView, String munName) {
		List<String> table = new ArrayList<String>();
		try {
			System.out.println(selectedView);
			
			Statement sentencia = c.createStatement();
			String sql = "Select * from "+selectedView+" where Municipio=\""+munName+"\";";
			ResultSet result = sentencia.executeQuery(sql);
			ResultSetMetaData rsmd = result.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			String labels = "";
			for(int i = 1; i<=columnsNumber; i++)  {
				String label = rsmd.getColumnLabel(i)+" ";
				labels+=label;
			}
			table.add(labels);
			
			//A partir de aquí dará error si la tabla está vacía.
			while(result.next()) {
				String linea = "";
				for(int i = 1; i<=columnsNumber; i++)  {
					String resultado = result.getObject(i)+" ";
					linea +=resultado;
				}
				table.add(linea);
			}
			
		} catch (SQLException SqlE) {
			Main.OL.outputText("La tabla "+selectedView+" esta vacia.");
			System.out.println("La tabla "+selectedView+" esta vacia.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;	}
	
	/**
	 * This method will return all entries in a specified table column
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public List<String> showTableColumn(String tableName, String columnName) {
		List<String> content = new ArrayList<String>();
		try {
			System.out.println(tableName);
			
			Statement sentencia = c.createStatement();
			String sql = "Select "+columnName+" from "+tableName+";";
			ResultSet result = sentencia.executeQuery(sql);
			
			//A partir de aquí dará error si la tabla está vacía.
			while(result.next()) {
				content.add(result.getString(1));
			}
			
		} catch (SQLException SqlE) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error tratando de recuperar los datos de la tabla","KaS-Weather", JOptionPane.ERROR_MESSAGE);
			Main.OL.outputText("Ha ocurrido un error tratando de recuperar los datos de la tabla '" + tableName + "' columna '" + columnName + "'.");
			System.out.println("Ha ocurrido un error tratando de recuperar los datos de la tabla '" + tableName + "' columna '" + columnName + "'.");
		}
		return content;
	}
	
	/**
	 * This method will return all entries in a specified table column as an array
	 * <b>WILL ONLY WORK IF BOOLEAN IS TRUE</b>
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public String[] showTableColumn(String tableName, String columnName, boolean array) {
		if (array) {
			String[] content;
			try {
				System.out.println(tableName);
				
				Statement sentencia = c.createStatement();
				String sql = "Select "+columnName+" from "+tableName+";";
				ResultSet result = sentencia.executeQuery(sql);
				int rowCount = result.getRow();
				result.first();
				content = new String[rowCount];
				int index = 0;
				
				//A partir de aquí dará error si la tabla está vacía.
				while(result.next() && index<=content.length) {
					content[index] = result.getString(1);
					index++;
				}
				return content;
			} catch (SQLException SqlE) {
				SqlE.printStackTrace();
				Main.OL.outputText("Ha ocurrido un error tratando de recuperar los datos de la tabla '" + tableName + "' columna '" + columnName + "'.");
				System.out.println("Ha ocurrido un error tratando de recuperar los datos de la tabla '" + tableName + "' columna '" + columnName + "'.");
			}
			return null;
		} else {
			return null;
		}
	}
 	
	/**
	 * This function will show the schema of all the tables inside the data base.
	 * It's intended for debugging.
	 */
	public void showSchema() {
		List<String> tablas = showAllTables();
		
		try {
			for (String nombreTabla : tablas) {
				System.out.println(nombreTabla);
				
				Statement sentencia = c.createStatement();
				String sql = "PRAGMA table_info("+nombreTabla+");";
				
				ResultSet result = sentencia.executeQuery(sql);
				ResultSetMetaData rsmd = result.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				System.out.println("NumColumna\tNombreTabla\tTipoDato\tNotNull");
				while (result.next()) {
					String labels = "   ";
					
					for(int i = 1; i<columnsNumber; i++) {
						labels += result.getString(i)+"      \t";
					}
					
					System.out.println(labels);
				}
				
				
			}
			
		} catch (SQLException SqlE) {
			Main.OL.outputText("Error durante el esquema.");
			System.out.println("Error durante el esquema.");
		}
	}
	
	/**
	 * This method will delete all data of a selected municipio from every table.
	 * @param munName
	 */
	public void deleteMun(String munName) {
		try {
			
			Statement sentencia = c.createStatement();
			String munCode = ""+CSVreader.munCode(munName);
			String sql = "DELETE FROM CODES WHERE Cod_mun=\""+munCode+"\";";
			int result = sentencia.executeUpdate(sql);
			if(result>0) {
				Main.OL.outputText("Se han borrado los datos del municipio con codigo "+munCode);
				System.out.println("Se han borrado los datos con éxito.");
			}
			
		} catch (SQLException SqlE) {
			Main.OL.outputText("No se han podido borrar los datos seleccionados.");
			System.err.println("No se han podido borrar los datos seleccionados.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se ha encontrado el municipio especificado","KaS-Weather", JOptionPane.ERROR_MESSAGE);
			Main.OL.outputText("No se ha encontrado el municipio especificado");
			System.err.println("No se ha encontrado el municipio especificado.");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This function will show the schema of a specific table.
	 * It's intended for debugging.
	 */
	public void showSchema(String tableName) {
		
		try {
			System.out.println(tableName);
			
			Statement sentencia = c.createStatement();
			String sql = "PRAGMA table_info("+tableName+");";
			
			ResultSet result = sentencia.executeQuery(sql);
			ResultSetMetaData rsmd = result.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			System.out.println("NumColumna\tNombreTabla\tTipoDato\tNotNull");
			while (result.next()) {
				String labels = "   ";
				
				for(int i = 1; i<columnsNumber; i++) {
					labels += result.getString(i)+"      \t";
				}
				
				System.out.println(labels);
			}
			
		} catch (SQLException SqlE) {
			Main.OL.outputText("Error tratando de mostrar el esquema de la tabla \"" + tableName+"\".\nNo existe esa tabla o no se ha encontrado.");
			System.out.println("Error tratando de mostrar el esquema de la tabla \"" + tableName+"\".\nNo existe esa tabla o no se ha encontrado.");
		}
	}
}
