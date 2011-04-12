package edu.sru.andgate.bitbot.graphics;

import edu.sru.andgate.bitbot.R;
import android.content.Context;
import android.media.MediaPlayer;

public class CollisionManager
{
	//Master Variables
	DrawableBot[] botList;
	TileMap tileMap;
	int numBots = 0;
	Context context;
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
		
		//Do Collision Detection for Bots Hitting a Boundary
		for(int i=0;i<numBots;i++)
		{
			//Determine current tile position for bot
			if(botList[i].parameters[0] > 0)
			{
				currentTileX = (int)Math.floor((botList[i].parameters[0]+tileMap.mapWidth)/tileMap.tileStep);
			}
			else
			{
				currentTileX = (int)Math.ceil((botList[i].parameters[0]+tileMap.mapWidth)/tileMap.tileStep);
			}
			if(botList[i].parameters[1] <= 0)
			{
				currentTileY = (int)Math.floor(Math.abs((botList[i].parameters[1] - tileMap.mapHeight)/tileMap.tileStep));
			}
			else
			{
				currentTileY = (int)Math.ceil(Math.abs((botList[i].parameters[1] - tileMap.mapHeight)/tileMap.tileStep));
			}
			
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
					if(distance <= botList[i].BOUNDING_RADIUS)
					{
						//Set bot position to the point of collision
						botList[i].parameters[1] = tileMap.tileLocations[currentTileX][currentTileY][1]-(tileMap.tileStep/2) + botList[i].BOUNDING_RADIUS;
						//Notify bot of boundary collision
						botList[i].onBoundaryCollision();
					}
				}
			}
				else if(tileMap.tileBoundaries[currentTileX][currentTileY][0] == 1)
				{
					//Computes distance from the boundary point to the center of the bot
					float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][1]+(tileMap.tileStep/2)) - botList[i].parameters[1]);
					
					//Check for collision
					if(distance <= botList[i].BOUNDING_RADIUS)
					{
						//Set bot position to the point of collision
						botList[i].parameters[1] = (tileMap.tileLocations[currentTileX][currentTileY][1]+(tileMap.tileStep/2)) - botList[i].BOUNDING_RADIUS;
						//Notify bot of boundary collision
						botList[i].onBoundaryCollision();
					}
				}
				else if(tileMap.tileBoundaries[currentTileX][currentTileY][0] == 2)
				{
					//Computes distance from the boundary point to the center of the bot
					float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][0]-(tileMap.tileStep/2)) - botList[i].parameters[0]);
					
					//Check for collision
					if(distance <= botList[i].BOUNDING_RADIUS)
					{
						//Set bot position to the point of collision
						botList[i].parameters[0] = (tileMap.tileLocations[currentTileX][currentTileY][0]-(tileMap.tileStep/2)) + botList[i].BOUNDING_RADIUS;
						//Notify bot of boundary collision
						botList[i].onBoundaryCollision();
					}
				}
				else if(tileMap.tileBoundaries[currentTileX][currentTileY][0] == 3)
				{
					//Computes distance from the boundary point to the center of the bot
					float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][0]+(tileMap.tileStep/2)) - botList[i].parameters[0]);
					
					//Check for collision
					if(distance <= botList[i].BOUNDING_RADIUS)
					{
						//Set bot position to the point of collision
						botList[i].parameters[0] = (tileMap.tileLocations[currentTileX][currentTileY][0]+(tileMap.tileStep/2)) - botList[i].BOUNDING_RADIUS;
						//Notify bot of boundary collision
						botList[i].onBoundaryCollision();
					}
			}
			else if(currentTileX != 0 && tileMap.tileBoundaries[currentTileX-1][currentTileY][0] == 3)
			{
				//Computes distance from the boundary point to the center of the bot
				float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][0]-(tileMap.tileStep/2)) - botList[i].parameters[0]);
				
				//Check for collision
				if(distance <= botList[i].BOUNDING_RADIUS)
				{
					//Set bot position to the point of collision
					botList[i].parameters[0] = (tileMap.tileLocations[currentTileX][currentTileY][0]-(tileMap.tileStep/2)) + botList[i].BOUNDING_RADIUS;
					//Notify bot of boundary collision
					botList[i].onBoundaryCollision();
				}
			}
			else if(currentTileX != tileMap.mapWidth && tileMap.tileBoundaries[currentTileX+1][currentTileY][0] == 2)
			{
				//Computes distance from the boundary point to the center of the bot
				float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][0]+(tileMap.tileStep/2)) - botList[i].parameters[0]);
				
				//Check for collision
				if(distance <= botList[i].BOUNDING_RADIUS)
				{
					//Set bot position to the point of collision
					botList[i].parameters[0] = (tileMap.tileLocations[currentTileX][currentTileY][0]+(tileMap.tileStep/2)) - botList[i].BOUNDING_RADIUS;
					//Notify bot of boundary collision
					botList[i].onBoundaryCollision();
				}
			}
			else if(currentTileY != 0 && tileMap.tileBoundaries[currentTileX][currentTileY-1][0] == 4)
			{
				//Computes distance from the boundary point to the center of the bot
				float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][1]+(tileMap.tileStep/2)) - botList[i].parameters[1]);
				
				//Check for collision
				if(distance <= botList[i].BOUNDING_RADIUS)
				{
					//Set bot position to the point of collision
					botList[i].parameters[1] = (tileMap.tileLocations[currentTileX][currentTileY][1]+(tileMap.tileStep/2)) - botList[i].BOUNDING_RADIUS;
					//Notify bot of boundary collision
					botList[i].onBoundaryCollision();
				}
			}
			else if(currentTileY != tileMap.mapHeight && tileMap.tileBoundaries[currentTileX][currentTileY+1][0] == 1)
			{
				//Computes distance from the boundary point to the center of the bot
				float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][1]-(tileMap.tileStep/2)) - botList[i].parameters[1]);
				
				//Check for collision
				if(distance <= botList[i].BOUNDING_RADIUS)
				{
					//Set bot position to the point of collision
					botList[i].parameters[1] = tileMap.tileLocations[currentTileX][currentTileY][1]-(tileMap.tileStep/2) + botList[i].BOUNDING_RADIUS;
					//Notify bot of boundary collision
					botList[i].onBoundaryCollision();
				}
			}
		}
	}
}
