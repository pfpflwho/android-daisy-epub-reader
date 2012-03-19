package org.androiddaisyreader.mock;

/**
 * Represents the Time Interval between two times specified in a book's content.
 * 
 * Note: these are not intended to be used more generally.
 * 
 * @author jharty
 */
public enum SegmentTimeInfo {
	OVERLAPPING,  // The time intervals overlap
	CONTIGUOUS,  // The times are contiguous
	GAP;  // There is a gap between the 2 times.
	
	public static SegmentTimeInfo compareTimesForAudioSegments(double timeToStartPlayingFrom, double timeLastSegmentFinished) {
		double difference = Math.abs(timeToStartPlayingFrom - timeLastSegmentFinished);
		if (difference < 0.001f) {
			return SegmentTimeInfo.CONTIGUOUS;
		}
		else if (timeToStartPlayingFrom > timeLastSegmentFinished) {
			return SegmentTimeInfo.GAP;
		}
		return SegmentTimeInfo.OVERLAPPING;
	}
}
