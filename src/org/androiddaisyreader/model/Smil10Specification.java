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
	private Stack<Part.Builder> partStack = new Stack<Part.Builder>();
	Smil.Builder smilBuilder = new Smil.Builder();
	private Daisy202Section parent;  // Our link to whoever or whatever our parent is

	Smil10Specification(Daisy202Section parent) {
		this.parent = parent;
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
			case SEQ:
				handleEndOfNestedGroup(current);
				break;
			default:
				break;
		}
	}
	private void handleEndOfNestedGroup(Element element) {
		Builder builder = partStack.pop();
		// Do we need to add other items gathered between our start and end tags? I think not...
		Part part = builder.build();
		// TODO We need to connect to our parent (in a Section or a nested part)
		if (partStack.empty()) {
			parent.setPart(part);
		}
	}
	
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) {
		current = elementMap.get(ParserUtilities.getName(localName, name));
		if (current == null) {
			return;
		}
		
		switch (current) {
			case AUDIO:
				handleAudio(attributes);
				break;
			case META:
				handleMeta(attributes);
				break;
			case PAR:  // fall through to SEQ
			case SEQ:
				handleStartOfNestedElement(current, attributes);
				break;
			case TEXT:
				handleTextElement(attributes);
				break;
			default:
				// Record the element(s) we don't handle in case we can improve our processing of smil files.
				recordUnhandledElement(current, attributes);
				break;
		}
	}
	
	/**
	 * Handle the Text Element.
	 * 
	 * The text element stores the location of a text fragment in an id
	 * attribute.
	 * @param attributes
	 */
	private void handleTextElement(Attributes attributes) {
		String location = ParserUtilities.getValueForName("id", attributes);
		partStack.peek().addTextElement(location);
		
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
		partStack.peek().addUnhandledElement(elementDetails.toString());
	}

	private void handleAudio(Attributes attributes) {
		// <audio src="file.mp3" clip-begin="npt=0.000s" clip-end="npt=3.578s" id="audio_0001"/>
		String audioFilename = ParserUtilities.getValueForName("src", attributes);
		double clipBegin = Double.parseDouble(ParserUtilities.getValueForName("clip-begin", attributes));
		double clipEnd = Double.parseDouble(ParserUtilities.getValueForName("clip-end", attributes));
		String id = ParserUtilities.getValueForName("id", attributes);
		
		// TODO 20120201 (jharty): temporary debug, we need to add the attributes to the parent element.
		System.out.println(audioFilename);
		Audio audio = new Audio();
		audio.setFilename(audioFilename);
		audio.setClipTimings(clipBegin, clipEnd);
		audio.setId(id);
		partStack.peek().addAudio(audio);
	}
	
	private void handleMeta(Attributes attributes) {
		String metaName = null;
		String content = null;
		
		for (int i = 0; i < attributes.getLength(); i++) {
			String name = attributes.getLocalName(i);
			if (name.equalsIgnoreCase("name")) {
				metaName = attributes.getValue(i);
			 }
			
			if (name.equalsIgnoreCase("content")) {
				content = attributes.getValue(i);
			}
		}
		
		Meta meta = metaMap.get(metaName);
		if (meta == null) {
			return;
		}
		
		switch (meta) {
		case FORMAT:
			smilBuilder.setFormat(content);
			break;
		default:
			break;
		}
	}

	private void handleStartOfNestedElement(Element element, Attributes attributes) {
		// TODO 20120201 (jharty): Decide whether to build a nested structure.
		String id = ParserUtilities.getValueForName("id", attributes);
		// The nested elements need adding to this structure, maybe as children?
		Part.Builder builder = new Part.Builder();
		builder.setTimingMode(element.toString());
		builder.setId(id);
		// Do we need to set or add anything else before adding this builder to the stack?
		partStack.push(builder);
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
