package org.androiddaisyreader.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class NavigatorTest extends TestCase {

	private ByteArrayInputStream bookContents;
	private Navigator navigator;
	
	@Override
	protected void setUp() {
		bookContents = NccSpecificationTest.createNCC();
		Book book = null;
		try {
			book = NccSpecification.readFromStream(bookContents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		navigator = new Navigator(book);
	}
	
	public void testNavigationFromStartToEndOfBook() {
		int elements = 0;
		while (navigator.hasNext()) {
			Navigable n = navigator.next();
			assertTrue(n != null);
			elements++;
		}
		assertEquals("Expected 5 elements for a book with 12231 sections", 5, elements);
	}
	
	public void testNavigationBackwardsThroughBook() {
		int elements = 0;
		
		// First we need to reach the end of the book
		while (navigator.hasNext()) {
			Navigable n = navigator.next();
			System.out.println(((Section)n).id);
		}
		System.out.println("Reversing...");
		
		// We should be at the end of the book now.
		while (navigator.hasPrevious()) {
			System.out.println("p");
			Navigable n = navigator.previous();
			assertTrue(n != null);
			Section s = (Section)n;
			System.out.println(s.id);
			elements++;
		}
		assertEquals("Expected 5 elements for a book with 12231 sections", 5, elements);
	}
}
