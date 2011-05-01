package edu.sru.andgate.bitbot.ide.botbuilder;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.sru.andgate.bitbot.R;

public class BotComponentAdapter extends ArrayAdapter<CustomListView> {
	private ArrayList<CustomListView> botComponents;
	private Context context;
	
	public BotComponentAdapter(Context context, int textViewID, ArrayList<CustomListView> botComponents){
		super(context, textViewID, botComponents);
		this.botComponents = botComponents;
		this.context = context;
	}

	@Override
	public View getView(int position, View newView, ViewGroup parentView){
	       View customView = newView;
           if (customView == null) {
               LayoutInflater layout = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               customView = layout.inflate(R.layout.ide_botbuilder_botcomponent, null);
           }
           CustomListView myView = botComponents.get(position);
           if (myView != null) {
                   TextView componentName = (TextView) customView.findViewById(R.id.title);
                   TextView componentDescription = (TextView) customView.findViewById(R.id.summary);
                   ImageView missionIcon = (ImageView) customView.findViewById(R.id.pic);
                   if (componentName != null) {
                         componentName.setText(myView.getBotComponentName());                            }
                   if(componentDescription != null){
                         componentDescription.setText(myView.getBotComponentDescription());
                   }
                   if(missionIcon !=null){
                	   missionIcon.setImageResource(myView.getImageIcon());
                   }
           }
           customView.setTag(myView.getBotComponentName());
           return customView;
   }
	

}