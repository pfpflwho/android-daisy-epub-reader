# Introduction #

There are times when things go wrong with Android applications and DaisyReader applications aren't immune from gremlins and bugs. I've gathered some tips here to help you help yourself and help the project to address various problems I'm aware of. Please add comments here, or add a message on the Google Group for this project, file an Issue, etc. to tell us about problems with our apps we don't cover here.

# An Application doesn't install #
Which of the applications fail to install? If you want to report an issue we need specific details.

A common cause that prevents an app from installing is because an different version is already installed which conflicts with the one you want to install. Typically this happens when you want to install a newer version.

  * Try uninstalling the existing copy. From the Settings menu, select Applications, then Manage Applications. You should see the existing application in the list of applications. Click on the appropriate item then select the Uninstall button & complete the uninstallation. Now try to install the new one again. I **think** this is the most likely cause of the problem.

Discussion: The way I create the development versions means this problem sometimes happens if I use different machines when creating the program. The problem is caused by the 'debug' signing key which is unique to each computer. It shouldn't happen once I finally make formal 'signed' releases where the signing key comes from Google.

## working with adb commands from the Android SDK ##
If you are use `adb install` to install software from the command line we get more information about the cause of the problem. For instance  here's an example where the install fails because the Android device has another version of the same application that was signed with a key from another development computer.

```
adb install -r ~/temp/EbookReader_Sprint2.apk 
* daemon not running. starting it now on port 5037 *
* daemon started successfully *
4356 KB/s (1430037 bytes in 0.320s)
	pkg: /data/local/tmp/EbookReader_Sprint2.apk
Failure [INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES]
```

The key is `Failure [INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES]`

To deinstall using `adb uninstall` we need to know the package name to deinstall the existing app on the phone.
  * `adb shell pm list packages` lists all the installed packages.
  * `adb uninstall org.androiddaisyreader.apps`

Now we can install the new version of the app, for instance:
```
 adb install -r ~/temp/EbookReader_Sprint2.apk 
3199 KB/s (1430037 bytes in 0.436s)
	pkg: /data/local/tmp/EbookReader_Sprint2.apk
Success
```
# Obtaining more information about problems #
Android devices have a log file on the device. The phone and most applications, including DaisyReader, etc. write messages to this log. The messages in the log often contain important clues about problems.

There are several ways to read the system logs. Here are 2 ways:
## Using the Android development tools ##
Do you have the Android development tools installed? The following web page has the link to the downloads & a link to the instructions on how to install the development tools.  http://developer.android.com/sdk/index.html  If so, try connecting your phone to your computer using USB. There is a program called logcat which is part of the Android tools. You may also need to install a specific 'USB device driver' on Windows computers in order to make the connection work fully. I'm assuming you're not very familiar with command line prompts or the Eclipse development tool. We want to run a command called 'ddms' once you have the Android tools installed. From the Windows Start menu (see screenshot)![http://android-daisy-epub-reader.googlecode.com/svn/screenshots/androidtools/Windows%20Key%20pressed..png](http://android-daisy-epub-reader.googlecode.com/svn/screenshots/androidtools/Windows%20Key%20pressed..png) type in ddms in the text box. Your computer should find one match, which should - hopefully - be the correct tool. Then start this program e.g. by clicking on the result. Once this program starts you should see a small phone icon and a long code - your phone's serial number for Android. Click on this icon or text to select this phone. Then you should see lots of lines of text appear below (see the second screenshot). ![http://android-daisy-epub-reader.googlecode.com/svn/screenshots/androidtools/DDMS%20Connected%20to%20Device%20with%20annotations.png](http://android-daisy-epub-reader.googlecode.com/svn/screenshots/androidtools/DDMS%20Connected%20to%20Device%20with%20annotations.png)

Now we've got this far, if you can clear the log (a little icon with a red x on it) we reduce the amount of text we need to review. Then try installing first one application & then save the log on your computer (using the icon like a diskette, next to the 'clear log' icon). email me this file. julianharty - at - gmail.com (replace the  - at - with @)

## Using an Android application ##
I can recommend the following Android application
https://play.google.com/store/apps/details?id=org.jtb.alogcat&hl=en It's called alogcat if you search for it in Android Market on your phone. Android Market has been renamed to Google Play on most phones (but not on my old Galaxy Tab phone). When you start this program it will display essentially the same logfile from your phone. It has various menu options. One clears the log messages. Another allows you to share them & has an option to email the log output, so you can try performing the installation again & then email me the log file from your device.