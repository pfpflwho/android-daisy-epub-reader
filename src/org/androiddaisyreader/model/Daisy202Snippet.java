package org.androiddaisyreader.model;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Daisy202Snippet extends Snippet {

	private FileSystemContext context;
	private String uri;
	private String id;

	// Prevent people from using the default constructor.
	private Daisy202Snippet() {
	}
	
	/**
	 * Create a DAISY 2.02 snippet.
	 * Uses the book's context & a composite reference.
	 * 
	 * The context may be a file location, an index into a zip file, etc. The
	 * context is needed as composite references contain relative references.
	 * 
	 * A composite reference is formatted as follows:
	 *   fire_safety.html#dol_1_4_rgn_cnt_0043
	 *   An example of the reference in context follows:
	 *   <text src="fire_safety.html#dol_1_4_rgn_cnt_0043" id="rgn_txt_0004_0017"/>
	 * @param context
	 * @param compositeReference
	 */
	Daisy202Snippet(FileSystemContext context, String compositeReference) {
		if (context == null) {
			throw new IllegalArgumentException("Programming error: context needs to be set");
		}
		
		this.context = context;
		String[] elements = compositeReference.split("#");
		if (elements.length != 2) {
			throw new IllegalArgumentException(
					"Expected composite reference in the form uri#id, got " + compositeReference);
		}
		this.uri = elements[0];
		this.id = elements[1];
	}

	/**
	 * @return the text contents for this text element.
	 */
	@Override
	public String getText() {
		Document doc = null;
		try {
			InputStream contents = context.getResource(uri);
			doc = Jsoup.parse(contents, context.getCharSet(uri), context.getBaseUri());
			return doc.getElementById(id).text(); 
		} catch (IOException ioe) {
			// TODO 20120214 (jharty): we need to consider more appropriate error reporting.
			throw new RuntimeException("TODO fix me", ioe);
		}
	}

	/**
	 * @return the id that points to the contents.
	 */
	public String getId() {
		// TODO 20120214 (jharty): Consider keeping the composite reference as
		// the ID since these IDs are only truly unique in the context of the
		// file...
		return id;
	}

}
