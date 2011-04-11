package edu.sru.andgate.bitbot;

import edu.sru.andgate.bitbot.graphics.GameActivity;
import edu.sru.andgate.bitbot.ide.CodeBuilderActivity;
import edu.sru.andgate.bitbot.ide.IDE;
import edu.sru.andgate.bitbot.ide.botbuilder.BotBuilderActivity;
import edu.sru.andgate.bitbot.interpreter.Test;
import edu.sru.andgate.bitbot.missionlist.MissionListActivity;
import edu.sru.andgate.bitbot.tutorial.Tutorial_List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Bot b = new Bot();
        b.setName("Nick");
        b.setBase("square");
        b.setTurret("basic");
        b.setCode(
        	"Let d = -1\n" +
			"\n" +
			"While 1 Do\n" + 
			"  call bot_move(45, 5)\n" +
			"  \n" +
			"  Let d = d + 1\n" +
			"Loop\n");
        b.saveBotToXML(getBaseContext(),"test_save.xml");
               
//        startActivity(new Intent(MainMenu.this, BotBuilderActivity.class));
        
        final ImageView bot_turret = (ImageView) findViewById(R.id.bot_turret);
        
        // Temporarily for testing - Josh
//		startActivity(new Intent(MainMenu.this, BotBuilderActivity.class));
//		startActivity(new Intent(MainMenu.this, GameActivity.class));
        
        /* ******************** Start Game *********************** */
        Button game_modes = (Button) findViewById(R.id.game_modes);
		game_modes.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				rotateImage(bot_turret, R.drawable.mainturret, R.id.bot_turret, 0);
				//open graphics for now
				Intent engineIntent = new Intent(MainMenu.this, GameActivity.class);
				startActivity(engineIntent);
			}
		});
		
		/* ******************** Tutorials *********************** */
		Button tutorial_btn = (Button) findViewById(R.id.tutorial_btn);
		tutorial_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				rotateImage(bot_turret, R.drawable.mainturret, R.id.bot_turret, 60);
				Intent engineIntent = new Intent(MainMenu.this, Tutorial_List.class);
				startActivity(engineIntent);
			}
		});
        
		/* ******************** Options *********************** */
        Button options_btn = (Button) findViewById(R.id.options_btn);
        options_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				rotateImage(bot_turret, R.drawable.mainturret, R.id.bot_turret, 120);
				//open interpreter for now
				Intent engineIntent = new Intent(MainMenu.this, Test.class);
				startActivity(engineIntent);
			}
		});
        
        /* ******************** Scores *********************** */
        Button scores_btn = (Button) findViewById(R.id.scores_btn);
        scores_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				rotateImage(bot_turret, R.drawable.mainturret, R.id.bot_turret, -60);
				Intent engineIntent = new Intent(MainMenu.this, MissionListActivity.class);
				startActivity(engineIntent);
				
			}
		});
        
        /* ******************** CodeBuilder *********************** */
        Button puzzle_btn = (Button) findViewById(R.id.puzzle_btn);
        puzzle_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				rotateImage(bot_turret, R.drawable.mainturret, R.id.bot_turret, -120);
				//open up IDE for now
				Intent engineIntent = new Intent(MainMenu.this, CodeBuilderActivity.class);
				startActivity(engineIntent);
			}
		});
        
        /* ******************** Exit *********************** */
        Button quit_btn = (Button) findViewById(R.id.quit_btn);
        quit_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				rotateImage(bot_turret, R.drawable.mainturret, R.id.bot_turret, 180);
				finish();
				System.runFinalizersOnExit(true);
				System.exit(0);
			}
		});    
    }
    
    public void rotateImage(ImageView img, int turret_id, int img_id, int rotate)
    {
    	img=(ImageView)findViewById(img_id);
    	Bitmap bmp = BitmapFactory.decodeResource(getResources(), turret_id);
    	// Getting width & height of the given image.
    	int w = bmp.getWidth();
    	int h = bmp.getHeight();
    	// Setting post rotate to 90
    	Matrix mtx = new Matrix();
    	mtx.postRotate(rotate);
    	// Rotating Bitmap
    	Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
    	BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);
    	img.setImageDrawable(bmd);
    }
}
