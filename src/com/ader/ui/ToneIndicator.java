package com.ader.ui;
// Baaed on code from http://stackoverflow.com/questions/2413426/playing-an-arbitrary-tone-with-android

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.view.View;

/*
 * Nick Williamson 2011-10-27
 * To conserve resources this probably should play our or built-in files but for now
 * we will construct tones in memory. 
 */

public class ToneIndicator implements Indicator {
    View view;
	Sound noTone;
	Sound highTone;
	Sound lowTone;
	Sound tickTone;
	
	public ToneIndicator(View view){
		this.view = view;
		highTone = new Sound();
		generateTone(0.25, 880, highTone);
		noTone = new Sound();
		generateTone(0.1, 0, noTone);
		lowTone = new Sound();
		generateTone(0.25, 220, lowTone);
		tickTone = new Sound();
		generateTone(0.1, 220, tickTone);
	}
	
	public void noMore(){
		playSound(lowTone.getData());
	}
	
	public void headingDepth(int level, int maxLevel) {
		play(level);
	}
	
	public void browsingTableOfContents(boolean browsingToc){
		byte[] data = new byte[highTone.ByteCount() + noTone.ByteCount() + lowTone.ByteCount()];
		int index = 0;
		if (browsingToc){
			System.arraycopy(lowTone.getData(), 0, data, index, lowTone.ByteCount());
			index += lowTone.ByteCount();
			System.arraycopy(noTone.getData(), 0, data, index, noTone.ByteCount());
			index += noTone.ByteCount();
			System.arraycopy(highTone.getData(), 0, data, index, highTone.ByteCount());
		}
		else{
			System.arraycopy(highTone.getData(), 0, data, index, highTone.ByteCount());
			index += highTone.ByteCount();
			System.arraycopy(noTone.getData(), 0, data, index, noTone.ByteCount());
			index += noTone.ByteCount();
			System.arraycopy(lowTone.getData(), 0, data, index, lowTone.ByteCount());
		}
		playSound(data);
	}

	public void pleaseWait(boolean active){
		// view.removeCallbacks(tick);
		if (active){
			playSound(tickTone.getData());
			// tick.run();
		}
	}
	
	private Runnable tick = new Runnable() {
	   	public void run(){
	   		if (view != null)
	   		{
	   			view.removeCallbacks(tick);
	   			playSound(tickTone.getData());
	   			view.postDelayed(this, 500);
	   		}
	   	}
	};
	
	private void play(int number){
		byte[] data = new byte[number * highTone.ByteCount() + (number - 1) * noTone.ByteCount()];
		int index = 0;
		System.arraycopy(highTone.getData(), 0, data, index, highTone.ByteCount());
		index += highTone.ByteCount();
		while (number-- > 1){
			System.arraycopy(noTone.getData(), 0, data, index, noTone.ByteCount());
			index += noTone.ByteCount();
			System.arraycopy(highTone.getData(), 0, data, index, highTone.ByteCount());
			index += highTone.ByteCount();
		}
		playSound(data);
	}

    private final int sampleRate = 8000;
    private final int bytesPerSample = 2;
    // private byte generatedSnd[];

    private void generateTone(final double duration, final double frequency, final Sound sound){
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                sound.setData(genTone(duration, frequency));
            }
        });
        thread.start();
    }

    byte[] genTone(double duration, double frequency){
    	int numSamples = (int)(duration * (double)sampleRate);
        byte[] generatedSnd = new byte[bytesPerSample * numSamples];
        double sample[] = new double[numSamples];
               // fill out the array
        for (int i = 0; i < numSamples; ++i) {
        	// modulate amplitude with half sine wave
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / frequency)) * Math.sin(Math.PI * i / numSamples);
        }
        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
        return generatedSnd;
    }

    void playSound(byte[] soundData){
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, soundData.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(soundData, 0, soundData.length);
        audioTrack.play();
    }
}
