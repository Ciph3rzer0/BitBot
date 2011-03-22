package edu.sru.andgate.bitbot;

import edu.sru.andgate.bitbot.graphics.GameEngine;
import edu.sru.andgate.bitbot.ide.IDE;
import edu.sru.andgate.bitbot.interpreter.Test;
import edu.sru.andgate.bitbot.tutorial.Tutorial_List;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity
{
	/// test
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		String[] menu_content = getResources().getStringArray(R.array.menu_content);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, menu_content));
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				String viewText = (String) ((TextView) view).getText();
				
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(), viewText, Toast.LENGTH_SHORT).show();
				
				if (viewText.equalsIgnoreCase("Engine"))
				{
					Intent engineIntent = new Intent(MainActivity.this, GameEngine.class);
					startActivity(engineIntent);
				}
				else if (viewText.equalsIgnoreCase("Tutorial"))
				{
					Intent engineIntent = new Intent(MainActivity.this, Tutorial_List.class);
					startActivity(engineIntent);
				}
				else if (viewText.equalsIgnoreCase("Interpreter"))
				{
					Intent engineIntent = new Intent(MainActivity.this, Test.class);
					startActivity(engineIntent);
				}else if (viewText.equalsIgnoreCase("IDE"))
				{
					Intent engineIntent = new Intent(MainActivity.this, IDE.class);
					startActivity(engineIntent);
				}
			}
		});
		
		// Intent engineIntent = new Intent(MainActivity.this, GameEngine.class);
		// // engineIntent.putExtra("com.android.samples.SpecialValue", "Hello, Joe!"); // key/value pair, where key needs current package prefix.
		// startActivity(engineIntent);
		//
		
	}
}