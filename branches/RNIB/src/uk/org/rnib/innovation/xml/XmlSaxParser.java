package uk.org.rnib.innovation.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XmlSaxParser extends DefaultHandler {
	private InputStream is;
	private String targetName;
	private String targetId;
	private int depth;
	private int parsingStartDepth;
	private boolean parsing = false;
	private StringBuilder text;
	private XmlNode docElem;
	private XmlNode node;
	private Map<String, String> namespaces;

    @Override
	public void startPrefixMapping(java.lang.String prefix,
            java.lang.String uri) throws SAXException {
    	if (!namespaces.containsKey(uri))
    		namespaces.put(uri, prefix);
    }

    @Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		if (parsing)
			text.append(ch, start, length);
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		depth++;
		String prefix = namespaces.containsKey(uri) ? namespaces.get(uri) : null;
		String n = prefix != null && prefix.length() > 0 
			? prefix + ":" + localName : localName;
		/*
		if (((targetName != null) && (n.equals(targetName)))
			|| ((targetId != null) && (attributes.getValue("id").equals(targetName)))) {
			parsing = true;
			parsingStartDepth = depth;
		}
		*/
		if (parsing) {
			parsingStartDepth = depth;
			insert(new XmlNode(n, null, depth, attributes));
			text = new StringBuilder();
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String name)
	throws SAXException {
		if (parsing) {
			String value = text != null ? text.toString() : "";
			if (value.length() > 0) {
				insert(new XmlNode("#text", value, depth + 1, null));
				node = node.getParentNode();
			}
			node = node.getParentNode();
			/*
			if (depth == parsingStartDepth)
				parsing = false;
			*/
		}
		depth--;
	}
	
	private void insert(XmlNode newNode) {
		if (docElem == null)
			docElem = newNode;
		if (node == null)
			node = newNode;
		else {
			if (newNode.getDepth() > node.getDepth()) {
				node = node.addChild(newNode);
			}
			else if (newNode.getDepth() == node.getDepth()) {
				node = node.getParentNode().addChild(newNode);
			}
			else {
				node = node.getParentNode().getParentNode().addChild(newNode);
			}
		}
	}
	
	private EntityResolver dummyEntityResolver() {
		// Thanks to http://www.junlu.com/msg/202604.html 
		EntityResolver er = new EntityResolver() {
		    public InputSource resolveEntity(String publicId, String systemId)
		    {
		        return new InputSource(new StringReader(" "));
		    }
		};
		return er;
	}
	
	public void parse() throws IOException, SAXException, ParserConfigurationException{
		EntityResolver der = dummyEntityResolver();
		// TODO (jharty): Extract the encoding from the stream
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		// factory.setValidating(false);
		// factory.setFeature("http://xml.org/sax/features/validation", false);
		// factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		// factory.setFeature("http://xml.org/sax/features/use-entity-resolver2", false);
		// factory.setFeature("http://xml.org/sax/features/namespaces", false);
		// factory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);

		SAXParser saxParser = factory.newSAXParser();
    	XMLReader xmlReader = saxParser.getXMLReader();

		// The EntityResolver ensures the SAX parser can correctly locate
		// referenced entities e.g. xhtml1-strict.dtd from
    	// the referenced URI when the files are in relative paths. 
		// 
    	// Thanks to: http://forums.sun.com/thread.jspa?threadID=413937 and
		// http://www.jdom.org/pipermail/jdom-interest/2001-August/007129.html
    	// which helped us to resolve the problem.
		
    	xmlReader.setEntityResolver(der);
    	xmlReader.setContentHandler(this);
    
    	org.xml.sax.InputSource input = new InputSource(is);
    	input.setEncoding("ISO-8859-1");
		
		xmlReader.parse(input);
	}
	
	public String parse(InputStream is, String targetName, String targetId) {
		this.targetName = targetName;
		this.targetId = targetId;
		this.is = is;
		parsing = false;
		namespaces = new HashMap<String, String>();
		try {
			parse();
			return text.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public XmlNode parse(InputStream is){
		this.is = is;
		targetName = null;
		targetId = null;
		parsing = true;
		namespaces = new HashMap<String, String>();
		try {
			parse();
			return docElem;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
