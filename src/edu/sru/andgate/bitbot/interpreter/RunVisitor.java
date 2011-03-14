package edu.sru.andgate.bitbot.interpreter;

import java.util.HashMap;

import edu.sru.andgate.bitbot.parser.*;

public class RunVisitor implements bc1Visitor
{
	private HashMap<String, String> vars = new HashMap<String, String>();
	
	public RunVisitor()
	{
		
	}
	
	
	//***************************************************
	//*  				 ROOT CLASSES 					*
	//***************************************************
	@Override
	public Object visit(SimpleNode node, Object data)
	{
		// This should never actually get called...
		System.out.println("[===SimpleNode===]");
		return null;
	}
	@Override
	public Object visit(ASTRoot node, Object data)
	{
		System.out.println(" ");
		System.out.println("[--- Root ---]");
		
		// Visit Program
		node.jjtGetChild(0).jjtAccept(this, null);
		
		return null;
	}
	@Override
	public Object visit(ASTProgram node, Object data)
	{
		System.out.println("[= Program =]");
		
		// Visit all Instructions
		for (int i=0; i<node.jjtGetNumChildren(); i++)
			node.jjtGetChild(i).jjtAccept(this, null);
		
		return null;
	}

	//***************************************************
	//*  				INSTRUCTIONS					*
	//***************************************************
	@Override
	public Object visit(ASTPrint node, Object data)
	{
		System.out.println("[Print]");
		return null;
	}
	@Override
	public Object visit(ASTDeclaration node, Object data)
	{
		String id = ((SimpleNode)node.jjtGetChild(0)).jjtGetValue().toString();
		String value = "0";
		
		vars.put(id, value);
		
		System.out.println("[Declaration] " + id + " = "  + value);
		return null;
	}
	@Override
	public Object visit(ASTAssignment node, Object data)
	{
		System.out.println("[Assignment]");
		
		// ID is first child
		String id = ((SimpleNode)node.jjtGetChild(0)).jjtGetValue().toString();
		// Value is second child
		String value = node.jjtGetChild(1).jjtAccept(this, null).toString();
		
		// Update the variable
		vars.put(id, value);
		
		System.out.println("[/Assignment: " + id + "=" + value + "]");
		
		return value;
	}

	
	//***************************************************
	//*  				   STRINGS 						*
	//***************************************************
	@Override
	public Object visit(ASTStringExpression node, Object data)
	{
		System.out.println("StringExpression");
		
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
			System.out.println("Unknown Operation: '" + op + "'");

		// Log
		System.out.println(v1 + " " + op + " " + v2 + " -> " + result);
		
		return result;
	}

	@Override
	public Object visit(ASTStringLiteral node, Object data)
	{
		String result = node.jjtGetValue().toString();
		System.out.println("StringLiteral: " + result);
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

		System.out.println(key + ":" + result);
		
		return result;
	}
	
	//***************************************************
	//*  				  LITERALS 						*
	//***************************************************
	@Override
	public Object visit(ASTIntegerLiteral node, Object data)
	{
		//System.out.println("(" + node.jjtGetValue().toString() + ")");
		return node.jjtGetValue().toString();
	}
	
	//***************************************************
	//*  				EXPRESSIONS						*
	//***************************************************
	@Override
	public Object visit(ASTBooleanExpression node, Object data)
	{
		System.out.println("BooleanExpression");
		// TODO actually return something
		return "0";
	}
	
	@Override
	public Object visit(ASTEqualityExpression node, Object data)
	{
		System.out.println("EqualityExpression");
		// TODO actually return something
		return "0";
	}
	
	@Override
	public Object visit(ASTRelationalExpression node, Object data)
	{
		System.out.println("RelationalExpression");
		// TODO actually return something
		return "0";
	}
	
	@Override
	public Object visit(ASTAdditiveExpression node, Object data)
	{
		// Determine the operation
		String op = node.jjtGetValue().toString();
		
		//System.out.println("AdditiveExpression: " + op);
		
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
			System.out.println("Unknown Operation: '" + op + "'");

		// Log
		System.out.println(v1 + " " + op + " " + v2 + " -> " + result);
		
		return result;
	}
	
	@Override
	public Object visit(ASTMultiplicativeExpression node, Object data)
	{
		// Determine the operation
		String op = node.jjtGetValue().toString();
		
		// Output for debug
		//System.out.println("MultiplicativeExpression: " + op);
		
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
			System.out.println("Unknown Operation: '" + op + "'");
		
		// Log
		System.out.println(v1 + " " + op + " " + v2 + " -> " + result);
		
		return result;
	}
	
	@Override
	public Object visit(ASTUnaryExpression node, Object data)
	{
		String op = node.jjtGetValue().toString();
		System.out.println("UnaryExpression: " + op);
		
		// Get operand
		int v1 = Integer.parseInt(node.jjtGetChild(0).jjtAccept(this, null).toString());
		
		// Log
		System.out.println(op + v1);
		
		// Negate if necessary
		if (op.equals("-")) return -v1;
		// else, return the same value
		return v1;
	}
	
	
}
