package edu.sru.andgate.bitbot.tutorial;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.interpreter.BotInterpreter;
import edu.sru.andgate.bitbot.interpreter.InstructionLimitedVirtualMachine;
import edu.sru.andgate.bitbot.tools.FileManager;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class Main_Tutorial extends Activity
{
	private boolean canSimulate = false;
	private EditText editor; 
	private TextView help_text;
	private String tutorialID;
	private int simulateFlag;
	private InputStream xml;
	private Tutorial myTutorial;
	private ActionItem for_shell, do_while_shell,
	   				   var_decl, print_shell, if_shell,
	   				   paren_tool, quote_tool, brace_tool, bracket_tool;
	private SlidingDrawer slidingDrawer;
	private Animation sIn_left, sOut_left, sIn_right, sOut_right;
	private TextView botOutput, main_text;
	private Button slideHandleButton, sequence_btn, selection_btn, iteration_btn, 
				   tools_btn, lock_btn, simulate_btn, to_code_button, back_to_code;
	private QuickAction qa;
	private File file;
	private BufferedWriter writer;
	private ViewFlipper vf;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_main);
		
		FileManager.setContext(getBaseContext());
		
		/*
		 * recieves content sent from previous view for re-use
		 */
		tutorialID = getIntent().getExtras().getString("File_ID");
		simulateFlag = getIntent().getExtras().getInt("Sim_Flag",0);
		
		xml = FileManager.readFile(tutorialID);
   	   	myTutorial = new Tutorial(xml);
			
		/*
		 * Action Items for Sequence, Selection, Iteration buttons
		 */
		for_shell = new ActionItem();
		do_while_shell = new ActionItem();
		var_decl = new ActionItem();
		print_shell = new ActionItem();
		if_shell = new ActionItem();
		
		/*
		 * Action Items for Quick Tools button
		 */
		paren_tool = new ActionItem();
		quote_tool = new ActionItem();
		brace_tool = new ActionItem();
		bracket_tool = new ActionItem();
		
		
		botOutput = (TextView) findViewById(R.id.ide_std_out);
		main_text = (TextView) findViewById(R.id.tutorial_text);
		main_text.setText(FileManager.readXML(tutorialID,"text"));
		
	
		/*
		 * create the text editor and cabinet button
		 */
		editor = (EditText) this.findViewById(R.id.editor);
		editor.setTextSize(12.0f);
		slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
		slideHandleButton = (Button) findViewById(R.id.slideHandleButton);
				
		/*
		 * sets attributes of the action items in the CustomPopUpWindow
		 */
		setActionItem(var_decl,editor, "Declare Variable", "Variable Declaration Selected", getResources().getString(R.string.var_declaration));
		setActionItem(print_shell,editor, "Print to console", "Print Statement Selected", getResources().getString(R.string.print_statement));
		setActionItem(if_shell,editor, "if statement shell", "if statement Selected", getResources().getString(R.string.if_statement));
		setActionItem(do_while_shell, editor, "do while shell", "do while statement selected", getResources().getString(R.string.do_while_statement));
		setActionItem(for_shell,editor, "for statement shell", "for statement selected", getResources().getString(R.string.for_statement));
		setActionItem(paren_tool,editor, "Parenthesis ( )", "Parenthesis Selected", getResources().getString(R.string.parenthesis));
		setActionItem(quote_tool,editor, "Quotations \" \"", "Quotes Selected", getResources().getString(R.string.quotations));
		setActionItem(brace_tool,editor,"Braces { }", "Braces Selected", getResources().getString(R.string.braces));
		setActionItem(bracket_tool, editor, "Brackets [ ]", "Brackets Selected", getResources().getString(R.string.brackets));
		
		/*
		 * sets the view flipper sider animations
		 */
		sIn_left = AnimationUtils.loadAnimation(this, R.anim.slidein_left);
		sOut_left = AnimationUtils.loadAnimation(this, R.anim.slideout_left);
		sIn_right = AnimationUtils.loadAnimation(this, R.anim.slidein_right);
		sOut_right = AnimationUtils.loadAnimation(this, R.anim.slideout_right);
		
		/*
		 * Set all the QuickAction buttons & onClick() methods 
		 */
		sequence_btn = (Button) this.findViewById(R.id.sequence_btn);
		sequence_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				qa = new QuickAction(v);
				qa.addActionItem(var_decl);
				qa.addActionItem(print_shell);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
								
		selection_btn = (Button) this.findViewById(R.id.selection_btn);
		selection_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				qa = new QuickAction(v);
				qa.addActionItem(if_shell);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
		
		iteration_btn = (Button) this.findViewById(R.id.iteration_btn);
		iteration_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				qa = new QuickAction(v);
				qa.addActionItem(for_shell);
				qa.addActionItem(do_while_shell);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
		
		tools_btn = (Button) this.findViewById(R.id.tools_btn);
		tools_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				qa = new QuickAction(v);
				qa.addActionItem(quote_tool);
				qa.addActionItem(paren_tool);
				qa.addActionItem(brace_tool);
				qa.addActionItem(bracket_tool);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
				
		lock_btn = (Button) this.findViewById(R.id.lock_btn);
		lock_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				if(tutorialID.equals("getting_started.xml")){
					Toast.makeText(Main_Tutorial.this, "Not available in this Tutorial", Toast.LENGTH_SHORT).show();
				}else{
					try
					{
					    file = new File(getFilesDir(),"tutorial.txt");
					    writer = new BufferedWriter(new FileWriter(file));
					    writer.write(editor.getText().toString());
					    writer.flush();
					    writer.close();
					    checkAnswer(editor.getText().toString(), myTutorial);
					} catch (IOException e) 
					{
					   e.printStackTrace();
					}
				}
			}
		});
		
		simulate_btn = (Button) this.findViewById(R.id.sim_btn);
		simulate_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if(simulateFlag == 0){
					Toast.makeText(Main_Tutorial.this, "No Simulation Available" , Toast.LENGTH_SHORT).show();
				}else if(canSimulate && simulateFlag == 1){
					InterpreteCode();
					
					vf = (ViewFlipper) findViewById(R.id.tutorial_view_flipper);
					
					vf.setInAnimation(sIn_right);
					vf.setOutAnimation(sOut_right);
					
					vf.showNext();
				}else if (canSimulate && simulateFlag == 2){
					//Graphical Simulation here
				}else{
					Toast.makeText(Main_Tutorial.this, "Simulation Available, but answer is not correct", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		to_code_button = (Button) this.findViewById(R.id.code_btn);
		to_code_button.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				vf = (ViewFlipper) findViewById(R.id.tutorial_view_flipper);

				vf.setInAnimation(sIn_right);
				vf.setOutAnimation(sOut_right);
				
				vf.showNext();
			}
		});
		
		back_to_code = (Button) this.findViewById(R.id.ide_back_to_code_btn);
		back_to_code.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				vf = (ViewFlipper) findViewById(R.id.tutorial_view_flipper);

				vf.setInAnimation(sIn_left);
				vf.setOutAnimation(sOut_left);
				
				vf.showPrevious();
			}
		});
		
		/*
		 * set the sliding drawer open listeners/handlers
		 */
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() 
		{
			@Override
			public void onDrawerOpened() 
			{
				if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
					slideHandleButton.setBackgroundResource(R.drawable.vertical_close_arrow);
		        }else{
		        	slideHandleButton.setBackgroundResource(R.drawable.closearrow);
		        }
					help_text = (TextView) findViewById(R.id.help_text);
				try{
					help_text.setText(myTutorial.getHint());
				}catch(Exception e){
					Log.v("BitBot", "Error setting hint text");
				}
			}
		});

		/*
		 *  set sliding drawer closed listerner/handlers
		 */
		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() 
		{
			@Override
			public void onDrawerClosed() 
			{
				if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
					slideHandleButton.setBackgroundResource(R.drawable.vertical_open_arrow);
		        }else{
		        	slideHandleButton.setBackgroundResource(R.drawable.openarrow);
		        }
			}
		});
		
	}
	
	// Temp variable declaration
	private InstructionLimitedVirtualMachine ilvm;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ide_tutorial_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.clear:
				editor.setText("");
				break;
				
		}
		return true;
	}
	
	/*
	 * creates the Action Item with the defined attributes: 
	 * 		title, message string, text to be added when clicked
	 */
	private void setActionItem(ActionItem item, final EditText editor, String title, final String popUpString, final String declaration)
	{
		item.setTitle(title);
		item.setIcon(getResources().getDrawable(R.drawable.icon));
		item.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				//Toast.makeText(Main_Tutorial.this, popUpString , Toast.LENGTH_SHORT).show();
				int start = editor.getSelectionStart();
				int end = editor.getSelectionEnd();
				editor.getText().replace(Math.min(start, end), Math.max(start, end),
				       declaration);
			}
		});
	}
		
	/*
	 * inputs: File, Resource ID of Tutorial File
	 * Output: User input to file, Toast to let user know if they were correct or not
	 * Method to check if the user input matches the correct tutorial answer
	 */
	protected void checkAnswer(String file, Tutorial currTutorial) throws IOException 
	{
		/*
		 * Temporary - Need to send strings(s) to interpreter and compare abstract 
		 * 				Syntax Tree's
		 */
		
		String temp1 = "";
		String temp2 = "";
		
		temp1 = file;
		temp2 = currTutorial.getAnswer();
		
		//Let the user know if they are right or not.
		if(temp1.equalsIgnoreCase(temp2))
		{
			int currStage = currTutorial.getStage();
			currStage++;
			String nextStage = "Correct Answer Stage: " + currStage + " Completed, Next Stage Available";
			String lastStage = "Correct Anwser, All stages of this Tutorial are finished. Simulation Ready";
			if(currTutorial.nextStage() == -1){
				Toast.makeText(Main_Tutorial.this, lastStage,Toast.LENGTH_SHORT).show();
				canSimulate = true;
			}else{
				Toast.makeText(Main_Tutorial.this, nextStage,Toast.LENGTH_SHORT).show();
			}
			/*
			 * call function to simulate code here if not using sim button...
			 */
		}else
		{
			Toast.makeText(Main_Tutorial.this,"Wrong Answer",Toast.LENGTH_SHORT).show();
			canSimulate = false;
		}
	}
	
	
	
	 /**
     * Temporary code to test the VM
     */
    private void InterpreteCode()
    {
    	Log.i("BitBot Interpreter", "----------------------------------------------------------");
    	Log.i("BitBot Interpreter", "--------------------- Begin Interpreter ------------------");
    	Log.i("BitBot Interpreter", Thread.currentThread().toString());
    	Log.i("BitBot Interpreter", "" + Thread.currentThread().getPriority());
    	Log.i("BitBot Interpreter", "----------------------------------------------------------");
    	BotInterpreter bi = null;
    	
//    	Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    	
    	try
		{
	    	ilvm = new InstructionLimitedVirtualMachine();
	    	
	    	bi = new BotInterpreter(null, editor.getText().toString()+"\n");
	    	bi.setOutputTextView(botOutput);
	    	botOutput.setText(bi.getBotLog());
	    	
	    	ilvm.addInterpreter(bi);
	    	ilvm.resume(2000);
		}
    	catch (Exception e)
		{
			// TODO Auto-generated catch block
			Log.e("BitBot Interpreter", e.getStackTrace().toString());
//			botOutput.setText(e.toString());
		}
    	catch (Error e)
    	{
    		Log.e("BitBot Interpreter", "ERROR: " + e.getStackTrace().toString());
//    		botOutput.setText(e.toString());
    	}
    	finally
    	{
    		if (botOutput != null && bi != null)
    			botOutput.setText(bi.getBotLog());
    	}
    	
    	Log.i("BitBot Interpreter", "----------------------------------------------------------");
    	Log.i("BitBot Interpreter", "---------------------- End Interpreter -------------------");
    	Log.i("BitBot Interpreter", Thread.currentThread().toString());
    	Log.i("BitBot Interpreter", "----------------------------------------------------------");
    }
}