package ai.aitia.mismatch_analysis.entity.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * AnalysisRequestDTO Object Definition :: Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class AnalysisRequestDTO implements Serializable {

	//=================================================================================================
	// members

	private static final long serialVersionUID = -5363562707054976998L;

	private String serviceDefinition;
	private HashMap<String, ArrayList<String>> systems;
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public AnalysisRequestDTO(final String serviceDefinition, final HashMap<String, ArrayList<String>> systems) {
		this.serviceDefinition = serviceDefinition;
		this.systems = new HashMap<String, ArrayList<String>>(systems);
	}

	//-------------------------------------------------------------------------------------------------
	public String getServiceDefinition() { return serviceDefinition; }
	public HashMap<String, ArrayList<String>> getSystems() { return systems; }
}
