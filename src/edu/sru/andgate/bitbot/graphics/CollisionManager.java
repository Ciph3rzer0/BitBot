package edu.sru.andgate.bitbot.graphics;

import android.util.Log;

public class CollisionManager
{
	//Master Variables
	DrawableBot[] botList;
	TileMap tileMap;
	int numBots = 0;
	
	int MAX_BOT_OBJECTS = 100;
	
	public CollisionManager(TileMap tMap)
	{
		botList = new DrawableBot[MAX_BOT_OBJECTS];
		tileMap = tMap;
	}
	
	public void addCollisionDetectorToBot(DrawableBot bot)
	{
		botList[numBots] = bot;
		numBots++;
	}
	
	public void onBotCollision(DrawableBot bot)
	{
		//Code
	}
	
	public void onBulletCollision()
	{
		//Code
	}
	
	public void update()
	{
		//Local Vars
		int currentTileX = 0;
		int currentTileY = 0;
		//Testing Only Vars
		float boundingRadius = 1.0f;
		
		//Do Collision Detection for Bots Hitting a Boundary
		for(int i=0;i<numBots;i++)
		{
			//Determine current tile position for bot
			currentTileX = (int)Math.floor((botList[i].parameters[0]+tileMap.mapWidth)/tileMap.tileStep);
			currentTileY = (int)Math.floor(Math.abs(botList[i].parameters[1] - tileMap.mapHeight)/tileMap.tileStep);
			//Check surrounding tiles to determine if further calculations are neccessary
			//Current Tile | Left Tile | Right Tile | Top Tile | Bottom Tile
			if(tileMap.tileBoundaries[currentTileX][currentTileY][0] != 0)
			{
				//Check possible collision points (Bottom | Top | Left | Right)
				if(tileMap.tileBoundaries[currentTileX][currentTileY][0] == 4)
				{
					//Computes distance from the boundary point to the center of the bot
					float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][1]-(tileMap.tileStep/2)) - botList[i].parameters[1]);
					
					//Check for collision
					if(distance <= boundingRadius)
					{
						Log.v("bitbot", "A COLLISION HAS OCCURED!");
						//PROPER COLLISION HANDLING HERE
						botList[i].parameters[1] = tileMap.tileLocations[currentTileX][currentTileY+1][1];	//Temporary Handling
					}
				}
			}
			else if(currentTileX != 0 && tileMap.tileBoundaries[currentTileX-1][currentTileY][0] == 3)
			{
				//Code
			}
			else if(currentTileX != tileMap.mapWidth && tileMap.tileBoundaries[currentTileX+1][currentTileY][0] == 2)
			{
				//Code
			}
			else if(currentTileY != 0 && tileMap.tileBoundaries[currentTileX][currentTileY+1][0] == 4)
			{
				//Code
			}
			else if(currentTileY != tileMap.mapHeight && tileMap.tileBoundaries[currentTileX][currentTileY-1][0] == 1)
			{
				//Code
			}
		}
	}
}
