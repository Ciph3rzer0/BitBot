package edu.sru.andgate.bitbot.customdialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.tools.FileManager;

public class BotBuilderDialog extends Dialog 
{
    String file;		//file in focus
    Activity activity;	//activity calling this
    
    public BotBuilderDialog(Activity act, String file, int theme) 
    {
        super(act, theme);
        this.file = file;
        this.activity = act;
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_popup);
        
        final ListView lst=(ListView)findViewById(R.id.myList);
        
        //List of Options
        String[] list = {"Rename File", "Delete", "Back"};
        
        //set the ListView items to our custom layout, with the List of Options available
        lst.setAdapter(new ArrayAdapter<String>(activity,R.layout.custom_popup_row, list));      
        
        //figure out which item in the list was clicked
        lst.setOnItemClickListener(new OnItemClickListener() {
        	
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
        		String clicked = ((TextView) v).getText().toString();
        		Log.v("Test", clicked);
        		if(clicked.equalsIgnoreCase("Rename File")){
        			//prompt user for new name
        			promptRename(file);
        		}else if (clicked.equalsIgnoreCase("Delete")){
        			//delete file & refresh activity to accept new change
        			FileManager.deleteXMLFile(file);
        			restartActivity();
        		}else if(clicked.equalsIgnoreCase("Back")){
        			dismissCustomDialog();
        		}
        		dismissCustomDialog();
           	}
        	});
        
    }
    
   /*
    * Ask user for new filename 
    */
    private void promptRename(final String srcFileName){
    	AlertDialog.Builder alert = new AlertDialog.Builder(activity);

		alert.setTitle("Rename file to: ");
		alert.setMessage("Rename File");

		// Set an EditText view to get user input 
		final EditText input = new EditText(activity);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				//If yes change filename
				String dstFileName = input.getText().toString();
				if(dstFileName == ""){
					dstFileName = "New File.txt";
				}
				FileManager.renameXMLFile(srcFileName, dstFileName);
				restartActivity();
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Return Focus
		  }
		});
		
		alert.show();
    }
    
    //refresh the activity to show the new changes
    private void restartActivity(){
    	Intent intent = activity.getIntent();
		activity.finish();
		activity.startActivity(intent);
    }
    
    //close the dialog
    private void dismissCustomDialog(){
    	this.dismiss();
    }
   

}