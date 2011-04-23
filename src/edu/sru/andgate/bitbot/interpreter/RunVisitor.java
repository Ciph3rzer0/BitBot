package edu.sru.andgate.bitbot.interpreter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;

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
	private Stack<HashMap<String, String>> varStack = new Stack<HashMap<String,String>>();
	
	//private HashMap<String, String> vars = new HashMap<String, String>();
	private HashMap<String, Node> subs = new HashMap<String, Node>();
	
	private volatile int instructionsLeft = 0;
	private volatile boolean _waiting = true;
	private volatile boolean _abort = false;
	private volatile boolean _interrupt = false;
	
	private PipedOutputStream $std_out = new PipedOutputStream();
	private PrintStream std_out = new PrintStream($std_out, true);
	
	private PipedInputStream $std_in = new PipedInputStream();
	
	private BotInterpreter bi;
	
	/**
	 * This replaces "this" for all jjtAccept calls.  It functions identically to 
	 * "this" until the thread has to abort, we then set __this__ to another Visitor
	 * that will quickly rise back up the stack.
	 */
	private RunVisitor __this__;
	
	
	/**
	 * This can allow you to control where debug output would go.
	 * Data here comes from the parser itself.
	 */
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
		
		// Put the first var hash on the stack.
		HashMap<String, String> vars = new HashMap<String, String>();
		varStack.push(vars);
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
		if (_interrupt)
			HandleInterrupt();
		
		
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
	
	public void HandleInterrupt()
	{
		if (_abort)
			throw new Error();
		else
		{
			
			
		}
		
	}
	
	/**
	 * Pause the machine.  Sets instructionLeft to 0.
	 */
	public void pause()
	{
		instructionsLeft = 0;		// Pause the machine.
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
	 * Flags this BotInterpreter to abort.  When it's execution thread runs next it will abort
	 * its execution of interpreted code.
	 */
	public void abort()
	{
		synchronized (this)
		{
			this.notify();
			_interrupt = true;
			_abort = true;
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
		
		// ASTProgram
		node.jjtGetChild(0).jjtAccept(this, null);
		
		return null;
	}
	@Override
	public Object visit(ASTProgram node, Object data)
	{
		out.println("[= Program =]");
		
		for (int i = 1; i < node.jjtGetNumChildren(); i++)
		{
			Node n = (SimpleNode)node.jjtGetChild(i);
			String name = (String) ((SimpleNode)node.jjtGetChild(i).jjtGetChild(0)).jjtGetValue();
			
			// Store in a hash
			subs.put(name, n);
			
			if (name != null)
				Log.d("BitBot Interpreter", name);
		}
		
//		if (node.jjtGetNumChildren() == 1)
//		{
			// ASTListOfInstructions
			node.jjtGetChild(0).jjtAccept(this, null);
//		}
//		else
//		{
//			// ASTListOfInstructions
//			node.jjtGetChild(1).jjtAccept(this, null);
//		}
		
		return null;
	}

	//***************************************************
	//*  				INSTRUCTIONS					*
	//***************************************************
	@Override
	public Object visit(ASTPrint node, Object data)
	{
		NextInstruction();
		
		// ASTExpression
//		String eval = node.jjtGetChild(0).jjtAccept(this, null).toString();
		Node n = node.jjtGetChild(0);
		String eval = n.jjtAccept(this, null).toString();
		
		// Print the line
//		out.println( "[PRINT] " + eval);
		std_out.println(eval);
		
		// Flush text to TextView
		bi.flush();
		
		return null;
	}
	@Override
	public Object visit(ASTDeclaration node, Object data)
	{
		//NextInstruction();
		
		// ASTIdentifier
		String id = ((SimpleNode)node.jjtGetChild(0)).jjtGetValue().toString();
		String value = "0";
		
		// If the user put in the optional assignment
		if (node.jjtGetNumChildren() == 2)
			value = ((SimpleNode)node.jjtGetChild(1)).jjtGetValue().toString();
		
		// Store variable in hash
		varStack.peek().put(id, value);
		
//		out.println("[Declaration] " + id + " = "  + value);
		return null;
	}
	@Override
	public Object visit(ASTAssignment node, Object data)
	{
		NextInstruction();
		
		// ASTIdentifier
		String id = ((SimpleNode)node.jjtGetChild(0)).jjtGetValue().toString();
		
		// ASTExpression
		String value = node.jjtGetChild(1).jjtAccept(this, null).toString();
		
		// Update the variable
		varStack.peek().put(id, value);
		
//		out.println("[/Assignment: " + id + "=" + value + "]");
		
		return value;
	}
	
	//***************************************************
	//*  				IDENTIFIERS						*
	//***************************************************
	@Override
	public Object visit(ASTIdentifier node, Object data)
	{
		String key = node.jjtGetValue().toString();
		String result = varStack.peek().get(key);
		
		if (result == null)
			result = "0";
		
//		out.println(key + ":" + result);
		
		return result;
	}
	
	//***************************************************
	//*  				  LITERALS 						*
	//***************************************************
	@Override
	public Object visit(ASTNumberLiteral node, Object data)
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
	public Object visit(ASTConcatExpression node, Object data)
	{
//		out.println("StringExpression");
		
		// Extract the operation
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
	
	@Override
	public Object visit(ASTBooleanExpression node, Object data)
	{
		NextInstruction();
		
		// Determine the operation
		String op = node.jjtGetValue().toString();
//		out.println("BooleanExpression: " + op);
		
		// Visit this nodes children to find out the two operands (v1 and v2)
		double v1 = 0, v2 = 0;
		try
		{
			v1 = Double.parseDouble(node.jjtGetChild(0).jjtAccept(this, null).toString());
			v2 = Double.parseDouble(node.jjtGetChild(1).jjtAccept(this, null).toString());
		}
		catch(Exception e){}
		
		// Evaluate
		int result = 0;
		if (op.equalsIgnoreCase("AND"))
			result = ((v1!=0)&&(v2!=0))?1:0;
		else if (op.equalsIgnoreCase("OR"))
			result = ((v1!=0)||(v2!=0))?1:0;
		else 
			Log.e("BitBot Interpreter", "Unknown Operation: '" + op + "'");

		// Log
//		out.println(v1 + " " + op + " " + v2 + " -> " + result);
		
		return result;
	}
	
	@Override
	public Object visit(ASTEqualityExpression node, Object data)
	{
		NextInstruction();
		
		// Determine the operation
		String op = node.jjtGetValue().toString();
//		out.println("EqualityExpression" + op);
		
		// Visit this nodes children to find out the two operands (v1 and v2)
		double v1 = 0, v2 = 0;
		try
		{
			v1 = Double.parseDouble(node.jjtGetChild(0).jjtAccept(this, null).toString());
			v2 = Double.parseDouble(node.jjtGetChild(1).jjtAccept(this, null).toString());
		}
		catch(Exception e){}
		
		// Evaluate
		int result = 0;
		if (op.equalsIgnoreCase("=="))
			result = (v1==v2)?1:0;
		else if (op.equalsIgnoreCase("!="))
			result = (v1!=v2)?1:0;
		else 
			Log.e("BitBot Interpreter", "Unknown Operation: '" + op + "'");

		// Log
//		out.println(v1 + " " + op + " " + v2 + " -> " + result);
		
		return result;
	}
	
	@Override
	public Object visit(ASTRelationalExpression node, Object data)
	{
		NextInstruction();
		
		// Determine the operation
		String op = node.jjtGetValue().toString();
//		out.println("RelationalExpression" + op);
		
		// Visit this nodes children to find out the two operands (v1 and v2)
		double v1 = 0, v2 = 0;
		try
		{
			v1 = Double.parseDouble(node.jjtGetChild(0).jjtAccept(this, null).toString());
			v2 = Double.parseDouble(node.jjtGetChild(1).jjtAccept(this, null).toString());
		}
		catch(Exception e){}
		
		//"<" | ">" | "<=" | ">="
		// Evaluate
		int result = 0;
		if (op.equalsIgnoreCase("<"))
			result = (v1<v2)?1:0;
		else if (op.equalsIgnoreCase(">"))
			result = (v1>v2)?1:0;
		else if (op.equalsIgnoreCase("<="))
			result = (v1<=v2)?1:0;
		else if (op.equalsIgnoreCase(">="))
			result = (v1>=v2)?1:0;
		else 
			Log.e("BitBot Interpreter", "Unknown Operation: '" + op + "'");

		// Log
//		out.println(v1 + " " + op + " " + v2 + " -> " + result);
		
		return result;
	}
	
	@Override
	public Object visit(ASTAdditiveExpression node, Object data)
	{
		NextInstruction();
		
		// Determine the operation
		String op = node.jjtGetValue().toString();
		
//		out.println("AdditiveExpression: " + op);
		
		// Visit this nodes children to find out the two operands (v1 and v2)
		double v1 = 0, v2 = 0, result = 0;
		try
		{
			v1 = Double.parseDouble(node.jjtGetChild(0).jjtAccept(this, null).toString());
			v2 = Double.parseDouble(node.jjtGetChild(1).jjtAccept(this, null).toString());
		}
		catch(Exception e){}
		
		// Evaluate
		if (op.equalsIgnoreCase("+"))	result = v1 + v2;	else
		if (op.equalsIgnoreCase("-"))	result = v1 - v2;	else 
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
//		out.println("MultiplicativeExpression: " + op);
		
		// Visit this nodes children to find out the two operands (v1 and v2)
		double v1 = 0, v2 = 0, result = 0;
		try
		{
			v1 = Double.parseDouble(node.jjtGetChild(0).jjtAccept(this, null).toString());
			v2 = Double.parseDouble(node.jjtGetChild(1).jjtAccept(this, null).toString());
		}
		catch(Exception e){}
		
		// Evaluate
		if (op.equalsIgnoreCase("*"))		result = v1 * v2;	else
		if (op.equalsIgnoreCase("/"))		result = v1 / v2;	else
		if (op.equalsIgnoreCase("%"))		result = v1 % v2;	else
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
		double v1 = Double.parseDouble(node.jjtGetChild(0).jjtAccept(this, null).toString());
		
		// Log
//		out.println("UnaryExpression: " + op + " " + v1);
		
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
		
//		out.println("Call Subroutine:  " + instr);
//		node.jjtGetChild(0).jjtAccept(this, null);
		
		int numP = node.jjtGetNumChildren() - 1;
		String[] params = null;
		if (numP > 0)
		{
			params = new String[numP];
			
			// Parameters start at 1, id is first.
			for (int i=1; i<node.jjtGetNumChildren(); i++)
				params[i-1] = node.jjtGetChild(i).jjtAccept(this, null).toString();
			
//			// Print them out temporarily
//			for (int i=0; i<params.length; i++)
//				out.println(params[i]);;
		}
		
		// Execute bot instructions
		if (instr.length() >= 4 && instr.substring(0, 4).equalsIgnoreCase("bot_") )
			bi.executeBotInstruction(instr, params);
		else
		{
			Node n = subs.get(instr);
			if (n != null)
				n.jjtAccept(this, params);
		}
		
		if (instr.equalsIgnoreCase("pow"))
		{
			double v1 = 0, v2 = 0;
			
			try {
				v1 = Double.parseDouble(params[0]);
				v2 = Double.parseDouble(params[1]);
			}
			catch(Exception e){}
			
			return Math.pow(v1, v2);
		}
		
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Object visit(ASTSubDef node, Object data)
	{
		Log.d("BitBot Interpreter", "In Sub Def");
		
//		// ListOfInstructions
//		node.jjtGetChild(1).jjtAccept(this, null);
		
		HashMap<String, String> hm = new HashMap<String, String>();
		// TODO: THIS IS GOING TO BE INEFFICIENT
		String[] localVars = (String[]) data;
		
		// Assign local variables
		if (localVars != null)
			for (int i = 0; (i < localVars.length) && (i < node.jjtGetNumChildren()-2); i++)
			{
				Log.v("BitBot Interpreter", localVars[i]);
				hm.put(((SimpleNode)node.jjtGetChild(i+1)).jjtGetValue().toString(), localVars[i]);
			}
		
		// Push local variables
		varStack.push(hm);
		
		// ListOfInstructions
		node.jjtGetChild(node.jjtGetNumChildren()-1).jjtAccept(this, null);
		
		// Pop local variables
		varStack.pop();
		
		return null;
	}
	
	
	@Override
	public Object visit(ASTWhileLoop node, Object data)
	{
//		out.println("[Begin While Loop]");
		
		// Loop while conditional is not false.  (0 == false, 1 == true)
		while ( Double.parseDouble(node.jjtGetChild(0).jjtAccept(this, null).toString()) != 0)
			node.jjtGetChild(1).jjtAccept(this, null);
		
		return null;
	}



	@Override
	public Object visit(ASTIfStatement node, Object data)
	{
//		out.println("[Begin IfStatement]");
		
		// Evaluate the conditional expression of the if statement
		double conditional = Double.parseDouble(node.jjtGetChild(0).jjtAccept(this, null).toString());
		
		// Execute if conditional is not false.  (0 == false, 1 == true)
		if ( conditional != 0)
			node.jjtGetChild(1).jjtAccept(this, null);
		else
			// Prevent null pointer when else is not there.
			if (node.jjtGetNumChildren() == 3)
				node.jjtGetChild(2).jjtAccept(this, null);
		
//		out.println("[/End IfStatement]");
		
		return null;
	}
	
	@Override
	public Object visit(ASTForLoop node, Object data)
	{
		// If five children, then there is an else
		boolean hasStep = node.jjtGetNumChildren()==5;
//		out.println("numChildren = " + node.jjtGetNumChildren());
		
//		String lcv = node.jjtGetChild(0).jjtAccept(this, null).toString();
		String lcv = ((SimpleNode)node.jjtGetChild(0)).jjtGetValue().toString();
		
		double first = 0, last = 0, step = 1;
		int instructionsIndex = 3;
		
		// Try to parse parameters
		try
		{
			first = Double.parseDouble(node.jjtGetChild(1).jjtAccept(this, null).toString());
			last = Double.parseDouble(node.jjtGetChild(2).jjtAccept(this, null).toString());
			
			if (hasStep)
			{
				step = Double.parseDouble(node.jjtGetChild(3).jjtAccept(this, null).toString());
				instructionsIndex = 4;
			}
		}
		catch (Exception e){}
		
		// Execute the for loop
		for (double i = first; i <= last; i += step)
		{
			// Store the lcv
			this.varStack.peek().put(lcv, i + "");
			node.jjtGetChild(instructionsIndex).jjtAccept(this, null);
		}
		
		node.jjtGetValue();
		node.jjtSetValue(new Boolean(true));
		
		
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
	
}
