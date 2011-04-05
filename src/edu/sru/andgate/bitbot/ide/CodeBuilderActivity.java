package edu.sru.andgate.bitbot.ide;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.sru.andgate.bitbot.MainMenu;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.missionlist.CustomListView;
import edu.sru.andgate.bitbot.missionlist.MissionBriefingActivity;
import edu.sru.andgate.bitbot.missionlist.MissionListActivity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CodeBuilderActivity extends ListActivity { 
	 private ArrayList<CustomListView> botCodeOptions = null;
	 private CodeListAdapter code_adapter;
	 private Hashtable<String, String> code_list = new Hashtable<String,String>();
	 
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.code_builder_main);
	        botCodeOptions = new ArrayList<CustomListView>();
	        this.code_adapter = new CodeListAdapter(this, R.layout.code_row, botCodeOptions);
	        setListAdapter(this.code_adapter);
	        
	        getMissions();
	        run();
	     
	        TextView new_program = (TextView)findViewById(R.id.create_program);
	        new_program.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					createNewProgram();
				}
	        });
	        
	 }
	  
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		 Intent engineIntent = new Intent(CodeBuilderActivity.this, IDE.class);
		 engineIntent.putExtra("File", code_list.get(v.getTag().toString()));
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
	              setAttributes(botCodeOptions, codeOption1, readXML("helloworld.xml", "program-name") , readXML("helloworld.xml", "program-description"), "helloworld.xml");
	            } catch (Exception e) { 
	            	Log.e("BACKGROUND_PROC", e.getMessage());
	            }
	 }
		 
	 private void setAttributes(ArrayList<CustomListView> array, CustomListView code_option, String code_name, String description, String filename){
		 //set mission attributes
		 code_option.setMissionName(code_name);
         code_option.setMissionDescription(description);        
         
         //add to file lookup table
         code_list.put(code_name, filename);
         
         array.add(code_option);
	 }
	 
	 /*
	 * Method that recieves an xml file name, and target <tag> 
	 * 	returns the text in the specified <tag></tag>
	 */
	public String readXML(String my_file, String tag_name) throws IOException{
	 		InputStream is = getAssets().open(my_file);
			
	 		try {
	       		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder;
				docBuilder = docBuilderFactory.newDocumentBuilder();
				
				Document doc = docBuilder.parse(is);
	            doc.getDocumentElement ().normalize ();
	            
	            NodeList tutorialText = doc.getElementsByTagName(tag_name);
	            Element myText = (Element) tutorialText.item(0);
	           	            
	            return ((Node)myText.getChildNodes().item(0)).getNodeValue().trim();
	            
	 		} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			
			
		    return null;
		}//end of readXML()
	
	private void createNewProgram(){
		final EditText input1 = new EditText(getBaseContext());
		final EditText input2 = new EditText(getBaseContext());
		new AlertDialog.Builder(CodeBuilderActivity.this)
	    .setTitle("New Program")
	    .setMessage("Program Description")
	    .setView(input2)
	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            String value2 = input1.getText().toString();
	        }
	    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            // Do nothing.
	        }
	    }).show();
		new AlertDialog.Builder(CodeBuilderActivity.this)
	    .setTitle("New Program")
	    .setMessage("Program Name")
	    .setView(input1)
	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	String value1 = input1.getText().toString();	            
	        }
	    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            // Do nothing.
	        }
	    }).show();
	}
}
