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
	//Declare the components and attributes needed for specified gametype
	private Context context;
	private String userBotFile, enemyBotFile, mapFile;
	private Bot[] bots;
	private VictoryDialog vd;
	private DefeatDialog dd;
	private Bot userBot;
	private TileMap tileMap;
	public NickGameActivity _game;
	
	//statistical vars for users BOT
	Random generator;
	private int totalBots, kills = 0, numBulletsContact = 0;
	private long start, elapsed;
	private float defaultZ;
	private double accuracy;
	private boolean victory, defeat;
	
	public BotVsBot(Context context, String mapFile, String userBotFile, String enemyBotFile)
	{
		//initialize the specified vars
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
		this._game = ga; 	//set the game activity
		int randomIndex; 	//random index iterator
		this.totalBots = 1; //total enemy bots allowed
		
		//set the size of the bot array to total bots allowed for mission
		bots = new Bot[totalBots]; 
			
		//create number of bots for game from enemy bot file (1)
		bots[0] = Bot.CreateBotFromXML(context, enemyBotFile);
		bots[0].setID(0);
		randomIndex = generator.nextInt(tileMap.enemySpawnPointsX.size());
		bots[0].getDrawableBot().setTranslation(tileMap.enemySpawnPointsX.get(randomIndex), tileMap.enemySpawnPointsY.get(randomIndex), defaultZ);  
//		ga.getILVM().addInterpreter(bots[0].getInterpreter());
		
		/*
		 * create users bot
		 * set users bot spawn point
		 */
		userBot = Bot.CreateBotFromXML(context, userBotFile);
		randomIndex = generator.nextInt(tileMap.userSpawnPointsX.size());
		userBot.getDrawableBot().setTranslation(tileMap.userSpawnPointsX.get(randomIndex), tileMap.userSpawnPointsY.get(randomIndex), defaultZ);
		
		start = System.currentTimeMillis(); //start the stopwatch
		
		ga.setEnemyBotsUseInterpreter(true);
	}

	@Override
	public void Update() {
		//check if victory conditions have been met, etc

		int botsLeft = bots.length; //kill counter
		
		for(int i = 0; i < bots.length;i++){
			if(!bots[i].getDrawableBot().isAlive()){
				botsLeft--;
			}
		}
		
		//if all bots are killed, victory
		if(botsLeft == 0 && victory == false && defeat == false){
			victory = true;
			//add to finished missions table
			Constants.finished_missions.add(_game.missionType);
			elapsed = System.currentTimeMillis() - start; //click the stopwatch
			Finalize("victory");
		}
		//if users Bot is killed, defeat
		if (!userBot.getDrawableBot().isAlive() && defeat == false && victory == false){
			defeat = true;
			elapsed = System.currentTimeMillis() - start; //click stopwatch
			Finalize("defeat");
		}
	}
	

	@Override
	public void Finalize(final String type) {
		//finalize things and show cooresponding dialog
		
		//if victory, calculate needed statistics
		if(type.equals("victory")){
			for(int i = 0; i < _game.getGameType().getBots().length; i++){
		       	if(_game.getGameType().getBots()[i].getDrawableBot().isAlive()){
		       		//do nothing
		       	}else{
		       		//increment kill counter
		       		kills++;
		       		//increment bullets in contact from number of bullets that hit specified enemy bot
		       		numBulletsContact += _game.getGameType().getBots()[i].getDrawableBot().getNumBulletsHit();
		       	}
			}
			
			//calculate users bots accuracy
			accuracy = ((double)numBulletsContact/(double)_game.getGameType().getBot().getDrawableGun().numShotsFired) * 100;
			accuracy = (double)Math.round(accuracy * 100) / 100;
		}
		
		//create a new Runnable for showing the victory/defeat dialogs
		_game.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//show dialog corresponding to a win, or loss
				if(type.equals("victory")){
					/*
					 * set statistics for bot here
					 */
					vd = new VictoryDialog(_game, R.style.CustomDialogTheme, kills, accuracy, elapsed);	
					vd.show();	
				}else if (type.equals("defeat")){
					/*
					 * set statistics for bot here
					 */
					dd = new DefeatDialog(_game, R.style.CustomDialogTheme);
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
