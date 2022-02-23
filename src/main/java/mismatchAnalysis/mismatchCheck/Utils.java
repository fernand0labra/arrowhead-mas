package mismatchAnalysis.mismatchCheck;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Node;

import serviceContract.Payload;
import serviceContract.ServiceContract;

public abstract class Utils {
	/* ******************************** */
	/* Necessary structs for SC parsing */
	/* ******************************** */
	protected String[][] keys;
	protected HashMap<String, HashMap<String, String>> withAttributes;
	protected List<String> withoutAttributes;
	protected Payload reqPayload;
	protected Payload resPayload;
	
	/**
	 * Recursively goes through each of the tags of the document by following
	 * the Document Object Model
	 * 
	 * @param actual The node which acts as root at a given level
	 * @param parentNames The parent names of the actual node
	 */
	abstract void fillRecursive(Object actual, LinkedList<String> parentNames);
	
	/**
	 * Operates over the contents of the payload and creates an object
	 * from them
	 * 
	 * @param actual The node which acts as root at a given level
	 * @param parentNames The parent names of the actual node
	 */
	abstract void checkContent(Object actual, LinkedList<String> parentNames);
	
	/**
	 * Parses a file containing a service definition into a Service Contract 
	 * object
	 * 
	 * @param file The file that defines the service interface
	 * @return The service contract object with the information of the file
	 * @throws Exception Error in the parsing
	 */
	abstract ServiceContract parse(File file) throws Exception;
	
	/**
	 * Obtains the file extension and returns the necessary utils for
	 * obtaining the Service Contract object
	 * 
	 * @param file The file that defines the service interface
	 * @return The utils that process the file with that extension
	 * @throws Exception File extension not accepted
	 */
	 public static Utils checkFileType(File file) throws Exception{
		 if(FilenameUtils.getExtension(file.getName()).equals("xml"))
			 return new CdlUtils();
		 else if(FilenameUtils.getExtension(file.getName()).equals("yaml"))
			 return new OpenapiUtils();
		 else
			 throw new Exception("File extension not accepted");
	 }
	
