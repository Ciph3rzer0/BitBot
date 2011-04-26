package edu.sru.andgate.bitbot.gametypes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public abstract class GameTypes {
	public int mapHeight, mapWidth;
	private int enemySpawnCode, userSpawnCode;
	public ArrayList<Integer> enemySpawnPointsX ,enemySpawnPointsY, userSpawnPointsX, userSpawnPointsY;

	public abstract void Initialize(); 

	public abstract void Update();
	
	public abstract void Finalize();
   
	public void setSpawnPoints(String mapFile, Context context)
	{
		int spawnArrayLoc = 109; //line the spawn location starts
		
		enemySpawnPointsX = new ArrayList<Integer>();
		enemySpawnPointsY = new ArrayList<Integer>();
		userSpawnPointsX = new ArrayList<Integer>();
		userSpawnPointsY = new ArrayList<Integer>();
		
		Log.v("GameTypes", "Loading Spawn Points...");
	
		try
		{			
		    InputStream mapFileStream = context.getAssets().open(mapFile);
			BufferedReader r = new BufferedReader(new InputStreamReader(mapFileStream));
		  
			//Read basic map information
			enemySpawnCode = 1;
			userSpawnCode = 2;
			
			mapWidth = Integer.parseInt(r.readLine()); 
			mapHeight = Integer.parseInt(r.readLine());
			
			//move reader down to begining of spawnPoints array (will be same in all maps)
			for(int i = 0; i < spawnArrayLoc; i++){
				r.readLine();			
			}
		
			//put the spawn point array in a temp array
			int enemyCounter = 0;
			int userCounter = 0;
			 for(int i=0;i<mapHeight;i++)
		        {
		        	String[] codes = r.readLine().split(" ");
		        	for(int j=0;j<codes.length;j++)
		        	{
		        		if(Integer.parseInt(codes[j]) == enemySpawnCode){
		        			Log.v("GameTypes", "Enemy Spawn Point Found at: " + j + "," + i);
		        			enemySpawnPointsX.add(enemyCounter,j);
		       				enemySpawnPointsY.add(enemyCounter,i);
		       				enemyCounter++;
		        		}else if(Integer.parseInt(codes[j]) == userSpawnCode){
		        			Log.v("GameTypes", "User Spawn Point Found at: " + j + "," + i);
		        			userSpawnPointsX.add(userCounter, j);
		        			userSpawnPointsY.add(userCounter, i);
		        			userCounter++;
		        		}
		        		
		        	}
		        }
				mapFileStream.close();
	          Log.v("GameTypes", "Spawn Points loading complete.");
	          
		}
		catch(Exception e)
		{
			System.out.println("Error loading Spawn Points: " + e.toString());
		}
	}
	
	public int getMapHeight(){
		return this.mapHeight;
	}
	
	public int getMapWidth(){
		return this.mapWidth;
	}
}