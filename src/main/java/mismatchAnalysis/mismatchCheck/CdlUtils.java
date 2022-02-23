package mismatchAnalysis.mismatchCheck;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import serviceContract.Format;
import serviceContract.Message;
import serviceContract.Payload;
import serviceContract.Security;
import serviceContract.Semantics;
import serviceContract.ServiceContract;
import serviceContract.ServiceElement;

public class CdlUtils extends Utils{

	public CdlUtils() {
		// First column of each row is the tag, if the size of the row has
		// more than one element, it is the attributes of the tag.
		String[][] cdlKeys = {
				{"contract",
					"xmlns", "xmlns:xsd"},
					
					{"interface",
						"protocol", "version", "address"},
					
						{"method",
							"name", "id"},
						
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
			keys = cdlKeys;
			
			// The key is the tag and the value is a HashMap of the attributes
			// i.e. HashMap<tag, HashMap<attribute, value>>
			withAttributes = new HashMap<String, HashMap<String, String>>();
			withoutAttributes = new LinkedList<String>();
			reqPayload = new Payload();
			resPayload = new Payload();
	}
	
	@Override
	void fillRecursive(Object actual, LinkedList<String> parentNames) {
		/* *********** */
		/* prepareInfo */
		/* *********** */
		
		Node actualNode = ((Node) actual);
		String tag = actualNode.getNodeName();

		if(tag=="option") {
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

		/* ********************* */
		/* fillRecursive(parent) */
		/* ********************* */

		// If it is a parent AND is in the keys of the map
		if(actualNode.getChildNodes().getLength() != 0 && (withAttributes.containsKey(tag) || withoutAttributes.contains(tag))) {
			// If it has attributes
			if(withAttributes.containsKey(tag) && !withAttributes.get(tag).isEmpty()) {
				int pos = 0;
				while(pos < actualNode.getAttributes().getLength()) { // For each attribute
					// Obtain the attribute name and value
					String attribute = actualNode.getAttributes().item(pos).getNodeName();
					String value = actualNode.getAttributes().item(pos).getNodeValue();

					// If the attribute is included in the set defined
					if(withAttributes.get(tag).containsKey(attribute))
						withAttributes.get(tag).put(attribute, value);

					pos++; // Next attribute
				}
			}
			int pos = 0;		
			LinkedList<String> newParentNames;
			while(pos < actualNode.getChildNodes().getLength()) { // For every child of parent
				if(actualNode.getChildNodes().item(pos).getNodeType() == Node.ELEMENT_NODE) {
					newParentNames = new LinkedList<String>(parentNames); 
					newParentNames.addFirst(actualNode.getNodeName());
					// If it is the payload apply a specific function
					if(actualNode.getChildNodes().item(pos).getNodeName() == "payload")
						checkContent(actualNode.getChildNodes().item(pos), newParentNames);
					else
						fillRecursive(actualNode.getChildNodes().item(pos), newParentNames);
				}
				pos++;
			}
		} 

		/* ******************** */
		/* fillRecursive(child) */
		/* ******************** */

		// If it is NOT a parent AND is in the keys of the map
		else if (actualNode.getChildNodes().getLength() == 0 && withAttributes.containsKey(tag)) {
			// If it has attributes
			if(!withAttributes.get(tag).isEmpty()) {
				int pos = 0;
				while(pos < actualNode.getAttributes().getLength()) { // For each attribute
					// Obtain the attribute name and value
					String attribute = actualNode.getAttributes().item(pos).getNodeName();
					String value = actualNode.getAttributes().item(pos).getNodeValue();

					// If the attribute is included in the set defined
					if(withAttributes.get(tag).containsKey(attribute)) {
						// Fill the attribute map for that specific tag
						if(tag == "optionPath") 
							withAttributes.get("optionPath").put(attribute, value);
						else if(tag == "optionEncode")
							withAttributes.get("optionEncode").put(attribute, value);
						else
							withAttributes.get(tag).put(attribute, value);
					}

					pos++; // Next attribute
				}
			}
		}
	}
	
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
		
		System.out.println(content);
		
		if(parentNames.contains("request")) // Request payload
			reqPayload.setContents(content);
		else // Response payload
			resPayload.setContents(content);
		
	}

	@Override
	ServiceContract parse(File file) throws Exception{	
		// An instance of factory that gives a document builder  
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 

		// An instance of builder to parse the specified xml file  
		DocumentBuilder db = dbf.newDocumentBuilder(); 
		Document doc = db.parse(file);  

		doc.getDocumentElement().normalize();  // Root element of the xml file
		Element root = doc.getDocumentElement();	
		
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
		fillRecursive(root, new LinkedList<String>());
		
		System.out.println(withAttributes + "\n");

		/* ****************************** */
		/* Establish protocol information */
		/* ****************************** */
		
		ServiceElement protocol = new ServiceElement();
		HashMap<String, String> attributes = withAttributes.get("interface");
		protocol.setName(attributes.get("protocol"));
		protocol.setVersion(attributes.get("version"));
		protocol.setRef("");
		
		/* ***************************** */
		/* Establish request information */
		/* ***************************** */
		
		// Set Encoding
		ServiceElement encodingReq = new ServiceElement();
		attributes = withAttributes.get("optionEncodeReq");
		encodingReq.setName(attributes.get("name"));
		encodingReq.setVersion(attributes.get("version"));
		encodingReq.setRef("");
		
		// Set Semantics (standard and ontology)
		Semantics semanticsReq = new Semantics();
		
		ServiceElement standardReq = new ServiceElement();
		attributes = withAttributes.get("standardReq");
		standardReq.setName(attributes.get("name"));
		standardReq.setVersion(attributes.get("version"));
		attributes = withAttributes.get("extRefReq");
		standardReq.setRef(attributes.get("ref"));
		
		ServiceElement ontologyReq = new ServiceElement();
		attributes = withAttributes.get("notationReq");
		ontologyReq.setName(attributes.get("ontology"));
		ontologyReq.setVersion("");
		ontologyReq.setRef(attributes.get("ref"));	
		
		semanticsReq.setStandard(standardReq);
		semanticsReq.setOntology(ontologyReq);
		
		// Set Security and QoS
		Security securityReq = new Security();
		attributes = withAttributes.get("securityReq");
		securityReq.setMode(attributes.get("mode"));
		securityReq.setDomain(attributes.get("domain"));
		securityReq.setIdentityAuth(attributes.get("identityAuth"));
		
		attributes = withAttributes.get("qosReq");
		int qosReq = Integer.parseInt(attributes.get("level"));		
		
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
		attributes = withAttributes.get("optionEncodeRes");
		encodingRes.setName(attributes.get("name"));
		encodingRes.setVersion(attributes.get("version"));
		encodingRes.setRef("");
		
		// Set Semantics (standard and ontology)
		Semantics semanticsRes = new Semantics();
		
		ServiceElement standardRes = new ServiceElement();
		attributes = withAttributes.get("standardRes");
		standardRes.setName(attributes.get("name"));
		standardRes.setVersion(attributes.get("version"));
		attributes = withAttributes.get("extRefRes");
		standardRes.setRef(attributes.get("ref"));
		
		ServiceElement ontologyRes = new ServiceElement();
		attributes = withAttributes.get("notationRes");
		ontologyRes.setName(attributes.get("ontology"));
		ontologyRes.setVersion("");
		ontologyRes.setRef(attributes.get("ref"));	
		
		semanticsRes.setStandard(standardRes);
		semanticsRes.setOntology(ontologyRes);
		
		// Set Security and QoS
		Security securityRes = new Security();
		attributes = withAttributes.get("securityRes");
		securityRes.setMode(attributes.get("mode"));
		securityRes.setDomain(attributes.get("domain"));
		securityRes.setIdentityAuth(attributes.get("identityAuth"));
		
		attributes = withAttributes.get("qosRes");
		int qosRes = Integer.parseInt(attributes.get("level"));
		
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
