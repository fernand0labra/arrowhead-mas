package ai.aitia.mismatch_analysis.controller.decision;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import ai.aitia.mismatch_analysis.entity.Analysis;

/**
 * Main :: Decision Module of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class DecisionMain {	
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public static Analysis main(ArrayList<Analysis> analysisList) {
		// Order the analysis by their compatibility
		analysisList.sort(new AnalysisComparator());
		Analysis finalAnalysis = new Analysis();
		
		for(Analysis analysis : analysisList)
			System.out.println(analysis.toString());
		
		for(Analysis analysis : analysisList) {
			if(analysis.getQualitativeM() == "nil") // If there is a complete mismatch
				analysis.setFlag("NOT_OK");
			else if(analysis.getQualitativeM() == "absolute") // If there are no mismatches
				analysis.setFlag("OK");
			else {
				// For each of the mismatches
				for(Entry<String, HashMap<String, Integer>> entry : analysis.getMismatch().entrySet()) { 
					switch(entry.getKey()) {
						case "protocol": // Mismatch in the encoding
							if(entry.getValue().get("protocol")==0)
								analysis.setFlag("ALTER_T");
							break;
						case "encoding": // Mismatch in the encoding
							if(entry.getValue().get("mediaTypeReq")==0 || entry.getValue().get("mediaTypeRes")==0) {
								analysis.setFlag("ALTER_G");
							}
							break;
						case "standard": // Mismatch in the semantics standard
							if(entry.getValue().get("nameReq")==0 || entry.getValue().get("nameRes")==0) {
								analysis.setFlag("ALTER_G");
							}
							break;
						case "ontology": // Mismatch in the ontology
							if(entry.getValue().get("nameReq")==0 || entry.getValue().get("nameRes")==0)
								analysis.setFlag("NOT_OK");
							break;
					}
				}
				
				// For each of the elements of the request
				for(Entry<String, HashMap<String, Integer>> entry : analysis.getNotation().get("request").entrySet()) { 
					if(entry.getValue().get("name")==0 || entry.getValue().get("type")==0) {
						analysis.setFlag("NOT_OK");
						break;
					}		
				}
				
				// For each of the elements of the response
				for(Entry<String, HashMap<String, Integer>> entry : analysis.getNotation().get("response").entrySet()) { 
					if(entry.getValue().get("name")==0 || entry.getValue().get("type")==0) {
						analysis.setFlag("NOT_OK");
						break;
					}						
				}
			} 
				
			finalAnalysis = analysis;
			
			// If the next analysis with the highest compatibility is positive
			if(!finalAnalysis.getFlag().equals("NOT_OK")) {
				return finalAnalysis;	
			}
		}
		
		// If none of the analyis has a positive result
		finalAnalysis.setFlag("NOT_OK");
		return finalAnalysis;		
	}
}

class AnalysisComparator implements Comparator<Analysis> {
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
    @Override
    public int compare(Analysis a1, Analysis a2)
    {
        if (a1.getQuantitativeM() < a2.getQuantitativeM())
            return 1;
        else if (a1.getQuantitativeM() > a2.getQuantitativeM())
            return -1;
        else
            return 0;
    }
}
