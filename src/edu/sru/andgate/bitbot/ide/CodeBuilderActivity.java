package edu.sru.andgate.bitbot.ide;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.sru.andgate.bitbot.MainMenu;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.missionlist.CustomListView;
import edu.sru.andgate.bitbot.missionlist.MissionBriefingActivity;
import edu.sru.andgate.bitbot.missionlist.MissionListActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CodeBuilderActivity extends ListActivity { 
	 private ArrayList<CustomListView> botCodeOptions = null;
	 private CodeListAdapter code_adapter;
	 //private Hashtable<String, Integer> code_list = new Hashtable<String,Integer>();
	 
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.code_builder_main);
	        botCodeOptions = new ArrayList<CustomListView>();
	        this.code_adapter = new CodeListAdapter(this, R.layout.code_row, botCodeOptions);
	        setListAdapter(this.code_adapter);
	        
	        getMissions();
	        run();
	     
	 }
	  
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		 Intent engineIntent = new Intent(CodeBuilderActivity.this, IDE.class);
		 //engineIntent.putExtra("File", code_list.get(v.getTag().toString()));
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
	              setAttributes(botCodeOptions, codeOption1, "Arena Challenge Code", "This dont mean shit", 0);
	              CustomListView codeOption2 = new CustomListView();
	              setAttributes(botCodeOptions, codeOption2, "Target Practice Code", "This dont mean shit", 0);
	            } catch (Exception e) { 
	              Log.e("BACKGROUND_PROC", e.getMessage());
	            }
	        }
	 private void setAttributes(ArrayList<CustomListView> array, CustomListView code_option, String code_name, String description, int fileLoc){
		 //set mission attributes
		 code_option.setMissionName(code_name);
         code_option.setMissionDescription(description);
        // code_option.setFileName(filename);
        
         //add to file lookup table
         //code_list.put(code_name, fileLoc);
        
         array.add(code_option);
	 }
}
