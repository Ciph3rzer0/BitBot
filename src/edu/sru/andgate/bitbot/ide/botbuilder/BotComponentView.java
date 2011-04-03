package edu.sru.andgate.bitbot.ide.botbuilder;

import edu.sru.andgate.bitbot.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BotComponentView extends LinearLayout
{
	private Context context;
	
	public BotComponentView(Context context)
	{
		super(context);
		this.context = context;
		inflate(context, R.layout.ide_botbuilder_botcomponent, null);
	}
	
	public BotComponentView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    this.context = context;
		
	    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
	    if(inflater != null)
	    	inflate(context, R.layout.ide_botbuilder_botcomponent, this);
		
//		this.findViewById(R.id.arrow_down);
	}
	
}
