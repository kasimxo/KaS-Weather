package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class OutputLog {
	
	private static File log;
	private static FileWriter output;
	
	
	public OutputLog() {
		String path = new File("").getAbsolutePath()+"/src/main/java/log.txt";
		path = OsPaths.cleanPath(path);
		this.log = new File(path);
		if(!log.exists()) {
			try {
				log.createNewFile();
				System.err.println("Se ha creado el archivo log.txt");
			} catch (IOException e) {
				System.out.println("No se ha podido crear el archivo log.txt");
				e.printStackTrace();
			}
		}
		
		try {
			OutputLog.output = new FileWriter(log);
			System.out.println("Se ha configurado la salida log.");
		} catch (IOException e) {
			System.out.println("No se ha podido configurar la salida de texto.");
			e.printStackTrace();
		}
	}
	

	/**
	 * This method appends text into the log.txt file.
	 * Every time this method is called, it adds the date
	 * @param 
	 * 	s -> The text we want to output
	 * @throws IOException 
	 */
	public void outputText(String s) {
		//BufferedWriter bw = new BufferedWriter(output);
		String date = Calendar.getInstance().getTime().toString();
		
		try {
			output.append(date+":\n"+s+"\n");
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
