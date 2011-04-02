package edu.sru.andgate.bitbot.missionlist;

public class CustomListView {
	private String missionName;
	private String missionDescription;
	private String filename;
	
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
	
}
