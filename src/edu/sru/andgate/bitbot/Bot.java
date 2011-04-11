package edu.sru.andgate.bitbot;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import edu.sru.andgate.bitbot.graphics.Drawable;
import edu.sru.andgate.bitbot.interpreter.BotInterpreter;
import edu.sru.andgate.bitbot.interpreter.SourceCode;

public class Bot
{
	private Drawable _drawable;
	private SourceCode _source;
	private BotInterpreter _interpreter;
	private String bot_name;
	private String base_name;
	private String turret_name;
	private String bot_code;
	
//	private Physical physical;
//	private VirtalMachine vm;
//	private Controller controller;
	
	//Body
	//Gun
	//Radar
	
	private float _x = 0;
	private float _y = 0;
	
	
	public Bot()
	{
	
	}
	
	public void readyInterpreter()
	{
		_interpreter = new BotInterpreter(this, _source.getCode());
	}
	
	public void attachSourceCode(SourceCode source)
	{
		_source = source;
	}
	
	public BotInterpreter getInterpreter()
	{
		return _interpreter;
	}
	
	public boolean Move(float degrees, float stepSize)
	{
//		_x += x;
//		_y += y;
//		_drawable.setTranslation(x, y, -10);
		
		// Convert degrees to radians
//		degrees = (float)(degrees*(180/Math.PI));
		
		// Scale stepSize down since we only have integers to work with
		stepSize /= 100;
		
		_drawable.move(degrees, stepSize);
		return true;
	}
	
	public boolean MoveForward()
	{
	
		return true;
	}
	
	public boolean MoveBack()
	{
	
		return true;
	}
	
	public boolean TurnGun()
	{
		
		return true;
	}
	
	public boolean TurnGunTo()
	{
		
		return true;
	}
	
	public boolean TurnGunRight()
	{
		
		return true;
	}
	
	public boolean TurnGunLeft()
	{
		
		return true;
	}
	
	public boolean Fire()
	{
		return true;
	}
	
	public double GetX()
	{
		return _x;
	}
	
	public double GetY()
	{
		return _y;
	}
	
	public double GetHeading()
	{
		return 0;
	}
	
	public void attachDrawable(Drawable d)
	{
		if (d != null)
		{
			_drawable = d;
			d.attachBot(this);
		}
		
	}
	
	public void setName(String name){
		this.bot_name = name;
	}
	
	public void setBase(String base){
		this.base_name = base;
	}
	
	public void setTurret(String turret){
		this.turret_name = turret;
	}
	
	public void setCode(String code){
		_source = new SourceCode("Basic", code);
		this.attachSourceCode(_source);
		this.bot_code = code;
	}
	
	public String getName(){
		return this.bot_name;
	}
	
	public String getBase(){
		return this.base_name;
	}
	
	public String getTurret(){
		return this.turret_name;
	}
	
	public SourceCode getCode(){
		SourceCode sc = new SourceCode("Basic", this.bot_code);
		return sc;
	}
	
	  public static Bot CreateBotFromXML(Context context, String xmlFile) {
			//read in bot from xmlFile
			try{
				FileInputStream is = context.openFileInput(xmlFile);
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder docBuilder;
				docBuilder = docBuilderFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(is);
		        doc.getDocumentElement ().normalize ();
		        
				Bot b = new Bot();
				b.setName(readXML(doc, "Bot-Name"));
				Log.v("BitBot", b.getName());
				b.setBase(readXML(doc, "Bot-Base"));
				Log.v("BitBot", b.getBase());
				b.setTurret(readXML(doc, "Bot-Turret"));
				Log.v("BitBot", b.getTurret());
				b.setCode(readXML(doc, "Bot-Code"));
				Log.v("BitBot", b.getCode().getCode());
				
				return b;
			}catch (Exception e){
				Log.v("BitBot", "Error reading file");
				//Log.v("BitBot", e.getStackTrace().toString());
			}
			return null;
			
			
		}
		
		 public void saveBotToXML(Context context, String filename){
	 	    XmlSerializer serializer = Xml.newSerializer();
	 	    StringWriter writer = new StringWriter();
	 	    try {
	 	        serializer.setOutput(writer);
	 	        serializer.startDocument("UTF-8", true);
	 	        serializer.startTag("", "Bot");
	 	           	serializer.startTag("", "Bot-Name");
	 	        	serializer.text(this.getName());
	 	        	serializer.endTag("", "Bot-Name");
	 	        	serializer.startTag("", "Bot-Base");
	 	        	serializer.text(this.getBase());
	 	        	serializer.endTag("", "Bot-Base");
	 	        	serializer.startTag("", "Bot-Turret");
	 	        	serializer.text(this.getTurret());
	 	        	serializer.endTag("", "Bot-Turret");
	 	        	serializer.startTag("", "Bot-Code");
	 	        	serializer.text(this.getCode().getCode());
	 	        	serializer.endTag("", "Bot-Code");
	 	        serializer.endTag("", "Bot");
	 	        serializer.endDocument();    
	 	    } catch (Exception e) {
	 	    		Log.v("BitBot", "XML Writer error");
	 	        	//Log.v("BitBot", e.getStackTrace().toString());
	 	    } 
	 	    try{
	 	          FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
	 	          Log.v("BitBot", "Success");
	 	          fos.write(writer.toString().getBytes());
	 	          fos.close();
	          }catch(Exception e){
	        	  Log.v("BitBot", "Output Stream Error");
	        	  //Log.v("BitBot", e.getStackTrace().toString());
	          }
	 	}
		  
			public static String readXML(Document doc, String tag_name) throws IOException{
					NodeList tutorialText = doc.getElementsByTagName(tag_name);
		            Element myText = (Element) tutorialText.item(0);
		            String text = ((Node)myText.getChildNodes().item(0)).getNodeValue().trim();
					 return text;
			}
}
