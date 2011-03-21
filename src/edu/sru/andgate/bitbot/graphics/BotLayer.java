//File: BotLayer.java
//Purpose: Bot Layer Handler.

package edu.sru.andgate.bitbot.graphics;

import javax.microedition.khronos.opengles.GL10;

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
	@Override
	public void draw(GL10 gl)
	{
		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[SELECTED_TEXTURE]);
		
		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		// Set the face rotation
		gl.glFrontFace(GL10.GL_CW);
		
		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		//Prepare openGL for drawing
		gl.glTranslatef(masterBotLayer.parameters[0],masterBotLayer.parameters[1], masterBotLayer.parameters[2]);				//Translate Object
		gl.glRotatef(layerParameters[3], layerParameters[4], layerParameters[5], layerParameters[6]);	//Rotate Object
		gl.glScalef(layerParameters[7], layerParameters[8], layerParameters[9]);					//Scale Object
		
		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
}