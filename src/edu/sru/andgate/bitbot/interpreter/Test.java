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
				vm.resume(1);
//				tv.setText(ps. toString());
			}
		});
		
		findViewById(R.id.notifyButton2).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				vm.resume(10);
//				tv.setText(ps. toString());
			}
		});
		
		findViewById(R.id.notifyButton3).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				vm.resume(1000);
//				tv.setText(ps. toString());
			}
		});
		
		
		
		// TODO: create some JUnit testing
		System.out.println("BotCode Parser:: ");
		
		vm = new InstructionLimitedVirtualMachine();
		
		// Set up a couple BotInterpreters
		BotInterpreter b1 = new BotInterpreter(null, code);
		vm.addInterpreter(b1);
		
//		BotInterpreter b2 = new BotInterpreter(null, code2);
//		vm.addInterpreter(b2);
		
		// Set up data output in textview
//		tv = (TextView) findViewById(R.id.txtMain);
//		b1.setPrintStream(new PrintStream(ps = new ByteArrayOutputStream(128)));
//		tv.setText(ps.toString());
		
	}
	
	
	
	/**
	 * Test Code
	 */
	String code =
	/*1*/	"Dim i as Integer;\n" +
	/*2*/	"Let i = 100;\n" +
	/*3*/	"While i Do\n" + 
	/*4*/	"Let b = 1*2*-3;\n" +
	/*5*/	"Let i = i-1;\n" + 
	/*6*/	"End\n"
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