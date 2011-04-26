package edu.sru.andgate.bitbot.graphics;

import javax.microedition.khronos.opengles.GL10;
import edu.sru.andgate.bitbot.Bot;
import android.content.Context;

public interface Drawable
{
	public abstract void attachBot(Bot bot);
	
	public abstract float[] getCurrentParameters();
	
	public abstract void setTranslation(float newTX, float newTY, float newTZ);
	
	public abstract void setRotation(float newAngle, float newRX, float newRY, float newRZ);
	
	public abstract void setRotationAngle(float newAngle);
	
	public abstract void moveByTouch(float speed);
	
	public abstract void move();
	
	public abstract void move(float angle, float stepSize);
	
	public abstract void setScale(float newSX, float newSY, float newSZ);
	
	public abstract void setTextureUpdateFlag(float flag);
	
	public abstract void setSelectedTexture(int selectedTex);
	
	public abstract int getSelectedTexture();
	
	public abstract void addTexture(int texturePointer);
	
	public abstract void loadGLTexture(GL10 gl, Context context, int curTexPointer);
	
	public abstract void draw(GL10 gl);
	
}