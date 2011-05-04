package edu.sru.andgate.bitbot.ide;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.customdialogs.CustomDialogListView;
import edu.sru.andgate.bitbot.tools.FileManager;

public class CodeBuilderActivity extends ListActivity 
{ 
	//Declare Components
	private ArrayList<CustomListView> botCodeOptions;
	private CodeListAdapter code_adapter;
	private String[] code_files;
	private Intent engineIntent;
	private Button new_program;
	CustomDialogListView dlg;
	 
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.code_builder_main);
       
        FileManager.setContext(getBaseContext()); //set context to use FileManager
		
        //initialize & set the custom list adapter
        botCodeOptions = new ArrayList<CustomListView>();
        this.code_adapter = new CodeListAdapter(this, R.layout.code_row, botCodeOptions);
        setListAdapter(this.code_adapter);
        
        //Get file names in "Code" directory
		code_files = FileManager.getFileNamesInDir(getDir("Code",Context.MODE_PRIVATE).getPath());
		
		//create the code options for the list
		CustomListView codeOptions[] = new CustomListView[code_files.length];
		for(int i = 0; i < code_files.length; i++){
			codeOptions[i] = new CustomListView(code_files[i].toString(), FileManager.getFileDescriptionFromFile("Code",code_files[i].toString()).substring(2));
			code_adapter.add(codeOptions[i]);
		}
		     
		//create a new program if clicked
		new_program = (Button) findViewById(R.id.create_program);
		new_program.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				try {
					engineIntent = new Intent(CodeBuilderActivity.this, IDE.class);
					engineIntent.putExtra("File", "New Program.txt");
					engineIntent.putExtra("Data", FileManager.readAssetsXML("code_template.xml", "program-code"));
					startActivity(engineIntent);
					FileManager.saveCodeFile(FileManager.readAssetsXML("code_template.xml", "program-code"), "New Program.txt");
					finish();
				} catch (Exception e) {
						
				}
			}
		});
		
		//on long click, show file manipulation options: Rename, save, save as, etc...
		ListView list = getListView();
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Log.v("BitBot", "Long Click Accepted");
				dlg = new CustomDialogListView(CodeBuilderActivity.this, view.getTag().toString(), R.style.CustomDialogTheme);
		        dlg.show();
		        return true;
			}
			
		});
	}
	 
	 /*
	  * Get appropriate data from selected list item
	  * send info to IDE activity
	  */
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		 engineIntent = new Intent(CodeBuilderActivity.this, IDE.class);
		 engineIntent.putExtra("File", v.getTag().toString());
		 engineIntent.putExtra("Data", FileManager.readTextFileFromDirectory("Code",v.getTag().toString()));
		 startActivity(engineIntent);
		 finish();
		
	 }
	 
	 //recieve the code files in the list
	 public String[] getCodeFiles(){
		 return this.code_files;
	 }
}