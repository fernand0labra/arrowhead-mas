package mismatchAnalysis.mismatchCheck;

import java.util.HashMap;
import java.util.Map.Entry;

public class Analysis {
	private HashMap<String, HashMap<String, Integer>> mismatch;
	private HashMap<String, HashMap<String, Integer>> uncertainty;
	private HashMap<String, HashMap<String, HashMap<String, Integer>>> notation;
	
	// Quantitative level of compatibility
	private double quantitativeM;
	// Qualitative level of compatibility
	private String qualitativeM;
	
	// Quantitative level of uncertainty
	private double quantitativeU;
	// Qualitative level of uncertainty
	private String qualitativeU;
	
	
	public Analysis() {
		mismatch = new HashMap<String, HashMap<String, Integer>>();
		uncertainty = new HashMap<String, HashMap<String, Integer>>();
		
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
		
	}

	/**
	 * @return the mismatch
	 */
	public HashMap<String, HashMap<String, Integer>> getMismatch() {
		return mismatch;
	}

	/**
	 * @param mismatch the mismatch to set
	 */
	public void setMismatch(HashMap<String, HashMap<String, Integer>> mismatch) {
		this.mismatch = mismatch;
	}

	/**
	 * @return the notation
	 */
	public HashMap<String, HashMap<String, HashMap<String, Integer>>> getNotation() {
		return notation;
	}

	/**
	 * @param notation the notation to set
	 */
	public void setNotation(HashMap<String, HashMap<String, HashMap<String, Integer>>> notation) {
		this.notation = notation;
	}

	/**
	 * @return the uncertainty
	 */
	public HashMap<String, HashMap<String, Integer>> getUncertainty() {
		return uncertainty;
	}

	/**
	 * @param uncertainty the uncertainty to set
	 */
	public void setUncertainty(HashMap<String, HashMap<String, Integer>> uncertainty) {
		this.uncertainty = uncertainty;
	}
	
	/**
	 * @return the quantitativeM
	 */
	public double getQuantitativeM() {
		return quantitativeM;
	}

	/**
	 * @param quantitativeM the quantitativeM to set
	 */
	public void setQuantitativeM(double quantitativeM) {
		this.quantitativeM = quantitativeM;
	}

	/**
	 * @return the quantitativeU
	 */
	public double getQuantitativeU() {
		return quantitativeU;
	}

	/**
	 * @param quantitativeU the quantitativeU to set
	 */
	public void setQuantitativeU(double quantitativeU) {
		this.quantitativeU = quantitativeU;
	}

	/**
	 * @return the qualitativeM
	 */
	public String getQualitativeM() {
		return qualitativeM;
	}

	/**
	 * @param qualitativeM the qualitativeM to set
	 */
	public void setQualitativeM(String qualitativeM) {
		this.qualitativeM = qualitativeM;
	}

	/**
	 * @return the qualitativeU
	 */
	public String getQualitativeU() {
		return qualitativeU;
	}

	/**
	 * @param qualitativeU the qualitativeU to set
	 */
	public void setQualitativeU(String qualitativeU) {
		this.qualitativeU = qualitativeU;
	}
	
