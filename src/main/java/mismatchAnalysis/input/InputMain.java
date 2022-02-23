package mismatchAnalysis.input;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import mismatchAnalysis.MismatchAnalysisMain;
import mismatchAnalysis.mismatchCheck.Utils;

public class InputMain {
	public static HashMap<String, File> main(String[] args) {
		/* *************************************************** */
		/* Obtain consumer and provider interface descriptions */
		/* *************************************************** */

		// Store them in a map with the name of the file
		HashMap<String, File> serviceContracts = new HashMap<String, File>();
		URL url = null;
		File consumer = null;
		File provider = null;

		switch(args[0]) {
			case "cdl":
				url = InputMain.class.getResource("/cdl/cdl_consumer.xml");
				consumer = new File(url.getPath());  
				serviceContracts.put("consumer", consumer);
	
				switch(args[1]) {
					case "normal":
						url = InputMain.class.getResource("/cdl/providers/cdl_provider/cdl_provider.xml");
						break;
					case "encoding":
						url = InputMain.class.getResource("/cdl/providers/cdl_provider_enc/cdl_provider_enc.xml");
						break;
					case "notation":
						url = InputMain.class.getResource("/cdl/providers/cdl_provider_not/cdl_provider_not.xml");
						break;
					case "protocol":
						url = InputMain.class.getResource("/cdl/providers/cdl_provider_pro/cdl_provider_pro.xml");
						break;
					case "semantics":
						url = InputMain.class.getResource("/cdl/providers/cdl_provider_sem/cdl_provider_sem.xml");
						break;
					case "protocol_encoding":
						url = InputMain.class.getResource("/cdl/providers/cdl_provider_pro_enc/cdl_provider_pro_enc.xml");
						break;
					case "protocol_encoding_semantics":
						url = InputMain.class.getResource("/cdl/providers/cdl_provider_pro_enc_sem/cdl_provider_pro_enc_sem.xml");
						break;
					case "protocol_encoding_semantics_notation":
						url = InputMain.class.getResource("/cdl/providers/cdl_provider_pro_enc_sem_not/cdl_provider_pro_enc_sem_not.xml");
						break;
					}
				break;
			case "openapi":
				url = InputMain.class.getResource("/openapi/openapi_consumer.yaml");
				consumer = new File(url.getPath());  
				serviceContracts.put("consumer", consumer);
	
				switch(args[1]) {
					case "normal":
						url = InputMain.class.getResource("/openapi/providers/openapi_provider/openapi_provider.yaml");
						break;
					case "encoding":
						url = InputMain.class.getResource("/openapi/providers/openapi_provider_enc/openapi_provider_enc.yaml");
						break;
					case "notation":
						url = InputMain.class.getResource("/openapi/providers/openapi_provider_not/openapi_provider_not.yaml");
						break;
					case "semantics":
						url = InputMain.class.getResource("/openapi/providers/openapi_provider_sem/openapi_provider_sem.yaml");
						break;
				}
				break;
			}

		provider = new File(url.getPath());
		serviceContracts.put("provider", provider);

		return serviceContracts;
	}

}
