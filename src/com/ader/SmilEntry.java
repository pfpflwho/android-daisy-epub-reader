package com.ader;

import org.dom4j.Element;

public class SmilEntry {
	private String src;
	private String clipBegin;
	private String clipEnd;
	private String id;

	public SmilEntry(Element audio) {
		src = audio.attributeValue("src");
		clipBegin = audio.attributeValue("clip-begin").substring(4);
		clipBegin = clipBegin.substring(0, clipBegin.indexOf("s"));
		clipEnd = audio.attributeValue("clip-end").substring(4);
		clipEnd = clipEnd.substring(0, clipEnd.indexOf("s"));
		id = audio.attributeValue("id");
	}

	public String getSrc() {
		return src;
	}

	public String getClipBegin() {
		return clipBegin;
	}

	public String getClipEnd() {
		return clipEnd;
	}

	public String getId() {
		return id;
	}

}
