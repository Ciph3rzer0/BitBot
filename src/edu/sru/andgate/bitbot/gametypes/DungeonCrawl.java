package edu.sru.andgate.bitbot.gametypes;

import java.util.Random;
import android.content.Context;
import android.util.Log;
import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.graphics.TileMap;

public class DungeonCrawl extends GameTypes{
	private Context context;
	private int totalBots;
	private String userBotFile;
	private Bot[] bots;
	private Bot userBot;
	private float defaultZ;
	private TileMap tileMap;
	Random generator;

	
	public DungeonCrawl(Context context, int numOfBots, TileMap tileMap, String mapFile, String userBotFile){
		this.totalBots = numOfBots;
		this.userBotFile = userBotFile;
		tileMap = new TileMap();
		tileMap.loadMapFile(mapFile, context);
		this.tileMap = tileMap;
		this.context = context;
		this.generator = new Random();
		defaultZ = -5.0f;
	}
	
	
	@Override
	public void Initialize() {
		Log.v("GameTypes", "GameType Accepted");
		int randomIndex;
		tileMap.setSpawnPoints();
		if(totalBots >= tileMap.enemySpawnPointsX.size())
			totalBots = tileMap.enemySpawnPointsX.size();
		
		bots = new Bot[totalBots];
			
		//create number of bots for game from enemy bot file
		for(int i = 0; i < totalBots; i++){
			bots[i] = Bot.CreateBotFromXML(context, "enemy_bot.xml");
			randomIndex = generator.nextInt(tileMap.enemySpawnPointsX.size());
			bots[i].getDrawableBot().setTranslation(tileMap.enemySpawnPointsX.get(randomIndex), tileMap.enemySpawnPointsY.get(randomIndex), defaultZ);  
			Log.v("GameTypes", "Bot Location[" + i + "] is: " + tileMap.enemySpawnPointsX.get(randomIndex) + "," + tileMap.enemySpawnPointsY.get(randomIndex) + "," + defaultZ);
			tileMap.enemySpawnPointsX.remove(randomIndex);
			tileMap.enemySpawnPointsY.remove(randomIndex);
		}
		Log.v("GameTypes", bots.length + " Bots Created");	
		
		/*
		 * create users bot
		 * set users bot spawn point
		 */
		userBot = Bot.CreateBotFromXML(context, userBotFile);
		randomIndex = generator.nextInt(tileMap.userSpawnPointsX.size());
		userBot.getDrawableBot().setTranslation(tileMap.userSpawnPointsX.get(randomIndex), tileMap.userSpawnPointsY.get(randomIndex), defaultZ);
		Log.v("GameTypes", "User Bot Location is: " + tileMap.userSpawnPointsX.get(randomIndex) + "," + tileMap.userSpawnPointsY.get(randomIndex) + "," + defaultZ);
		
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
