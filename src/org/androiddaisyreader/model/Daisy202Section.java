package org.androiddaisyreader.model;

import java.util.ArrayList;

public class Daisy202Section extends Section {

	
	public static class Builder {
		private Section newInstance = new Section();
		
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

		public void setId(String id) {
			newInstance.id = id;
			
		}
	}
}
