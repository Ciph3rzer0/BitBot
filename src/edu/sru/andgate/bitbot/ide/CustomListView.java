/*
 * Custom List View item that has a picture along with
 * Two Lines of Text for Mission name, and Description.
 */

package edu.sru.andgate.bitbot.ide;

public class CustomListView {
	private String codeName;
	private String codeDescription;
	
	public CustomListView(String codeName, String description){
		setCodeName(codeName);
		setCodeDescription(description);
	}
	
	public String getCodeName(){
		return codeName;
	}
	
	public void setCodeName(String missionName){
		this.codeName = missionName;
	}
	
	public String getCodeDescription(){
		return codeDescription;	}
	
	public void setCodeDescription(String missionDescription){
		this.codeDescription = missionDescription;
	}	

}