	public String toString(String indent) {
		String mismatchString =
				indent + "MISMATCH ARRAY: " + "\n" +
					
				indent + "\tProtocol: " + "\n" +
					indent + "\t\tprotocol: " + "\t" + String.valueOf(mismatch.get("protocol").get("protocol")) + "\n" +
					indent + "\t\tversion: " + "\t" + String.valueOf(mismatch.get("protocol").get("version")) + "\n" +
				
				indent + "\tEncoding: " + "\n" +
					indent + "\t\tRESPONSE: \n" +
						indent + "\t\t\tmediaTypeReq: " + "\t" + String.valueOf(mismatch.get("encoding").get("mediaTypeReq")) + "\n" +
						indent + "\t\t\tversionReq: " + "\t" + String.valueOf(mismatch.get("encoding").get("versionReq")) + "\n" +
					indent + "\t\tRESPONSE: \n" +
					indent + "\t\t\tmediaTypeRes: " + "\t" + String.valueOf(mismatch.get("encoding").get("mediaTypeRes")) + "\n" +
					indent + "\t\t\tversionRes: " + "\t" + String.valueOf(mismatch.get("encoding").get("versionRes")) + "\n" +	
					
				indent + "\tSemantics: " + "\n" +
					indent + "\t\tStandard: " + "\n" +
						indent + "\t\t\tREQUEST: \n" +
							indent + "\t\t\t\tnameReq: " + "\t" + String.valueOf(mismatch.get("standard").get("nameReq")) + "\n" +
							indent + "\t\t\t\tversionReq: " + "\t" + String.valueOf(mismatch.get("standard").get("versionReq")) + "\n" +
						indent + "\t\t\tRESPONSE: \n" +
							indent + "\t\t\t\tnameRes: " + "\t" + String.valueOf(mismatch.get("standard").get("nameRes")) + "\n" +
							indent + "\t\t\t\tversionRes: " + "\t" + String.valueOf(mismatch.get("standard").get("versionRes")) + "\n" +	
					
					indent + "\t\tOntology: " + "\n" +
						indent + "\t\t\tREQUEST: \n" +	
							indent + "\t\t\t\tnameReq: " + "\t" + String.valueOf(mismatch.get("ontology").get("nameReq")) + "\n" +
							indent + "\t\t\t\tversionReq: " + "\t" + String.valueOf(mismatch.get("ontology").get("versionReq")) + "\n" +
						indent + "\t\t\tRESPONSE: \n" +
							indent + "\t\t\t\tnameRes: " + "\t" + String.valueOf(mismatch.get("ontology").get("nameRes")) + "\n" +
							indent + "\t\t\t\tversionRes: " + "\t" + String.valueOf(mismatch.get("ontology").get("versionRes")) + "\n";	
		
				String notationString = 
						"\n" + indent + "NOTATION ARRAY: \n";
				
				notationString += indent + "\t\tREQUEST: \n";
				for(Entry<String, HashMap<String, Integer>> variable : notation.get("request").entrySet()) {
					notationString += indent + "\t\t\t" + variable.getKey() + ": " + "\n";
					String value = "";
					
					for(Entry<String, Integer> entry : variable.getValue().entrySet()) {
						switch(entry.getKey()) {
						case "name":
							value = indent + "\t\t\t\tNameReq: " + "\t" + String.valueOf(entry.getValue()) + "\n";
							break;
						case "type":
							value = indent + "\t\t\t\ttypeReq: " + "\t" + String.valueOf(entry.getValue()) + "\n";
							break;
					}
					
						notationString += indent + value;
					}
				}
				
				notationString += indent + "\t\tRESPONSE: \n";
				for(Entry<String, HashMap<String, Integer>> variable : notation.get("response").entrySet()) {
					notationString += indent + "\t\t\t" + variable.getKey() + ": " + "\n";
					String value = "";
					
					for(Entry<String, Integer> entry : variable.getValue().entrySet()) {
						switch(entry.getKey()) {
						case "name":
							value = indent + "\t\t\t\tNameRes: " + "\t" + String.valueOf(entry.getValue()) + "\n";
							break;
						case "type":
							value = indent + "\t\t\t\ttypeRes: " + "\t" + String.valueOf(entry.getValue()) + "\n";
							break;
					}
					
						notationString += indent + value;
					}
				}
				
		String quantitativeMString = "\n" + indent + "QUANTITATIVE VALUE (%): " + "\t" + String.valueOf(quantitativeM) + "\n";
		
		String qualitativeMString = "\n" + indent + "QUALITATIVE VALUE: " + "\t" + qualitativeM + "\n";
				
		String uncertaintyString =
				"\n" + indent + "UNCERTAINTY ARRAY: " + "\n" +
					
				indent + "\tProtocol: " + "\n" +
					indent + "\t\tprotocol: " + "\t" + String.valueOf(uncertainty.get("protocol").get("protocol")) + "\n" +
					indent + "\t\tversion: " + "\t" + String.valueOf(uncertainty.get("protocol").get("version")) + "\n" +
				
				indent + "\tEncoding: " + "\n" +
					indent + "\t\tRESPONSE: \n" +
						indent + "\t\t\tmediaTypeReq: " + "\t" + String.valueOf(uncertainty.get("encoding").get("mediaTypeReq")) + "\n" +
						indent + "\t\t\tversionReq: " + "\t" + String.valueOf(uncertainty.get("encoding").get("versionReq")) + "\n" +
					indent + "\t\tRESPONSE: \n" +
					indent + "\t\t\tmediaTypeRes: " + "\t" + String.valueOf(uncertainty.get("encoding").get("mediaTypeRes")) + "\n" +
					indent + "\t\t\tversionRes: " + "\t" + String.valueOf(uncertainty.get("encoding").get("versionRes")) + "\n" +	
					
				indent + "\tSemantics: " + "\n" +
					indent + "\t\tStandard: " + "\n" +
						indent + "\t\t\tREQUEST: \n" +
							indent + "\t\t\t\tnameReq: " + "\t" + String.valueOf(uncertainty.get("standard").get("nameReq")) + "\n" +
							indent + "\t\t\t\tversionReq: " + "\t" + String.valueOf(uncertainty.get("standard").get("versionReq")) + "\n" +
						indent + "\t\t\tRESPONSE: \n" +
							indent + "\t\t\t\tnameRes: " + "\t" + String.valueOf(uncertainty.get("standard").get("nameRes")) + "\n" +
							indent + "\t\t\t\tversionRes: " + "\t" + String.valueOf(uncertainty.get("standard").get("versionRes")) + "\n" +	
					
					indent + "\t\tOntology: " + "\n" +
						indent + "\t\t\tREQUEST: \n" +	
							indent + "\t\t\t\tnameReq: " + "\t" + String.valueOf(uncertainty.get("ontology").get("nameReq")) + "\n" +
							indent + "\t\t\t\tversionReq: " + "\t" + String.valueOf(uncertainty.get("ontology").get("versionReq")) + "\n" +
						indent + "\t\t\tRESPONSE: \n" +
							indent + "\t\t\t\tnameRes: " + "\t" + String.valueOf(uncertainty.get("ontology").get("nameRes")) + "\n" +
							indent + "\t\t\t\tversionRes: " + "\t" + String.valueOf(uncertainty.get("ontology").get("versionRes")) + "\n";	
		
		String quantitativeUString = "\n" + indent + "QUANTITATIVE VALUE (%): " + "\t" + String.valueOf(quantitativeU) + "\n";
		
		String qualitativeUString = "\n" + indent + "QUALITATIVE VALUE: " + "\t" + qualitativeU + "\n";

		return indent + "ANALYSIS\n\n" + 
			mismatchString + notationString + quantitativeMString + qualitativeMString +
			uncertaintyString + quantitativeUString + qualitativeUString;
	}
	
}
