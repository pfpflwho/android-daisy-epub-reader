# Introduction #

The Daisy Reader application has been designed in three distinct parts which co-operate to provide the features and functions we believe users (including you) want. The aim is to allow individual parts to be replaced to suit various needs, of libraries and users. Here are some examples of these needs.

## For libraries ##
There are various organisations who have a large library of DAISY books, typically to serve registered users in their country. They may want to customise the Daisy Reader software to integrate it with their online library to allow users to sign-in, manage their collection of books from this library, etc.

The application is designed so the selection of books can be replaced with a custom application (known as an Activity in Android) without affecting the rest of the functionality of the player. The application starts by launching the Home Screen http://code.google.com/p/android-daisy-epub-reader/source/browse/trunk/src/com/ader/HomeScreen.java which allows users to find, select and open books stored on their Android Phone's memory card. This menu can be modified programatically to launch a separate Android Activity that allows users to connect to their preferred library. If we discover users want to connect to several distinct libraries then an additional screen could be added to allow them to select which library to connect to. Conversely, we could include this additional screen but bypass it and connect directly to a specific library if they only have one library configured. Something to consider once we start adding support for online libraries.

Currently ([r205](https://code.google.com/p/android-daisy-epub-reader/source/detail?r=205)) the application does not include support for any of the online libraries. I (Julian Harty) working with several organisations in the USA and Scandinavia to find ways to elegantly support their respective libraries by implementing common protocols and services to reduce the development, configuration and support burdens for all concerned.

## To suit the individual user ##
Different users may have different needs and preferences. For instance a blind user might not care what's displayed on the screen, but want to use gestures to control the application. Some partly blind users may want large visible text, others may want the text to be tiny. Dyslexic users might want to interact using icons and pictures.

The application has been designed so we will be able to replace the user-interface for the player independently of the actual player. At the moment ([r205](https://code.google.com/p/android-daisy-epub-reader/source/detail?r=205)) the code is still integrated and split across two main Java Classes http://code.google.com/p/android-daisy-epub-reader/source/browse/trunk/src/com/ader/DaisyReader.java which starts the plater; and http://code.google.com/p/android-daisy-epub-reader/source/browse/trunk/src/com/ader/DaisyPlayer.java which provides a combination of the user-interface and the actual playing of the audio files. We want to replace DaisyPlayer with an Android Service, and will try to fully separate the user-interface from the player at that point.

At the moment the application provides a single version of the user-interface that seems to work adequately for the current blind and dyslexic users of the pre-release software. we would like to include several user-interfaces in future, and others are welcome to develop and release additional user-interfaces.

# Simplest possible way of connecting to online DAISY libraries #
Damien suggested adding an embedded web browser to enable users to login to their preferred library / libraries. They can then sign-in if the library requires it.
  * Ideally we would specify a default download folder, when creating the web view, so the books would reside in a common, easy to locate, area on the Android device.
  * Often the DAISY book will be packaged as a zip file. We can either unpack the book before using the content, or ideally process the zipped content directly (which uses less storage and reduces pollution in terms of files)

Update: After some initial experimentation using an embedded WebView in a sample Android application. We can probably use a WebView to provide adequate functionality for users who can read and interpret the contents of web pages on Android. However for users who rely on TextToSpeech e.g. using the TalkBack accessibility service, the main problem seems to be the WebView doesn't trigger suitable events to drive TalkBack or other Accessibility services on Android.

Note: for Android version 9 and later versions, Android includes the [Download Manager](http://developer.android.com/reference/android/app/DownloadManager.html) which offloads some of the complications of the actual downloads. We still need to implement a download mechanism to work on older versions of Android.

StackOverflow has some useful posts regarding the download mechanisms, see:
  * http://stackoverflow.com/questions/3926629/downloadlistener-not-working
  * http://stackoverflow.com/questions/6870709/android-downloadlistener-or-asynctask
  * http://stackoverflow.com/questions/4860484/download-a-file-from-webview-to-a-custom-folder
  * http://stackoverflow.com/questions/7288109/manually-handle-mp3-download-in-webview


# Ideas to present and synchronise multimedia content #
_these are rough notes_
## For full audio DAISY books ##
How about using a webview to display text and images for books that include pre-recorded audio? We could try using the Java-to-javascript bridge in the webview to synchronize the contents of the webview with the audio currently playing. When the player finishes playing an audio section (currently the end of an mp3 file) it would fire an event to load the next section. Loading the next section would also update the UI view. We can have a separate thread that processes the timing info from the clip-end timings to also trigger updates to the view **while the audio keeps playing without interruption**. This may enable us to obviate the need to chunk up the audio, and to bypass the restriction placed by not being able to tell the MediaPlayer when to stop playing within an audio file.

## For full text DAISY books ##
We need to use TTS to 'read' the text anyway (assuming the user wants to listen to the audio) so we will probably need to segment the text content so we don't overrun / overload the TTS engine. Therefore we should be able to segment the full text so it doesn't exceed the viewable area on the current device's screen. The segmenting algorithm is likely to be relatively sophisticated to provide a pleasing user experience; however we can start simply and see how sophisticated we need to get to please the users.

# Coping with interruptions #
We need to make sure the app copes appropriately with interruptions, including phone calls, being put into the background, etc.

Here's a potentially useful example that detects the state of phone calls https://code.google.com/p/mynpr/source/browse/trunk/mynpr/src/com/webeclubbin/mynpr/StreamingMediaPlayer.java

We also need to make sure the new app (and any of our apps) correctly retains the correct location in the book when the app leaves the foreground.

And if we're downloading a file, perhaps http://stackoverflow.com/questions/1875670/what-to-do-with-asynctask-in-onpause would be relevant? And in particular the suggestion to use a Service in https://groups.google.com/forum/#!topic/android-developers/vwRrlc84gy0 seems the best of the options.

# Tips on interfacing with the MediaPlayer #
Here's a useful tutorial that creates a remote Service to control the media player. It doesn't provide the functionality we need, however it may remind us of some good practices.
http://www.helloandroid.com/tutorials/musicdroid-audio-player-part-ii

And a wide ranging set of examples of working with SoundPool's and the MediaPlayer http://www.vogella.com/articles/AndroidMedia/article.html

# Ways to reduce the volume of music when TTS speaks #
Here's a good discussion on using AudioManager to reduce the volume of the media player when TTS is speaking.
http://stackoverflow.com/questions/6383862/interrupting-mediaplayer

Julian Harty 27 Dec 2013