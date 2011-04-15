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
import edu.sru.andgate.bitbot.graphics.GameActivity;
import edu.sru.andgate.bitbot.ide.botbuilder.BotBuilderActivity;
import edu.sru.andgate.bitbot.ide.botbuilder.BotComponentView;
import edu.sru.andgate.bitbot.tools.ReadXML;
import edu.sru.andgate.bitbot.tutorial.Main_Tutorial;

public class MissionBriefingActivity extends Activity {
	private ReadXML read;
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.mission_briefing);
	        
	        read = new ReadXML(getBaseContext());
	       final String missionFile = getIntent().getExtras().getString("Filename");
	       final int missionIcon = getIntent().getExtras().getInt("Icon",0);
	       
	        TextView mission_text = (TextView) findViewById(R.id.mission_text);
	        TextView title_bar = (TextView) findViewById(R.id.title_bar);
	        
	        mission_text.setText(ReadXML.readXML(missionFile, "mission-text"));
			title_bar.setText("\t" + ReadXML.readXML(missionFile,"mission-name"));
			
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
			
			Button mission_btn = (Button) findViewById(R.id.take_mission);
			mission_btn.setText("Take Mission");
			mission_btn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					Intent engineIntent = new Intent(MissionBriefingActivity.this, BotBuilderActivity.class);
					startActivity(engineIntent);
				}
			});
			
	 }
}
