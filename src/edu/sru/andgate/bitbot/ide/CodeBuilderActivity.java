package edu.sru.andgate.bitbot.ide;

import java.util.ArrayList;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.tools.FileManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class CodeBuilderActivity extends ListActivity 
{ 
	 private ArrayList<CustomListView> botCodeOptions;
	 private CodeListAdapter code_adapter;
	 private String[] code_files;
	 private Intent engineIntent;
	 private Button new_program;
	 
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.code_builder_main);
	       
	        FileManager.setContext(getBaseContext());
			
	        botCodeOptions = new ArrayList<CustomListView>();
	        this.code_adapter = new CodeListAdapter(this, R.layout.code_row, botCodeOptions);
	        setListAdapter(this.code_adapter);
	        
	        // Get file names in "Code" directory
  			code_files = FileManager.getFileNamesInDir(getDir("Code",Context.MODE_PRIVATE).getPath());
  			
  			CustomListView codeOptions[] = new CustomListView[code_files.length];
  			
  			for(int i = 0; i < code_files.length; i++){
  				codeOptions[i] = new CustomListView(code_files[i].toString(), FileManager.getFileDescriptionFromFile("Code",code_files[i].toString()).substring(2));
  				code_adapter.add(codeOptions[i]);
   			}
  			     	   			   
			new_program = (Button) findViewById(R.id.create_program);
			new_program.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View v) {
					try {
						engineIntent = new Intent(CodeBuilderActivity.this, IDE.class);
						engineIntent.putExtra("File", FileManager.readXML("code_template.xml", "program-code"));
						startActivity(engineIntent);
					} catch (Exception e) {
							
					}
				
				}
			});
		}
	  
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		 engineIntent = new Intent(CodeBuilderActivity.this, IDE.class);
		 engineIntent.putExtra("File", FileManager.readTextFileFromDirectory("Code",v.getTag().toString()));
		 startActivity(engineIntent);
	 }
}