package com.ader;

import org.dom4j.Element;

public class NCCEntry {
	private String smil;
	private String smilRef;
	public String Text;

	public NCCEntry (Element element) {
		// navigate to the anchor element
		element = (Element) element.elements("a").get(0);
		
		Text = element.getText();
		
		// extract the smil an smilref from the href attribute
		smil = element.attributeValue("href");
		int hashPosition = smil.indexOf("#");
		smilRef = smil.substring(hashPosition + 1);
		smil = smil.substring(0, hashPosition);
	}
	
	public String GetSmil() {
		return smil;
	}
	
	public String GetSmilRef() {
		return smilRef	;
	}
	
	public String GetText() {
		return Text;
	}
}
