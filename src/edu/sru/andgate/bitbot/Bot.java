package edu.sru.andgate.bitbot;

import android.util.Log;
import edu.sru.andgate.bitbot.graphics.Drawable;
import edu.sru.andgate.bitbot.interpreter.BotInterpreter;
import edu.sru.andgate.bitbot.interpreter.SourceCode;

public class Bot
{
	private Drawable _drawable;
	private SourceCode _source;
	private BotInterpreter _interpreter;
	
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
	
}
