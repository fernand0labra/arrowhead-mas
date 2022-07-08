package ai.aitia.mismatch_analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import ai.aitia.mismatch_analysis.entity.DatabaseSetup;
import eu.arrowhead.common.CommonConstants;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, AnalysisProviderConstants.BASE_PACKAGE})
public class AnalysisProviderMain {

	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	public static void main(final String[] args) {
		DatabaseSetup.main(new String[0]);
		SpringApplication.run(AnalysisProviderMain.class, args);
	}	
}
