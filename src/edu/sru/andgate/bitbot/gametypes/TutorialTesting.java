package edu.sru.andgate.bitbot.gametypes;

import java.util.Random;

import android.content.Context;
import android.util.Log;
import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.graphics.NickGameActivity;
import edu.sru.andgate.bitbot.graphics.TileMap;

public class TutorialTesting extends GameTypes
{
	private Context context;
	private int totalBots;
	private String userBotFile;
	private Bot[] bots;
	private Bot userBot;
	private float defaultZ;
	private TileMap tileMap;
	Random generator;
	public NickGameActivity _game;
	
	public TutorialTesting(Context context, int numOfBots, String userBotFile)
	{
		this.tileMap = new TileMap();
		this.context = context;
		this.userBotFile = userBotFile;
		this.tileMap.loadMapFile("tutorial_arena.map", context);
		this.tileMap.setSpawnPoints();
		this.totalBots = numOfBots;
		this.generator = new Random();
		defaultZ = -5.0f;
	}
	
	
	@Override
	public void Initialize(NickGameActivity ga)
	{
		this._game = ga;
		Log.v("GameTypes", "GameType Accepted");
		int randomIndex;
		
		if(totalBots >= tileMap.enemySpawnPointsX.size())
			totalBots = tileMap.enemySpawnPointsX.size();
		
		bots = new Bot[totalBots];
			
		//create number of bots for game from enemy bot file
		for(int i = 0; i < totalBots; i++){
			bots[i] = Bot.CreateBotFromXML(context, "enemy_bot.xml");
			randomIndex = generator.nextInt(tileMap.enemySpawnPointsX.size());
			bots[i].getDrawableBot().setTranslation(tileMap.enemySpawnPointsX.get(randomIndex), tileMap.enemySpawnPointsY.get(randomIndex), defaultZ);  
			tileMap.enemySpawnPointsX.remove(randomIndex);
			tileMap.enemySpawnPointsY.remove(randomIndex);
		}
		
		/*
		 * create users bot
		 * set users bot spawn point
		 */
		userBot = Bot.CreateBotFromXML(context, userBotFile);
		randomIndex = generator.nextInt(tileMap.userSpawnPointsX.size());
		userBot.getDrawableBot().setTranslation(tileMap.userSpawnPointsX.get(randomIndex), tileMap.userSpawnPointsY.get(randomIndex), defaultZ);
		
	}

	@Override
	public void Update() {
		//if instructions run out, stop?
	}
	

	@Override
	public void Finalize(final String type) {
		
		
	}

	@Override
	public TileMap getMap(){
		return this.tileMap;
	}
	
	@Override
	public Bot[] getBots(){
		return this.bots;
	}
	
	@Override
	public Bot getBot(){
		return this.userBot;
	}
}
