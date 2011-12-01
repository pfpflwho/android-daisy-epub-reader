package uk.org.rnib.innovation.daisyplayer;

import uk.org.rnib.innovation.daisyplayer.tests.Tests;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class TestRunner extends Activity{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setTextSize(20);
        tv.setText("Testing...");
        setContentView(tv);
        tv.forceLayout();
        /* replaced by proper unit tests
        Tests tests = new Tests();
        boolean ok = true;
        long d1 = new java.util.Date().getTime();
        ok &= tests.openTest();
        long d2 = new java.util.Date().getTime();
        ok &= tests.moveToBeginning();
        ok &= tests.moveToSuperHeadingFromFirst();
        ok &= tests.moveToPreviousHeadingFromFirst();
        ok &= tests.moveToNextHeadingFromFirst();
        ok &= tests.moveToFirstSubHeadingFromFirst();
        ok &= tests.moveToHeadingNcx13();
        ok &= tests.moveToPreviousHeadingFromNcx12();
        if (ok){
        	tv.setTextColor(Color.GREEN);
        	tv.setText("Good");
        }
        else{
        	tv.setTextColor(Color.RED);
        	tv.setText("Bad");
        }
        tv.append("\nIn " + (double)((d2 - d1) / 1000));
        */
    }
}
