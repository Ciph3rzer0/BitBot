package edu.sru.andgate.bitbot.gametypes;

import android.content.Context;
import android.util.Log;
import edu.sru.andgate.bitbot.Bot;

public class DungeonCrawl extends GameTypes{
	private int[][][] spawnPoints;
	private Context context;
	private int totalBots;
	private String mapFile;
	private Bot[] bots;
	
	public DungeonCrawl(Context context, int numOfBots, String mapFile){
		this.context = context;
		this.totalBots = numOfBots;
		this.mapFile = mapFile;
		bots = new Bot[numOfBots];
		setSpawnPoints(mapFile, context);
		this.spawnPoints = getSpawnPoints();
	}
	
	
	@Override
	public void Initialize() {
		Log.v("GameTypes", "GameType Accepted");
		//create number of bots for game from enemy bot file
		for(int i = 0; i < totalBots; i++){
			bots[i] = Bot.CreateBotFromXML(context, "enemy_bot.xml");
		}
		Log.v("GameTypes", bots.length + " Bots Created");
		
		/*
		 * select random spawn points from those that are available
		 *set bots starting position to it
		 *
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
