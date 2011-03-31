package edu.sru.andgate.bitbot.interpreter;

import java.util.HashSet;
import java.util.Set;

import android.util.Log;

public class InstructionLimitedVirtualMachine
{
	/**
	 * Set of Execution Threads running in this virtual machine.
	 */
	private Set<BotInterpreter> botThreads = new HashSet<BotInterpreter>();
	
	/**
	 * 
	 */
	public InstructionLimitedVirtualMachine() {}
	
	/**
	 * Add an interpreter to this virtual machine.  Starts the thread before adding it.
	 * @param bot the BotInterpreter to be added.
	 * @return true if successful.
	 */
	public boolean addInterpreter(BotInterpreter bot)
	{
		// TODO : Start the thread?
		bot.start();
		
		return botThreads.add(bot);
	}
	
	/**
	 * remove an interpreter from this virtual machine.
	 * 
	 * @param bot the BotInterpreter to be removed.
	 * @return true if successful.
	 */
	public boolean removeInterpreter(BotInterpreter bot)
	{
		// TODO : Gracefully stop the thread.
		return botThreads.remove(bot);
	}
	
	/**
	 * Attempt to start running the thread of each interpreter.  Before calling an 
	 * Interpreter's resume() function, it checks to see if the thread is still running.
	 * If the thread is still running, then resume is not called, and an error is logged.
	 *    
	 * @param numberOfInstructionsToExecute The number of instructions each interpreter should run.
	 */
	public void resume(int numberOfInstructionsToExecute)
	{
		// Resume each thread attached to the VirtualMachine.
		for (BotInterpreter bi : botThreads)
		{
			if (bi.isWaiting())
				bi.resume(numberOfInstructionsToExecute);
			else
				Log.e("BitBot Interpreter", "Thread not finished before next resume was called.");
		}
	}
	
	/**
	 * Raises a flag in all threads that causes them to exit.  All data is lost.
	 */
	public void stop()
	{
		// Resume each thread attached to the VirtualMachine.
		for (BotInterpreter bi : botThreads)
		{
			try
			{
				bi.abort();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Raises a flag in all threads that causes them to exit.  All data is lost.
	 */
	public void pause()
	{
		// Resume each thread attached to the VirtualMachine.
		for (BotInterpreter bi : botThreads)
		{
//			try
//			{
				bi.pause();
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
		}
	}
	
	/**
	 * 
	 */
	public void threadFinish(BotInterpreter bot)
	{
		Log.d("BitBot Interpreter", "VM: Thread '" + Thread.currentThread().getName() + "' ending.");
	}
}
