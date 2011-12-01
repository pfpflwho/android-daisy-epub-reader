package uk.org.rnib.innovation.daisyplayer;

import uk.org.rnib.innovation.xml.XmlNode;

public class NavPoint {
	private static final int DAISY_VERSION_3 = 
		uk.org.rnib.innovation.daisyplayer.DaisyPlayer.DAISY_VERSION_3;
	private XmlNode node;
	private String text;
	private Audio audio;
	private int depth;
	private NavPoint firstChild;
	private NavPoint lastChild;
	private int childCount;
	private String content;
	private int daisyVersion;

	public String getText() {
		return text;
	}

	public Audio getAudio() {
		return audio;
	}

	public String getContent() {
		return content;
	}
	
	public String getId() {
		return node.getAttribute("id");
	}

	public NavPoint getLastChild() {
		return lastChild;
	}

	public NavPoint getParent() {
		XmlNode n = node.getParentNode();
		if (n.getNodeName().equals(node.getNodeName()))
			return new NavPoint(n, daisyVersion);
		return null;
	}

	public int getDepth() {
		return depth;
	}

	public int getChildCount() {
		return childCount;
	}

	public NavPoint(XmlNode node, int daisyVersion) {
		this.node = node;
		this.daisyVersion = daisyVersion;
		if (daisyVersion == DAISY_VERSION_3) {
			XmlNode navLabel = node.getFirstChildElement();
			text = navLabel.getFirstChildElement().getFirstChild()
					.getNodeValue();
			audio = new Audio(navLabel.getFirstChildElement()
					.getNextSiblingElement(), daisyVersion);
			XmlNode n = node.getFirstChildElement().getNextSiblingElement()
					.getNextSiblingElement();
			if (n != null) {
				firstChild = new NavPoint(n, daisyVersion);
				XmlNode ns = n;
				XmlNode c = n;
				do {
					c = ns;
					ns = ns.getNextSiblingElement();
					childCount++;
				} while (ns != null);
				lastChild = new NavPoint(c, daisyVersion);
			}
			XmlNode p = node;
			do {
				depth++;
				p = p.getParentNode();
			} while (p.getNodeName().equals(node.getNodeName()));
			content = node.getFirstChildElement().getNextSiblingElement()
					.getAttribute("src");
		} else {
			depth = Integer.parseInt(node.getNodeName().substring(1));
			XmlNode a = node.getFirstChildElement();
			text = a.getFirstChild().getNodeValue();
			content = a.getAttribute("href");
		}
	}

	private boolean isNavPoint(XmlNode node) {
		String name = node.getNodeName();
		return daisyVersion == DAISY_VERSION_3 ? name.equals("navPoint") : name.startsWith("h"); 
	}
	
	private boolean compare(XmlNode node1, XmlNode node2, 
			boolean includeParent, boolean includeChild) {
		int d1 = node1.getDepth();
		int d2 = node2.getDepth();
		if (!includeParent && (d1 < d2))
			return false;
		if (!includeChild && (d1 > d2))
			return false;
		if (!includeParent && !includeChild && (d1 != d2))
			return false;
		return true;
	}
	
	public NavPoint getFirstChild() {
		if (daisyVersion == DAISY_VERSION_3)
			return firstChild;
		else {
			XmlNode n = node;
			do {
				n = n.getNextSiblingElement();
			} while ((n != null) && !isNavPoint(n));
			if (n == null)
				return null;
			NavPoint np = new NavPoint(n, daisyVersion);
			// depth in this content is that of the heading not of the node
			if (np.getDepth() <= getDepth())
				return null;
			return np;
		}
	}

	public NavPoint getPrevious(boolean includeParent, boolean includeChild, XmlNode abortNode) {
		XmlNode n = node;
		do {
			n = n.getPrevious(includeParent, includeChild, abortNode);
		} while ((n != null) && !isNavPoint(n));
		return n != null ? new NavPoint(n, daisyVersion) : null;
	}

	public NavPoint getNext(boolean includeParent, boolean includeChild, XmlNode abortNode) {
		if (daisyVersion == DAISY_VERSION_3) {
			XmlNode n = node;
			do {
				n = n.getNext(includeParent, includeChild, abortNode);
			} while ((n != null) && !isNavPoint(n));
			return n != null ? new NavPoint(n, daisyVersion) : null;
		}
		else {
			XmlNode n = node;
			do {
				n = n.getNextSiblingElement();
			} while ((n != null) && (!isNavPoint(n) || compare(n, node, includeChild, includeParent)));
			return n != null ? new NavPoint(n, daisyVersion) : null;
		}
	}

	public String toString() {
		return depth + " " + childCount + " " + text;
	}
}
