package edu.sru.andgate.bitbot.interpreter;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.parser.Node;
import edu.sru.andgate.bitbot.parser.SimpleNode;
import edu.sru.andgate.bitbot.parser.bc1;

public class Test extends Activity
{
	public static final String LOG = "BitBot AST Compare";
	
	InstructionLimitedVirtualMachine vm;
	TextView tv;
	OutputStream ps;
	
	BotInterpreter b1;
	
	/**
	 * Compares the AST of both source code files.  By default, <code>precise,</code> mode
	 * is enabled, meaning every variable and value must be the same.
	 *  
	 * @param s1 one source file
	 * @param s2 another source file
	 * @return true if the method determines them to be equal.
	 */
	public static boolean CompareCode(String s1, String s2)
	{
		return CompareCode(s1, s2, true);
	}
	
	/**
	 * Compares the AST of both source code files.  When <code>precise,</code> mode
	 * is enabled, every variable and value must be the same.  If precise is false, variable
	 * and constant substitutions are allowed.
	 * 
	 * Other things such as \< or \> will also be interchangeable if precise mode is disabled.
	 *  
	 * @param s1 one source file
	 * @param s2 another source file
	 * @param precise checks value and variable names if true.
	 * @return true if the method determines them to be equal.
	 */
	public static boolean CompareCode(String s1, String s2, boolean precise)
	{
		boolean result = false;
		Node n1 = null, n2 = null;
		
		// Parse the first file
		try
		{
			n1 = ( new bc1(new ByteArrayInputStream(s1.getBytes())) ).Start();
		}
		catch(Exception e)
		{
			Log.e(LOG, "Error parsing first source file\n" + e.getStackTrace());
		}
		
		// Parse the second file
		try
		{
			n2 = ( new bc1(new ByteArrayInputStream(s2.getBytes())) ).Start();
		}
		catch(Exception e)
		{
			Log.e(LOG, "Error parsing second source file\n" + e.getStackTrace());
		}
		
//		Dump(n1);	Dump(n2);
		
		// Run the compare
		if (precise)
		{
			if (compareNodePrecise(n1, n2))
				result = true;
		}
		else
			if (compareNodeLoose(n1, n2))
				result = true;
		
		return result;
	}
	
	/**
	 * Recursively checks node's class types to determine equality.
	 * 
	 * @param n one node to check
	 * @param o another node to check
	 * @return true if class types are equal
	 */
	private static boolean compareNodeLoose(Node n, Node o)
	{
		if (n == null || o == null)
			return false;
		
		try
		{
			if ( (n.jjtGetNumChildren() == o.jjtGetNumChildren()) == false )
				throw new ArrayIndexOutOfBoundsException();
			
			for (int i = 0; i < n.jjtGetNumChildren(); i++)
			{
				String sn = n.getClass().getSimpleName();
				String on = o.getClass().getSimpleName();
				
				if (!sn.equalsIgnoreCase(on))
				{
					Log.w(LOG, sn + " != " + on);
					return false;
				}
				
				if(compareNodeLoose(n.jjtGetChild(i), o.jjtGetChild(i)) == false)
					return false;
			}
			
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			Log.w(LOG, "Number of nodes is not the same");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Recursively checks node's class types and values to determine equality.
	 * 
	 * @param n one node to check
	 * @param o another node to check
	 * @return true if class types and values are equal
	 */
	private static boolean compareNodePrecise(Node n, Node o)
	{
		if (n == null || o == null)
			return false;
	
		try
		{
			if ( (n.jjtGetNumChildren() == o.jjtGetNumChildren()) == false )
				throw new ArrayIndexOutOfBoundsException();
			
			// Test class names
			String sn = n.getClass().getSimpleName();
			String so = o.getClass().getSimpleName();
			
			if (!sn.equals(so))
			{
				Log.w(LOG, sn + " != " + so);
				return false;
			}
			
			// Test Values
			String vn = ((SimpleNode)n).jjtGetValue() + "";
			String vo = ((SimpleNode)o).jjtGetValue() + "";
			
			Log.w(LOG, vn + " && " + vo);
			if (!vn.equalsIgnoreCase(vo))
			{
				Log.w(LOG, vn + " != " + vo);
				return false;
			}
			
			// Recursively check children
			for (int i = 0; i < n.jjtGetNumChildren(); i++)
				if(compareNodePrecise(n.jjtGetChild(i), o.jjtGetChild(i)) == false)
					return false;
			
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			Log.w(LOG, "Number of nodes is not the same");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Creates a textual representation of the AST starting at node <code>s</code>.
	 * @param s the node to dump
	 */
	public static void Dump(Node s)
	{
		Dump(s, "");
	}
	
	/**
	 * Creates a textual representation of the AST starting at node <code>s</code> with
	 * <code>prefix</code> in front of it.
	 * <p>
	 * This code is recursively called to put <code>"- "</code> on the line a number of
	 * times equal to the depth of the node.
	 * @param s the node to dump
	 * @param prefix the string to prefix to all output at this depth
	 */
	public static void Dump(Node s, String prefix)
	{
		
		System.out.println(prefix + s.toString() + ": " + ((SimpleNode)s).jjtGetValue());
		
		for(int i=0; i < s.jjtGetNumChildren(); i++)
			Dump(s.jjtGetChild(i), prefix + "- ");
	}
	
	
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
			"call sub1()\n" +
			"\n" +
			"Let i = 5\n" +
			"for n = 0 to i\n" +
			"  print n\n" + 
			"next\n" +
			"\n" +
			"end\n" +
			"\n" +
			"sub sub1\n" +
			"  print \"In sub1\"\n" +
			"return\n" +			
			"\n"
			;
			
//		boolean j = 3==4==5;
//		
	
		// Set up a couple BotInterpreters
		b1 = new BotInterpreter(null, forLoop);
		b1.setOutputTextView(tv);
		vm.addInterpreter(b1);
		tvR.setText(forLoop);
		
		/*
		BotInterpreter b2 = new BotInterpreter(null, code2);
		b2.setOutputTextView(tvR);
		vm.addInterpreter(b2);
		
		// Set up data output in textview
		tv = (TextView) findViewById(R.id.txtMain);
		b1.setPrintStream(new PrintStream(ps = new ByteArrayOutputStream(128)));
		tv.setText(ps.toString());
		*/
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