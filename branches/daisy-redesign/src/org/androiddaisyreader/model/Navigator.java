package org.androiddaisyreader.model;

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
	
	public Navigator(Book book) {
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
		if ((stack.size() > 1) || stack.peek().hasPrevious()) {
			return true;
		}
		return false;
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
		// TODO 20120127 (jharty): I'm sure this logic is overly complicated. Simplify and make more elegant.
		Navigable item;
		
		if (!hasPrevious()) {
			return null;
		}
		
		if (stack.peek().hasPrevious()) {
			boolean pushedItem = false;
			
			item = stack.peek().previous();
			// Now try to get to the bottom right root
			while (item.getChildren().size() > 0) {
				ListIterator<? extends Navigable> nextLevel = item.getChildren().listIterator();
				if (!pushedItem) {
					// We need to reset the cursor to before the current item as we will not use it yet.
					// It will be used when the stack is popped again.
					stack.peek().next();
				}
				
				while (nextLevel.hasNext()) {
					item = nextLevel.next();
				}
				stack.push(nextLevel);
				pushedItem = true;
			}
			if (pushedItem) {
				// Reset the cursor to be before the item we're about to return. 
				stack.peek().previous();
			}
		} else {
			// We have finished at this level, go up one level
			stack.pop();
			// How do we get the current item on the stack
			item = stack.peek().previous();
		}

		return item;
		
	}
	

}
