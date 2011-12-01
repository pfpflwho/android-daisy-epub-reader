package uk.org.rnib.innovation.audioplayer;


public interface AudioPlayer{
	public boolean isPlaying();
	public void start();
	public void pause();
	public void stop();
	public void release();
	public void setScreenOnWhilePlaying(boolean on);
	public void play(String root, double startAt, double stopAt);
	// These are used by the current 20111101 daisyplayer ui which does not know about smil audio clips.
	public double getCurrentPosition();
	public double getDuration();
	public void seekTo(int msec);
}
