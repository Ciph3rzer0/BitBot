package edu.sru.andgate.bitbot.missionlist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.sru.andgate.bitbot.R;

public class MissionListAdapter extends ArrayAdapter<CustomListView> {
	//Declare custom arrayList adapter & context
	private ArrayList<CustomListView> missionItems;
	private Context context;
	
	public MissionListAdapter(Context context, int textViewID, ArrayList<CustomListView> missionItems){
		super(context, textViewID, missionItems);
		this.missionItems = missionItems;
		this.context = context;
	}

	@Override
	public View getView(int position, View newView, ViewGroup parentView){
	       View customView = newView;
           if (customView == null) {
        	 //inflate the latout with the custom view
             LayoutInflater layout = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             customView = layout.inflate(R.layout.mission_row, null);
           }
           CustomListView myView = missionItems.get(position);
           if (myView != null) {
		    	//set the attributes for the custom layout: code name, and description
		    	TextView missionName = (TextView) customView.findViewById(R.id.mission);
		       	TextView missionDescription = (TextView) customView.findViewById(R.id.description);
		       	ImageView missionIcon = (ImageView) customView.findViewById(R.id.icon);
           	if (missionName != null) {
                 missionName.setText(myView.getMissionName());                            }
           	if(missionDescription != null){
                 missionDescription.setText(myView.getMissionDescription());
           	}
           	if(missionIcon !=null){
        	   missionIcon.setImageResource(myView.getImageIcon());
           	}
           }
           customView.setTag(myView.getMissionName());
           return customView;
   }
	

}