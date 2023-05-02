package utilities;

import java.io.FileReader;
import java.io.File;
import org.json.simple.parser.JSONParser;

import kong.unirest.json.JSONArray;

public class JsonHandler {

	public static void main(String[] args) 
    {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try {
        	FileReader reader = new FileReader(new File("").getAbsolutePath() + "test.json");
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);
             
            //Iterate over employee array
            //employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );
 
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
	
}
