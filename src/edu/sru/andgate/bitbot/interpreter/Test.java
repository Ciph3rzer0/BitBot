package edu.sru.andgate.bitbot.interpreter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.parser.*;

public class Test extends Activity
{
	InstructionLimitedVirtualMachine vm;
	TextView tv;
	OutputStream ps;
	
	BotInterpreter b1;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testmain);
		
		// Set up the button to run the interpreters for 2 instructions.
		findViewById(R.id.notifyButton).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Resume(1);
			}
		});
		
		findViewById(R.id.notifyButton2).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Resume(10);
			}
		});
		
		findViewById(R.id.notifyButton3).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Resume(1000);
			}
		});
		
		tv = (TextView)findViewById(R.id.txtMain);
		TextView tvR = (TextView)findViewById(R.id.txtMainRight);
		
		
		// TODO: create some JUnit testing
		System.out.println("BotCode Parser:: ");
		
		vm = new InstructionLimitedVirtualMachine();
		
		String forLoop = 
			"Let i = 5\n" +
			"for n = 0 to i\n" +
			"  print n\n" + 
			"next\n"
			;
			
		
		
		
		// Set up a couple BotInterpreters
		b1 = new BotInterpreter(null, forLoop);
		b1.setOutputTextView(tv);
		vm.addInterpreter(b1);
		tvR.setText(forLoop);
		
//		BotInterpreter b2 = new BotInterpreter(null, code2);
//		b2.setOutputTextView(tvR);
//		vm.addInterpreter(b2);
		
		// Set up data output in textview
//		tv = (TextView) findViewById(R.id.txtMain);
//		b1.setPrintStream(new PrintStream(ps = new ByteArrayOutputStream(128)));
//		tv.setText(ps.toString());
		
	}
	
	private void Resume(int i)
	{
		vm.resume(i);
//		tv.setText(b1.getBotLog());
	}
	
	
	
	/**
	 * Test Code
	 */
	String code111 =
		/*1*/	"PRINT 1 AND 0\n" +
				"\n" +
		/*1*/	"PRINT 1 OR 0\n"
		;
		
		
	String code =
		/*1*/	"Print \"Hello World\" & \"1\" & \"2\"\n" +
		/*2*/	"Print \"Test \" & (5-1)\n" +
		/*3*/	"Dim i as Integer\n" +
		/*4*/	"Let i = 16\n" +
		/*5*/	"Print \"i = \" & i\n" +
		/*6*/	"While i Do\n" + 
		/*7*/	"  Print i\n" +
		/*8*/	"  IF i-1 THEN\n" +
		/*9*/	"    Print \"Not One! \" & i\n" +
		/*0*/	"  ELSE\n" +
		/*1*/	"    Print \"ONE!!!!!!!\"\n" +
		/*2*/	"  ENDIF\n" +
		/*3*/	"  Let i = i-1\n" + 
		/*4*/	"Loop\n"
	;
	
	
	String code11 = 
	/*1*/	"Let a = 3*4/5;\n" + 
	/*2*/	"Let b = 1*2*-3;\n" +
	/*3*/	"Let c = -1+2-(3+4)-5;\n" + 
	/*4*/	"Subroutine bot_mySub\n" +
	/*5*/	"Let d = (1+1)/2;\n" +
	/*6*/	"End\n" +
	/*7*/	"Let e = 5;\n" +
	/*8*/	"Call bot_mySub(1, 55);\n";
	
	
	
	String code1 = 
	/*1*/	"Dim var as Integer;\n" +
	/*2*/	"Dim a as Integer;\n" +
	/*3*/	"Let a = 3-1;\n" +
	/*4*/	"Let var = \"hello world\" & 1+(2+3+4-5)*a;\n" +
	/*5*/	"Let a = -a*3*4/5;\n" + 
	/*6*/	"Let a = 1*2*-3;\n" +
	/*7*/	"Let a = -1+2-(3+4)-5;\n" +
	/*8*/	"Print a;\n";
	
	String code3 = 
	/*1*/	"Dim var as Integer;\n" +
	/*2*/	"Dim a as Integer;\n" +
	/*3*/	"Let a = 3-1 < 4 == 2+3*2 <= 1 and 5+2;\n" +
	/*4*/	"Let a = 3 < 4 < 5;\n";
	
	String code2 = 
	/*1*/	"Let a = 1*2*-3;\n" +
	/*2*/	"Call robot_fire(one, two, three);\n" +
	/*3*/	"" +
	/*4*/	"" +
	/*5*/	"";
	
	
}