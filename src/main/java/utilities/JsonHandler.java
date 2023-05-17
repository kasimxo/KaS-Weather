package utilities;

import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.JOptionPane;

import java.io.File;


import org.json.simple.parser.JSONParser;
import utilities.OsPaths;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import main.Main;

/**
 * This class handles the JSON response from the server and puts all the data into the data base
 * @author Alumno
 *
 */
public class JsonHandler {
	
	public static String municipio="";
	
	public static void main(String[] args) {
    }
	
	/**
	 * Here we make sure all data in the JSON file goes into the data base
	 */
	public static void toDataBase() {
		sqlInsertIntoDatabase();
		Main.OL.outputText("Se han insertado todos los datos en la base de datos");
		System.out.println("Se han insertado todos los datos en la base de datos");
	}
	
	
	/**
	 * This method will read the JSON file and insert all the corresponding Temperature data into de data base.
	 * Good luck trying to understand it, not my proudest work.
	 */
	public static void sqlInsertIntoDatabase() {
		//JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        municipio = "";
        try {
        	String path = OsPaths.cleanPath(new File("").getAbsolutePath() + "/src/main/java/Resultado_Consulta.json");
        	Reader reader = new FileReader(path);
        	Object obj = jsonParser.parse(reader);
        	/**
        	 * We use JSONArray due to how we receive the data:
        	 * since it starts with [...] its a json array
        	 * if insted it were a {...} it would be a json object
        	 */
        	JSONArray array = new JSONArray(obj.toString());
			JSONObject j = (JSONObject) array.get(0);
			municipio = j.get("nombre").toString();
			JSONObject j2 = (JSONObject) j.get("prediccion");
			JSONArray jA = (JSONArray) j2.get("dia");
			String valuesTemperature = "";
			String codmun = "" + CSVreader.munCode(municipio);
			String prov = "" + CSVreader.getProv(municipio);
			try {
				Main.mDB.insertValues("CODES", codmun+",\""+prov+"\",\""+municipio+"\"");
				//Here we are going through all days
				for (Object object : jA) {
					//Here we are inside a specific day
					JSONObject j3 = new JSONObject(object.toString());
					String f = j3.getString("fecha").toString();
					f = f.substring(0,10);
					
					JSONObject Temp = new JSONObject(j3.get("temperatura").toString());
					String min = Temp.getString("minima"); 
					String max = Temp.getString("maxima");
					
					JSONObject sensTerm = new JSONObject(j3.get("sensTermica").toString());
					String maxRel = sensTerm.getString("maxima");
					String minRel = sensTerm.getString("minima");
					
					valuesTemperature+=codmun+",\""+f + "\"," + max + "," + min + "," +maxRel+","+minRel;
					Main.mDB.insertValues("TEMPERATURE", valuesTemperature);
					valuesTemperature="";
					
					JSONArray wind = new JSONArray(j3.get("viento").toString());
					JSONObject windD = (JSONObject) wind.get(0);
					String direccion =""+ windD.getString("direccion");
					String vel = ""+windD.getString("velocidad");
					
					JSONArray rachaMaxArr = new JSONArray(j3.get("rachaMax").toString());
					JSONObject rachaMaxObj = (JSONObject) rachaMaxArr.get(0);
					String rachamax = ""+rachaMaxObj.getString("value");
					
					Main.mDB.insertValues("WIND", "\""+codmun+"\",\""+f+"\",\""+direccion+"\",\""+vel+"\",\""+rachamax+"\"");
					
					JSONObject humidityObj = new JSONObject(j3.get("humedadRelativa").toString());
					String humidityMax = ""+humidityObj.getString("maxima");
					String humidityMin = ""+humidityObj.getString("minima");
					String valuesHumidity = "\""+codmun+"\",\""+f+"\",\""+humidityMax+"\",\""+humidityMin+"\"";
					
					Main.mDB.insertValues("HUMIDITY", valuesHumidity);
					
					JSONArray precipitationArr = new JSONArray(j3.get("probPrecipitacion").toString());
					JSONObject precipitationObj = (JSONObject) precipitationArr.get(0);
					String valorPrecipitation = ""+precipitationObj.getString("value");
					String valuesPrecipitation = "\""+codmun+"\",\""+f+"\",\""+valorPrecipitation+"\"";
					
					Main.mDB.insertValues("PRECIPITATION", valuesPrecipitation);
					
					JSONArray skyArr = new JSONArray(j3.get("estadoCielo").toString());
					JSONObject skyObj = (JSONObject) skyArr.get(0);
					String valor = ""+skyObj.getString("value");
					String descripcion = ""+skyObj.getString("descripcion");
					String uvMax = ""+j3.getString("uvMax");
					String valuesSky = "\""+codmun+"\",\""+f+"\",\""+valor+"\",\""+descripcion+"\",\""+uvMax+"\"";
					
					Main.mDB.insertValues("SKY", valuesSky);
					
					JSONArray snowArr = new JSONArray(j3.get("cotaNieveProv").toString());
					JSONObject snowObj = (JSONObject) snowArr.get(0);
					String valorSnow = ""+snowObj.getString("value");
					String valuesSnow = "\""+codmun+"\",\""+f+"\",\""+valorSnow+"\"";
					
					Main.mDB.insertValues("SNOW", valuesSnow);
				}
			} catch (SQLException e) {
				Main.OL.outputText("Ya se habia hecho esa consulta hoy");
				System.out.println("Ya se habia hecho esa consulta hoy.");
			}
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Ha ocurrido un error leyendo la respuesta del servidor","KaS-Weather", JOptionPane.ERROR_MESSAGE);
        	Main.OL.outputText("Se ha producido un error leyendo la respuesta del servidor");
        	System.out.println("Se ha producido un error leyendo la respuesta del servidor.");
            e.printStackTrace();
        } 
	}
	
	
	/**
	 * This method will return the value of a JSON Object key
	 * @param key
	 * @return
	 */
	public static String getValue(JSONObject JObject, String key) {
		try {
			return JObject.getString(key).toString();
		} catch (JSONException e) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error tratando de recuperar los datos del archivo JSON","KaS-Weather", JOptionPane.ERROR_MESSAGE);
			Main.OL.outputText("Se ha producido un error al tratar de recuperar los datos del archivo JSON");
			System.out.println("Se ha producido un error al tratar de recuperar los datos del archivo JSON.");
			return null;
		}
	}
	
}
