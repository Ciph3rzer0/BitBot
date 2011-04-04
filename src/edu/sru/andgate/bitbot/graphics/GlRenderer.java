//File: GlRenderer.java
//Purpose: Render game graphics.
//NOTE: For missing texture white square issue:
//		-Solution 1: Put all textures under drawable-nodpi to prevent resizing to a non-power of 2.
//		-Solution 2: Setting minSdkVersion to anything under 5 works. Weird.

package edu.sru.andgate.bitbot.graphics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
//import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class GlRenderer implements Renderer
{
	private Context context;
	boolean goinUp = true;
	int drawList[][];
	DrawableBot[] objectList;
	float[] paramList;
	int objCount = 0;
	int totalObjCount = 0;
	int botCount = 0;
	int botLayerCount = 0;
	public float cameraZoom = 8.42f;	//2.6 for testing
	float aspectRatio;
	float cameraX = 0.0f;
	float cameraY = 0.0f;
	float screenWidth;
	float screenHeight;
	public int drawListSize = 0;
	boolean frameDrawn = false;
	boolean startSimulation = false;
	boolean preloadTextures = false;
	public boolean continuousCameraUpdate = false;
	TileMap tileMap;
	
	int MAX_OBJECTS = 500;
	
	//Constructor hands over context
	public GlRenderer(Context context)
	{
		this.context = context;
		//List of all objects in game
		objectList = new DrawableBot[MAX_OBJECTS];
		//Get parameter list of current object
		paramList = new float[11];
	}
	
	public boolean getFrameDrawn()
	{
		return frameDrawn;
	}
	
	public void setFrameDrawn(boolean fd)
	{
		frameDrawn = fd;
	}
	
	public void setDrawList(int[][] dl)
	{
		drawList = dl;
	}
	
	public void setTileMap(TileMap tm)
	{
		tileMap = tm;
	}
	
	public void addObjectToWorld(DrawableBot obj)
	{
		objectList[objCount] = obj;		//Add object to master list
		obj.ID = objCount;				//Set the object ID to the corresponding index in the master list
		objCount++;						//Increment Total Object Count
	}
	
	public void startSimulation()
	{
		startSimulation = true;
	}
	
	public void setPreloadTextureFlag(boolean texLoadFlag)
	{
		preloadTextures = true;
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		//Pre-load Textures
		if(preloadTextures)
		{
			//Bot Objects
			for(int i=0;i<objCount;i++)
			{
				for(int j=0;j<objectList[i].textureHopper.size();j++)
				{
					objectList[i].loadGLTexture(gl, this.context, j);
					objectList[i].setTextureUpdateFlag(0.0f);
				}
			}
			
			if (tileMap != null)
			{
				//Tile Map Textures
				for(int i=0;i<tileMap.numTextures;i++)
				{
					tileMap.loadGLTexture(gl, this.context, i);
				}
			}
			
			preloadTextures = false;
		}
		//Main Simulation
		if(startSimulation)
		{
			//Clear Screen and Depth Buffer
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			
			//Tile map update is a special case and isnt used with drawList
			gl.glLoadIdentity();
//			tileMap.draw(gl);
			
			//Draw all objects in Draw List
			for(int i=0;i < drawListSize;i++)	//NEEDS CHANGED BACK
			{
				//Handle Bot/BotLayer Objects
				if(drawList[0][i] == 0)
				{
					//Reset ModelView Matrix
					gl.glLoadIdentity();
					//Draw object with parameter changes loaded
					objectList[drawList[1][i]].draw(gl);
				}
				//Handle OTHER_OBJECT_TYPES_HERE
			}
			//Reset Draw List
			//drawListSize = 0;
			
			//CAMERA MOVEMENT CONTROLLER
			if(continuousCameraUpdate)
			{
				gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
				gl.glLoadIdentity(); 					//Reset The Projection Matrix
			   // gl.glOrthof((-1.0f * cameraZoom), cameraZoom, ((-1.0f * aspectRatio) * cameraZoom), (aspectRatio * cameraZoom), 0.01f, 100.0f);
			    gl.glOrthof(((cameraX) - cameraZoom), (cameraX) + cameraZoom, (cameraY) - (aspectRatio * cameraZoom), (cameraY) + (aspectRatio * cameraZoom), 0.01f, 100.0f);
			    
			    if (tileMap != null)
			    	tileMap.setDrawRegion(((cameraX) - cameraZoom), (cameraX) + cameraZoom, (cameraY) + (aspectRatio * cameraZoom), (cameraY) - (aspectRatio * cameraZoom));
				
			    
			    gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
				gl.glLoadIdentity(); 					//Reset The Modelview Matrix
			}
			
			//Notify Game Engine that the frame has been drawn
			frameDrawn = true;
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		//Calculate Aspect Ratio
		aspectRatio = (float)height/(float)width;
		screenWidth = width;
		screenHeight = height;
		
		//Prevent A Divide By Zero
		if(height == 0)
		{
			height = 1;
		}

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport //CAMERA TRANSLATION
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix

		/*
		//Perspective Camera Setup: (Old)
		gl.glTranslatef(0.0f, 0.0f, 0.0f);
		GLU.gluPerspective(gl, 90.0f, (float)width / (float)height, 0.1f, 100.0f); //2nd parameter here will allow you to view more, or zoom out
		*/
		
	    //Orthographic Camera Setup:
		//gl.glEnable(GL10.GL_DEPTH_TEST);
	    //gl.glOrthof((-1.0f * cameraZoom), cameraZoom, ((-1.0f * aspectRatio) * cameraZoom), (aspectRatio * cameraZoom), 0.01f, 100.0f);
	    gl.glOrthof(((cameraX) - cameraZoom), (cameraX) + cameraZoom, (cameraY) - (aspectRatio * cameraZoom), (cameraY) + (aspectRatio * cameraZoom), 0.01f, 100.0f);
	    
	    if (tileMap != null)
	    tileMap.setDrawRegion(((cameraX) - cameraZoom), (cameraX) + cameraZoom, (cameraY) + (aspectRatio * cameraZoom), (cameraY) - (aspectRatio * cameraZoom));

		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		//gl.glLoadIdentity(); 					//Reset The Modelview Matrix
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{		
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.5f); 	//Background Clear Color (Black)
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables/Disables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA); 
		
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
	}
}