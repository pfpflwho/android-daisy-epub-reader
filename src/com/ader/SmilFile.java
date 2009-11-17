package com.ader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SmilFile extends ArrayList<SmilEntry> {
	private String fileName;

	
	public String getFilename() {
		return this.fileName;
	}

	public String Open(File smilFileData) {
		clear();
		this.fileName = smilFileData.getName();

		SAXReader reader = new SAXReader();

		try {
			String temp = "";

			Document document = reader.read(smilFileData);

			List<Element> list = (List<Element>)document.getRootElement().elements("body");
			list = list.get(0).elements();
			list = list.get(0).elements();
			list = list.get(0).elements();

			// iterate through the par elements
			for (ListIterator<Element> i = list.listIterator(); i.hasNext();) {
				Element element = i.next();

				if (element.getName() == "seq")
					 ProcessAudioElements(element);

			}

			temp = this.get(0).getId();
			return temp;

		} catch (DocumentException e) {
			return e.getNestedException().getMessage();
		}
	}

	void ProcessAudioElements(Element seqElement) {
		List<Element> list = seqElement.elements();
		for (ListIterator<Element> i = list.listIterator(); i.hasNext();) {
			Element element = i.next();
			if (element.getName() == "audio")
				add(new SmilEntry(element));
		}
	}
}
