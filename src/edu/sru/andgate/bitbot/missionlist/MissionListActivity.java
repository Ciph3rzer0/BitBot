package edu.sru.andgate.bitbot.missionlist;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.ide.botbuilder.BotBuilderActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MissionListActivity extends ListActivity {
	// private ProgressDialog m_ProgressDialog = null; 
	 private ArrayList<CustomListView> myMissions = null;
	 private MissionListAdapter mission_adapter;
	 private Hashtable<String, String> mission_list;
	 private Hashtable<String, Integer> mission_icons;
	 private Button new_btn, old_btn;
	 
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.mission_main);
	        
	        myMissions = new ArrayList<CustomListView>();
	        mission_icons= new Hashtable<String,Integer>();
	        mission_list = new Hashtable<String, String>();
	       
	        this.mission_adapter = new MissionListAdapter(this, R.layout.mission_row, myMissions);
	        setListAdapter(this.mission_adapter);
	       
	        getMissions();
	        run();   
	        
	        Button new_btn = (Button)findViewById(R.id.new_btn);
	        new_btn.setText("New");
	        new_btn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					
				}
			});
	        
	        Button old_btn = (Button)findViewById(R.id.old_btn);
	        old_btn.setText("Old");
	        old_btn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) {
							
				}
			});
	        
	 }

	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		 	Intent myIntent = new Intent(MissionListActivity.this, MissionBriefingActivity.class);
        	myIntent.putExtra("Filename",  mission_list.get(v.getTag().toString()));
        	myIntent.putExtra("Icon",  mission_icons.get(v.getTag().toString()));
        	startActivity(myIntent);
	 }
	 
	 public void run() {
	            if(myMissions != null && myMissions.size() > 0){
	                mission_adapter.notifyDataSetChanged();
	                for(int i=0;i<myMissions.size();i++)
	                mission_adapter.add(myMissions.get(i));
	            }
	 }
	       
	 private void getMissions(){
	          try{
	              myMissions = new ArrayList<CustomListView>();
	              CustomListView mission1 = new CustomListView();
	              setAttributes(myMissions,mission1, "Arena", "Navigate through the Arena", "arena.xml", R.drawable.arena);  
	              CustomListView mission2 = new CustomListView();
	              setAttributes(myMissions, mission2, "Target Practice", "Test your shooting skills.", "target_practice.xml", R.drawable.target);
	          	} catch (Exception e) { 
	              Log.e("BACKGROUND_PROC", e.getMessage());
	            }
	        }
	 
	 private void setAttributes(ArrayList<CustomListView> array, CustomListView mission, String mission_name, String description, String filename, int image){
		 //set mission attributes
		 mission.setMissionName(mission_name);
         mission.setMissionDescription(description);
         mission.setFileName(filename);
         mission.setImageIcon(image);
         
         //add to file lookup, and image lookup table(s)
         mission_list.put(mission_name, filename);
         mission_icons.put(mission_name, image);
         array.add(mission);
	 }
}
