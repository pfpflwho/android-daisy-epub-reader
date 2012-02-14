/**
 * 
 */
package org.androiddaisyreader.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author jharty
 *
 */
public class DummyBookContext extends BookContext {

	private String contents;

	public DummyBookContext(String contents) {
		this.contents = contents;
	}

	@Override
	public InputStream getResource(String uri) throws FileNotFoundException {
		return new ByteArrayInputStream(contents.getBytes());
	}

	@Override
	public String getCharSet(String uri) {
		// TODO Auto-generated method stub
		return super.getCharSet(uri);
	}

	@Override
	public String getBaseUri() {
		return File.separator;
	}

	
}
