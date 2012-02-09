package org.androiddaisyreader.model;

import static org.androiddaisyreader.model.XmlUtilities.obtainEncodingStringFromInputStream;

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
			input.setEncoding(encoding);
			saxParser.parse(input);
			Section section = smil.build();
			// TODO 20120207 (jharty): consider checking the section contents here.
			System.out.println("parsed file " + args[0] + " without error");
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
