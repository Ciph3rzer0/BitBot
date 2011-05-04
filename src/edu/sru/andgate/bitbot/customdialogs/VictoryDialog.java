package edu.sru.andgate.bitbot.customdialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.graphics.NickGameActivity;

public class VictoryDialog extends Dialog 
{
	//declare component variables
    TextView kills_text, accuracy_text, time_text;
    Button end;
    NickGameActivity nga;
    Context context;

    //bot statistic vars
    int kills;
    double accuracy;
    long time;
    
    public VictoryDialog(NickGameActivity nga, int theme, int kills, double accuracy, long time) 
    {
        super(nga, theme);
        this.kills = kills;
        this.nga = nga;
        this.accuracy = accuracy;
        this.time = time/1000;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.victory_popup);       
        
        //create the victory dialog
        
        //set the kills
        kills_text = (TextView) findViewById(R.id.kill_text);
        kills_text.setText("" + kills);
        
        //set the accuracy
        accuracy_text = (TextView) findViewById(R.id.accuracy_text);
        accuracy_text.setText("" + accuracy + "%");
        
        //set the time elapsed to complete the mission
        time_text = (TextView) findViewById(R.id.time_text);
        time_text.setText("" + time + " seconds");
        
        //end the mission
        end = (Button) findViewById(R.id.end_btn);
        end.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//finish the game activity
				nga.finish();
				//close dialog
				dismissDialog();
			}
		});
        
    }
    
    //close the dialog
    public void dismissDialog(){
    	this.dismiss();
    }
 
}