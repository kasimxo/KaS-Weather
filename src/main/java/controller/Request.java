package controller;

import java.net.URLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonObject;

import kong.unirest.GetRequest;
import kong.unirest.Header;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * This class handles using AEMET API
 * @author andres
 *
 */
public class Request {
	private static final String key = "?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXNpbXhvQGdtYWlsLmNvbSIsImp0aSI6ImFmMGZkZDMyLWQwODMtNGJjOC05NWQ0LTdjOThhYTY2NTljYSIsImlzcyI6IkFFTUVUIiwiaWF0IjoxNjgyOTY5NzI1LCJ1c2VySWQiOiJhZjBmZGQzMi1kMDgzLTRiYzgtOTVkNC03Yzk4YWE2NjU5Y2EiLCJyb2xlIjoiIn0.pKricJ8G91gvl2TtE9D0bnfhPnIBFo6V3jWXxPYjU0E";

	public void Request() {
		
	}
	
	
	
	public static String getUrlConsulta(int munCode) {

		HttpResponse response = Unirest.get("https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" + munCode +key)
		  .header("cache-control", "no-cache")
		  .asString();

		System.out.println(response.getStatus());
	
		String s = (String) response.getBody();
		String[] splitedLines = s.split("\n");
		String dataLink = splitedLines[3].split(" ")[4];
		dataLink = dataLink.substring(1, dataLink.length()-2);
	
		return dataLink;
	}
	
	
	/**
	 * This methods connects to a given URL and returns the content as a String, without any formatting-
	 * @param dataLink
	 * @return
	 */
	public static String getRawData(String dataLink) {
		
		try {
			//Primero conectamos con la url
			URL url = new URL(dataLink);
			URLConnection urlCon = url.openConnection();
		
			//Ahora establecemos un scanner del contenido de la página
			Scanner sc = new Scanner(urlCon.getInputStream());
			String rawData="";
			//Aquí hacemos un print en pantalla del contenido simplemente para ver que funciona
			while(sc.hasNext()) {
				rawData+=sc.next();
			}
			return rawData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
