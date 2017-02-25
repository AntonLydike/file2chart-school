package examination.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import examination.core.*;

public class ExamReader {

	private static final Pattern line_pattern = Pattern.compile("^([0-9A-z:.]+)(?:[\\s]*)([0-9A-z:.]+)(?:[\\s]*)([0-9A-z:.]+)(?:[\\s]*)([0-9A-z:.]+)(?:[\\s]*)([0-9A-z:.]+)(?:[\\s]*)$");


	public static ReaderResult read(String filename) {
		Examination exam = null;

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			
			String type = br.readLine();
			
			if (!type.startsWith("Art:")) {
				return new ReaderResult("Read Error: Wrong filetype!");
			}
			
			type = type.substring(5); // read first line, discard first 5 characters
			String hl7_line = br.readLine().substring(16); // read second line, discard first 16 characters
			String[] hl7_arr = hl7_line.split("\\|"); // split the HL7 at "|"

			// get and separate name first
			String pat_name[] = hl7_trim(hl7_arr[1]).split(",");

			// create patient
			Patient pat = new Patient(
					hl7_trim(hl7_arr[0]),
					pat_name[1].trim(),
					pat_name[0].trim(),
					hl7_trim(hl7_arr[2]),
					hl7_trim(hl7_arr[3])
					);

			// create examination
			exam = new Examination(type, pat);

			// discard line 3 as it is not usefull for us
			br.readLine();


			String line;
			int linesRead = 0, linesSkipped = 0;
			while ((line = br.readLine()) != null) {
				Matcher match = line_pattern.matcher(line);

				// try matching it
				if (!match.matches()) {
					// no match? print error and continue!
					System.out.println("Match error: no match!");
					System.out.println(line);
					linesSkipped++;
					continue;
				} 

				try {
					Measurement item = new Measurement(
							match.group(1), 
							match.group(2), 
							Integer.parseInt(match.group(3)), 
							Integer.parseInt(match.group(4)), 
							Integer.parseInt(match.group(5))
							);


					exam.insert(item);
					linesRead++;

				} catch (NumberFormatException e) {

					System.out.println("Read error: malformed number:");
					System.out.println(e.getLocalizedMessage() + "\n\n");
					linesSkipped++;
					e.printStackTrace();
				} catch (ParseException e) {
					System.out.println("Read error: malformed date:");
					System.out.println(e.getLocalizedMessage() + "\n\n");
					linesSkipped++;
					e.printStackTrace();
				}



			}

			return new ReaderResult(linesSkipped, exam, linesRead, filename, pat.getPid());
		} catch (FileNotFoundException e) {
			System.out.println("File not foud:");
			System.out.println(e.getLocalizedMessage() + "\n\n");
			e.printStackTrace();

			return new ReaderResult("File \"" + filename + "\" not foud");
		} catch (IOException e) {
			System.out.println("Input/Output error:");
			System.out.println(e.getLocalizedMessage() + "\n\n");
			e.printStackTrace();

			return new ReaderResult("Input/Output error!");
		}

	}

	private static String hl7_trim (String hl7_source) {
		return hl7_source.replace('^', ' ').trim();
	}



}
