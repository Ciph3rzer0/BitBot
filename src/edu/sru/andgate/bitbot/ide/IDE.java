package edu.sru.andgate.bitbot.ide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.sru.andgate.bitbot.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class IDE extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ide);
      //create the action items for the CustomPopUpWindow
		final ActionItem for_shell = new ActionItem();
		final ActionItem do_while_shell = new ActionItem();
		final ActionItem var_decl = new ActionItem();
		final ActionItem print_shell = new ActionItem();
		final ActionItem if_shell = new ActionItem();
		final ActionItem paren_tool = new ActionItem();
		final ActionItem quote_tool = new ActionItem();
		
		//create the text editor and cabinet button
		final EditText editor = (EditText) this.findViewById(R.id.editor);
		editor.setTextSize(12.0f);
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
	
			}
		});
		
		Button send_btn = (Button) this.findViewById(R.id.send_btn);
		send_btn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
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
				slideHandleButton.setBackgroundResource(R.drawable.closearrow);
				
			}
		});

		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() 
		{
			@Override
			public void onDrawerClosed() 
			{
				slideHandleButton.setBackgroundResource(R.drawable.openarrow);
			}
		});
		
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
	
	public void addText(EditText edit){
		
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