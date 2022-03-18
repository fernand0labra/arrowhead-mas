package ai.aitia.mismatch_analysis.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * Analysis Object Definition :: Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class Analysis {
	
	//=================================================================================================
	// members
	
	private HashMap<String, HashMap<String, Integer>> mismatch;
	private HashMap<String, HashMap<String, Integer>> uncertainty;
	private HashMap<String, HashMap<String, HashMap<String, Integer>>> notation;
	
	// Meaning of the tags used
	private HashMap<String, String> tagMeaning;
	
	// Quantitative level of compatibility
	private double quantitativeM;
	// Qualitative level of compatibility
	private String qualitativeM;
	
	// Quantitative level of uncertainty
	private double quantitativeU;
	// Qualitative level of uncertainty
	private String qualitativeU;
	
	// Flag indicating the necessary actions to be performed
	private String flag;
	
	// System for which the analysis has been performed
	private String system;
	
	// Service for which the analysis has been performed
	private String service;
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public Analysis() {
		mismatch = new HashMap<String, HashMap<String, Integer>>();
		uncertainty = new HashMap<String, HashMap<String, Integer>>();
		tagMeaning = new HashMap<String, String>();
		
		/* ************************* */
		/* Protocol Mismatch Section */
		/* ************************* */
		
		mismatch.put("protocol", new HashMap<String, Integer>());
		mismatch.get("protocol").put("protocol", 1);
		mismatch.get("protocol").put("version", 1);
		
		uncertainty.put("protocol", new HashMap<String, Integer>());
		uncertainty.get("protocol").put("protocol", 0);
		uncertainty.get("protocol").put("version", 0);
		
		/* ************************* */
		/* Encoding Mismatch Section */
		/* ************************* */
		
		mismatch.put("encoding", new HashMap<String, Integer>());
		// Request
		mismatch.get("encoding").put("mediaTypeReq", 1);
		mismatch.get("encoding").put("versionReq", 1);
		// Response
		mismatch.get("encoding").put("mediaTypeRes", 1);
		mismatch.get("encoding").put("versionRes", 1);
		
		uncertainty.put("encoding", new HashMap<String, Integer>());
		// Request
		uncertainty.get("encoding").put("mediaTypeReq", 0);
		uncertainty.get("encoding").put("versionReq", 0);
		// Response
		uncertainty.get("encoding").put("mediaTypeRes", 0);
		uncertainty.get("encoding").put("versionRes", 0);
		
		/* ************************* */
		/*         Semantics         */
		/* ************************* */
		
		mismatch.put("standard", new HashMap<String, Integer>());
		// Request
		mismatch.get("standard").put("nameReq", 1);
		mismatch.get("standard").put("versionReq", 1);
		// Response
		mismatch.get("standard").put("nameRes", 1);
		mismatch.get("standard").put("versionRes", 1);
		
		mismatch.put("ontology", new HashMap<String, Integer>());
		// Request
		mismatch.get("ontology").put("nameReq", 1);
		mismatch.get("ontology").put("versionReq", 1);
		// Response
		mismatch.get("ontology").put("nameRes", 1);
		mismatch.get("ontology").put("versionRes", 1);
		
		uncertainty.put("standard", new HashMap<String, Integer>());
		// Request
		uncertainty.get("standard").put("nameReq", 0);
		uncertainty.get("standard").put("versionReq", 0);
		// Response
		uncertainty.get("standard").put("nameRes", 0);
		uncertainty.get("standard").put("versionRes", 0);
		
		uncertainty.put("ontology", new HashMap<String, Integer>());
		// Request
		uncertainty.get("ontology").put("nameReq", 0);
		uncertainty.get("ontology").put("versionReq", 0);
		// Response
		uncertainty.get("ontology").put("nameRes", 0);
		uncertainty.get("ontology").put("versionRes", 0);
		
		/* ************************* */
		/*         Ontology          */
		/* ************************* */
		
		notation = new HashMap<String, HashMap<String, HashMap<String, Integer>>>();
		notation.put("request", new HashMap<String, HashMap<String, Integer>>());
		notation.put("response", new HashMap<String, HashMap<String, Integer>>());
		
		setQuantitativeM(0);
		setQuantitativeU(0);
		
		setQualitativeM("");
		setQualitativeU("");
		
		setFlag("");
		setSystem("");
		setService("");
		
		tagMeaning.put("protocol", "Different names");
		tagMeaning.put("version", "Different versions");
		
		tagMeaning.put("nameReq", "Different names in the request");
		tagMeaning.put("versionReq", "Different versions in the request");
		tagMeaning.put("nameRes", "Different names in the response");
		tagMeaning.put("versionRes", "Different versions in the request");
		
		tagMeaning.put("mediaTypeReq", "Different types in the request");
		tagMeaning.put("mediaTypeRes", "Different types in the response");
	}

	//-------------------------------------------------------------------------------------------------
	public HashMap<String, HashMap<String, Integer>> getMismatch() { return mismatch; }
	public void setMismatch(HashMap<String, HashMap<String, Integer>> mismatch) { this.mismatch = mismatch; }
	
	public HashMap<String, HashMap<String, HashMap<String, Integer>>> getNotation() { return notation; }
	public void setNotation(HashMap<String, HashMap<String, HashMap<String, Integer>>> notation) { this.notation = notation; }
	
	public HashMap<String, HashMap<String, Integer>> getUncertainty() { return uncertainty; }
	public void setUncertainty(HashMap<String, HashMap<String, Integer>> uncertainty) { this.uncertainty = uncertainty; }
	
	public HashMap<String, String> getTagMeaning() { return tagMeaning; }
	public void setTagMeaning(HashMap<String, String> tagMeaning) { this.tagMeaning = tagMeaning; }
	
	public double getQuantitativeM() { return quantitativeM; }
	public void setQuantitativeM(double quantitativeM) { this.quantitativeM = quantitativeM; }
	
	public double getQuantitativeU() { return quantitativeU; }
	public void setQuantitativeU(double quantitativeU) { this.quantitativeU = quantitativeU; }
	
	public String getQualitativeM() { return qualitativeM; }
	public void setQualitativeM(String qualitativeM) { this.qualitativeM = qualitativeM; }
	
	public String getQualitativeU() { return qualitativeU; }
	public void setQualitativeU(String qualitativeU) { this.qualitativeU = qualitativeU; }
	
	public String getFlag() { return flag; }
	public void setFlag(String flag) { this.flag = flag; }
	
	public String getSystem() { return system; }
	public void setSystem(String system) { this.system = system; }
	
	public String getService() { return service; }
	public void setService(String service) { this.service = service; }	
	
	//-------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		
		/* ****************************************** */
		/*   Information about the Analysed System	  */
		/* ****************************************** */
		
		String info = "\n" + "\tSYSTEM: " + getSystem() + "\n" + 
					  		 "\tSERVICE: " + getService() + "\n" +
					  		 "\tFLAG: " + getFlag() + "\n\n";
				
		/* ****************************************** */
		/*     Information about the mismatches       */
		/* ****************************************** */
		
		String mismatchString =
				"\n" + "MISMATCH ARRAY: " + "\n" +
					
				"\tProtocol: " + "\n" +
					"\t\tprotocol: " + "\t" + String.valueOf(mismatch.get("protocol").get("protocol")) + "\n" +
					"\t\tversion: " + "\t" + String.valueOf(mismatch.get("protocol").get("version")) + "\n" +
				
				"\tEncoding: " + "\n" +
					"\t\tREQUEST: \n" +
						"\t\t\tmediaTypeReq: " + "\t" + String.valueOf(mismatch.get("encoding").get("mediaTypeReq")) + "\n" +
						"\t\t\tversionReq: " + "\t" + String.valueOf(mismatch.get("encoding").get("versionReq")) + "\n" +
					"\t\tRESPONSE: \n" +
					"\t\t\tmediaTypeRes: " + "\t" + String.valueOf(mismatch.get("encoding").get("mediaTypeRes")) + "\n" +
					"\t\t\tversionRes: " + "\t" + String.valueOf(mismatch.get("encoding").get("versionRes")) + "\n" +	
					
				"\tSemantics: " + "\n" +
					"\t\tStandard: " + "\n" +
						"\t\t\tREQUEST: \n" +
							"\t\t\t\tnameReq: " + "\t" + String.valueOf(mismatch.get("standard").get("nameReq")) + "\n" +
							"\t\t\t\tversionReq: " + "\t" + String.valueOf(mismatch.get("standard").get("versionReq")) + "\n" +
						"\t\t\tRESPONSE: \n" +
							"\t\t\t\tnameRes: " + "\t" + String.valueOf(mismatch.get("standard").get("nameRes")) + "\n" +
							"\t\t\t\tversionRes: " + "\t" + String.valueOf(mismatch.get("standard").get("versionRes")) + "\n" +	
					
					"\t\tOntology: " + "\n" +
						"\t\t\tREQUEST: \n" +	
							"\t\t\t\tnameReq: " + "\t" + String.valueOf(mismatch.get("ontology").get("nameReq")) + "\n" +
							"\t\t\t\tversionReq: " + "\t" + String.valueOf(mismatch.get("ontology").get("versionReq")) + "\n" +
						"\t\t\tRESPONSE: \n" +
							"\t\t\t\tnameRes: " + "\t" + String.valueOf(mismatch.get("ontology").get("nameRes")) + "\n" +
							"\t\t\t\tversionRes: " + "\t" + String.valueOf(mismatch.get("ontology").get("versionRes")) + "\n";	
		
				String notationString = 
						"\n" + "NOTATION ARRAY: \n";
				
				notationString += "\t\tREQUEST: \n";
				for(Entry<String, HashMap<String, Integer>> variable : notation.get("request").entrySet()) {
					notationString += "\t\t\t" + variable.getKey() + ": " + "\n";
					String value = "";
					
					for(Entry<String, Integer> entry : variable.getValue().entrySet()) {
						switch(entry.getKey()) {
						case "name":
							value = "\t\t\t\tNameReq: " + "\t" + String.valueOf(entry.getValue()) + "\n";
							break;
						case "type":
							value = "\t\t\t\ttypeReq: " + "\t" + String.valueOf(entry.getValue()) + "\n";
							break;
					}
					
						notationString += value;
					}
				}
				
				notationString += "\t\tRESPONSE: \n";
				for(Entry<String, HashMap<String, Integer>> variable : notation.get("response").entrySet()) {
					notationString += "\t\t\t" + variable.getKey() + ": " + "\n";
					String value = "";
					
					for(Entry<String, Integer> entry : variable.getValue().entrySet()) {
						switch(entry.getKey()) {
						case "name":
							value = "\t\t\t\tNameRes: " + "\t" + String.valueOf(entry.getValue()) + "\n";
							break;
						case "type":
							value = "\t\t\t\ttypeRes: " + "\t" + String.valueOf(entry.getValue()) + "\n";
							break;
					}
					
						notationString += value;
					}
				}
				
		String quantitativeMString = "\n" + "QUANTITATIVE VALUE (%): " + "\t" + String.valueOf(quantitativeM) + "\n";
		
		String qualitativeMString = "\n" + "QUALITATIVE VALUE: " + "\t" + qualitativeM + "\n\n";
				
		/* ****************************************** */
		/*     Information about the uncertainty      */
		/* ****************************************** */
		
		String uncertaintyString =
				"\n" + "UNCERTAINTY ARRAY: " + "\n" +
					
				"\tProtocol: " + "\n" +
					"\t\tprotocol: " + "\t" + String.valueOf(uncertainty.get("protocol").get("protocol")) + "\n" +
					"\t\tversion: " + "\t" + String.valueOf(uncertainty.get("protocol").get("version")) + "\n" +
				
				"\tEncoding: " + "\n" +
					"\t\tREQUEST: \n" +
						"\t\t\tmediaTypeReq: " + "\t" + String.valueOf(uncertainty.get("encoding").get("mediaTypeReq")) + "\n" +
						"\t\t\tversionReq: " + "\t" + String.valueOf(uncertainty.get("encoding").get("versionReq")) + "\n" +
					"\t\tRESPONSE: \n" +
					"\t\t\tmediaTypeRes: " + "\t" + String.valueOf(uncertainty.get("encoding").get("mediaTypeRes")) + "\n" +
					"\t\t\tversionRes: " + "\t" + String.valueOf(uncertainty.get("encoding").get("versionRes")) + "\n" +	
					
				"\tSemantics: " + "\n" +
					"\t\tStandard: " + "\n" +
						"\t\t\tREQUEST: \n" +
							"\t\t\t\tnameReq: " + "\t" + String.valueOf(uncertainty.get("standard").get("nameReq")) + "\n" +
							"\t\t\t\tversionReq: " + "\t" + String.valueOf(uncertainty.get("standard").get("versionReq")) + "\n" +
						"\t\t\tRESPONSE: \n" +
							"\t\t\t\tnameRes: " + "\t" + String.valueOf(uncertainty.get("standard").get("nameRes")) + "\n" +
							"\t\t\t\tversionRes: " + "\t" + String.valueOf(uncertainty.get("standard").get("versionRes")) + "\n" +	
					
					"\t\tOntology: " + "\n" +
						"\t\t\tREQUEST: \n" +	
							"\t\t\t\tnameReq: " + "\t" + String.valueOf(uncertainty.get("ontology").get("nameReq")) + "\n" +
							"\t\t\t\tversionReq: " + "\t" + String.valueOf(uncertainty.get("ontology").get("versionReq")) + "\n" +
						"\t\t\tRESPONSE: \n" +
							"\t\t\t\tnameRes: " + "\t" + String.valueOf(uncertainty.get("ontology").get("nameRes")) + "\n" +
							"\t\t\t\tversionRes: " + "\t" + String.valueOf(uncertainty.get("ontology").get("versionRes")) + "\n";	
		
		String quantitativeUString = "\n" + "QUANTITATIVE VALUE (%): " + "\t" + String.valueOf(quantitativeU) + "\n";
		
		String qualitativeUString = "\n" + "QUALITATIVE VALUE: " + "\t" + qualitativeU + "\n";

		return 	"\n\n***********************************************************************************************************\n" +
				"***********************************************************************************************************\n\n" +
				"ANALYSIS(" + getSystem() + ")\n\n" + 
					"INFO:\n" + info +
					"***********************************************************************************************************\n\n" +
					"COMPATIBILITY:\n" + mismatchString + notationString + quantitativeMString + qualitativeMString + 
					"***********************************************************************************************************\n\n" +
					"UNCERTAINTY:\n" + uncertaintyString + quantitativeUString + qualitativeUString;
	}
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Auxiliar method for displaying compatibility and uncertainty summaries
	 */
	public void summarize() {
		System.out.println(
				"***********************************************************************************************************\n\n" +
				"COMPATIBILITY SUMMARY: \n" +
				this.recursiveSummary(this.getMismatch(), new LinkedList<String>(),  "compatibility"));
		
		System.out.println(
				"***********************************************************************************************************\n\n" +
				"UNCERTAINTY SUMMARY: \n" +
				this.recursiveSummary(this.getUncertainty(), new LinkedList<String>(), "uncertainty"));
	}
	
	//-------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	/**
	 * Summarizes the contents of the map depending on whether it is a compatibility or uncertainty 
	 * summary
	 * 
	 * @param actualMap		The map that acts as root at a given level
	 * @param parentNames	The parent names of the actual node
	 * @param summary		The string containing the summary of the map at a given level
	 * @param type			(compatibility) or (uncertainty) summary
	 * @return				A string containing the summary of the map
	 */
	private String recursiveSummary(HashMap<String, ?> actualMap, LinkedList<String> parentNames, String type) {
		String summary = "";
		
		for(Entry<String, Object> entry : ((HashMap<String, Object>) actualMap).entrySet())
			if(entry.getValue() instanceof HashMap<?, ?>) {
				LinkedList<String> newParentNames = new LinkedList<String>(parentNames);
				newParentNames.add(entry.getKey());
				summary += recursiveSummary((HashMap<String, Object>) entry.getValue(), newParentNames, type);
			} else {
				if(type.equals("compatibility")) {
					if(Integer.valueOf(entry.getValue().toString()) == 0) {
						summary += "\tMismatch in " + parentNames.getFirst() + ":\n " + 
										"\t\t" + tagMeaning.get(entry.getKey()) + "\n";
					}
				}
				
				else
					if(Integer.valueOf(entry.getValue().toString()) == 1) {
						summary += "\tUncertainty in " + parentNames.getFirst() + ":\n " + 
										"\t\t" + tagMeaning.get(entry.getKey()) + "\n";
					}
			}
		
		return summary;
	}
}
