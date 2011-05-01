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
	private String hint;
	private Stage[] stages;
	private int stageNum = 0, numStages;
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element root;
	private NodeList stagesNodeList;
	
	
	public IntroPopupText(InputStream xml)
	{
		try
		{
			docBuilderFactory = DocumentBuilderFactory.newInstance();
	        docBuilder = docBuilderFactory.newDocumentBuilder();
	        
			doc = docBuilder.parse(xml);
			root = doc.getDocumentElement();
					
			stagesNodeList = root.getElementsByTagName("stage");
			numStages = stagesNodeList.getLength();
			
			stages = new Stage[numStages];
			hint = null;
			
			for (int i = 0; i < numStages; i++)
			{
				hint = getData(((Element)stagesNodeList.item(i)).getElementsByTagName("hint"));
				
				stages[i] = new Stage(hint);
				
				Log.v("Intro","hint:\n " + hint+"\n");
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
	
	private class Stage
	{
		private String hint;
		
		private Stage(String hint)
		{
			this.hint = hint;
		}
	}

}
