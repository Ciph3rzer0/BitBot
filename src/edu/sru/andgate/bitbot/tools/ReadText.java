package edu.sru.andgate.bitbot.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class ReadText {
	private static Context context;
	
	public ReadText(Context context){
		ReadText.context = context;
	}
	
	 public static String readTextFileFromDirectory(String directory, String filename)
	 {
	    	String line;
	    	String temp = "";
	    	// try opening the myfilename.txt
	    	try 
	    	{
	    		// open the file for reading
	    		File f = new File(context.getDir(directory, Context.MODE_PRIVATE), filename);
	    		FileInputStream fis = new FileInputStream(f);
	    		//InputStream instream = openFileInput();
	    	 
	    		// if file the available for reading
	    		if (fis != null) {
	    			// prepare the file for reading
	    			InputStreamReader inputreader = new InputStreamReader(fis);
	    			BufferedReader buffreader = new BufferedReader(inputreader);
	    	 
	    			// read every line of the file into the line-variable, on line at the time
	    			while (( line = buffreader.readLine()) != null) {
	    				// do something with the settings from the file
	    				temp+=line + "\n";
	    			}    	 
	    		 }
	    	 	 	// close the file again
	    		 	fis.close();
	    		 	return temp.toString();
	    	  } catch (java.io.FileNotFoundException e) {
	    		  // do something if the myfilename.txt does not exits
	    		  Log.v("Test", "File Not Found");
	    	  } catch (IOException e) {
	    		  // TODO Auto-generated catch block
	    		  Log.v("Test", "I/O Error");
			}
			return temp.toString();
	    }
	 
}
