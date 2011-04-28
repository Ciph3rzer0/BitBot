package edu.sru.andgate.bitbot.customdialogs;

import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.tools.FileManager;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntroCustomDialog extends Dialog 
{
    TextView selection;
    Button next;
    Context context;
    IntroPopupText ipt;
    String file;
    
    public IntroCustomDialog(String file, Context context, int theme) 
    {
        super(context, theme);
        this.context = context;
        this.file = file;
                
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_intro_popup);
        
        FileManager.setContext(context);
        
        ipt = new IntroPopupText(FileManager.readFile(file));
        selection = (TextView) findViewById(R.id.intro_text);
        
        selection.setText(ipt.getHint());
        Button next = (Button) findViewById(R.id.next_btn);
        next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(ipt.nextStage() != -1){
					selection.setText(ipt.getHint());
				}else{
					dismissDialog();
				}
			}
		});   
    }
    
    
    private void dismissDialog(){
    	this.dismiss();
    }
   

}