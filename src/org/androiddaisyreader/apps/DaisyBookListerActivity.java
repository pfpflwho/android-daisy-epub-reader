package org.androiddaisyreader.apps;

import static org.androiddaisyreader.model.XmlUtilities.obtainEncodingStringFromInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.androiddaisyreader.model.BookContext;
import org.androiddaisyreader.model.Daisy202Book;
import org.androiddaisyreader.model.FileSystemContext;
import org.androiddaisyreader.model.Navigator;
import org.androiddaisyreader.model.NccSpecification;
import org.androiddaisyreader.model.Section;
import org.androiddaisyreader.model.ZippedBookContext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DaisyBookListerActivity extends Activity {
    private EditText filename;
	private Button nextSection;
	private Daisy202Book book;
	private TextView sectionTitle;
	private Navigator navigator;
	private Controller controller = new Controller();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button open = (Button)findViewById(R.id.openbook);
        open.setOnClickListener(openListener);
        
        filename = (EditText)findViewById(R.id.filename);
        
        nextSection = (Button) findViewById(R.id.nextsection);
        nextSection.setOnClickListener(nextSectionListener);
        
        sectionTitle = (TextView) findViewById(R.id.sectiontitle);
    }
    
    private OnClickListener nextSectionListener = new OnClickListener() {
    	public void onClick(View v) {
    		controller.next();
    	}
    };
    
    private OnClickListener openListener = new OnClickListener() {

		public void onClick(View v) {
    		InputStream contents;
			try {
				// contents = new FileInputStream(filename.getText().toString());
				BookContext bookContext = openBook(filename.getText().toString());
				
				contents = bookContext.getResource("ncc.html");
				
				String encoding = obtainEncodingStringFromInputStream(contents);
				
				book = NccSpecification.readFromStream(contents);
				Toast.makeText(getBaseContext(), book.getTitle(), Toast.LENGTH_LONG).show();
				nextSection.setEnabled(true);
				navigator = new Navigator(book);
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
    	}
    };
    
	private static BookContext openBook(String filename) throws IOException {
		BookContext bookContext;
		
		if (filename.endsWith(".zip")) {
			bookContext = new ZippedBookContext(filename);
		} else {
			File directory = new File(filename);
			bookContext = new FileSystemContext(directory.getParent());
			directory = null;
		}
		return bookContext;
	}
	
	private class NavigationListener {
		
	}
	
	private class Controller {
		public void next() {
			// TODO 20120220 (jharty): First step in the migration process. Clean me up!
			if (navigator.hasNext()) {
				Section section = ((Section) navigator.next());
				sectionTitle.setText(section.getTitle());
			} else {
				Toast.makeText(getBaseContext(), "At end of " + book.getTitle(), Toast.LENGTH_LONG).show();
				nextSection.setEnabled(false);
			}
			
		}
	}
	
	
}