package uk.org.rnib.innovation.xml;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;

public class XmlNode {
	private Node node;
	private String name;
	private String value;
	private int depth;
	private Map<String, String> attributes;
	private XmlNode parent;
	private XmlNode previousSibling;
	private XmlNode nextSibling;
	private XmlNode firstChild;
	private XmlNode lastChild;

	public XmlNode(String name, String value, int depth, Attributes attributes) {
		this.name = name;
		this.value = value;
		this.depth = depth;
		if (attributes != null) {
			this.attributes = new HashMap<String, String>();
			for (int i = 0; i < attributes.getLength(); i++)
				this.attributes.put(attributes.getLocalName(i), attributes
						.getValue(i));
		}
	}

	public XmlNode addChild(XmlNode child) {
		if (lastChild == null) {
			firstChild = child;
			lastChild = child;
		} else {
			lastChild.nextSibling = child;
			child.previousSibling = lastChild;
			lastChild = child;
		}
		child.parent = this;
		return child;
	}

	public String getNodeValue() {
		return node != null ? node.getNodeValue() : value;
	}

	public void setNodeValue(String value) {
		this.value = value;
	}

	public int getDepth() {
		return depth;
	}

	public XmlNode(Node node) {
		this.node = node;
	}

	public String toString() {
		return toString(true);
	}

	public String toString(boolean extended) {
		StringBuilder builder = new StringBuilder();
		builder.append("Name=" + name + "\n");
		// builder.append("Type=" + node.getNodeType() + "\n");
		// builder.append("value=" + node.getNodeValue() + "\n");
		if (extended) {
			builder.append("Next: ");
			XmlNode next = getNextSiblingElement();
			if (next != null)
				builder.append(next.getNodeName());
			builder.append("\n");
			builder.append("Child: ");
			XmlNode first = getFirstChildElement();
			if (first != null)
				builder.append(first.toString(false));
		}
		return builder.toString();
	}

	private static boolean isElement(Node node) {
		int t = node.getNodeType();
		int i = org.w3c.dom.Entity.ELEMENT_NODE;
		return t == i;
	}

	public boolean isElement() {
		if (node != null) {
			return isElement(node);
		} else {
			return !name.equals("#text");
		}
	}

	private Node skipTextNodes(Node node, boolean next) {
		while ((node != null) && !isElement(node))
			node = next ? node.getNextSibling() : node.getPreviousSibling();
		return node;
	}

	private XmlNode skipTextNodes(XmlNode node, boolean next) {
		while ((node != null) && !node.isElement())
			node = next ? node.nextSibling : node.previousSibling;
		return node;
	}

	public XmlNode findElementByAttribute(String name, String value,
			boolean includeChild) {
		// return new XmlNode(findElementByAttribute(node, name, value,
		// checkChildren));
		XmlNode n = this;
		do {
			String a = n.getAttribute(name);
			if (a.equals(value))
				return n;
		} while ((n = n.getNext(true, includeChild, this)) != null);
		return null;
	}

	/*
	 * private Node findElementByAttribute(Node node, String name, String value,
	 * boolean checkChildren) { if (node == null) return null; Element element =
	 * (Element)node; String id = element.hasAttribute(name) ?
	 * element.getAttribute(name) : null; if ((id != null) &&
	 * (id.equals(value))) return element; Node fc = element.getFirstChild(); if
	 * (checkChildren && (fc != null)){ fc = skipTextNodes(fc.getNextSibling(),
	 * true); if (fc != null) return findElementByAttribute((Element)fc, name,
	 * value, true); } Node ns = element.getNextSibling(); if (ns != null){ ns =
	 * skipTextNodes(ns.getNextSibling(), true); if (ns != null) return
	 * findElementByAttribute((Element)ns, name, value, true); } return
	 * findElementByAttribute((Element)element.getParentNode(), name, value,
	 * false); }
	 */
	public XmlNode getFirstChildElement() {
		if (node != null) {
			Node n = skipTextNodes(node.getFirstChild(), true);
			return n != null ? new XmlNode(n) : null;
		} else {
			return skipTextNodes(firstChild, true);
		}
	}

	public XmlNode getLastChildElement() {
		if (node != null) {
			Node n = skipTextNodes(node.getLastChild(), false);
			return n != null ? new XmlNode(n) : null;
		} else {
			return skipTextNodes(lastChild, false);
		}
	}

	public XmlNode getPreviousSiblingElement() {
		if (node != null) {
			Node n = skipTextNodes(node.getPreviousSibling(), false);
			return n != null ? new XmlNode(n) : null;
		} else {
			return skipTextNodes(previousSibling, false);
		}
	}

