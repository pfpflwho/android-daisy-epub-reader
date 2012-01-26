package org.androiddaisyreader.model;

import java.util.List;

public interface Navigable {
	public Navigable getParent();
	public List<Navigable> getChildren();
}
