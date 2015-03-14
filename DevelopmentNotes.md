#summary Some tips and notes for developers and maintainers of this project.
#labels Phase-Implementation,Featured,Phase-QA

# Introduction #

Here are some tips that may help us to maintain the project effectively and efficiently.
# Building the project in Eclipse #
We use an ant script to extract the svn revision number and insert it as part of the version number in `AndroidManifest.xml`. This script should be run automatically by Eclipse when building the project. Sometimes Eclipse will complain about an empty `{project_loc}`. Re-running the build sometimes clears this error message. However another tip is to make sure the project is selected in the project explorer before running the build. See http://blog.samratdhillon.com/archives/473 for an explanation of this solution.

# Running the tests #
We added JUnit tests in [r50](https://code.google.com/p/android-daisy-epub-reader/source/detail?r=50), and have been slowly refactoring the code and adding tests to support the refactoring. In the process we're removing unnecessary dependencies on Android libraries, etc. By removing these dependencies the tests can currently ([r67](https://code.google.com/p/android-daisy-epub-reader/source/detail?r=67)) be run using the standard JUnit 3 test runner.

## Running the tests specific to Android ##
We have some tests which test the Activities and the respective UI. These tests are located in the `../activitytests` folder.

To run these Activity Tests we need to use the `android.test.InstrumentationTestRunner`

The tests expect a test DAISY book to be copied to a specific folder on the sdcard on the Android phone or Android Virtual Device (AVD). The DAISY book is called minidaisyaudiobook. This entire folder needs to be copied into the `/sdcard/Books/testaudiobooks/` folder. The source files for the test book are available as part of the project in `../Resources/testfiles/` and online at http://code.google.com/p/android-daisy-epub-reader/source/browse/#svn%2Ftrunk%2FResources%2Ftestfiles%2Fminidaisyaudiobook%253Fstate%253Dclosed
You can use adb to transfer the files using `adb push`

In Eclipse we can easily create a suitable `Run Configuration`. Here's one way, there are bound to be other ways.
  * Select Run Configurations.
  * Navigate on the left to Android JUnit Test and select this option.
  * Select the New button (above the list of run configurations)
  * On the 'Test' tab select the 'Run all tests in the selected project, or package' radio button. Then specify the package name of `com.ader.test.ui`
  * Apply the changes
  * Run the tests

Here's a slightly more involved way to run these tests using keyboard commands.
  * Select the Run menu - the Run menu should appear
  * Type n as the short key for Run Configurations - the Run Configurations should appear
  * Use the arrow down keys in the Test tab to go to the Run all tests in the selected project, or package (about 4 down arrows)
  * Select the Alt+S keyboard menu to Search for the package name - The Folder Selection dialog should appear
  * Press the right-arrow key to expand the project into the set of file folders. Navigate down to the `activitytests` folder
  * Press the 

&lt;Enter&gt;

 key to expand activitytests. com.ader.tests.ui should appear below the folder
  * Use the down-arrow key to select it and press 

&lt;Enter&gt;


  * Tab to the Instrumentation Runner drop-down option.
  * Press down-arrow until android.test.InstrumentationTestRunner appears in the drop-down field.
  * Tab (or press Alt+Y key combination) to Apply the changes.
  * Use Alt+R to run the tests (You can also tab to the Run button).
  * Select the Device or Android Virtual Device (AVD) to use. To select the device you may need a combination of Tab, Arrow and Space keys.

You can also use the context-specific (right-click) menu to run the Activity Tests as an Android JUnit test.
  * Navigate in the Package Explorer in Eclipse to `com.ader.test.ui`. Click once on the package name to select it.
  * Click the right-click button on your mouse. From the small pop-up menu select `Run As...`
  * From the sub-menu select Android JUnit test.

Regardless of how you start the Activity Tests they should run successfully. They may take a while to start, particularly when you use an Android Virtual Device (AVD). Eventually you may hear a split-second of audio.
## Running tests that rely on external files ##
Some of the tests use additional files, rather than having all the contents e.g. of a book stored in the JUnit code. Getting these tests to run in both Eclipse and on an Android Device such as a phone can be challenging and has been something I've been experimenting with from time to time over the life of this project.

On an Android device, the simplest place for these test files to go is on the memory card, which is mapped as `/sdcard`. See http://code.google.com/p/android-daisy-epub-reader/source/detail?r=320 for an initial set of files. On your computer a similar folder would need to be created, which Eclipse (etc) then uses.
  * For linux and on a mac simply create /sdcard using a command such as mkdir from a terminal window e.g. `mkdir /sdcard` You may need Administrative privileges to run this command successfully. The test files will need to be copied onto the sdcard somehow. Here are various ways that might be suitable for you:
    * Using `adb push` from the command line when the Android device is connected and visible (Use `adb devices` to check the device has been detected.
    * By 'mounting' the memory card on the phone when the USB connection to the computer has been established. The mounted memory card should then appear as a logical drive in Windows e.g. `D:\`
    * By physically removing the memory card from the Android device, inserting it into a suitable card reader on the computer, then copy the files from one location (this project) to another.
  * For Windows, create a directory in the root of the logical drive. Typically this is `C:\` so from a command `cmd` window a command such as `md c:\sdcard` should work. Again you many need administrative permissions to run the command successfully. You can also use Windows Explorer to create the directory. I don't know the details of where to create the folder on a Windows machine with more than one logical drive, I'd suggest starting with creating the directory on the same logical drive as Eclipse uses.

Some of the tests currently rely on content in the /Resources project folder currently fail when run using the Android JUnit test runner as the contents of this folder is not pushed to the emulator or devices. We're aware of this limitation, see http://code.google.com/p/android-daisy-epub-reader/issues/detail?id=10 For now please use the standard JUnit test runner from eclipse; and see http://dtmilano.blogspot.com/2009/12/android-testing-external-libraries.html for some tips on configuring the test runner in Eclipse correctly.

## Getting the JUnit tests to run in Eclipse ##
When Eclipse has both the Android SDK and the standard Java tools installed there are 2 possible test runners for JUnit tests. For some reason, the classpath for the Eclipse JUnit Test Runner has the Android library on the classpath, instead of the standard JUnit 3 library. We can fix this by editing the Bootstrap Entries on the Classpath tab for the relevant "run configuration".

I assume there are more elegant and reliable ways than using Eclipse's GUI, however that's the way I know, so here are the hacky instructions :)
  1. Select the Android entry e.g. Android 1.6 on my machine in the Classpath tab. This will cause the various buttons on the right of the dialog to be enabled. We want the 'Advanced...' button.
  1. Select the Advanced... button, and in the small popup dialog select 'Add Library' in the radio buttons before selecting the OK button. We'll follow this process twice, once for the JRE System Library, and again for the JUnit library.
  1. Select the JRE System Library and the Next button. Accept the "Workspace default JRE". This should work fine, mine's set to jre6.
  1. Click the Finish button.
  1. As mentioned earlier, repeat the steps to add JUnit 3.
  1. You should now have 3 items under Bootstrap Entries. Now Remove the Android library e.g. Android 1.6 for me.
  1. Select the Apply button. Now you should be able to use the Run button to run the JUnit tests with the standard JUnit test runner.

## Running all the tests together in Eclipse ##
In Eclipse, we can create a 'Run Configuration' to run all the tests for a project.

One way to do so, is to create a new Run Configuration e.g. from the Run menu. Select JUnit, then the 'new' icon.
  * In the Test tab: Pick the radio button next to the text 'Run all the tests in the selected project, package or source folder. Make sure the Test runner: is JUnit 3.
  * In the Arguments tab: nothing needs to be set
  * In the Classpath tab: The bootstrap entries should be set to JRE System Library [J2SE-1.5] and JUnit 3, remove any other entries e.g. remove Android if it's listed. (see the top elsewhere in this wiki page for details of how to configure the classpath).

I've not needed to change any of the other options or settings. So try to Run the tests once you've got this far :)

## Running the tests from the command line ##
Note: This is incomplete work and about half of the tests currently fail when run on a device because some of the external files are not in the location the tests expect. I will work to improve the tests so they can be run cleanly from the command line.

For now, here's the basic command line that seems to at least run all the tests `adb shell am instrument -w com.ader/android.test.InstrumentationTestRunner`

I have been trying to use additional command line options to filter which tests to run (e.g. so we can run all the small tests that don't have any external file dependencies). I'm still experimenting... Here's the official documentation http://developer.android.com/reference/android/test/InstrumentationTestRunner.html

The command options seem to be very sensitive to the order they're called.

Also I currently seem to need to rebuild the tests in Eclipse before changes take effect. I'll find out how to build the tests using the command line e.g. using `ant` and revise these notes.
### How to run the Activity Tests using adb shell ###

` adb shell am instrument -w -e class com.ader.test.ui.ListenToBookTest com.ader/android.test.InstrumentationTestRun `

=== How to run the small tests using adb shell

`adb shell am instrument -w -e size small com.ader/android.test.InstrumentationTestRunner `

Note: I'm fixing some of the small tests that currently fail when run in this way.

# Importing the project into Eclipse #
Here are a couple of gotchas that have tripped us up in the past which may be worth documenting to help you fix them if they happen to you too.

## Setting Java Compilation to 5.0 ##
If you decide to import this project into Eclipse, which has the Android Development Tool plugin installed and configured, then you might get the following error message: `[2010-06-20 12:57:58 - DaisyReader] Android requires .class compatibility set to 5.0. Please fix project properties.`

To fix this problem, select the Project Properties, expand the Java Compiler option, and:
  * Enable Project Specific Settings
  * Set the Compiler Compliance Level to 1.5
  * Select the OK button to save the changes.

Depending on how your Eclipse IDE is configured the project may rebuild automatically. If it doesn't then you can probably force a rebuild by using the Project > Clean... menu.

## Don't share folders ##
Don't use a common folder for your source and your eclipse project as it prevents you from importing the project into your current workspace. I've learnt to create parallel folders or directories e.g. in linux I have:
  * `/home/me/workspace/opensourceprojects` for my Eclipse workspace
  * `/home/me/code/opensourceprojects` where I then create a folder called DaisyReader when I use subversion to obtain the source code of this project.

Once I have both the workspace and the code, I use File > Import... > General > Existing Project into Workspace

# Tips #
  * We can generate QR codes for downloads using the zxing online web tool here
http://zxing.appspot.com/generator/
  * Please update the [releasenotes.txt](http://android-daisy-epub-reader.googlecode.com/svn/trunk/releasenotes.txt) with siginficant changes. Note some revisions don't need comments, and those made through the web UI will not be recorded in the document anyway.

# Links to information on Android testing #
Here are a collection of links that may be useful as we add more automated tests, and as we re-engineer the application into distinct components.
|URL|Comments|
|:--|:-------|
|http://developer.android.com/guide/samples/ApiDemos/tests/src/com/example/android/apis/app/LocalServiceTest.html|How to create automated tests for an Android Service, useful if and when we make the player a Service.|
|http://dtmilano.blogspot.com/|Lots of useful blog posts, with examples, on automated testing for Android|
|http://www.slideshare.net/marc_chung/testing-android|A short presentation on Automating Android Testing|
|http://code.google.com/p/mwta/downloads/list|My published documents on Android Test Automation.|

## Coping with svn ##
Here are a couple of notes to remind me how to recover when I've gotten into trouble with svn.

To recover files that were accidentally deleted (often because eclipse seems to copy the .svn contents from ../src/ to ../bin/ use `svn copy` e.g. `svn copy https://android-daisy-epub-reader.googlecode.com/svn/branches/daisy-redesign/src/org/androiddaisyreader/TextSection.java@395 .\TextSection.java`
then commit the changes.

When svn refuses to allow me to commit code because the file is out of date. I ended up using another svn instance. Committed a change to the file it complained about, then return to the original workspace and try committing my changes again. `svn clean`, `svn update`, and deleting the file in my local workspace (after making a safe copy of my changes) seem to be part of the process.

## Processing SMIL files from the command line ##
We have a simple java program which accepts a smil filename from the command line and calls the code and methods which process SMIL files for the new book `model`. The main purpose is to help us quickly discover if we've more work to do in order to process real files, rather than our hand-crafted test data. Here is an example of how to call it in a Windows command shell.
```
..\bin>for %1 in ("c:\Users\jharty\Downloads\daisy.org\daisy 2.02\frontpage
-202-complete\frontpage\*.smil") do java org.androiddaisyreader.model.ProcessExternalSmilFile "%1"
```

Please note the double-quotes which are needed when the path or filename contains spaces. Also, this code is currently in the daisy-redesign branch ([r412](https://code.google.com/p/android-daisy-epub-reader/source/detail?r=412)). We are likely to merge this branch into trunk, hopefully soon.

Note: the first time I ran this I discovered we have problems parsing the times used in audio segments so the test has quickly paid for itself :)