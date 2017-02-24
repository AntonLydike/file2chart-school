package application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Measurement {
	private String date;
	private long unixTime;
	private String time;
	private int systole;
	private int diastole;
	private int pulse;
	
	
	public Measurement (String date, long unixTime, String time, int systole, int diastole, int pulse) {
		this.date = date;
		this.unixTime = unixTime;
		this.time = time;
		this.systole = systole;
		this.diastole = diastole;
		this.pulse = pulse;
	}
	
	public Measurement (String date, String time, int systole, int diastole, int pulse) {
		long unixTime = -1;
		
		String fullDate = date + " " + time;
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		Date dateObj;
		try {
			dateObj = dateFormat.parse(fullDate);
			unixTime = (long)dateObj.getTime()/1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.date = date;
		this.unixTime = unixTime;
		this.time = time;
		this.systole = systole;
		this.diastole = diastole;
		this.pulse = pulse;
	}
	
	

	public Measurement () {

	}

	
	public String getDate () {
		return date;
	}

	public long getUnixTime () {
		return unixTime;
	}

	public String getTime () {
		return time;
	}

	public int getSystole () {
		return systole;
	}

	public int getDiastole () {
		return diastole;
	}

	public int getPulse () {
		return pulse;
	}

	public void setDate (String date) {
		this.date = date;
	}

	public void setUnix_date (long unixTime) {
		this.unixTime = unixTime;
	}

	public void setTime (String time) {
		this.time = time;
	}

	public void setSystole (int systole) {
		this.systole = systole;
	}

	public void setDiastole (int diastole) {
		this.diastole = diastole;
	}

	public void setPulse (int pulse) {
		this.pulse = pulse;
	}
}