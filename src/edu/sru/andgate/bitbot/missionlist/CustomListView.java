/*
 * Custom List View item that has a picture along with
 * Two Lines of Text for Mission name, and Description.
 */

package edu.sru.andgate.bitbot.missionlist;

public class CustomListView {
	//Declare mission attributes
	private String missionName;
	private String missionDescription;
	private String filename;
	private int image_icon;
	
	/* ************* Getter and setter methods ************ */
	public String getMissionName(){
		return missionName;
	}
	
	public void setMissionName(String missionName){
		this.missionName = missionName;
	}
	
	public String getFileName(){
		return this.filename;
	}
	
	public void setFileName(String filename){
		this.filename = filename;
	}
	
	public String getMissionDescription(){
		return missionDescription;
	}
	
	public void setMissionDescription(String missionDescription){
		this.missionDescription = missionDescription;
	}
	
	public int getImageIcon(){
		return image_icon;
	}
	
	public void setImageIcon(int image){
		this.image_icon = image;
	}
}
