//File: Bot.java
//Purpose: Game Bot class.

package edu.sru.andgate.bitbot.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.opengl.GLUtils;
import android.util.Log;

import java.util.*;

public class DrawableParticleEmitter implements Drawable
{
	float[] parameters;
	int ID = 0;
	int textureCount = 0;
	int MAX_TEXTURE_ARRAY_SIZE = 5;
	int SELECTED_TEXTURE = 0;
	float moveAngle = 90.0f;
	float moveStepSize = 0.05f;
	ArrayList<Integer> textureHopper;
	ArrayList<Integer> layerIdList;
	boolean textureLoaded = false;
	BotLayer masterTurret;
	DrawableBot masterBot;
	int masterBotID = -1;
	int numLiveSparks = 0;
	int numTextures = 0;
	int numLiveExplosions = 0;
	float[][][] sparkParticles;
	float[][][] explosionParticles;
	int[] liveSparkEmitters;
	int[] liveExplosionEmitters;
	int lastBotSpark = -1;
	int consecutiveSparkCount = 0;
	
	//int MAX_CONSECUTIVE_SPARK_EMITTERS = 2;
	int MAX_SPARK_EMITTERS = 15;
	int MAX_EXPLOSION_EMITTERS = 10;
	float BOUNDING_RADIUS = 0.2f;
	int DAMAGE = 10;
	float SPEED = 0.4f;
	float LIFESPAN = 13.0f;
	float BARREL_LENGTH = 1.0f;
	float FUNNY_ANGLE = 90.0f;
	
	float EXPLOSION_SPEED = 0.4f;		//0.2
	float EXPLOSION_LIFESPAN = 40.0f;	//40.0
	
	public FloatBuffer vertexBuffer;	// buffer holding the vertices
	public float vertices[] = {
			-1.0f, -1.0f,  0.0f,		// V1 - bottom left
			-1.0f,  1.0f,  0.0f,		// V2 - top left
			 1.0f, -1.0f,  0.0f,		// V3 - bottom right
			 1.0f,  1.0f,  0.0f			// V4 - top right
	};

	public FloatBuffer textureBuffer;	// buffer holding the texture coordinates
	public float texture[] = {    		
			// Mapping coordinates for the vertices
			0.0f, 1.0f,		// top left		(V2)
			0.0f, 0.0f,		// bottom left	(V1)
			1.0f, 1.0f,		// top right	(V4)
			1.0f, 0.0f		// bottom right	(V3)
	};
	
	//Texture pointer
	public int[] textures = new int[MAX_TEXTURE_ARRAY_SIZE];

	public DrawableParticleEmitter()
	{
		// a float has 4 bytes so we allocate for each coordinate 4 bytes
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		
		// allocates the memory from the byte buffer
		vertexBuffer = byteBuffer.asFloatBuffer();
		
		// fill the vertexBuffer with the vertices
		vertexBuffer.put(vertices);
		
		// set the cursor position to the beginning of the buffer
		vertexBuffer.position(0);
		
		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
		
		parameters = new float[11];
		
		textureHopper = new ArrayList<Integer>(MAX_TEXTURE_ARRAY_SIZE);
		
		//Initialize parameters
		parameters[0] = 0.3f;	//tX
		parameters[1] = 0.1f;	//tY
		parameters[2] = -5.0f;	//tZ
		parameters[3] = 0.0f;	//rotationAngle
		parameters[4] = 0.0f;	//rX
		parameters[5] = 0.0f;	//rY
		parameters[6] = 1.0f;	//rZ
		parameters[7] = 0.85f;	//sX 0.3
		parameters[8] = 0.85f;	//sY
		parameters[9] = 0.85f;	//sZ
		parameters[10] = 1.0f;	//textureUpdateFlag (NO = 0.0, YES = 1.0) (Avoid at all costs)
		
		sparkParticles = new float[MAX_SPARK_EMITTERS][4][5]; //Index -> LocX, LocY, Lifetime Remaining, Trajectory, Speed
		explosionParticles = new float[MAX_EXPLOSION_EMITTERS][8][5]; //Index -> LocX, LocY, Lifetime Remaining, Trajectory, Speed
		
		liveSparkEmitters = new int[MAX_SPARK_EMITTERS];
		liveExplosionEmitters = new int[MAX_EXPLOSION_EMITTERS];
		
		addTexture(R.drawable.particle);
		addTexture(R.drawable.particle2);
		addTexture(R.drawable.particle3);
	}
	

