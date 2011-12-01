package uk.org.rnib.innovation.daisyplayer;

import java.io.File;
import java.io.IOException;

import uk.org.rnib.innovation.audioplayer.AudioPlayer;
import uk.org.rnib.innovation.audioplayer.MockAudioPlayer;
import uk.org.rnib.innovation.audioplayer.WrappedMediaPlayer;
import uk.org.rnib.innovation.fileopener.FileOpener;
import uk.org.rnib.innovation.fileopener.PhysicalFileOpener;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ader.R;
import com.ader.utilities.Logging;
import com.google.marvin.widget.GestureOverlay;
import com.google.marvin.widget.GestureOverlay.Gesture;
import com.google.marvin.widget.GestureOverlay.GestureListener;

public class UI extends Activity implements OnCompletionListener {
	private static final String TAG = "UI";
	private TextView mainText;
	private TextView statusText;
	private TextView depthText;
	private GestureOverlay gestureOverlay;
	private TextView contentsToRead;

	// private File sdcard;
	private String root;
	private DaisyPlayer dp;
	private AudioPlayer ap;
	private TextRenderer tr;
	private boolean isPlaying = false;
	private int depth = 1;
	private String headingAudioRef;
	private boolean browsing = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daisyplayerframe);
		depthText = (TextView) findViewById(R.id.depthText);
		mainText = (TextView) findViewById(R.id.mainText);
		statusText = (TextView) findViewById(R.id.statusText);
        contentsToRead = (TextView) findViewById(R.id.contentsToRead);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.daisyPlayerLayout);
		gestureOverlay = new GestureOverlay(this, gestureListener);
		frameLayout.addView(gestureOverlay);
		setContentView(frameLayout);

		try {
			statusText.setText("Opening...");
			ap = new WrappedMediaPlayer(frameLayout, this);
			// ap = new MockAudioPlayer(frameLayout, this, 2);
			FileOpener fo = new PhysicalFileOpener();
			tr = new TextRenderer(fo);
			dp = new DaisyPlayer(fo);
			// sdcard = Environment.getExternalStorageDirectory();
			root = getIntent().getStringExtra(DaisyPlayer.DAISY_ROOT_KEY);
			if (root == null)
				root = "/Are_you_ready_z3986/";
			String name = getIntent().getStringExtra(DaisyPlayer.DAISY_NAME_KEY);
			if (name == null) {
				// TODO 20111129(jharty): this assumes DAISY3 we need to have better error handling.
				// Consider simply logging the problem and finishing this activity.
				name = "speechgen.opf";
			}
			File f = new File(/* sdcard + */root + name);
			if (f.exists()) {
				dp.open(/* sdcard + */root, name);
				dp.SyncToNavPoint();
				if (dp.getHeading() != null) {
					renderDepth();
					// dp.moveToNextNavPoint(true);
					renderHeading();
					/*
					 * dp.moveToNextNavPoint(); mainText.setText(dp.getMessage());
					 */
				}
			} else
				mainText.setText("Unable to open " + /* sdcard + */root + name);
			// TODO 20111129 Beware catching Exceptions. Catch specific exceptions.
		} catch (Exception e) {
			statusText.setText(e.toString() + e.getMessage());
		}
	}

	private void renderHeading() {
		Heading h = dp.getHeading();
		AudioClip audio = h.getAudio();
		if (audio == null) {
			dp.SyncToNavPoint();
			audio = dp.getAudio();
		}
		String ref = audio.getSrc() + " " + audio.getClipBegin() + " " + audio.getClipEnd();
		if (!ref.equals(headingAudioRef))
		{
			mainText.setText("(" + h.getDepth() 
					+ (h.hasChildren() ? "+" : "")
					+ ") "
					+ h.getText()
					+ "\n" + audio.toString() + "\n" + h.getContent());
			headingAudioRef = ref;
			play(audio);
		}
	}
	
	private void renderDepth() {
		depthText.setText(depth + " of " + dp.getMaxDepth());
	}

	private void renderNone() {
		statusText.setText("None");
	}
	
	private void renderBlank() {
		statusText.setText("");
	}
	
	private void parentNavPoint() {
		if (dp.moveToSuperHeading()) {
			// mainText.setText(dp.getNavPoint() != null ? dp.getNavPoint().toString()
			// : "null");
			renderHeading();
			renderBlank();
		}
		else
			renderNone();
	}

	private void firstChildNavPoint() {
		if (dp.moveToFirstSubHeading()) {
			renderHeading();
			renderBlank();
		}
		else
			renderNone();
	}

	private void previousNavPoint() {
		if (dp.moveToPreviousHeading(false, false)) {
			renderHeading();
			renderBlank();
		}
		else
			renderNone();
	}

	private void nextNavPoint() {
		if (dp.moveToNextHeading(false, false)) {
			renderHeading();
			renderBlank();
		}
		else
			renderNone();
	}

	private void playPause() {
		isPlaying = !isPlaying;
		if (!isPlaying)
			ap.pause();
		else
			ap.start();
	}

	private void play() {
		dp.SyncToNavPoint();
		browsing = false;
		isPlaying = true;
		playing();
	}

	private void playing() {
		// mainText.append("\n" + dp.getAudio().toString());
		// contentsToRead.setText(dp.getTextUrl() + "\n" + dp.getAudio().toString());
		AudioClip a = dp.getAudio();
		statusText.setText(a.getClipBegin() + "-" + a.getClipEnd());
		// renderHeading();
		try {
			contentsToRead.setText(tr.Render(root + dp.getTextUrl()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// isPlaying = true;
		play(a);
	}

	private void play(AudioClip audio) {
		ap.play(/* sdcard + */root + audio.getSrc(), audio.getClipBegin(),
				audio.getClipEnd());
	}

	private void next() {
		if (!browsing) {
			if (dp.moveToNext())
				playing();
			else {
				isPlaying = false;
				statusText.setText("Stopped");
			}
		}
	}
	
	private void toggleBrowsing() {
		browsing = !browsing;
		statusText.setText(browsing ? "Browsing" : "Playback");
	}
	
	private void up() {
		if (browsing)
			previousNavPoint();
		else {
			if (dp.moveToPreviousHeading(depth)) {
				renderHeading();
				play();
			}
			else
				renderNone();
		}
	}

	private void down() {
		if (browsing)
			nextNavPoint();
		else {
			if (dp.moveToNextHeading(depth)) {
				renderHeading();
				play();
			}
			else
				renderNone();
		}
	}

	private void left() {
		if (browsing)
			parentNavPoint();
		else
			if (depth > 1) {
				depth--;
				renderDepth();
			}
	}
	
	private void right() {
		if (browsing)
			firstChildNavPoint();
		else
			if (depth < dp.getMaxDepth()) {
				depth++;
				renderDepth();
			}
	}

	public void onCompletion(MediaPlayer arg0) {
		statusText.setText("@ " + ap.getCurrentPosition());
		if (isPlaying)
			next();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
			left();
			return true;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			right();
			return true;
		case KeyEvent.KEYCODE_DPAD_UP:
			up();
			return true;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			down();
			return true;
		case KeyEvent.KEYCODE_ENTER:
			play();
			return true;
		case KeyEvent.KEYCODE_SPACE:
			playPause();
			return true;
		case KeyEvent.KEYCODE_T:
			toggleBrowsing();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private GestureListener gestureListener = new GestureListener() {

		private long startTime;

		public void onGestureStart(int g) {
			startTime = System.currentTimeMillis();
			Logging.logInfo(TAG, "onGestureStart @" + startTime + " Value: "
					+ g);
		}

		public void onGestureChange(int g) {
			long interimValue = System.currentTimeMillis() - startTime;
			Logging.logInfo(TAG, "onGestureChange. Duration is: "
					+ interimValue + " Value: " + g);
		}

		public void onGestureFinish(int g) {
			long timeTaken = System.currentTimeMillis() - startTime;
			Logging.logInfo(TAG, "onGestureFinish. Duration is: " + timeTaken
					+ " Value: " + g);

			switch (g) {
			case Gesture.CENTER:
				if (timeTaken > 1000) {
					toggleBrowsing();
				} else {
					if (browsing) {
						play();
					} else {
						playPause();
					}
				}
				break;
			case Gesture.UP:
				up();
				break;
			case Gesture.DOWN:
				down();
			case Gesture.LEFT:
				left();
			case Gesture.RIGHT:
				right();
			case Gesture.DOWNLEFT:
				Log.w(TAG, "Downleft Gesture not yet implemented");
				break;
			case Gesture.DOWNRIGHT:
				next();
				break;
			default:
				Log.i(TAG, "Unused Gesture entered. Ignoring it.");
				break;
			}
		}
	};
}