	/**
	 * Analyzes the relevant aspects of the Service Contracts of the consumer
	 * and the provider returning an analysis object with the level of mismatch
	 * 
	 * @param consumer The service contract of the consumer
	 * @param provider The service contract of the provider
	 * @return The analysis of mismatches
	 */
	Analysis analyseServiceContract(ServiceContract consumer, ServiceContract provider) {
		Analysis analysis = new Analysis();
		
		/* ***************** */
		/* Protocol Analysis */
		/* ***************** */
		
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
		
		if(consumer.getRequest().getFormat().getEncoding().getName().equals("") || provider.getRequest().getFormat().getEncoding().getName().equals("")) {
			analysis.getUncertainty().get("encoding").put("mediaTypeReq", 1);
			analysis.getUncertainty().get("encoding").put("versionReq", 1);
		} else {
			// Request encoding type
			encodingReq = !consumer.getRequest().getFormat().getEncoding().getName().equals(provider.getRequest().getFormat().getEncoding().getName());
			if(encodingReq) {
				analysis.getMismatch().get("encoding").put("mediaTypeReq", 0);
				analysis.getMismatch().get("encoding").put("versionReq", 0);
			}
			else {
				// Request encoding version
				encodingReq = !consumer.getRequest().getFormat().getEncoding().getVersion().equals(provider.getRequest().getFormat().getEncoding().getVersion());
				if(encodingReq)
					analysis.getMismatch().get("encoding").put("versionReq", 0);
			}
		}
				
		boolean encodingRes;
		
		if(consumer.getResponse().getFormat().getEncoding().getName().equals("") || provider.getResponse().getFormat().getEncoding().getName().equals("")) {
			analysis.getUncertainty().get("encoding").put("mediaTypeRes", 1);
			analysis.getUncertainty().get("encoding").put("versionRes", 1);
		} else {
			// Response encoding type
			encodingRes = !consumer.getResponse().getFormat().getEncoding().getName().equals(provider.getResponse().getFormat().getEncoding().getName());
			if(encodingRes) {
				analysis.getMismatch().get("encoding").put("mediaTypeRes", 0);
				analysis.getMismatch().get("encoding").put("versionRes", 0);
			}
			else {
				// Response encoding version
				encodingRes = !consumer.getResponse().getFormat().getEncoding().getVersion().equals(provider.getResponse().getFormat().getEncoding().getVersion());
				if(encodingRes)
					analysis.getMismatch().get("encoding").put("versionRes", 0);
			}
		}
		
		
		/* ****************** */
		/* Semantics Analysis */
		/* ****************** */
		
		boolean semanticsReq;
		
		// If the standard name in the request is empty
		if(consumer.getRequest().getFormat().getSemantics().getStandard().getName().equals("") || provider.getRequest().getFormat().getSemantics().getStandard().getName().equals("")) {
			analysis.getUncertainty().get("standard").put("nameReq", 1);
			analysis.getUncertainty().get("standard").put("versionReq", 1);
		} else {
			// Request standard name
			semanticsReq = !consumer.getRequest().getFormat().getSemantics().getStandard().getName().equals(provider.getRequest().getFormat().getSemantics().getStandard().getName());
			if(semanticsReq) {
				analysis.getMismatch().get("standard").put("nameReq", 0);
				analysis.getMismatch().get("standard").put("versionReq", 0);
			}
			else {
				// Request standard version
				semanticsReq = !consumer.getRequest().getFormat().getSemantics().getStandard().getVersion().equals(provider.getRequest().getFormat().getSemantics().getStandard().getVersion());
				if(semanticsReq)
					analysis.getMismatch().get("standard").put("versionReq", 0);
			}
		}
		
		// If the ontology name in the request is empty
		if(consumer.getRequest().getFormat().getSemantics().getOntology().getName().equals("") || provider.getRequest().getFormat().getSemantics().getOntology().getName().equals("")) {
			analysis.getUncertainty().get("ontology").put("nameReq", 1);
			analysis.getUncertainty().get("ontology").put("versionReq", 1);
		} else {
			// Request ontology name
			semanticsReq = !consumer.getRequest().getFormat().getSemantics().getOntology().getName().equals(provider.getRequest().getFormat().getSemantics().getOntology().getName());
			if(semanticsReq) {
				analysis.getMismatch().get("ontology").put("nameReq", 0);
				analysis.getMismatch().get("ontology").put("versionReq", 0);
			}
			else {
				// Request ontology version
				semanticsReq = !consumer.getRequest().getFormat().getSemantics().getOntology().getVersion().equals(provider.getRequest().getFormat().getSemantics().getOntology().getVersion());
				if(semanticsReq)
					analysis.getMismatch().get("ontology").put("versionReq", 0);
			}
		}
		
		boolean semanticsRes;
		
		// If the standard name in the response is empty
		if(consumer.getResponse().getFormat().getSemantics().getStandard().getName().equals("") || provider.getResponse().getFormat().getSemantics().getStandard().getName().equals("")) {
			analysis.getUncertainty().get("standard").put("nameRes", 1);
			analysis.getUncertainty().get("standard").put("versionRes", 1);
		} else {
			// Response standard name
			semanticsRes = !consumer.getResponse().getFormat().getSemantics().getStandard().getName().equals(provider.getResponse().getFormat().getSemantics().getStandard().getName());
			if(semanticsRes) {
				analysis.getMismatch().get("standard").put("nameRes", 0);
				analysis.getMismatch().get("standard").put("versionRes", 0);
			}
			else {
				// Response standard version
				semanticsRes = !consumer.getResponse().getFormat().getSemantics().getStandard().getVersion().equals(provider.getResponse().getFormat().getSemantics().getStandard().getVersion());
				if(semanticsRes)
					analysis.getMismatch().get("standard").put("versionRes", 0);
			}
		}
				
		// If the ontology name in the response is empty
		if(consumer.getResponse().getFormat().getSemantics().getOntology().getName().equals("") || provider.getResponse().getFormat().getSemantics().getOntology().getName().equals("")) {
			analysis.getUncertainty().get("ontology").put("nameRes", 1);
			analysis.getUncertainty().get("ontology").put("versionRes", 1);
		} else {
			// Response ontology name
			semanticsRes = !consumer.getResponse().getFormat().getSemantics().getOntology().getName().equals(provider.getResponse().getFormat().getSemantics().getOntology().getName());
			if(semanticsRes) {
				analysis.getMismatch().get("ontology").put("nameRes", 0);
				analysis.getMismatch().get("ontology").put("versionRes", 0);
			}
			else {
				// Response ontology version
				semanticsRes = !consumer.getResponse().getFormat().getSemantics().getOntology().getVersion().equals(provider.getResponse().getFormat().getSemantics().getOntology().getVersion());
				if(semanticsRes)
					analysis.getMismatch().get("ontology").put("versionRes", 0);
			}
		}
			
		
		/* ***************** */
		/* Notation Analysis */
		/* ***************** */
		
		Payload consumerPayload = consumer.getRequest().getPayload();
		Payload providerPayload = provider.getRequest().getPayload();
		
		// Request
		for(Entry<String, HashMap<String, String>> entryConsumer : consumerPayload.getContents().entrySet()) { // For each variable of the consumer
			analysis.getNotation().get("request").put(entryConsumer.getKey(), new HashMap<String, Integer>());
			analysis.getNotation().get("request").get(entryConsumer.getKey()).put("name", 1);
			analysis.getNotation().get("request").get(entryConsumer.getKey()).put("type", 1);
			
			// If the name of the variable is the same
			if(providerPayload.getContents().containsKey(entryConsumer.getKey())) {
				// If the type of the variable is not the same
				if(!entryConsumer.getValue().get("type").equals(providerPayload.getContents().get(entryConsumer.getKey()).get("type"))){
					analysis.getNotation().get("request").get(entryConsumer.getKey()).put("type", 0);
				}
			} 
			
			// If the variation of the variable is the same as the name
			else if(providerPayload.getContents().containsKey(entryConsumer.getValue().get("variation"))) {
				// If the type of the variable is not the same
				if(!entryConsumer.getValue().get("type").equals(providerPayload.getContents().get(entryConsumer.getValue().get("variation")).get("type"))){
					analysis.getNotation().get("request").get(entryConsumer.getKey()).put("type", 0);
				}
			}
			
			// If the variable is not the same
			else {
				analysis.getNotation().get("request").get(entryConsumer.getKey()).put("name", 0);
				analysis.getNotation().get("request").get(entryConsumer.getKey()).put("type", 0);
			}
		}
		
		consumerPayload = consumer.getResponse().getPayload();
		providerPayload = provider.getResponse().getPayload();
		
		// Response
		for(Entry<String, HashMap<String, String>> entryConsumer : consumerPayload.getContents().entrySet()) { // For each variable of the consumer
			analysis.getNotation().get("response").put(entryConsumer.getKey(), new HashMap<String, Integer>());
			analysis.getNotation().get("response").get(entryConsumer.getKey()).put("name", 1);
			analysis.getNotation().get("response").get(entryConsumer.getKey()).put("type", 1);
			
			// If the name of the variable is the same
			if(providerPayload.getContents().containsKey(entryConsumer.getKey())) {
				// If the type of the variable is not the same
				if(!entryConsumer.getValue().get("type").equals(providerPayload.getContents().get(entryConsumer.getKey()).get("type"))){
					analysis.getNotation().get("response").get(entryConsumer.getKey()).put("type", 0);
				}
			} 
			
			// If the variation of the variable is the same as the name
			else if(providerPayload.getContents().containsKey(entryConsumer.getValue().get("variation"))) {
				// If the type of the variable is not the same
				if(!entryConsumer.getValue().get("type").equals(providerPayload.getContents().get(entryConsumer.getValue().get("variation")).get("type"))){
					analysis.getNotation().get("response").get(entryConsumer.getKey()).put("type", 0);
				}
			} 
			
			// If the variable is not the same
			else {
				analysis.getNotation().get("response").get(entryConsumer.getKey()).put("name", 0);
				analysis.getNotation().get("response").get(entryConsumer.getKey()).put("type", 0);
			}
		}
			
		
		/* ************************** */
		/* Set the Quantitative value */
		/* ************************** */
		
		// f(x) = g(x) + h(x) + i(x) + l(x)
		
		// PROTOCOL MISMATCH [0 <= g(x) <= 0.15] g(x) = protocol * 0.12 + version * 0.03
		double protocolM = analysis.getMismatch().get("protocol").get("protocol") * 0.12 + analysis.getMismatch().get("protocol").get("version") * 0.03;
		
		// ENCODING MISMATCH [0 <= h(x) <= 0.15] h(x) = nameReq * 0.06 + versionReq * 0.015 + nameRes * 0.06 + versionRes * 0.015
		double encodingM = 
				analysis.getMismatch().get("encoding").get("mediaTypeReq") * 0.06 + analysis.getMismatch().get("encoding").get("versionReq") * 0.015 + 
				analysis.getMismatch().get("encoding").get("mediaTypeRes") * 0.06 + analysis.getMismatch().get("encoding").get("versionRes") * 0.015;
		
		// SEMANTICS MISMATCH [0 <= i(x) <= 0.25] i(x) = j(x) + k(x)
			// STANDARD MISMATCH[0 <= j(x) <= 0.125] nameReq * 0.06 + versionReq * 0.0025 + nameRes * 0.06 + versionRes * 0.0025
			double standardM = 
					analysis.getMismatch().get("standard").get("nameReq") * 0.06 + analysis.getMismatch().get("standard").get("versionReq") * 0.0025 + 
					analysis.getMismatch().get("standard").get("nameRes") * 0.06 + analysis.getMismatch().get("standard").get("versionRes") * 0.0025;
					
			// ONTOLOGY MISMATCH [0 <= k(x) <= 0.125] nameReq * 0.06 + versionReq * 0.0025 + nameRes * 0.06 + versionRes * 0.0025
			double ontologyM = 
					analysis.getMismatch().get("ontology").get("nameReq") * 0.06 + analysis.getMismatch().get("ontology").get("versionReq") * 0.0025 + 
					analysis.getMismatch().get("ontology").get("nameRes") * 0.06 + analysis.getMismatch().get("ontology").get("versionRes") * 0.0025;
			
		double semanticsM = standardM + ontologyM;
			
		// NOTATION [0 <= l(x) <= 0.45] l(x) = m(x) + n(x)
			// REQUEST 	[0 <= m(x) <= 0.225] SUM{name * (0.225 * 0.2 / N) + type * (0.225 * 0.8 / N)} over N (number of variables)
			double notationRequest = 0;
			
			int parametersRequest = analysis.getNotation().get("request").size();
			double nameWeight = (0.225 * 0.2 / parametersRequest);
			double typeWeight = (0.225 * 0.8 / parametersRequest);
			
			for(Entry<String, HashMap<String, Integer>> entry : analysis.getNotation().get("request").entrySet()) {
				notationRequest += entry.getValue().get("name") * nameWeight + entry.getValue().get("type") * typeWeight;
			}
			
			// RESPONSE [0 <= n(x) <= 0.225] SUM{name * (0.225 * 0.2 / N) + type * (0.225 * 0.8 / N)} over N (number of variables)
			double notationResponse = 0;
			
			int parametersResponse = analysis.getNotation().get("response").size();
			nameWeight = (0.225 * 0.2 / parametersResponse);
			typeWeight = (0.225 * 0.8 / parametersResponse);
			
			for(Entry<String, HashMap<String, Integer>> entry : analysis.getNotation().get("response").entrySet()) {
				notationRequest += entry.getValue().get("name") * nameWeight + entry.getValue().get("type") * typeWeight;
			}
			
		double notation = notationRequest + notationResponse;
		
		double quantitativeM = (protocolM + encodingM + semanticsM + notation) * 100;
		analysis.setQuantitativeM(quantitativeM);
		
		// PROTOCOL UNCERTAINTY [0 <= g(x) <= 0.33] g(x) = protocol * 0.264 + version * 0.066
		double protocolU = analysis.getUncertainty().get("protocol").get("protocol") * 0.264 + analysis.getUncertainty().get("protocol").get("version") * 0.066;
		
		// ENCODING UNCERTAINTY [0 <= h(x) <= 0.33] h(x) = nameReq * 0.132 + versionReq * 0.06 + nameRes * 0.132 + versionRes * 0.06
		double encodingU = 
				analysis.getUncertainty().get("encoding").get("mediaTypeReq") * 0.132 + analysis.getUncertainty().get("encoding").get("versionReq") * 0.06 + 
				analysis.getUncertainty().get("encoding").get("mediaTypeRes") * 0.132 + analysis.getUncertainty().get("encoding").get("versionRes") * 0.06;
		
		// SEMANTICS UNCERTAINTY [0 <= i(x) <= 0.34] i(x) = j(x) + k(x)
			// STANDARD UNCERTAINTY[0 <= j(x) <= 0.17] nameReq * 0.068 + versionReq * 0.017 + nameRes * 0.068 + versionRes * 0.017
			double standardU = 
					analysis.getUncertainty().get("standard").get("nameReq") * 0.068 + analysis.getUncertainty().get("standard").get("versionReq") * 0.017 + 
					analysis.getUncertainty().get("standard").get("nameRes") * 0.068 + analysis.getUncertainty().get("standard").get("versionRes") * 0.017;
		
			// ONTOLOGY UNCERTAINTY [0 <= k(x) <= 0.17] nameReq * 0.068 + versionReq * 0.017 + nameRes * 0.068 + versionRes * 0.017
			double ontologyU = 
					analysis.getUncertainty().get("ontology").get("nameReq") * 0.068 + analysis.getUncertainty().get("ontology").get("versionReq") * 0.017 + 
					analysis.getUncertainty().get("ontology").get("nameRes") * 0.068 + analysis.getUncertainty().get("ontology").get("versionRes") * 0.017;
			
		double semanticsU = standardU + ontologyU;
				
		double quantitativeU = (protocolU + encodingU + semanticsU) * 100;
		analysis.setQuantitativeU(quantitativeU);
		
		/* ************************* */
		/* Set the Qualitative value */
		/* ************************* */
		
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
		
		return analysis;
	};
	
	
}
