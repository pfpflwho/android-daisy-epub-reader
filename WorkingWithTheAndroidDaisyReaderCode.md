# Introduction #

We have been working on a new codebase that will eventually replace the existing DaisyReader code. The old code uses a Java namespace of `com.ader` and currently lives in `trunk` The new codebase uses a Java namespace of org.androiddaisyreader and lives in `branches`

The new code is more modular, the core engine is built into a separate `jar` file which can then be used in other projects including Android apps and other Java applications.


# Using the core engine in Android projects #

There are several details to get right:
  * Build the code for Java 6, not later versions of Java. In practice, the least painful way I have found is to use Sun's JDK 6 and avoid JDK 7 entirely.
  * There is a `build.xml` script in http://code.google.com/p/android-daisy-epub-reader/source/browse/branches/daisy-redesign/build.xml which can be used to build the `jar`
  * Copy the `jar` file to the `/libs` folder of your Android project (assuming you're using ADK 17 or later, the Android build tools automatically process files in the `libs` folder.
  * Clean the Android project and build it.

## Tips ##
Enable the verbose build option in your choice of development tool for the Android Build Process to spot errors including the library. The Android dex compilation process currently rejects code built by Java 7

I discovered several helpful tips: - Run `ant` with the `-v` command line option and scrutinize the output carefully. That's how I knew the java6 compiler was being used at the end of all my changes. And similarly when I built the Android app using ant, the dex stage had enough detail to tell me which jar files it processed, etc.

Similarly in Eclipse I enabled the verbose level of logging for the Android build output. `Preferences>Android>Build>Build output>Verbose`

I wrote about some of the challenges I faced with the new Android tools and Java 7 at http://stackoverflow.com/questions/9824491/android-sdk-r17-ruins-working-projects/10557629#10557629