package org.androiddaisyreader.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.androiddaisyreader.model.Part.Builder;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * Parser to handle SMIL 1.0 files used by Daisy 2.02 books.
 * 
 * @author jharty
 */
public class Smil10Specification extends DefaultHandler {
	
	private Element current;
	private Part.Builder partBuilder;
	private Daisy202Section.Builder sectionBuilder;
	private BookContext context;
	
	boolean handlingPar = false;

	public Smil10Specification(BookContext context) {
		this.context = context;
		sectionBuilder = new Daisy202Section.Builder();
	}
	
	public Section build() {
		return sectionBuilder.build();
	}
	
	@Override
	public void endElement(String uri, String localName, String name)
		throws SAXException {
		
		current = elementMap.get(ParserUtilities.getName(localName, name));
		if (current == null) {
			return;
		}
		
		switch (current) {
			case PAR:
				handlingPar = false;
				addPartToSection();
				break;
			case SEQ:
				// do nothing
				break;
			case AUDIO:
			case TEXT:
				if (!handlingPar) {
					addPartToSection();
				}
			default:
				break;
		}
	}
	
	private void addPartToSection() {
		sectionBuilder.addPart(partBuilder.build());
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) {
		current = elementMap.get(ParserUtilities.getName(localName, name));
		if (current == null) {
			return;
		}
		
		switch (current) {
			case AUDIO:
				if (!handlingPar) {
					newPart();
				}
				handleAudio(attributes);
				break;
			case META:
				handleMeta(attributes);
				break;
			case PAR:
				handlingPar = true;
				handlePar(attributes);
				break;
			case SEQ:
				// do nothing.
				break;
			case TEXT:
				if (!handlingPar) {
					newPart();
				}
				handleTextElement(attributes);
				break;
			default:
				// Record the element(s) we don't handle in case we can improve our processing of smil files.
				recordUnhandledElement(current, attributes);
				break;
		}
	}
	
	private void handlePar(Attributes attributes) {
		newPart();
		String id = ParserUtilities.getValueForName("id", attributes);
		partBuilder.setId(id);
	}

	private void newPart() {
		partBuilder = new Part.Builder();
	}

	/**
	 * Handle the Text Element.
	 * 
	 * The text element stores the location of a text fragment in an id
	 * attribute.
	 * @param attributes
	 */
	private void handleTextElement(Attributes attributes) {
		String id = ParserUtilities.getValueForName("id", attributes);
		String src = ParserUtilities.getValueForName("src", attributes);
		// TODO 20120207 (jharty) Refactor for a text reference into a html file
		// Create HTML Snippet Reader
		partBuilder.addSnippet(new Daisy202Snippet(context, src));
	}

	private void recordUnhandledElement(Element element, Attributes attributes) {
		StringBuilder elementDetails = new StringBuilder();
		elementDetails.append(String.format("[%s ", element.toString()));
		for (int i = 0; i < attributes.getLength(); i++) {
			elementDetails.append(
					String.format("%s=%s", 
							attributes.getLocalName(i), 
							attributes.getValue(i)));
			 }
		elementDetails.append("]");
	}

	private void handleAudio(Attributes attributes) {
		// <audio src="file.mp3" clip-begin="npt=0.000s" clip-end="npt=3.578s" id="audio_0001"/>
		String audioFilename = ParserUtilities.getValueForName("src", attributes);
		double clipBegin = extractTiming("clip-begin", attributes);
		double clipEnd = extractTiming("clip-end", attributes);
		String id = ParserUtilities.getValueForName("id", attributes);
		
		Audio audio = new Audio(id, audioFilename, clipBegin, clipEnd);
		partBuilder.addAudio(audio);
	}
	
	private double extractTiming(String value, Attributes attributes) {
		String rawValue = ParserUtilities.getValueForName(value, attributes);
		String trimmedValue = rawValue.replace("npt=", "").replace("s", "");
		return Double.parseDouble(trimmedValue);
	}

	private void handleMeta(Attributes attributes) {
		String metaName = null;
		
		for (int i = 0; i < attributes.getLength(); i++) {
			String name = attributes.getLocalName(i);
			if (name.equalsIgnoreCase("name")) {
				metaName = attributes.getValue(i);
			 }
			
			if (name.equalsIgnoreCase("content")) {
			}
		}
		
		Meta meta = metaMap.get(metaName);
		if (meta == null) {
			return;
		}
		
		switch (meta) {
		case FORMAT:
			// TODO 20120207 (jharty): store the format.
			break;
		default:
			break;
		}
	}

	private enum Element {
		AUDIO,
		META,
		PAR,
		SEQ,
		TEXT;
		@Override
		public String toString() {
			return this.name().toLowerCase();
		}
	}
	
	private static Map <String, Element> elementMap = new HashMap<String, Element>(Element.values().length);
	static {
		for (Element e : Element.values()) {
			elementMap.put(e.toString(), e);
		}
	}
	
	private enum Meta {
		FORMAT {
			@Override
			public String toString() {
				return "dc:format";
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

}
