package ai.aitia.mismatch_analysis.controller.input;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import ai.aitia.mismatch_analysis.controller.WrongMethodException;
import ai.aitia.mismatch_analysis.entity.serviceContract.Method;
import ai.aitia.mismatch_analysis.entity.serviceContract.Payload;
import ai.aitia.mismatch_analysis.entity.serviceContract.Security;
import ai.aitia.mismatch_analysis.entity.serviceContract.ServiceContract;
import ai.aitia.mismatch_analysis.entity.serviceContract.ServiceElement;

/**
 * Contract Description Language (CDL) Parsing Utils :: Input Module of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class CdlUtils extends Utils{
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	@Override
	void fillContractInfo(Object actual, LinkedList<String> parentNames) throws Exception {
		
		/* *********** */
		/* prepareInfo */
		/* *********** */
		
		Node actualNode = ((Node) actual);
		String tag = actualNode.getNodeName();
		
		if(tag == "method") { // If the tag is the method
			// If the method's name is not included in the set defined
			if(!operations.contains(actualNode.getAttributes().getNamedItem("name").getNodeValue()))
				throw new WrongMethodException("The method is not allowed");
			
			parentNames = new LinkedList<String>();
			HashMap<String, HashMap<String, String>> methodInfo = new HashMap<String, HashMap<String, String>>();
			
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
			
			// If it is a parent AND is in the keys of the map
			if(actualNode.getChildNodes().getLength() != 0 && contractInfo.containsKey(tag)) {		
				// If it has attributes
				if(contractInfo.containsKey(tag) && !contractInfo.get(tag).isEmpty())
					fillAttributes(actualNode, tag, contractInfo);
				
				int pos = 0;		
				while(pos < actualNode.getChildNodes().getLength()) { // For every child of parent
					if(actualNode.getChildNodes().item(pos).getNodeType() == Node.ELEMENT_NODE)
						fillContractInfo(actualNode.getChildNodes().item(pos), null);
					pos++;
				}
			}
			
			/* *********************** */
			/* fillContractInfo(child) */
			/* *********************** */
			
			// If it is NOT a parent AND is in the keys of the map
			else if (actualNode.getChildNodes().getLength() == 0 && contractInfo.containsKey(tag)) {
				// If it has attributes
				if(!contractInfo.get(tag).isEmpty())
					fillAttributes(actualNode, tag, contractInfo);
			}
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Override
	void fillMethodInfo(Object actual, LinkedList<String> parentNames, HashMap<String, HashMap<String, String>> methodInfo) {
		
		/* *********** */
		/* prepareInfo */
		/* *********** */
		
		Node actualNode = ((Node) actual);
		String tag = actualNode.getNodeName();

		if(tag=="option") { // Build the different options
			String parentName = parentNames.getFirst();
			String firstLetter = parentName.substring(0, 1);
			String rest = parentName.substring(1);
			
			tag="option" + firstLetter.toUpperCase() + rest; // optionPath ;; optionEncode
		}
		
		if(parentNames.contains("request")) {
			tag = tag + "Req";
		} else if(parentNames.contains("response")) {
			tag = tag + "Res";
		}
		
		/* ********************** */
		/* fillMethodInfo(parent) */
		/* ********************** */

		// If it is a parent AND is in the keys of the map
		if(actualNode.getChildNodes().getLength() != 0 && (methodInfo.containsKey(tag) || withoutAttributes.contains(tag))) {
			// If it has attributes
			if(methodInfo.containsKey(tag) && !methodInfo.get(tag).isEmpty())
				fillAttributes(actualNode, tag, methodInfo);
			
			int pos = 0;		
			LinkedList<String> newParentNames;
			
			while(pos < actualNode.getChildNodes().getLength()) { // For every child of parent
				if(actualNode.getChildNodes().item(pos).getNodeType() == Node.ELEMENT_NODE) {
					newParentNames = new LinkedList<String>(parentNames); 
					newParentNames.addFirst(actualNode.getNodeName());
					
					// If it is the payload build a structure dynamically
					if(actualNode.getChildNodes().item(pos).getNodeName() == "payload")
						checkContent(actualNode.getChildNodes().item(pos), newParentNames);
					else
						fillMethodInfo(actualNode.getChildNodes().item(pos), newParentNames, methodInfo);
				}
				pos++;
			}
		} 

		/* ******************** */
		/* fillMethodInfo(child) */
		/* ******************** */

		// If it is NOT a parent AND is in the keys of the map
		else if (actualNode.getChildNodes().getLength() == 0 && methodInfo.containsKey(tag)) {
			// If it has attributes
			if(!methodInfo.get(tag).isEmpty())
				fillAttributes(actualNode, tag, methodInfo);
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Override
	void checkContent(Object actual, LinkedList<String> parentNames) {
		Node actualNode = ((Node) actual);
		HashMap<String, HashMap<String, String>> content = new HashMap<String, HashMap<String, String>>();
		
		int pos = 0;
		while(pos < actualNode.getChildNodes().getLength()) {
			if(actualNode.getChildNodes().item(pos).getNodeType() == Node.ELEMENT_NODE) {
				Node child = actualNode.getChildNodes().item(pos);
				String name = ""; // Name of the variable
				
				if(child.getNodeName() == "complextype") {
					// TODO
				} else if(child.getNodeName() == "complexelement") {
					// TODO
				} else if(child.getNodeName() == "element") {
					int i = 0;
					while(i < child.getAttributes().getLength()) { // For each attribute
						// Obtain the attribute name and value
						String attribute = child.getAttributes().item(i).getNodeName();
						String value = child.getAttributes().item(i).getNodeValue();
	
						if(attribute == "name") {
							name = value;
							// Create a new map for the variable
							content.put(name, new HashMap<String, String>());
						}
							
						// Set the content of the tag
						content.get(name).put(attribute, value);
						
						i++; // Next attribute
					}
				}
			}
			pos++;
		}
		
		if(parentNames.contains("request")) reqPayload.setContents(content); // Request payload
		else 								resPayload.setContents(content); // Response payload
	}

	//-------------------------------------------------------------------------------------------------
	@Override
	ServiceContract parse(File file, String operationId) throws Exception{	
		// An instance of factory that gives a document builder  
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 

		// An instance of builder to parse the specified xml file  
		DocumentBuilder db = dbf.newDocumentBuilder(); 
		Document doc = db.parse(file);  

		doc.getDocumentElement().normalize(); 
		Element root = doc.getDocumentElement(); // Root element of the xml file
		
		// Build the necessary structures for parsing
		buildMap(contractKeys, contractInfo);
		buildList(operationKeys, operations);
		buildList(responseCodeKeys, responseCodes);
		
		fillContractInfo(root, null);
		
		/* ***************************** */
		/* Parse Service Contract Object */
		/* ***************************** */
		
		ServiceContract sc = new ServiceContract();
		
			/* ****************************** */
			/* Establish protocol information */
			/* ****************************** */
			
			ServiceElement protocol = sc.getProtocol();
			HashMap<String, String> attributes = contractInfo.get("interface");
			protocol.setName(attributes.get("protocol"));
			protocol.setVersion(attributes.get("version"));
			
			Method method = sc.getMethod();
			for(HashMap<String, HashMap<String, String>> methodInfo : methodInfoList) { // For each of the methods
	
				// If it is not the method requested
				if(!methodInfo.get("method").get("id").equals(operationId))
					continue;
				
				/* ***************************** */
				/* Establish request information */
				/* ***************************** */
				
				// Set Encoding
				ServiceElement encodingReq = method.getRequest().getFormat().getEncoding();
				attributes = methodInfo.get("optionEncodeReq");
				encodingReq.setName(attributes.get("name"));
				encodingReq.setVersion(attributes.get("version"));
				
				// Set Semantics (standard OR ontology)
				if(!methodInfo.get("standardReq").get("name").equals("")) { // If there is a standard
					ServiceElement standardReq = method.getRequest().getFormat().getSemantics().getStandard();
					
					attributes = methodInfo.get("standardReq");
					standardReq.setName(attributes.get("name"));
					standardReq.setVersion(attributes.get("version"));
					
					attributes = methodInfo.get("extRefReq");
					standardReq.setRef(attributes.get("ref"));
				} 
				
				else if(!methodInfo.get("notationReq").get("ontology").equals("")){ // If there is an ontology
					ServiceElement ontologyReq = method.getRequest().getFormat().getSemantics().getOntology();
					
					attributes = methodInfo.get("notationReq");
					ontologyReq.setName(attributes.get("ontology"));
					ontologyReq.setRef(attributes.get("ref"));
				}
				
				// Set Security and QoS
				Security securityReq = method.getRequest().getFormat().getSecurity();
				attributes = methodInfo.get("securityReq");
				securityReq.setMode(attributes.get("mode"));
				securityReq.setDomain(attributes.get("domain"));
				securityReq.setIdentityAuth(attributes.get("identityAuth"));
				
				attributes = methodInfo.get("qosReq");
				if(!attributes.get("level").equals(""))
					method.getRequest().getFormat().setQos(Integer.parseInt(attributes.get("level")));		
				
				// Set Notation (i.e. the payload)
				method.getRequest().getPayload().setContents(reqPayload.getContents());
				
				/* ****************************** */
				/* Establish response information */
				/* ****************************** */
				
				// Set Encoding
				ServiceElement encodingRes = method.getResponse().getFormat().getEncoding();
				attributes = methodInfo.get("optionEncodeRes");
				encodingRes.setName(attributes.get("name"));
				encodingRes.setVersion(attributes.get("version"));
				
				// Set Semantics (standard OR ontology)
				if(!methodInfo.get("standardRes").get("name").equals("")) { // If there is a standard
					ServiceElement standardRes = method.getResponse().getFormat().getSemantics().getStandard();
					
					attributes = methodInfo.get("standardRes");
					standardRes.setName(attributes.get("name"));
					standardRes.setVersion(attributes.get("version"));
					
					attributes = methodInfo.get("extRefRes");
					standardRes.setRef(attributes.get("ref"));
				} else if(!methodInfo.get("notationRes").get("ontology").equals("")){ // If there is an ontology
					ServiceElement ontologyRes = method.getResponse().getFormat().getSemantics().getOntology();
					
					attributes = methodInfo.get("notationRes");
					ontologyRes.setName(attributes.get("ontology"));
					ontologyRes.setRef(attributes.get("ref"));	
				}
				
				// Set Security and QoS
				Security securityRes = method.getResponse().getFormat().getSecurity();
				attributes = methodInfo.get("securityRes");
				securityRes.setMode(attributes.get("mode"));
				securityRes.setDomain(attributes.get("domain"));
				securityRes.setIdentityAuth(attributes.get("identityAuth"));
				
				attributes = methodInfo.get("qosRes");
				if(!attributes.get("level").equals(""))
					method.getResponse().getFormat().setQos(Integer.parseInt(attributes.get("level")));		
				
				// Set Notation (i.e. the payload)
				method.getResponse().getPayload().setContents(resPayload.getContents());
				
				/* **************************** */
				/* Establish method information */
				/* **************************** */
				
				attributes = methodInfo.get("method");
				method.setName(attributes.get("name"));
				method.setId(attributes.get("id"));
			}
		
		return sc;
	}
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public CdlUtils() {
		String[] cdlOperations = {"GET", "POST"}; // GET, POST, DELETE, PUT
		operationKeys = cdlOperations;
		
		String[] cdlResponseCodes = {"200", "404"};
		responseCodeKeys = cdlResponseCodes;
		
		// First column of each row is the tag, if the size of the row has
		// more than one element, it is the attributes of the tag.
		
		String[][] cdlContractInfo = {
			{"contract",
			"xmlns", "xmlns:xsd"},
			
			{"interface",
				"protocol", "version", "address"},
			
			// For generalization purposes
			{"method"}, 
		};
		contractKeys = cdlContractInfo;
			
		String[][] cdlMethodInfo = {
			{"method",
				"name", "id", "path"}, 
				{"path"}, 
						{"optionPath",
							"value"},
						{"param",
							"name", "style", "type", "required"},
				
				{"request"}, // Definition Tags for the Request
						{"formatReq"},
							{"encodeReq"}, 
								{"optionEncodeReq",
									"name", "mediaType", "version"},
							{"securityReq",
								"mode", "domain", "identityAuth"},
							{"qosReq",
								"level"},
							{"semanticReq"},
								{"standardReq",
									"name", "version"},
								{"extRefReq",
									"name", "ref", "type"},
								{"notationReq",
									"ontology", "ref"},
				
				{"response", // Definition Tags for the Response
					"status"},
						{"formatRes"},
							{"encodeRes"}, 
								{"optionEncodeRes",
									"name", "mediaType", "version"},
							{"securityRes",
								"mode", "domain", "identityAuth"},
							{"qosRes",
								"level"},
							{"semanticRes"},
								{"standardRes",
									"name", "version"},
								{"extRefRes",
									"name", "ref", "type"},
								{"notationRes",
									"ontology", "ref"}
			};
			methodKeys = cdlMethodInfo;
			
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
	
	//-------------------------------------------------------------------------------------------------
	private void fillAttributes(Node node, String tag, HashMap<String, HashMap<String, String>> map) {
		int pos = 0;
		while(pos < node.getAttributes().getLength()) { // For each attribute
			// Obtain the attribute name and value
			String attribute = node.getAttributes().item(pos).getNodeName();
			String value = node.getAttributes().item(pos).getNodeValue();
			
			// If the attribute is included in the set defined
			if(map.get(tag).containsKey(attribute))
				map.get(tag).put(attribute, value);

			pos++; // Next attribute
		}
	}
}
