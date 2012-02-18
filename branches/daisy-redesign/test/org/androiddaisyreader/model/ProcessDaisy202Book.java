package org.androiddaisyreader.model;

import static org.androiddaisyreader.model.XmlUtilities.obtainEncodingStringFromInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProcessDaisy202Book {

	private static BookContext bookContext;
	private static InputStream contents;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			CommandLineUtilities.printUsage("ProcessDaisy202Book");
			System.exit(1);
		}
		
		StringBuilder filename = new StringBuilder();
		
		// To help cope with spaces in the filename e.g. on my windows machine.
		for (int i = 0; i < args.length; i++) {
			filename.append(args[i]);
		}
		if (filename.toString().endsWith(".zip")) {
			bookContext = new ZippedBookContext(filename.toString());
			contents = bookContext.getResource("ncc.html");
		} else {
			contents = new FileInputStream(filename.toString());
			String encoding = obtainEncodingStringFromInputStream(contents);
	
			File directory = new File(filename.toString());
			bookContext = new FileSystemContext(directory.getParent());
			directory = null;
		}
		
		Daisy202Book book = NccSpecification.readFromStream(contents);
		System.out.println("Book: " + book.getTitle());
		Navigator navigator = new Navigator(book);
		while (navigator.hasNext()) {
			Section section = (Section) navigator.next();
			System.out.println(section.level + " " + section.title);
		}
		
		System.exit(0);
	}

}
