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
import edu.sru.andgate.bitbot.graphics.BotLayer;
import edu.sru.andgate.bitbot.graphics.Drawable;
import edu.sru.andgate.bitbot.graphics.DrawableBot;
import edu.sru.andgate.bitbot.graphics.DrawableGun;
import edu.sru.andgate.bitbot.interpreter.BotInterpreter;
import edu.sru.andgate.bitbot.interpreter.SourceCode;
import edu.sru.andgate.bitbot.tools.Constants;

public class Bot
{
	private DrawableBot _drawable;
	private SourceCode _source;
	private BotInterpreter _interpreter;
	private String bot_name, bot_code;
	private int base_image, turret_image, bullet_image;
	private DrawableBot _bot;
	private BotLayer _layer;
	private DrawableGun _gun;
	private static Constants constants;
	
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
		constants = new Constants();
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
		stepSize /= 100;
		
		_drawable.setMove(degrees, stepSize);
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
	
	public void attachDrawable(DrawableBot d)
	{
		if (d != null)
		{
			_drawable = d;
			d.attachBot(this);
		}
		
	}
	
	
	public void callOnBoundaryCollision()
	{
		_interpreter.callOnBoundaryCollision();
	}
	
	public void callOnTouchEvent(float x, float y)
	{
		_interpreter.callOnTouchEvent(x, y);
	}
	
	
	
	public void setName(String name){
		this.bot_name = name;
	}
	
	public void setBase(int base){
		this.base_image = base;
	}
	
	public void setTurret(int turret){
		this.turret_image = turret;
	}
	
	public void setCode(String code){
		this.bot_code = code;
	}
	
	public void setBullet(int bullet){
		this.bullet_image = bullet;
	}
	
	public String getName(){
		return this.bot_name;
	}
	
	public int getBase(){
		return this.base_image;
	}
	
	public int getTurret(){
		return this.turret_image;
	}
	
	public int getBullet(){
		return this.bullet_image;
	}
	
	public void setBotLayer(BotLayer bl){
		this._layer = bl;
	}
	public BotLayer getBotLayer(){
		return this._layer;
	}
	
	public void setDrawableBot(DrawableBot db){
		this._bot = db;
	}
	public DrawableBot getDrawableBot(){
		return this._bot;
	}
	
	public void setDrawableGun(DrawableGun dg){
		this._gun = dg;
	}
	
	public DrawableGun getDrawableGun(){
		return this._gun;
	}
	
	public SourceCode getCode(){
		SourceCode sc = new SourceCode("Basic", this.bot_code);
		return sc;
	}
	
	/**
	 * Given an xml file this will take the data and make a Bot object out of it
	 * @param context a context to open the file
	 * @param xmlFile the file name
	 * @return
	 */
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
			b.setBase(Integer.parseInt(readXML(doc, "Bot-Base")));
			b.setTurret(Integer.parseInt(readXML(doc, "Bot-Turret")));
			b.setCode(readXML(doc, "Bot-Code"));
			b.setBullet(Integer.parseInt(readXML(doc, "Bot-Bullet")));
			
			DrawableBot db = new DrawableBot(b);
			db.setRotation(180.0f,0.0f,0.0f,-5.0f);
			b.setDrawableBot(db);
			db.addTexture(b.getBase());
			db.addTexture(constants.damage1.get(b.getBase()));	
			db.addTexture(constants.damage2.get(b.getBase()));
			db.addDamageTextureToSequence(1, 50);
		    db.addDamageTextureToSequence(2, 25);
			db.attachCollisionSound(context, R.raw.bot_wall_collision);
			BotLayer bl = new BotLayer(db);
			b.setBotLayer(bl);
			bl.addTexture(b.getTurret());
			DrawableGun dg = new DrawableGun(db, bl);
			b.setDrawableGun(dg);
			dg.addTexture(b.getBullet());
			b.attachDrawable(db);
			b.attachSourceCode(new SourceCode(b.getCode().getName(), b.getCode().getCode()+"\n"));
			b.readyInterpreter();
							
			return b;
		}catch (Exception e){
			Log.v("BitBot", "Error reading file");
		}
		return null;
		
		
	}

	public void saveBotToXML(Context context, String filename)
	{
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
			serializer.text(""+this.getBase());
			serializer.endTag("", "Bot-Base");
			serializer.startTag("", "Bot-Turret");
			serializer.text(""+this.getTurret());
			serializer.endTag("", "Bot-Turret");
			serializer.startTag("", "Bot-Bullet");
			serializer.text(""+this.getBullet());
			serializer.endTag("", "Bot-Bullet");
			serializer.startTag("", "Bot-Code");
			serializer.text(this.getCode().getCode());
			serializer.endTag("", "Bot-Code");
			serializer.endTag("", "Bot");
			serializer.endDocument();    
		} catch (Exception e) {
			Log.v("BitBot", "XML Writer error");
		}
		try {
			FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			Log.v("BitBot", "Success");
			fos.write(writer.toString().getBytes());
			fos.close();
		} catch(Exception e) {
			Log.v("BitBot", "Output Stream Error");
		}
	}
	
	public static String readXML(Document doc, String tag_name) throws IOException
	{
		NodeList tutorialText = doc.getElementsByTagName(tag_name);
		Element myText = (Element) tutorialText.item(0);
		String text = ((Node)myText.getChildNodes().item(0)).getNodeValue().trim();
		return text;
	}
}
