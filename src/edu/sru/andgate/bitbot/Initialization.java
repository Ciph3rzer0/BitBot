/*
 * Temp class to do some initializations for testing
 */

package edu.sru.andgate.bitbot;

import java.io.File;
import java.io.FileOutputStream;

import edu.sru.andgate.bitbot.interpreter.Test;
import edu.sru.andgate.bitbot.tools.FileManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

public class Initialization
{
	Bot enemy;
	File mediaDir, f, f2;
	FileOutputStream fos, fos2;
	
	public Initialization(ContextWrapper cw)
	{
		FileManager.setContext(cw.getBaseContext());
			
		
		String data =
			"// See if onBoundaryCollision works!\n" +
			"Let _d = -3\n" +
			"\n" +
			"While 1 Do\n" + 
			"  call bot_move(90, _d)\n" +
			"  \n" +
			"  Let i = i + 1\n" +
			"Loop\n" +
			"\n" +
			"End\n" +
			"\n" +
			"sub onBoundaryCollision\n" +
			"	Let _d = _d * -1\n" +
			"	\n" +
			"return\n";
		
		String data02 =
			"//Place Holder File\n" + "Let d = -1\n" +
			"\n" +
			"While 1 Do\n" + 
			"  call bot_move(45, 5)\n" +
			"  Let g = g + 1\n" +
			"Loop\n";
		
		boolean b = Test.CompareCode(data, data02);
		
		if (b)
			Log.v("BitBot INIT", "Sources are equal");
		else
			Log.v("BitBot INIT", "Sources are not equal");
		
		enemy = new Bot();
		enemy.setName("Generic Enemy");
		enemy.setBase(R.drawable.adambot);
		enemy.setTurret(R.drawable.adamturret);
		enemy.setBullet(R.drawable.bulletnew);
		enemy.setCode(data);
		enemy.saveBotToXML(cw, "enemy_bot.xml");
		
		data.substring(2);
		String data2 = "//Simple While Loop\n"; 
		data2 += cw.getResources().getString(R.string.example_code);
		data2.substring(2);
		// Is this necessary?
		// cw = new ContextWrapper(cw.getBaseContext());
		mediaDir = cw.getDir("Code", Context.MODE_PRIVATE);
		
		if (mediaDir.exists())
		{
			Log.v("Test", "Directory made");
		} else
		{
			Log.v("Test", "Directory !exist");
		}
		f = new File(cw.getDir("Code", Context.MODE_PRIVATE), "Program Code.txt");
		f2 = new File(cw.getDir("Code", Context.MODE_PRIVATE), "test code.txt");
		
		try
		{
			f.createNewFile();
			f2.createNewFile();
			fos2 = new FileOutputStream(f2);
			fos = new FileOutputStream(f);
			fos.write(data.getBytes());
			fos2.write(data2.getBytes());
			fos2.close();
			fos.close();
		} catch (Exception e)
		{
			Log.v("Test", "Error writing file");
		}
	}
}
