package ai.aitia.mismatch_analysis.controller.input;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import ai.aitia.mismatch_analysis.entity.serviceContract.Payload;
import ai.aitia.mismatch_analysis.entity.serviceContract.ServiceContract;

/**
 * Parsing Utils :: Input Module of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso
 * @version 1.0.0
 */

public abstract class Utils {
	
	//=================================================================================================
	// members
	
	protected String[][] contractKeys; // Common keys of the contract
	protected String[][] methodKeys; // Specific keys for each of the methods
	
	protected String[] operationKeys; // Accepted operations keys
	protected String[] responseCodeKeys; // Accepted response codes keys
	
	// Common information of the contract
	protected HashMap<String, HashMap<String, String>> contractInfo; 
	// Specific information for each of the methods
	protected ArrayList<HashMap<String, HashMap<String, String>>> methodInfoList;
	// Tags without attributes
	protected List<String> withoutAttributes; 
	
	protected ArrayList<String> operations; // Accepted operations
	protected ArrayList<String> responseCodes; // Accepted response codes
		
	protected Payload reqPayload = new Payload();
	protected Payload resPayload = new Payload();
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Recursively goes through each of the contractInfo tags filling the information
	 * of the contract
	 * 
	 * @param actual		The node which acts as root at a given level
	 * @param parentNames	The parent names of the actual node
	 * @throws Exception 	The interface definition does not contain an allowed method
	 */
	abstract void fillContractInfo(Object actual, LinkedList<String> parentNames) throws Exception;
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Recursively goes through each of the methodInfoList tags filling the information
	 * of the method
	 * 
	 * @param actual 		The node which acts as root at a given level
	 * @param parentNames 	The parent names of the actual node
	 * @param methodInfo	The object in which the information is stored 
	 */
	abstract void fillMethodInfo(Object actual, LinkedList<String> parentNames, HashMap<String, HashMap<String, String>> methodInfo);
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Operates over the contents of the payload and creates an object
	 * from them
	 * 
	 * @param actual 		The node which acts as root at a given level
	 * @param parentNames 	The parent names of the actual node
	 */
	abstract void checkContent(Object actual, LinkedList<String> parentNames);
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Parses a file containing a service definition into a Service Contract 
	 * object
	 * 
	 * @param file 			The file that defines the service interface
	 * @param operationId	The identifier of the requested operation
	 * @return 				The Service Contract object with the information of the file
	 * @throws Exception 	Error in the parsing
	 */
	abstract ServiceContract parse(File file, String operationId) throws Exception;
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Obtains the file extension and returns the necessary utils for
	 * obtaining the Service Contract object
	 * 
	 * @param file 			The file that defines the service interface
	 * @return 				The utils that process the file with that extension
	 * @throws Exception 	File extension not accepted
	 */
	 public static Utils checkFileType(File file) throws Exception{
		 if(FilenameUtils.getExtension(file.getName()).equals("xml"))
			 return new CdlUtils();
		 else if(FilenameUtils.getExtension(file.getName()).equals("yaml"))
			 return new OpenapiUtils();
		 else
			 throw new Exception("File extension not accepted");
	 }	
	 
	//-------------------------------------------------------------------------------------------------
	/**
	 * Fills the list with the given keys
	 * 
	 * @param keys			The keys that need to be added
	 * @param list			The list where the keys are to be added
	 */
	protected void buildList(String[] keys, ArrayList<String> list) {
		for(String key : keys)
			list.add(key);
	}
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Fills the map with the given keys
	 * 
	 * @param keys			The keys that need to be added
	 * @param map			The map where the keys are to be added
	 */
	protected void buildMap(String[][] keys, HashMap<String, HashMap<String, String>> map) {
		for(String[] key : keys) { // Fill the map with the tags and their attributes
			HashMap<String, String> attributes = new HashMap<String, String>();

			if(key.length > 1) { // If the tag contains attributes
				int pos = 1;
				while(pos < key.length) {
					attributes.put(key[pos], ""); // Add the attribute as a key
					pos++;
				}
				map.put(key[0], attributes);
			} else {
				withoutAttributes.add(key[0]);
			}
		}
	}
}
