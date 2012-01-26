package org.androiddaisyreader.model;

import java.util.Date;
import java.util.List;

public class Daisy202Book extends Book {
	private Date date;

	public static class Builder {
		private Daisy202Book book = new Daisy202Book();

		public Builder addSection(Section section) {
			book.sections.add(section);
			return this;
		}
		
		public Builder setDate(Date date) {
			book.date = date;
			return this;
		}

		public Builder setTitle(String content) {
			// TODO 20120124 (jharty): consider cleaning up the content.
			book.title = content.trim();
			return this;
		}

		public Daisy202Book build() {
			return book;
		}
	}

	private NccSpecification ncc;
	private Daisy202Book() {
		super();
	}
	
	public Navigable getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Navigable> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate() {
		return date;
	}
	
}
