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
		
		String wildMan =
			"// rnd\n" +
			"\n" + 
			"for I 1 to 1000\n" +
			"let a = call int(call rnd() * 360)\n" +
			"let b = call int(call rnd() * 360)\n" +
			"\n" + 
			"for j 1 to 20\n" +
			"call bot_move(a, 10)\n" +
			"call bot_turn(b)\n" +
			"\n" + 
			"if j%5 == 0 then\n" +
			"call bot_fire()\n" +
			"endif\n" +
			"next\n" +
			"\n" + 
			"next\n" +
			"\n" + 
			"call bot_move(0,0)\n"
			;
		
		String squareDawg = 
			"// Square\n" + 
			"\n" + 
			"let _backup 0\n" + 
			"let a 270\n" + 
			"\n" + 
			"while 1 do\n" + 
			"call bot_move(a, 15)\n" + 
			"call bot_fire()\n" + 
			"\n" + 
			"if _backup then\n" + 
			"for I 1 to 16\n" + 
			"call bot_move(a, -14)\n" + 
			"next\n" + 
			"\n" + 
			"let _backup 0\n" + 
			"\n" + 
			"let b call int(call rnd() * 2)\n" + 
			"if b then\n" + 
			"let a = a + 90\n" + 
			"else\n" + 
			"let a = a - 90\n" + 
			"endif\n" + 
			"\n" + 
			"let _a = a\n" + 
			"endif\n" + 
			"\n" + 
			"loop\n" + 
			"\n" + 
			"end\n" + 
			"\n" + 
			"sub onBoundaryCollision\n" + 
			"let _backup 1\n" + 
			"call bot_turn(180 + _a)\n" + 
			"return\n"
			;
		
		String buzzsaw = 
			"// Patrol the perimeter and shout towards the inside\n" + 
			"\n" + 
			"let _a = 20\n" + 
			"\n" + 
			"while 1 do\n" +  
			"call bot_move(_a, 15)\n" + 
			"let r call rnd() * 90 - 45\n" + 
			"\n" + 
			"for j 1 to 10\n" + 
			"call bot_turn(_a + 90 + r)\n" + 
			"next\n" + 
			"\n" + 
			"call bot_fire()\n" + 
			"loop\n" + 
			"\n" + 
			"end\n" + 
			"\n" + 
			"sub onTouch with x, y\n" +  
			"let _a = call angleTo(x, y)\n" +  
			"call bot_turn(_a)\n" + 
			"call bot_fire()\n" + 
			"return\n" + 
			"\n" + 
			"sub onBoundaryCollision\n" + 
			"let _a = _a + 90\n" + 
			"return\n"
			;
		
		String wanderer =
			"// last\n" + 
			"\n" + 
			"while 1 do\n" + 
			"\n" + 
			"let lastx call bot_x()\n" + 
			"let lasty call bot_y()\n" + 
			"\n" + 
			"for I 1 to 4\n" + 
			"call bot_move(_a, 20)\n" + 
			"next\n" + 
			"\n" + 
			"let dx lastx - call bot_x()\n" + 
			"let dy lasty - call bot_y()\n" + 
			"let _a call angleTo(dx, dy) + 180\n" + 
			"Let r = call rnd() * 20 - 10\n" + 
			"Let _a = _a + r\n" + 
			"\n" + 
			"call shoot()\n" + 
			"\n" + 
			"loop\n" + 
			"end\n" + 
			"\n" + 
			"sub onBoundaryCollision\n" + 
			"let _a call rnd()*360\n" + 
			"return\n" + 
			"\n" + 
			"sub shoot\n" + 
			"call bot_turn(_a)\n" + 
			"call bot_fire()\n" + 
			"call bot_turn(_a + 180)\n" + 
			"call bot_fire()\n" + 
			"call bot_turn(_a - 90)\n" + 
			"call bot_fire()\n" + 
			"call bot_turn(_a + 90)\n" + 
			"call bot_fire()\n" + 
			"call bot_turn(_a)\n" + 
			"return\n"
			;
		
		/*
		 * Creates an enemy bot(s) for later use
		 */
		enemy = new Bot();
		enemy.setName("Generic Enemy");
		enemy.setBase(R.drawable.adambot);
		enemy.setTurret(R.drawable.adamturret);
		enemy.setBullet(R.drawable.bulletnew);
		

		enemy.setName("Wild Man");
		enemy.setCode(wildMan);
		enemy.saveBotToXML(cw, "wildman.xml");

		enemy.setName("Square Dawn");
		enemy.setCode(squareDawg);
		enemy.saveBotToXML(cw, "squaredawg.xml");

		enemy.setName("Buzzsaw");
		enemy.setCode(buzzsaw);
		enemy.saveBotToXML(cw, "buzzsaw.xml");

		enemy.setName("Wanderer");
		enemy.setCode(wanderer);
		enemy.saveBotToXML(cw, "wanderer.xml");
		
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
