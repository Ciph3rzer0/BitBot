package edu.sru.andgate.bitbot.missionlist;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.sru.andgate.bitbot.MainMenu;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.custombutton.CustomButtonView;
import edu.sru.andgate.bitbot.graphics.GameActivity;
import edu.sru.andgate.bitbot.ide.botbuilder.BotBuilderActivity;
import edu.sru.andgate.bitbot.ide.botbuilder.BotComponentView;
import edu.sru.andgate.bitbot.tutorial.Main_Tutorial;

public class MissionBriefingActivity extends Activity {
	 
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.mission_briefing);
	        
	       final String missionFile = getIntent().getExtras().getString("Filename");
	       final int missionIcon = getIntent().getExtras().getInt("Icon",0);
	       
	        TextView mission_text = (TextView) findViewById(R.id.mission_text);
	        TextView title_bar = (TextView) findViewById(R.id.title_bar);
	        try {
				mission_text.setText(readXML(missionFile, "mission-text"));
				title_bar.setText("\t" + readXML(missionFile,"mission-name"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ImageView mission_icon = (ImageView) findViewById(R.id.mission_icon);
			mission_icon.setImageResource(missionIcon);		
			
			Button back_btn = (Button) findViewById(R.id.back_btn);
			back_btn.setText("Back");
			back_btn.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View v) 
				{
					finish();
				}
			});
			
			CustomButtonView mission_btn = (CustomButtonView) findViewById(R.id.take_mission);
			mission_btn.setTitle("Take Mission");
			mission_btn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					Intent engineIntent = new Intent(MissionBriefingActivity.this, BotBuilderActivity.class);
					startActivity(engineIntent);
				}
			});
			
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
		}//end of main
}
