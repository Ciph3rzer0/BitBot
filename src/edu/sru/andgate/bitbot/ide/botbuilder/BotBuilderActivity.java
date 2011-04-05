package edu.sru.andgate.bitbot.ide.botbuilder;

import edu.sru.andgate.bitbot.R;
import android.app.Activity;
import android.os.Bundle;

public class BotBuilderActivity extends Activity
{
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ide_botbuilder_main);
		
		BotComponentView c = (BotComponentView)findViewById(R.id.bb_chassis);
		BotComponentView t = (BotComponentView)findViewById(R.id.bb_turret);
		
		c.setTitle("Square Chassis");
		c.setSummary("A stable base that is fast and sturdy.");
		c.setPicID(R.drawable.spinnerbase);
		
		t.setTitle("Basic Turret");
		t.setSummary("A turret for shooting stuff.");
		t.setPicID(R.drawable.spinnerturret);
	}
	
}