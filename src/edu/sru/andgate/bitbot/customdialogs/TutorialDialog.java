package edu.sru.andgate.bitbot.customdialogs;

import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.graphics.NickGameActivity;
import edu.sru.andgate.bitbot.tutorial.Tutorial_List;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TutorialDialog extends Dialog 
{
    Button graphic_btn, console_btn;
    Activity act;
    Context context;
    
    public TutorialDialog(Activity a, Context context, int theme) 
    {
        super(context, theme);
        this.context = context;    
        this.act = a;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_choice);       
        
        graphic_btn = (Button) findViewById(R.id.graphical);
        graphic_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//start graphical tutorial list			
				dismissDialog();
			}
		});
        
        console_btn = (Button) findViewById(R.id.textual);
        console_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//start bot language tutorial list
				Intent engineIntent = new Intent(act, Tutorial_List.class);
				act.startActivity(engineIntent);
				dismissDialog();
			}
		});
        
    }
    
    public void dismissDialog(){
    	this.dismiss();
    }
 
}