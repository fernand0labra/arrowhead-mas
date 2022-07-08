package eu.arrowhead.common.dto.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class AnalysisResponseDTO implements Serializable {

	//=================================================================================================
	// members

	private static final long serialVersionUID = -5363562707054976998L;

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
	public AnalysisResponseDTO() {}
	
	//-------------------------------------------------------------------------------------------------
	public AnalysisResponseDTO(
			HashMap<String, HashMap<String, Integer>> mismatch, 
			HashMap<String, HashMap<String, Integer>> uncertainty, 
			HashMap<String, HashMap<String, HashMap<String, Integer>>> notation,
			HashMap<String, String> tagMeaning,
			double quantitativeM, String qualitativeM,
			double quantitativeU, String qualitativeU,
			final String flag, final String system, final String service) {
		
		this.mismatch = mismatch;
		this.uncertainty = uncertainty;
		this.notation = notation;
		this.tagMeaning = tagMeaning;
		this.quantitativeM = quantitativeM; this.qualitativeM = qualitativeM;
		this.quantitativeU = quantitativeU; this.qualitativeU = qualitativeU;
		this.flag = flag;
		this.system = system;
		this.service = service;
	}

	//-------------------------------------------------------------------------------------------------
	public HashMap<String, HashMap<String, Integer>> getMismatch() { return mismatch; }
	public HashMap<String, HashMap<String, Integer>> getUncertainty() { return uncertainty; }
	public HashMap<String, HashMap<String, HashMap<String, Integer>>> getNotation() { return notation; }
	public HashMap<String, String> getTagMeaning() { return tagMeaning; };
	
	public double getQuantitativeM() { return quantitativeM; }
	public String getQualitativeM() { return qualitativeM; }
	
	public double getQuantitativeU() { return quantitativeU; }
	public String getQualitativeU() { return qualitativeU; }
	
	public String getFlag() { return flag; }
	public String getSystem() { return system; }
	public String getService() { return service; }
	
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
	 * Summarize the data stored in the analysis
	 */
	public void summarize() {
		String result = "";
		
		switch(this.flag) {
		case "OK":
			result = "\tOK: No actions required\n";
			break;
		case "ALTER_T":
			result = "\tALTER_T: Call Translator System\n";
			break;
		case "ALTER_G":
			result = "\tALTER_G: Call Interface Generator System\n";
			break;
		case "NOT_OK":
			result = "\tNOT_OK: Impossible exchange of information\n";
			break;
		}
		
		System.out.println(
				"***********************************************************************************************************\n\n" +
				"RESULT: \n" + result);
		
		String compatibility = this.recursiveSummary(this.getMismatch(), new LinkedList<String>(),  "compatibility");
		
		if(compatibility.equals(""))
			compatibility = "\tNo mismatch between the service definitions\n";
		
		System.out.println(
				"***********************************************************************************************************\n\n" +
				"COMPATIBILITY SUMMARY: \n" + compatibility);
		
		String uncertainty = this.recursiveSummary(this.getUncertainty(), new LinkedList<String>(), "uncertainty");
		
		if(uncertainty.equals(""))
			uncertainty = "\tNo uncertainty between the service definitions\n";
		
		System.out.println(
				"***********************************************************************************************************\n\n" +
				"UNCERTAINTY SUMMARY: \n" + uncertainty);
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
