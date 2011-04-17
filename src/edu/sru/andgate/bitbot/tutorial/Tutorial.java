package edu.sru.andgate.bitbot.tutorial;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;

public class Tutorial 
{
	private String title, text, hint, answer;
	private Stage[] stages;
	private int stageNum = 0, numStages;
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element root;
	private NodeList stagesNodeList;
	private boolean completionStatus;
	
	public Tutorial(InputStream xml)
	{
		try
		{
			docBuilderFactory = DocumentBuilderFactory.newInstance();
	        docBuilder = docBuilderFactory.newDocumentBuilder();
	        
			doc = docBuilder.parse(xml);
			root = doc.getDocumentElement();
			
			title = getData(root.getElementsByTagName("title"));
			text = getData(root.getElementsByTagName("text"));
			System.out.println("title = " + title);
			
			stagesNodeList = root.getElementsByTagName("stage");
			numStages = stagesNodeList.getLength();
			
			stages = new Stage[numStages];
			hint = null;
			answer = null;
			
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
	
	public void setCompletionStatus(boolean b){
		this.completionStatus = b;
	}

	public Boolean isCompleted() {
		if(this.completionStatus = true){
			return true;
		}else{
			return false;
		}
	}
}
