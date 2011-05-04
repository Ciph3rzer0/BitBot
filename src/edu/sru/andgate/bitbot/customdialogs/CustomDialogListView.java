package edu.sru.andgate.bitbot.customdialogs;

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
import android.widget.Toast;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.ide.CodeBuilderActivity;
import edu.sru.andgate.bitbot.tools.FileManager;

public class CustomDialogListView extends Dialog 
{
    String file;	//file in focus
    CodeBuilderActivity activity;	//current activity
    
    public CustomDialogListView(CodeBuilderActivity act, String file, int theme) 
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
        
        //List of options
        String[] list = {"Share", "Save As...", "Rename", "Delete", "Back"};
        
        lst.setAdapter(new ArrayAdapter<String>(activity,R.layout.custom_popup_row, list));      
        
        //find out which option was selected
        lst.setOnItemClickListener(new OnItemClickListener() {
        	
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
        		String clicked = ((TextView) v).getText().toString();
        		Log.v("Test", clicked);
        		if(clicked.equalsIgnoreCase("Share")){
        			//Start a new intent to send email
        			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
					emailIntent.setType("text/txt"); //type of content in email
					emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "A Gift From BitBot");
					emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, FileManager.readTextFileFromDirectory("Code", file));
					activity.startActivity(Intent.createChooser(emailIntent, "Sending mail..."));
        		}else if(clicked.equalsIgnoreCase("Save As...")){
        			//prompt user to save file
    				promptSaveAs(file);
        		}else if(clicked.equalsIgnoreCase("Rename")){
        			//prompt for  rename
        			promptRename(file);
        		}else if (clicked.equalsIgnoreCase("Delete")){
        			//remove file from list
        			FileManager.deleteTextFile("Code", file);
        			restartActivity();
        		}else if(clicked.equalsIgnoreCase("Back")){
        			//return focus to user
        			dismissCustomDialog();
        		}
        		
        		dismissCustomDialog();
           	}
        	});
        
    }
    
    /*
     * Prompt user for filename to save the file as...
     */
    private void promptSaveAs(final String srcFileName){
    	AlertDialog.Builder alert = new AlertDialog.Builder(activity);

		alert.setTitle("Save file As: ");
		alert.setMessage("New File Name");

		// Set an EditText view to get user input 
		final EditText input = new EditText(activity);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			//if yes try to save file
			String dstFileName = input.getText().toString();
			//if file doesn't already exist, continue
			if(!checkFileExistence(dstFileName)){
  				FileManager.saveCodeFileAs(srcFileName, dstFileName);
  				restartActivity();
  			}else{
  				//if it did exist, ask user if they want to overwrite the file
  				pomptOverwrite("saveas", srcFileName, dstFileName);
  			}
		}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // return focus
		  }
		});
		
		alert.show();
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
				  String dstFileName = input.getText().toString();
				  if(dstFileName == ""){
				  dstFileName = "New File.txt";
			  }
			  
			  //check if file exists already
			  if(!checkFileExistence(dstFileName)){
				  //if it doesn't rename
				  FileManager.renameCodeFile(srcFileName, dstFileName);
				  restartActivity();
			  }else{
				  //if it does ask to overwrite
				  pomptOverwrite("rename", srcFileName, dstFileName);
			  }
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    //give focus back to requester
		  }
		});
		
		alert.show();
    }
    
    //refresh activity to show changes
    private void restartActivity(){
    	Intent intent = activity.getIntent();
		activity.finish();
		activity.startActivity(intent);
    }
    
    //close the dialog
    private void dismissCustomDialog(){
    	this.dismiss();
    }
    
    //return if a file already exists or not
    private boolean checkFileExistence(String dstName){
    	for(int i = 0; i < activity.getCodeFiles().length; i++){
    		if(activity.getCodeFiles()[i].equals(dstName)){
    				return true;
    		}
    	}
    	return false;
    }
    
    /*
     * ask user if it is okay to overwrite an existing file
     */
    private void pomptOverwrite(final String caller, final String srcFileName, final String dstFileName) {
		AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		alert.setTitle("File Already Exists");
		alert.setMessage("Do you want to overwrite this file?");
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			//if yes, find out if caller was for renaming or saving a file as...
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(caller.equals("rename")){
					FileManager.renameCodeFile(srcFileName, dstFileName);
					restartActivity();
					Toast.makeText(activity, "File Overwritten Successfully", Toast.LENGTH_SHORT).show();
				}else if(caller.equals("saveas")){
					FileManager.saveCodeFileAs(srcFileName, dstFileName);
	  				restartActivity();
				}
			}
		});
		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			//if no, let user no nothing was overwritten
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(activity, "File Not Overwritten", Toast.LENGTH_SHORT).show();
			}
		});
	alert.show();
	}   

}