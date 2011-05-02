package edu.sru.andgate.bitbot.graphics;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GameView extends GLSurfaceView implements OnKeyListener
{
	final private Context _context;
	
	/**
	 * This variable is used by the GameActivity to determine if there is a new touch
	 * event to handle, or if it has already handled it.
	 */
	public boolean touchNeedsToBeHandled = false;
	
	public float touchX = 0;
	public float touchY = 0;
	
	public GameView(Context context)
	{
		super(context);
		_context = context;
		// TODO Auto-generated constructor stub
	}
	
	public GameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		_context = context;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean onTouchEvent(final MotionEvent event)
	{
//		if (event.getAction() != MotionEvent.ACTION_DOWN)
//			return true;
		
		// Store the touch coordinates
		touchX = event.getX();
		touchY = event.getY();
		
		// Flag that this touch event needs handled
		touchNeedsToBeHandled = true;
		
		// Pause for a bit
//		try {
//			Thread.sleep(20);
//		} catch (InterruptedException e) {}
		
		return true;
	}
}
