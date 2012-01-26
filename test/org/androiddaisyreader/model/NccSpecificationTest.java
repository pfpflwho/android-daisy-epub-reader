package org.androiddaisyreader.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.androiddaisyreader.testutilities.CreateDaisy202Book;
import org.androiddaisyreader.testutilities.NotImplementedException;
import org.androiddaisyreader.testutilities.SampleContent;

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
	
	public void testUsingValidSampleContent() throws IOException {
		ByteArrayInputStream content = new ByteArrayInputStream((SampleContent.simpleValidNccHtml).getBytes());
		Daisy202Book anotherThingy = NccSpecification.readFromStream(content, "utf-8");
		assertEquals(SampleContent.firstTitle, anotherThingy.getTitle());
		assertEquals(1, anotherThingy.sections.size());
		assertEquals(1, anotherThingy.getChildren().size());
	}
	
	public void testCorrectSectionsForTwoSectionContents() throws IOException {
		ByteArrayInputStream content = new ByteArrayInputStream((SampleContent.validIcelandicNccHtml).getBytes("utf-8"));
		Daisy202Book icelandicContents = NccSpecification.readFromStream(content, "utf-8");
		assertEquals(2, icelandicContents.getChildren().size());
	}
	
	public void testNestingOfSections() throws NotImplementedException, IOException {
		ByteArrayOutputStream out  = new ByteArrayOutputStream();
		CreateDaisy202Book eBookContents = new CreateDaisy202Book(out);
		eBookContents.writeXmlHeader();
		eBookContents.writeDoctype();
		eBookContents.writeXmlns();
		eBookContents.writeBasicMetadata();
		eBookContents.addTheseLevels("12231");
		eBookContents.writeEndOfDocument();
		ByteArrayInputStream bookContents = new ByteArrayInputStream(out.toByteArray());
		Daisy202Book book = NccSpecification.readFromStream(bookContents);
		assertEquals("Count should match the number of level 1 sections.", 2, book.getChildren().size());
	}
}
