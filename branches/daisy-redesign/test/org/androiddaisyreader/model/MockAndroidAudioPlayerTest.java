package org.androiddaisyreader.model;

import org.androiddaisyreader.AudioPlayer;
import org.androiddaisyreader.mock.MockAndroidAudioPlayer;

import junit.framework.TestCase;

public class MockAndroidAudioPlayerTest extends TestCase {

	private static final String AUDIO_PLAYER_STATE_INCORRECT = "The Audio Player State is incorrect";
	private MockAndroidAudioPlayer mockPlayer;
	private Audio initialSegment;
	private Audio contiguousSegment;
	private Audio gapAfterContiguousSegments;
	private Audio overlapWithInitialSegment;
	private Audio newAudioFilename;

	@Override
	protected void setUp() {
		mockPlayer = new MockAndroidAudioPlayer();
		initialSegment = new Audio("initial", "file1.mp3", 0.0f, 1.234f);
		contiguousSegment = new Audio("contiguous", "file1.mp3", 1.234f, 7.983f);
		gapAfterContiguousSegments = new Audio("gap", "file1.mp3", 15.001f, 26.771f);
		overlapWithInitialSegment = new Audio("overlap", "file1.mp3", 0.9f, 2.086f);
		newAudioFilename = new Audio("newfile", "new.mp3", 0.0f, 11.589f);
	}

	public void testContiguousSegmentsAreRecognisedAsContiguous() {
		mockPlayer.playFileSegment(initialSegment);
		assertEquals(AUDIO_PLAYER_STATE_INCORRECT, 
				AudioPlayerState.PLAY_NEW_FILE, 
				mockPlayer.getInternalPlayerState());
		mockPlayer.playFileSegment(contiguousSegment);
		assertEquals(AUDIO_PLAYER_STATE_INCORRECT, 
				AudioPlayerState.CONTINUE_PLAYING_EXISTING_FILE, 
				mockPlayer.getInternalPlayerState());
	}

}
