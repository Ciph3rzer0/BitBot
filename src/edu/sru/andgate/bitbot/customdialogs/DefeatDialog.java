package edu.sru.andgate.bitbot.customdialogs;

import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.graphics.NickGameActivity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DefeatDialog extends Dialog 
{
    Button end;
    NickGameActivity nga;
    Context context;
    
    public DefeatDialog(NickGameActivity nga, Context context, int theme) 
    {
        super(context, theme);
        this.context = context;    
        this.nga = nga;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defeat_popup);       
        
        end = (Button) findViewById(R.id.end_btn);
        end.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nga.finish();
				dismissDialog();
			}
		});
        
    }
    
    public void dismissDialog(){
    	this.dismiss();
    }
 
}