package edu.sru.andgate.bitbot.ide;

import java.util.ArrayList;

import edu.sru.andgate.bitbot.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CodeListAdapter extends ArrayAdapter<CustomListView>
{
	private ArrayList<CustomListView> codeItems;
	private Context context;
	
	public CodeListAdapter(Context context, int textViewID, ArrayList<CustomListView> codeItems)
	{
		super(context, textViewID, codeItems);
		this.codeItems = codeItems;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View newView, ViewGroup parentView)
	{
		View customView = newView;
		if (customView == null)
		{
			LayoutInflater layout = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			customView = layout.inflate(R.layout.code_row, null);
		}
		
		CustomListView myView = codeItems.get(position);
		if (myView != null)
		{
			TextView codeName = (TextView) customView.findViewById(R.id.program);
			TextView codeDescription = (TextView) customView.findViewById(R.id.summary);
			if (codeName != null) {
				codeName.setText("Program Name: "+ myView.getCodeName());                            }
			if(codeDescription != null){
				codeDescription.setText("Summary: "+ myView.getCodeDescription());
			}
		}
		customView.setTag(myView.getCodeName());
		return customView;
	}
}
