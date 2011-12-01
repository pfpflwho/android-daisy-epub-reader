package uk.org.rnib.innovation.daisyplayer;

import java.io.Serializable;

import uk.org.rnib.innovation.xml.XmlNode;

public class Audio implements Serializable {
	private static final int DAISY_VERSION_3 = 
		uk.org.rnib.innovation.daisyplayer.DaisyPlayer.DAISY_VERSION_3;
	private XmlNode node;
	private String src;
	private double clipBegin;
	private double clipEnd;
	private int daisyVersion;

	public String getSrc() {
		return src;
	}

	public double getClipBegin() {
		return clipBegin;
	}

	public double getClipEnd() {
		return clipEnd;
	}

	public Audio(XmlNode node, int daisyVersion) {
		this.daisyVersion = daisyVersion;
		this.node = node;
		src = node.getAttribute("src");
		clipBegin = timeInMs(daisyVersion == DAISY_VERSION_3 ? node
				.getAttribute("clipBegin")
				: node.getAttribute("clip-begin"));
		clipEnd = timeInMs(daisyVersion == DAISY_VERSION_3 ? node.getAttribute("clipEnd")
				: node.getAttribute("clip-end"));
	}

	public Audio getPrevious() {
		XmlNode n = node.getPreviousSiblingElement();
		if ((n != null) && (n.getNodeName().equals("audio")))
			return new Audio(n, daisyVersion);
		return null;
	}

	public Audio getNext() {
		XmlNode n = node.getNextSiblingElement();
		if (n != null)
			return new Audio(n, daisyVersion);
		return null;
	}

	private static double timeInMs(String text) {
		int i0 = text.indexOf("=");
		if (i0 == -1) {
			int i1 = text.indexOf(":", i0 + 1);
			int m = Integer.parseInt(text.substring(0, i1));
			int i2 = text.indexOf(":", i1 + 1);
			int h = Integer.parseInt(text.substring(i1 + 1, i2 - i1));
			double s = Double.parseDouble(text.substring(i2 + 1));
			return ((h * 60 + m) * 60) + s;
		} else {
			if (text.endsWith("ms")) {
				double s = Double.parseDouble(text.substring(i0 + 1, text
						.length() - 2));
				return s / 1000;
			}
			if (text.endsWith("s")) {
				double s = Double.parseDouble(text.substring(i0 + 1, text
						.length() - 1));
				return s;
			}
		}
		return -1; // error
	}

	public String toString() {
		return src + " " + clipBegin + " " + clipEnd;
	}
}
