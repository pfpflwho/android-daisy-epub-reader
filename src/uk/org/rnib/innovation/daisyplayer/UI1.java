package uk.org.rnib.innovation.daisyplayer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class UI1 extends Activity {
	private TextView tv;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = new TextView(this);
        setContentView(tv);
        // DaisyPlayer dp = (DaisyPlayer)getIntent().getSerializableExtra("payload");
        DummyDaisyPlayer dp = (DummyDaisyPlayer)getIntent().getSerializableExtra("payload");
        tv.setText("Message=" + dp.getMessage());
    }
}
