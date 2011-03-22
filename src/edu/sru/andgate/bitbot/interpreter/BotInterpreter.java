package edu.sru.andgate.bitbot.interpreter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PrintStream;

import android.util.Log;
import edu.sru.andgate.bitbot.graphics.Bot;
import edu.sru.andgate.bitbot.parser.Node;
import edu.sru.andgate.bitbot.parser.SimpleNode;
import edu.sru.andgate.bitbot.parser.bc1;

public class BotInterpreter
{
	private RunThread rThread = null;
	
	/**
	 * This is the root node of an AST generated from the source
	 * of the botcode program to be executed.
	 */
	private SimpleNode root;
	
	/**
	 * This is essentially the "Program Counter".  
	 * It traverses the AST executing instructions.
	 */
	private RunVisitor rv;
	
	/**
	 * This is the object we manipulate to cause changes in accordance with
	 * the interpreted instructions.
	 */
	private Bot bot;
	
	
	private PipedInputStream botOutput;
	private StringBuffer botLog;
	
	public String getBotLog()
	{
		return botLog.toString();
	}
	
	
	// TODO: Dumb, Hack
	public void setPrintStream(PrintStream p)
	{
		rv.setPrintStream(p);
	}
	
	
	/**
	 * Create a BotInterpreter given code as a String.
	 * @param code the source code to execute.
	 */
	public BotInterpreter(Bot b, String code)
	{
		this( b, new ByteArrayInputStream(code.getBytes()) );
	}
	
	/**
	 * Create a BotInterpreter given code as a ByteArrayInputStream.
	 * @param code the source code to execute.
	 */
	public BotInterpreter(Bot b, ByteArrayInputStream isCode)
	{
		// Assign the bot
		this.bot = b;
		
		// Create the parser.
		bc1 parser = new bc1(isCode);
		
		// The root node of the AST generated from the isCode
		SimpleNode rootNode = null;
		
		try
		{
			rootNode = parser.Start();
		}
		catch (Exception e)
		{
			System.out.println("Oops.");
			System.out.println(e.getMessage());
		}
		
		root = rootNode;
		
		Dump(root);
		
		if (root == null)
			Log.e("BitBot Interpreter", "Error: The code did not compile correctly");
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
	
	
	/**
	 * Create a BotInterpreter given an AST to execute.
	 * @param root The root node of the AST this Interpreter should execute.
	 */
	public BotInterpreter(Bot b, SimpleNode root)
	{
		this.bot = b;
		this.root = root;			// Store the root of the AST to execute.
		
		if (root == null)
			Log.e("BitBot Interpreter", "Error: The code did not compile correctly. root is null.");		
	}
	
	/**
	 * Create a new thread to execute the AST, and begin executing (0 instructions).
	 */
	public void start()
	{
		if (root == null)
			Log.e("BitBot Interpreter", "Error: Cannot start().  root is null.");
		else
		{
			if (rThread == null)
			{
				rThread = new RunThread(this);
				rThread.start();
			}
			else
				Log.e("BitBot Interpreter", "ERROR in BotInterpreter.start(), rThread not null.");
		}
	}
	
	/**
	 * Execute a specific number of instructions.
	 * @param numberOfInstructionsToExecute Number of instructions to execute.
	 */
	public void resume(int numberOfInstructionsToExecute)
	{
		try
		{
			int b;
			
			while (botOutput.available() > 1)
			{
				b = botOutput.read();
				botLog.append((char)b);
//				Log.w("botLog", botLog.toString());
			}
			Log.i("botLog", botLog.toString());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (rv != null)
			rv.resume(numberOfInstructionsToExecute);
		else
			Log.e("BitBot Interpreter", "RunVisitor was null in BotInterpreter.resume()");
	}
	
	
	public boolean executeBotInstruction(String instr, String[] params)
	{
		System.out.println("Executing bot instruction " + instr);
		
		for (int i=0; i<params.length; i++)
			System.out.println("param[" + i + "] = " + params[i]);
		
		
		if (instr.equalsIgnoreCase("bot_move"))
		{
			return true;
		}
		else if (instr.equalsIgnoreCase("bot_turn"))
		{
			return true;
		}
		else if (instr.equalsIgnoreCase("bot_fire"))
		{
			return true;
		}
		else
			Log.w("BitBot Interpreter", "Instruction not valid" + instr);
		
		return false;
	}
	
	
	
	
	/**
	 * A thread that starts executing on the root node.
	 * @author Josh
	 *
	 */
	private class RunThread extends Thread
	{
		/**
		 * Check to make sure we only instantiate this thread once per BotInterpreter.
		 */
		private RunThread(BotInterpreter bi)
		{
			if (rv == null)
			{
				rv = new RunVisitor(bi);
				
				try
				{
					botLog = new StringBuffer();
					botOutput = new PipedInputStream(rv.getStdOut());
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else
				Log.e("BitBot Interpreter", "Error: two RunThreads running in BotInterpreter.");
		}
		
		/**
		 * Cause the RunVisitor to visit the first node in the AST and traverse it until it
		 *  comes to an instruction to execute.  It then suspends and waits to resume().
		 */
		public void run()
		{
			Log.i("BitBot Interpreter", "Thread '" + this.getName() + "' has started.");
			
			// Send the visitor to the first node.
			root.jjtAccept(rv, null);
			
			Log.i("BitBot Interpreter", "Thread '" + this.getName() + "' has finished.");
		}
	}
	
	
	
	
	/**
	 * Determine if this BotInterpreter's thread is waiting or not.
	 * @return true if this thread is waiting.
	 */
	public synchronized boolean isWaiting()
	{
		return rv.isWaiting();
	}
	
}




