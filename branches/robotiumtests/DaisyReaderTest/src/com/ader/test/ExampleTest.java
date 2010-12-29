package com.ader.test;

import android.test.ActivityInstrumentationTestCase2;

import com.ader.DaisyReader;

public class ExampleTest extends ActivityInstrumentationTestCase2<DaisyReader> {

	public ExampleTest() {
		super("com.ader", DaisyReader.class);
		// TODO Auto-generated constructor stub
	}

	public void testTrue() {
		assertTrue("This better pass!", true);
	}

}
