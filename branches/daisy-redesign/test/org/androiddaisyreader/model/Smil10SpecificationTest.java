package org.androiddaisyreader.model;

import static org.androiddaisyreader.model.XmlUtilities.obtainEncodingStringFromInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import junit.framework.TestCase;

public class Smil10SpecificationTest extends TestCase {
	private static final String simpleSmil10Contents = 
		"<?xml version=\"1.0\" encoding=\"windows-1252\"?>" +
		"<!DOCTYPE smil PUBLIC \"-//W3C//DTD SMIL 1.0//EN\" \"http://www.w3.org/TR/REC-smil/SMIL10.dtd\">" +
		"<smil>" +
		"<head>" +
		"<meta name=\"dc:format\" content=\"Daisy 2.02\" />" +
		"</head>" +
		"<body>" +
		"<seq dur=\"1.3s\">" +
		"<par endsync=\"last\" id=\"par_12\">" +
		"<text src=\"dummy.html#s8\" id=\"i10\" />" +
		"</par>" +
		"</seq>" +
		"</body>" +
		"</smil>";
	
	public void testParsingOfSimpleSmil10Structure() throws IOException, SAXException, ParserConfigurationException {
		InputStream contents = new ByteArrayInputStream(simpleSmil10Contents.getBytes());
		String encoding = obtainEncodingStringFromInputStream(contents);
		Smil10Specification smil = new Smil10Specification();
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		XMLReader saxParser = factory.newSAXParser().getXMLReader();
		saxParser.setEntityResolver(XmlUtilities.dummyEntityResolver());
		saxParser.setContentHandler(smil);
		InputSource input = new InputSource(contents);
		input.setEncoding(encoding);
		saxParser.parse(input);
		Section section = smil.build();
		for (Navigable p : section.navigables) {
			System.out.println(p.toString());
		}
		assertEquals("Expected one part", 1, section.navigables.size());
		Part part = (Part) section.navigables.get(0);
		assertEquals("The part should contain one snippet", 1, part.getSnippets().size());
		// TODO 20120207 revise once we implement processing of the snippets.
		assertEquals("The snippet name is incorrect", "dummy.html#s8", part.getSnippets().get(0).getText());
		assertEquals("Currently we expect only one snippet.", 1, part.getSnippets().size());
	}

}
