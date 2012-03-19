package org.androiddaisyreader.mock;

import java.util.logging.*;
import org.androiddaisyreader.AudioCallbackListener;
import org.androiddaisyreader.AudioPlayer;
import org.androiddaisyreader.model.Audio;
import org.androiddaisyreader.model.AudioPlayerState;

public class MockAndroidAudioPlayer implements AudioPlayer {

	Logger log = Logger.getLogger("MockAudioPlayer");
	Integer currentVolume = 0;
	Boolean muted = false;
	Boolean playing = true;
	private Audio currentSegment;
	AudioPlayerState state = AudioPlayerState.INITIALIZED;
	
	/**
	 * Android's MediaPlayer does not include a facility to 'stop' at a
	 * particular duration. Instead it plays to the end of the file. Therefore
	 * we want to emulate the behaviour of the Android player in this Mock
	 * audio player.
	 */
	public void playFileSegment(Audio audioSegment) {
		
		if (this.currentSegment == null) {
			log.info("Starting to play a new file");
			this.state = AudioPlayerState.PLAY_NEW_FILE;
			this.currentSegment = audioSegment;
			return;
		}

		// Note: if we are able to determine the overall duration of the fileToPlay
		// we could check whether the duration is valid. For now, we'll assume it is.
		
		if (currentSegment.getAudioFilename().matches(audioSegment.getAudioFilename())) {
			double newClipStartsAt = audioSegment.getClipBegin();
			double previousClipEndsAt = currentSegment.getClipEnd();
			SegmentTimeInfo interval = SegmentTimeInfo.compareTimesForAudioSegments(
					newClipStartsAt, previousClipEndsAt);
			switch(interval) {
			case CONTIGUOUS:
				log.info("The player will continue playing the existing audio, without interruption.");
				this.state = AudioPlayerState.CONTINUE_PLAYING_EXISTING_FILE;
				break;
			case GAP:
				log.warning(String.format("There is a gap between the audio segments, last" +
											"sergment finished at %f, next segment starts at %f",
											newClipStartsAt, previousClipEndsAt));
				break;
			case OVERLAPPING:
				log.warning(
						String.format(
								"The player was asked to play earlier in the file than expected." + 
										" Generally the next request %f should be contiguous with the" +
										" end of the previous section %f", newClipStartsAt, previousClipEndsAt));
				this.state = AudioPlayerState.OVERLAPPING_CONTENTS;
				break;
			}
		} else {
			log.info("Stop playing the current file and start playing the newly specified file");
			this.state = AudioPlayerState.PLAY_NEW_FILE;
		}
		currentSegment = audioSegment;
		playing = true;
		log.info(String.format("Playing %s from %f to %f", currentSegment.getAudioFilename(), 
								currentSegment.getClipBegin(), currentSegment.getClipEnd()));
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
