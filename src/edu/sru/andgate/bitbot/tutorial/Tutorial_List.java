package edu.sru.andgate.bitbot.tutorial;

import java.util.Hashtable;

import edu.sru.andgate.bitbot.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Tutorial_List extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        /*
         * table lookups for selected tutorial 
         */
        final Hashtable<String, String> tutorials_table = new Hashtable<String, String>();
	        tutorials_table.put("Getting Started", "getting_started.xml");
	        tutorials_table.put("Print Statement", "print_tutorial.xml");
	        tutorials_table.put("Back to Main Menu", "");
        
        /*
         * table look up for tutorial's simulation ability
         */
        final Hashtable<String, Integer> simulation_table = new Hashtable<String, Integer>();
        	simulation_table.put("Print Statement", 1);
        
        //set the items in ListView to names of menu_contents string array  
        final String[] menu_items = getResources().getStringArray(R.array.menu_contents);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.tutorial_list, menu_items));
        final int endList = menu_items.length-1;
        //Filter the text in the list
        final android.widget.ListView list = getListView();
        list.setTextFilterEnabled(true);

        /*
         * on list click() start a new activity
         * pass in the resource id of the file cooresponding to the list item (Still need to figure out)
         */
        list.setOnItemClickListener(new OnItemClickListener() 
        {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        	{
         	  if(((String)((TextView) view).getText() == menu_items[endList].toString()))
         	  {
         		  //go back to main menu a.k.a kill this activity
         		  finish();
         	  }else
	         	  {
         		  /*
         		   * start new activity, sending cooresponding tutorial file path, and simulation ability
         		   */
	         		Intent myIntent = new Intent(Tutorial_List.this, Main_Tutorial.class);
	         		myIntent.putExtra("File_ID", tutorials_table.get((String)((TextView) view).getText()));
	         		myIntent.putExtra("Sim_Flag", simulation_table.get((String)((TextView) view).getText()));
	         		startActivity(myIntent);
	         	  }
        	}
          
        });
  }
}