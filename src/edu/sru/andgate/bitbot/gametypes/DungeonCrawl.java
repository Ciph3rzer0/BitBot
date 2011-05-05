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

public class DungeonCrawl extends GameTypes
{
	//Declare the components and attributes needed for specified gametype
	private Context context;
	private String userBotFile;
	private Bot[] bots;
	private VictoryDialog vd;
	private DefeatDialog dd;
	private TileMap tileMap;
	private Bot userBot;
	public NickGameActivity _game;

	//statistical vars for users BOT
	private int totalBots, kills = 0, numBulletsContact = 0;
	private long start, elapsed;
	private float defaultZ;
	private double accuracy;
	private boolean victory, defeat;
	private Random generator;
	
	public DungeonCrawl(Context context, String mapFile, String userBotFile)
	{
		//initialize the specified vars
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
		this._game = ga;	//set game activity in use
		int randomIndex;	//random index for iterator
		this.totalBots = generator.nextInt(tileMap.enemySpawnPointsX.size()-1) + 1; //total bots available
		
		//if to many bots requested, set to max
		if(totalBots >= tileMap.enemySpawnPointsX.size())
			totalBots = tileMap.enemySpawnPointsX.size();
		
		bots = new Bot[totalBots]; //initialize bot array
			
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
		//get users bot current tile locations
		int currentTileX = (int)Math.floor((userBot.getDrawableBot().parameters[0]+tileMap.mapWidth)/tileMap.tileStep);
		int currentTileY = (int)Math.floor(Math.abs((userBot.getDrawableBot().parameters[1] - tileMap.mapHeight)/tileMap.tileStep));
		
		//if user reaches finish point
		for(int i = 0; i < tileMap.finishPointsX.size(); i++){
			//victory
			if(userBot.getDrawableBot().isAlive && victory == false && defeat == false && tileMap.tileLocations[currentTileX][currentTileY][0] == tileMap.finishPointsX.get(i) && tileMap.tileLocations[currentTileX][currentTileY][1] == tileMap.finishPointsY.get(i)){
				victory = true;	
				Constants.finished_missions.add(_game.missionType);
				elapsed = System.currentTimeMillis() - start;
				Finalize("victory");
			}else
				//defeat
				if(!userBot.getDrawableBot().isAlive && victory == false && defeat == false){
				defeat = true;
				Finalize("defeat");
			}	
		}
	}
	

	@Override
	public void Finalize(final String type) {
		//show corresponding dialog if victory, or defeat
		
		if(type.equals("victory")){
			for(int i = 0; i < _game.getGameType().getBots().length; i++){
		       	if(_game.getGameType().getBots()[i].getDrawableBot().isAlive()){
		       		//do nothing
		       	}else{
		       		//calculate statistics
		       		kills++;
		       		numBulletsContact += _game.getGameType().getBots()[i].getDrawableBot().getNumBulletsHit();
		       	}
			}
			
			//calculate accuracy
			accuracy = ((double)numBulletsContact/(double)_game.getGameType().getBot().getDrawableGun().numShotsFired) * 100;
			accuracy = (double)Math.round(accuracy * 100) / 100;
		}
	
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
