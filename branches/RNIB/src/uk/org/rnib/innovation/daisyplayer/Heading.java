package uk.org.rnib.innovation.daisyplayer;

// wrap NavPoint and hide the XMLness
public class Heading {
	private NavPoint navPoint;

	public AudioClip getAudio() {
		return navPoint.getAudio() != null ? new AudioClip(navPoint.getAudio()) : null;
	}

	public String getText() {
		return navPoint.getText().trim();
	}

	public String getContent() {
		return navPoint.getContent();
	}

	public int getDepth() {
		return navPoint.getDepth();
	}
	
	public String getId() {
		return navPoint.getId();
	}

	/*
	public int getChildCount() {
		return navPoint.getChildCount();
	}
	*/
	
	public boolean hasChildren() {
		NavPoint np = navPoint.getFirstChild();
		return np != null;
	}

	public Heading(NavPoint navPoint) {
		this.navPoint = navPoint;
	}
}
