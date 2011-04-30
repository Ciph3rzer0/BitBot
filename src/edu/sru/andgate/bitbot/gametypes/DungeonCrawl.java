package edu.sru.andgate.bitbot.gametypes;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.customdialogs.DefeatDialog;
import edu.sru.andgate.bitbot.customdialogs.VictoryDialog;
import edu.sru.andgate.bitbot.graphics.GameActivity;
import edu.sru.andgate.bitbot.graphics.NickGameActivity;
import edu.sru.andgate.bitbot.graphics.TileMap;
import edu.sru.andgate.bitbot.tools.Constants;

public class DungeonCrawl extends GameTypes
{
	private Context context;
	private int totalBots;
	private String userBotFile;
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
	
	public DungeonCrawl(Context context, String mapFile, String userBotFile)
	{
		this.userBotFile = userBotFile;
		this.tileMap = new TileMap();
		this.tileMap.loadMapFile(mapFile, context);
		this.tileMap.setSpawnPoints();
		this.context = context;
		this.generator = new Random();
		victory = false;
		defeat = false;
		defaultZ = -5.0f;
	}
	
	
	@Override
	public void Initialize(NickGameActivity ga)
	{
		this._game = ga;
		Log.v("GameTypes", "GameType Accepted");
		int randomIndex;
		this.totalBots = generator.nextInt(tileMap.enemySpawnPointsX.size()-1) + 1;
		
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
		start = System.currentTimeMillis();

	}

	@Override
	public void Update() {
		int botsLeft = bots.length;
		//check if victory conditions have been met, etc
		for(int i = 0; i < bots.length;i++){
			if(!bots[i].getDrawableBot().isAlive()){
				botsLeft--;
			}
		}
		
		if(botsLeft == 0 && victory == false){
			victory = true;
			Constants.finished_missions.add(_game.missionType);
			elapsed = System.currentTimeMillis() - start;
			Finalize(victory);
		}else if (!userBot.getDrawableBot().isAlive()){
			elapsed = System.currentTimeMillis() - start;
			Finalize(defeat);
		}
	}
	

	@Override
	public void Finalize(final boolean type) {
		if(type == victory){
			for(int i = 0; i < _game.getGameType().getBots().length; i++){
		       	if(_game.getGameType().getBots()[i].getDrawableBot().isAlive()){
		       		//do nothing
		       	}else{
		       		_game.kills++;
		       		_game.numBulletsContact += _game.getGameType().getBots()[i].getDrawableBot().getNumBulletsHit();
		       	}
			}
			accuracy = ((double)_game.numBulletsContact/(double)_game.numShotsFired) * 100;
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
				if(type == victory){
					vd = new VictoryDialog(_game,_game, R.style.CustomDialogTheme, _game.kills, accuracy, elapsed);	
					vd.show();	
				}else if (type == defeat){
					dd = new DefeatDialog(_game, _game, R.style.CustomDialogTheme);
					dd.show();
				}
			}
		});	
		
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
