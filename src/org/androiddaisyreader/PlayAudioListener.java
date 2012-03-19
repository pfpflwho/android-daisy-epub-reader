package org.androiddaisyreader;

import org.androiddaisyreader.model.Audio;

public interface PlayAudioListener {
	/**
	 * Play the audio from part of a file.
	 * Starts at the time specified in the from parameter. Finishes when the
	 * duration has been reached, or at the end of the file, whichever's sooner.
	 * 
	 * @param segment The audio segment to play
	 */
	public void playFileSegment(Audio segment);
}
