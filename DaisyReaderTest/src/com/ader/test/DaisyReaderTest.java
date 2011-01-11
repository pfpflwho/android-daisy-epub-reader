package com.ader.test;

import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

@SuppressWarnings("unchecked")
public class DaisyReaderTest extends ActivityInstrumentationTestCase2 {

	private static final String TARGET_PACKAGE_ID = "com.ader";
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.ader.HomeScreen";

	private static Class<?> launcherActivityClass;
	static{
		try {
			launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public DaisyReaderTest() throws ClassNotFoundException {
		super(TARGET_PACKAGE_ID, launcherActivityClass);
	}
	
	private Solo solo;
	
	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	
	public void testOpenHelp(){
		
		solo.clickOnButton("Help");
		
		boolean lableIsShown = solo.searchText("Instructions for the DAISY player");
		
		//The // is to state that ( should not be treated as a special reg ex symbol
		boolean scrolledTextIsShown = solo.searchText("and sections \\(where the depth might be level 2\\)");
		
		solo.clickOnButton("Close Instructions");
		
		assertTrue("Lable is not shown", lableIsShown);
		assertTrue("Last part of text is not shown", scrolledTextIsShown);
	}
	
	public void testSettingsSaveCorrectFolder(){
		
		solo.clickOnButton("Settings");
		
		solo.clickOnText("Root.*");
		
		solo.clearEditText(0);
		
		solo.enterText(0, "/sdcard/");
		
		solo.clickOnButton("OK");
		
		boolean toastIsShownNameSaved = solo.searchText("New folder name saved");
		
		boolean dialogIsShown = solo.searchText("SD Card folder changed successfully");
		
		assertTrue("Toast displaying saved name is not shown", toastIsShownNameSaved);
		assertTrue("Dialog with success message is not shown", dialogIsShown);
		
	}
	
	public void testSettingsSaveIncorrectFolder(){
		
		solo.clickOnButton("Settings");
		
		solo.clickOnText("Root.*");
		
		solo.clearEditText(0);
		
		solo.enterText(0, "test");
		solo.goBack();  // Clear the soft keyboard
		
		solo.clickOnButton("OK");
		
		solo.sendKey(Solo.ENTER);solo.sendKey(Solo.ENTER);
		
		boolean toastIsShownNameNotSaved = solo.searchText("New folder name NOT saved");
		
		assertTrue("Toast displaying folder is not saved is not shown", toastIsShownNameNotSaved);
		
	}
	
	}


