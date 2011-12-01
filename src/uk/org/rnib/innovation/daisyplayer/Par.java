package uk.org.rnib.innovation.daisyplayer;

import java.io.Serializable;

import uk.org.rnib.innovation.xml.XmlNode;

public class Par implements Serializable{
	private XmlNode node;
	private String src;
	private Audio audio;
	private int daisyVersion;
	
	public Audio getAudio(){
		return audio;
	}
	
	public String getSrc(){
		return src; 
	}
	
	public XmlNode getPrevious(boolean includeParent, boolean includeChild, XmlNode abortNode) {
		return node.getPrevious(includeParent, includeChild, abortNode);
	}
	
	public XmlNode getNext(boolean includeParent, boolean includeChild, XmlNode abortNode) {
		return node.getNext(includeParent, includeChild, abortNode);
	}

	public Par(XmlNode node, int daisyVersion, boolean first){
		this.node = node;
		this.daisyVersion = daisyVersion;
		src = node.getFirstChildElement()
			.getAttribute("src");
		/*
		NodeList nl = node.getElementsByTagName("audio");
		if (nl.getLength() > 0)
			audio = new Audio(new XmlNode(nl.item(0)));
		*/
		XmlNode n = first ? node.findFirstElementByTagName("audio")
				: node.findLastElementByTagName("audio");
		if (n != null)
			audio = new Audio(n, daisyVersion);
	}
	
	public String toString(){
		return node.getAttribute("id") + "\n" + src + "\n" + audio.toString();
	}
}
