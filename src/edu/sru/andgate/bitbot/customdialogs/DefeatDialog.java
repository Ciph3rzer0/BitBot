/*
 * Create a dialog to show when user's Bot is defeated 
 */

package edu.sru.andgate.bitbot.customdialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.graphics.NickGameActivity;

public class DefeatDialog extends Dialog 
{
    Button end;
    NickGameActivity nga;
    
    public DefeatDialog(NickGameActivity nga, int theme) 
    {
        super(nga, theme);
        this.nga = nga;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defeat_popup);       
        
        end = (Button) findViewById(R.id.end_btn);
        
        /*
         * on end button click, finish game Activity, close dialog
         */
        end.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nga.finish();
				dismissDialog();
			}
		});
        
    }
    
    //close dialog
    public void dismissDialog(){
    	this.dismiss();
    }
 
}