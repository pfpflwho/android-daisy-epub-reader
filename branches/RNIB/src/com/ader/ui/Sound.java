package com.ader.ui;

public class Sound {
	byte[] data;
	int sampleRate;
	int bitsPerSample;
	int channels;
	String message;
	
	public byte[] getData(){
		return data;
	}

	public void setData(byte[] soundbytes) {
		this.data = soundbytes;
	}

	public int getSampleRate(){
		return sampleRate;
	}

	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}
	
	public int getBitsPerSmple(){
		return bitsPerSample;
	}

	public void setBitsPerSample(int bitsPerSample) {
		this.bitsPerSample = bitsPerSample;
	}
	
	public int getChannels(){
		return channels;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}

	public int ByteCount(){
		return data.length;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
}
