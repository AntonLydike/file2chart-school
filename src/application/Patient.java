package application;

public class Patient {
	private String pid;
	private String fname;
	private String lname;
	private String station;
	private String insurance;
	
	public Patient (String pid, String fname, String lname, String station, String insurance) {
		this.pid = pid;
		this.fname = fname;
		this.lname = lname;
		this.station = station;
		this.insurance = insurance;
	}

	public Patient () {

	}

	
	public String getPid () {
		return pid;
	}

	public String getFname () {
		return fname;
	}

	public String getLname () {
		return lname;
	}

	public String getStation () {
		return station;
	}

	public String getInsurance () {
		return insurance;
	}

	public void setPid (String pid) {
		this.pid = pid;
	}

	public void setFname (String fname) {
		this.fname = fname;
	}

	public void setLname (String lname) {
		this.lname = lname;
	}

	public void setStation (String station) {
		this.station = station;
	}

	public void setInsurance (String insurance) {
		this.insurance = insurance;
	}
}