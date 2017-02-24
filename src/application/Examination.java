package application;

import java.util.ArrayList;

public class Examination {
	private String type;
	private Patient patient;
	private ArrayList<Measurement> measurements;
	
	public Examination (String type, Patient patient) {
		this.type = type;
		this.patient = patient;
		this.measurements = new ArrayList<Measurement>();
	}

	public Examination () {

	}
	
	public boolean insert(Measurement m) {
		return measurements.add(m);
	}
	
	public Measurement getIndex(int index) {
		return measurements.get(index);
	}

	
	public String getType () {
		return type;
	}

	public Patient getPatient () {
		return patient;
	}

	public ArrayList<Measurement> getMeasurements () {
		return measurements;
	}

	public void setType (String type) {
		this.type = type;
	}

	public void setPatient (Patient patient) {
		this.patient = patient;
	}

	public void setMeasurements (ArrayList<Measurement> measurements) {
		this.measurements = measurements;
	}
}