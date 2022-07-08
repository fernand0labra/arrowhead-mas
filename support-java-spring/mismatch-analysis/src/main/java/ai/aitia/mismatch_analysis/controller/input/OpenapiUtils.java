package ai.aitia.mismatch_analysis.controller.input;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.yaml.snakeyaml.Yaml;

import ai.aitia.mismatch_analysis.entity.serviceContract.Method;
import ai.aitia.mismatch_analysis.entity.serviceContract.Payload;
import ai.aitia.mismatch_analysis.entity.serviceContract.ServiceContract;
import ai.aitia.mismatch_analysis.entity.serviceContract.ServiceElement;

/**
 * OpenAPI Parsing Utils :: Input Module of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class OpenapiUtils extends Utils {

	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	@Override
	void fillContractInfo(Object actual, LinkedList<String> parentNames) throws Exception {
		
		HashMap<String, Object> actualMap = ((HashMap<String, Object>) actual);
		for(Entry<String, Object> entry : actualMap.entrySet()) { // For each entry of the map
			String tag = entry.getKey();
			
			/* *********** */
			/* prepareInfo */
			/* *********** */
			
			if(operations.contains(tag)) { // If the tag is an allowed method
				HashMap<String, HashMap<String, String>> methodInfo = new HashMap<String, HashMap<String, String>>();
				
				// Build the tags/attributes matrix
				String[][] openapiMethodInfo = new String[responseCodes.size() + 1][];
				
				String[] operationArray = {tag,
												"summary", "description", "operationId"};
				openapiMethodInfo[0] = operationArray;
				
				// responses200, responses404, ...
				int index = 1;
				for(String code : responseCodes) {
					String[] responses = {code,
												"description", "encoding"};
					openapiMethodInfo[index] = responses;
					index++;
				}	
				
				methodKeys = openapiMethodInfo;
				
				// Build the map that will contain the method info
				buildMap(methodKeys, methodInfo);	
				
				// Fill the method info and store it in the list
				fillMethodInfo(actual, parentNames, methodInfo);
				methodInfoList.add(methodInfo);
			}
			
			else {
				
				/* ************************ */
				/* fillContractInfo(parent) */
				/* ************************ */
				
				// If the entry is a map
				if(entry.getValue() instanceof HashMap<?, ?>) {
					if(!parentNames.isEmpty() && parentNames.getFirst().equals("paths"))
						contractInfo.get("paths").put("value", tag);
					
					LinkedList<String> newParentNames = new LinkedList<String>(parentNames);
					newParentNames.addFirst(tag);
					fillContractInfo(entry.getValue(), newParentNames);
				} 
				// If the entry is an array of maps (e.g. servers, parameters, etc.)
				else if(entry.getValue() instanceof ArrayList) { 
					LinkedList<String> newParentNames = new LinkedList<String>(parentNames);
					newParentNames.addFirst(tag);
					for(HashMap<?, ?> map : (ArrayList<HashMap<?, ?>>) entry.getValue())
						fillContractInfo(map, newParentNames);
				}
				
				/* *********************** */
				/* fillContractInfo(child) */
				/* *********************** */
				
				else {
					if(!parentNames.isEmpty() && contractInfo.containsKey(parentNames.getFirst())) {									
						if(contractInfo.containsKey(parentNames.getFirst()) && !contractInfo.get(parentNames.getFirst()).isEmpty()) {
							contractInfo.get(parentNames.getFirst()).put(tag, entry.getValue().toString());
						}
					}
				}
			}
			
		}
		
	}
	
	//-------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	@Override
	void fillMethodInfo(Object actual, LinkedList<String> parentNames, HashMap<String, HashMap<String, String>> methodInfo) {
		
		HashMap<String, Object> actualMap = ((HashMap<String, Object>) actual);
		for(Entry<String, Object> entry : actualMap.entrySet()) { // For each entry of the map
			String tag = entry.getKey(); // Obtain the tag
			
			/* *********** */
			/* prepareInfo */
			/* *********** */
			
			// Check response codes specific info
			if(responseCodes.contains(tag)) {
				methodInfo.get(tag).put("description", ((HashMap<String, Object>)entry.getValue()).get("description").toString());
				
				try {
					for(Entry<String, Object> entryResponseCodes : ((HashMap<String, Object>) 
																		((HashMap<String, Object>) entry.getValue())
																			.get("content")).entrySet())
							methodInfo.get(tag).put("encoding", (entryResponseCodes.getKey().split("/")[1]).toUpperCase());
				}
				catch(Exception e) { } // If no content defined
			}
			
			else {
				for(String operation : operations) {
					if(parentNames.contains(operation)) { // If it is a type of operation
						if(methodInfo.containsKey(operation) && !methodInfo.get(operation).isEmpty()) { //If it has attributes
							
							// If it is a request object
							if(tag.equals("parameters")) {
								LinkedList<String> newParentNames = new LinkedList<String>();
								newParentNames.add("request");
								checkContent(entry.getValue(), newParentNames); // Build the object
							}
						}
					}
				}
			}
			
			
			/* ********************** */
			/* fillMethodInfo(parent) */
			/* ********************** */
			
			// If the entry is a map
			if(entry.getValue() instanceof HashMap<?, ?>) {
				LinkedList<String> newParentNames = new LinkedList<String>();
				
				// If it is a response object
				if(parentNames.getFirst().equals("schema") && tag.equals("properties")) {
					newParentNames.add("response");
					checkContent(entry.getValue(), newParentNames); // Build the object
				} else {
					newParentNames.addFirst(tag);
					fillMethodInfo(entry.getValue(), newParentNames, methodInfo);
				}
			}
				
			// If the entry is an array of maps (e.g. servers, parameters, etc.)
			else if(entry.getValue() instanceof ArrayList) { 
				LinkedList<String> newParentNames = new LinkedList<String>(parentNames);
				newParentNames.addFirst(tag);
				for(HashMap<?, ?> map : (ArrayList<HashMap<?, ?>>) entry.getValue())
					fillMethodInfo(map, newParentNames, methodInfo);
			}
			
			/* ********************* */
			/* fillMethodInfo(child) */
			/* ********************* */
			
			else {
				if(methodInfo.containsKey(parentNames.getFirst())) {									
					if(methodInfo.containsKey(parentNames.getFirst()) && !methodInfo.get(parentNames.getFirst()).isEmpty()) {	
						methodInfo.get(parentNames.getFirst()).put(tag, entry.getValue().toString());
					}
				}
			}
		}		
	}

	//-------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	@Override
	void checkContent(Object actual, LinkedList<String> parentNames) {	
		HashMap<String, HashMap<String, String>> content = new HashMap<String, HashMap<String, String>>();
		
		if(parentNames.contains("request")) { // Request payload
			ArrayList<HashMap<String, Object>> actualList = ((ArrayList<HashMap<String, Object>>) actual);
			for(HashMap<String, Object> actualMap : actualList) { // For each of the parameters
				String name = ""; // Name of an element
				String type = ""; // Type of an element
				
				for(Entry<String, Object> entry : actualMap.entrySet()){					
					if(entry.getKey().equals("name")) {
						name = entry.getValue().toString();
					} else if(entry.getKey().equals("schema")) {
						type = ((HashMap<String, String>) entry.getValue()).get("type");
					}
				}
				
				String firstLetter = type.substring(0, 1);
				String rest = type.substring(1);
				type = firstLetter.toUpperCase() + rest;
				
				content.put(name, new HashMap<String, String>());
				content.get(name).put("name", name);
				content.get(name).put("type", type);	
			}
				
			 reqPayload.setContents(content);
		}
		
		else { // Response payload			
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
			
			resPayload.setContents(content);
		}
	}

	//-------------------------------------------------------------------------------------------------
	@Override
	ServiceContract parse(File file, String operationId) throws Exception {	
		// Build the necessary structs for parsing
		buildMap(contractKeys, contractInfo);
		buildList(operationKeys, operations);
		buildList(responseCodeKeys, responseCodes);
		
		Yaml yaml = new Yaml();			
		fillContractInfo(yaml.load(new FileInputStream(file)), new LinkedList<String>());
		
		/* ***************************** */
		/* Parse Service Contract Object */
		/* ***************************** */
		
		ServiceContract sc = new ServiceContract();
		
			/* ****************************** */
			/* Establish protocol information */
			/* ****************************** */
			
			ServiceElement protocol = sc.getProtocol();
			protocol.setName("HTTP");
			protocol.setVersion("");
			protocol.setRef("");
			
			Method method = sc.getMethod();
			for(HashMap<String, HashMap<String, String>> methodInfo : methodInfoList) {
				String actualOperation = "";
				
				for(String operation : operations) {
					actualOperation = operation;
					try {
						// If it is not the method requested
						if(!methodInfo.get(operation).get("operationId").equals(operationId))
							continue;
					} catch(Exception e) { continue; } // The operation is not included
				}

				/* ***************************** */
				/* Establish request information */
				/* ***************************** */
				
				// Set Encoding
				ServiceElement encodingReq = method.getRequest().getFormat().getEncoding();
				encodingReq.setName("JSON");
				
				// Set Notation (i.e. the payload)
				method.getRequest().getPayload().setContents(reqPayload.getContents());
				
				/* ****************************** */
				/* Establish response information */
				/* ****************************** */
				
				// Set Encoding
				ServiceElement encodingRes = method.getResponse().getFormat().getEncoding();
				encodingRes.setName(methodInfo.get("200").get("encoding"));
				encodingRes.setVersion("");
				encodingRes.setRef("");
				
				// Set Notation (i.e. the payload)
				method.getResponse().getPayload().setContents(resPayload.getContents());
			
				/* **************************** */
				/* Establish method information */
				/* **************************** */
				
				method.setName(actualOperation);
				method.setId(methodInfo.get(actualOperation).get("operationId"));
			}
		
		return sc;
	}

	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public OpenapiUtils() {
		String[] openapiOperations = {"get"}; // GET, POST, DELETE, PUT
		operationKeys = openapiOperations;
		
		String[] openapiResponseCodes = {"200", "404"};	
		responseCodeKeys = openapiResponseCodes;
		
		// First column of each row is the tag, if the size of the row has
		// more than one element, it is the attributes of the tag.
		
		String[][] openapiContractInfo = {
				{"openapi"},
				
				{"info",
					"title", "description", "version"},
				
				{"servers",
					"url", "description"},
					
				{"paths",
					"value"}
			};
	    
		contractKeys = openapiContractInfo;
		
		// The key is the tag and the value is a HashMap of the attributes
		// i.e. HashMap<tag, HashMap<attribute, value>>
		contractInfo = new HashMap<String, HashMap<String, String>>();
		methodInfoList = new ArrayList<HashMap<String, HashMap<String, String>>>();
		withoutAttributes = new LinkedList<String>();
		
		operations = new ArrayList<String>();
		responseCodes = new ArrayList<String>();
		
		reqPayload = new Payload();
		resPayload = new Payload();
	}

}
