package edu.sru.andgate.bitbot.ide;

import java.util.ArrayList;

import edu.sru.andgate.bitbot.MainMenu;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.missionlist.CustomListView;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CodeBuilderActivity extends ListActivity {
	// private ProgressDialog m_ProgressDialog = null; 
	 private ArrayList<CustomListView> botCodeOptions = null;
	 private missionListAdapter code_adapter;
	 //private Runnable viewOrders;
	 
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.mission_main);
	        botCodeOptions = new ArrayList<CustomListView>();
	        this.code_adapter = new missionListAdapter(this, R.layout.code_row, botCodeOptions);
	        setListAdapter(this.code_adapter);
	        
	        getMissions();
	        run();
	     
	 }
	  
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		 Intent engineIntent = new Intent(CodeBuilderActivity.this, IDE.class);
		 startActivity(engineIntent);
	 }
	 
	 public void run() {
	            if(botCodeOptions != null && botCodeOptions.size() > 0){
	                code_adapter.notifyDataSetChanged();
	                for(int i=0;i<botCodeOptions.size();i++)
	                code_adapter.add(botCodeOptions.get(i));
	            }
	 }
	       
	 private void getMissions(){
	          try{
	              botCodeOptions = new ArrayList<CustomListView>();
	              CustomListView codeOption1 = new CustomListView();
	              codeOption1.setMissionName("Arena Challenge Code");
	              codeOption1.setMissionDescription("This dont mean shit");
	              
	              CustomListView codeOption2 = new CustomListView();
	              codeOption2.setMissionName("Target Practice Code");
	              codeOption2.setMissionDescription("This dont mean shit");
	              botCodeOptions.add(codeOption1);
	              botCodeOptions.add(codeOption2);
	            } catch (Exception e) { 
	              Log.e("BACKGROUND_PROC", e.getMessage());
	            }
	        }
}
