package org.androiddaisyreader.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part implements Navigable {
	private List<Snippet> snippets = new ArrayList<Snippet>();
	private List<Part> parts = new ArrayList<Part>();
	private Audio audio;
	private Image image;
	public String id;
	public String timingMode;
	
	private Part() {}

	public Audio getAudio() {
		return audio;
	}
	
	public Image getImage() {
		return image;
	}
	
	public List<Snippet> getSnippets() {
		return Collections.unmodifiableList(snippets);
	}
	
	public boolean hasAudio() {
		return audio != null;
	}
	
	public boolean hasImage() {
		return image != null;
	}
	
	public boolean hasSnippets() {
		return !snippets.isEmpty();
	}
	
	public static class Builder {
		private Part newInstance;
		
		public Builder addPart(Part part) {
			newInstance.parts.add(part);
			return this;
		}
		
		public Builder addSnippet(Snippet snippet) {
			newInstance.snippets.add(snippet);
			return this;
		}
		
		public Builder setAudio(Audio audioClip) {
			newInstance.audio = audioClip;
			return this;
		}
		
		public Builder setImage(Image image) {
			newInstance.image = image;
			return this;
		}
		
		public Part build() {
			return newInstance;
		}

		public Builder setId(String id) {
			newInstance.id = id;
			return this;
			
		}

		public Builder setTimingMode(String mode) {
			newInstance.timingMode = mode;
			return this;
		}
	}

	public Navigable getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Navigable> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
