package edu.sru.andgate.bitbot.gametypes;

import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.graphics.GameActivity;
import edu.sru.andgate.bitbot.graphics.NickGameActivity;
import edu.sru.andgate.bitbot.graphics.TileMap;

public abstract class GameTypes {
	
	public abstract void Initialize(NickGameActivity ga); 

	public abstract void Update();
	
	public abstract void Finalize(final String type);
 
	public abstract TileMap getMap();
	
	public abstract Bot[] getBots();
	
	public abstract Bot getBot();

}