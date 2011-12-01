package uk.org.rnib.innovation.audioplayer;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.View;

public class WrappedMediaPlayer implements AudioPlayer {
	private final static int SEC_TO_MSEC = 1000;
	MediaPlayer player;
	View view;
	OnCompletionListener listener;
	int startAtMsec;
	int stopAtMsec;
	String path;

	// Pass handler in otherwise it doesn't work.
	public WrappedMediaPlayer(View view, OnCompletionListener listener) {
		this.view = view;
		this.listener = listener;
		player = new MediaPlayer();
		player.setOnCompletionListener(listener);
	}

	public void play(String path, double startAt, double stopAt) {
		try {
			view.removeCallbacks(checkPlayer);
			player.pause();
			if (!path.equals(this.path)) {
				player.reset();
				player.setDataSource(path);
				player.prepare();
				this.path = path;
			}
			startAtMsec = ((int) startAt * SEC_TO_MSEC);
			player.seekTo(startAtMsec);
			stopAtMsec = ((int) stopAt * SEC_TO_MSEC);
			long delay = (long) ((stopAt - startAt) * SEC_TO_MSEC * 0.95);
			view.postDelayed(checkPlayer, delay);
			player.start();
		} catch (IOException e) {
		}
	}

	public double getCurrentPosition() {
		return (double) player.getCurrentPosition() / SEC_TO_MSEC;
	}

	public double getDuration() {
		return (stopAtMsec - startAtMsec) / SEC_TO_MSEC;
	}

	public boolean isPlaying() {
		return player.isPlaying();
	}

	// very ugly hack as MediaPlayer has no stop position or position change
	// monitoring
	private Runnable checkPlayer = new Runnable() {
		public void run() {
			view.removeCallbacks(checkPlayer);
			long p = player.getCurrentPosition();
			if (p >= stopAtMsec) {
				player.pause();
				listener.onCompletion(null);
			} else {
				view.postDelayed(checkPlayer, 5);
			}
		}
	};

	public void start() {
		player.start();
		if (stopAtMsec > 0)
			view.postDelayed(checkPlayer, 50);
	}

	public void pause() {
		player.pause();
	}

	public void prepare() throws IOException {
		player.prepare();
	}

	public void stop() {
		player.stop();
	}

	public void release() {
		player.release();
	}

	/*
	 * Nick Williamson 2011-10-28 One approach to being able to stop audio
	 * playback at a specified position was to use AudioTrack and decode the mp3
	 * using jlayer - based on the approach at
	 * http://mindtherobot.com/blog/624/android
	 * -audio-play-an-mp3-file-on-an-audiotrack/ - this proved to be slow.
	 * 
	 * private static Sound decode(String path, int startMs, int maxMs) throws
	 * IOException { int sampleFrequency = 0; int channels = 0;
	 * ByteArrayOutputStream outStream = new ByteArrayOutputStream(1024); float
	 * totalMs = 0; boolean seeking = true; File file = new File(path);
	 * InputStream inputStream = new BufferedInputStream(new
	 * FileInputStream(file), 8 * 1024); try{ Bitstream bitstream = new
	 * Bitstream(inputStream); Decoder decoder = new Decoder(); boolean done =
	 * false; while (! done) { javazoom.jl.decoder.Header frameHeader =
	 * bitstream.readFrame(); if (frameHeader == null) { done = true; } else {
	 * totalMs += frameHeader.ms_per_frame(); if (totalMs >= startMs) { seeking
	 * = false; }
	 * 
	 * if (! seeking) { SampleBuffer output = (SampleBuffer)
	 * decoder.decodeFrame(frameHeader, bitstream); sampleFrequency =
	 * output.getSampleFrequency(); channels = output.getChannelCount(); short[]
	 * pcm = output.getBuffer(); for (short s : pcm) { outStream.write(s &
	 * 0xff); outStream.write((s >> 8 ) & 0xff); } }
	 * 
	 * if (totalMs >= (startMs + maxMs)) { done = true; } }
	 * bitstream.closeFrame(); } Sound sound = new Sound(); sound.setMessage(""
	 * + outStream.size()); sound.setSampleRate(sampleFrequency);
	 * sound.setChannels(channels); sound.setData(outStream.toByteArray());
	 * return sound; } catch (BitstreamException e) { throw new
	 * IOException("Bitstream error: " + e); } catch (DecoderException e) {
	 * throw new IOException("Decoder 	error: " + e); } finally {
	 * outStream.close(); } }
	 */

	public void setOnCompletionListener(Object o) {
		player.setOnCompletionListener(null);
	}

	public void setScreenOnWhilePlaying(boolean screenOn) {
		player.setScreenOnWhilePlaying(screenOn);
	}

	public void seekTo(int msec) {
		player.seekTo(msec);
	}
}
