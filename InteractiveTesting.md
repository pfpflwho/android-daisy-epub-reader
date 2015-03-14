# Introduction #

Effective testing will help to increase our chances of providing a reliable and trustworthy application to our users. Some of the testing can and should be performed automatically using tools and techniques provided by the Android platform. Other testing needs to be performed on devices. Once we have relevant automated tests we may be able to reduce the scope of our manual testing.

# Overview of Interactive Testing #
Here, interactive testing means a person using the Daisy Reader, generally on physical Android devices such as mobile phones. The person will use the Daisy Reader to navigate around folders and ebooks in a variety of formats. The formats will include DAISY 202, DAISY 3, and the epub format. Attempts to read unsupported formats should be handled gracefully by the user-interface.

# Testing navigation #
Navigation for the Daisy Reader includes:
  * Navigating folders and files on the sdcard. Folders may be several levels deep
  * Navigating content at various 'levels' within a book e.g. going to the next page and the next chapter.

Users may want to navigate the user interface in various ways e.g. by using gestures on a touchscreen device, by using the trackball, or d-pad when devices have these, and by using keys when devices have a keyboard. Keyboards could be physical e.g. like the G1, or virtual. Users may have custom keyboards e.g. braille input.

# Device testing #
There are already lots of different types of Android devices available. These differ in form-factor, performance, capabilities, user-input options, etc. Ideally we'd like the reader to work well on all these devices and to take advantage of the various user-input methods available provided and configured on these devices. As the Android platform includes support for custom Input Method Entry (IME) potentially users can have one or more of these custom IME's installed e.g. a Braille soft keyboard.

# Performance testing #
The player should be able to play audio books without audible pauses or glitches.

# Robustness Testing of the Daisy Reader #
When the software is 'robust' it will be able to cope well with problems and with undesirable events (from the perspective of the application). Some examples of problems include:
  * Corrupt or incomplete ebooks e.g. when one or more of the underlying files are corrupt.

Some examples of undesirable events include:
  * Removal of the sdcard