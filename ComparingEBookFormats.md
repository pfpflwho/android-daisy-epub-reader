# Introduction #

Since we need to write code that will correctly detect and process several ebook specifications this wiki page will be where we record our understanding of the key attributes and differences of the various formats of ebook publishing formats. Please don't expect this material to be definitive or necessarily accurate, we're learning as we go!

# Details #

|Attribute|DAISY 202|DAISY 3|epub|
|:--------|:--------|:------|:---|
|Packaging (1)|folder of files|folder of files|a single zip file with a filename suffix of .epub|
|Start with |ncc.html|_an opf file_ (I don't know if there is a specific filename.) |content.opf|
|mimetype file |  |  |Required at a specific offset in the zip file|
|xmlns (XML namespace)|html xmlns="http://www.w3.org/1999/xhtml"|package xmlns="http://openbook.org/namespaces/oeb-package/1.0/" A DOCTYPE of type package is included in the opf file. (There is no DOCTYPE in the content.opf in an epub book AFAIK.)|package xmlns="http://www.idpf.org/2007/opf" version="2.0"|
|information about the book |Stored in the 

&lt;head&gt;

 and includes 

&lt;title&gt;

 and various 

&lt;meta name=...&gt;

 tags.|Stored in the 

&lt;metadata&gt;

 element which may contain subsidiary <...-metadata> elements to group further tags of the form <...:.....> elements e.g. 

&lt;dc:Creator&gt;

, 

&lt;dc:Description&gt;

, etc. (Note: in the examples from gh-accessibility.com the first letter after : is Capitalized, while epub content.opf seems to use lower-case tags.)|Seems similar to DAISY 3, the differences I've observed include: 1) no grouping tags e.g. no 

&lt;dc-metadata&gt;

 or 

&lt;x-metadata&gt;

 tags. 2) tag names are in lowercase.|
|Version-specific meta-data|starts with `ncc:`|starts with `dtb:`|TBD|
|SMIL files |Essential, contain details of the book's content. Contents are formatted using: "-//W3C//DTD SMIL 1.0//EN" "SMIL10.dtd"|Essential, contain details of the book's content. Contents are formatted using: http://www.w3.org/2001/SMIL20/ |Not generally used. There are works in progress to include SMIL files in EPUB format e.g. as part of EPUB version 3. See http://code.google.com/p/epub-revision/wiki/MediaOverlaySpec for more details. |
|  |  |  |  |
(1) perhaps we could add the ability to process a book compressed in a zip file for DAISY 202 and DAISY 3? since we will do something similar for epub...

## DAISY 2.02 ##
Daisy 2.02 essentially uses html markup with a few tweaks. meta data about the book is contained in the `<head>` tag. The book's structure is contained in the `<body>` where each item has a 'level' represented in a `<h>` tag e.g. the first level is in a `<h1>` tag, the second level in a `<h2>` tag, etc. <span> elements contain information about pages of the book.<br>
<br>
<h2>DAISY 3</h2>
DAISY 3 has a structure that aims to represent a digital book more faithfully than DAISY 2.02 did. Everything starts from a single file with an extension of .opf This opf file contains a number of elements including:<br>
<ul><li><code>&lt;metadata&gt;</code> (stored in the <code>&lt;head&gt;</code> tag of DAISY 2.02).<br>
</li><li><code>&lt;x-metadata&gt;</code> which seems to be optional, and contains extra meta data of which dtb:totalTime seems of interest to users.<br>
</li><li><code>&lt;manifest&gt;</code> which lists the rest of the files that comprise the book e.g. the ncx<br>
</li><li><code>&lt;spine&gt;</code>
</li><li><code>&lt;guide&gt;</code> also seems to be optional.</li></ul>

<h3>Differences between DAISY 3 and DAISY 2.02</h3>
Here's a useful external (and authoritative) summary of key differences between DAISY 2.02 and DAISY 3 formats.<br>
<br>
<a href='http://www.daisy.org/daisypedia/difference-between-daisy-2daisy-2.02-and-daisy-3'>http://www.daisy.org/daisypedia/difference-between-daisy-2daisy-2.02-and-daisy-3</a>

<h2>epub</h2>
Very similar to DAISY 3, with some slight differences - possibly intended to simplify the processing, or to tighten-up possible ambiguities?<br>
<br>
The epub content is contained in a single 'container' file, with a file extension of <code>.epub</code> The container stores the remaining files using the zip file format.<br>
<br>
Within the container are various files, including 2 manadatory files. These mandatory files are:<br>
<ol><li>The mimetype file, at offset 14 in the container file<br>
</li><li>The 'container' file which points to another content file <code>META-INF/content.xml</code> This contains a reference to the location of content.opf which is stored in another folder within the 'zip' file.</li></ol>

Essentially the epub file also always contains another couple of files, including:<br>
<ul><li>content.opf<br>
</li><li>An .ncx file (the filename is specified in <code>content.opf</code> in <code>&lt;manifest&gt;&lt;item id="ncx" href="_filename_.ncx"&gt;</code> (Here <i>filename</i> represents the filename for the ncx file.)</li></ul>

<h1>Implementation ideas</h1>
Here are some rough notes on how we might handle the various forms of digital book in the program. They're intended for the programmers; we're likely to make numerous changes as we implement the design in the actual code - particularly as we learn more about how books are actually structured by various publishers and software tools.<br>
<br>
<h2>Thoughts</h2>
<ul><li>Should we add strict pre-conditions in the code to assert our understanding? The main advantage would be to find differences, anomalies and quirks quickly and force us to address them soon(er). The disadvantage would be that the software might refuse to read books that it could cope with.<br>
</li><li>Should we change the Java class structure to have an object that represents a generic <code>DigitalBook</code> where we then sub-class this object for each type of digital book? (In practice we may use a Java Interface and classes that implement it, rather than sub-classes).<br>
</li><li>How should we cope with zip compression e.g. should it be explicitly represented in the <code>DigitalBook</code> object? Do we wrap all File IO calls to seamlessly cope with content in a zip file? Does the Java.util.zip cope with uncompressed files? By analogy, Windows XP etc treats zip files as folders in Windows Explorer...</li></ul>

<h1>Further reading</h1>
Elizabeth Castro's <i>EPUB Straight to to the Point</i> book is a useful and readable guide to some of the nuances and intricacies of creating EPUB content. See her site <a href='http://www.elizabethcastro.com/epub/'>http://www.elizabethcastro.com/epub/</a> for more information.