package org.androiddaisyreader.model;

public class Audio {

	private String audioFilename;
	private double clipBegin;
	private double clipEnd;
	private String id;

	public void setFilename(String audioFilename) {
		this.audioFilename = audioFilename;
	}

	public void setClipTimings(double clipBegin, double clipEnd) {
		this.clipBegin = clipBegin;
		this.clipEnd = clipEnd;
	}

	public void setId(String id) {
		this.id = id;
	}

}
