package edu.sru.andgate.bitbot.interpreter;

import edu.sru.andgate.bitbot.parser.ASTAdditiveExpression;
import edu.sru.andgate.bitbot.parser.ASTAssignment;
import edu.sru.andgate.bitbot.parser.ASTBooleanExpression;
import edu.sru.andgate.bitbot.parser.ASTConcatExpression;
import edu.sru.andgate.bitbot.parser.ASTDeclaration;
import edu.sru.andgate.bitbot.parser.ASTEqualityExpression;
import edu.sru.andgate.bitbot.parser.ASTForLoop;
import edu.sru.andgate.bitbot.parser.ASTIdentifier;
import edu.sru.andgate.bitbot.parser.ASTIfStatement;
import edu.sru.andgate.bitbot.parser.ASTListOfInstructions;
import edu.sru.andgate.bitbot.parser.ASTMultiplicativeExpression;
import edu.sru.andgate.bitbot.parser.ASTNumberLiteral;
import edu.sru.andgate.bitbot.parser.ASTPrint;
import edu.sru.andgate.bitbot.parser.ASTProgram;
import edu.sru.andgate.bitbot.parser.ASTRelationalExpression;
import edu.sru.andgate.bitbot.parser.ASTRoot;
import edu.sru.andgate.bitbot.parser.ASTStringLiteral;
import edu.sru.andgate.bitbot.parser.ASTSubCall;
import edu.sru.andgate.bitbot.parser.ASTSubDef;
import edu.sru.andgate.bitbot.parser.ASTUnaryExpression;
import edu.sru.andgate.bitbot.parser.ASTWhileLoop;
import edu.sru.andgate.bitbot.parser.SimpleNode;
import edu.sru.andgate.bitbot.parser.bc1Visitor;

public class CompareVisitor implements bc1Visitor
{

	@Override
	public Object visit(SimpleNode node, Object data)
	{
		// TODO Auto-generated method stub
		node.getClass().getSimpleName();
		return null;
	}

	@Override
	public Object visit(ASTRoot node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTProgram node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTListOfInstructions node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTWhileLoop node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTIfStatement node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTForLoop node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTSubCall node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTSubDef node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTPrint node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTDeclaration node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTAssignment node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTConcatExpression node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTStringLiteral node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTBooleanExpression node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTEqualityExpression node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTRelationalExpression node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTAdditiveExpression node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTMultiplicativeExpression node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTUnaryExpression node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTIdentifier node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTNumberLiteral node, Object data)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
