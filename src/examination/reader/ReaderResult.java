package examination.reader;

import examination.core.Examination;

public class ReaderResult {
	private int linesSkipped;
	private Examination result;
	private int linesRead;
	private String filename;
	private String pid;
	private String error = null;
	
	public ReaderResult (int linesSkipped, Examination result, int linesRead, String filename, String pid) {
		this.linesSkipped = linesSkipped;
		this.result = result;
		this.linesRead = linesRead;
		this.filename = filename;
		this.pid = pid;
	}
	
	public ReaderResult (String error) {
		this.error = error;
	}
	
	public boolean success() {
		return error == null;
	}
	
	public String getError() {
		return error;
	}

	
	public int getLinesSkipped() {
		return linesSkipped;
	}

	public Examination getResult () {
		return result;
	}

	public int getLinesRead () {
		return linesRead;
	}

	public String getFilename () {
		return filename;
	}
	
	public String getPID() {
		return pid;
	}

}
