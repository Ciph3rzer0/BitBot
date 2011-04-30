package edu.sru.andgate.bitbot.graphics;

import edu.sru.andgate.bitbot.R;

public class TileMapTextureLoader
{
	TileMap tileMap;
	public TileMapTextureLoader(TileMap tMap)
	{
		tileMap = tMap;
	}
	
	public void loadMapTextures(String mapFile)
	{
		//***ADD MAP TEXTURES HERE IN THE ORDER THEY ARE LISTEN IN THE .MAP FILE***
		//Map: testarena.map
		if(mapFile.equals("testarena.map"))
		{
      	  tileMap.addTexture(R.drawable.deftile);
    	  tileMap.addTexture(R.drawable.seltile);
    	  tileMap.addTexture(R.drawable.stone);
    	  tileMap.addTexture(R.drawable.brick);
    	  tileMap.addTexture(R.drawable.grass);
    	  tileMap.addTexture(R.drawable.sandtile);
		}
	}
}
