package org.androiddaisyreader.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part implements Navigable {
	private List<Snippet> snippets = new ArrayList<Snippet>();
	private Audio audio;
	private Image image;
	
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
