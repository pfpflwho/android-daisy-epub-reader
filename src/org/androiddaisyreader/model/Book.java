package org.androiddaisyreader.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Book implements Navigable {
	// meta data
	protected String title;
	protected String author;
	protected List<Section> sections = new ArrayList<Section>();
	
	String getAuthor() {
		return author;
	}
	
		String getTitle() {
		return title;
	}
	
	boolean hasAuthor() {
		return author != null;
	}
	
	boolean hasTitle() {
		return title != null;
	}
	
}
