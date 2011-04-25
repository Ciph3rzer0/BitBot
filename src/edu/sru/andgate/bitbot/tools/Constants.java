package edu.sru.andgate.bitbot.tools;

import java.util.Hashtable;

import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.R.drawable;

public class Constants {
	//tutorial list tables
	public Hashtable<String, String> tutorials_table = new Hashtable<String, String>();
	public Hashtable<String, Integer> simulation_table = new Hashtable<String, Integer>();
	 
	public Constants(){
		//tutorial list
		tutorials_table.put("Getting Started", "getting_started.xml");
        tutorials_table.put("Print Statement", "print_tutorial.xml");
        tutorials_table.put("Defining Variables", "declaring_vars.xml");
        tutorials_table.put("Loops","loops.xml");
        tutorials_table.put("Selection Statements", "if_statement.xml");
        tutorials_table.put("Back to Main Menu", "");
	 
        //simulation table
        simulation_table.put("Print Statement", 1);
    	simulation_table.put("Defining Variables", 1);	 
	 }
	 
	 
}
