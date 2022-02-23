package mismatchAnalysis.mismatchCheck;

import serviceContract.ServiceContract;

import java.io.File;
import java.util.HashMap;

import mismatchAnalysis.input.InputMain;

public class MismatchCheckMain {
	public static Analysis main(HashMap<String, File> serviceContracts) {
		try {  		
			/* *************************************************** */
			/* Obtain consumer and provider interface descriptions */
			/* *************************************************** */

			// Check file extension
			File consumer = serviceContracts.get("consumer");
			File provider = serviceContracts.get("provider");
			
			Utils utilsConsumer = Utils.checkFileType(consumer);
			Utils utilsProvider = Utils.checkFileType(provider);
			
			/* ************************************************** */
			/* Parse consumer and provider interface descriptions */
			/* ************************************************** */
	
			ServiceContract scConsumer = utilsConsumer.parse(consumer);
			ServiceContract scProvider = utilsProvider.parse(provider);
			
			/* ************************************* */
			/* Show their information in the console */
			/* ************************************* */
			
			System.out.println();
			System.out.println(scConsumer.toString());
			System.out.println("-----------------------------------------------------------------------------------------------------------\n");
			System.out.println(scProvider.toString());
			
			Analysis analysis = utilsConsumer.analyseServiceContract(scConsumer, scProvider);
			
			return analysis;
		}   
		catch (Exception e)   
		{  
			e.printStackTrace();  
		}
		
		return null;
	}
}
