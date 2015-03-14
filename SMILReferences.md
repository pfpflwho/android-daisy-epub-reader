# Introduction #

I'm having to learn how to correctly interpret and process DAISY content with various types of content beyond the current support for audio e.g. for text content.

This page contains some links and comments to help us implement better support for the various types of SMIL elements.

# Thoughts on design and implementation #
A SMIL element in java should know how to 'read' itself. The read action may depend on the type of element e.g. for audio the audio would be played, whereas text may be displayed, and optionally 'spoken' using text-to-speech.

# External references #

|URL|Description|
|:--|:----------|
|http://www.w3schools.com/smil/|Tutorial on SMIL|
|http://marvel.incheon.ac.kr/infomation/SMIL/smil-tutorial/toc.html|A walkthrough of SMIL|
|http://www.w3.org/AudioVideo/#Specificat|w3c specifications for SMIL 1.0, 2.1, 2.0, and 3.0|
|http://www.w3.org/TR/REC-smil/|Detailed specification of SMIL 1.0|
|http://www.w3.org/TR/smil-boston-dom/java-binding.html|Java language bindings for SMIL 1.0 from the w3c|
|http://www.x-smiles.org/xsmiles_smil.html|SMIL 2.0 implementation in Java with source code and sample applications|
|http://www.dcs.gla.ac.uk/~jj/teaching/demms4/slides/l5-smil-1.pdf|Useful tutorial on how to create SMIL files from scratch.|