package edu.sru.andgate.bitbot.customdialog;

import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.ide.CodeBuilderActivity;
import edu.sru.andgate.bitbot.tools.FileManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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

public class CustomDialog extends Dialog 
{
    TextView selection;
    Context context;
    String file;
    Activity activity;
    
    public CustomDialog(Activity act, String file, Context context, int theme) 
    {
        super(context, theme);
        this.context = context;
        this.file = file;
        this.activity = act;
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_popup);
        final ListView lst=(ListView)findViewById(R.id.myList);
        String[] list = {"Save As...", "Rename", "Delete", "Back"};
        
        lst.setAdapter(new ArrayAdapter<String>(context,R.layout.custom_popup_row, list));      
        
        lst.setOnItemClickListener(new OnItemClickListener() {
        	
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
        		String clicked = ((TextView) v).getText().toString();
        		Log.v("Test", clicked);
        		if(clicked.equalsIgnoreCase("Save As...")){
        			promptSaveAs(activity, file);
        		}else if(clicked.equalsIgnoreCase("Rename")){
        			promptRename(activity, file);
        		}else if (clicked.equalsIgnoreCase("Delete")){
        			FileManager.deleteFile("Code", file);
        			restartActivity();
        		}else if(clicked.equalsIgnoreCase("Back")){
        			dismissCustomDialog();
        		}
        		
        		dismissCustomDialog();
           	}
        	});
        
    }
    
    private void promptSaveAs(Activity act, final String srcFileName){
    	AlertDialog.Builder alert = new AlertDialog.Builder(this.context);

		alert.setTitle("Save file As: ");
		alert.setMessage("New File Name");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this.context);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			  String dstFileName = input.getText().toString();
			  FileManager.saveCodeFileAs(srcFileName, dstFileName);
			  restartActivity();
		}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});
		
		alert.show();
    }
    
    private void promptRename(Activity act, final String srcFileName){
    	AlertDialog.Builder alert = new AlertDialog.Builder(this.context);

		alert.setTitle("Rename file to: ");
		alert.setMessage("Rename File");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this.context);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			  String dstFileName = input.getText().toString();
			  if(dstFileName == ""){
				  dstFileName = "New File.txt";
			  }
			  FileManager.renameCodeFile(srcFileName, dstFileName);
			  restartActivity();
		}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});
		
		alert.show();
    }
    
    private void restartActivity(){
    	Intent intent = activity.getIntent();
		activity.finish();
		activity.startActivity(intent);
    }
    
    private void dismissCustomDialog(){
    	this.dismiss();
    }
   

}