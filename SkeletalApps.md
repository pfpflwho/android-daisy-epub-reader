# Introduction #

Several aspects of developing an Accessible, eBook reader are relatively unknown. We need to have a good understanding of several key areas of the Android platform in order to create an effective, useful app where we can continue developing the app to add new capabilities. We also want to be able to understand and maintain our code on an ongoing basis. Creating Skeletal apps may help us gain sufficient understanding of key aspects of Android to enable us to do a far better job of creating the main DaisyReader apps.

This wiki page describes several areas where skeletal apps may help us in our research and understanding.

# Skeletal Apps to consider #

## TTS ##
We have several areas of AndroidTTS to consider including:
  * How does TTS behave on pre and post ICS devices
  * Can we use TTS to read the contents of the books? How much text can we pass it to speak reliably?
  * Can we use pre-recorded audio snippets effectively? e.g. to voice the UI of the app in languages not available as full TTS languages.

### Polylingual TTS ###
We may want to support the interleaving of several spoken languages.
  * Books may be in one written language e.g. French, where the App's UI is in another language e.g. Italian.
  * Books may include several written languages e.g. some dialog in French where the rest of the book is in English.
There have been some reported problems with support for multiple TTS instances in ICS and later, as noted in AndroidTTS. We would like to understand the issues and see if it's practical to create a polylingual app.

This is a more advanced topic for our project.

