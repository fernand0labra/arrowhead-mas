package mismatchAnalysis.mismatchCheck;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.yaml.snakeyaml.Yaml;

import serviceContract.Format;
import serviceContract.Message;
import serviceContract.Payload;
import serviceContract.Security;
import serviceContract.Semantics;
import serviceContract.ServiceContract;
import serviceContract.ServiceElement;

public class OpenapiUtils extends Utils {

	private String[] operations = {"get"}; // GET, POST, DELETE, PUT
	private String[] responseCodes = {"200", "404"};
	
	public OpenapiUtils() {
		String[][] openapiKeys = {
				{"openapi"},
				
				{"info",
					"title", "description", "version"},
				
				{"servers",
					"url", "description"},
					
				{"paths",
					"value"}
			};
		
		String[][] dynamicOperation = new String[2 + responseCodes.length][];
		for(String operation : operations) {
			String[] operationArray = {operation,
											"summary", "description", "operationId"};
			dynamicOperation[0] = operationArray;
			
			String firstLetter = operation.substring(0, 1);
			String rest = operation.substring(1);
			
			String[] parametersArray = {"parameters" + firstLetter.toUpperCase() + rest,
											"name", "in", "required", "description", "type"};
			dynamicOperation[1] = parametersArray;
			
			int index = 2;
			for(String code : responseCodes) {
				String[] responses = {"responses" + firstLetter.toUpperCase() + rest + code,
										"description", "encoding"};
				dynamicOperation[index] = responses;
				index++;
			}	
			
			String[][] newOpenapiKeys = new String[openapiKeys.length + dynamicOperation.length][];     

			for (int i = 0; i < openapiKeys.length; i++) {
			    newOpenapiKeys[i] = openapiKeys[i];
			}
			
			for (int i = openapiKeys.length; i < openapiKeys.length + dynamicOperation.length; i++) {
			    newOpenapiKeys[i] = dynamicOperation[i - openapiKeys.length];
			}
			
			openapiKeys = newOpenapiKeys;
		}
	    
		keys = openapiKeys;
		
		// The key is the tag and the value is a HashMap of the attributes
		// i.e. HashMap<tag, HashMap<attribute, value>>
		withAttributes = new HashMap<String, HashMap<String, String>>();
		withoutAttributes = new LinkedList<String>();
		reqPayload = new Payload();
		resPayload = new Payload();
	}

