package edu.sru.andgate.bitbot.graphics;

import edu.sru.andgate.bitbot.Bot;
import android.util.Log;

public class CollisionManager
{
	//Master Variables
	DrawableBot[] botList;
	DrawableGun[] gunList;
	DrawableParticleEmitter particleEmitter;
	TileMap tileMap;
	float[][][] bulletTileIndicies;
	float[][] botTileIndicies;
	int numBots = 0;
	int numGuns = 0;
	float sparkAngle = 0.0f;
	
	int MAX_BOT_OBJECTS = 100;
	int MAX_GUN_OBJECTS = 100;
	int MAX_BULLETS_PER_GUN = 8;
	float FUNNY_ANGLE = 45.0f;
	
	public CollisionManager(TileMap tMap)
	{
		botList = new DrawableBot[MAX_BOT_OBJECTS];
		gunList = new DrawableGun[MAX_GUN_OBJECTS];
		bulletTileIndicies = new float[MAX_GUN_OBJECTS][MAX_BULLETS_PER_GUN][2];	//Gun -> BulletID's -> TileX, TileY
		botTileIndicies = new float[MAX_BOT_OBJECTS][2];	//BotID -> TileX, TileY
		for(int i=0;i<MAX_GUN_OBJECTS;i++)
		{
			for(int j=0;j<MAX_BULLETS_PER_GUN;j++)
			{
				bulletTileIndicies[i][j][0] = -1;
				bulletTileIndicies[i][j][0] = -1;
			}
		}
		for(int i=0;i<MAX_BOT_OBJECTS;i++)
		{
			botTileIndicies[i][0] = -1;
			botTileIndicies[i][1] = -1;
		}
		
		tileMap = tMap;
	}
	
	public void addCollisionDetector(DrawableBot bot)
	{
		botList[numBots] = bot;
		numBots++;
	}
	
	public void addCollisionDetector(DrawableGun gun)
	{
		gunList[numGuns] = gun;
		numGuns++;
	}
	
	public void addCollisionDetector(Bot bot)
	{
		botList[numBots] = bot.getDrawableBot();
		numBots++;
		
		gunList[numGuns] = bot.getDrawableGun();
		numGuns++;
	}
	
	public void setParticleEmitter(DrawableParticleEmitter emitter)
	{
		particleEmitter = emitter;
	}
	
	public void update()
	{
		//Local vars
		int currentTileX = 0;
		int currentTileY = 0;
		
		//BOT V BOT Prep
		for(int i=0;i<numBots;i++)
		{
			currentTileX = (int)Math.floor((botList[i].parameters[0]+tileMap.mapWidth)/tileMap.tileStep);
			currentTileY = (int)Math.floor(Math.abs((botList[i].parameters[1] - tileMap.mapHeight)/tileMap.tileStep));
			
			botTileIndicies[i][0] = currentTileX;
			botTileIndicies[i][1] = currentTileY;
		}
		
		currentTileX = 0;
		currentTileY = 0;
		
		//BULLETS VS BOTS (Preparation)
		for(int i=0;i<numGuns;i++)
		{
			for(int j=0;j<gunList[i].MAX_BULLETS;j++)
			{
				//Determine current tile position for bullet
				if(gunList[i].bullets[j][0] > 0)
				{
					currentTileX = (int)Math.floor((gunList[i].bullets[j][0]+tileMap.mapWidth)/tileMap.tileStep);	//FLOOR
				}
				else
				{
					currentTileX = (int)Math.floor((gunList[i].bullets[j][0]+tileMap.mapWidth)/tileMap.tileStep);	//CEIL?
				}
				if(gunList[i].bullets[j][1] <= 0)
				{
					currentTileY = (int)Math.floor(Math.abs((gunList[i].bullets[j][1] - tileMap.mapHeight)/tileMap.tileStep)); //FLOOR
				}
				else
				{
					currentTileY = (int)Math.floor(Math.abs((gunList[i].bullets[j][1] - tileMap.mapHeight)/tileMap.tileStep)); //CEIL?
				}
				//Add to list
				bulletTileIndicies[i][j][0] = currentTileX;
				bulletTileIndicies[i][j][1] = currentTileY;
				
				//BULLET V BOUNDARIES
				//Check surrounding tiles to determine if further calculations are neccessary
				//Current Tile | Left Tile | Right Tile | Top Tile | Bottom Tile
				if(currentTileX >= 0 && currentTileX <= (tileMap.mapWidth-1) && currentTileY >= 0 && currentTileY <= (tileMap.mapHeight-1))
				{
					if(tileMap.tileBoundaries[currentTileX][currentTileY][0] != 0)
					{
						//Check possible collision points (Bottom | Top | Left | Right)
						if(tileMap.tileBoundaries[currentTileX][currentTileY][0] == 4)
						{
							//Computes distance from the boundary point to the center of the bullet
							float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][1]-(tileMap.tileStep/2)) - gunList[i].bullets[j][1]);
							
							//Check for collision
							if(distance <= gunList[i].BOUNDING_RADIUS)
							{
								//Set position of bullet to collision point
								gunList[i].bullets[j][1] = tileMap.tileLocations[currentTileX][currentTileY][1]-(tileMap.tileStep/2) + gunList[i].BOUNDING_RADIUS;
								//Set lifespan remaining to 0 (kill bullet)
								gunList[i].bullets[j][2] = 0.0f;
							}
						}
						else if(tileMap.tileBoundaries[currentTileX][currentTileY][0] == 1)
						{
							//Computes distance from the boundary point to the center of the bot
							float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][1]+(tileMap.tileStep/2)) - gunList[i].bullets[j][1]);
							
