package edu.sru.andgate.bitbot.missionlist;

import java.util.ArrayList;
import edu.sru.andgate.bitbot.R;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

public class MissionListActivity extends ListActivity {
	 private ProgressDialog m_ProgressDialog = null; 
	 private ArrayList<CustomListView> myMissions = null;
	 private missionListAdapter mission_adapter;
	 private Runnable viewOrders;
	 
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.mission_main);
	        myMissions = new ArrayList<CustomListView>();
	        this.mission_adapter = new missionListAdapter(this, R.layout.mission_row, myMissions);
	        setListAdapter(this.mission_adapter);
	        
	        viewOrders = new Runnable(){
	            @Override
	            public void run() {
	                getMissions();
	            }
	        };
	        Thread thread =  new Thread(null, viewOrders, "MagentoBackground");
	        thread.start();
	        m_ProgressDialog = ProgressDialog.show(MissionListActivity.this,    
	              "Please wait...", "Retrieving data ...", true);
	    }
	    private Runnable returnRes = new Runnable() {

	        @Override
	        public void run() {
	            if(myMissions != null && myMissions.size() > 0){
	                mission_adapter.notifyDataSetChanged();
	                for(int i=0;i<myMissions.size();i++)
	                mission_adapter.add(myMissions.get(i));
	            }
	            m_ProgressDialog.dismiss();
	            mission_adapter.notifyDataSetChanged();
	        }
	    };
	    private void getMissions(){
	          try{
	              myMissions = new ArrayList<CustomListView>();
	              CustomListView mission1 = new CustomListView();
	              mission1.setMissionName("Arena");
	              mission1.setMissionDescription("This dont mean shit");
	              CustomListView mission2 = new CustomListView();
	              mission2.setMissionName("Target Practice");
	              mission2.setMissionDescription("This dont mean shit");
	              myMissions.add(mission1);
	              myMissions.add(mission2);
	              Thread.sleep(5000);
	              Log.i("ARRAY", ""+ myMissions.size());
	            } catch (Exception e) { 
	              Log.e("BACKGROUND_PROC", e.getMessage());
	            }
	            runOnUiThread(returnRes);
	        }
}
