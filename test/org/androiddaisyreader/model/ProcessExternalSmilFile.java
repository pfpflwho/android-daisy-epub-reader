package org.androiddaisyreader.model;

import static org.androiddaisyreader.model.XmlUtilities.obtainEncodingStringFromInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

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
			CommandLineUtilities.printUsage("ProcessExternalSmilFile");
			System.exit(1);
		}
		
		StringBuilder filename = new StringBuilder();
		
		// To help cope with spaces in the filename e.g. on my windows machine.
		for (int i = 0; i < args.length; i++) {
			filename.append(args[i]);
		}
		
		InputStream contents = new FileInputStream(filename.toString());
		String encoding = obtainEncodingStringFromInputStream(contents);

		File directory = new File(filename.toString());
		
		BookContext bookContext = new FileSystemContext(directory.getParent());
		
		Daisy202Section section = new Daisy202Section.Builder()
			.setHref(directory.getName())
			.setContext(bookContext)
			.build();
		
		directory = null;

		for (Part part : section.getParts()) {
			for (int j = 0; j < part.getSnippets().size(); j++) {

				String text = part.getSnippets().get(j).getText();
				String id = part.getSnippets().get(j).getId();

				if (part.getAudioElements().size() > 0) {
					Audio audio = part.getAudioElements().get(0);
					System.out.printf(" [%s]: %s < Show text for %f seconds => %s", 
							id, 
							audio.getAudioFilename(), 
							audio.getClipEnd() - audio.getClipBegin(),	
							text);
				} else {
					System.out.printf(" [%s]: => %s", id, text);
				}
			}
		}
		// TODO 20120207 (jharty): consider checking the section contents here.
		System.out.println("\nparsed file " + args[0] + " without error");
		System.exit(0);

	}

}
