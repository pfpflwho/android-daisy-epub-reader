package org.androiddaisyreader;

public interface PlayAudioListener {
	/**
	 * Play the audio from an entire file. 
	 * Starts at the beginning and finishes at the end of the file.
	 * 
	 * @param fileToPlay the full filename (including the path)
	 */
	public void playFile(String fileToPlay);
	
	/**
	 * Play the audio from part of a file.
	 * Starts at the time specified in the from parameter. Finishes when the
	 * duration has been reached, or at the end of the file, whichever's sooner.
	 * 
	 * @param fileToPlay the full filename (including the path)
	 * @param from the starting offset in milli-seconds.
	 * @param duration how long to play in milli-seconds.
	 */
	public void playFileSegment(String fileToPlay, double from, double duration);
}
