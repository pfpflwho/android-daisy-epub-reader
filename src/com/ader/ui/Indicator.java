package com.ader.ui;

public interface Indicator {
	public void noMore();
	public void headingDepth(int level, int maxLevel);
	public void browsingTableOfContents(boolean browsingToc);
	public void pleaseWait(boolean active);
}
