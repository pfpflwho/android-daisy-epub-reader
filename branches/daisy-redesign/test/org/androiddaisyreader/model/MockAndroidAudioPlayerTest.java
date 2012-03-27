package org.androiddaisyreader.model;

import junit.framework.TestCase;

import org.androiddaisyreader.mock.MockAndroidAudioPlayer;

public class MockAndroidAudioPlayerTest extends TestCase {

	private static final String AUDIO_PLAYER_STATE_INCORRECT = "The Audio Player State is incorrect";
	private MockAndroidAudioPlayer playertoTest;
	private Audio initialSegment;
	private Audio contiguousSegment;
	private Audio gapAfterContiguousSegments;
	private Audio overlapWithInitialSegment;
	private Audio newAudioFilename;

	@Override
	protected void setUp() {
		playertoTest = new MockAndroidAudioPlayer();
		initialSegment = new Audio("initial", "file1.mp3", 0.0f, 1.234f);
		contiguousSegment = new Audio("contiguous", "file1.mp3", 1.234f, 7.983f);
		gapAfterContiguousSegments = new Audio("gap", "file1.mp3", 15.001f, 26.771f);
		overlapWithInitialSegment = new Audio("overlap", "file1.mp3", 0.9f, 2.086f);
		newAudioFilename = new Audio("newfile", "new.mp3", 0.0f, 11.589f);
		playertoTest.playFileSegment(initialSegment);
	}

	public void testContiguousSegmentsAreRecognisedAsContiguous() {
		assertEquals(AUDIO_PLAYER_STATE_INCORRECT, 
				AudioPlayerState.PLAY_NEW_FILE, 
				playertoTest.getInternalPlayerState());
		playertoTest.playFileSegment(contiguousSegment);
		assertAudioPlayerStateIs(AudioPlayerState.CONTINUE_PLAYING_EXISTING_FILE);
	}

	public void testGapBetweenSegmentsIsDetected() {
		playertoTest.playFileSegment(contiguousSegment);
		playertoTest.playFileSegment(gapAfterContiguousSegments);
		assertAudioPlayerStateIs(AudioPlayerState.GAP_BETWEEN_CONTENTS);
	}
	
	public void testOverlappingSegmentsAreDetected() {
		playertoTest.playFileSegment(overlapWithInitialSegment);
		assertAudioPlayerStateIs(AudioPlayerState.OVERLAPPING_CONTENTS);
	}

	public void testNewAudioFileReplacesExistingOne() {
		playertoTest.playFileSegment(newAudioFilename);
		assertAudioPlayerStateIs(AudioPlayerState.PLAY_NEW_FILE);
	}
	
	/**
	 * Private helper method that asserts the AudioPlayer is in the expected state.
	 * @param expectedState
	 */
	private void assertAudioPlayerStateIs(AudioPlayerState expectedState) {
		assertEquals(AUDIO_PLAYER_STATE_INCORRECT, expectedState, 
				playertoTest.getInternalPlayerState());
	}
}
