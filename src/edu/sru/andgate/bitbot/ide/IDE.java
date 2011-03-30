package edu.sru.andgate.bitbot.ide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.interpreter.BotInterpreter;
import edu.sru.andgate.bitbot.interpreter.InstructionLimitedVirtualMachine;
import edu.sru.andgate.bitbot.tutorial.ActionItem;
import edu.sru.andgate.bitbot.tutorial.QuickAction;

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
import android.widget.ImageButton;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class IDE extends Activity {
    /** Called when the activity is first created. */
	
	/**
	 * Used for sliding the ViewFlipper
	 */
	private Animation sIn_left, sOut_left, sIn_right, sOut_right;
	
	private EditText editor;
	private TextView botOutput;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ide);
        
        /*
		 * Action Items for Sequence, Selection, Iteration buttons
		 */
		final ActionItem for_shell = new ActionItem();
		final ActionItem do_while_shell = new ActionItem();
		final ActionItem var_decl = new ActionItem();
		final ActionItem print_shell = new ActionItem();
		final ActionItem if_shell = new ActionItem();
		
		/*
		 * Action Items for Quick Tools button
		 */
		final ActionItem paren_tool = new ActionItem();
		final ActionItem quote_tool = new ActionItem();
		final ActionItem brace_tool = new ActionItem();
		final ActionItem bracket_tool = new ActionItem();
		
		/*
		 * Action Items for Bot Functions button
		 */
		final ActionItem move_bot = new ActionItem();
		final ActionItem rotate_turret = new ActionItem();
		
		
		//create the text editor and cabinet button
		editor = (EditText) findViewById(R.id.editor);
		botOutput = (TextView) findViewById(R.id.ide_std_out);
		
		final SlidingDrawer slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
		final Button slideHandleButton = (Button) findViewById(R.id.slideHandleButton);
		
		
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
		setActionItem(move_bot,editor,"bot.move()", "Move Function Selected", getResources().getString(R.string.bot_move));
		setActionItem(rotate_turret, editor, "turret.rotate()", "Turret Rotation Selected", getResources().getString(R.string.turret_rotate));
		
		/*
		 * Set all the QuickAction buttons onClick() methods 
		 */
		Button sequence_btn = (Button) this.findViewById(R.id.sequence_btn);
		sequence_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				QuickAction qa = new QuickAction(v);
				qa.addActionItem(var_decl);
				qa.addActionItem(print_shell);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
								
		Button selection_btn = (Button) this.findViewById(R.id.selection_btn);
		selection_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				QuickAction qa = new QuickAction(v);
				qa.addActionItem(if_shell);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
		
		Button iteration_btn = (Button) this.findViewById(R.id.iteration_btn);
		iteration_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				QuickAction qa = new QuickAction(v);
				qa.addActionItem(for_shell);
				qa.addActionItem(do_while_shell);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
		
		Button tools_btn = (Button) this.findViewById(R.id.tools_btn);
		tools_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				QuickAction qa = new QuickAction(v);
				qa.addActionItem(quote_tool);
				qa.addActionItem(paren_tool);
				qa.addActionItem(brace_tool);
				qa.addActionItem(bracket_tool);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
		
		Button bot_code = (Button) this.findViewById(R.id.bot_btn);
		bot_code.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				QuickAction qa = new QuickAction(v);
				qa.addActionItem(move_bot);
				qa.addActionItem(rotate_turret);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
		
		sIn_left = AnimationUtils.loadAnimation(this, R.anim.slidein_left);
		sOut_left = AnimationUtils.loadAnimation(this, R.anim.slideout_left);
		sIn_right = AnimationUtils.loadAnimation(this, R.anim.slidein_right);
		sOut_right = AnimationUtils.loadAnimation(this, R.anim.slideout_right);
		
		ImageButton delete_line = (ImageButton) this.findViewById(R.id.delete_line);
		delete_line.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		ImageButton delete_word = (ImageButton) this.findViewById(R.id.delete_word);
		delete_word.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					
			}
		});
		
		ImageButton move_left = (ImageButton) this.findViewById(R.id.move_left);
		move_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					editor.setSelection(editor.getSelectionStart() -1);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		ImageButton move_right = (ImageButton) this.findViewById(R.id.move_right);
		move_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					editor.setSelection(editor.getSelectionStart() +1);
				}catch(Exception e){
					e.printStackTrace();
				}
					
			}
		});
		
		ImageButton tab_over = (ImageButton) this.findViewById(R.id.tab_over);
		tab_over.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				expandEditText("\t");				
			}
		});
		
		ImageButton send_btn = (ImageButton) this.findViewById(R.id.send_btn);
		send_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				InterpreteCode();
				
				ViewFlipper vf = (ViewFlipper) findViewById(R.id.ide_view_flipper);
				
				vf.setInAnimation(sIn_right);
				vf.setOutAnimation(sOut_right);
				
				vf.showNext();
				
			}
		});
		
		Button back_to_code = (Button) this.findViewById(R.id.ide_back_to_code_btn);
		back_to_code.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				ViewFlipper vf = (ViewFlipper) findViewById(R.id.ide_view_flipper);

				vf.setInAnimation(sIn_left);
				vf.setOutAnimation(sOut_left);
				
				vf.showPrevious();
			}
		});
		
		/*
		 * set the sliding drawer open/closed listeners and handlers
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
			}
		});

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
	
    
    private void InterpreteCode()
    {
    	try
		{
	    	InstructionLimitedVirtualMachine ilvm = new InstructionLimitedVirtualMachine();
	    	BotInterpreter bi = new BotInterpreter(null, editor.getText().toString());
	    	
	    	ilvm.addInterpreter(bi);
	    	ilvm.resume(10000);
	    	
		}
    	catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			botOutput.setText(e.toString());
		}
    	catch (Error e)
    	{
    		e.printStackTrace();
    		botOutput.setText(e.toString());
    	}
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
				expandEditText(declaration);
			}
		});
	}
	
	public void expandEditText(String append){
		int start = editor.getSelectionStart();
		int end = editor.getSelectionEnd();
		editor.getText().replace(Math.min(start, end), Math.max(start, end),
		       append);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.ide_tutorial_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.clear_btn:    editor.setText("");
	        						break;
	  
	    }
	    return true;
	}
	
	//read in a text file
	 private String readTxt(int id) throws IOException
	 {
		 String line = null;
		 String temp = "";
		 
		 InputStream input = getResources().openRawResource(id);
		 InputStreamReader inputreader = new InputStreamReader(input);
		  BufferedReader bufferedReader = new BufferedReader(inputreader);
		  while((line = bufferedReader.readLine()) != null && !line.equals("----------"))
		  {
			  temp+=line.toString() + "\n";
		  }
		 bufferedReader.close();
		  
		 return temp;
	    }
}
