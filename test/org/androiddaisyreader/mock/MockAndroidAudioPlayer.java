package org.androiddaisyreader.mock;

import java.util.logging.*;
import org.androiddaisyreader.AudioCallbackListener;
import org.androiddaisyreader.AudioPlayer;
import org.androiddaisyreader.model.AudioPlayerState;

public class MockAndroidAudioPlayer implements AudioPlayer {

	Logger log = Logger.getLogger("MockAudioPlayer");
	Integer currentVolume = 0;
	Boolean muted = false;
	Boolean playing = true;
	String currentlyPlaying = null;
	double from = 0f;
	double to = 0f;
	AudioPlayerState state = AudioPlayerState.INITIALIZED;
	
	public void playFile(String fileToPlay) {
		this.currentlyPlaying = fileToPlay;
		log.info("Playing " + fileToPlay);
	}
	
	/**
	 * Android's MediaPlayer does not include a facility to 'stop' at a
	 * particular duration. Instead it plays to the end of the file. Therefore
	 * we want to emulate the behaviour of the Android player in this Mock
	 * audio player.
	 */
	public void playFileSegment(String fileToPlay, double from, double duration) {
		
		if (currentlyPlaying == null) {
			log.info("Starting to play a new file");
			this.state = AudioPlayerState.PLAY_NEW_FILE;
		}

		this.currentlyPlaying = fileToPlay;
		this.from = from;
		
		// Note: if we are able to determine the overall duration of the fileToPlay
		// we could check whether the duration is valid. For now, we'll assume it is.
		
		if (fileToPlay.matches(currentlyPlaying)) {
			if (from >= this.to) { // TODO We should only allow a small tolerance e.g. a milli-second.
				log.info("The player will continue playing the existing audio, without interruption.");
				this.state = AudioPlayerState.CONTINUE_PLAYING_EXISTING_FILE;
			} else {
				log.warning(
						String.format(
								"The player was asked to play earlier in the file than expected." + 
								" Generally the next request %f should be contiguous with the" +
								" end of the previous section %f", from, to));
				this.state = AudioPlayerState.OVERLAPPING_CONTENTS;
			}
		} else {
			log.info("Stop playing the current file and start playing the newly specified file");
			this.state = AudioPlayerState.PLAY_NEW_FILE;
		}
		
		this.to = from + duration;
		log.info(String.format("Playing %s from %f to %f", fileToPlay, from, to));
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