	public XmlNode getNextSiblingElement() {
		if (node != null) {
			Node n = skipTextNodes(node.getNextSibling(), true);
			return n != null ? new XmlNode(n) : null;
		} else {
			return skipTextNodes(nextSibling, true);
		}
	}

	public XmlNode getPrevious(boolean includeParent, boolean includeChild,
			XmlNode abortNode) {
		XmlNode ps = getPreviousSiblingElement();
		if (ps != null) {
			if (includeChild) {
				XmlNode lc = ps.getLastChildElement();
				if (lc != null)
					return lc;
			}
			return ps;
		}
		if (!includeParent)
			return null;
		XmlNode n = this;
		n = n.getParentNode();
		if (!n.equals(abortNode))
			return n.getPrevious(includeParent, false, abortNode);
		return null;
	}

	public XmlNode getNext(boolean includeParent, boolean includeChild,
			XmlNode abortNode) {
		if (includeChild) {
			XmlNode fc = getFirstChildElement();
			if (fc != null)
				return fc;
		}
		XmlNode ns = getNextSiblingElement();
		if (ns != null)
			return ns;
		if (!includeParent)
			return null;
		XmlNode n = this;
		n = n.getParentNode();
		if (!n.equals(abortNode))
			return n.getNext(includeParent, false, abortNode);
		return null;
	}

	public String getAttribute(String name) {
		if (node != null)
			return ((Element) node).getAttribute(name);
		else {
			if (!attributes.containsKey(name))
				return "";
			return attributes.get(name);
		}
	}

	public XmlNode findFirstElementByTagName(String name) {
		if (node != null) {
			NodeList nl = ((Element) node).getElementsByTagName(name);
			if (nl.getLength() > 0)
				return new XmlNode(nl.item(0));
		} else {
			XmlNode n = this.firstChild;
			do {
				if (n.name.equals(name))
					return n;
			} while ((n = n.getNext(true, true, this)) != null);
		}
		return null;
	}

	public XmlNode findLastElementByTagName(String name) {
		if (node != null) {
			NodeList nl = ((Element) node).getElementsByTagName(name);
			if (nl.getLength() > 0)
				return new XmlNode(nl.item(nl.getLength() - 1));
		} else {
			XmlNode n = this.lastChild;
			do {
				if (n.name.equals(name))
					return n;
			} while ((n = n.getPrevious(true, true, this)) != null);
		}
		return null;
	}

	public XmlNode getFirstChild() {
		return node != null ? new XmlNode(node.getFirstChild()) : firstChild;
	}

	public XmlNode getLastChild() {
		return node != null ? new XmlNode(node.getLastChild()) : lastChild;
	}

	public String getLocalName() {
		return node != null ? node.getLocalName() : name;
	}

	public XmlNode getNextSibling() {
		return node != null ? new XmlNode(node.getNextSibling()) : nextSibling;
	}

	public String getNodeName() {
		return node != null ? node.getNodeName() : name;
	}

	public XmlNode getParentNode() {
		return node != null ? new XmlNode(node.getParentNode()) : parent;
	}

	public XmlNode getPreviousSibling() {
		return node != null ? new XmlNode(node.getPreviousSibling())
				: previousSibling;
	}

	public String getInnerText() {
		StringBuilder text = new StringBuilder();
		if (node != null) {
			Node n = node;
			while ((n = getNext(n)) != null)
				if (!XmlNode.isElement(n))
					text.append(n.getNodeValue());
		} else {
			XmlNode n = this;
			// text.append("not implemented yet");
			while (((n = n.getNextIncludingText(false, true, this)) != null) && !n.isElement())
				text.append(n.getNodeValue());
		}
		return text.toString();
	}

	// might want to refactor this with getNext
	public XmlNode getNextIncludingText(boolean includeParent, boolean includeChild,
			XmlNode abortNode) {
		if (includeChild) {
			XmlNode fc = getFirstChild();
			if (fc != null)
				return fc;
		}
		XmlNode ns = getNextSibling();
		if (ns != null)
			return ns;
		if (!includeParent)
			return null;
		XmlNode n = this;
		n = n.getParentNode();
		if (!n.equals(abortNode))
			return n.getNextIncludingText(includeParent, false, abortNode);
		return null;
	}

	private Node getNext(Node node) {
		Node fc = node.getFirstChild();
		if (fc != null)
			return fc;
		Node ns = node.getNextSibling();
		if (ns != null)
			return ns;
		return null;
	}
}
