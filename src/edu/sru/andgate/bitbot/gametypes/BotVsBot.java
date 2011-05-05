package edu.sru.andgate.bitbot.gametypes;

import java.util.Random;

import android.content.Context;
import android.util.Log;
import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.customdialogs.DefeatDialog;
import edu.sru.andgate.bitbot.customdialogs.VictoryDialog;
import edu.sru.andgate.bitbot.graphics.NickGameActivity;
import edu.sru.andgate.bitbot.graphics.TileMap;
import edu.sru.andgate.bitbot.tools.Constants;

public class BotVsBot extends GameTypes
{
	private Context context;
	private int totalBots, kills = 0, numBulletsContact = 0;
	private String userBotFile, enemyBotFile, mapFile;
	private Bot[] bots;
	private VictoryDialog vd;
	private DefeatDialog dd;
	private long start, elapsed;
	private Bot userBot;
	private float defaultZ;
	private double accuracy;
	private TileMap tileMap;
	private boolean victory, defeat;
	Random generator;
	public NickGameActivity _game;
	
	public BotVsBot(Context context, String mapFile, String userBotFile, String enemyBotFile)
	{
		this.tileMap = new TileMap();
		this.context = context;
		this.mapFile = mapFile;
		this.userBotFile = userBotFile;
		this.enemyBotFile = enemyBotFile;
		this.tileMap.loadMapFile(mapFile, context);
		this.tileMap.setSpawnPoints();
		this.generator = new Random();
		victory = false;
		defeat = false;
		defaultZ = -5.0f;
	}
	
	
	@Override
	public void Initialize(NickGameActivity ga)
	{
		this._game = ga;
		Log.v("GameTypes", "Bot-Vs-Bot Accepted");
		int randomIndex;
		this.totalBots = 1;
		
		bots = new Bot[totalBots];
			
		//create number of bots for game from enemy bot file
		bots[0] = Bot.CreateBotFromXML(context, enemyBotFile);
		bots[0].setID(0);
		randomIndex = generator.nextInt(tileMap.enemySpawnPointsX.size());
		bots[0].getDrawableBot().setTranslation(tileMap.enemySpawnPointsX.get(randomIndex), tileMap.enemySpawnPointsY.get(randomIndex), defaultZ);  
		
		/*
		 * create users bot
		 * set users bot spawn point
		 */
		userBot = Bot.CreateBotFromXML(context, userBotFile);
		randomIndex = generator.nextInt(tileMap.userSpawnPointsX.size());
		userBot.getDrawableBot().setTranslation(tileMap.userSpawnPointsX.get(randomIndex), tileMap.userSpawnPointsY.get(randomIndex), defaultZ);
		start = System.currentTimeMillis();
		
		ga.setEnemyBotsUseInterpreter(true);
	}

	@Override
	public void Update()
	{
		int botsLeft = bots.length;
		//check if victory conditions have been met, etc
		for(int i = 0; i < bots.length;i++){
			if(!bots[i].getDrawableBot().isAlive()){
				botsLeft--;
			}
		}
		
		if(botsLeft == 0 && victory == false && defeat == false){
			victory = true;
			Constants.finished_missions.add(_game.missionType);
			elapsed = System.currentTimeMillis() - start;
			Finalize("victory");
		}
		if (!userBot.getDrawableBot().isAlive() && defeat == false && victory == false){
			defeat = true;
			elapsed = System.currentTimeMillis() - start;
			Finalize("defeat");
		}
	}
	

	@Override
	public void Finalize(final String type)
	{
		if(type.equals("victory")){
			for(int i = 0; i < _game.getGameType().getBots().length; i++){
		       	if(_game.getGameType().getBots()[i].getDrawableBot().isAlive()){
		       		//do nothing
		       	}else{
		       		kills++;
		       		numBulletsContact += _game.getGameType().getBots()[i].getDrawableBot().getNumBulletsHit();
		       	}
			}
			accuracy = ((double)numBulletsContact/(double)_game.getGameType().getBot().getDrawableGun().numShotsFired) * 100;
			accuracy = (double)Math.round(accuracy * 100) / 100;
		}
		// Not sure here yet
		Log.v("GameTypes", "Victory is Yours");
		_game.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(type.equals("victory")){
					vd = new VictoryDialog(_game,_game, R.style.CustomDialogTheme, kills, accuracy, elapsed);	
					vd.show();	
				}else if (type.equals("defeat")){
					dd = new DefeatDialog(_game, _game, R.style.CustomDialogTheme);
					dd.show();
				}
			}
		});	
		
	}

	@Override
	public TileMap getMap()
	{
		return this.tileMap;
	}
	
	@Override
	public Bot[] getBots()
	{
		return this.bots;
	}
	
	@Override
	public Bot getBot()
	{
		return this.userBot;
	}
}
