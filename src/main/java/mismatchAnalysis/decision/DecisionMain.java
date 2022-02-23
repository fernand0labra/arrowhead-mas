package mismatchAnalysis.decision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import mismatchAnalysis.mismatchCheck.Analysis;
import mismatchAnalysis.mismatchCheck.MismatchCheckMain;

public class DecisionMain {
	public static ArrayList<String> main(Analysis analysis) {
		System.out.println("-----------------------------------------------------------------------------------------------------------\n");
		System.out.println(analysis.toString(""));
		
		ArrayList<String> flags = new ArrayList<String>();

		if(analysis.getQualitativeM() == "nil")
			flags.add("NOT_OK");
		else if(analysis.getQualitativeM() == "absolute")
			flags.add("OK");
		else {
			// For each of the mismatches
			for(Entry<String, HashMap<String, Integer>> entry : analysis.getMismatch().entrySet()) { 
				switch(entry.getKey()) {
					case "protocol":
						if(entry.getValue().get("protocol")==0)
							flags.add("ALTER_T");
						break;
					case "encoding":
						if(entry.getValue().get("mediaTypeReq")==0 || entry.getValue().get("mediaTypeRes")==0)
							if(!flags.contains("ALTER_G"))
								flags.add("ALTER_G");
						break;
					case "standard":
						if(entry.getValue().get("nameReq")==0 || entry.getValue().get("nameRes")==0)
							if(!flags.contains("ALTER_G"))
								flags.add("ALTER_G");
						break;
					case "ontology":
						if(entry.getValue().get("nameReq")==0 || entry.getValue().get("nameRes")==0)
							flags.add("NOT_OK");
						break;
				}
			}
			
			// For each of the elements of the request
			for(Entry<String, HashMap<String, Integer>> entry : analysis.getNotation().get("request").entrySet()) { 
				if(entry.getValue().get("name")==0 || entry.getValue().get("type")==0) {
					flags = new ArrayList<String>();
					flags.add("NOT_OK");
					return flags;
				}		
			}
			
			// For each of the elements of the response
			for(Entry<String, HashMap<String, Integer>> entry : analysis.getNotation().get("response").entrySet()) { 
				if(entry.getValue().get("name")==0 || entry.getValue().get("type")==0) {
					flags = new ArrayList<String>();
					flags.add("NOT_OK");
					return flags;
				}						
			}
		} 
			
		System.out.println("***********************************************************************************************************\n");
		return flags;
	}
}
