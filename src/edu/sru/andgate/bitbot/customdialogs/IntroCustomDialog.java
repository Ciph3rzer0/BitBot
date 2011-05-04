package edu.sru.andgate.bitbot.customdialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.sru.andgate.bitbot.R;
import edu.sru.andgate.bitbot.tools.FileManager;

public class IntroCustomDialog extends Dialog 
{
	//declare component variables
    TextView selection;
    Button next;
    Context context;
    IntroPopupText ipt;
    String file;
    
    public IntroCustomDialog(String file, Context context, int theme) 
    {
        super(context, theme);
        this.context = context; //set the context in use
        this.file = file; 		//set the file in use
                
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_intro_popup);
        
        FileManager.setContext(context); //initialize the context to user FileManager
        
        //populate the popup text with the appropriate stages of text
        ipt = new IntroPopupText(FileManager.readFile(file));
        
        //find the texview in the dialog and set the text to the appropriate hint
        selection = (TextView) findViewById(R.id.intro_text);
        selection.setText(ipt.getHint());
        
        Button next = (Button) findViewById(R.id.next_btn);
        next.setOnClickListener(new View.OnClickListener() {
			//on click set text to next available hint, or close the dialog
			@Override
			public void onClick(View v) {
				if(ipt.nextStage() != -1){
					//set text to next available hint
					selection.setText(ipt.getHint());
				}else{
					//if no more hints, close dialog
					dismissDialog();
				}
			}
		});   
    }
    
    //close the dialog
    private void dismissDialog(){
    	this.dismiss();
    }
   

}