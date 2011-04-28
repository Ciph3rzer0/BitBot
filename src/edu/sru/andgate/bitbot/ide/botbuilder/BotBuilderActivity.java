package edu.sru.andgate.bitbot.ide.botbuilder;

import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.graphics.BotLayer;
import edu.sru.andgate.bitbot.graphics.DrawableBot;
import edu.sru.andgate.bitbot.graphics.GameActivity;
import edu.sru.andgate.bitbot.graphics.NickGameActivity;
import edu.sru.andgate.bitbot.interpreter.SourceCode;
import edu.sru.andgate.bitbot.tools.Constants;
import edu.sru.andgate.bitbot.tools.FileManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class BotBuilderActivity extends Activity
{
	private Bot _currentBot;
	private Constants constant;
	private BotComponentView c, t;
	private TextView tv;
	private Spinner spinner;
	private Button b;
	private String missionType, mapFile;
	private String[] code_files;
	ArrayAdapter<String> adapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ide_botbuilder_main);
		
		missionType = getIntent().getExtras().getString("GameType");
		mapFile = getIntent().getExtras().getString("Map");
		
		FileManager.setContext(getBaseContext());
		c = (BotComponentView)findViewById(R.id.bb_chassis);
		t = (BotComponentView)findViewById(R.id.bb_turret);
			
		c.setTitle("Square Chassis");
		c.setSummary("A stable base that is fast and sturdy.");
		c.setPicID(R.drawable.adambot);
		
		t.setTitle("Basic Turret");
		t.setSummary("A turret for shooting stuff.");
		t.setPicID(R.drawable.adamturret);
		
		code_files = FileManager.getFileNamesInDir(getDir("Code",Context.MODE_PRIVATE).getPath());
		
		spinner = (Spinner) findViewById(R.id.program_title);
		adapter = new ArrayAdapter<String>(this, R.layout.spinner_line, code_files);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);  
		
		tv = (TextView) findViewById(R.id.program_text);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) 
			{
				tv.setText(FileManager.readTextFileFromDirectory("Code",((String)((TextView) view).getText().toString())));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub
			}
		});
		
		b = (Button) findViewById(R.id.bb_soft1);
		b.setTag(R.drawable.bulletnew);
		b.setText("Bullet");
		b.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.bulletnew), null, null,null);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("BitBot", b.getTag().toString());
			}
		});
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.botbuilder_menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.saveas:
				promptSaveBot();
				break;
				
		}
		return true;
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
		//Save bot to xml before going to graphics - Possible load from xml inside graphics
		saveBot("test_bot.xml");
				
		// Start up the game engine
		Intent engineIntent = new Intent(BotBuilderActivity.this, NickGameActivity.class);
		engineIntent.putExtra("Bot", "test_bot.xml");
		engineIntent.putExtra("GameType", missionType);
		engineIntent.putExtra("Map", mapFile);
		startActivity(engineIntent);
		
	}
	
	public void promptSaveBot(){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Save Bot As...");
		alert.setMessage("Bot Filename:");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			  String value = input.getText().toString();
			  if(value != null)
				  saveBot(value);
		}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});
		
		alert.show();
	}	
	
	public void saveBot(String filename){
		//Save bot to xml before going to graphics - Possible load from xml inside graphics
		_currentBot = new Bot();
		_currentBot.setName("Bot"); //for now, need to get the name from the textview?
		constant = new Constants();
		_currentBot.setBase(c.getPicID());
		_currentBot.setTurret(t.getPicID()); 
		_currentBot.setBullet(R.drawable.bulletnew);
		_currentBot.setCode(tv.getText().toString());
		Log.v("BitBot", _currentBot.getCode().getCode());
		_currentBot.saveBotToXML(this.getBaseContext(), filename);
	}
	
}