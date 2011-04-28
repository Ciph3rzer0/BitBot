package edu.sru.andgate.bitbot.gametypes;

import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.graphics.TileMap;

public abstract class GameTypes {
	
	public abstract void Initialize(); 

	public abstract void Update();
	
	public abstract void Finalize();
 
	public abstract TileMap getMap();
	
	public abstract Bot[] getBots();
	
	public abstract Bot getBot();
	
	public abstract boolean hasVictory();
}