package uk.org.rnib.innovation.daisyplayer;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import uk.org.rnib.innovation.fileopener.FileOpener;
import uk.org.rnib.innovation.xml.XmlNode;
import uk.org.rnib.innovation.xml.XmlSaxParser;

public class TextRenderer {
	private FileOpener fileOpener;
	private String path;
	private XmlNode docElem;
	
	
	public TextRenderer(FileOpener fileOpener) {
		this.fileOpener = fileOpener;
	}
	
	public String Render(String url) throws IOException{
		int i = url.indexOf("#");
		String path = url.substring(0, i);
		String id = url.substring(i + 1);
		// return new XmlSaxParser().parse(fileOpener.open(path), id);
		if (!path.equals(this.path)) {
			docElem = new XmlSaxParser().parse(fileOpener.open(path));
			this.path = path;
		}
		XmlNode n = docElem.findElementByAttribute("id", id, true);
		if (n != null)
			return n.getInnerText();
		else
			return "";
	}
	
	/* This is too slow
	public String Render1(String url) throws IOException{
		int i = url.indexOf("#");
		String path = url.substring(0, i);
		String id = url.substring(i + 1);
		XmlNode node;
		Document doc = null;
		InputStream is = null;
		try {
			if (!path.equals(this.path)){
				is = fileOpener.open(path);
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				doc = builder.parse(is);
				this.path = path;
			}
			node = new XmlNode(doc.getDocumentElement()).findElementByAttribute("id", id, true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null)
				is.close();
		}
		if (node != null)
			return node.getInnerText();
		return null;
	}
	*/
}
