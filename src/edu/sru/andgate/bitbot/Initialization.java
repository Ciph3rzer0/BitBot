/*
 * Temp class to do some initializations for testing
 */

package edu.sru.andgate.bitbot;

import java.io.File;
import java.io.FileOutputStream;
import edu.sru.andgate.bitbot.tools.FileManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

public class Initialization
{
	Bot b;
	File mediaDir, f, f2;
	FileOutputStream fos, fos2;
	
	public Initialization(ContextWrapper cw)
	{
		FileManager.setContext(cw.getBaseContext());
		
		b = new Bot();
		b.setName("Nick");
		b.setBase("square");
		b.setTurret("basic");
		b.setCode(
			"Let d = -1\n" +
			"\n" + "While 1 Do\n" +
			"  call bot_move(90, 5)\n" +
			"  \n" +
			"  Let d = d + 1\n" +
			"Loop\n"
		);
		
		b.saveBotToXML(cw.getBaseContext(), "test_save.xml");
		
		String data = "";
		String data2 = cw.getResources().getString(R.string.example_code);
		
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
		f = new File(cw.getDir("Code", Context.MODE_PRIVATE), "Program Code");
		
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
