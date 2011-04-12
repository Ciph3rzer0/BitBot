//File: GameGraphics.java
//Purpose: Test graphics rendering class, as well as imitate a game engine.
//Note: This class will just be renamed GameEngine at this point.

package edu.sru.andgate.bitbot.graphics;

import java.util.Timer;
import java.util.TimerTask;

import edu.sru.andgate.bitbot.Bot;
import edu.sru.andgate.bitbot.Constants;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.interpreter.InstructionLimitedVirtualMachine;
import edu.sru.andgate.bitbot.interpreter.SourceCode;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

public class GameActivity extends Activity
{	
	private GLSurfaceView glSurfaceView;
	GlRenderer gameRenderer;
	DrawableBot test;
	DrawableBot test2;
	DrawableBot test3;
	Drawable bot4,bot5,bot6,bot7,bot8,bot9,bot10;
	BotLayer testTurret;
	BotLayer test2Turret;
	int[][] drawList;
	int drawListPointer = 0;
	boolean gameLoop = true;
	
	int MAX_OBJECTS = 250;
	
	DrawableBot[] testBotArray;
	int NUM_TEST_BOTS = 0;
	
	final int TYPE_BOT = 0;
	
	int viewType = 0;
	
	TileMap testMap;
	CollisionManager collisionManager;
	
	TextView codeTxt;
	ScrollView codeScroll;
	
	InstructionLimitedVirtualMachine ilvm = new InstructionLimitedVirtualMachine();
	
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Keep screen from shutting off
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Set the layout.
        //setContentView(R.layout.game_activity);
        
        testBotArray = new DrawableBot[NUM_TEST_BOTS];
        
        // Initiate the Open GL view and create an instance with this activity
        //glSurfaceView = new GLSurfaceView(this);
        if(viewType == 0)
        {
        	setContentView(R.layout.game_activity);
        	glSurfaceView = (GLSurfaceView) findViewById(R.id.game_view);
        }
        else if(viewType == 1)
        {
        	setContentView(R.layout.game_activity_scrollview);
        	glSurfaceView = (GLSurfaceView) findViewById(R.id.game_view);
        	codeTxt = (TextView) findViewById(R.id.code_txt);
			codeScroll = (ScrollView) findViewById(R.id.code_scroll);
        }
        else if(viewType == 2)
        {
        	setContentView(R.layout.game_activity_scrollview);
        	glSurfaceView = (GLSurfaceView) findViewById(R.id.game_view);
        	codeTxt = (TextView) findViewById(R.id.code_txt);
			codeScroll = (ScrollView) findViewById(R.id.code_scroll);
			
    		Timer t = new Timer();
    		t.schedule(new TimerTask()
    		{
    			@Override
    			public void run()
    			{
    				codeTxt.post(new Runnable()
    				{
    					@Override
    					public void run()
    					{
    						codeTxt.append("\nmore code");
    						codeScroll.fullScroll(View.FOCUS_DOWN); 
    					}
    				});
    			}
    		}, 1000, 1000);
        }
        
        //Declare Tile Map
        testMap = new TileMap();
        
        //Declare Collision Manager
        collisionManager = new CollisionManager(getBaseContext(),testMap);		
		
        //Declare Draw List
        drawList = new int[2][MAX_OBJECTS];       
        
        //Test Bot 1
        test = new DrawableBot();
        test.setTranslation(-3.5f,0.1f,-5.0f);
        test.addTexture(R.drawable.sand);	//TextureID = 0
        
        
        //Test Bot 1 Turret Layer
        testTurret = new BotLayer(test);
        testTurret.addTexture(R.drawable.sandturret);
        testTurret.setRotationAngle(test.parameters[3]);
        //testTurret.setTranslation(0.0f,0.0f,-6.0f);
        
        //Test Bot 2
        test2 = new DrawableBot();
        test2.setTranslation(3.5f,0.1f,-5.0f);
        test2.setRotation(180.0f,0.0f,0.0f,-5.0f);
        test2.addTexture(R.drawable.adambot);	//TextureID = 0
        test2.moveStepSize = 0.08f;
        collisionManager.addCollisionDetectorToBot(test2);
        
        //Test Bot 2 Turret Layer
        test2Turret = new BotLayer(test2);
        test2Turret.addTexture(R.drawable.adamturret);
        test2Turret.setRotationAngle(test2.parameters[3]);
        
        //Test Bot 3
        test3 = new DrawableBot();
        test3.setTranslation(0.0f,0.1f,-5.0f);
        test3.setScale(1.2f,1.2f,1.2f);
        test3.addTexture(R.drawable.red);	//TextureID = 0
        test3.addTexture(R.drawable.green);	//TextureID = 1
        test3.setSelectedTexture(0);
       
