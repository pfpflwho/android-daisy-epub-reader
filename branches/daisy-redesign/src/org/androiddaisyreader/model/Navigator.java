package org.androiddaisyreader.model;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

/** 
 * Navigates through a book.
 * @author jharty
 *
 */
public class Navigator {
	private Book book;
	private Stack<ListIterator<? extends Navigable>> stack = new Stack<ListIterator<? extends Navigable>>();
	
	Navigator(Book book) {
		this.book = book;
		gotoStartOfContent();
	}
	
	public void gotoStartOfContent() {
		stack.clear();
		stack.push(book.getChildren().listIterator());
	}
	
	
	// think about goto(navigation_point);
	
	public boolean hasNext() {
		while ((stack.size() > 1) && !stack.peek().hasNext()) {
			stack.pop();
		}
		return stack.peek().hasNext();
	}
	
	public boolean hasPrevious() {
		return stack.peek().hasPrevious();
	}
	
	public Navigable next() {
		Navigable item;
		
		if (!hasNext()) {
			return null;
		}
		
		item = stack.peek().next();
		
		if (item.getChildren().size() > 0) {
			stack.push(item.getChildren().listIterator());
		}
		return item;
	}
	
	public Navigable previous() {
		return stack.peek().previous();
	}
	

}
