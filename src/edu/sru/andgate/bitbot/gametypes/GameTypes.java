package edu.sru.andgate.bitbot.gametypes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.util.Log;

public abstract class GameTypes {
	private int mapHeight, mapWidth;
	private int[][][] spawnPoints;
	
	public abstract void Initialize(); 

	public abstract void Update();
	
	public abstract void Finalize();
   
	public void setSpawnPoints(String mapFile, Context context)
	{
		int spawnArrayLoc = 160; //line the spawn location starts
		Log.v("bitbot", "Loading Spawn Points...");
	
		try
		{			
		    InputStream mapFileStream = context.getAssets().open(mapFile);
			BufferedReader r = new BufferedReader(new InputStreamReader(mapFileStream));
		  
			//Read basic map information
			mapWidth = Integer.parseInt(r.readLine()); 
			mapHeight = Integer.parseInt(r.readLine());
			
			spawnPoints = new int[mapWidth][mapHeight][1];
			
			//move reader down to begining of spawnPoints array (will be same in all maps)
			for(int i = 0; i < spawnArrayLoc; i++){
				r.readLine();			
			}
			
			 //put the spawn point array in a temp array
			 for(int i=0;i<mapHeight;i++)
		        {
		        	String[] codes = r.readLine().split(" ");
		        	for(int j=0;j<codes.length;j++)
		        	{
		        		spawnPoints[j][i][0] = Integer.parseInt(codes[j]);
		        	}
		        }
				mapFileStream.close();
	          Log.v("bitbot", "Spawn Points loading complete.");
	          
		}
		catch(Exception e)
		{
			System.out.println("Error loading Spawn Points: " + e.toString());
		}
	}
	
	public int[][][] getSpawnPoints(){
		return this.spawnPoints;
	}
	
	public int getMapHeight(){
		return this.mapHeight;
	}
	
	public int getMapWidth(){
		return this.mapWidth;
	}
}