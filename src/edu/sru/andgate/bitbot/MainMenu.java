package edu.sru.andgate.bitbot;

import edu.sru.andgate.bitbot.customdialogs.TutorialDialog;
import edu.sru.andgate.bitbot.graphics.GameActivity;
import edu.sru.andgate.bitbot.ide.CodeBuilderActivity;
import edu.sru.andgate.bitbot.interpreter.Test;
import edu.sru.andgate.bitbot.missionlist.MissionListActivity;
import edu.sru.andgate.bitbot.tools.Constants;
import edu.sru.andgate.bitbot.tools.FileManager;
import edu.sru.andgate.bitbot.tutorial.BotBasic_Tutorial_List;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenu extends Activity {
	Initialization init;
	ContextWrapper cw;
	Dialog helpDialog;
	TextView help_text;
	ImageView bot_turret;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        //Initialize some things: code text docs, saved bot
        cw = new ContextWrapper(getBaseContext());
        init = new Initialization(cw);
        
        helpDialog = new Dialog(this, R.style.CustomDialogTheme);
        helpDialog.setContentView(R.layout.floating_help);
        help_text = (TextView) helpDialog.findViewById(R.id.help_view);
        FileManager.setContext(getBaseContext());
        help_text.setText(FileManager.readAssetsXML("help.xml", "text"));

        bot_turret = (ImageView) findViewById(R.id.bot_turret);
                
        /* ******************** Start Game *********************** */
        Button game_modes = (Button) findViewById(R.id.game_modes);
		game_modes.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				rotateImage(bot_turret, R.drawable.mainturret, R.id.bot_turret, 0);
				Intent engineIntent = new Intent(MainMenu.this, MissionListActivity.class);
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
				/*Intent engineIntent = new Intent(MainMenu.this, Tutorial_List.class);
				startActivity(engineIntent);*/
				TutorialDialog td = new TutorialDialog(MainMenu.this, MainMenu.this, R.style.CustomDialogTheme);
				td.show();
			}
		});
        
		/* ******************** Options *********************** */
        Button help_btn = (Button) findViewById(R.id.help_btn);
        help_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				rotateImage(bot_turret, R.drawable.mainturret, R.id.bot_turret, 120);
				helpDialog.show();
			}
		});
        
        /* ******************** Scores *********************** */
        Button about_btn = (Button) findViewById(R.id.scores_btn);
        about_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				rotateImage(bot_turret, R.drawable.mainturret, R.id.bot_turret, -60);				
			}
		});
        
        /* ******************** CodeBuilder *********************** */
        Button ide_btn = (Button) findViewById(R.id.ide_btn);
        ide_btn.setOnClickListener(new View.OnClickListener() 
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
				//System.runFinalizersOnExit(true);
				//System.exit(0);
			}
		}); 
               
        /*
         * temp help button
         */
        Button interpreter_btn = (Button)findViewById(R.id.interpreter_btn);
        interpreter_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//open interpreter for now
				Intent engineIntent = new Intent(MainMenu.this, Test.class);
				startActivity(engineIntent);
			}
		});
        
        /*
         * temp graphics button
         */
        Button graphics_btn = (Button) findViewById(R.id.graphics_btn);
        graphics_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent engineIntent = new Intent(MainMenu.this, GameActivity.class);
				startActivity(engineIntent);
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
