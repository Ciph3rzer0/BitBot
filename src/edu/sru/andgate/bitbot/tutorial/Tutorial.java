package edu.sru.andgate.bitbot.tutorial;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Tutorial 
{
	private String title, text, hint, answer; //tutorial attributes
	private Stage[] stages; //stages of the tutorial
	private int stageNum = 0, numStages;
	
	//XML builder declarations
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element root;
	private NodeList stagesNodeList;
	
	public Tutorial(InputStream xml)
	{
		try
		{
			//prepare the xml for reading
			docBuilderFactory = DocumentBuilderFactory.newInstance();
	        docBuilder = docBuilderFactory.newDocumentBuilder();
	        
			doc = docBuilder.parse(xml);
			root = doc.getDocumentElement();
			
			title = getData(root.getElementsByTagName("title"));
			text = getData(root.getElementsByTagName("text"));
			System.out.println("title = " + title);
			
			//select the stage tag for reading
			stagesNodeList = root.getElementsByTagName("stage");
			numStages = stagesNodeList.getLength();
			
			stages = new Stage[numStages];
			hint = null;
			answer = null;
			
			for (int i = 0; i < numStages; i++)
			{
				//set the appropriate stage to hold the appropriate hint & answer
				hint = getData(((Element)stagesNodeList.item(i)).getElementsByTagName("hint"));
				answer = getData(((Element)stagesNodeList.item(i)).getElementsByTagName("answer"));
				
				stages[i] = new Stage(hint, answer);
				
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
	
	//private class that contains the tutorials hint, and answer
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
