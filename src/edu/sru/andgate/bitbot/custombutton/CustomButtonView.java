package edu.sru.andgate.bitbot.custombutton;

import edu.sru.andgate.bitbot.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomButtonView extends LinearLayout
{
	private Context context;
	private TextView title;
	
	
	
	public CustomButtonView(Context context)
	{
		super(context);
		this.context = context;
		inflate(context, R.layout.custom_button, null);
		
	}
	
	public CustomButtonView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    this.context = context;

	    setClickable(true);
	    setFocusable(true);
	    
	    inflate(context, R.layout.custom_button, this);
		  
		title = (TextView)findViewById(R.id.title);
	}
	
	public void setTitle(String s)
	{
		title.setText(s);
	}	
		
}
