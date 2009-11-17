package com.ader;

public class Bookmark {
	private String filename;
	private int nccIndex;
	private int position;

	public Bookmark(String filename, int nccIndex, int position) {
		this.filename = filename;
		this.nccIndex = nccIndex;
		this.position = position;
	}
	
	public String getFilename() {
		return filename;
	}
	public int getNccIndex() {
		return nccIndex;
	}
	public int getPosition() {
		return position;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setNccIndex(int nccIndex) {
		this.nccIndex = nccIndex;
	}
	public void setPosition(int position) {
		this.position = position;
	}
}
