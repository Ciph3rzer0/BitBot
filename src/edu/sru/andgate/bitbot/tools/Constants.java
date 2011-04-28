package edu.sru.andgate.bitbot.tools;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.R.drawable;

public class Constants {
	public static boolean hasShownBefore = false;
	public static ArrayList<String> finished_tutorials = new ArrayList<String>();
	
	
	//damage image lookup table
	public Hashtable<Integer, Integer> damage1 = new Hashtable<Integer,Integer>();
	public Hashtable<Integer, Integer> damage2 = new Hashtable<Integer,Integer>();
	
	//tutorial list tables
	public Hashtable<String, String> tutorials_table = new Hashtable<String, String>();
	public Hashtable<String, Integer> simulation_table = new Hashtable<String, Integer>();
	 
	public Constants(){
		//damage image1
		damage1.put(R.drawable.adambot, R.drawable.adambotd1);
		
		//damage image2
		damage2.put(R.drawable.adambot, R.drawable.adambotd2);
		
		//tutorial list
        tutorials_table.put("Print Statement", "print_tutorial.xml");
        tutorials_table.put("Defining Variables", "declaring_vars.xml");
        tutorials_table.put("Loops","loops.xml");
        tutorials_table.put("Sub Routines", "sub_routines.xml");
        tutorials_table.put("Selection Statements", "if_statement.xml");
        tutorials_table.put("Back to Main Menu", "");
	 
        //simulation table
        simulation_table.put("Print Statement", 1);
    	simulation_table.put("Defining Variables", 1);	 
        simulation_table.put("Loops", 1);
        simulation_table.put("Sub Routines", 1);
        simulation_table.put("Selection Statements", 1);
        
	 }
	 
	 
}
