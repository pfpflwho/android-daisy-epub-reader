package uk.org.rnib.innovation.audioplayer;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.View;

public class MockAudioPlayer implements AudioPlayer {
	private final static int SEC_TO_MSEC = 1000;
	View view;
	OnCompletionListener listener;
	int startAtMsec;
	int stopAtMsec;
	double speed;

	public MockAudioPlayer(View view, OnCompletionListener listener, double speed) {
		this.view = view;
		this.listener = listener;
		this.speed = speed;
	}

	public double getCurrentPosition() {
		return stopAtMsec / SEC_TO_MSEC;
	}

	public double getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void play(String root, double startAt, double stopAt) {
		this.view = view;
		stopAtMsec = ((int) stopAt * SEC_TO_MSEC);
		long delay = (long) ((stopAt - startAt) * SEC_TO_MSEC / speed);
		view.postDelayed(checkPlayer, delay);
	}

	public void release() {
		// TODO Auto-generated method stub

	}

	public void seekTo(int msec) {
		// TODO Auto-generated method stub

	}

	public void setScreenOnWhilePlaying(boolean on) {
		// TODO Auto-generated method stub

	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	private Runnable checkPlayer = new Runnable() {
		public void run() {
			view.removeCallbacks(checkPlayer);
			listener.onCompletion(null);
		}
	};
}
