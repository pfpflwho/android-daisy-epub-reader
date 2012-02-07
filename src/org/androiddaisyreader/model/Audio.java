package org.androiddaisyreader.model;

public class Audio {

	private String audioFilename;
	private double clipBegin;
	private double clipEnd;
	private String id;

	public Audio(String id, String audioFilename, double clipBegin, double clipEnd) {
		this.id = id;
		this.audioFilename = audioFilename;
		this.clipBegin = clipBegin;
		this.clipEnd = clipEnd;
	}
}
