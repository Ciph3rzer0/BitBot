package edu.sru.andgate.bitbot.customdialogs;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

public class IntroPopupText 
{
	private String hint; //tutorial hints
	private Stage[] stages; //multi-stages
	private int stageNum = 0, numStages; //current stage number, total number of stages
	
	//XML builder declarations
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element root;
	private NodeList stagesNodeList;
	
	
	public IntroPopupText(InputStream xml)
	{
		try
		{
			//prepare the xml for reading
			docBuilderFactory = DocumentBuilderFactory.newInstance();
	        docBuilder = docBuilderFactory.newDocumentBuilder();
	        
			doc = docBuilder.parse(xml);
			root = doc.getDocumentElement();
			
			//select the stage tag for reading
			stagesNodeList = root.getElementsByTagName("stage");
			numStages = stagesNodeList.getLength();
			
			stages = new Stage[numStages];
			hint = null;
			
			for (int i = 0; i < numStages; i++)
			{
				//set the appropriate stage to hold the appropriate hint
				hint = getData(((Element)stagesNodeList.item(i)).getElementsByTagName("hint"));
				stages[i] = new Stage(hint);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	//Get the data from a specified xml tag
	private String getData(NodeList e)
	{
		return ((Node)e.item(0).getChildNodes().item(0)).getNodeValue();
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
	
	
	//private class that contains a string
	private class Stage
	{
		private String hint;
		
		private Stage(String hint)
		{
			this.hint = hint;
		}
	}

}