        Bot b = Bot.CreateBotFromXML(getBaseContext(), "test_save.xml");
        Constants c = new Constants();
        DrawableBot db = new DrawableBot();
        db.setTranslation(0.0f,5.0f,-5.0f);
        db.addTexture(c.base_table.get(b.getBase()));
        BotLayer bl = new BotLayer(db);
        bl.addTexture(c.turret_table.get(b.getTurret()));
		b.attachDrawable(db);
		Log.v("BitBot", b.getCode().getCode());
		b.attachSourceCode(b.getCode());
		b.readyInterpreter();
		ilvm.addInterpreter(b.getInterpreter());
		// Run the vm every second.
		Timer t = new Timer();
		t.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				ilvm.resume(4);
			}
		}, 50, 50);
        
        /*
		String code =
			"Let d = -1\n" +
			"\n" +
			"While 1 Do\n" + 
			"  call bot_move(45, 5)\n" +
			"  \n" +
			"  Let d = d + 1\n" +
			"Loop\n"
		;
        SourceCode source = new SourceCode("Basic", code);
        
		Bot b = new Bot();
		b.attachDrawable(test3);
		b.attachSourceCode(source);
		b.readyInterpreter();
		ilvm.addInterpreter(b.getInterpreter());
		
		// Run the vm every second.
		Timer t = new Timer();
		t.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				ilvm.resume(4);
			}
		}, 50, 50);
        */
        
        gameRenderer = new GlRenderer(this.getBaseContext());
        
        //Set renderer to be the main renderer with the current activity context
        glSurfaceView.setEGLConfigChooser(false);
        glSurfaceView.setRenderer(gameRenderer);
        glSurfaceView.setRenderMode(0); //0 = Render When Dirty
        
        //Mass Test Bots
        for(int i=0;i<NUM_TEST_BOTS;i++)
        {
        	testBotArray[i] = new DrawableBot();
        	testBotArray[i].addTexture(R.drawable.red);
        	testBotArray[i].setTranslation(2.0f,0.0f,-5.0f);
        	gameRenderer.addObjectToWorld(testBotArray[i]);
        }
        
//        /* Comment out to turn off TileMap()
       // testMap = new TileMap();
        testMap.loadMapFile("testarena.map", this.getBaseContext());
        gameRenderer.setTileMap(testMap);
        
        //testMap.addTexture(R.drawable.stone);
        //gameRenderer.setTileMap(testMap);
        //*/
        
        //Add test objects to world
        gameRenderer.addObjectToWorld(testTurret);
        gameRenderer.addObjectToWorld(test);
        gameRenderer.addObjectToWorld(test2);
        gameRenderer.addObjectToWorld(test2Turret);
        gameRenderer.addObjectToWorld(test3);
        
        //Nick Test Loaded Bot
        gameRenderer.addObjectToWorld(db);
        gameRenderer.addObjectToWorld(bl);
   
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
       
        //Nick Test loaded bot
        addToDrawList(TYPE_BOT, db.ID);
        addToDrawList(TYPE_BOT, bl.ID);
      
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
    	Runnable gR = new Runnable()
    	{
    		//Proof of concept variables
    		float move = 0.01f;
    		float rotate = 1.0f;
    		boolean goinUp = true;
    		boolean thisFrameDrawn = false;
    		
    		/*
    		//Testing FPS Only
    		long startTime = 0;
    		long endTime = 0;
    		long timeCount = 0;
    		int frameCount = 0;
    		*/
    		
    		//Game Loop
    		public void run()
    		{
    			while(gameLoop)
    			{
    				//startTime = System.currentTimeMillis();
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
    	    		//test2.setTranslation(3.5f,(-1*(0.01f + move)),-5.0f);
    	    		test2.move();
    	    		//test2.move(45.0f, 0.1f);
    	    		test2Turret.setRotationAngle(rotate);
    	    		
    	    		if(goinUp)
    	    		{
    	    			move += 0.03f;
    	    			if(move >= 1.0f)
    	    			{
    	    				goinUp = false;
    	    				test.setRotation(180.0f,0.0f,0.0f,-5.0f);
    	    				testTurret.setRotationAngle(test.parameters[3]);
    	    				//test2.setRotation(0.0f,0.0f,0.0f,-5.0f);
    	    				test3.setSelectedTexture(0);
    	    			}
    	    		}
    	    		else
    	    		{
    	    			move -= 0.03f;
    	    			if(move <= -8.5f)
    	    			{
    	    				goinUp = true;
    	    				test.setRotation(0.0f,0.0f,0.0f,-5.0f);
    	    				testTurret.setRotationAngle(test.parameters[3]);
    	    				//test2.setRotation(180.0f,0.0f,0.0f,-5.0f);
    	    				test3.setSelectedTexture(1);
    	    			}
    	    		}
    	    		
    				//Collision Detection Updater
    				collisionManager.update();
    	    		
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
    	    		while(!thisFrameDrawn && gameLoop)
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
    	    		/*
    	    		endTime = System.currentTimeMillis();
    	    		timeCount += (endTime-startTime);
    	    		frameCount++;
    	    		if(timeCount >= 1000.0)
    	    		{
    	    			Log.v("bitbot", "FPS: " + frameCount);
    	    			frameCount = 0;
    	    			timeCount = 0;
    	    		}
    	    		*/
    			}
    		}
    	};
    	Thread gT = new Thread(gR);
    	gT.setName("Game Thread: " + gT.getName());
    	gT.start();
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
		gameLoop = false;
		finish();
	}
}