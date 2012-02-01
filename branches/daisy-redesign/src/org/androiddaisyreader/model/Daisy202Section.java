package org.androiddaisyreader.model;

import java.util.ArrayList;

public class Daisy202Section extends Section {
	
	private Part part;

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
	public boolean smilFilenameIsValid(String smilFilename) {
		if (smilFilename.endsWith(".smil")) {
			return true;
		}
		return false;
	}
	
	public static class Builder {
		private Section newInstance = new Daisy202Section();
		
		Builder() {
			newInstance.navigables = new ArrayList<Navigable>();
		}
		
		public Builder addPart(Part part) {
			newInstance.navigables.add(part);
			return this;
		}
		
		public Builder addSection(Section section) {
			newInstance.navigables.add(section);
			return this;
		}
		
		public Builder setLevel(int level) {
			// Consider adding logic to protect the integrity of this Section
			newInstance.level = level;
			return this;
		}
		
		public Builder setParent(Navigable parent) {
			newInstance.parent = parent;
			return this;
		}
		
		public Builder setTitle(String title) {
			newInstance.title = title;
			return this;
		}
		
		public Section build() {
			return newInstance;
		}
		
		public int getLevel() {
			return newInstance.level;
		}

		public Builder setId(String id) {
			newInstance.id = id;
			return this;
			
		}

		public Builder setHref(String href) {
			newInstance.href = href;
			return this;
		}
	}

	public void setPart(Part part) {
		this.part = part;
	}
}
