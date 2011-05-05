//File: Bot.java
//Purpose: Game Bot class.

package edu.sru.andgate.bitbot.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import edu.sru.andgate.bitbot.Bot;

public class DrawableBot implements Drawable
{
	private Bot _bot;						//Reference To Bot Container
	public float[] parameters;
	private int numBulletsInContact;
	int ID = 0;
	int textureCount = 0;
	int MAX_TEXTURE_ARRAY_SIZE = 5;
	int SELECTED_TEXTURE = 0;
	int numDamageSprites = 0;
	int currentDamageSprite = 0;
	int normalSprite = 0;
	float distanceRemaining = 0;
	float rise = 0;
	float run = 0;
	int numLayers = 0;
	int[][] damageSprites;
	float moveAngle = 0.0f;
	float moveStepSize = 0.0f;
	ArrayList<Integer> textureHopper;
	ArrayList<Integer> layerIdList;
	boolean textureLoaded = false;
	public boolean isAlive = true;
	BotLayer[] layers;
	
	//Master Parameters
	int BOT_TYPE = 0; 						//0=Enemy 1=User
	int HEALTH = 100;
	int MAX_LAYERS = 3;	
	float BOUNDING_RADIUS = 0.75f; 			//Should be 0.75
	
	public FloatBuffer vertexBuffer;		// buffer holding the vertices
	public float vertices[] = {
			-1.0f, -1.0f,  0.0f,			// V1 - bottom left
			-1.0f,  1.0f,  0.0f,			// V2 - top left
			 1.0f, -1.0f,  0.0f,			// V3 - bottom right
			 1.0f,  1.0f,  0.0f				// V4 - top right
	};

	public FloatBuffer textureBuffer;		// buffer holding the texture coordinates
	public float texture[] = {    		
			// Mapping coordinates for the vertices
			0.0f, 1.0f,						// top left		(V2)
			0.0f, 0.0f,						// bottom left	(V1)
			1.0f, 1.0f,						// top right	(V4)
			1.0f, 0.0f						// bottom right	(V3)
	};
	
	//Texture pointer
	public int[] textures = new int[MAX_TEXTURE_ARRAY_SIZE];
	
	public DrawableBot(Bot bot)
	{
		this();
		_bot = bot;
	}
	
	public DrawableBot()
	{
		numBulletsInContact = 0;
		//Allocate Buffer Objects
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuffer.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
		
		//Initialize Arrays
		parameters = new float[11];
		
		damageSprites = new int[MAX_TEXTURE_ARRAY_SIZE][2];
		
		layers = new BotLayer[MAX_LAYERS];
		
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
	}
	
