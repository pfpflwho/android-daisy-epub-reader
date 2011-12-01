package uk.org.rnib.innovation.daisyplayer;

import java.io.InputStream;

import uk.org.rnib.innovation.fileopener.FileOpener;
import uk.org.rnib.innovation.xml.XmlNode;
import uk.org.rnib.innovation.xml.XmlSaxParser;

public class DaisyPlayer {
	public static final int DAISY_VERSION_2 = 2;
	public static final int DAISY_VERSION_3 = 3;
	public static final String DAISY_ROOT_KEY = "root";
	public static final String DAISY_NAME_KEY = "name";
	private FileOpener fileOpener;
	private String message;
	private String root;
	// private Document doc;
	// private XmlNode docElem;
	private XmlNode navMap;
	private XmlNode body;
	private NavPoint navPoint;
	private NavPoint firstNavPoint;
	private String smilName;
	private String smilId;
	private String smilIdNodeName;
	private Par par;
	private Audio audio;
	private int maxDepth;
	private int daisyVersion;

	public String getMessage() {
		return message;
	}

	// Wrap NavPoint to hide xml methods.
	public Heading getHeading() {
		return new Heading(navPoint);
	}

	public String getTextUrl() {
		return par.getSrc();
	}

	public AudioClip getAudio() {
		return new AudioClip(audio);
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public DaisyPlayer(FileOpener fileOpener) {
		this.fileOpener = fileOpener;
	}

	public void open(String root, String name) throws Exception {
		try {
			this.root = root;
			/*
			 * when using dom parser
			 * 
			parse(root + name);
			String docType = doc.getDoctype().getName();
			if (docType.equals("package")) {
				XmlNode document = new XmlNode(doc.getDocumentElement());
			*/
			XmlNode docElem = parse(root + name);
			if (docElem.getNodeName().equals("package")) {
				daisyVersion = DAISY_VERSION_3;
				XmlNode ncx = docElem.findElementByAttribute("id", "ncx", true);
				String ncxPath = ncx.getAttribute("href");
				/* 
				 * when using dom parser
				 * 
				parse(root + ncxPath);
				NodeList navMaps = doc.getDocumentElement()
						.getElementsByTagName("navMap");
				NodeList navMaps = parse(root + ncxPath)
					.getElementsByTagName("navMap");
				if (navMaps.getLength() > 0)
					navPoint = new NavPoint(new XmlNode(navMaps.item(0))
							.getFirstChildElement());
				*/
				ncx = parse(root + ncxPath);
				XmlNode meta = ncx.findFirstElementByTagName("meta");
				XmlNode dtbDepth = meta.findElementByAttribute("name", "dtb:depth", false);
				if (dtbDepth != null)
					maxDepth = Integer.parseInt(dtbDepth.getAttribute("content"));
				navMap = ncx.findFirstElementByTagName("navMap");
				if (navMap != null)
					navPoint = new NavPoint(navMap.getFirstChildElement(), daisyVersion);
			}
			/*
			 * when using dom parser
			 * 
			if (docType.equals("html")) {
				XmlNode document = new XmlNode(doc.getDocumentElement());
			*/
			if (docElem.getNodeName().equals("html")) {
				daisyVersion = DAISY_VERSION_2;
				XmlNode nccDepth = docElem
					.findFirstElementByTagName("head")
					.findElementByAttribute("name", "ncc:depth", true);
				if (nccDepth != null)
					maxDepth = Integer.parseInt(nccDepth.getAttribute("content"));
				navMap = docElem.findFirstElementByTagName("body");
				XmlNode firstH = navMap.getFirstChildElement();
				navPoint = new NavPoint(firstH, daisyVersion);
			}
			firstNavPoint = navPoint;
			if (maxDepth == 0) {
				NavPoint n = firstNavPoint;
				do {
					if (n.getDepth() > maxDepth)
						maxDepth = n.getDepth();
				} while ((n = n.getNext(true, true, navMap)) != null);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public XmlNode parse(String path) throws Exception {
		InputStream is = null;
		try {
			is = fileOpener.open(path);
			/*
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(is).getDoc;
			*/
			
			/* too slow
			XmlDomParser xp = new XmlDomParser();
			return new XmlNode(xp.parse(is));
			*/
			
			return new XmlSaxParser().parse(is);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null)
				is.close();
		}
	}

	// may only need this for unit testing
	public void moveToBeginning() {
		navPoint = firstNavPoint;
		SyncToNavPoint();
	}

	public boolean moveToPreviousHeading(boolean includeParent,
			boolean includeChild) {
		NavPoint n = navPoint.getPrevious(includeParent, includeChild, navMap);
		if (n == null)
			return false;
		navPoint = n;
		return true;
	}

	public boolean moveToPreviousHeading(int depth) {
		NavPoint n = navPoint;
		do {
			n = n.getPrevious(true, navPoint.getDepth() < depth, navMap);
		} while ((n != null) && (n.getDepth() > depth));
		if (n == null)
			return false;
		navPoint = n;
		return true;
	}

	public boolean moveToNextHeading(boolean includeParent,
			boolean includeChild) {
		NavPoint n = navPoint.getNext(includeParent, includeChild, navMap);
		if (n == null)
			return false;
		navPoint = n;
		return true;
	}

	public boolean moveToNextHeading(int depth) {
		NavPoint n = navPoint;
		do {
			n = n.getNext(true, navPoint.getDepth() < depth, navMap);
		} while ((n != null) && (n.getDepth() > depth));
		if (n == null)
			return false;
		navPoint = n;
		return true;
	}

	public boolean moveToSuperHeading() {
		NavPoint n = navPoint.getParent();
		if (n == null)
			return false;
		navPoint = n;
		return true;
	}

	public boolean moveToFirstSubHeading() {
		NavPoint n = navPoint.getFirstChild();
		if (n == null)
			return false;
		navPoint = n;
		return true;
	}
	
	public boolean moveToHeading(String id) {
		XmlNode n = navMap.findElementByAttribute("id", id, true);
		if (n == null)
			return false;
		navPoint = new NavPoint(n, daisyVersion);
		return true;
	}

	public boolean moveToPrevious() {
		Audio a = audio.getPrevious();
		if (a != null) {
			audio = a;
			return true;
		}
		XmlNode n = par.getPrevious(true, false, body);
		if (n != null) {
			if (n.getNodeName().equals(smilIdNodeName))
				smilId = n.getAttribute("id");
			if (n.getNodeName().equals("seq"))
				n = n.getFirstChildElement();
			if (n != null) {
				sync(n, false);
				return true;
			}
		}
		if (!moveToPreviousHeading(true, true))
			return false;
		SyncToNavPoint();
		par = new Par(body.findLastElementByTagName("par"), daisyVersion, false);
		return true;
	}

	public boolean moveToNext() {
		Audio a = audio.getNext();
		if (a != null) {
			audio = a;
			return true;
		}
		XmlNode n = par.getNext(true, false, body);
		if (n != null) {
			if (n.getNodeName().equals(smilIdNodeName))
				smilId = n.getAttribute("id");
			while (n.getNodeName().equals("seq"))
				n = n.getFirstChildElement();
			if (n != null) {
				sync(n, true);
				return true;
			}
		}
		if (!moveToNextHeading(true, true))
			return false;
		SyncToNavPoint();
		return true;
	}

	private void sync(XmlNode node, boolean first) {
		par = new Par(node, daisyVersion, first);
		audio = par.getAudio();
		String ref = smilName + "#" + smilId;
		NavPoint n = navPoint.getNext(true, true, navMap);
		if ((n != null) && (ref.equals(n.getContent())))
			navPoint = n;
	}

	public void SyncToNavPoint() {
		try {
			String content = navPoint.getContent();
			int i = content.indexOf("#");
			// message = root + content.substring(0, i) + " " +
			// content.substring(i + 1);
			smilName = content.substring(0, i);
			smilId = content.substring(i + 1);
			String name = smilIdNodeName + "#" + smilId;
			if (!name.equals(smilName)){
				XmlNode docElem = parse(root + smilName);
				smilIdNodeName = smilName;
			// }
				/*
				XmlNode n = new XmlNode(doc.getDocumentElement())
						.findElementByAttribute("id", smilId, true);
				*/
				body = docElem.findFirstElementByTagName("body");
				XmlNode n = docElem.findElementByAttribute("id", smilId, true);
				smilIdNodeName = n.getNodeName();
				if (!n.getNodeName().equals("par"))
					n = n.getParentNode();
				sync(n, true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
