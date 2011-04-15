package edu.sru.andgate.bitbot.ide;

import java.io.File;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.tools.ReadDirectory;
import edu.sru.andgate.bitbot.tools.ReadText;
import edu.sru.andgate.bitbot.tools.ReadXML;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CodeBuilderActivity extends ListActivity { 
		private String[] code_files;
		private Intent engineIntent;
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.code_builder_main);
	       
	        ReadXML.setContext(getBaseContext());
	        new ReadText(getBaseContext());
	        
	        code_files = ReadDirectory.getFiles(getDir("Code",Context.MODE_PRIVATE).getPath());
	        setListAdapter(new ArrayAdapter<String>(this, R.layout.tutorial_list, code_files));
            //Filter the text in the list
            final android.widget.ListView list = getListView();
            list.setTextFilterEnabled(true);
                        
           list.setOnItemClickListener(new OnItemClickListener() 
            {
            	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
            	{
            		if(position == 0)
            		{
            			//Do Nothing
            		}else{
	            		engineIntent = new Intent(CodeBuilderActivity.this, IDE.class);
	            		engineIntent.putExtra("File", ReadText.readTextFileFromDirectory("Code",(String)((TextView) view).getText().toString()));
	                   	startActivity(engineIntent);
            		}
            	}
            });   
           
           Button new_program = (Button) findViewById(R.id.create_program);
           new_program.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					engineIntent = new Intent(CodeBuilderActivity.this, IDE.class);
					engineIntent.putExtra("File", ReadXML.readXML("code_template.xml", "program-code"));
					startActivity(engineIntent);
				} catch (Exception e) {
					
				}
				
			}
		});
	 }
}