	@SuppressWarnings("unchecked")
	@Override
	void fillRecursive(Object actual, LinkedList<String> parentNames) {
		/* *********** */
		/* prepareInfo */
		/* *********** */
		
		HashMap<String, Object> actualMap = ((HashMap<String, Object>) actual);
		for(Entry<String, Object> entry : actualMap.entrySet()) { // For each entry of the map
			String tag = entry.getKey(); // Obtain the tag
			if(!parentNames.isEmpty())
				if(parentNames.getFirst().equals("paths"))
					withAttributes.get("paths").put("value", tag);
				else if(parentNames.contains("content")) {
					if(parentNames.getFirst().equals("content"))
						withAttributes.get("responsesGet200").put("encoding", (tag.split("/")[1]).toUpperCase());
					else if(parentNames.getFirst().equals("schema") && tag.equals("properties")) {
						// LinkedList<String> newParentNames = new LinkedList<String>();
						// newParentNames.add("response");
						checkContent(entry.getValue(), null); // Build the object
					}
				}
				else if(parentNames.contains("responsesGet")) { // If it is a type of response
					// If it is on the accepted responses
					if(tag.equals("200") || tag.equals("404"))
						tag = "responsesGet" + tag;
				}
				else if(parentNames.contains("get")) { // If it is a type of operation
					if(withAttributes.containsKey("get") && !withAttributes.get("get").isEmpty()) { //If it has attributes
						// If it is not an attribute child AND it is a tag child 
						if(!withAttributes.get("get").containsKey(tag) && parentNames.getFirst().equals("get"))
							tag += "Get";
						
						if(parentNames.contains("parametersGet") && parentNames.getFirst().equals("schema")) {
							// LinkedList<String> newParentNames = new LinkedList<String>();
							// newParentNames.add("request");
							// checkContent(entry.getValue(), null); // Build the object
							withAttributes.get("parametersGet").put(tag, entry.getValue().toString());
						}
					}
				}
			
			/* ********************* */
			/* fillRecursive(parent) */
			/* ********************* */
			
			// If the entry is a map
			if(entry.getValue() instanceof HashMap<?, ?>) {
				LinkedList<String> newParentNames = new LinkedList<String>(parentNames);
				newParentNames.addFirst(tag);
				fillRecursive(entry.getValue(), newParentNames);
			} 
			// If the entry is an array of maps (e.g. servers, parameters, etc.)
			else if(entry.getValue() instanceof ArrayList) { 
				LinkedList<String> newParentNames = new LinkedList<String>(parentNames);
				newParentNames.addFirst(tag);
				for(HashMap<?, ?> map : (ArrayList<HashMap<?, ?>>) entry.getValue())
					fillRecursive(map, newParentNames);
			}
			
			/* ******************** */
			/* fillRecursive(child) */
			/* ******************** */
			
			else {
				if(!parentNames.isEmpty() && withAttributes.containsKey(parentNames.getFirst())) {									
					if(withAttributes.containsKey(parentNames.getFirst()) && !withAttributes.get(parentNames.getFirst()).isEmpty()) {					
						withAttributes.get(parentNames.getFirst()).put(tag, entry.getValue().toString());
					}
				}
			}
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	void checkContent(Object actual, LinkedList<String> parentNames) {	
		HashMap<String, HashMap<String, String>> content = new HashMap<String, HashMap<String, String>>();
		
		/*
		if(!(actual instanceof HashMap<?, ?>) && (parentNames.getFirst().equals("response") || parentNames.getFirst().equals("request"))) { // If it is not an object
			if(parentNames.getFirst().equals("response")) {
				//content.put(actualMap., null);
				resPayload.setContents(content);
			}
			else {
				reqPayload.setContents(content);
			}
			
			return;
		}
		*/ // TODO In case the response or request information is not an object
		
		HashMap<String, Object> actualMap = ((HashMap<String, Object>) actual);
		
		for(Entry<String, Object> entry : actualMap.entrySet()){	
			String name = entry.getKey(); // Name of an element
			String type = ""; // Type of an element
			
			if(((HashMap<String, String>) entry.getValue()).containsKey("format"))
				type = ((HashMap<String, String>) entry.getValue()).get("format");
			else 
				type = ((HashMap<String, String>) entry.getValue()).get("type");
			
			String firstLetter = type.substring(0, 1);
			String rest = type.substring(1);
			type = firstLetter.toUpperCase() + rest;
			
			content.put(name, new HashMap<String, String>());
			content.get(name).put("name", name);
			content.get(name).put("type", type);

		}

		System.out.println(content);
		
		resPayload.setContents(content);
		
		content = new HashMap<String, HashMap<String, String>>();
		String name = withAttributes.get("parametersGet").get("name");
		String type = withAttributes.get("parametersGet").get("type");
		
		String firstLetter = type.substring(0, 1);
		String rest = type.substring(1);
		type = firstLetter.toUpperCase() + rest;
		
		content.put(name, new HashMap<String, String>());
		content.get(name).put("name", name);
		content.get(name).put("type", type);
		
		reqPayload.setContents(content);
		
		System.out.println(content);
	}

	@Override
	ServiceContract parse(File file) throws Exception {		
		for(String[] tag : keys) { // Fill the map with the tags and their attributes
			HashMap<String, String> attributes = new HashMap<String, String>();

			if(tag.length > 1) { // If the tag contains attributes
				int pos = 1;
				while(pos < tag.length) {
					attributes.put(tag[pos], ""); // Add the attribute as a key
					pos++;
				}
				withAttributes.put(tag[0], attributes);
			} else {
				withoutAttributes.add(tag[0]);
			}
		}
		
		Yaml yaml = new Yaml();
		fillRecursive(yaml.load(new FileInputStream(file)), new LinkedList<String>());
		
		System.out.println(withAttributes + "\n");
	
		/* ****************************** */
		/* Establish protocol information */
		/* ****************************** */
		
		ServiceElement protocol = new ServiceElement();
		protocol.setName("HTTP");
		protocol.setVersion("");
		protocol.setRef("");
		
		/* ***************************** */
		/* Establish request information */
		/* ***************************** */
		
		// Set Encoding
		ServiceElement encodingReq = new ServiceElement();
		encodingReq.setName("JSON");
		encodingReq.setVersion("");
		encodingReq.setRef("");
		
		// Set Semantics (standard and ontology)
		Semantics semanticsReq = new Semantics();
		
		ServiceElement standardReq = new ServiceElement();
		standardReq.setName("");
		standardReq.setVersion("");
		standardReq.setRef("");
		
		ServiceElement ontologyReq = new ServiceElement();
		ontologyReq.setName("");
		ontologyReq.setVersion("");
		ontologyReq.setRef("");	
		
		semanticsReq.setStandard(standardReq);
		semanticsReq.setOntology(ontologyReq);
		
		// Set Security and QoS
		Security securityReq = new Security();
		securityReq.setMode("");
		securityReq.setDomain("");
		securityReq.setIdentityAuth("");
		
		int qosReq = Integer.parseInt("0");		
		
		// Set Notation (i.e. the payload)
		Payload payloadReq = new Payload();
		payloadReq.setContents(reqPayload.getContents());
		
		// Set format
		Format formatReq = new Format();
		formatReq.setEncoding(encodingReq);
		formatReq.setQos(qosReq);
		formatReq.setSecurity(securityReq);
		formatReq.setSemantics(semanticsReq);
		
		// Create Message Request
		Message request = new Message();
		request.setFormat(formatReq);
		request.setPayload(payloadReq);
		
		/* ****************************** */
		/* Establish response information */
		/* ****************************** */
		
		// Set Encoding
		ServiceElement encodingRes = new ServiceElement();
		encodingRes.setName(withAttributes.get("responsesGet200").get("encoding"));
		encodingRes.setVersion("");
		encodingRes.setRef("");
		
		// Set Semantics (standard and ontology)
		Semantics semanticsRes = new Semantics();
		
		ServiceElement standardRes = new ServiceElement();
		standardRes.setName("");
		standardRes.setVersion("");
		standardRes.setRef("");
		
		ServiceElement ontologyRes = new ServiceElement();
		ontologyRes.setName("");
		ontologyRes.setVersion("");
		ontologyRes.setRef("");	
		
		semanticsRes.setStandard(standardRes);
		semanticsRes.setOntology(ontologyRes);
		
		// Set Security and QoS
		Security securityRes = new Security();
		securityRes.setMode("");
		securityRes.setDomain("");
		securityRes.setIdentityAuth("");
		
		int qosRes = Integer.parseInt("0");
		
		// Set Notation (i.e. the payload)
		Payload payloadRes = new Payload();
		payloadRes.setContents(resPayload.getContents());
		
		// Set format
		Format formatRes = new Format();
		formatRes.setEncoding(encodingRes);
		formatRes.setQos(qosRes);
		formatRes.setSecurity(securityRes);
		formatRes.setSemantics(semanticsRes);
		
		// Create Message Response
		Message response = new Message();
		response.setFormat(formatRes);
		response.setPayload(payloadRes);
		
		/* ************************** */
		/* Establish Service Contract */
		/* ************************** */
		
		ServiceContract sc = new ServiceContract();
		sc.setProtocol(protocol);
		sc.setRequest(request);
		sc.setResponse(response);
		
		return sc;
	}

}
