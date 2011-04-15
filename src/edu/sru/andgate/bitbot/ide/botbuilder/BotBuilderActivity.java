package edu.sru.andgate.bitbot.ide.botbuilder;

import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.graphics.GameActivity;
import edu.sru.andgate.bitbot.interpreter.SourceCode;
import edu.sru.andgate.bitbot.tools.ReadDirectory;
import edu.sru.andgate.bitbot.tools.ReadText;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class BotBuilderActivity extends Activity
{
	private ReadText readtxt;
	private Bot _currentBot;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ide_botbuilder_main);
		
		readtxt = new ReadText(getBaseContext());
		BotComponentView c = (BotComponentView)findViewById(R.id.bb_chassis);
		BotComponentView t = (BotComponentView)findViewById(R.id.bb_turret);
		
		c.setTitle("Square Chassis");
		c.setSummary("A stable base that is fast and sturdy.");
		c.setPicID(R.drawable.spinnerbase);
		
		t.setTitle("Basic Turret");
		t.setSummary("A turret for shooting stuff.");
		t.setPicID(R.drawable.spinnerturret);
		
		final String[] code_files = ReadDirectory.getFiles(getDir("Code",Context.MODE_PRIVATE).getPath());
             	
       	Spinner spinner = (Spinner) findViewById(R.id.program_title);
       	ArrayAdapter<String> adapter;
       	adapter = new ArrayAdapter<String>(this, R.layout.spinner_line, code_files);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);  
        
        final TextView tv = (TextView) findViewById(R.id.program_text);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {               
               	tv.setText(ReadText.readTextFileFromDirectory("Code",((String)((TextView) view).getText().toString())));
        }

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		
		}
		});		
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