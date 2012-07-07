package com.ader.test;

import junit.framework.AssertionFailedError;

import com.ader.ui.HomeScreen;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

@SuppressWarnings("unchecked")
public class DaisyReaderTest extends ActivityInstrumentationTestCase2<HomeScreen> {

	private static final String SDCARD_FOLDER_NAME = "/sdcard/";
	private static final String TARGET_PACKAGE_ID = "com.ader";
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.ader.ui.HomeScreen";

	private static Class<?> launcherActivityClass;
	static{
		try {
			launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public DaisyReaderTest() throws ClassNotFoundException {
		super(TARGET_PACKAGE_ID, HomeScreen.class);
	}
	
	private Solo solo;
	
	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	@MediumTest
	public void testOpenHelp(){
		
		solo.clickOnButton("Help");
		
		boolean lableIsShown = solo.searchText("Instructions for the DAISY player");
		
		//The // is to state that ( should not be treated as a special reg ex symbol
		boolean scrolledTextIsShown = solo.searchText("and sections \\(where the depth might be level 2\\)");
		
		solo.clickOnButton("Close Instructions");
		
		assertTrue("Lable is not shown", lableIsShown);
		assertTrue("Last part of text is not shown", scrolledTextIsShown);
	}
	
	@MediumTest
	public void testSettingsSaveCorrectFolder(){
		
		solo.clickOnButton("Settings");
		
		solo.clickOnText("Root.*");
		
		String oldValue = solo.getEditText(0).getText().toString();
		solo.clearEditText(0);
		
		solo.getEditText(0).setInputType(0);
		solo.enterText(0, SDCARD_FOLDER_NAME);
		// solo.goBack(); // clear the soft keyboard.
		try {
		solo.clickOnButton("OK");
		} catch (AssertionFailedError e) {
			solo.goBack();
			solo.clickOnButton("OK");
		}
		
		boolean toastIsShownNameSaved = solo.searchText("New folder name saved");
		
		boolean dialogIsShown = solo.searchText("SD Card folder changed successfully");
		
		assertTrue("Toast displaying saved name is not shown", toastIsShownNameSaved);
		
		// Only expect the second message if a different valid folder was specified.
		if (SDCARD_FOLDER_NAME.equals(oldValue)) {
			assertFalse("Dialog with success message should not be shown", dialogIsShown);
		} else {
			assertTrue("Dialog with success message is not shown", dialogIsShown);
		}
	}
	
	@MediumTest
	public void testSettingsSaveIncorrectFolder(){
		
		solo.clickOnButton("Settings");
		
		solo.clickOnText("Root.*");
		
		solo.clearEditText(0);
		
		solo.enterText(0, "non existent folder name");
		
		try {
			solo.clickOnButton("OK");
			} catch (AssertionFailedError e) {
				solo.goBack();
				solo.clickOnButton("OK");
			}
		
		solo.sendKey(Solo.ENTER);solo.sendKey(Solo.ENTER);
		
		boolean toastIsShownNameNotSaved = solo.searchText("New folder name NOT saved");
		
		assertTrue("Toast displaying 'folder is not saved' is not shown", toastIsShownNameNotSaved);
		
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	}


