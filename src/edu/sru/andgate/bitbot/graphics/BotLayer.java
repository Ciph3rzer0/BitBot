//File: BotLayer.java
//Purpose: Bot Layer Handler.

package edu.sru.andgate.bitbot.graphics;

public class BotLayer extends Bot
{
	Bot masterBotLayer;
	float[] layerParameters;
	
	public BotLayer(Bot master)
	{
		masterBotLayer = master;
		layerParameters = new float[11];
		layerParameters[0] = masterBotLayer.parameters[0];
		layerParameters[1] = masterBotLayer.parameters[1];
		layerParameters[2] = masterBotLayer.parameters[2];
		layerParameters[3] = 0.0f;
		layerParameters[4] = 0.0f;
		layerParameters[5] = 0.0f;
		layerParameters[6] = 1.0f;
		layerParameters[7] = masterBotLayer.parameters[7];
		layerParameters[8] = masterBotLayer.parameters[8];
		layerParameters[9] = masterBotLayer.parameters[9];
		layerParameters[10] = masterBotLayer.parameters[10];
	}
	
	@Override
	public void setRotationAngle(float newAngle)
	{
		layerParameters[3] = newAngle;
	}
	
	@Override
	public float[] getCurrentParameters()
	{
		layerParameters[0] = masterBotLayer.parameters[0];
		layerParameters[1] = masterBotLayer.parameters[1];
		layerParameters[2] = masterBotLayer.parameters[2];
		//This Variable Handled In BotLayer Class
		layerParameters[4] = masterBotLayer.parameters[4];
		layerParameters[5] = masterBotLayer.parameters[5];
		layerParameters[6] = masterBotLayer.parameters[6];
		layerParameters[7] = masterBotLayer.parameters[7];
		layerParameters[8] = masterBotLayer.parameters[8];
		layerParameters[9] = masterBotLayer.parameters[9];
		layerParameters[10] = masterBotLayer.parameters[10];
		
		return layerParameters;
	}
}
