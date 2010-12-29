package com.ader.test;

import android.test.ActivityInstrumentationTestCase2;

import com.ader.DaisyReader;
import com.jayway.android.robotium.solo.Solo;

public class ExampleTest extends ActivityInstrumentationTestCase2<DaisyReader> {
	private Solo solo;

	public ExampleTest() {
		super("com.ader", DaisyReader.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	@Override
	protected void tearDown() throws Exception {
		try {
			solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	}

	public void testTrue() {
		assertTrue("This better pass!", true);
	}

}
