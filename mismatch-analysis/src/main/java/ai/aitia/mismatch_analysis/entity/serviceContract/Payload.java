package ai.aitia.mismatch_analysis.entity.serviceContract;

import java.util.HashMap;

/**
 * Payload Object Definition :: Service Contract Definition of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class Payload {
	
	//=================================================================================================
	// members
	
	private HashMap<String, HashMap<String, String>> contents;

	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public Payload() {
		this.contents = new HashMap<String, HashMap<String, String>>();
	}
	
	//-------------------------------------------------------------------------------------------------
	public HashMap<String, HashMap<String, String>> getContents() { return contents; }
	public void setContents(HashMap<String, HashMap<String, String>> contents) { this.contents = contents; }	
}
