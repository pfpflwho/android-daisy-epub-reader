package org.androiddaisyreader.model;

import static org.androiddaisyreader.model.XmlUtilities.obtainEncodingStringFromInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class ProcessExternalSmilFile {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			printUsage("ProcessExternalSmilFile");
			System.exit(1);
		}
		
		StringBuilder filename = new StringBuilder();
		
		// To help cope with spaces in the filename e.g. on my windows machine.
		for (int i = 0; i < args.length; i++) {
			filename.append(args[i]);
		}
		
		InputStream contents = new FileInputStream(filename.toString());
		String encoding = obtainEncodingStringFromInputStream(contents);
		Smil10Specification smil = new Smil10Specification();
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		XMLReader saxParser;
		try {
			saxParser = factory.newSAXParser().getXMLReader();
			saxParser.setEntityResolver(XmlUtilities.dummyEntityResolver());
			saxParser.setContentHandler(smil);
			InputSource input = new InputSource(contents);
			// TODO 20120209 (jharty) need to map unsupported windows code page.
			input.setEncoding(encoding);
			saxParser.parse(input);
			Section section = smil.build();
			if (section.navigables.size() > 0) {
				// TODO 20120209 (jharty): replace this mess with cleaner code
				// once I've worked out ways to get the text we're looking for...
				System.out.printf("*** There are [%d] navigables\n", section.navigables.size());
				for (int i = 0; i < section.navigables.size(); i++) {
					Part part = (Part) section.navigables.get(i);
					for (int j = 0; j < part.getSnippets().size(); j++) {
						String snippetReference = part.getSnippets().get(j).getText();
						String[] elements = snippetReference.split("#");
						File smilFile = new File(filename.toString());
						String snippetFilename = smilFile.getParent()
								+ File.separatorChar + elements[0];
						String id = elements[1];
						File fileToReadFrom = new File(snippetFilename);
						FullText fullText = new FullText();
						StringBuilder fileContents = fullText
							.getContentsOfHTMLFile(fileToReadFrom);
						Document processedContents = fullText
							.processHTML(fileContents.toString());
						Element element = processedContents.getElementById(id);
						if (part.getAudioElements().size() > 0) {
							Audio audio = part.getAudioElements().get(0);
							System.out.printf(" [%s]: %s < Show text for %f seconds => %s", 
								id, 
								audio.getAudioFilename(), 
								audio.getClipEnd() - audio.getClipBegin(),	
								element.text());
						} else {
							System.out.printf(" [%s]: => %s", id, element.text());
						}
					}
				}
			} else {
				System.out.println("No text found in this smil file.");
			}
			// TODO 20120207 (jharty): consider checking the section contents here.
			System.out.println("\nparsed file " + args[0] + " without error");
			System.exit(0);
		} catch (SAXException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	private static void printUsage(String programName) {
		System.out.println("Usage: \n\t" + programName + "smilfilename");
	}

}
