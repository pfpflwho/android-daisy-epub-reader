# Introduction #

The team met on January 8th 2010 to review the current UI (Daisy Reader release 82). We discussed the next steps for the user experience and user interaction to get better results for users to easily: find, access, navigate, and read audio books. This is our UX Proposal, based on the meeting minutes and discussions.

# First Time Installation #

When users launches the application for the first time, a welcome splash page will display a dialog, offering welcoming words and top quick tips to explain how the application works. A Checkbox control will be offered for users to turn off this dialog when application starts next time. This dialog can be accessed again via the Help button from the application menu.

Closing this dialog, a simple Home menu will be offered on the screen:

  * Open new book
  * Search for books
  * Open last book

## Open book ##

Open book link will show the SD card file manager to enable the user to navigate to the folder containing books. Soon, we would like the file manager to search and give list of books available on the storage automatically.

## Search ##

Search was considered for near future, where the Daisy Reader can consume data from public APIs offering daisy books for free download, or offering bookmark list of sites listing free books. Lists will natively be rendered in the Daisy UI, so users can easily pick, and download, the book of interest.

## Open last book ##

Open last book should offer option the reader to remember the last book the user was reading and open the book on the place where last left.

# Android Buttons #

## Back ##

Android Back button will work consistently with the Android platform. If the users in reading the book, the back button will take users back to Content, back to File List or Search Results, back to screen from where application was launched (say you opened book from Email or Browser), or to Android Home screen if no previous action was logged.

## Menu ##

### When on home page ###

  * Open book
  * Search for books
  * Open last book
  * Settings
  * Help

### When reading a book ###

  * Home
  * Contents
  * Add Bookmark
  * View bookmarks
  * Search (text search, provided the text is included in the 'talking book')
  * Help
  * (Note we need to include a way to remove obsolete bookmarks)
  * The user should also be able to navigate up and down the hierarchy of a DAISY book (assuming the book includes one or more levels in the hierarchy as some books don't have a hierarchy in the contents)

## Search ##

  * Android Search Button will trigger the web or file search UI also accessible from the home page.
  * In reading mode should bring up the search book option.

# Reading a book #

Once book is downloaded or copied on the SD Card, and users open the book from the home menu as new, the Reader will show the content and read the book credits - "About the book".

If the user is returning to the book, Reader stars reading from the last "page" user left.

Team discussed should the navigation mimic the traditional Daisy players, something current users are used to, or adopt fresh approach for the touchscreen device. Based on the idea that the Android Daisy Reader should be universally designed for all users, the team decided on the later.

## Navigating Contents ##

Daisy books are XML files, so the team was exploring best logical way to navigate the tree.

### Gestures ###

  * Swipe up (previous sibling)
  * Swipe Down (next sibling)
  * Swipe Right (first child)
  * Swipe Left (parent)

(Team, please review this if I captured the discussion about navigation correctly)

Same navigation logic will apply when users are reading at any level of the book.