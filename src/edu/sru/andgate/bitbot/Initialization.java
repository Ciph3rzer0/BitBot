/*
 * Class that initializes Sample code, Enemy BOTS, etc.
 */

package edu.sru.andgate.bitbot;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import edu.sru.andgate.bitbot.tools.FileManager;

public class Initialization
{
	Bot enemy;
	File mediaDir, f, f2;
	FileOutputStream fos, fos2;
	
	public Initialization(ContextWrapper cw)
	{
		FileManager.setContext(cw.getBaseContext());
			
		
		String sampleCode1 =
			"//SAMPLE CODE - Don't overwrite\n" +
			"Let _d = -5\n" +
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
			
		/*
		 * Creates an enemy bot(s) for later use
		 */
		enemy = new Bot();
		enemy.setName("Generic Enemy");
		enemy.setBase(R.drawable.adambot);
		enemy.setTurret(R.drawable.adamturret);
		enemy.setBullet(R.drawable.bulletnew);
		enemy.setCode(sampleCode1);
		enemy.saveBotToXML(cw, "enemy_bot.xml");
		
		
		//sampleCode1.substring(2);
		
		String data2 = "//SAMPLE CODE - Don't overwrite\n"; 
		data2 += cw.getResources().getString(R.string.example_code);
		//data2.substring(2);
		
		//initiallize directory where code files will be stored
		mediaDir = cw.getDir("Code", Context.MODE_PRIVATE);
	
		//initialize 2 sample files for user
		f = new File(cw.getDir("Code", Context.MODE_PRIVATE), "Sample Program Code 1.txt");
		f2 = new File(cw.getDir("Code", Context.MODE_PRIVATE), "Sample Program Code 2.txt");
		try
		{
			f.createNewFile();
			f2.createNewFile();
			fos2 = new FileOutputStream(f2);
			fos = new FileOutputStream(f);
			fos.write(sampleCode1.getBytes());
			fos2.write(data2.getBytes());
			fos2.close();
			fos.close();
		} catch (Exception e)
		{
			Log.v("Test", "Error writing file");
		}
	}
}
