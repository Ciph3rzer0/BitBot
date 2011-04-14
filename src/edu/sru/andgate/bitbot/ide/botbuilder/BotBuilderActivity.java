package edu.sru.andgate.bitbot.ide.botbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.graphics.GameActivity;
import edu.sru.andgate.bitbot.interpreter.SourceCode;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

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
		
		final String[] temp = getFiles(getDir("Code",Context.MODE_PRIVATE).getPath());
             	
       	Spinner spinner = (Spinner) findViewById(R.id.program_title);
       	ArrayAdapter<String> adapter;
       	adapter = new ArrayAdapter<String>(this, R.layout.spinner_line, temp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);  
        
        final TextView tv = (TextView) findViewById(R.id.program_text);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {               
               	tv.setText(readTextFile((String)((TextView) view).getText().toString()));
               	//tv.setText((String)((TextView) view).getText().toString());

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
	
	 public static String[] getFiles(String dir)
	    {
		    File folder = new File(dir);
		    File[] listOfFiles = folder.listFiles();
		    String[] items = new String[listOfFiles.length];
		    for (int i = 0; i < listOfFiles.length; i++) 
		    {
		    	if (listOfFiles[i].isFile()) 
		    	{
		    		items[i] = listOfFiles[i].getName();
		    	}
		    	else if (listOfFiles[i].isDirectory()) 
		    	{
		    		// System.out.println("Directory " + listOfFiles[i].getName());
		    	}
		    }
		    return items;	
	    }
	    
	    public String readTextFile(String filename)
	    {
	    	 String line;
	    	 String temp = "";
	    	// try opening the myfilename.txt
	    	 try 
	    	 {
	    		 // open the file for reading
	    		 File f = new File(getDir("Code", Context.MODE_PRIVATE), filename);
	    		 FileInputStream fis = new FileInputStream(f);
	    		 //InputStream instream = openFileInput();
	    	 
	    		 // if file the available for reading
	    		 if (fis != null) {
	    			 // prepare the file for reading
	    			 InputStreamReader inputreader = new InputStreamReader(fis);
	    			 BufferedReader buffreader = new BufferedReader(inputreader);
	    	 
	    			 // read every line of the file into the line-variable, on line at the time
	    			 while (( line = buffreader.readLine()) != null) {
	    				 // do something with the settings from the file
	    				 temp+=line+"\n";
	    			 }    	 
	    		 }
	    	 
	    		 	// close the file again
	    		 	fis.close();
	    		 	return temp.toString();
	    	  } catch (java.io.FileNotFoundException e) {
	    		  // do something if the myfilename.txt does not exits
	    		  Log.v("Test", "File Not Found");
	    	  } catch (IOException e) {
	    		  // TODO Auto-generated catch block
	    		  Log.v("Test", "I/O Error");
			}
			return temp.toString();
	    }
	
}