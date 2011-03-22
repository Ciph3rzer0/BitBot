package edu.sru.andgate.bitbot.interpreter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;

import android.util.Log;

import edu.sru.andgate.bitbot.parser.*;

/**
 * This Visitor implementation handles traversing the AST generated by the bc1
 * interpreter.  The various visit() methods handle each type of node that shows
 * up in a bc1 AST.  Some operations count as instructions.  Those that count
 * as instructions cause the private instructionsLeft variable to be decremented
 * and if it becomes less than or equal to zero, the thread pauses.
 * <p>
 * You start this visitor by:
 * <p><blockquote><pre>
 * {@code
 * SimpleNode node;
 * RunVisitor rv;
 * ...
 * node.jjtAccept(rv);
 * }
 * </pre></blockquote>
 * 
 * Note that instructionsLeft starts at zero, so the first real instruction will cause the 
 * thread to wait.  You must then use {@code rv.resume(n);} to make the visitor execute n instructions.
 * 
 * @author Josh
 */
public class RunVisitor implements bc1Visitor
{
	private HashMap<String, String> vars = new HashMap<String, String>();
	private int instructionsLeft = 0;
	private boolean _waiting = true;
	
	private PipedOutputStream $std_out = new PipedOutputStream();
	private PrintStream std_out = new PrintStream($std_out, true);
	
	private PipedInputStream $std_in = new PipedInputStream();
	
	private BotInterpreter bi;
	
	
	
	private PrintStream out = new PrintStream( 
		new OutputStream()
		{
			@Override
			public void write(int oneByte) throws IOException
			{
				System.out.write(oneByte);
			}
		}
	);
	
