package uk.org.rnib.innovation.daisyplayer;

import java.io.Serializable;

import org.w3c.dom.Document;

import uk.org.rnib.innovation.fileopener.FileOpener;
import uk.org.rnib.innovation.xml.XmlNode;

public class DummyDaisyPlayer implements IDaisyPlayer, Serializable {
	private String message;
	private Document dom;
	private XmlNode node;
	private NavPoint navPoint;
	private Par par;
	private Audio audio;
	private FileOpener fileOpener;
	
	public void setFileOpener(FileOpener fileOpener){
		this.fileOpener = fileOpener;
	}

	public DummyDaisyPlayer(){
		message = "I'm a dummy";
	}
	
	public void SyncToNavPoint() {
		// TODO Auto-generated method stub

	}

	public AudioClip getAudio() {
		// TODO Auto-generated method stub
		return null;
	}

	public Heading getHeading() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMessage() {
		return message + " " + fileOpener;
	}

	public String getTextUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public void moveToBeginning() {
		// TODO Auto-generated method stub

	}

	public boolean moveToFirstChildNavPoint() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean moveToNext() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean moveToNextNavPoint(boolean includeParentNavPoint,
			boolean includeChildNavPoints) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean moveToParentNavPoint() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean moveToPrevious() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean moveToPreviousNavPoint(boolean includeParentNavPoint,
			boolean includeChildNavPoints) {
		// TODO Auto-generated method stub
		return false;
	}

	public void open(String root, String name) throws Exception {
		// TODO Auto-generated method stub

	}

	public void parse(String path) throws Exception {
		// TODO Auto-generated method stub

	}

}
