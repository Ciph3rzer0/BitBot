package edu.sru.andgate.bitbot.customdialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.tutorial.BotBasic_Tutorial_List;
import edu.sru.andgate.bitbot.tutorial.GameSpecific_Tutorial_List;

public class TutorialDialog extends Dialog 
{
	//declare component variables
    Button graphic_btn, console_btn;
    Activity act;
    Context context;
    
    public TutorialDialog(Activity a, int theme) 
    {
        super(a, theme);
        this.act = a; //set the activity in use
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_choice);       
        
        //display the choices for the tutorials
        
        //open graphical or game specific tutorial list
        graphic_btn = (Button) findViewById(R.id.graphical);
        graphic_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//start graphical tutorial list	
				Intent engineIntent = new Intent(act, GameSpecific_Tutorial_List.class);
				act.startActivity(engineIntent);
				dismissDialog();
			}
		});
        
        //open console or BotBasic language tutorial list
        console_btn = (Button) findViewById(R.id.textual);
        console_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//start bot language tutorial list
				Intent engineIntent = new Intent(act, BotBasic_Tutorial_List.class);
				act.startActivity(engineIntent);
				dismissDialog();
			}
		});
        
    }
    
    //close the dialog
    public void dismissDialog(){
    	this.dismiss();
    }
 
}