//File: GameGraphics.java
//Purpose: Test graphics rendering class, as well as imitate a game engine.
//Note: This class will just be renamed GameEngine at this point.

package edu.sru.andgate.bitbot.graphics;

import edu.sru.andgate.bitbot.R;
//import edu.sru.andgate.bitbot.graphics.*;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
//import java.io.*;

public class GameEngine extends Activity
{	
	private GLSurfaceView glSurfaceView;
	GlRenderer gameRenderer;
	Bot test;
	Bot test2;
	Bot test3;
	Bot bot4,bot5,bot6,bot7,bot8,bot9,bot10;
	BotLayer testTurret;
	BotLayer test2Turret;
	int[][] drawList;
	int drawListPointer = 0;
	
	int MAX_OBJECTS = 250;
	
	Bot[] testBotArray;
	int NUM_TEST_BOTS = 0;
	
	final int TYPE_BOT = 0;
	
	TileMap testMap;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {		
        super.onCreate(savedInstanceState);
        
        Log.v("bitbot", "onCreate called");
        
        testBotArray = new Bot[NUM_TEST_BOTS];
        
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initiate the Open GL view and create an instance with this activity
        glSurfaceView = new GLSurfaceView(this);
        
        //Declare Draw List
        drawList = new int[2][MAX_OBJECTS];       
        
        //Test Bot 1
        test = new Bot();
        test.setTranslation(-3.5f,0.1f,-5.0f);
        test.addTexture(R.drawable.sand);	//TextureID = 0
        
        //Test Bot 1 Turret Layer
        testTurret = new BotLayer(test);
        testTurret.addTexture(R.drawable.sandturret);
        testTurret.setRotationAngle(test.parameters[3]);
        //testTurret.setTranslation(0.0f,0.0f,-6.0f);
        
        //Test Bot 2
        test2 = new Bot();
        test2.setTranslation(3.5f,0.1f,-5.0f);
        test2.setRotation(180.0f,0.0f,0.0f,-5.0f);
        test2.addTexture(R.drawable.adambot);	//TextureID = 0
        
        //Test Bot 2 Turret Layer
        test2Turret = new BotLayer(test2);
        test2Turret.addTexture(R.drawable.adamturret);
        test2Turret.setRotationAngle(test2.parameters[3]);
        
        //Test Bot 3
        test3 = new Bot();
        test3.setTranslation(0.0f,0.1f,-5.0f);
        test3.setScale(1.2f,1.2f,1.2f);
        test3.addTexture(R.drawable.red);	//TextureID = 0
        test3.addTexture(R.drawable.green);	//TextureID = 1
        test3.setSelectedTexture(0);
        
        gameRenderer = new GlRenderer(this.getBaseContext());
        
        //Set renderer to be the main renderer with the current activity context
        glSurfaceView.setEGLConfigChooser(false);
        glSurfaceView.setRenderer(gameRenderer);
        glSurfaceView.setRenderMode(0); //0 = Render When Dirty
        setContentView(glSurfaceView);
        
        //Mass Test Bots
        for(int i=0;i<NUM_TEST_BOTS;i++)
        {
        	testBotArray[i] = new Bot();
        	testBotArray[i].addTexture(R.drawable.red);
        	testBotArray[i].setTranslation(2.0f,0.0f,-5.0f);
        	gameRenderer.addObjectToWorld(testBotArray[i]);
        }
        
        testMap = new TileMap();
        testMap.loadMapFile("testmap.map", this.getBaseContext());
        gameRenderer.setTileMap(testMap);
        
        //testMap.addTexture(R.drawable.stone);
        gameRenderer.setTileMap(testMap);
        
        //Add test objects to world
        gameRenderer.addObjectToWorld(testTurret);
        gameRenderer.addObjectToWorld(test);
        gameRenderer.addObjectToWorld(test2);
        gameRenderer.addObjectToWorld(test2Turret);
        gameRenderer.addObjectToWorld(test3);
   
        //Connect draw list to Renderer
        gameRenderer.setDrawList(drawList);
        
		//Tell camera to continuously update so it can follow user bot
		gameRenderer.continuousCameraUpdate = true;
        
        preloadTextures();							//Should always be called before game starts
        
        //Mass of bots
        
        for(int i=0;i<NUM_TEST_BOTS;i++)
        {
        	addToDrawList(TYPE_BOT,testBotArray[i].ID);
        }
        
        //Add objects to draw list (which is cleared by renderer after each frame is drawn)
        addToDrawList(TYPE_BOT,test.ID);
        addToDrawList(TYPE_BOT,testTurret.ID);
        addToDrawList(TYPE_BOT,test2.ID);
        addToDrawList(TYPE_BOT,test2Turret.ID);
        addToDrawList(TYPE_BOT,test3.ID);
      
        gameRenderer.drawListSize = drawListPointer+1;
        
        gameRenderer.startSimulation();
        fakeGameLoop();
    }
    
