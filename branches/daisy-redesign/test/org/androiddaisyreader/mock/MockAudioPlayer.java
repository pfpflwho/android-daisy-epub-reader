package org.androiddaisyreader.mock;

import java.util.logging.*;
import org.androiddaisyreader.AudioCallbackListener;
import org.androiddaisyreader.AudioPlayer;

public class MockAudioPlayer implements AudioPlayer {

	Logger log = Logger.getLogger("MockAudioPlayer");
	Integer currentVolume = 0;
	Boolean muted = false;
	Boolean playing = true;
	
	public void playFile(String fileToPlay) {
		log.info("Playing " + fileToPlay);

	}

	public void increaseVolume() {
		currentVolume++;
	}

	public void decreaseVolume() {
		currentVolume--;
	}

	public void toggleMute() {
		muted = !muted;
	}

	public void addCallbackListener(AudioCallbackListener listener) {
		// TODO Auto-generated method stub

	}
	
	public Integer getCurrentVolume() {
		return currentVolume;
	}
	
	public Boolean isMuted() {
		return muted;
	}

	public Boolean isPlaying() {
		return playing;
	}

}
