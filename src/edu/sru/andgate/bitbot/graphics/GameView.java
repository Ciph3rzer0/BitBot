package edu.sru.andgate.bitbot.graphics;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class GameView extends GLSurfaceView implements OnKeyListener
{
	final private Context _context;
	
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
	
}
