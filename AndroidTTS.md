# Introduction #
We want the DaisyReader app to be able to read the contents of DAISY and other eBooks. We also want the app to communicate with the user by 'speaking', what's sometimes called a 'self-voicing' app. We may be able to rely on Android's Accessibility capabilities to 'speak'; for instance, the combination of designing our app carefully to provide the hints and information combined with Explore-By-Touch might be enough to provide a good user experience on recent versions of Android, such as ICS and Jelly Bean. We will try to find out...

We are struggling to decide between relying on the Accessibility support provided in the recent versions of Android (e.g. ICS and Jelly Bean) or writing explicit code to 'voice' the app. We want DaisyReader to be useful on older versions of Android, including 2.x versions. Android's Accessibility support is unlikely to provide the support we need on Android 2.x

## International and multi-language support ##
DaisyReader needs to support many human languages, some of these languages are not currently available as audio (TTS).

There seem to be a couple of potential solutions to investigate:
  * Can we use pre-recorded audio for languages that are unsupported?
  * Can we extend the TTS engine somehow?

Pre-recorded speech can be read from the android device by using a combination of `addSpeech(...)` and `speak(...)`

The following don't format automatically as URLs as they include spaces in the URL. So you need to copy and paste them to reach the documentation.
`http://developer.android.com/reference/android/speech/tts/TextToSpeech.html#addSpeech(java.lang.String, java.lang.String)`

`http://developer.android.com/reference/android/speech/tts/TextToSpeech.html#speak(java.lang.String, int, java.util.HashMap<java.lang.String, java.lang.String>)`

Someone was investigating possible ways to extend the TTS engine, see the following link:
http://stackoverflow.com/questions/9272241/extending-android-tts-engine

Also, the user interface of the app may be in one language and the book in another. Older versions of Android apparently enabled app developers to have several TTS instances, one per language, however ICS apparently doesn't support this behaviour (see Volker's comment in http://stackoverflow.com/questions/9450365/android-not-acknowledging-tts-engine )

## The user experience of using TTS in ICS ##
TTS support in ICS also appears to have some adverse behaviours in terms of the user-experience with loading TTS data e.g. see http://stackoverflow.com/questions/9450365/android-not-acknowledging-tts-engine

## How well does TTS work for longer pieces of text? ##
http://stackoverflow.com/questions/15018794/trouble-in-reading-the-text-in-tts

## Can the rate of speech be varied? ##
see `setSpeechRate`
http://stackoverflow.com/questions/10690077/texttospeech-tts

## Miscellaneous Limitations in Android TTS ##
Apparently the fall-back doesn't work when pre-recorded audio is missing. See http://stackoverflow.com/questions/9143439/texttospeech-fall-back-to-back-end-service-when-pre-recorded-wav-file-missing

# Other projects that use Android TTS #
Here are various projects I've found that also use Android TTS. There will be additional projects both on Google Code, and elsewhere on the web. Please add links to them in the comments and I can include relevant ones here.

  * http://code.google.com/p/ttsaid/ Text to Speech (TTS) application to help visually impaired people (Android platform) Uses GPLv3 license to incompatible with this project in terms of the codebases.
  * 

I've listed the following seemingly dormant projects here to save me time researching them.
  * http://code.google.com/p/mobile-for-visually-impaired (a single commit, no functionality currently)