    public void preloadTextures()
    {
    	gameRenderer.setPreloadTextureFlag(true);
    	glSurfaceView.requestRender();
    }
    
    public void addToDrawList(int type, int objectID)
    {
    	drawList[0][drawListPointer] = type;		//Set Object Type
    	drawList[1][drawListPointer] = objectID;	//Set Object ID
    	drawListPointer++;							//Increment Draw List Pointer / (Size-1)
    }
    
    public void fakeGameLoop()
    {
    	new Thread(new Runnable()
    	{
    		//Proof of concept variables
    		float move = 0.01f;
    		float rotate = 1.0f;
    		boolean goinUp = true;
    		boolean thisFrameDrawn = false;
    		
    		//Game Loop
    		public void run()
    		{
    			while(true)
    			{
    				//IMPORTANT VARIABLE FOR RENDERER SYNCHRONIZATION
    				thisFrameDrawn = false;
    				
    				//Handle 360 Degree Turret Rotation
    				rotate += 1.0f;
    				if(rotate >= 360)
    				{
    					rotate = 0.0f;
    				}
    				
    				//Make some other stuff happen
    	    		test.setTranslation(-3.5f,(0.01f + move),-5.0f);
    	    		test2.setTranslation(3.5f,(-1*(0.01f + move)),-5.0f);
    	    		//test2.move(45.0f, 0.1f);
    	    		test2Turret.setRotationAngle(rotate);
    	    		
    	    		if(goinUp)
    	    		{
    	    			move += 0.05f;
    	    			if(move >= 5.0f)
    	    			{
    	    				goinUp = false;
    	    				test.setRotation(180.0f,0.0f,0.0f,-5.0f);
    	    				testTurret.setRotationAngle(test.parameters[3]);
    	    				test2.setRotation(0.0f,0.0f,0.0f,-5.0f);
    	    				test3.setSelectedTexture(0);
    	    			}
    	    		}
    	    		else
    	    		{
    	    			move -= 0.05f;
    	    			if(move <= -5.0f)
    	    			{
    	    				goinUp = true;
    	    				test.setRotation(0.0f,0.0f,0.0f,-5.0f);
    	    				testTurret.setRotationAngle(test.parameters[3]);
    	    				test2.setRotation(180.0f,0.0f,0.0f,-5.0f);
    	    				test3.setSelectedTexture(1);
    	    			}
    	    		}
    	    		
    				//Camera Stuff
    				gameRenderer.cameraX = test2.parameters[0];
    				gameRenderer.cameraY = test2.parameters[1];
    	    		
    				/*
    	            //Add objects to draw list (which is cleared by renderer after each frame is drawn)
    	            addToDrawList(TYPE_BOT,test.ID);
    	            addToDrawList(TYPE_BOT,testTurret.ID);
    	            addToDrawList(TYPE_BOT,test2.ID);
    	            addToDrawList(TYPE_BOT,test2Turret.ID);
    	            addToDrawList(TYPE_BOT,test3.ID);
    	            
    	            for(int i=0;i<NUM_TEST_BOTS;i++)
    	            {
    	            	addToDrawList(TYPE_BOT,testBotArray[i].ID);
    	            }
    	            
    	            gameRenderer.drawListSize = drawListPointer+1;
    	    		*/
    	            
    	            //Renderer Synchronization / Draw Frame Request
    	    		while(!thisFrameDrawn)
    	    		{
	    	    		if(gameRenderer.getFrameDrawn())
	    	    		{
	    	    			//gameRenderer.drawListSize = drawListPointer+1;
	    	    			gameRenderer.setFrameDrawn(false);
	    	    			glSurfaceView.requestRender();
	    	    			thisFrameDrawn = true;
	    	    			//drawListPointer = 0;
	    	    		}
    	    		}
    			}
    		}
    	}).start();
    }
    
	@Override
	protected void onResume()
	{
		super.onResume();
		glSurfaceView.onResume();
		preloadTextures(); //Reload textures on resume to prevent missing texture / white square problem.
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		glSurfaceView.onPause();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		//Additional Destroy Code Here
	}
}