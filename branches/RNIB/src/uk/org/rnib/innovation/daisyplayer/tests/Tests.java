package uk.org.rnib.innovation.daisyplayer.tests;

import java.io.IOException;

import android.test.suitebuilder.annotation.MediumTest;
import junit.framework.TestCase;
import uk.org.rnib.innovation.daisyplayer.AudioClip;
import uk.org.rnib.innovation.daisyplayer.DaisyPlayer;
import uk.org.rnib.innovation.daisyplayer.Heading;
import uk.org.rnib.innovation.daisyplayer.TextRenderer;
import uk.org.rnib.innovation.fileopener.MockFileOpener;

public class Tests extends TestCase {
	private DaisyPlayer dp;
	private MockFileOpener fileOpener = null;
	TextRenderer tr;

	public void setUp() {
		fileOpener = new MockFileOpener();
		dp = new DaisyPlayer(fileOpener);
		tr = new TextRenderer(fileOpener);
	}

	@MediumTest
	public void testOpen() {
		try {
			open();
			if (!isFirstHeading(dp.getHeading()))
				dp.SyncToNavPoint();
				assert(isAtBeginning());
		} catch (Exception e) {
		}
	}

	@MediumTest
	public void testoveToBeginning() {
		open();
		dp.moveToBeginning();
		assert(isFirstHeading(dp.getHeading()));
	}

	@MediumTest
	public void testMoveToSuperHeadingFromFirst() {
		open();
		dp.moveToBeginning();
		assert(dp.moveToSuperHeading());
		assert(isFirstHeading(dp.getHeading()));
	}

	@MediumTest
	public void testMoveToPreviousSameDepthHeadingFromFirst() {
		open();
		dp.moveToBeginning();
		// in the mock book there is only one heading at depth 1
		assertTrue(!dp.moveToPreviousHeading(false, false));
		assertTrue(isFirstHeading(dp.getHeading()));
	}

	@MediumTest
	public void testMoveToNextSameDepthHeadingFromFirst() {
		open();
		dp.moveToBeginning();
		// in the mock book there is only one heading at depth 1
		assertTrue(!dp.moveToNextHeading(false, false));
		assertTrue(isFirstHeading(dp.getHeading()));
	}

	@MediumTest
	public void testMoveToFirstSubHeadingFromFirst() {
		open();
		dp.moveToBeginning();
		assertTrue(dp.moveToFirstSubHeading());
		assertTrue(isSecondHeading(dp.getHeading()));
	}

	@MediumTest
	public void testMoveToHeadingNcx13() {
		final String ID = "ncx-13";
		open();
		assertTrue(dp.moveToHeading(ID));
		Heading h = dp.getHeading();
		assertTrue(h.getId().equals(ID));
	}

	@MediumTest
	public void testMoveToPreviousHeadingFromNcx12() {
		final String BEFORE_ID = "ncx-12";
		final String AFTER_ID = "ncx-10";
		open();
		assertTrue(dp.moveToHeading(BEFORE_ID));
		assertTrue(dp.moveToPreviousHeading(true, true));
		Heading h = dp.getHeading();
		String id = h.getId();
		assertTrue(h.getId().equals(AFTER_ID));
	}

	@MediumTest
	public void testMoveToNextBetweenSmil1And2() {
		final String ID = "ncx-10";
		final String BEFORE_TEXTURL = "AreYouReadyV3.xml#page5"; 
		final String AFTER_TEXTURL = "AreYouReadyV3.xml#dtb110";
		open();
		// move to last par in smil
		assertTrue(dp.moveToHeading(ID));
		dp.SyncToNavPoint();
		do {
			assertTrue(dp.moveToNext());
		} while (!dp.getTextUrl().equals(BEFORE_TEXTURL));
		assertTrue(dp.getTextUrl().equals(BEFORE_TEXTURL));
		assertTrue(dp.moveToNext());
		assertTrue(dp.getTextUrl().equals(AFTER_TEXTURL));
	}	
	
	@MediumTest
	public void testMoveToPreviousBetweenSmil8And7() {
		final String ID = "ncx-12";
		final String BEFORE_TEXTURL = "AreYouReadyV3.xml#dtb110";
		final String AFTER_TEXTURL = "AreYouReadyV3.xml#page5"; 
		open();
		// move to first par in smil
		assertTrue(dp.moveToHeading(ID));
		dp.SyncToNavPoint();
		assertTrue(dp.getTextUrl().equals(BEFORE_TEXTURL));
		assertTrue(dp.moveToPrevious());
		assertTrue(dp.getTextUrl().equals(AFTER_TEXTURL));
	}
	
	@MediumTest
	public void testTextRendererAtBeginningOfBook() {
		open();
		dp.SyncToNavPoint();
		try {
			String t = tr.Render(dp.getTextUrl());
			assertTrue(t.equals("A SHORT MANUAL ON DISASTER PREPAREDNESS"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@MediumTest
	public void testPlaybackFromBegginningToSecondSmil() {
		final String BEFORE_TEXTURL = "AreYouReadyV3.xml#page1";
		final String AFTER_TEXTURL = "AreYouReadyV3.xml#dtb11";
		open();
		dp.SyncToNavPoint();
		String u = null;
		do {
			assertTrue(!dp.getTextUrl().equals(u));
			assertTrue(dp.moveToNext());
		} while (!dp.getTextUrl().equals(BEFORE_TEXTURL));
		assertTrue(dp.moveToNext());
		assertTrue(dp.getTextUrl().equals(AFTER_TEXTURL));
	}
	
	
	
	private boolean open() {
		try {
			dp.open("/", "speechgen.opf");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean isFirstHeading(Heading h) {
		if (!h.getText().equals("A SHORT MANUAL ON DISASTER PREPAREDNESS"))
			return false;
		if (!areEqual(h.getAudio(), "speechgen0002.mp3", 0, 5.939))
			return false;
		if (!h.getContent().equals("speechgen0002.smil#tcp3"))
			return false;
		return true;
	}

	private boolean isAtBeginning() {
		if (!isFirstTextUrl(dp.getTextUrl()))
			return false;
		if (!isFirstAudio(dp.getAudio()))
			return false;
		return true;
	}

	private boolean isSecondHeading(Heading h) {
		if (!h.getText().equals("CONTENTS"))
			return false;
		if (!areEqual(h.getAudio(), "speechgen0003.mp3", 0, 4.065))
			return false;
		if (!h.getContent().equals("speechgen0003.smil#tcp13"))
			return false;
		return true;
	}

	private boolean areEqual(AudioClip a, String src, double clipBegin,
			double clipEnd) {
		if (!a.getSrc().equals(src))
			return false;
		if (a.getClipBegin() != clipBegin)
			return false;
		if (a.getClipEnd() != clipEnd)
			return false;
		return true;
	}

	private boolean isFirstTextUrl(String textUrl) {
		if (!textUrl.equals("AreYouReadyV3.xml#dtb3"))
			return false;
		return true;
	}

	private boolean isFirstAudio(AudioClip a) {
		if (!areEqual(a, "speechgen0002.mp3", 0, 5.939))
			return false;
		return true;
	}
}
