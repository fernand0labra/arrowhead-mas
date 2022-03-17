package ai.aitia.mismatch_analysis.controller.mismatchCheck;

import java.util.HashMap;
import java.util.Map.Entry;

import ai.aitia.mismatch_analysis.entity.Analysis;
import ai.aitia.mismatch_analysis.entity.serviceContract.Format;
import ai.aitia.mismatch_analysis.entity.serviceContract.Payload;
import ai.aitia.mismatch_analysis.entity.serviceContract.ServiceContract;

/**
 * Utilities :: Mismatch Check Module of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class MismatchUtils {

	//=================================================================================================
	// members
	
	private static boolean consumerWithoutStandardReq;
	private static boolean providerWithoutStandardReq;
	
	private static boolean consumerWithoutOntologyReq;
	private static boolean providerWithoutOntologyReq;
	
	
	private static boolean consumerWithoutStandardRes;
	private static boolean providerWithoutStandardRes;
	
	private static boolean consumerWithoutOntologyRes;
	private static boolean providerWithoutOntologyRes;
	
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
	public static Analysis analyse(ServiceContract consumer, ServiceContract provider) {
		Analysis analysis = new Analysis();
		
		analysis.setSystem(provider.getSystem());
		analysis.setService(provider.getService());
		
		boolean consumerEmptyRequest = consumer.getMethod().getRequest().getFormat().equals(new Format()) && !provider.getMethod().getRequest().getFormat().equals(new Format());
		boolean providerEmptyRequest = !consumer.getMethod().getRequest().getFormat().equals(new Format()) && provider.getMethod().getRequest().getFormat().equals(new Format());
		
		boolean consumerEmptyResponse = consumer.getMethod().getResponse().getFormat().equals(new Format()) && !provider.getMethod().getResponse().getFormat().equals(new Format());
		boolean providerEmptyResponse = !consumer.getMethod().getResponse().getFormat().equals(new Format()) && provider.getMethod().getResponse().getFormat().equals(new Format());
				
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
		
		boolean encodingReq;
		
		// If the request is empty in the consumer and not in the provider or viceversa
		if(consumerEmptyRequest || providerEmptyRequest) {
			analysis.getMismatch().get("encoding").put("mediaTypeReq", 0);
			analysis.getMismatch().get("encoding").put("versionReq", 0);
		}
		// If the encoding name in the request is empty
		else if(consumer.getMethod().getRequest().getFormat().getEncoding().getName().equals("") || provider.getMethod().getRequest().getFormat().getEncoding().getName().equals("")) {
			analysis.getUncertainty().get("encoding").put("mediaTypeReq", 1);
			analysis.getUncertainty().get("encoding").put("versionReq", 1);
		} else {
			// Request encoding type
			encodingReq = !consumer.getMethod().getRequest().getFormat().getEncoding().getName().equals(provider.getMethod().getRequest().getFormat().getEncoding().getName());
			if(encodingReq) {
				analysis.getMismatch().get("encoding").put("mediaTypeReq", 0);
				analysis.getMismatch().get("encoding").put("versionReq", 0);
			}
			else {
				// Request encoding version
				encodingReq = !consumer.getMethod().getRequest().getFormat().getEncoding().getVersion().equals(provider.getMethod().getRequest().getFormat().getEncoding().getVersion());
				if(encodingReq)
					analysis.getMismatch().get("encoding").put("versionReq", 0);
			}
		}
				
		boolean encodingRes;
		
		// If the response is empty in the consumer and not in the provider or viceversa
		if(consumerEmptyResponse || providerEmptyResponse) {
			analysis.getMismatch().get("encoding").put("mediaTypeRes", 0);
			analysis.getMismatch().get("encoding").put("versionRes", 0);
		}
		// If the encoding name in the response is empty
		else if(consumer.getMethod().getResponse().getFormat().getEncoding().getName().equals("") || provider.getMethod().getResponse().getFormat().getEncoding().getName().equals("")) {
			analysis.getUncertainty().get("encoding").put("mediaTypeRes", 1);
			analysis.getUncertainty().get("encoding").put("versionRes", 1);
		} else {
			// Response encoding type
			encodingRes = !consumer.getMethod().getResponse().getFormat().getEncoding().getName().equals(provider.getMethod().getResponse().getFormat().getEncoding().getName());
			if(encodingRes) {
				analysis.getMismatch().get("encoding").put("mediaTypeRes", 0);
				analysis.getMismatch().get("encoding").put("versionRes", 0);
			}
			else {
				// Response encoding version
				encodingRes = !consumer.getMethod().getResponse().getFormat().getEncoding().getVersion().equals(provider.getMethod().getResponse().getFormat().getEncoding().getVersion());
				if(encodingRes)
					analysis.getMismatch().get("encoding").put("versionRes", 0);
			}
		}
		
		
		/* ****************** */
		/* Semantics Analysis */
		/* ****************** */
		
		boolean semanticsReq;
		
		// If the standard name in the request is empty
		consumerWithoutStandardReq = consumer.getMethod().getRequest().getFormat().getSemantics().getStandard().getName().equals("");
		providerWithoutStandardReq = provider.getMethod().getRequest().getFormat().getSemantics().getStandard().getName().equals("");
		
		// If the ontology name in the request is empty
		consumerWithoutOntologyReq = consumer.getMethod().getRequest().getFormat().getSemantics().getOntology().getName().equals("");
		providerWithoutOntologyReq = provider.getMethod().getRequest().getFormat().getSemantics().getOntology().getName().equals("");
		
		// If the request is empty in the consumer and not in the provider or viceversa
		if(consumerEmptyRequest || providerEmptyRequest) {
			analysis.getMismatch().get("standard").put("nameReq", 0);
			analysis.getMismatch().get("standard").put("versionReq", 0);
			
			analysis.getMismatch().get("ontology").put("nameReq", 0);
			analysis.getMismatch().get("ontology").put("versionReq", 0);
		}
		
		// No standard and no ontology are defined
		else if(consumerWithoutStandardReq && providerWithoutStandardReq && consumerWithoutOntologyReq && providerWithoutOntologyReq) {
			analysis.getUncertainty().get("standard").put("nameReq", 1);
			analysis.getUncertainty().get("standard").put("versionReq", 1);
			
			analysis.getUncertainty().get("ontology").put("nameReq", 1);
			analysis.getUncertainty().get("ontology").put("versionReq", 1);
		} 
		
		// The standard is defined in both SCs
		else if(!(consumerWithoutStandardReq || providerWithoutStandardReq) && (consumerWithoutOntologyReq || providerWithoutOntologyReq)) {			
			// Request standard name
			semanticsReq = !consumer.getMethod().getRequest().getFormat().getSemantics().getStandard().getName().equals(provider.getMethod().getRequest().getFormat().getSemantics().getStandard().getName());
			if(semanticsReq) {
				analysis.getMismatch().get("standard").put("nameReq", 0);
				analysis.getMismatch().get("standard").put("versionReq", 0);
			}
			else {
				// Request standard version
				semanticsReq = !consumer.getMethod().getRequest().getFormat().getSemantics().getStandard().getVersion().equals(provider.getMethod().getRequest().getFormat().getSemantics().getStandard().getVersion());
				if(semanticsReq)
					analysis.getMismatch().get("standard").put("versionReq", 0);
			}
		}
		
		// The standard is defined only in one of the SCs
		else if((!consumerWithoutStandardReq && providerWithoutStandardReq) || (consumerWithoutStandardReq && !providerWithoutStandardReq)) {
			if(!consumerWithoutStandardReq && providerWithoutStandardReq)
				// If the other SC defines an ontology
				if(!providerWithoutOntologyReq) {
					analysis.getMismatch().get("standard").put("nameReq", 0);
					analysis.getMismatch().get("standard").put("versionReq", 0);
					
					analysis.getMismatch().get("ontology").put("nameReq", 0);
					analysis.getMismatch().get("ontology").put("versionReq", 0);
				} else {
					analysis.getUncertainty().get("standard").put("nameReq", 1);
					analysis.getUncertainty().get("standard").put("versionReq", 1);
				}
			else {
				// If the other SC defines an ontology
				if(!consumerWithoutOntologyReq) {
					analysis.getMismatch().get("standard").put("nameReq", 0);
					analysis.getMismatch().get("standard").put("versionReq", 0);
					
					analysis.getMismatch().get("ontology").put("nameReq", 0);
					analysis.getMismatch().get("ontology").put("versionReq", 0);
				} else {
					analysis.getUncertainty().get("standard").put("nameReq", 1);
					analysis.getUncertainty().get("standard").put("versionReq", 1);
				}
			}
		}
			
		// The ontology is defined in both SCs
		else if((consumerWithoutStandardReq || providerWithoutStandardReq) && !(consumerWithoutOntologyReq || providerWithoutOntologyReq)) {			
			// Request ontology name
			semanticsReq = !consumer.getMethod().getRequest().getFormat().getSemantics().getOntology().getName().equals(provider.getMethod().getRequest().getFormat().getSemantics().getOntology().getName());
			if(semanticsReq) {
				analysis.getMismatch().get("ontology").put("nameReq", 0);
				analysis.getMismatch().get("ontology").put("versionReq", 0);
			}
			else {
				// Request ontology version
				semanticsReq = !consumer.getMethod().getRequest().getFormat().getSemantics().getOntology().getVersion().equals(provider.getMethod().getRequest().getFormat().getSemantics().getOntology().getVersion());
				if(semanticsReq)
					analysis.getMismatch().get("ontology").put("versionReq", 0);
			}
		}
		
		// The ontology is defined only in one of the SCs
		else if((!consumerWithoutOntologyReq && providerWithoutOntologyReq) || (consumerWithoutOntologyReq && !providerWithoutOntologyReq)) {
			if(!consumerWithoutOntologyReq && providerWithoutOntologyReq)
				// If the other SC defines a standard
				if(!providerWithoutStandardReq) {
					analysis.getMismatch().get("standard").put("nameReq", 0);
					analysis.getMismatch().get("standard").put("versionReq", 0);
					
					analysis.getMismatch().get("ontology").put("nameReq", 0);
					analysis.getMismatch().get("ontology").put("versionReq", 0);
				} else {
					analysis.getUncertainty().get("ontology").put("nameReq", 1);
					analysis.getUncertainty().get("ontology").put("versionReq", 1);
				}
			else {
				// If the other SC defines a standard
				if(!consumerWithoutStandardReq) {
					analysis.getMismatch().get("standard").put("nameReq", 0);
					analysis.getMismatch().get("standard").put("versionReq", 0);
					
					analysis.getMismatch().get("ontology").put("nameReq", 0);
					analysis.getMismatch().get("ontology").put("versionReq", 0);
				} else {
					analysis.getUncertainty().get("standard").put("nameReq", 1);
					analysis.getUncertainty().get("standard").put("versionReq", 1);
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
		
		boolean semanticsRes;
		
		// If the standard name in the response is empty
		consumerWithoutStandardRes = consumer.getMethod().getResponse().getFormat().getSemantics().getStandard().getName().equals("");
		providerWithoutStandardRes = provider.getMethod().getResponse().getFormat().getSemantics().getStandard().getName().equals("");
		
		// If the ontology name in the response is empty
		consumerWithoutOntologyRes = consumer.getMethod().getResponse().getFormat().getSemantics().getOntology().getName().equals("");
		providerWithoutOntologyRes = provider.getMethod().getResponse().getFormat().getSemantics().getOntology().getName().equals("");
		
		// If the response is empty in the consumer and not in the provider or viceversa
		if(consumerEmptyResponse || providerEmptyResponse) {
			analysis.getMismatch().get("standard").put("nameRes", 0);
			analysis.getMismatch().get("standard").put("versionRes", 0);
			
			analysis.getMismatch().get("ontology").put("nameRes", 0);
			analysis.getMismatch().get("ontology").put("versionRes", 0);
		}
		
		// No standard and no ontology are defined
		else if(consumerWithoutStandardRes && providerWithoutStandardRes && consumerWithoutOntologyRes && providerWithoutOntologyRes) {
			analysis.getUncertainty().get("standard").put("nameRes", 1);
			analysis.getUncertainty().get("standard").put("versionRes", 1);
			
			analysis.getUncertainty().get("ontology").put("nameRes", 1);
			analysis.getUncertainty().get("ontology").put("versionRes", 1);
		}
		
		// The standard is defined in both SCs
		else if(!(consumerWithoutStandardRes || providerWithoutStandardRes) && (consumerWithoutOntologyRes || providerWithoutOntologyRes)) {			
			// Response standard name
			semanticsRes = !consumer.getMethod().getResponse().getFormat().getSemantics().getStandard().getName().equals(provider.getMethod().getResponse().getFormat().getSemantics().getStandard().getName());
			if(semanticsRes) {
				analysis.getMismatch().get("standard").put("nameRes", 0);
				analysis.getMismatch().get("standard").put("versionRes", 0);
			}
			else {
				// Response standard version
				semanticsRes = !consumer.getMethod().getResponse().getFormat().getSemantics().getStandard().getVersion().equals(provider.getMethod().getResponse().getFormat().getSemantics().getStandard().getVersion());
				if(semanticsRes)
					analysis.getMismatch().get("standard").put("versionRes", 0);
			}
		}
		
		// The standard is defined only in one of the SCs
		else if((!consumerWithoutStandardRes && providerWithoutStandardRes) || (consumerWithoutStandardRes && !providerWithoutStandardRes)) {
			if(!consumerWithoutStandardRes && providerWithoutStandardRes)
				// If the other SC defines an ontology
				if(!providerWithoutOntologyRes) {
					analysis.getMismatch().get("standard").put("nameRes", 0);
					analysis.getMismatch().get("standard").put("versionRes", 0);
					
					analysis.getMismatch().get("ontology").put("nameRes", 0);
					analysis.getMismatch().get("ontology").put("versionRes", 0);
				} else {
					analysis.getUncertainty().get("standard").put("nameRes", 1);
					analysis.getUncertainty().get("standard").put("versionRes", 1);
				}
			else {
				// If the other SC defines an ontology
				if(!consumerWithoutOntologyRes) {
					analysis.getMismatch().get("standard").put("nameRes", 0);
					analysis.getMismatch().get("standard").put("versionRes", 0);
					
					analysis.getMismatch().get("ontology").put("nameRes", 0);
					analysis.getMismatch().get("ontology").put("versionRes", 0);
				} else {
					analysis.getUncertainty().get("standard").put("nameRes", 1);
					analysis.getUncertainty().get("standard").put("versionRes", 1);
				}
			}
		}
		
		// The ontology is defined in both SCs
		else if((consumerWithoutStandardRes || providerWithoutStandardRes) && !(consumerWithoutOntologyRes || providerWithoutOntologyRes)) {			
			// Response ontology name
			semanticsRes = !consumer.getMethod().getResponse().getFormat().getSemantics().getOntology().getName().equals(provider.getMethod().getResponse().getFormat().getSemantics().getOntology().getName());
			if(semanticsRes) {
				analysis.getMismatch().get("ontology").put("nameRes", 0);
				analysis.getMismatch().get("ontology").put("versionRes", 0);
			}
			else {
				// Response ontology version
				semanticsRes = !consumer.getMethod().getResponse().getFormat().getSemantics().getOntology().getVersion().equals(provider.getMethod().getResponse().getFormat().getSemantics().getOntology().getVersion());
				if(semanticsRes)
					analysis.getMismatch().get("ontology").put("versionRes", 0);
			}
		}
		
		// The ontology is defined only in one of the SCs
		else if((!consumerWithoutOntologyRes && providerWithoutOntologyRes) || (consumerWithoutOntologyRes && !providerWithoutOntologyRes)) {
			if(!consumerWithoutOntologyRes && providerWithoutOntologyRes)
				// If the other SC defines a standard
				if(!providerWithoutStandardRes) {
					analysis.getMismatch().get("standard").put("nameRes", 0);
					analysis.getMismatch().get("standard").put("versionRes", 0);
					
					analysis.getMismatch().get("ontology").put("nameRes", 0);
					analysis.getMismatch().get("ontology").put("versionRes", 0);
				} else {
					analysis.getUncertainty().get("ontology").put("nameRes", 1);
					analysis.getUncertainty().get("ontology").put("versionRes", 1);
				}
			else {
				// If the other SC defines a standard
				if(!consumerWithoutStandardRes) {
					analysis.getMismatch().get("standard").put("nameRes", 0);
					analysis.getMismatch().get("standard").put("versionRes", 0);
					
					analysis.getMismatch().get("ontology").put("nameRes", 0);
					analysis.getMismatch().get("ontology").put("versionRes", 0);
				} else {
					analysis.getUncertainty().get("standard").put("nameRes", 1);
					analysis.getUncertainty().get("standard").put("versionRes", 1);
				}
			}
		}
		
		// Both ontology and standard are defined (Semantic Mismatch)
		else {
			analysis.getMismatch().get("standard").put("nameRes", 0);
			analysis.getMismatch().get("standard").put("versionRes", 0);
			
			analysis.getMismatch().get("ontology").put("nameRes", 0);
			analysis.getMismatch().get("ontology").put("versionRes", 0);
		}
			
		
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
		compute(analysis);
		
		return analysis;
	}

	//-------------------------------------------------------------------------------------------------
	/**
	 * Compute the compatibility and uncertainty for an analysis given
	 * 
	 * @param analysis			The Analysis object with an analysis on two SCs
	 */
	private static void compute(Analysis analysis) {
		
		/* ****************************************************/
		/* COMPATIBILITY :: f(x) = p(x) + e(x) + sm(x) + n(x) */
		/* ****************************************************/
		
		// PROTOCOL MISMATCH [0 <= p(x) <= 0.15] p(x) = protocol * 0.12 + version * 0.03
		double protocolNameM = 0.12;
		double protocolVersionM = 0.03;
		double protocolM = analysis.getMismatch().get("protocol").get("protocol") * protocolNameM + analysis.getMismatch().get("protocol").get("version") * protocolVersionM;
		
		// ENCODING MISMATCH [0 <= e(x) <= 0.15] e(x) = nameReq * 0.06 + versionReq * 0.015 + nameRes * 0.06 + versionRes * 0.015
		double encodingMediaTypeM = 0.06;
		double encodingVersionM = 0.015;
		double encodingM = 
				analysis.getMismatch().get("encoding").get("mediaTypeReq") * encodingMediaTypeM + analysis.getMismatch().get("encoding").get("versionReq") * encodingVersionM + 
				analysis.getMismatch().get("encoding").get("mediaTypeRes") * encodingMediaTypeM + analysis.getMismatch().get("encoding").get("versionRes") * encodingVersionM;
		
		// SEMANTICS MISMATCH [0 <= sm(x) <= 0.25] sm(x) = st(x) + o(x)
		double standardM = 0;
		double ontologyM = 0;
		
		// If it uses a standard in the request and/or the response
		boolean standardReq = !consumerWithoutStandardReq && !providerWithoutStandardReq && consumerWithoutOntologyReq && providerWithoutOntologyReq;
		boolean standardRes = !consumerWithoutStandardRes && !providerWithoutStandardRes && consumerWithoutOntologyRes && providerWithoutOntologyRes;
		if(standardReq || standardRes) { 
			// STANDARD MISMATCH[0 <= st(x) <= 0.25] st(x) = reqS(x) + resS(x)
				double standardNameM = 0.1;
				double standardVersionM = 0.025;
				
				if(standardReq) // If it uses the standard in the request
					// REQUEST [0 <= reqS(x) <= 0.125] reqS(x) = nameReq * 0.1 + versionReq * 0.025
					standardM += analysis.getMismatch().get("standard").get("nameReq") * standardNameM + analysis.getMismatch().get("standard").get("versionReq") * standardVersionM;
				
				if(standardRes) // If it uses the standard in the response
					// RESPONSE [0 <= resS(x) <= 0.125] resS(x) = nameRes * 0.1 + versionRes * 0.025 
					standardM += analysis.getMismatch().get("standard").get("nameRes") * standardNameM + analysis.getMismatch().get("standard").get("versionRes") * standardVersionM;
		} 
		
		// If it uses an ontology in the request and/or the response
		boolean ontologyReq = consumerWithoutStandardReq && providerWithoutStandardReq && !consumerWithoutOntologyReq && !providerWithoutOntologyReq;
		boolean ontologyRes = consumerWithoutStandardRes && providerWithoutStandardRes && !consumerWithoutOntologyRes && !providerWithoutOntologyRes;
		if(ontologyReq || ontologyRes) { 
			// ONTOLOGY MISMATCH [0 <= o(x) <= 0.25] o(x) = reqO(x) + resO(x)
				double ontologyName = 0.1;
				double ontologyVersion = 0.025;
				
				if(ontologyReq) // If it uses the ontology in the request
					// REQUEST [0 <= reqO(x) <= 0.125] reqO(x) = nameReq * 0.1 + versionReq * 0.025
					ontologyM += analysis.getMismatch().get("ontology").get("nameReq") * ontologyName + analysis.getMismatch().get("ontology").get("versionReq") * ontologyVersion;
				
				if(ontologyRes) // If it uses the ontology in the response
					// RESPONSE [0 <= resO(x) <= 0.125] resO(x) = nameRes * 0.1 + versionRes * 0.025
					ontologyM += analysis.getMismatch().get("ontology").get("nameRes") * ontologyName + analysis.getMismatch().get("ontology").get("versionRes") * ontologyVersion;
		}
		
		// If the standard or ontology is empty in the request and/or response
		boolean emptyReq = consumerWithoutStandardReq && providerWithoutStandardReq && consumerWithoutOntologyReq && providerWithoutOntologyReq;
		boolean emptyRes = consumerWithoutStandardRes && providerWithoutStandardRes && consumerWithoutOntologyRes && providerWithoutOntologyRes;
		if(emptyReq || emptyRes) {
			if(emptyReq)
				standardM += 0.125;
			
			if(emptyRes)
				ontologyM += 0.125;
		}
	
		double semanticsM = standardM + ontologyM;
			
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
		double quantitativeM = (protocolM + encodingM + semanticsM + notation) * 100;
		analysis.setQuantitativeM(quantitativeM);
		
		// Set the qualitative value
		String qualitativeM;
		
		if(quantitativeM <= 25)
			qualitativeM = "nil";
		else if(quantitativeM <= 50)
			qualitativeM = "low";
		else if(quantitativeM <= 75)
			qualitativeM = "medium";
		else if(quantitativeM < 100)
			qualitativeM = "high";
		else
			qualitativeM = "absolute";
		
		analysis.setQualitativeM(qualitativeM);	
		
		
		/* ************************************** */
		/* UNCERTAINTY g(x) = p(x) + e(x) + sm(x) */
		/* ************************************** */
		
		// PROTOCOL [0 <= p(x) <= 0.33] p(x) = protocol * 0.264 + version * 0.066
		double protocolNameU = 0.264;
		double protocolVersionU = 0.066;
		double protocolU = analysis.getUncertainty().get("protocol").get("protocol") * protocolNameU + analysis.getUncertainty().get("protocol").get("version") * protocolVersionU;
		
		// ENCODING [0 <= e(x) <= 0.33] e(x) = nameReq * 0.132 + versionReq * 0.06 + nameRes * 0.132 + versionRes * 0.06
		double encodingMediaTypeU = 0.132;
		double encodingVersionU = 0.06;
		double encodingU = 
				analysis.getUncertainty().get("encoding").get("mediaTypeReq") * encodingMediaTypeU + analysis.getUncertainty().get("encoding").get("versionReq") * encodingVersionU + 
				analysis.getUncertainty().get("encoding").get("mediaTypeRes") * encodingMediaTypeU + analysis.getUncertainty().get("encoding").get("versionRes") * encodingVersionU;	
		
		// SEMANTICS [0 <= sm(x) <= 0.34] sm(x) = st(x) + o(x)
		double standardU = 0;
		double ontologyU = 0;
		
		if(standardReq || standardRes) { // If it uses a standard in the request and/or the response
			// STANDARD [0 <= j(x) <= 0.34] j(x) = 
				double standardNameU = 0.136;
				double standardVersionU = 0.034;	
		
				if(standardReq) // If it uses the standard in the request
					// REQUEST [0 <= reqS(x) <= 0.17] reqS(x) = nameReq * 0.136 + versionReq * 0.034
					standardU += analysis.getUncertainty().get("standard").get("nameReq") * standardNameU + analysis.getUncertainty().get("standard").get("versionReq") * standardVersionU;
				
				if(standardRes) // If it uses the standard in the response
					// RESPONSE [0 <= resS(x) <= 0.17] resS(x) = nameRes * 0.136 + versionRes * 0.034 
					standardU += analysis.getUncertainty().get("standard").get("nameRes") * standardNameU + analysis.getUncertainty().get("standard").get("versionRes") * standardVersionU;
		} 
		
		if(ontologyReq || ontologyRes) { // If it uses an ontology in the request and/or the response
			// ONTOLOGY [0 <= o(x) <= 0.34] o(x) = reqO(x) + resO(x)
				double ontologyNameU = 0.136;
				double ontologyVersionU = 0.034;						
				
				if(ontologyReq) // If it uses the ontology in the request
					// REQUEST [0 <= reqO(x) <= 0.34] reqO(x) = nameReq * 0.136 + versionReq * 0.034
					ontologyU += analysis.getUncertainty().get("ontology").get("nameReq") * ontologyNameU + analysis.getUncertainty().get("ontology").get("versionReq") * ontologyVersionU;
				
				if(ontologyRes) // If it uses the ontology in the response
					// RESPONSE [0 <= resO(x) <= 0.34] resO(x) = nameRes * 0.136 + versionRes * 0.034
					ontologyU += analysis.getUncertainty().get("ontology").get("nameRes") * ontologyNameU + analysis.getUncertainty().get("ontology").get("versionRes") * ontologyVersionU;
		} 
		
		// If the standard or ontology is empty in the request and/or response
		if(emptyReq || emptyRes) {
			if(emptyReq)
				standardU += 0.17;
			
			if(emptyRes)
				ontologyU += 0.17;
		}
			
		double semanticsU = standardU + ontologyU;
				
		// Set the quantitative value
		double quantitativeU = (protocolU + encodingU + semanticsU) * 100;
		analysis.setQuantitativeU(quantitativeU);	
		
		// Set the qualitative value
		String qualitativeU;
		
		if(quantitativeU <= 25)
			qualitativeU = "nil";
		else if(quantitativeU <= 50)
			qualitativeU = "low";
		else if(quantitativeU <= 75)
			qualitativeU = "medium";
		else if(quantitativeU < 100)
			qualitativeU = "high";
		else
			qualitativeU = "absolute";
		
		analysis.setQualitativeU(qualitativeU);
	}
}
