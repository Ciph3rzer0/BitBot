package edu.sru.andgate.bitbot.ide.botbuilder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.sru.andgate.bitbot.R;

public class BotComponentView extends RelativeLayout
{
	private Context context;
	
	private ImageView pic;
	private TextView title;
	private TextView summary;
	private String botTitle;
	private int botPic;
	
	public BotComponentView(Context context)
	{
		super(context);
		this.context = context;
		inflate(context, R.layout.ide_botbuilder_botcomponent, null);
		
	}
	
	public BotComponentView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    this.context = context;
		
	    setClickable(true);
	    setFocusable(true);
	    
	    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
	    if(inflater != null)
	    	inflate(context, R.layout.ide_botbuilder_botcomponent, this);
		
//	    Resources res = getResources();
//	    
		pic = (ImageView)findViewById(R.id.pic);
		title = (TextView)findViewById(R.id.title);
		summary = (TextView)findViewById(R.id.summary);
		botPic = R.drawable.adambot;
		botTitle="Default";
		
//	    // Read in attributes
//		int attributeCount = attrs.getAttributeCount();
//		for(int i = 0; i < attributeCount; i++) {
//			if("pic".equals(attrs.getAttributeName(i)))
//			{
//				iv.setImageResource(attrs.getAttributeIntValue(i, R.drawable.adambot));
//			}
//			
//			if("title".equals(attrs.getAttributeName(i)))
//			{
//				int id = attrs.getAttributeIntValue(i, 0);
//				String text = String.format(res.getString(id), username, mailCount);
//				CharSequence styledText = Html.fromHtml(text);
//				tvTitle.setText("sa");
//			}
//			if("summary".equals(attrs.getAttributeName(i))) {
//				
//			}
//		}   
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		return true;
	}
	
	public void setTitle(String s)
	{
		title.setText(s);
		this.botTitle = s;
	}
	
	public void setSummary(String s)
	{
		summary.setText(s);
	}
	
	public void setPicID(int resID)
	{
		pic.setImageResource(resID);
		this.botPic = resID;
	}
	
	public String getTitle(){
		return this.botTitle;
	}
	
	public int getPicID(){
		return this.botPic;
	}
}