	public boolean setPrintStream(PrintStream p)
	{
		if (p != null)
		{
			out = p;
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Creates a RunVisitor that interacts with the BotInterpreter <code>bi</code>.
	 * Actually, as of now it does nothing.
	 * @param bi
	 */
	public RunVisitor(BotInterpreter bi)
	{
		this.bi = bi;
	}
	
	/**
	 * Determine if main execution thread is waiting.
	 * @return true if execution thread is waiting.
	 */
	public boolean isWaiting()
	{
		return _waiting;
	}
	
	/**
	 * Decrements the instructionsLeft and if <= 0 makes the thread wait.
	 */
	private void NextInstruction()
	{
		// If we have less than 1 instruction left, we need to wait until we're
		// allowed to do more processing.
		if (instructionsLeft <= 0)
		{
			try
			{
				synchronized (this)
				{
					_waiting = true;					// Update the waiting status.
					this.wait();						// Suspend the thread.
				}
			}
			catch (InterruptedException e)
			{
				Log.e("BitBot Interpreter", "Thread was interrupted.");
				Log.e("BitBot Interpreter", Log.getStackTraceString(e));
			}
		}
		
		instructionsLeft--;								// We used one instruction.
	}
	
	/**
	 * Cause the interpreters thread to resume.  Executing a set number of instructions
	 * before waiting to be resumed again.
	 * @param numberOfInstructions The number of instructions to run.
	 */
	public void resume(int numberOfInstructions)
	{
		instructionsLeft = numberOfInstructions;		// Reset the number of instructions left
		
		synchronized (this)
		{
			this.notify();								// Resume the Interpreting thread.
			_waiting = false;							// Update the waiting status.
		}
	}
	
	/**
	 * This is the standard output of the interpreter.  Attach it wherever needed.
	 * @return the standard output of the interpreter.
	 */
	public PipedOutputStream getStdOut()
	{
		return $std_out;
	}
	
	/**
	 * Set the standard input of the interpreter.  Pass in input from the keyboard, or whatever.
	 * @param in the standard input of the interpreter.
	 */
	public void setStdIn(PipedInputStream in)
	{
		$std_in = in;
	}
	
	
	
	//***************************************************
	//*  				 ROOT CLASSES 					*
	//***************************************************
	@Override
	public Object visit(SimpleNode node, Object data)
	{
		// This should never actually get called...
		out.println("[===SimpleNode===] ERR: should not print. ");
		return null;
	}
	@Override
	public Object visit(ASTRoot node, Object data)
	{
		out.println(" ");
		out.println("[--- Root ---]");
		
		// Visit Program
		node.jjtGetChild(0).jjtAccept(this, null);
		
		return null;
	}
	@Override
	public Object visit(ASTProgram node, Object data)
	{
		out.println("[= Program =]");
		
		// ListOfInstructions
		node.jjtGetChild(0).jjtAccept(this, null);
		
		return null;
	}

	//***************************************************
	//*  				INSTRUCTIONS					*
	//***************************************************
	@Override
	public Object visit(ASTPrint node, Object data)
	{
		NextInstruction();
		
		out.println( "[PRINT] " + node.jjtGetChild(0).jjtAccept(this, null).toString() );
		std_out.println(node.jjtGetChild(0).jjtAccept(this, null).toString() );
		
		return null;
	}
	@Override
	public Object visit(ASTDeclaration node, Object data)
	{
		//NextInstruction();
		
		String id = ((SimpleNode)node.jjtGetChild(0)).jjtGetValue().toString();
		String value = "0";
		
		vars.put(id, value);
		
//		out.println("[Declaration] " + id + " = "  + value);
		return null;
	}
	@Override
	public Object visit(ASTAssignment node, Object data)
	{
		NextInstruction();
		
		// ID is first child
		String id = ((SimpleNode)node.jjtGetChild(0)).jjtGetValue().toString();
		// Value is second child
		String value = node.jjtGetChild(1).jjtAccept(this, null).toString();
		
		// Update the variable
		vars.put(id, value);
		
//		out.println("[/Assignment: " + id + "=" + value + "]");
		
		return value;
	}

	
	//***************************************************
	//*  				   STRINGS 						*
	//***************************************************
	@Override
	public Object visit(ASTConcatExpression node, Object data)
	{
//		out.println("StringExpression");
		
		// Determine the operation
		String op = node.jjtGetValue().toString();
		
		// Visit this nodes children to find out the two operands (v1 and v2)
		String v1 = "", v2 = "";
		try
		{
			v1 = node.jjtGetChild(0).jjtAccept(this, null).toString();
			v2 = node.jjtGetChild(1).jjtAccept(this, null).toString();
		}
		catch(Exception e){}
		
		// Evaluate
		String result = ":err:";
		if (op.equalsIgnoreCase("&"))
			result = v1 + v2;
		else 
			Log.e("BitBot Interpreter", "Unknown Operation: '" + op + "'");

		// Log
//		out.println(v1 + " " + op + " " + v2 + " -> " + result);
		
		return result;
	}

	
	
	//***************************************************
	//*  				IDENTIFIERS						*
	//***************************************************
	@Override
	public Object visit(ASTIdentifier node, Object data)
	{
		String key = node.jjtGetValue().toString();
		String result = vars.get(key);

//		out.println(key + ":" + result);
		
		return result;
	}
	
	//***************************************************
	//*  				  LITERALS 						*
	//***************************************************
	@Override
	public Object visit(ASTIntegerLiteral node, Object data)
	{
		//out.println("(" + node.jjtGetValue().toString() + ")");
		return node.jjtGetValue().toString();
	}
	
	@Override
	public Object visit(ASTStringLiteral node, Object data)
	{
		String result = node.jjtGetValue().toString();
		result = result.substring(1, result.length()-1);
		
//		out.println("StringLiteral: " + result);
		return result;
	}
	
	//***************************************************
	//*  				EXPRESSIONS						*
	//***************************************************
	@Override
	public Object visit(ASTBooleanExpression node, Object data)
	{
//		out.println("BooleanExpression");
		// TODO actually return something
		return "0";
	}
	
	@Override
	public Object visit(ASTEqualityExpression node, Object data)
	{
//		out.println("EqualityExpression");
		// TODO actually return something
		return "0";
	}
	
	@Override
	public Object visit(ASTRelationalExpression node, Object data)
	{
//		out.println("RelationalExpression");
		// TODO actually return something
		return "0";
	}
	
	@Override
	public Object visit(ASTAdditiveExpression node, Object data)
	{
		NextInstruction();
		
		// Determine the operation
		String op = node.jjtGetValue().toString();
		
		//out.println("AdditiveExpression: " + op);
		
		// Visit this nodes children to find out the two operands (v1 and v2)
		int v1 = 0, v2 = 0;
		try
		{
			v1 = Integer.parseInt(node.jjtGetChild(0).jjtAccept(this, null).toString());
			v2 = Integer.parseInt(node.jjtGetChild(1).jjtAccept(this, null).toString());
		}
		catch(Exception e){}
		
		// Evaluate
		int result = 0;
		if (op.equalsIgnoreCase("+"))			result = v1 + v2;
		else if (op.equalsIgnoreCase("-"))		result = v1 - v2;
		else 
			Log.e("BitBot Interpreter", "Unknown Operation: '" + op + "'");

		// Log
//		out.println(v1 + " " + op + " " + v2 + " -> " + result);
		
		return result;
	}
	
	@Override
	public Object visit(ASTMultiplicativeExpression node, Object data)
	{
		NextInstruction();
		
		// Determine the operation
		String op = node.jjtGetValue().toString();
		
		// Output for debug
		//out.println("MultiplicativeExpression: " + op);
		
		// Visit this nodes children to find out the two operands (v1 and v2)
		int v1 = 0, v2 = 0;
		try
		{
			v1 = Integer.parseInt(node.jjtGetChild(0).jjtAccept(this, null).toString());
			v2 = Integer.parseInt(node.jjtGetChild(1).jjtAccept(this, null).toString());
		}
		catch(Exception e){}
		
		// Evaluate
		int result = 0;
		if (op.equalsIgnoreCase("*"))			result = v1 * v2;
		else if (op.equalsIgnoreCase("/"))		result = v1 / v2;
		else 
			Log.e("BitBot Interpreter", "Unknown Operation: '" + op + "'");
		
		// Log
//		out.println(v1 + " " + op + " " + v2 + " -> " + result);
		
		return result;
	}
	
	@Override
	public Object visit(ASTUnaryExpression node, Object data)
	{
		NextInstruction();
		
		// Get operation
		String op = node.jjtGetValue().toString();
		// Get operand
		int v1 = Integer.parseInt(node.jjtGetChild(0).jjtAccept(this, null).toString());
		
		// Log
//		out.println("UnaryExpression: " + op);
//		out.println(op + v1);
		
		// Negate if necessary
		if (op.equals("-")) return -v1;
		// else, return the same value
		return v1;
	}
	
	@Override
	public Object visit(ASTSubCall node, Object data)
	{
//		String instr = ((SimpleNode)node.jjtGetChild(0).jjtAccept(this, null)).jjtGetValue().toString();
		String instr = ((SimpleNode)node.jjtGetChild(0)).jjtGetValue().toString();
		
		out.println("Call Subroutine:  " + instr);
//		node.jjtGetChild(0).jjtAccept(this, null);
		
		int numP = node.jjtGetNumChildren() - 1;
		String[] params = null;
		if (numP > 0)
		{
			params = new String[numP];
			
			// Parameters start at 1, id is first.
			for (int i=1; i<node.jjtGetNumChildren(); i++)
				params[i-1] = (String) node.jjtGetChild(i).jjtAccept(this, null);
			
			// Print them out temporarily
			for (int i=0; i<params.length; i++)
				out.println(params[i]);
		}
		
		// Execute bot instructions
		// TODO: check what this string is
		Log.e("RV", instr.substring(0, 4));
		if ( instr.substring(0, 4).equalsIgnoreCase("bot_") )
		{
			Log.w("RV", "call began with 'bot_'");
			bi.executeBotInstruction(instr, params);
		}
		else
			Log.w("RV", "call did not begin with 'bot_'");
		
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(ASTSubDeclaration node, Object data)
	{
		// Identifier
		String name = node.jjtGetChild(0).jjtAccept(this, null).toString();
		out.println("Sub Declaration: " + name);
		
		// ListOfInstructions
		node.jjtGetChild(1).jjtAccept(this, null);
		
		out.println("End Sub Declaration:");
		
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public Object visit(ASTWhileLoop node, Object data)
	{
		out.println("[Begin While Loop]");
		
		final long startTime = System.nanoTime();
		final long endTime;
		try {
			// Loop while conditional is not false.  (0 == false, 1 == true)
			while ( Integer.parseInt(node.jjtGetChild(0).jjtAccept(this, null).toString()) != 0)
				node.jjtGetChild(1).jjtAccept(this, null);
		
		} finally {
		  endTime = System.nanoTime();
		}
		final long duration = endTime - startTime;
		
		out.println("endTime = " + duration);
		out.println("[/End While Loop]");
		
		return null;
	}



	@Override
	public Object visit(ASTIfStatement node, Object data)
	{
//		out.println("[Begin IfStatement]");
		
		// Evaluate the conditional expression of the if statement
		int conditional = Integer.parseInt(node.jjtGetChild(0).jjtAccept(this, null).toString());
		
		// Execute if conditional is not false.  (0 == false, 1 == true)
		if ( conditional != 0)
			node.jjtGetChild(1).jjtAccept(this, null);
		else
			node.jjtGetChild(2).jjtAccept(this, null);
		
//		out.println("[/End IfStatement]");
		
		return null;
	}



	@Override
	public Object visit(ASTForLoop node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object visit(ASTListOfInstructions node, Object data)
	{
		// Execute every instruction in list
		for (int i=0; i<node.jjtGetNumChildren(); i++)
			node.jjtGetChild(i).jjtAccept(this, null);
		
		return null;
	}
	
	
//	@Override
//	public Object visit(ASTParameterList node, Object data)
//	{
//		out.println("[Parameter List]");
//		
//		for (int i=0; i<node.jjtGetNumChildren(); i++)
//			node.jjtGetChild(i).jjtAccept(this, null);
//		
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	
}
