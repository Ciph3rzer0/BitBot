package edu.sru.andgate.bitbot;

import java.util.Hashtable;

public class Constants {
	//save-load bot image table lookups
	public Hashtable <String, Integer> base_table = new Hashtable <String,Integer>();
	public Hashtable <Integer, String> reverse_base_table = new Hashtable <Integer, String>();
	public Hashtable <String, Integer> turret_table = new Hashtable <String,Integer>();
	public Hashtable <Integer, String> reverse_turret_table = new Hashtable <Integer, String>();
	
	//tutorial list tables
	public Hashtable<String, String> tutorials_table = new Hashtable<String, String>();
	public Hashtable<String, Integer> simulation_table = new Hashtable<String, Integer>();
	 
	 public Constants(){
		 //save-load bot lookups
		 base_table.put("square", R.drawable.adambot);
		 turret_table.put("basic",R.drawable.adamturret);
		 reverse_base_table.put(R.drawable. adambot,"square");
		 reverse_turret_table.put(R.drawable.adamturret, "basic");
		 
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
