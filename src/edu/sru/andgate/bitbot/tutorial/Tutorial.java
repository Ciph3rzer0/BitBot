package edu.sru.andgate.bitbot.tutorial;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;

public class Tutorial 
{
	private String title, text;
	private Stage[] stages;
	private int stageNum = 0;
	
	public Tutorial(InputStream xml)
	{
		try
		{
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	        
			Document doc = docBuilder.parse(xml);
			Element root = doc.getDocumentElement();
			
			title = getData(root.getElementsByTagName("title"));
			text = getData(root.getElementsByTagName("text"));
			System.out.println("title = " + title);
			
			NodeList stagesNodeList = root.getElementsByTagName("stage");
			int numStages = stagesNodeList.getLength();
			
			stages = new Stage[numStages];
			String hint = null, answer = null;
			
			for (int i = 0; i < numStages; i++)
			{
				hint = getData(((Element)stagesNodeList.item(i)).getElementsByTagName("hint"));
				answer = getData(((Element)stagesNodeList.item(i)).getElementsByTagName("answer"));
				
				stages[i] = new Stage(hint, answer);
				
//				System.out.println("hint:\n " + hint + "\nanswser:\n " + answer + "\n \n ");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private String getData(NodeList e)
	{
		return ((Node)e.item(0).getChildNodes().item(0)).getNodeValue();
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getText()
	{
		return text;
	}
	
	public int getStage()
	{
		return stageNum;
	}
	
	public int nextStage()
	{
		if (stageNum+1 < stages.length)
			return ++stageNum;
		else
			return -1;
	}
	
	public String getHint()
	{
		return stages[stageNum].hint;
	}
	
	public String getAnswer()
	{
		return stages[stageNum].answer;
	}
	
	private class Stage
	{
		private String hint, answer;
		
		private Stage(String hint, String answer)
		{
			this.hint = hint;
			this.answer = answer;
		}
	}
}
