package org.androiddaisyreader.model;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import junit.framework.TestCase;

public class NccSpecificationTest extends TestCase {
	
	// This is a spike, and intended to be replaced once we integrate this code with the main project.
	public void testReadFromFile() throws IOException {
		File inputFile = new File("C:\\Users\\jharty\\Downloads\\daisy.org\\daisy 2.02\\fire-safety\\FireSafety\\ncc.html");
		Daisy202Book thingy = NccSpecification.readFromFile(inputFile);
		assertEquals("4980FSafe", thingy.getTitle());
		
		// TODO 201201 25 (jharty): the following test is ugly and uses a deprecated constructor.
		assertEquals(new Date(2003 - 1900, 12 - 1, 24), thingy.getDate());
	}
}
