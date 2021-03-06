package edu.sru.andgate.bitbot.tutorial;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.tools.Constants;

public class GameSpecific_Tutorial_List extends ListActivity {
	private String[] tutorial_items;
	private int endList;
	private android.widget.ListView list;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //set the items in ListView to names of menu_contents string array  
        tutorial_items = getResources().getStringArray(R.array.gamespecific_list);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.tutorial_list, tutorial_items));
        endList = tutorial_items.length-1;
        //Filter the text in the list
        list = getListView();
        list.setTextFilterEnabled(true);

        /*
         * on list click() start a new activity
         * pass in the resource id of the file cooresponding to the list item (Still need to figure out)
         */
        list.setOnItemClickListener(new OnItemClickListener() 
        {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        	{
         	  if(((String)((TextView) view).getText() == tutorial_items[endList].toString()))
         	  {
         		  //go back to main menu a.k.a kill this activity
         		  finish();
         	  }else{
	         		  /*
	         		   * start new activity, sending cooresponding tutorial file path, and simulation ability
	         		   */
	         		  if(Constants.finished_tutorials.contains(Constants.tutorials_table.get((String)((TextView) view).getText()))){
		         			Toast.makeText(GameSpecific_Tutorial_List.this, "This tutorial has been completed.", Toast.LENGTH_SHORT).show();
		         			Intent myIntent = new Intent(GameSpecific_Tutorial_List.this, Main_Tutorial.class);
				         	myIntent.putExtra("File_ID", Constants.tutorials_table.get((String)((TextView) view).getText()));
				         	myIntent.putExtra("Sim_Flag", Constants.simulation_table.get((String)((TextView) view).getText()));
				         	startActivity(myIntent);
		         		}else{
			         		Intent myIntent = new Intent(GameSpecific_Tutorial_List.this, Main_Tutorial.class);
			         		myIntent.putExtra("File_ID", Constants.tutorials_table.get((String)((TextView) view).getText()));
			         		myIntent.putExtra("Sim_Flag", Constants.simulation_table.get((String)((TextView) view).getText()));
			         		myIntent.putExtra("BotNum", Constants.tutorial_bot_num.get((String) ((TextView) view).getText()));
			         		startActivity(myIntent);
	         		  }
	         	  }
        	}
          
        });
  }
}