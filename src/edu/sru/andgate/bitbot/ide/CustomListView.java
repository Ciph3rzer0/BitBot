/*
 * Custom List View item that has a picture along with
 * Two Lines of Text for Mission name, and Description.
 */

package edu.sru.andgate.bitbot.ide;

public class CustomListView {
	private String codeName; //file name
	private String codeDescription; //file description
	
	public CustomListView(String codeName, String description){
		//set the corresponding attributes
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
