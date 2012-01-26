package org.androiddaisyreader;

public interface AudioPlayer extends PlayAudioListener, VolumeControlListener {
	public void addCallbackListener(AudioCallbackListener listener);

}
