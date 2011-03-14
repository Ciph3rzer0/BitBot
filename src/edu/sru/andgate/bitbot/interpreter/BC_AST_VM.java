package edu.sru.andgate.bitbot.interpreter;
import java.util.HashMap;

import edu.sru.andgate.bitbot.parser.Node;
import edu.sru.andgate.bitbot.parser.SimpleNode;

public class BC_AST_VM
{
	private SimpleNode root;
	private HashMap vars;
	private SimpleNode pc;
	RunVisitor rv;
	
	public BC_AST_VM(SimpleNode root)
	{
		this.root = root;
		pc = root;
		
		rv = new RunVisitor();
	}
	
	public void run()
	{
		root.jjtAccept(rv, null);
		//root.childrenAccept(rv, null);
		
		//recursiveAccept(root);
	}
	
	
	private void recursiveAccept(Node s)
	{
		s.jjtAccept(rv, null);
		
		for(int i=0; i < s.jjtGetNumChildren(); i++)
			recursiveAccept(s.jjtGetChild(i));
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




