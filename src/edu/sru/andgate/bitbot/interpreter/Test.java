package edu.sru.andgate.bitbot.interpreter;

import java.io.ByteArrayInputStream;

import android.app.Activity;
import android.os.Bundle;


import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.parser.*;

public class Test extends Activity
{
	SimpleNode root;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		System.out.println("BotCode Parser:: ");
		
		// TODO: create some JUnit testing
		String code1 = 
	/*1*/	"Dim var as Integer;\n" +
	/*2*/	"Dim a as Integer;\n" +
	/*3*/	"Let a = 3-1;\n" +
	/*4*/	"Let var = \"hello world\" & 1+(2+3+4-5)*a;\n" +
	/*5*/	"Let a = -a*3*4/5;\n" + 
	/*6*/	"Let a = 1*2*-3;\n" +
	/*7*/	"Let a = -1+2-(3+4)-5;\n" +
	/*8*/	"Print a;\n";
		
		String code = 
			/*1*/	"Dim var as Integer;\n" +
			/*2*/	"Dim a as Integer;\n" +
			/*3*/	"Let a = 3-1 < 4 == 2+3*2 <= 1 and 5+2;\n" +
			/*4*/	"Let a = 3 < 4 < 5;\n";
			
		// Parse using JavaCC and JJTree
		bc1 parser = new bc1(new ByteArrayInputStream(code.getBytes()));
		try
		{
			SimpleNode n = parser.Start();
			root = n;
			
			n.dump("");
			System.out.println("Thank you.");
		} catch (Exception e)
		{
			System.out.println("Oops.");
			System.out.println(e.getMessage());
		}
		
		
		// Lets examine our "compiled" code
		System.out.println(" ");
		System.out.println("My Dump AST");
		
		Dump(root);
		BC_AST_VM vm = new BC_AST_VM(root);
		vm.run();
		
		System.out.println(" ");
		System.out.println("Traversing AST");
	}
	
	
	
	
	
	private void Dump(Node s)
	{
		Dump(s, "");
	}
	
	private void Dump(Node s, String space)
	{
		
		System.out.println(space + s.toString() + ": " + ((SimpleNode)s).jjtGetValue());
		
		for(int i=0; i < s.jjtGetNumChildren(); i++)
			Dump(s.jjtGetChild(i), space + "- ");
	}
	
}