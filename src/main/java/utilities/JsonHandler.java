package utilities;

import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;
import java.io.File;


import org.json.simple.parser.JSONParser;
import utilities.OsPaths;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class JsonHandler {
	
	public static String municipio="";
	
	public static void main(String[] args) {
    }
	/**
	 * This method gets the temperature from a JSON file
	 * @return
	 */
	public static String getTemperatura() {
		//JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try {
        	String path = OsPaths.cleanPath(new File("").getAbsolutePath() + "/src/main/java/test.json");
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
			String output2="<html><body><table><tr><th>Fecha: </th><th>Minima: </th><th>Maxima: </th></tr>";
			JSONObject j2 = (JSONObject) j.get("prediccion");
			JSONArray jA = (JSONArray) j2.get("dia");
			
			for (Object object : jA) {
				JSONObject j3 = new JSONObject(object.toString());
				String f = j3.getString("fecha").toString();
				JSONObject j4 = new JSONObject(j3.get("temperatura").toString());
				String min = j4.getString("minima"); 
				String max = j4.getString("maxima");
				f = f.substring(0,10);
				output2+="<tr><td>"+ f + "</td><td>" + min + "</td><td>" + max + "</td></tr>";
			}
			output2+="</table></body></html>";
			return output2;
			
        } catch (Exception e) {
            e.printStackTrace();
        } 
		return null;
	}
	
}