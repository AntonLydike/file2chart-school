package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class MedFileReader {
	
	private static final Pattern line_pattern = Pattern.compile("^([0-9A-z:.]+(?:[\\s]*))([0-9A-z:.]+(?:[\\s]*))([0-9A-z:.]+(?:[\\s]*))([0-9A-z:.]+(?:[\\s]*))([0-9A-z:.]+(?:[\\s]*))$");
	
	public static Examination read(String filename) {
		Examination exam = null;
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String type = br.readLine().substring(5); // read first line, discard first 5 characters
			String hl7_line = br.readLine().substring(16); // read second line, discard first 16 characters
			String[] hl7_arr = hl7_line.split("|"); // split the HL7 at "|"
			
			// get and separate name first
			String pat_name[] = hl7_trim(hl7_arr[1]).split(",");
			
			// create patient
			Patient pat = new Patient(
						hl7_trim(hl7_arr[0]),
						pat_name[1],
						pat_name[0],
						hl7_trim(hl7_arr[2]),
						hl7_trim(hl7_arr[3])
					);
			
			// create examination
			exam = new Examination(type, pat);
			
			// discard line 3 as it is not usefull for us
			br.readLine();
			
			
		    String line;
		    while ((line = br.readLine()) != null) {
		    	line_pattern.matcher(line);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exam;
	}
	
	private static String hl7_trim (String hl7_source) {
		return hl7_source.replace('^', ' ').trim();
	}
	
	
	
}
