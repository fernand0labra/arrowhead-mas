package eu.arrowhead.common.dto.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalysisRequestDTO implements Serializable {

	//=================================================================================================
	// members

	private static final long serialVersionUID = -5363562707054976998L;

	private String serviceDefinition;
	private HashMap<String, ArrayList<String>> systems;
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public AnalysisRequestDTO(final String serviceDefinition, final HashMap<String, ArrayList<String>> systems) {
		this.serviceDefinition = serviceDefinition;
		this.systems = new HashMap<String, ArrayList<String>>(systems);
	}

	//-------------------------------------------------------------------------------------------------
	public String getServiceDefinition() { return serviceDefinition; }
	public HashMap<String, ArrayList<String>> getSystems() { return systems; }
}
