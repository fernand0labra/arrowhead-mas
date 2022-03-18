package ai.aitia.mismatch_analysis.controller.mismatchCheck;

import java.util.HashMap;
import java.util.Map.Entry;

import ai.aitia.mismatch_analysis.entity.Analysis;

/**
 * Algorithm Compute Utilities :: Mismatch Check Module of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class AlgorithmUtils {
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Compute the compatibility and uncertainty for an analysis given
	 * 
	 * @param semanticConditions	Necessary conditions for semantic analysis
	 * @param analysis				The Analysis object with an analysis on two SCs
	 */
	public static void compute(HashMap<String, Boolean> semanticConditions, Analysis analysis) {
		
		/* ****************************************************/
		/* COMPATIBILITY :: f(x) = p(x) + e(x) + sm(x) + n(x) */
		/* ****************************************************/
		
		// PROTOCOL [0 <= p(x) <= 0.15] p(x) = protocol * 0.12 + version * 0.03
		HashMap<String, Double> protocolWeights = new HashMap<String, Double>();
		protocolWeights.put("protocolName", 0.12);
		protocolWeights.put("protocolVersion", 0.03);
		
		// ENCODING [0 <= e(x) <= 0.15] e(x) = nameReq * 0.06 + versionReq * 0.015 + nameRes * 0.06 + versionRes * 0.015
		HashMap<String, Double> encodingWeights = new HashMap<String, Double>();
		encodingWeights.put("encodingMediaType", 0.06);
		encodingWeights.put("encodingVersion", 0.015);
		
		// SEMANTICS [0 <= sm(x) <= 0.25] sm(x) = st(x) + o(x)
		HashMap<String, Double> semanticWeights = new HashMap<String, Double>();
		
			// STANDARD [0 <= st(x) <= 0.25] st(x) = reqS(x) + resS(x)
				// REQUEST [0 <= reqS(x) <= 0.125] reqS(x) = nameReq * 0.1 + versionReq * 0.025
				// RESPONSE [0 <= resS(x) <= 0.125] resS(x) = nameRes * 0.1 + versionRes * 0.025 
				semanticWeights.put("standardName", 0.1);
				semanticWeights.put("standardVersion", 0.025);
		
			// ONTOLOGY [0 <= o(x) <= 0.25] o(x) = reqO(x) + resO(x)
				// REQUEST [0 <= reqO(x) <= 0.125] reqO(x) = nameReq * 0.1 + versionReq * 0.025
				// RESPONSE [0 <= resO(x) <= 0.125] resO(x) = nameRes * 0.1 + versionRes * 0.025
				semanticWeights.put("ontologyName", 0.1);
				semanticWeights.put("ontologyVersion", 0.025);
				
		// NOTATION [0 <= n(x) <= 0.45] n(x) = reqN(x) + resN(x)
			// REQUEST 	[0 <= reqN(x) <= 0.225] SUM{name * (0.225 * 0.2 / N) + type * (0.225 * 0.8 / N)} over N (number of variables)
			double notationRequest = 0;
			double nameWeight;
			double typeWeight;
			
			int parametersRequest = analysis.getNotation().get("request").size();
			if(parametersRequest == 0)
				notationRequest+=0.225;
			else {
				nameWeight = (0.225 * 0.2 / parametersRequest);
				typeWeight = (0.225 * 0.8 / parametersRequest);
				
				for(Entry<String, HashMap<String, Integer>> entry : analysis.getNotation().get("request").entrySet()) {
					notationRequest += entry.getValue().get("name") * nameWeight + entry.getValue().get("type") * typeWeight;
				}
			}
						
			// RESPONSE [0 <= resN(x) <= 0.225] SUM{name * (0.225 * 0.2 / N) + type * (0.225 * 0.8 / N)} over N (number of variables)
			double notationResponse = 0;
			
			int parametersResponse = analysis.getNotation().get("response").size();
			if(parametersResponse == 0) 
				notationResponse = 0.225;
			else {
				nameWeight = (0.225 * 0.2 / parametersResponse);
				typeWeight = (0.225 * 0.8 / parametersResponse);
				
				for(Entry<String, HashMap<String, Integer>> entry : analysis.getNotation().get("response").entrySet()) {
					notationRequest += entry.getValue().get("name") * nameWeight + entry.getValue().get("type") * typeWeight;
				}
			}
			
		double notation = notationRequest + notationResponse;
		
		// Set the quantitative value				
		analysis.setQuantitativeM((
						computeProtocol(protocolWeights, analysis.getMismatch()) + 
						computeEncoding(encodingWeights, analysis.getMismatch()) + 
						computeSemantics(semanticConditions, semanticWeights, analysis.getMismatch()) + 
						notation) * 100);
		
		// Set the qualitative value
		analysis.setQualitativeM(selectQualitative(analysis.getQuantitativeM()));	
		
		
		/* ************************************** */
		/* UNCERTAINTY g(x) = p(x) + e(x) + sm(x) */
		/* ************************************** */
		
		// PROTOCOL [0 <= p(x) <= 0.33] p(x) = protocol * 0.264 + version * 0.066
		protocolWeights.put("protocolName", 0.264);
		protocolWeights.put("protocolVersion", 0.066);
		
		// ENCODING [0 <= e(x) <= 0.33] e(x) = nameReq * 0.132 + versionReq * 0.06 + nameRes * 0.132 + versionRes * 0.06
		encodingWeights.put("encodingMediaType", 0.132);
		encodingWeights.put("encodingVersion", 0.06);
		
		// SEMANTICS [0 <= sm(x) <= 0.34] sm(x) = st(x) + o(x)
		
		// STANDARD [0 <= j(x) <= 0.34] j(x) = reqS(x) + resS(x)
			// REQUEST [0 <= reqS(x) <= 0.17] reqS(x) = nameReq * 0.136 + versionReq * 0.034
			// RESPONSE [0 <= resS(x) <= 0.17] resS(x) = nameRes * 0.136 + versionRes * 0.034 
			semanticWeights.put("standardName", 0.136);
			semanticWeights.put("standardVersion", 0.034);
		
		// ONTOLOGY [0 <= o(x) <= 0.34] o(x) = reqO(x) + resO(x)
			// REQUEST [0 <= reqO(x) <= 0.34] reqO(x) = nameReq * 0.136 + versionReq * 0.034
			// RESPONSE [0 <= resO(x) <= 0.34] resO(x) = nameRes * 0.136 + versionRes * 0.034
			semanticWeights.put("ontologyVersion", 0.136);
			semanticWeights.put("ontologyVersion", 0.034);	
			
		// Set the quantitative value
		analysis.setQuantitativeU((
				computeProtocol(protocolWeights, analysis.getUncertainty()) + 
				computeEncoding(encodingWeights, analysis.getUncertainty()) + 
				computeSemantics(semanticConditions, semanticWeights, analysis.getUncertainty())) * 100);
		
		// Set the qualitative value
		analysis.setQualitativeU(selectQualitative(analysis.getQuantitativeU()));	
	}
	
	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	/**
	 * Computes the protocol mismatch/uncertainty value based on the weights defined and the analysis given
	 * 
	 * @param weights 		The function coefficients 
	 * @param mapAnalysis	The mismatch/uncertainty map
	 * @return				The protocol mismatch/uncertainty value
	 */
	private static double computeProtocol(HashMap<String, Double> weights, HashMap<String, HashMap<String, Integer>> mapAnalysis) {
		double protocolName = weights.get("protocolName");
		double protocolVersion = weights.get("protocolVersion");
		
		return mapAnalysis.get("protocol").get("protocol") * protocolName + mapAnalysis.get("protocol").get("version") * protocolVersion;
	}
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Computes the encoding mismatch/uncertainty value based on the weights defined and the analysis given
	 * 
	 * @param weights 		The function coefficients 
	 * @param mapAnalysis	The mismatch/uncertainty map
	 * @return				The encoding mismatch/uncertainty value
	 */
	private static double computeEncoding(HashMap<String, Double> weights, HashMap<String, HashMap<String, Integer>> mapAnalysis) {
		double encodingMediaType = weights.get("encodingMediaType");
		double encodingVersion = weights.get("encodingVersion");
		return 
				mapAnalysis.get("encoding").get("mediaTypeReq") * encodingMediaType + mapAnalysis.get("encoding").get("versionReq") * encodingVersion + 
				mapAnalysis.get("encoding").get("mediaTypeRes") * encodingMediaType + mapAnalysis.get("encoding").get("versionRes") * encodingVersion;
	}
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Computes the semantics mismatch/uncertainty value based on the weights defined and the analysis given
	 * 
	 * @param semanticConditions	Necessary conditions for semantic analysis
	 * @param weights 				The function coefficients 
	 * @param mapAnalysis			The mismatch/uncertainty map
	 * @return						The protocol mismatch/uncertainty value
	 */
	private static double computeSemantics(HashMap<String, Boolean> semanticConditions, HashMap<String, Double> weights, HashMap<String, HashMap<String, Integer>> mapAnalysis) {
		double standard = 0;
		double ontology = 0;
		
		// If it uses a standard in the request and/or the response
		boolean standardReq = 
				!semanticConditions.get("consumerWithoutStandardReq") && !semanticConditions.get("providerWithoutStandardReq") && 
				semanticConditions.get("consumerWithoutOntologyReq") && semanticConditions.get("providerWithoutOntologyReq");
		
		boolean standardRes = 
				!semanticConditions.get("consumerWithoutStandardRes") && !semanticConditions.get("providerWithoutStandardRes") && 
				semanticConditions.get("consumerWithoutOntologyRes") && semanticConditions.get("providerWithoutOntologyRes");
		
		if(standardReq || standardRes) { 
			
				double standardName = weights.get("standardName");
				double standardVersion = weights.get("standardVersion");
				
				if(standardReq) // If it uses the standard in the request
					standard += mapAnalysis.get("standard").get("nameReq") * standardName + mapAnalysis.get("standard").get("versionReq") * standardVersion;
				
				if(standardRes) // If it uses the standard in the response
					standard += mapAnalysis.get("standard").get("nameRes") * standardName + mapAnalysis.get("standard").get("versionRes") * standardVersion;
		} 
		
		// If it uses an ontology in the request and/or the response
		boolean ontologyReq = 
				semanticConditions.get("consumerWithoutStandardReq") && semanticConditions.get("providerWithoutStandardReq") && 
				!semanticConditions.get("consumerWithoutOntologyReq") && !semanticConditions.get("providerWithoutOntologyReq");
		
		boolean ontologyRes = 
				semanticConditions.get("consumerWithoutStandardRes") && semanticConditions.get("providerWithoutStandardRes") && 
				!semanticConditions.get("consumerWithoutOntologyRes") && !semanticConditions.get("providerWithoutOntologyRes");
		
		if(ontologyReq || ontologyRes) { 
				double ontologyName = weights.get("ontologyName");
				double ontologyVersion = weights.get("ontologyVersion");
				
				if(ontologyReq) // If it uses the ontology in the request
					ontology += mapAnalysis.get("ontology").get("nameReq") * ontologyName + mapAnalysis.get("ontology").get("versionReq") * ontologyVersion;
				
				if(ontologyRes) // If it uses the ontology in the response
					ontology += mapAnalysis.get("ontology").get("nameRes") * ontologyName + mapAnalysis.get("ontology").get("versionRes") * ontologyVersion;
		}
		
		// If the standard or ontology is empty in the request and/or response
		boolean emptyReq = 
				semanticConditions.get("consumerWithoutStandardReq") && semanticConditions.get("providerWithoutStandardReq") && 
				semanticConditions.get("consumerWithoutOntologyReq") && semanticConditions.get("providerWithoutOntologyReq");
		
		boolean emptyRes = 
				semanticConditions.get("consumerWithoutStandardRes") && semanticConditions.get("providerWithoutStandardRes") && 
				semanticConditions.get("consumerWithoutOntologyRes") && semanticConditions.get("providerWithoutOntologyRes");
		
		if(emptyReq || emptyRes) {
			if(emptyReq)
				standard += weights.get("standardName") + weights.get("standardVersion");
			
			if(emptyRes)
				ontology += weights.get("ontologyName") + weights.get("ontologyVersion");
		}
		
		return standard + ontology;
	}
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Selects the qualitative value associated with the quantitative given
	 * 
	 * @param quantitative  Quantitative value of an analysis
	 * @return				Qualitative value of an analysis
	 */
	private static String selectQualitative(Double quantitative) {
		if(quantitative <= 25)
			return "nil";
		else if(quantitative <= 50)
			return "low";
		else if(quantitative <= 75)
			return "medium";
		else if(quantitative < 100)
			return "high";
		else
			return "absolute";
	}
}
