/**
 * 
 */
package org.androiddaisyreader.model;

/**
 * AudioPlayerState represents the internal state of the audio player.
 * 
 * @author jharty
 */
public enum AudioPlayerState {
	INITIALIZED,
	CONTINUE_PLAYING_EXISTING_FILE,
	PLAY_NEW_FILE,
	OVERLAPPING_CONTENTS,
	PLAYING_UNSPECIFIED_AUDIO;
}
