package edu.sru.andgate.bitbot.missionlist;

import java.util.ArrayList;
import java.util.Hashtable;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.tools.Constants;
import edu.sru.andgate.bitbot.tools.FileManager;

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
	        
	        FileManager.setContext(getBaseContext());
	        myMissions = new ArrayList<CustomListView>();
	        mission_icons = new Hashtable<String,Integer>();
	        mission_list = new Hashtable<String, String>();
	 	        
	       String[] titles = getResources().getStringArray(R.array.mission_titles);
           String[] descriptions = getResources().getStringArray(R.array.mission_descriptions);
	       String[] files = getResources().getStringArray(R.array.mission_files);
	       int[] images = {R.drawable.arena, R.drawable.target};
            
           CustomListView[] clv = new CustomListView[titles.length];
	       for(int i = 0; i < clv.length; i++){
	    	   clv[i] = new CustomListView();
	    	   clv[i].setMissionName(titles[i]);
	           clv[i].setMissionDescription(descriptions[i]);
	           clv[i].setFileName(files[i]);
	           clv[i].setImageIcon(images[i]);
	           
	           //add to file lookup, and image lookup table(s)
	           mission_list.put(titles[i], files[i]);
	           mission_icons.put(titles[i], images[i]);
	           
	           myMissions.add(clv[i]);
	       }
	        
	       this.mission_adapter = new MissionListAdapter(this, R.layout.mission_row, myMissions);
	       setListAdapter(this.mission_adapter);
	       	        
	        new_btn = (Button)findViewById(R.id.new_btn);
	        new_btn.setText("New");
	        new_btn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					
				}
			});
	        
	        old_btn = (Button)findViewById(R.id.old_btn);
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
		 	if(Constants.finished_missions.contains(FileManager.readAssetsXML(mission_list.get(v.getTag().toString()), "mission-name"))){
		 		Toast.makeText(MissionListActivity.this, "Mission Already Completed", Toast.LENGTH_SHORT).show();
		 		Intent myIntent = new Intent(MissionListActivity.this, MissionBriefingActivity.class);
	        	myIntent.putExtra("Filename",  mission_list.get(v.getTag().toString()));
	        	myIntent.putExtra("Icon",  mission_icons.get(v.getTag().toString()));
	        	myIntent.putExtra("Map", FileManager.readAssetsXML(mission_list.get(v.getTag().toString()), "map-file"));
	        	startActivity(myIntent);
		 	}else{
		 		Intent myIntent = new Intent(MissionListActivity.this, MissionBriefingActivity.class);
	        	myIntent.putExtra("Filename",  mission_list.get(v.getTag().toString()));
	        	myIntent.putExtra("Icon",  mission_icons.get(v.getTag().toString()));
	        	myIntent.putExtra("Map", FileManager.readAssetsXML(mission_list.get(v.getTag().toString()), "map-file"));
	        	startActivity(myIntent);
		 	}
	 }

}