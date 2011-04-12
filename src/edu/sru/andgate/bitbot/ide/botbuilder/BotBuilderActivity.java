package edu.sru.andgate.bitbot.ide.botbuilder;

import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.graphics.GameActivity;
import edu.sru.andgate.bitbot.interpreter.SourceCode;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BotBuilderActivity extends Activity
{
	private Bot _currentBot;
	
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
	
	/**
	 * Sets the code associated with the bot
	 * @param sc a SourceCode object.
	 */
	public void setProgram(SourceCode sc)
	{
		if (_currentBot != null )
			_currentBot.attachSourceCode(sc);
	}
	
	/**
	 * Start the mission
	 * @param v the view that called this.
	 */
	public void begin(View v)
	{
		// Start up the game engine
		startActivity(new Intent(BotBuilderActivity.this, GameActivity.class));
	}
	
}