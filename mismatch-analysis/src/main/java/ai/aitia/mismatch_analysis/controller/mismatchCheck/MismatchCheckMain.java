package ai.aitia.mismatch_analysis.controller.mismatchCheck;

import ai.aitia.mismatch_analysis.entity.Analysis;
import ai.aitia.mismatch_analysis.entity.serviceContract.ServiceContract;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main :: Mismatch Check Module of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class MismatchCheckMain {
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public static ArrayList<Analysis> main(HashMap<String, ArrayList<ServiceContract>> serviceContracts) {
		ServiceContract consumer = serviceContracts.get("consumer").get(0); // Obtain the consumer SC
		ArrayList<ServiceContract> providers = serviceContracts.get("provider"); // Obtain the list of provider SCs
		
		ArrayList<Analysis> analysisList = new ArrayList<Analysis>();
		
		// Analyse each of the SCs of the provider
		for(ServiceContract provider : providers)
			analysisList.add(MismatchUtils.analyse(consumer, provider));
			
		
		return analysisList;
	}

}
