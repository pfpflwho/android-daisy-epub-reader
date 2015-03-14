# Introduction #

We would like to share various design notes online as part of this project, partly to help refine our thinking and partly to try and help other people who may want to adopt or adapt our work. Here is the design algorithm for finding suitable eBooks on local storage (i.e. directly stored on the phone or removable storage such as a memory card).

# Details #

## Basic steps ##
Starting with /sdcard/ obtain a list of the contents. The contents may include:
  * folders (also known as sub-directories)
  * zip files (which will be treated a compressed book)
  * ncc.html, the starting point for a DAISY 2.02 book. Note: the case of the filename is ignored for DAISY 2.02 books.
  * content.opf, the starting point for a DAISY 3 book.
  * epub files (which are essentially DAISY 3 books packed - zipped - in a specific format)
  * other files (which we want to ignore) e.g. photos, music, other documents, etc.

For each item in the list of contents we apply several checks; if these checks pass without problems then the item is added to the list of contents (which we can treat as a 'bookshelf' metaphor). The checks for each type of item include:
  * For a folder, check whether it contains ncc.html or content.opf. We need to decide what to do if it includes both files. For now we will simply include the folder in the list.
  * For a zip file, uncompress it and treat the contents as if they represent a folder i.e. check if it contains ncc.html or content.opf. Add it to the list if it does contain either, else skip it.
  * For ncc.html add it to the list. We don't need to provide more information at this stage.
  * For content.opf add it to the list.  We don't need to provide more information at this stage.
  * For an epub file: read the bytes at a fixed offset (14?) which should represent the first file in the zip file - the content type. See the epubcheck file for more details on what and how to check.

## Usability Enhancements ##
Once the basics are in place, we can add some enhancements to the code, for example to extract details of the book that may be useful to the reader, such as the title, author, etc. We can also check whether the book is well formed i.e. whether we expect the program would be able to read the contents of the book without major problems.

## Technical enhancements ##
We expect there will be problems with our program's ability to process various books (mistakes we make) and with the contents (problems with the book file(s)). To improve the program, and to help problems to be fixed, we would like to collect more information on the problem(s). Potentially the information could be emailed from the device, where the email is prepared by the program ready for the user to review and send if they see fit. Any such feature would need to comply with appropriate privacy and user preferences.

# Assumptions and limitations #
  * We are currently assuming books are only stored on the removable memory card, addressed as /sdcard/ by the Android file system.