package org.androiddaisyreader.model;

import static org.androiddaisyreader.model.XmlUtilities.obtainEncodingStringFromInputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.SAXParserFactory;

import org.androiddaisyreader.model.Daisy202Section.Builder;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class NccSpecification extends DefaultHandler {
	private Element current;
	private Stack<Daisy202Section.Builder> headingStack = new Stack<Daisy202Section.Builder>();  // TODO 20120124 (jharty): replace with something that doesn't use Vector
	private StringBuilder buffer = new StringBuilder();
	private static Integer NUM_LEVELS_AVAILABLE_IN_DAISY202 = 6;
	
	Daisy202Book.Builder bookBuilder = new Daisy202Book.Builder();
	
	private enum Element {
		META,
		TITLE,
		H1,
		H2,
		H3,
		H4,
		H5,
		H6,
		SPAN;
		@Override public String toString() {
			return this.name().toLowerCase();
		}
	}
	
	private static Map <String, Element> elementMap = new HashMap<String, Element>(Element.values().length);
	static {
		for (Element e : Element.values()) {
			elementMap.put(e.toString(), e);
		}
	}
	
	private static Map <Element, Integer> levelMap = new HashMap<Element, Integer>(NUM_LEVELS_AVAILABLE_IN_DAISY202);
	static {
		levelMap.put(Element.H1, 1);
		levelMap.put(Element.H2, 2);
		levelMap.put(Element.H3, 3);
		levelMap.put(Element.H4, 4);
		levelMap.put(Element.H5, 5);
		levelMap.put(Element.H6, 6);
	}
	
	private enum Meta {
		TITLE {
			@Override
			public String toString() {
				return "dc:title";
			}
		},
		CREATOR {
			@Override
			public String toString() {
				return "dc:creator";
			}
		},
		LANGUAGE {
			@Override
			public String toString() {
				return "dc:language";
			}
		},
		CHARACTERSET {
			@Override
			public String toString() {
				return "ncc:charset";
			}
		},
		DATE {
			@Override
			public String toString() {
				return "dc:date";
			}
		}
		// Add more enums as we need them.
	}
	
	private static Map <String, Meta> metaMap = new HashMap<String, Meta>(Meta.values().length);
	static {
		for (Meta m : Meta.values()) {
			metaMap.put(m.toString(), m);
		}
	}
	
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) {
		current = elementMap.get(getName(localName, name));
		if (current == null) {
			return;
		}
		
		switch (current) {
			case H1:
			case H2:
			case H3:
			case H4:
			case H5:
			case H6:
				buffer.setLength(0);
				handleStartOfHeading(current, attributes);
				break;
			case META:
				handleMeta(attributes);
				break;
			case SPAN:
				// TODO 20120124 (jharty): We need to handle page numbers at some point.
				break;
			default:
				// do nothing for now for unmatched elements
				break;
		}
	}
	
	private void handleStartOfHeading(Element heading, Attributes attributes) {
		Daisy202Section.Builder builder = new Daisy202Section.Builder();
		builder.setId(getId(attributes));
		builder.setLevel(levelMap.get(heading));
		
		if (!headingStack.empty()) {
			Daisy202Section.Builder previous = headingStack.peek();
			
			while (!headingStack.isEmpty() && previous.getLevel() >= levelMap.get(heading)) {
				attachSectionToParent();
			}
		}
		headingStack.push(builder);
	}

	private void attachSectionToParent() {
		Daisy202Section.Builder sibblingBuilder = headingStack.pop();
		Section sibbling = sibblingBuilder.build();
		if (headingStack.empty()) {
			bookBuilder.addSection(sibbling);
		} else {
			headingStack.peek().addSection(sibbling);
		}
	}

	private String getId(Attributes attributes) {
		for (int i = 0; i < attributes.getLength(); i++) {
			String name = attributes.getLocalName(i);
			if (name.equalsIgnoreCase("id")) {
				return attributes.getValue(i);
			}
		}
		return null;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		buffer.append(ch, start, length);
	}
	
	@Override
	public void endElement(String uri, String localName, String name)
	throws SAXException {
		// add current element type to the book model.
		current = elementMap.get(getName(localName, name));
		if (current == null) {
			return;
		}
		
		switch (current) {
			case H1:
			case H2:
			case H3:
			case H4:
			case H5:
			case H6:
				handleEndOfHeading(current);
				break;
			default:
				break;
		}
	}
	
	private void handleEndOfHeading(Element current) {
		Builder currentBuilder = headingStack.peek();
		int levelOnStack = currentBuilder.getLevel();
		Integer currentLevel = levelMap.get(current);
		if (levelOnStack != currentLevel) {
			throw new IllegalStateException(
					String.format("Expected the same level as [%s] found [%s]", 
							currentLevel, levelOnStack, currentBuilder));
		}

		currentBuilder.setTitle(buffer.toString());
	}

	private void handleMeta(Attributes attributes) {
		String metaName = null;
		String content = null;
		String scheme = null;
		
		for (int i = 0; i < attributes.getLength(); i++) {
			String name = attributes.getLocalName(i);
			if (name.equalsIgnoreCase("name") || name.equalsIgnoreCase("Content-type")) {
				metaName = attributes.getValue(i);
			}
			
			if (name.equalsIgnoreCase("content")) {
				content = attributes.getValue(i);
			}
			
			if (name.equalsIgnoreCase("scheme")) {
				scheme = attributes.getValue(i);
			}
		}
		
		
		Meta meta = metaMap.get(metaName);
		if (meta == null) {
			return;
		}
		
		switch (meta) {
			case DATE:
				Date date = parseDate(content, scheme);
				bookBuilder.setDate(date);
				break;
			case TITLE:
				bookBuilder.setTitle(content);
				break;
			default:
				// this handles null (apparently :)
		}
	}

	private Date parseDate(String content, String scheme) {
		String format = scheme.replaceAll("m", "M");
		DateFormat formatter =  new SimpleDateFormat(format);
		try {
			return formatter.parse(content);
		} catch (ParseException pe) {
			throw new IllegalArgumentException(String.format("Problem parsing the date[%s] using scheme [%s]",
					content, scheme), pe);
		}
		
	}

	// Possible bug between Android and Java...
	// On Android the element name is returned in localName, on the
	// workstation it's returned in 'name'
	// Adding a temporary workaround until I understand what's happening!
	private String getName(String localName, String name) {
		if (localName.length() > 0 ) {
			return localName;
		}
		return name;
	}
	
	public Daisy202Book build() {
		return bookBuilder.build();
	}

	static Daisy202Book readFromFile(File file) throws IOException {
		InputStream contents = new BufferedInputStream(new FileInputStream(file));
		String encoding = obtainEncodingStringFromInputStream(contents);
		return readFromStream(contents, encoding);
	}
	
	static Daisy202Book readFromStream(InputStream contents, String encoding) throws IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		NccSpecification specification = new NccSpecification();
		try {
			XMLReader saxParser = factory.newSAXParser().getXMLReader();
			saxParser.setEntityResolver(XmlUtilities.dummyEntityResolver());
			saxParser.setContentHandler(specification);
			InputSource input = new InputSource(contents);
			input.setEncoding(encoding);
			saxParser.parse(input);
			return specification.build();
			
		} catch (Exception e) {
			throw new IOException("Couldn't parse the ncc.html contents.", e);
		}
	}

}
