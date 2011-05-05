/*
 * Custom List View item that has a picture along with
 * Two Lines of Text for Mission name, and Description.
 */

package edu.sru.andgate.bitbot.ide.botbuilder;

public class CustomListView {
	//Declare Bot's Component attributes
	private String botComponentName;
	private String botComponentDescription;
	private int image_icon;
		
	public void setBotComponentName(String botComponenentName){
		this.botComponentName = botComponenentName; //set the components name
	}
	
	public String getBotComponentName(){
		return this.botComponentName;
	}
		
	public String getBotComponentDescription(){
		return this.botComponentDescription;
	}
	
	public void setBotComponentDescription(String botComponentDescription){
		this.botComponentDescription = botComponentDescription;
	}
	
	public int getImageIcon(){
		return image_icon;
	}
	
	public void setImageIcon(int image){
		this.image_icon = image;
	}
}