	@Override
	public void attachBot(Bot bot)
	{
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#getCurrentParameters()
	 */
	@Override
	public float[] getCurrentParameters()
	{
		return parameters;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#setTranslation(float, float, float)
	 */
	@Override
	public void setTranslation(float newTX, float newTY, float newTZ)
	{
		parameters[0] = newTX;
		parameters[1] = newTY;
		parameters[2] = newTZ;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#setRotation(float, float, float, float)
	 */
	@Override
	public void setRotation(float newAngle, float newRX, float newRY, float newRZ)
	{
		parameters[3] = newAngle;
		parameters[4] = newRX;
		parameters[5] = newRY;
		parameters[6] = newRZ;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#setRotationAngle(float)
	 */
	@Override
	public void setRotationAngle(float newAngle)
	{
		parameters[3] = newAngle;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#move(float, float, float)
	 */
	@Override
	public void moveByTouch(float speed)
	{
		//
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#move(float, float)
	 */
	@Override
	public void move(float angle, float stepSize)
	{
		float rise = (float)(Math.sin(angle * (Math.PI / 180)) * stepSize) + parameters[1];
		float run = (float)(Math.cos(angle * (Math.PI / 180)) * stepSize) + parameters[0];
		
		parameters[0] = run;
		parameters[1] = rise;
	}
	
	public void move()
	{
		float rise = (float)(Math.sin(moveAngle * (Math.PI / 180)) * moveStepSize) + parameters[1];
		float run = (float)(Math.cos(moveAngle * (Math.PI / 180)) * moveStepSize) + parameters[0];
		
		parameters[0] = run;
		parameters[1] = rise;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#setScale(float, float, float)
	 */
	@Override
	public void setScale(float newSX, float newSY, float newSZ)
	{
		parameters[7] = newSX;
		parameters[8] = newSY;
		parameters[9] = newSZ;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#setTextureUpdateFlag(float)
	 */
	@Override
	public void setTextureUpdateFlag(float flag)
	{
		parameters[10] = flag;
	}
	
	public void setBoundingRadius(float radius)
	{
		BOUNDING_RADIUS = radius;
	}
	
	public void onBoundaryCollision()
	{
		//For now, flip angle and continue
		moveAngle = Math.abs(moveAngle - 360.0f) % 360.0f;
		parameters[3] = moveAngle + 90.0f;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#setSelectedTexture(int)
	 */
	@Override
	public void setSelectedTexture(int selectedTex)
	{
		SELECTED_TEXTURE = selectedTex;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#getSelectedTexture()
	 */
	@Override
	public int getSelectedTexture()
	{
		return SELECTED_TEXTURE;
	}
	
	/*
	public void setID(int id)
	{
		ID = id;
	}
	public int getID()
	{
		return ID;
	}
	*/
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#addTexture(int)
	 */
	@Override
	public void addTexture(int texturePointer)
	{
		textureHopper.add(texturePointer);
		numTextures++;
	}
	
	public void attachMasterBot(DrawableBot bot)
	{
		masterBot = bot;
		masterBotID = masterBot.ID;
	}
	
	public void setMasterBotID(int ID)
	{
		masterBotID = ID;
	}
	
	public void fireSparks(float initAngle, float centroidX, float centroidY)
	{
		int workingSparkID = -1;
		
		if(numLiveSparks < MAX_SPARK_EMITTERS)
		{
			for(int i=0;i<MAX_SPARK_EMITTERS;i++)
			{
				if(liveSparkEmitters[i] == 0)
				{
					workingSparkID = i;
					liveSparkEmitters[i] = 1;
					numLiveSparks++;
					break;
				}
			}
			if(workingSparkID != -1)
			{
				for(int j=0;j<4;j++)
				{
					sparkParticles[workingSparkID][j][0] = centroidX;
					sparkParticles[workingSparkID][j][1] = centroidY;
					sparkParticles[workingSparkID][j][2] = LIFESPAN;
					sparkParticles[workingSparkID][j][3] = initAngle;
					sparkParticles[workingSparkID][j][4] = SPEED;
				}
			}
		}
	}
	
	public void fireExplosion(float initAngle, float centroidX, float centroidY)
	{
		int workingExplosionID = -1;
		
		if(numLiveExplosions < MAX_EXPLOSION_EMITTERS)
		{
			for(int i=0;i<MAX_EXPLOSION_EMITTERS;i++)
			{
				if(liveExplosionEmitters[i] == 0)
				{
					workingExplosionID = i;
					liveExplosionEmitters[i] = 1;
					numLiveExplosions++;
					break;
				}
			}
			if(workingExplosionID != -1)
			{
				for(int j=0;j<8;j++)
				{
					explosionParticles[workingExplosionID][j][0] = centroidX;
					explosionParticles[workingExplosionID][j][1] = centroidY;
					explosionParticles[workingExplosionID][j][2] = EXPLOSION_LIFESPAN;
					explosionParticles[workingExplosionID][j][3] = initAngle;
					explosionParticles[workingExplosionID][j][4] = EXPLOSION_SPEED;
				}
			}
		}
	}
	
	public void update()
	{
		//SPARKS
		for(int i=0;i<MAX_SPARK_EMITTERS;i++)
		{
			if(liveSparkEmitters[i] == 1)
			{
				for(int j=0;j<4;j++)
				{
					//Calculate next particle x and y position				
					float nextX = sparkParticles[i][j][0] - ((float)(Math.sin(((sparkParticles[i][j][3] + (j*FUNNY_ANGLE)) % 360) * (Math.PI / 180)) * sparkParticles[i][j][4]));
					float nextY = (float)(Math.cos(((sparkParticles[i][j][3] + (j*FUNNY_ANGLE)) % 360) * (Math.PI / 180)) * sparkParticles[i][j][4]) + sparkParticles[i][j][1];
					
					//Update Particle Position
					sparkParticles[i][j][0] = nextX;
					sparkParticles[i][j][1] = nextY;
					//Decrement Lifespan
					sparkParticles[i][j][2] -= 1.0f;
					//Set appropriate sprite texture
					if(sparkParticles[i][j][2] > (LIFESPAN*0.75))
					{
						setSelectedTexture(0);
					}
					else if(sparkParticles[i][j][2] <= (LIFESPAN * 0.75) && sparkParticles[i][j][2] > (LIFESPAN * 0.5))
					{
						setSelectedTexture(1);
						sparkParticles[i][j][4] -= (sparkParticles[i][j][4] * 0.25f);
					}
					else if(sparkParticles[i][j][2] <= (LIFESPAN * 0.5))
					{
						setSelectedTexture(2);
						sparkParticles[i][j][4] -= (sparkParticles[i][j][4] * 0.5f);
					}
					//If the particle has reached the end of its life, make it inactive
					if(sparkParticles[i][j][2] <= 0)
					{
						liveSparkEmitters[i] = 0;
						numLiveSparks--;
					}
				}
			}
		}
		//EXPLOSIONS
		for(int i=0;i<MAX_EXPLOSION_EMITTERS;i++)
		{
			if(liveExplosionEmitters[i] == 1)
			{
				for(int j=0;j<8;j++)
				{
					//Calculate next particle x and y position				
					float nextX = explosionParticles[i][j][0] - ((float)(Math.sin(((explosionParticles[i][j][3] + (j*45.0)) % 360) * (Math.PI / 180)) * explosionParticles[i][j][4]));
					float nextY = (float)(Math.cos(((explosionParticles[i][j][3] + (j*45.0)) % 360) * (Math.PI / 180)) * explosionParticles[i][j][4]) + explosionParticles[i][j][1];
					
					//Update Particle Position
					explosionParticles[i][j][0] = nextX;
					explosionParticles[i][j][1] = nextY;
					//Decrement Lifespan
					explosionParticles[i][j][2] -= 1.0f;
					//Set appropriate sprite texture
					if(explosionParticles[i][j][2] > (EXPLOSION_SPEED*0.75))
					{
						setSelectedTexture(0);
					}
					else if(explosionParticles[i][j][2] <= (EXPLOSION_LIFESPAN * 0.75) && explosionParticles[i][j][2] > (EXPLOSION_LIFESPAN * 0.5))
					{
						setSelectedTexture(1);
						explosionParticles[i][j][4] -= (explosionParticles[i][j][4] * 0.25f);
					}
					else if(explosionParticles[i][j][2] <= (EXPLOSION_LIFESPAN * 0.5))
					{
						setSelectedTexture(2);
						explosionParticles[i][j][4] -= (explosionParticles[i][j][4] * 0.5f);
					}
					//If the particle has reached the end of its life, make it inactive
					if(explosionParticles[i][j][2] <= 0)
					{
						liveExplosionEmitters[i] = 0;
						numLiveExplosions--;
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#loadGLTexture(javax.microedition.khronos.opengles.GL10, android.content.Context, int)
	 */
	@Override
	public void loadGLTexture(GL10 gl, Context context, int curTexPointer)
	{		
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),textureHopper.get(curTexPointer));

		// generate one texture pointer
		gl.glGenTextures(1, textures, curTexPointer);
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[curTexPointer]);
		
		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		// Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		
		// Clean up
		bitmap.recycle();
		
		//textureLoaded = true;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#draw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void draw(GL10 gl)
	{
		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[SELECTED_TEXTURE]);
		
		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		// Set the face rotation
		//gl.glFrontFace(GL10.GL_CW);
		
		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		//Draw live spark emitters to screen
		for(int i=0;i<MAX_SPARK_EMITTERS;i++)
		{
			//If live round, prepare and draw
			if(liveSparkEmitters[i] == 1)
			{
				for(int j=0;j<4;j++)
				{
					gl.glLoadIdentity();
					//gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f); // 0x8080FFFF
					//Translate Bullet
					gl.glTranslatef(sparkParticles[i][j][0],sparkParticles[i][j][1], -5.0f);	//Translate Object
					//gl.glRotatef(parameters[3], parameters[4], parameters[5], parameters[6]);	//Rotate Object
					gl.glScalef(0.15f, 0.15f, 0.15f);											//Scale Object
					//Draw
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
				}
			}
		}
		
		//Draw live explosion emitters to screen
		for(int i=0;i<MAX_EXPLOSION_EMITTERS;i++)
		{
			//If live round, prepare and draw
			if(liveExplosionEmitters[i] == 1)
			{
				for(int j=0;j<8;j++)
				{
					gl.glLoadIdentity();
					//gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f); // 0x8080FFFF
					//Translate Bullet
					gl.glTranslatef(explosionParticles[i][j][0],explosionParticles[i][j][1], -5.0f);	//Translate Object
					//gl.glRotatef(parameters[3], parameters[4], parameters[5], parameters[6]);			//Rotate Object
					gl.glScalef(0.15f, 0.15f, 0.15f);													//Scale Object
					//Draw
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
				}
			}
		}

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

}