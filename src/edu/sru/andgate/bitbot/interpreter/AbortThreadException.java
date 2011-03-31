package edu.sru.andgate.bitbot.interpreter;

public class AbortThreadException extends Exception
{
	private static final long serialVersionUID = 1L;

	public AbortThreadException()
	{
		super("BotInterpreter Thread was aborted");
	}
}
