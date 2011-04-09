package edu.sru.andgate.bitbot.interpreter;

public class SourceCode
{
	private String _name, _code;
	
	public SourceCode(String name, String code)
	{
		_name = name;
		_code = code;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getCode()
	{
		return _code;
	}
}
