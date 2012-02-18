/**
 * 
 */
package org.androiddaisyreader.model;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * BookContext provides a resource-independent way to access a book's contents.
 * 
 * The current design should work for zipped content and files in a directory.
 * Potentially it may also support URI based content e.g. on a web server.
 * 
 * @author Julian Harty
 */
public interface BookContext {

	/**
	 * Get a named resource using this Book's Context.
	 * 
	 * The uri is relative to the Book's Context e.g. a filename without the
	 * folder name, or a filename within a zip file.
	 *  
	 * @param uri the name of the resource.
	 * @return an InputStream with the contents of the resource. 
	 * @throws FileNotFoundException if the resource is not found or other
	 *         problems related to obtaining the contents.
	 */
	public InputStream getResource(String uri) throws FileNotFoundException;
		
	/**
	 * Obtain the character set for the specified uri.
	 * 
	 * The character set is important when parsing the contents of the uri.
	 * 
	 * TODO 20120218 (jharty): actually implement code to return the correct
	 * contents rather than a hardcoded result.
	 * 
	 * @param uri the name of the resource.
	 * @return a string representing the character set of the uri.
	 */
	public String getCharSet(String uri);
	
	/**
	 * Obtain the base URI for the Book's Context. e.g. the directory name
	 * or the name of the zip file.
	 * 
	 * @return the base URI, or null if none is available/relevant.
	 */
	public String getBaseUri();
	
}
