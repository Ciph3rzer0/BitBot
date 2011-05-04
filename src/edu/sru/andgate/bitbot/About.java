package edu.sru.andgate.bitbot;

import edu.sru.andgate.bitbot.tools.FileManager;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends Activity {
	TextView about_text;
	
	@Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        about_text = (TextView)findViewById(R.id.about);
        about_text.setText(FileManager.readAssetsXML("about.xml", "text"));
	}
}
