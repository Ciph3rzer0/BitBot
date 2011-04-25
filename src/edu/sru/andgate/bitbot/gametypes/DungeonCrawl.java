package edu.sru.andgate.bitbot.gametypes;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.util.Log;
import edu.sru.andgate.bitbot.Bot;

public class DungeonCrawl extends GameTypes{
	private Context context;
	private int totalBots;
	private String userBotFile;
	private Bot[] bots;
	private Bot userBot;
	private float defaultZ;
	Random generator;

	
	public DungeonCrawl(Context context, int numOfBots, String mapFile, String userBotFile){
		this.context = context;
		this.totalBots = numOfBots;
		this.userBotFile = userBotFile;
		setSpawnPoints(mapFile, context);
		this.generator = new Random();
		defaultZ = 0.0f;
	}
	
	
	@Override
	public void Initialize() {
		Log.v("GameTypes", "GameType Accepted");
		int randomIndex;
		
		if(totalBots >= enemySpawnPointsX.size())
			totalBots = enemySpawnPointsX.size();
		
		bots = new Bot[totalBots];
			
		//create number of bots for game from enemy bot file
		for(int i = 0; i < totalBots; i++){
			bots[i] = Bot.CreateBotFromXML(context, "enemy_bot.xml");
			randomIndex = generator.nextInt(enemySpawnPointsX.size());
			bots[i].getDrawableBot().setTranslation((float)enemySpawnPointsX.get(randomIndex), (float)enemySpawnPointsY.get(randomIndex), defaultZ);  
			Log.v("GameTypes", "Bot Location[" + i + "] is: " + (float)enemySpawnPointsX.get(randomIndex) + "," + (float)enemySpawnPointsY.get(randomIndex) + "," + defaultZ);
			enemySpawnPointsX.remove(randomIndex);
			enemySpawnPointsY.remove(randomIndex);
		}
		Log.v("GameTypes", bots.length + " Bots Created");	
		
		/*
		 * create users bot
		 * set users bot spawn point
		 */
		userBot = Bot.CreateBotFromXML(context, userBotFile);
		randomIndex = generator.nextInt(userSpawnPointsX.size());
		userBot.getDrawableBot().setTranslation((float)userSpawnPointsX.get(randomIndex), (float)userSpawnPointsY.get(randomIndex), defaultZ);
		Log.v("GameTypes", "User Bot Location is: " + (float)userSpawnPointsX.get(randomIndex) + "," + (float)userSpawnPointsY.get(randomIndex) + "," + defaultZ);
		
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
