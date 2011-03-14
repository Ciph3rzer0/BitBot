package edu.sru.andgate.bitbot.tutorial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.sru.andgate.bitbot.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class Main_Tutorial extends Activity {
	
	//set the tutorial id to the passed in int id
	public int tutorialID =R.raw.for_loop_tutorial;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_main);

		//create the action items for the CustomPopUpWindow
		final ActionItem for_shell = new ActionItem();
		final ActionItem do_while_shell = new ActionItem();
		final ActionItem var_decl = new ActionItem();
		final ActionItem print_shell = new ActionItem();
		final ActionItem if_shell = new ActionItem();
		
		//create the text editor and cabinet button
		final EditText editor = (EditText) this.findViewById(R.id.editor);
		final SlidingDrawer slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
		final Button slideHandleButton = (Button) findViewById(R.id.slideHandleButton);
		
		editor.setTextSize(12.0f);
		
		/*
		 * sets attributes of the action items in the CustomPopUpWindow
		 */
		setActionItem(var_decl,editor, "Declare Variable", "Variable Declaration Selected", getResources().getString(R.string.var_declaration));
		setActionItem(print_shell,editor, "Print to console", "Print Statement Selected", getResources().getString(R.string.print_statement));
		setActionItem(if_shell,editor, "if statement shell", "if statement Selected", getResources().getString(R.string.if_statement));
		setActionItem(do_while_shell, editor, "do while shell", "do while statement selected", getResources().getString(R.string.do_while_statement));
		setActionItem(for_shell,editor, "for statement shell", "for statement selected", getResources().getString(R.string.for_statement));
		
		/*
		 * Set all the QuickAction buttons onClick() methods 
		 */
		Button sequence_btn = (Button) this.findViewById(R.id.sequence_btn);
		sequence_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				QuickAction qa = new QuickAction(v);
				qa.addActionItem(var_decl);
				qa.addActionItem(print_shell);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
								
		Button selection_btn = (Button) this.findViewById(R.id.selection_btn);
		selection_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				QuickAction qa = new QuickAction(v);
				qa.addActionItem(if_shell);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
		
		Button iteration_btn = (Button) this.findViewById(R.id.iteration_btn);
		iteration_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				QuickAction qa = new QuickAction(v);
				qa.addActionItem(for_shell);
				qa.addActionItem(do_while_shell);
				qa.setAnimStyle(QuickAction.ANIM_AUTO);
				qa.show();
			}
		});
		
		Button clear_btn = (Button) this.findViewById(R.id.clear_btn);
		clear_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editor.setText("");
			}
		});
		
		Button lock_btn = (Button) this.findViewById(R.id.lock_btn);
		lock_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
				    File file = new File(getFilesDir(),"tutorial.txt");
				    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				    writer.write(editor.getText().toString());
				    writer.flush();
				    writer.close();
				    checkAnswer(file, tutorialID);
				} catch (IOException e) {
				   e.printStackTrace();
				}
			}
		});
		
		Button simulate_btn = (Button) this.findViewById(R.id.sim_btn);
		simulate_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//code to create simulation - I.E. send file to bot code
			}
		});
		
		Button back_btn = (Button) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), Tutorial_List.class);
                startActivity(myIntent);
                finish();
			}
		});
		
		/*
		 * set the sliding drawer open/closed listeners and handlers
		 */
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				slideHandleButton.setBackgroundResource(R.drawable.openarrow);
			}
		});

		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				slideHandleButton.setBackgroundResource(R.drawable.closearrow);
			}
		});
		
	}
	
	/*
	 * creates the Action Item with the defined attributes: 
	 * 		title, message string, text to be added when clicked
	 */
	private void setActionItem(ActionItem item, final EditText editor, String title, final String popUpString, final String declaration){
		item.setTitle(title);
		item.setIcon(getResources().getDrawable(R.drawable.android));
		item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(Main_Tutorial.this, popUpString , Toast.LENGTH_SHORT).show();
				editor.append("\n" + declaration);
			}
		});
	}
	
	/*
	 * inputs: File, Resource ID of Tutorial File
	 * Output: User input to file, Toast to let user know if they were correct or not
	 * Method to check if the user input matches the correct tutorial answer
	 */
	protected void checkAnswer(File file, int resId) throws IOException {
			String line1 = null;
			String line2 = null;
			String temp1 = "";
			String temp2 = "";
			
		  // wrap a BufferedReader around FileReader
		  InputStream input = getResources().openRawResource(resId);
		  BufferedReader bufferedReader1 = new BufferedReader(new FileReader(file.getAbsolutePath()));
		  InputStreamReader inputreader = new InputStreamReader(input);
		  BufferedReader bufferedReader2 = new BufferedReader(inputreader);

		  // use the readLine method of the BufferedReader to read one line at a time.
		  // the readLine method returns null when there is nothing else to read.
		  while ((line1 = bufferedReader1.readLine()) != null)
		  {
		    temp1+=line1.toString();
		  }
		  while((line2 = bufferedReader2.readLine()) != null)
		  {
			  temp2+=line2.toString();
		  }
		  // close the BufferedReader(s) when we're done
		  bufferedReader1.close();
		  bufferedReader2.close();
		
		  //Let the user know if they are right or not.
		  if(temp1.equals(temp2)){
			  Toast.makeText(Main_Tutorial.this,"Correct Answer",Toast.LENGTH_SHORT).show();
			  /*
			   * call function to simulate code here...
			   */
		  }else{
			  Toast.makeText(Main_Tutorial.this,"Wrong Answer",Toast.LENGTH_SHORT).show();
		  }
		
	}
	
}