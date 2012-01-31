package org.androiddaisyreader.model;

import java.util.Collections;
import java.util.List;

public class Section implements Navigable {
	protected List<Navigable> navigables;
	protected int level;
	protected Navigable parent;
	protected String title;
	protected String id;
	protected String href;

	public String getHref() {
		return href;
	}
	
	public int getLevel() {
		return level;
	}
	
	public String getSmilFilename() {
		String[] values = href.split("#");
		String smilFilename = values[0];
		return smilFilenameIsValid(smilFilename) ? smilFilename : null;
	}

	/**
	 * Simple helper method to validate the smil filename.
	 * 
	 * We can enhance this to suit our needs.
	 * @param smilFilename
	 * @return true if the filename seems to represent a smil file, else false.
	 */
	private boolean smilFilenameIsValid(String smilFilename) {
		if (smilFilename.endsWith(".smil")) {
			return true;
		}
		return false;
	}

	public String getTitle() {
		return title;
	}


	public Navigable getParent() {
		return parent;
	}

	public List<Navigable> getChildren() {
		return Collections.unmodifiableList(navigables);
	}
}
 