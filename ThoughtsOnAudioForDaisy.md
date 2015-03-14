# Introduction #

DAISY players use audio to read the books. They may play pre-recorded audio and/or use speech synthesis to 'read' text aloud.

We need to consider the characteristics and capabilities of the Android platform, including timing and synchronisation between text (and other content) being displayed on screen and the audio output.

This wiki page contains various ideas and analysis which may help us to implement robust designs and solutions for audio in all its forms for a DAISY reader on Android.

These ideas are works-in-progress. Some will make their way into code.

# Thoughts #
## Pseudo OnCompletion Events at end of each segment ##
Android's `MediaPlayer` does not allow the caller to specify the end of a logical audio segment in a larger file (that may contain multiple audio segments). Many full-audio DAISY books consist of mp3 files containing lots of segments, arranged contiguously (and thankfully, in-order) - so we can tolerate the current behaviour in terms of playing the audio. However now we're aiming to synchronise the text on screen with the relevant audio segment, the current behaviour is insufficient for our needs.

One idea is to use timers, that run in parallel while the audio is being played, to generate additional OnCompletion events (in addition to the ones generated when Android's MediaPlayer finishes playing at the end of a file). The caller registers a listener for OnCompletion events and can synchronise the screen updates with these events.

### Considerations ###
  * We need to account for pauses, resetting timers when users navigate through the book, etc.
  * We may want to identify the source of each event e.g. we may need to handle the actual OnCompletion from the MediaPlayer differently e.g. to supply it with a new audio file.

## SoundPool ##
Android provides the powerful `MediaPlayer` and a lighter-weight `SoundPool`. Potentially we can use the SoundPool in place of the MediaPlayer we're currently using.

Defining characteristics:
  * `SoundPool` allows the start and duration of audio to be specified. The MediaPlayer only allows the offset to be specified and continues playing until the end of the file. DAISY books often contain several audio segments in a single audio file (in mp3 format). Currently we rely on the characteristics of how audio is stored contiguously and in order which means that users are unaware the audio simply plays to the end of the file (in the MediaPlayer). The SoundPool may allow us finer-grained control of the audio playback.
  * The SoundPool takes some time to load and process audio segments. We may need to write slightly more complex code to load, schedule, play, and unload audio segments from the SoundPool particularly where the audio files are large (> 1MB).

### Links ###
References:
  * http://developer.android.com/reference/android/media/SoundPool.html

Sample projects:
  * http://stackoverflow.com/questions/5202510/soundpool-sample-not-ready
  * http://www.vogella.com/articles/AndroidMedia/article.html
  * http://blog.nelsondev.net/?p=207