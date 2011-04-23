package edu.sru.andgate.bitbot.gametypes;

import android.content.Context;
import android.util.Log;
import edu.sru.andgate.bitbot.Bot;

public class DungeonCrawl extends GameTypes{
	private int[][][] spawnPoints;
	private Context context;
	private int totalBots;
	private String mapFile, userBotFile;
	private Bot[] bots;
	private Bot userBot;
	
	public DungeonCrawl(Context context, int numOfBots, String mapFile, String userBotFile){
		this.context = context;
		this.totalBots = numOfBots;
		this.mapFile = mapFile;
		this.userBotFile = userBotFile;
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
			//set the bots attributes here if not loading from file
		}
		Log.v("GameTypes", bots.length + " Bots Created");
		
		/*
		 * select random spawn points from those that are available
		 * set bots starting position to these points
		*/
	
		
		/*
		 * create users bot
		 * set users bot spawn point
		 */
		userBot = Bot.CreateBotFromXML(context, userBotFile);
		
		/*
		 * set victory conditions
		 */
			
		/*
		 * send all info to game engine to run
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