							//Check for collision
							if(distance <= gunList[i].BOUNDING_RADIUS)
							{
								gunList[i].bullets[j][1] = (tileMap.tileLocations[currentTileX][currentTileY][1]+(tileMap.tileStep/2)) - gunList[i].BOUNDING_RADIUS;
								//Set lifespan remaining to 0 (kill bullet)
								gunList[i].bullets[j][2] = 0.0f;
							}
						}
						else if(tileMap.tileBoundaries[currentTileX][currentTileY][0] == 2)
						{
							//Computes distance from the boundary point to the center of the bot
							float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][0]-(tileMap.tileStep/2)) - gunList[i].bullets[j][0]);
							
							//Check for collision
							if(distance <= gunList[i].BOUNDING_RADIUS)
							{
								gunList[i].bullets[j][0] = (tileMap.tileLocations[currentTileX][currentTileY][0]-(tileMap.tileStep/2)) + gunList[i].BOUNDING_RADIUS;
								//Set lifespan remaining to 0 (kill bullet)
								gunList[i].bullets[j][2] = 0.0f;
							}
						}
						else if(tileMap.tileBoundaries[currentTileX][currentTileY][0] == 3)
						{
							//Computes distance from the boundary point to the center of the bot
							float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][0]+(tileMap.tileStep/2)) - gunList[i].bullets[j][0]);
							
							//Check for collision
							if(distance <= gunList[i].BOUNDING_RADIUS)
							{
								gunList[i].bullets[j][0] = (tileMap.tileLocations[currentTileX][currentTileY][0]+(tileMap.tileStep/2)) - gunList[i].BOUNDING_RADIUS;
								//Set lifespan remaining to 0 (kill bullet)
								gunList[i].bullets[j][2] = 0.0f;
							}
						}
					}
					if(currentTileX != 0 && tileMap.tileBoundaries[currentTileX-1][currentTileY][0] == 3)
					{
						//Computes distance from the boundary point to the center of the bot
						float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][0]-(tileMap.tileStep/2)) - gunList[i].bullets[j][0]);
						
						//Check for collision
						if(distance <= gunList[i].BOUNDING_RADIUS)
						{
							gunList[i].bullets[j][0] = (tileMap.tileLocations[currentTileX][currentTileY][0]-(tileMap.tileStep/2)) + gunList[i].BOUNDING_RADIUS;
							//Set lifespan remaining to 0 (kill bullet)
							gunList[i].bullets[j][2] = 0.0f;
						}
					}
					if((currentTileX != tileMap.mapWidth-1) && tileMap.tileBoundaries[currentTileX+1][currentTileY][0] == 2)
					{
						//Computes distance from the boundary point to the center of the bot
						float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][0]+(tileMap.tileStep/2)) - gunList[i].bullets[j][0]);
						
						//Check for collision
						if(distance <= gunList[i].BOUNDING_RADIUS)
						{
							gunList[i].bullets[j][0] = (tileMap.tileLocations[currentTileX][currentTileY][0]+(tileMap.tileStep/2)) - gunList[i].BOUNDING_RADIUS;
							//Set lifespan remaining to 0 (kill bullet)
							gunList[i].bullets[j][2] = 0.0f;
						}
					}
					if(currentTileY != 0 && tileMap.tileBoundaries[currentTileX][currentTileY-1][0] == 4)
					{
						//Computes distance from the boundary point to the center of the bot
						float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][1]+(tileMap.tileStep/2)) - gunList[i].bullets[j][1]);
						
						//Check for collision
						if(distance <= gunList[i].BOUNDING_RADIUS)
						{
							gunList[i].bullets[j][1] = (tileMap.tileLocations[currentTileX][currentTileY][1]+(tileMap.tileStep/2)) - gunList[i].BOUNDING_RADIUS;
							//Set lifespan remaining to 0 (kill bullet)
							gunList[i].bullets[j][2] = 0.0f;
						}
					}
					if((currentTileY != tileMap.mapHeight-1) && tileMap.tileBoundaries[currentTileX][currentTileY+1][0] == 1)
					{
						//Computes distance from the boundary point to the center of the bot
						float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][1]-(tileMap.tileStep/2)) - gunList[i].bullets[j][1]);
						
						//Check for collision
						if(distance <= gunList[i].BOUNDING_RADIUS)
						{
							gunList[i].bullets[j][1] = tileMap.tileLocations[currentTileX][currentTileY][1]-(tileMap.tileStep/2) + gunList[i].BOUNDING_RADIUS;
							//Set lifespan remaining to 0 (kill bullet)
							gunList[i].bullets[j][2] = 0.0f;
						}
					}
				}
			}
		}
		
		
		//Local Vars
		currentTileX = 0;
		currentTileY = 0;
		
		//BOTS V BOUNDARIES
		for(int i=0;i<numBots;i++)
		{
			//Determine current tile position for bot
			if(botList[i].parameters[0] > 0)
			{
				currentTileX = (int)Math.floor((botList[i].parameters[0]+tileMap.mapWidth)/tileMap.tileStep);
			}
			else
			{
				currentTileX = (int)Math.floor((botList[i].parameters[0]+tileMap.mapWidth)/tileMap.tileStep);
			}
			if(botList[i].parameters[1] <= 0)
			{
				currentTileY = (int)Math.floor(Math.abs((botList[i].parameters[1] - tileMap.mapHeight)/tileMap.tileStep));
			}
			else
			{
				currentTileY = (int)Math.floor(Math.abs((botList[i].parameters[1] - tileMap.mapHeight)/tileMap.tileStep));
			}
			/*
			if(botList[i].BOT_TYPE == 1)
			{
				Log.v("bitbot", "Current Tile Pos: " + currentTileX + " , " + currentTileY);
			}
			*/
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
					if(distance < botList[i].BOUNDING_RADIUS)
					{
						//Set bot position to the point of collision
						botList[i].parameters[1] = tileMap.tileLocations[currentTileX][currentTileY][1]-(tileMap.tileStep/2) + botList[i].BOUNDING_RADIUS;
						//Notify bot of boundary collision
						botList[i].onBoundaryCollision();
					}
				}
				else if(tileMap.tileBoundaries[currentTileX][currentTileY][0] == 1)
				{
					//Computes distance from the boundary point to the center of the bot
					float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][1]+(tileMap.tileStep/2)) - botList[i].parameters[1]);
					
					//Check for collision
					if(distance < botList[i].BOUNDING_RADIUS)
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
					if(distance < botList[i].BOUNDING_RADIUS)
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
					if(distance < botList[i].BOUNDING_RADIUS)
					{
						//Set bot position to the point of collision
						botList[i].parameters[0] = (tileMap.tileLocations[currentTileX][currentTileY][0]+(tileMap.tileStep/2)) - botList[i].BOUNDING_RADIUS;
						//Notify bot of boundary collision
						botList[i].onBoundaryCollision();
					}
				}
			}
			if(currentTileX != 0 && tileMap.tileBoundaries[currentTileX-1][currentTileY][0] == 3)
			{
				//Computes distance from the boundary point to the center of the bot
				float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][0]-(tileMap.tileStep/2)) - botList[i].parameters[0]);
				
				//Check for collision
				if(distance < botList[i].BOUNDING_RADIUS)
				{
					//Set bot position to the point of collision
					botList[i].parameters[0] = (tileMap.tileLocations[currentTileX][currentTileY][0]-(tileMap.tileStep/2)) + botList[i].BOUNDING_RADIUS;
					//Notify bot of boundary collision
					botList[i].onBoundaryCollision();
				}
			}
			if(currentTileX != (tileMap.mapWidth-1) && tileMap.tileBoundaries[currentTileX+1][currentTileY][0] == 2)
			{
				//Computes distance from the boundary point to the center of the bot
				float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][0]+(tileMap.tileStep/2)) - botList[i].parameters[0]);
				
				//Check for collision
				if(distance < botList[i].BOUNDING_RADIUS)
				{
					//Set bot position to the point of collision
					botList[i].parameters[0] = (tileMap.tileLocations[currentTileX][currentTileY][0]+(tileMap.tileStep/2)) - botList[i].BOUNDING_RADIUS;
					//Notify bot of boundary collision
					botList[i].onBoundaryCollision();
				}
			}
			if(currentTileY != 0 && tileMap.tileBoundaries[currentTileX][currentTileY-1][0] == 4)
			{
				//Computes distance from the boundary point to the center of the bot
				float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][1]+(tileMap.tileStep/2)) - botList[i].parameters[1]);
				
				//Check for collision
				if(distance < botList[i].BOUNDING_RADIUS)
				{
					//Set bot position to the point of collision
					botList[i].parameters[1] = (tileMap.tileLocations[currentTileX][currentTileY][1]+(tileMap.tileStep/2)) - botList[i].BOUNDING_RADIUS;
					//Notify bot of boundary collision
					botList[i].onBoundaryCollision();
				}
			}
			if(currentTileY != (tileMap.mapHeight-1) && tileMap.tileBoundaries[currentTileX][currentTileY+1][0] == 1)
			{
				//Computes distance from the boundary point to the center of the bot
				float distance = Math.abs((tileMap.tileLocations[currentTileX][currentTileY][1]-(tileMap.tileStep/2)) - botList[i].parameters[1]);
				
				//Check for collision
				if(distance < botList[i].BOUNDING_RADIUS)
				{
					//Set bot position to the point of collision
					botList[i].parameters[1] = tileMap.tileLocations[currentTileX][currentTileY][1]-(tileMap.tileStep/2) + botList[i].BOUNDING_RADIUS;
					//Notify bot of boundary collision
					botList[i].onBoundaryCollision();
				}
			}
			
			//Now check to see if there are any possible bullet collisions
			for(int k=0;k<numGuns;k++)
			{
				for(int l=0;l<MAX_BULLETS_PER_GUN;l++)
				{
					if((bulletTileIndicies[k][l][0] >= currentTileX-1 && bulletTileIndicies[k][l][0] <= currentTileX+1) && (bulletTileIndicies[k][l][1] >= currentTileY-1 && bulletTileIndicies[k][l][1] <= currentTileY+1) && bulletTileIndicies[k][l][0] != -1 && bulletTileIndicies[k][l][1] != -1 && botList[i].ID != gunList[k].masterBotID && botList[i].isAlive && gunList[k].bullets[l][2] > 0)
					{
						float a = Math.abs(gunList[k].bullets[l][1] - botList[i].parameters[1]); 		//(y-leg)
						float b = Math.abs(gunList[k].bullets[l][0] - botList[i].parameters[0]); 		//(x-leg)
						float distance = (float)Math.sqrt((float)Math.pow(a,2) + (float)Math.pow(b,2)); //hypotenuse
						
						if(distance <= (gunList[k].BOUNDING_RADIUS + botList[i].BOUNDING_RADIUS))
						{
							//Log.v("bitbot","BOT HIT! GunID: " + k + " BulletID: " + l);
							//Kill Bullet
							gunList[k].bullets[l][2] = 0.0f;
							//Decrement Bot Health
							botList[i].HEALTH -= gunList[k].DAMAGE;
							if(botList[i].HEALTH <= 0)
							{
								botList[i].onKill();
								particleEmitter.fireExplosion(sparkAngle, botList[i].parameters[0], botList[i].parameters[1]);
							}
							else
							{
								botList[i].onDamage();
								if(particleEmitter != null)
								{
									sparkAngle = (sparkAngle+FUNNY_ANGLE)%360;
									particleEmitter.fireSparks(sparkAngle, botList[i].parameters[0], botList[i].parameters[1]);
								}
							}
						}
					}
				}
			}
			
			//Check for possible Bot v Bot collisions
			for(int k=0;k<numBots;k++)
			{
				if((botTileIndicies[k][0] >= currentTileX-1 && botTileIndicies[k][0] <= currentTileX+1) && (botTileIndicies[k][1] >= currentTileY-1 && botTileIndicies[k][1] <= currentTileY+1) && botTileIndicies[k][0] != -1 && botTileIndicies[k][1] != -1 && botList[i].isAlive && botList[k].isAlive && botList[i].ID != botList[k].ID)
				{
					//Get distnace between current bot and other bots
					float a = Math.abs(botList[k].parameters[1] - botList[i].parameters[1]); 		//(y-leg)
					float b = Math.abs(botList[k].parameters[0] - botList[i].parameters[0]); 		//(x-leg)
					float distance = (float)Math.sqrt((float)Math.pow(a,2) + (float)Math.pow(b,2)); //hypotenuse
					
					//Check collision threshold
					if(distance <= (botList[i].BOUNDING_RADIUS + botList[k].BOUNDING_RADIUS))
					{
						Log.v("bitbot", "BOT V BOT COLLISION!");
						
						/*
						float overlapDistance = Math.abs(distance - (botList[i].BOUNDING_RADIUS + botList[k].BOUNDING_RADIUS));
						
						float xOver = (float)Math.sin(botList[i].parameters[3]) * overlapDistance;
						float yOver = (float)Math.cos(botList[i].parameters[3]) * overlapDistance;
						
						botList[k].parameters[0] = botList[k].parameters[0] - xOver;
						botList[k].parameters[1] = botList[k].parameters[1] + yOver;
						*/
					}
				}
			}
		}
	}
}