	public void attachCollisionSound(Context context, int soundID){
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
		//Ensure that we dont overshoot the touch target
		if(speed > distanceRemaining && distanceRemaining > 0)
		{
			speed = distanceRemaining;
		}
		//If we are not at our destination, calculate next move position
		if(distanceRemaining > 0)
		{
			rise = (float)(Math.sin(moveAngle * (Math.PI/180)) * speed) + parameters[1];
			run = (float)(Math.cos(moveAngle * (Math.PI/180)) * speed) + parameters[0];
		
			parameters[0] = run;
			parameters[1] = rise;
		}
		distanceRemaining -= speed;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#move(float, float)
	 */
	@Override
	public void move(float angle, float stepSize)
	{
		//Calculate next move (x and y) coordinates based off local variables
		float rise = (float)(Math.sin(angle * (Math.PI / 180)) * stepSize) + parameters[1];
		float run = (float)(Math.cos(angle * (Math.PI / 180)) * stepSize) + parameters[0];
		
		parameters[0] = run;
		parameters[1] = rise;
	}
	
	public void move()
	{
		//Calculate next move (x and y) coordinates based off global variables
		float rise = (float)(Math.sin(moveAngle * (Math.PI / 180)) * moveStepSize) + parameters[1];
		float run = (float)(Math.cos(moveAngle * (Math.PI / 180)) * moveStepSize) + parameters[0];
		
		parameters[0] = run;
		parameters[1] = rise;
	}
	
	public void setMove(float angle, float velocity)
	{
		//Set global move variables (for interpreter)
		moveAngle = angle;
		moveStepSize = velocity;
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
	
	public void setBotType(int TYPE)
	{
		BOT_TYPE = TYPE;
	}
	
	public void setBoundingRadius(float radius)
	{
		BOUNDING_RADIUS = radius;
	}
	
	public void onBoundaryCollision()
	{
		moveStepSize = 0;
		
		if (_bot != null)
			_bot.callOnBoundaryCollision();
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#setSelectedTexture(int)
	 */
	@Override
	public void setSelectedTexture(int selectedTex)
	{
		SELECTED_TEXTURE = selectedTex;
	}
	
	public void addDamageTextureToSequence(int texID, int range)
	{
		damageSprites[numDamageSprites][0] = texID;
		damageSprites[numDamageSprites][1] = range;
		numDamageSprites++;
	}
	
	public void setNormalSprite(int texID)
	{
		normalSprite = texID;
	}
	
	public void onDamage()
	{
		//Get health value which the next damage sprite should be activated at
		int triggerHealth = damageSprites[currentDamageSprite][1];
		
		//If that health value has been met, update sprite
		if(HEALTH <= triggerHealth && currentDamageSprite < numDamageSprites)
		{
			SELECTED_TEXTURE = damageSprites[currentDamageSprite][0];
			currentDamageSprite++;
		}
		numBulletsInContact++;
	}
	
	public void onKill()
	{
		isAlive = false;
	}
	
	public void onTouchEvent(float touchX, float touchY)
	{
		//Calculate Distance Remaining to Touch Waypoint
		distanceRemaining = (float)Math.sqrt((Math.pow((touchX-parameters[0]), 2) + Math.pow((touchY-parameters[1]), 2)));
		
		//Determine appropriate move angle based on the current quadrant
		if((touchX-parameters[0]) >= 0.0f && (touchY-parameters[1]) >= 0)
		{
			moveAngle = (float)Math.abs(Math.toDegrees(Math.atan((touchY-parameters[1])/(touchX-parameters[0]))) % 360);
		}
		else if((touchX-parameters[0]) < 0.0f && (touchY-parameters[1]) >= 0)
		{
			moveAngle = (90.0f - (float)Math.abs(Math.toDegrees(Math.atan((touchY-parameters[1])/(touchX-parameters[0]))) % 360)) + 90.0f;
		}
		else if((touchX-parameters[0]) < 0.0f && (touchY-parameters[1]) < 0)
		{
			moveAngle = (float)Math.abs(Math.toDegrees(Math.atan((touchY-parameters[1])/(touchX-parameters[0]))) % 360) + 180.0f;
		}
		else
		{
			moveAngle = (180.0f - (float)Math.abs(Math.toDegrees(Math.atan((touchY-parameters[1])/(touchX-parameters[0]))) % 360)) + 180.0f;
		}
		
		//Rotate the bot sprite
		parameters[3] = 360 - (90+moveAngle);
		
		//Pass result to interpreter
		if (_bot != null)
			_bot.callOnTouchEvent(touchX-parameters[0], touchY-parameters[1]);
	}
	
	public void onBotFocus(DrawableBot bot)
	{
		//EMPTY FOR NOW
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#getSelectedTexture()
	 */
	@Override
	public int getSelectedTexture()
	{
		return SELECTED_TEXTURE;
	}

	public void setID(int id)
	{
		ID = id;
	}
	public int getID()
	{
		return ID;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#addTexture(int)
	 */
	@Override
	public void addTexture(int texturePointer)
	{
		textureHopper.add(texturePointer);
	}
	
	public void attachLayer(BotLayer layer)
	{
		layers[numLayers] = layer;
		numLayers++;
	}

	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#loadGLTexture(javax.microedition.khronos.opengles.GL10, android.content.Context, int)
	 */
	@Override
	public void loadGLTexture(GL10 gl, Context context, int curTexPointer)
	{
		//Load bot sprite from image file
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),textureHopper.get(curTexPointer));

		//Generate Texture Pointer
		gl.glGenTextures(1, textures, curTexPointer);
		
		//Bind texture to array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[curTexPointer]);
		
		//Filter Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		//Specifiy 2-DIM Texture
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		
		//Clean up
		bitmap.recycle();
		
		//textureLoaded = true;
	}
	
	/* (non-Javadoc)
	 * @see edu.sru.andgate.bitbot.graphics.Drawable#draw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void draw(GL10 gl)
	{
		//Bind selected texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[SELECTED_TEXTURE]);
		
		//Enable Buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		//Set face rotation
		gl.glFrontFace(GL10.GL_CW);
		
		//Point to buffers
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		//Prepare OpenGL Matrix for Drawing
		gl.glTranslatef(parameters[0],parameters[1], parameters[2]);				//Translate Object
		gl.glRotatef(parameters[3], parameters[4], parameters[5], parameters[6]);	//Rotate Object
		gl.glScalef(parameters[7], parameters[8], parameters[9]);					//Scale Object
		
		//Draw object as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		//Clean up before exiting
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	
	public boolean isAlive(){
		return this.isAlive;
	}
	
	public int getNumBulletsHit(){
		return this.numBulletsInContact;
	}

}
