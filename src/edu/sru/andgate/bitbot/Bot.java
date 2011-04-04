package edu.sru.andgate.bitbot;

import edu.sru.andgate.bitbot.graphics.Drawable;

public class Bot
{
	private Drawable drawable;
//	private Physical physical;
//	private VirtalMachine vm;
//	private Controller controller;
	
	//Body
	//Gun
	//Radar
	
	
	public Bot()
	{
		
	}
	
	public boolean Move()
	{
	
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
		return 0;
	}
	
	public double GetY()
	{
		return 0;
	}
	
	public double GetHeading()
	{
		return 0;
	}
	
	public void attachDrawable(Drawable d)
	{
		if (d != null)
		{
			drawable = d;
			d.attachBot(this);
		}
		
	}
	
}
