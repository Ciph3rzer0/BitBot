package edu.sru.andgate.bitbot.tools;

import java.util.ArrayList;
import java.util.Hashtable;

import android.os.Bundle;

import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.gametypes.BotVsBot;
import edu.sru.andgate.bitbot.gametypes.DungeonCrawl;
import edu.sru.andgate.bitbot.gametypes.GameType;
import edu.sru.andgate.bitbot.gametypes.TutorialTesting;

public class Constants
{
	public static boolean hasShownBefore = false;
	
	public static ArrayList<String> finished_tutorials = new ArrayList<String>();
	public static ArrayList<String> finished_missions = new ArrayList<String>();
	
	// damage image lookup table
	public static Hashtable<Integer, Integer> damage1 = new Hashtable<Integer, Integer>();
	public static Hashtable<Integer, Integer> damage2 = new Hashtable<Integer, Integer>();
	
	// tutorial list tables
	public static Hashtable<String, String> tutorials_table = new Hashtable<String, String>();
	public static Hashtable<String, Integer> simulation_table = new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> tutorial_bot_num = new Hashtable<String, Integer>();
	
	// Hash for Missions
//	public static Hashtable<String, Class> missions = new Hashtable<String, Class>();
	
	/**
	 * This block of code runs when the class is defined in memory (before most everything)
	 * This code handles initializing some constant stuff we need.
	 */
	static
	{
		// damage image1
		damage1.put(R.drawable.adambot, R.drawable.adambotd1);
		
		// damage image2
		damage2.put(R.drawable.adambot, R.drawable.adambotd2);
		
		// tutorial list
		tutorials_table.put("Print Statement", "print_tutorial.xml");
		tutorials_table.put("Defining Variables", "declaring_vars.xml");
		tutorials_table.put("Loops", "loops.xml");
		tutorials_table.put("Sub Routines", "sub_routines.xml");
		tutorials_table.put("Selection Statements", "if_statement.xml");
		tutorials_table.put("Bot Movement", "bot_movement.xml");
		tutorials_table.put("Gun Fire", "bot_fire.xml");
		tutorials_table.put("Boundary Collisions", "bot_boundary_collisions.xml");
		tutorials_table.put("Back to Main Menu", "");
		
		// simulation table
		simulation_table.put("Print Statement", 1);
		simulation_table.put("Defining Variables", 1);
		simulation_table.put("Loops", 1);
		simulation_table.put("Sub Routines", 1);
		simulation_table.put("Selection Statements", 1);
		simulation_table.put("Bot Movement", 2);
		simulation_table.put("Gun Fire", 2);
		simulation_table.put("Boundary Collisions", 2);
		
		tutorial_bot_num.put("Bot Movement", 0);
		tutorial_bot_num.put("Gun Fire", 2);
		tutorial_bot_num.put("Boundary Collisions", 0);
		
//		missions.put("BOT versus BOT", BotVsBot.class);
//		missions.put("Dungeon Crawl", DungeonCrawl.class);
//		missions.put("Tutorial", TutorialTesting.class);
	}
	
}
