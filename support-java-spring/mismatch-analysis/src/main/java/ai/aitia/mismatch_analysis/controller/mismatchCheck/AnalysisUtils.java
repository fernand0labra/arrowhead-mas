package ai.aitia.mismatch_analysis.controller.mismatchCheck;

import java.util.HashMap;
import java.util.Map.Entry;

import ai.aitia.mismatch_analysis.entity.Analysis;
import ai.aitia.mismatch_analysis.entity.serviceContract.Format;
import ai.aitia.mismatch_analysis.entity.serviceContract.Payload;
import ai.aitia.mismatch_analysis.entity.serviceContract.Semantics;
import ai.aitia.mismatch_analysis.entity.serviceContract.ServiceContract;
import ai.aitia.mismatch_analysis.entity.serviceContract.ServiceElement;

/**
 * Analysis Utilities :: Mismatch Check Module of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class AnalysisUtils {
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Analyse the differences on both consumer and provider's SCs
	 * 
	 * @param consumer			The SC object of the consumer
	 * @param provider			The SC object of the provider
	 * @return					An analysis object with the analysis of the SCs
	 */
	@SuppressWarnings("unchecked")
	public static Analysis analyse(ServiceContract consumer, ServiceContract provider) {
		Analysis analysis = new Analysis();
		
		analysis.setSystem(provider.getSystem());
		analysis.setService(provider.getService());
		
		
		HashMap<String, Boolean> semanticConditions = new HashMap<String, Boolean>();

		// If the standard name in the request is empty
		semanticConditions.put("consumerWithoutStandardReq", consumer.getMethod().getRequest().getFormat().getSemantics().getStandard().getName().equals(""));
		semanticConditions.put("providerWithoutStandardReq", provider.getMethod().getRequest().getFormat().getSemantics().getStandard().getName().equals(""));
		
		// If the ontology name in the request is empty
		semanticConditions.put("consumerWithoutOntologyReq", consumer.getMethod().getRequest().getFormat().getSemantics().getOntology().getName().equals(""));
		semanticConditions.put("providerWithoutOntologyReq", provider.getMethod().getRequest().getFormat().getSemantics().getOntology().getName().equals(""));
		
		// If the standard name in the response is empty
		semanticConditions.put("consumerWithoutStandardRes", consumer.getMethod().getResponse().getFormat().getSemantics().getStandard().getName().equals(""));
		semanticConditions.put("providerWithoutStandardRes", provider.getMethod().getResponse().getFormat().getSemantics().getStandard().getName().equals(""));
		
		// If the ontology name in the response is empty
		semanticConditions.put("consumerWithoutOntologyRes", consumer.getMethod().getResponse().getFormat().getSemantics().getOntology().getName().equals(""));
		semanticConditions.put("providerWithoutOntologyRes", provider.getMethod().getResponse().getFormat().getSemantics().getOntology().getName().equals(""));	
		
		
		boolean consumerEmptyFormatMismatchReq = 
				consumer.getMethod().getRequest().getFormat().equals(new Format()) && 
				!provider.getMethod().getRequest().getFormat().equals(new Format());
		
		boolean providerEmptyFormatMismatchReq = 
				!consumer.getMethod().getRequest().getFormat().equals(new Format()) && 
				provider.getMethod().getRequest().getFormat().equals(new Format());
		
		boolean consumerEmptyFormatMismatchRes = 
				consumer.getMethod().getResponse().getFormat().equals(new Format()) && 
				!provider.getMethod().getResponse().getFormat().equals(new Format());
		
		boolean providerEmptyFormatMismatchRes = 
				!consumer.getMethod().getResponse().getFormat().equals(new Format()) && 
				provider.getMethod().getResponse().getFormat().equals(new Format());
		
		/* ***************** */
		/* Protocol Analysis */
		/* ***************** */
		
		// If the protocol name is empty
		if(consumer.getProtocol().getName().equals("") || provider.getProtocol().getName().equals("")) {
			analysis.getUncertainty().get("protocol").put("protocol", 1);
			analysis.getUncertainty().get("protocol").put("version", 1);
		} else {
			// Protocol type
			if(!consumer.getProtocol().getName().equals(provider.getProtocol().getName())) {
				analysis.getMismatch().get("protocol").put("protocol", 0);
				analysis.getMismatch().get("protocol").put("version", 0);
			}
			// Protocol version
			else if(!consumer.getProtocol().getVersion().equals(provider.getProtocol().getVersion())) {
				analysis.getMismatch().get("protocol").put("version", 0);
			}
		}
				
		/* ***************** */
		/* Encoding Analysis */
		/* ***************** */
		
		// If the request is empty in the consumer and not in the provider or viceversa
		if(consumerEmptyFormatMismatchReq || providerEmptyFormatMismatchReq) {
			analysis.getMismatch().get("encoding").put("mediaTypeReq", 0);
			analysis.getMismatch().get("encoding").put("versionReq", 0);
		} else		
			analyseEncoding(consumer, provider, analysis, "Req");
				
		// If the response is empty in the consumer and not in the provider or viceversa
		if(consumerEmptyFormatMismatchRes || providerEmptyFormatMismatchRes) {
			analysis.getMismatch().get("encoding").put("mediaTypeRes", 0);
			analysis.getMismatch().get("encoding").put("versionRes", 0);
		} else			
			analyseEncoding(consumer, provider, analysis, "Res");
		
		/* ****************** */
		/* Semantics Analysis */
		/* ****************** */
		
		// If the request is empty in the consumer and not in the provider or viceversa
		if(consumerEmptyFormatMismatchReq || providerEmptyFormatMismatchReq) {
			analysis.getMismatch().get("standard").put("nameReq", 0);
			analysis.getMismatch().get("standard").put("versionReq", 0);
			
			analysis.getMismatch().get("ontology").put("nameReq", 0);
			analysis.getMismatch().get("ontology").put("versionReq", 0);
		} else		
			analyseSemantics(semanticConditions, consumer, provider, analysis, "Req");
			
		// If the response is empty in the consumer and not in the provider or viceversa
		if(consumerEmptyFormatMismatchRes || providerEmptyFormatMismatchRes) {
			analysis.getMismatch().get("standard").put("nameRes", 0);
			analysis.getMismatch().get("standard").put("versionRes", 0);
			
			analysis.getMismatch().get("ontology").put("nameRes", 0);
			analysis.getMismatch().get("ontology").put("versionRes", 0);
		} else			
			analyseSemantics(semanticConditions, consumer, provider, analysis, "Res");
		
		/* ***************** */
		/* Notation Analysis */
		/* ***************** */
		
		Payload consumerPayload = consumer.getMethod().getRequest().getPayload();
		Payload providerPayload = provider.getMethod().getRequest().getPayload();
		
		boolean namecNamep;
		boolean namecVariationp;
		boolean variationcNamep;
		boolean variationcVariationp;
		
		int index;
		
		// Request
		for(Entry<String, HashMap<String, String>> entryConsumer : consumerPayload.getContents().entrySet()) { // For each variable of the consumer
			namecNamep = false;
			namecVariationp = false;
			variationcNamep = false;
			variationcVariationp = false;
			
			analysis.getNotation().get("request").put(entryConsumer.getKey(), new HashMap<String, Integer>());
			analysis.getNotation().get("request").get(entryConsumer.getKey()).put("name", 1);
			analysis.getNotation().get("request").get(entryConsumer.getKey()).put("type", 1);
						
			index = 0;
			Object[] providerSet = providerPayload.getContents().entrySet().toArray();
			while(!(namecNamep || namecVariationp || variationcNamep || variationcVariationp) && index < providerSet.length) {
				Entry<String, HashMap<String, String>> entryProvider = (Entry<String, HashMap<String, String>>) providerSet[index];
				
				// If the name of the consumer is the same as the name of the provider
				namecNamep = entryConsumer.getKey().equals(entryProvider.getKey());
				
				// If the variation is defined in the consumer
				if(entryConsumer.getValue().containsKey("variation")) { 	
					// If the variation of the consumer is the same as the name of the provider
					variationcNamep = entryConsumer.getValue().get("variation").equals(entryProvider.getKey());
					
					// If the variation is defined in the provider
					if(entryProvider.getValue().containsKey("variation")) {
						// If the name of the consumer is the same as the variation of the provider
						namecVariationp = entryConsumer.getKey().equals(entryProvider.getValue().get("variation"));
						
						// If the variation of the consumer is the same as the variation of the provider
						variationcVariationp = entryConsumer.getValue().get("variation").equals(entryProvider.getValue().get("variation"));
					}					
				}
				
				index++;
			}
			
			index--;
			
			// If the variable is the same
			if(namecNamep || namecVariationp || variationcNamep || variationcVariationp) {
				Entry<String, HashMap<String, String>> entryProvider;
				entryProvider = (Entry<String, HashMap<String, String>>) providerSet[index];
				
				// If the type of the variable is not the same
				if(!entryConsumer.getValue().get("type").equals(entryProvider.getValue().get("type"))){
					analysis.getNotation().get("request").get(entryConsumer.getKey()).put("type", 0);
				}
			}
			
			// If the variable is not the same
			else {
				analysis.getNotation().get("request").get(entryConsumer.getKey()).put("name", 0);
				analysis.getNotation().get("request").get(entryConsumer.getKey()).put("type", 0);
			}
		}
		
		consumerPayload = consumer.getMethod().getResponse().getPayload();
		providerPayload = provider.getMethod().getResponse().getPayload();
		
		// Response
		for(Entry<String, HashMap<String, String>> entryConsumer : consumerPayload.getContents().entrySet()) { // For each variable of the consumer
			namecNamep = false;
			namecVariationp = false;
			variationcNamep = false;
			variationcVariationp = false;
			
			analysis.getNotation().get("response").put(entryConsumer.getKey(), new HashMap<String, Integer>());
			analysis.getNotation().get("response").get(entryConsumer.getKey()).put("name", 1);
			analysis.getNotation().get("response").get(entryConsumer.getKey()).put("type", 1);
			
			index = 0;
			Object[] providerSet = providerPayload.getContents().entrySet().toArray();
			while(!(namecNamep || namecVariationp || variationcNamep || variationcVariationp) && index < providerSet.length) {				
				Entry<String, HashMap<String, String>> entryProvider = (Entry<String, HashMap<String, String>>) providerSet[index];
				
				// If the name of the consumer is the same as the name of the provider
				namecNamep = entryConsumer.getKey().equals(entryProvider.getKey());
				
				// If the variation is defined in the consumer
				if(entryConsumer.getValue().containsKey("variation")) { 	
					// If the variation of the consumer is the same as the name of the provider
					variationcNamep = entryConsumer.getValue().get("variation").equals(entryProvider.getKey());
					
					// If the variation is defined in the provider
					if(entryProvider.getValue().containsKey("variation")) {
						// If the name of the consumer is the same as the variation of the provider
						namecVariationp = entryConsumer.getKey().equals(entryProvider.getValue().get("variation"));
						
						// If the variation of the consumer is the same as the variation of the provider
						variationcVariationp = entryConsumer.getValue().get("variation").equals(entryProvider.getValue().get("variation"));
					}					
				}
				
				index++;
			}
			
			index--;
			
			// If the variable is the same
			if(namecNamep || namecVariationp || variationcNamep || variationcVariationp) {				
				Entry<String, HashMap<String, String>> entryProvider;
				entryProvider = (Entry<String, HashMap<String, String>>) providerSet[index];
				
				// If the type of the variable is not the same
				if(!entryConsumer.getValue().get("type").equals(entryProvider.getValue().get("type"))){
					analysis.getNotation().get("response").get(entryConsumer.getKey()).put("type", 0);
				}
			}
			
			// If the variable is not the same
			else {
				analysis.getNotation().get("response").get(entryConsumer.getKey()).put("name", 0);
				analysis.getNotation().get("response").get(entryConsumer.getKey()).put("type", 0);
			}
		}
		
		// Compute the compatibility and uncertainty values
		AlgorithmUtils.compute(semanticConditions, analysis);
		
		return analysis;
	}

	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Analyses the encoding of both service definitions and updates the analysis object
	 * 
	 * @param consumer 		Service definition of the consumer
	 * @param provider 		Service definition of the provider
	 * @param analysis		Analysis object
	 * @param messageType	Request or Response
	 */
	private static void analyseEncoding(ServiceContract consumer, ServiceContract provider, Analysis analysis, String messageType) {
		ServiceElement encodingC = null;
		ServiceElement encodingP = null;
		
		if(messageType.equals("Req")) {
			encodingC = consumer.getMethod().getRequest().getFormat().getEncoding();
			encodingP = provider.getMethod().getRequest().getFormat().getEncoding();
		}
		
		else if(messageType.equals("Res")){
			encodingC = consumer.getMethod().getResponse().getFormat().getEncoding();
			encodingP = provider.getMethod().getResponse().getFormat().getEncoding();
		}
		
		// If the encoding name is empty
		if(encodingC.getName().equals("") || encodingP.getName().equals("")) {
			analysis.getUncertainty().get("encoding").put("mediaType" + messageType, 1);
			analysis.getUncertainty().get("encoding").put("version" + messageType, 1);
		} else {
			// Encoding type
			if(!encodingC.getName().equals(encodingP.getName())) {
				analysis.getMismatch().get("encoding").put("mediaType" + messageType, 0);
				analysis.getMismatch().get("encoding").put("version" + messageType, 0);
			}
			else {
				// Encoding version				
				if(!encodingC.getVersion().equals(encodingP.getVersion()))
					analysis.getMismatch().get("encoding").put("version" + messageType, 0);
			}
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Analyses the semantics of both service definitions and updates the analysis object
	 * 
	 * @param semanticConditions	Necessary conditions for semantic analysis
	 * @param consumer 				Service definition of the consumer
	 * @param provider 				Service definition of the provider
	 * @param analysis				Analysis object
	 * @param messageType			Request or Response
	 */
	private static void analyseSemantics(HashMap<String, Boolean> semanticConditions, ServiceContract consumer, ServiceContract provider, Analysis analysis, String messageType) {
		Semantics semanticsC = null;
		Semantics semanticsP = null;
		
		boolean consumerWithoutStandard = semanticConditions.get("consumerWithoutStandard" + messageType);
		boolean providerWithoutStandard = semanticConditions.get("providerWithoutStandard" + messageType);

		boolean consumerWithoutOntology = semanticConditions.get("consumerWithoutOntology" + messageType);
		boolean providerWithoutOntology = semanticConditions.get("providerWithoutOntology" + messageType);
		
		if(messageType.equals("Req")) {
			semanticsC = consumer.getMethod().getRequest().getFormat().getSemantics();
			semanticsP = provider.getMethod().getRequest().getFormat().getSemantics();
		}
		
		else if(messageType.equals("Res")) {
			semanticsC = consumer.getMethod().getResponse().getFormat().getSemantics();
			semanticsP = provider.getMethod().getResponse().getFormat().getSemantics();
		}
		
		// No standard and no ontology are defined
		if(consumerWithoutStandard && providerWithoutStandard && consumerWithoutOntology && providerWithoutOntology) {
			analysis.getUncertainty().get("standard").put("name" + messageType, 1);
			analysis.getUncertainty().get("standard").put("version" + messageType, 1);
			
			analysis.getUncertainty().get("ontology").put("name" + messageType, 1);
			analysis.getUncertainty().get("ontology").put("version" + messageType, 1);
		} 
		
		// The standard is defined in both SCs
		else if(!(consumerWithoutStandard || providerWithoutStandard) && (consumerWithoutOntology || providerWithoutOntology)) {			
			// Standard name
			if(!semanticsC.getStandard().getName().equals(semanticsP.getStandard().getName())) {
				analysis.getMismatch().get("standard").put("name" + messageType, 0);
				analysis.getMismatch().get("standard").put("version" + messageType, 0);
			}
			else {
				// Standard version
				if(!semanticsC.getStandard().getVersion().equals(semanticsP.getStandard().getVersion()))
					analysis.getMismatch().get("standard").put("version" + messageType, 0);
			}
		}
		
		// The standard is defined only in one of the SCs
		else if((!consumerWithoutStandard && providerWithoutStandard) || (consumerWithoutStandard && !providerWithoutStandard)) {
			if(!consumerWithoutStandard && providerWithoutStandard)
				// If the other SC defines an ontology
				if(!providerWithoutOntology) {
					analysis.getMismatch().get("standard").put("name" + messageType, 0);
					analysis.getMismatch().get("standard").put("version" + messageType, 0);
					
					analysis.getMismatch().get("ontology").put("name" + messageType, 0);
					analysis.getMismatch().get("ontology").put("version" + messageType, 0);
				} else {
					analysis.getUncertainty().get("standard").put("name" + messageType, 1);
					analysis.getUncertainty().get("standard").put("version" + messageType, 1);
				}
			else {
				// If the other SC defines an ontology
				if(!consumerWithoutOntology) {
					analysis.getMismatch().get("standard").put("name" + messageType, 0);
					analysis.getMismatch().get("standard").put("version" + messageType, 0);
					
					analysis.getMismatch().get("ontology").put("name" + messageType, 0);
					analysis.getMismatch().get("ontology").put("version" + messageType, 0);
				} else {
					analysis.getUncertainty().get("standard").put("name" + messageType, 1);
					analysis.getUncertainty().get("standard").put("version" + messageType, 1);
				}
			}
		}
			
		// The ontology is defined in both SCs
		else if((consumerWithoutStandard || providerWithoutStandard) && !(consumerWithoutOntology || providerWithoutOntology)) {			
			// Ontology name
			if(!semanticsC.getOntology().getName().equals(semanticsC.getOntology().getName())) {
				analysis.getMismatch().get("ontology").put("name" + messageType, 0);
				analysis.getMismatch().get("ontology").put("version" + messageType, 0);
			}
			else {
				// Ontology version
				if(!semanticsC.getOntology().getVersion().equals(semanticsP.getOntology().getVersion()))
					analysis.getMismatch().get("ontology").put("version" + messageType, 0);
			}
		}
		
		// The ontology is defined only in one of the SCs
		else if((!consumerWithoutOntology && providerWithoutOntology) || (consumerWithoutOntology && !providerWithoutOntology)) {
			if(!consumerWithoutOntology && providerWithoutOntology)
				// If the other SC defines a standard
				if(!providerWithoutStandard) {
					analysis.getMismatch().get("standard").put("name" + messageType, 0);
					analysis.getMismatch().get("standard").put("version" + messageType, 0);
					
					analysis.getMismatch().get("ontology").put("name" + messageType, 0);
					analysis.getMismatch().get("ontology").put("version" + messageType, 0);
				} else {
					analysis.getUncertainty().get("ontology").put("name" + messageType, 1);
					analysis.getUncertainty().get("ontology").put("version" + messageType, 1);
				}
			else {
				// If the other SC defines a standard
				if(!consumerWithoutStandard) {
					analysis.getMismatch().get("standard").put("name" + messageType, 0);
					analysis.getMismatch().get("standard").put("version" + messageType, 0);
					
					analysis.getMismatch().get("ontology").put("name" + messageType, 0);
					analysis.getMismatch().get("ontology").put("version" + messageType, 0);
				} else {
					analysis.getUncertainty().get("standard").put("name" + messageType, 1);
					analysis.getUncertainty().get("standard").put("version" + messageType, 1);
				}
			}
		}
		
		// Both ontology and standard are defined (Semantic Mismatch)
		else {
			analysis.getMismatch().get("standard").put("nameReq", 0);
			analysis.getMismatch().get("standard").put("versionReq", 0);
			
			analysis.getMismatch().get("ontology").put("nameReq", 0);
			analysis.getMismatch().get("ontology").put("versionReq", 0);
		}
	}
}
