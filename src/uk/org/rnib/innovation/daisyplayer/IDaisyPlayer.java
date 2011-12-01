package uk.org.rnib.innovation.daisyplayer;

public interface IDaisyPlayer{

	public abstract String getMessage();

	// Wrap NavPoint to hide xml methods.
	public abstract Heading getHeading();

	/*
	public String getSmilId(){
		return smilId;
	}
	
	public String getSrc(){
		return par.getSrc();
	}
	 */

	public abstract String getTextUrl();

	public abstract AudioClip getAudio();

	public abstract void open(String root, String name) throws Exception;

	public abstract void parse(String path) throws Exception;

	// may only need this for unit testing
	public abstract void moveToBeginning();

	public abstract boolean moveToPreviousNavPoint(
			boolean includeParentNavPoint, boolean includeChildNavPoints);

	public abstract boolean moveToNextNavPoint(boolean includeParentNavPoint,
			boolean includeChildNavPoints);

	public abstract boolean moveToParentNavPoint();

	public abstract boolean moveToFirstChildNavPoint();

	public abstract boolean moveToPrevious();

	public abstract boolean moveToNext();

	public abstract void SyncToNavPoint();

}