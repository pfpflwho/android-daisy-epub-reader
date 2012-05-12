package org.androiddaisyreader.model;

import org.xml.sax.Attributes;

/**
 * Utility class to extract the timing values from the SMIL files.
 * 
 * @author Julian Harty
 */
public class ExtractTimingValues {

	/**
	 * Extract the effective value of the time offset for a given element.  
	 * @param elementName the name of the element to extract the value for.
	 * @param attributes the set of attributes which include the expected name.
	 * @return the double representing the effective value of the time offset.
	 */
	static double extractTiming(String elementName, Attributes attributes) {
		String rawValue = ParserUtilities.getValueForName(elementName, attributes);
		String trimmedValue = rawValue.replace("npt=", "").replace("s", "");
		return Double.parseDouble(trimmedValue);
	}
}
