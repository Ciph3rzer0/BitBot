package edu.sru.andgate.bitbot.tools;

import java.io.File;

public class ReadDirectory {
	public ReadDirectory(){
		
	}
	 public static String[] getFiles(String dir)
	 {
		    File folder = new File(dir);
		    File[] listOfFiles = folder.listFiles();
		    String[] items = new String[listOfFiles.length];
		    for (int i = 0; i < listOfFiles.length; i++) 
		    {
		    	if (listOfFiles[i].isFile()) 
		    	{
		    		items[i] = listOfFiles[i].getName();
		    	}
		    	else if (listOfFiles[i].isDirectory()) 
		    	{
		    		// System.out.println("Directory " + listOfFiles[i].getName());
		    	}
		    }
		    return items;	
	    }	 	
}
