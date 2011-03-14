package edu.sru.andgate.bitbot.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import edu.sru.andgate.bitbot.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Square {
	
	float[] parameters;
	int myID = 0;
	
	private FloatBuffer vertexBuffer;	// buffer holding the vertices
	private float vertices[] = {
			-1.0f, -1.0f,  0.0f,		// V1 - bottom left
			-1.0f,  1.0f,  0.0f,		// V2 - top left
			 1.0f, -1.0f,  0.0f,		// V3 - bottom right
			 1.0f,  1.0f,  0.0f			// V4 - top right
	};

	private FloatBuffer textureBuffer;	// buffer holding the texture coordinates
	private float texture[] = {    		
			// Mapping coordinates for the vertices
			0.0f, 1.0f,		// top left		(V2)
			0.0f, 0.0f,		// bottom left	(V1)
			1.0f, 1.0f,		// top right	(V4)
			1.0f, 0.0f		// bottom right	(V3)
	};
	
	/** The texture pointer */
	private int[] textures = new int[1];

	public Square() {
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
		
		//Test parameters
		parameters[0] = 0.3f;
		parameters[1] = 0.1f;
		parameters[2] = -5.0f;
		parameters[3] = 0.0f;
		parameters[4] = 0.0f;
		parameters[5] = 0.0f;
		parameters[6] = 1.0f;
		parameters[7] = 0.15f;
		parameters[8] = 0.15f;
		parameters[9] = 0.15f;
		parameters[10] = 1.0f;
	}
	
	public float[] getCurrentParameters()
	{
		return parameters;
	}
	
	public void setTranslation(float newTX, float newTY, float newTZ)
	{
		parameters[0] = newTX;
		parameters[1] = newTY;
		parameters[2] = newTZ;
	}
	
	public void setRotation(float newAngle, float newRX, float newRY, float newRZ)
	{
		parameters[3] = newAngle;
		parameters[4] = newRX;
		parameters[5] = newRY;
		parameters[6] = newRZ;
	}
	
	public void setScale(float newSX, float newSY, float newSZ)
	{
		//code
	}
	
	public void setTX(float newVar)
	{
		parameters[0] = newVar;
	}
	public void setTY(float newVar)
	{
		parameters[1] = newVar;
	}
	public void setTZ(float newVar)
	{
		parameters[2] = newVar;
	}
	public void setSX(float newVar)
	{
		parameters[7] = newVar;
	}
	public void setSY(float newVar)
	{
		parameters[8] = newVar;
	}
	public void setSZ(float newVar)
	{
		parameters[9] = newVar;
	}
	public void setID(int id)
	{
		myID = id;
	}
	public int getID()
	{
		return myID;
	}

	/**
	 * Load the texture for the square
	 * @param gl
	 * @param context
	 */
	public void loadGLTexture(GL10 gl, Context context) {
		// loading texture
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.blue);

		// generate one texture pointer
		gl.glGenTextures(1, textures, 0);
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		//gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); 

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT)
		
		// Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		
		// Clean up
		bitmap.recycle();
	}

	
	/** The draw method for the square with the GL context */
	public void draw(GL10 gl) {
		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		// Set the face rotation
		gl.glFrontFace(GL10.GL_CW);
		
		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		
	}
}
