package edu.sru.andgate.bitbot.gametypes;

import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.graphics.DrawableBot;
import android.content.Context;

public class DungeonCrawl extends GameTypes{
	protected int[][][] spawnPoints;
	protected Context context;
	protected int totalBots;
	protected String mapFile;
	protected Bot[] bots;
	
	public DungeonCrawl(Context context, int numOfBots, String mapFile){
		this.context = context;
		this.totalBots = numOfBots;
		this.mapFile = mapFile;
		bots = new Bot[numOfBots];
	}
	
	
	@Override
	public void Initialize() {
		//Get Spawn Points
		spawnPoints = getSpawnPoints(mapFile, context);
		
		//create number of bots for game
		for(int i = 0; i < totalBots; i++){
			bots[i] = new Bot();
			//do some other stuff to set up bots have to talk to adam for how gun works etc.
		}
		
		/*
		 * select random spawn points from those that are available
		 *set bots starting position to it
		 *put the spawn point array in a temp array
		*/
				
	}

	@Override
	public void Update() {
		//check if victory conditions have been met, etc
		
	}

	@Override
	public void Finalize() {
		// Not sure here yet
		
	}

}
