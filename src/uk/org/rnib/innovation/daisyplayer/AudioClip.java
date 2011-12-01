package uk.org.rnib.innovation.daisyplayer;

import java.io.Serializable;

public class AudioClip implements Serializable{
	private Audio audio;
	
	public String getSrc(){
		return audio.getSrc();
	}
	
	public double getClipBegin(){
		return audio.getClipBegin();
	}
	
	public double getClipEnd(){
		return audio.getClipEnd();
	}
	
	public AudioClip(Audio audio){
		this.audio = audio;
	}
	
	public String toString(){
		return audio.toString();
	}
}
