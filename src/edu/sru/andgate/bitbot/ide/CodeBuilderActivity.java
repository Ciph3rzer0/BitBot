package edu.sru.andgate.bitbot.ide;

import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.tools.FileManager;
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

public class CodeBuilderActivity extends ListActivity 
{ 
	private String[] code_files;
	private Intent engineIntent;
	private android.widget.ListView list;
	private Button new_program;
		
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.code_builder_main);
		   
		FileManager.setContext(getBaseContext());
		
		// Get file names in "Code" directory
		code_files = FileManager.getFileNamesInDir(getDir("Code",Context.MODE_PRIVATE).getPath());
		setListAdapter(new ArrayAdapter<String>(this, R.layout.tutorial_list, code_files));
		
		//Filter the text in the list
		list = getListView();
		list.setTextFilterEnabled(true);
		                
		list.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				engineIntent = new Intent(CodeBuilderActivity.this, IDE.class);
				engineIntent.putExtra("File", FileManager.readTextFileFromDirectory("Code",(String)((TextView) view).getText().toString()));
				startActivity(engineIntent);
			}
		});   
		   
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
}
