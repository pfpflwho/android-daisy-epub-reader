# Introduction #

The DAISY reader software is already available in several languages, thanks to the contributions of several volunteers. Additional languages would help make the software usable for more users.

Your contributions are welcome, either to provide updates, and corrections for existing languages, or to help provide a new translation. This wiki page aims to help you help the project by explaining what to translate and how to donate the translations so we can include them into this project, and therefore into the application.

If you notice problems with the instructions or if you have suggestions for how to improve them, please add a comment to this wiki page, or raise a new issue on this site Issues

# How Android applications support languages #

Android encourages developers to separate content from the main program logic. Content includes things such as text (called strings by Android), images, icons, and even audio and video, etc. Android's term for Content is Resources, and the files containing the various content are placed in sub-folders of a folder called `res`.

There are several sub-folders:
  * `values` which contains the strings (the text)
  * `drawable` which contains images and icons, etc
  * `layout` which contains custom layouts e.g. to improve the formatting of screens in the application.
These folders contain the default content, these are the values that will be used if the DAISY Reader software doesn't have any more suitable content available.

Android's uses the term 'localization' to describe translating software for specific languages. Localization actually includes more than simply translating the text; for instance some countries format numbers differently from the default US English format, localization includes number formatting, and many other details. For more details on localization for android, several useful technical guides are available online, such as:
  * http://developer.android.com/resources/tutorials/localization/index.html
  * http://developer.android.com/guide/topics/resources/localization.html

The localized content are stored in folders that reflect / represent the language and possibly country codes for a given locale. For instance `values-DE` includes the German language translation.

Here are some examples of existing localization for the DAISY Reader project:

|Language|Online file containing the translated strings|Downloadable 'raw' file for editing|
|:-------|:--------------------------------------------|:----------------------------------|
|German|http://code.google.com/p/android-daisy-epub-reader/source/browse/trunk/res/values-de/strings.xml|http://android-daisy-epub-reader.googlecode.com/svn/trunk/res/values-de/strings.xml|
|Norwegian|http://code.google.com/p/android-daisy-epub-reader/source/browse/trunk/res/values-nb/strings.xml|http://android-daisy-epub-reader.googlecode.com/svn/trunk/res/values-nb/strings.xml|
|Default Values (US English|http://code.google.com/p/android-daisy-epub-reader/source/browse/trunk/res/values/strings.xml|http://android-daisy-epub-reader.googlecode.com/svn/trunk/res/values/strings.xml|

Android will use the most appropriate translation provided by our software, the DAISY Reader. It does this at the level of individual sentences or phrases (each item in the list of strings). If it cannot find an item in the set of strings, it will use a more general value, and if necessary use the default item.

# How to edit the file for an existing language #
Here are a set of sequential steps which should be enough to download and edit a file for an existing language. I will explain how to send the updated file later in this document.
  1. Download the 'raw' file, using the appropriate link from the table above, and save the file in a known location on your computer where you'll be able to find it later.
  1. Open the downloaded file in a text editor, for Microsoft Windows, the basic Notepad editor is better than using a word processor as these may corrupt the format of the file.
  1. Add new terms & Edit existing terms. See the notes below for details of how to edit the file.
  1. Save the changes on your local machine.

## How to edit the strings file ##
The lines to edit follow a consistent format: `<string name="a_unique_name">The text that will be used</string>` You will need to edit the text between the `>` and `<` characters, don't change anything else otherwise the file will be hard or even impossible for us to use.

If you would like to add a new item e.g. because you noticed a row in the default file that doesn't exist in language-specific file: you can follow a similar process to the one we have just described, however you first need to add a line to the file that is formatted correctly and contains the relevant 'name' for the row you want to change. You can either add the item directly using the text editor, or copy and paste the relevant line from the default `strings.xml` file.

You now need to change the default text to a concise, clear translation. Then save the file, as before.

# Creating the strings file for a language we don't currently have #
Creating the strings file for a new language is very similar to the process for editing an existing file. The main change is that you start by downloading the default file http://android-daisy-epub-reader.googlecode.com/svn/trunk/res/values/strings.xml You will then need to change the text for as many of the items as you can. If you don't have translations for some of the items in the rows, don't worry. Once you have sent the file to the project, and it's been added to the project, other people may be able to help provide these missing items.

# Providing the file to the project #
Once you have made the changes you consider appropriate, there are several ways to provide it to the project so we can include it with the main DAISY Reader application. Here are 2 ways that should work well:

## Creating a new Issue on this site ##
_You will need to have an account on the Google system to use this feature._

Create a new 'Issue' using the Issues tab on this project site.
The link to create a new issue is http://code.google.com/p/android-daisy-epub-reader/issues/entry Please enter the following text in the 'summary' field 'Translation file for language ...' where you enter the specific language (or locale if you happen to know the correct format e.g. DE for German).

Now use the 'Attach a file' link on the web form and attach your updated file.

In the Description text box please summarize your work e.g. which locale do you think the file is suitable for e.g. French and briefly explain any work you think the file would benefit from e.g. translating lines you weren't able to do.

## Email the file ##
You can email the file directly to me julianharty - at - gmail - dot - com

We will then try to integrate your work into the main project and release a new version of the DAISY Reader Android application.

# Receiving updates from the project team #
If you ended up submitting an issue, then 'star' the Issue once you've created it and you'll automatically receive updates as we work on the issue. If we have your email address I'm happy to email you directly. We may also announce new versions of the software in the project's discussion group http://groups.google.com/group/android-daisy-epub-reader-project

# Receiving public credit for your work. #
Tell us in the issue or email if you'd like credit for your work and we'll do so if we use your work in the project.

# Tips #
  * Online translation tools such as http://translate.google.com may help you with your translation work. While the results may not be sufficiently accurate to allow auto-translation they're usually good enough to provide some clues to you.
  * Please try to keep the translations accurate and concise.

# Further reading #
I like the succinct description on how to translate Android applications at http://code.google.com/p/secrets-for-android/wiki/Translating you might find it helps provide another perspective on the process.

The following link introduces open translation tools might help some of you (and me) to improve our translations http://en.flossmanuals.net/open-translation-tools/ch034_introduction/

And here's a web page containing recommendations for various words and terms often used in mobile phone apps which can help us to pick more appropriate translations for the languages it supports http://www.icanlocalize.com/site/tutorials/mobile-app-keywords/

Another reference is the following list of Android Supported Language and Locales http://colincooper.net/?p=238 I found some discrepancies e.g. several of the locales are available in Android 2.1 on the Xperia X8 (which has lots of useful locales available to use)

Here is the wikipedia page for the 2 character language codes which Android uses to locate the relevant language file http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes
And the ISO country codes http://www.iso.org/iso/country_codes/iso_3166_code_lists/country_names_and_code_elements.htm

And the following web page contains some useful information that helps with the translation process http://www.wilsonmar.com/android_localization.htm

One for the developers, how to support plurals http://www.kaloer.com/android-plurals
# Comments #
Please comment on these instructions and the process at the foot of this wiki page.