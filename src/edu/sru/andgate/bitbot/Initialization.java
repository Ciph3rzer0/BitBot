/*
 * Temp class to do some initializations for testing
 */


package edu.sru.andgate.bitbot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import edu.sru.andgate.bitbot.graphics.GameActivity;
import edu.sru.andgate.bitbot.ide.CodeBuilderActivity;
import edu.sru.andgate.bitbot.ide.IDE;
import edu.sru.andgate.bitbot.ide.botbuilder.BotBuilderActivity;
import edu.sru.andgate.bitbot.interpreter.Test;
import edu.sru.andgate.bitbot.missionlist.MissionListActivity;
import edu.sru.andgate.bitbot.tools.ReadXML;
import edu.sru.andgate.bitbot.tutorial.Tutorial;
import edu.sru.andgate.bitbot.tutorial.Tutorial_List;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
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

public class Initialization {
       Bot b; 
       File mediaDir, f, f2;
       FileOutputStream fos, fos2;
    
       public Initialization(ContextWrapper cw)
       {
    	   ReadXML.setContext(cw.getBaseContext());
    	   
        b = new Bot();
        b.setName("Nick");
        b.setBase("square");
        b.setTurret("basic");
        b.setCode(
        	"Let d = -1\n" +
			"\n" +
			"While 1 Do\n" + 
			"  call bot_move(90, 5)\n" +
			"  \n" +
			"  Let d = d + 1\n" +
			"Loop\n");
        b.saveBotToXML(cw.getBaseContext(),"test_save.xml");
        
        String data = "";
        String data2 = cw.getResources().getString(R.string.example_code);
    
        cw = new ContextWrapper(cw.getBaseContext());
        mediaDir = cw.getDir("Code", Context.MODE_PRIVATE);
       
        if (mediaDir.exists()){
        	Log.v("Test", "Directory made");
        }else{
        	Log.v("Test", "Directory !exist");
        }
        	f = new File(cw.getDir("Code", Context.MODE_PRIVATE), "Program Code");
        	f2 = new File(cw.getDir("Code", Context.MODE_PRIVATE), "test code.txt");
        	try{
	        	f.createNewFile();
	        	f2.createNewFile();
	        	fos2 = new FileOutputStream(f2);
	        	fos = new FileOutputStream(f);
	        	fos.write(data.getBytes());
	        	fos2.write(data2.getBytes());
	        	fos2.close();
	        	fos.close();
        	}catch(Exception e){
        		Log.v("Test", "Error writing file");
        	}
    }
}
