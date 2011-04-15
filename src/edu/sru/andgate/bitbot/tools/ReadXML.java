package edu.sru.andgate.bitbot.tools;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
	
public class ReadXML {
	static Context context;
	
	public ReadXML(Context context){		
			ReadXML.context = context;
	}
	/*
	 * Method that recieves an xml file name, and target <tag> 
	 * 	returns the text in the specified <tag></tag>
	 */
	public static String readXML(String my_file, String tag_name)
	{
	 		try {
	 			InputStream is = context.getAssets().open(my_file);
	       		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder;
				docBuilder = docBuilderFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(is);
	            doc.getDocumentElement ().normalize ();
	           
	            NodeList tutorialText = doc.getElementsByTagName(tag_name);
	            Element myText = (Element) tutorialText.item(0);
	            
	            return ((Node)myText.getChildNodes().item(0)).getNodeValue().trim();
	            
	 		} catch (Exception e)
			{
				e.printStackTrace();
			}
			 return null;
		}//end of readXML()
}
