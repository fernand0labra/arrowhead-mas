package ai.aitia.mismatch_analysis.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ai.aitia.mismatch_analysis.AnalysisProviderConstants;
import ai.aitia.mismatch_analysis.controller.decision.DecisionMain;
import ai.aitia.mismatch_analysis.controller.input.InputMain;
import ai.aitia.mismatch_analysis.controller.mismatchCheck.MismatchCheckMain;
import ai.aitia.mismatch_analysis.entity.Analysis;
import ai.aitia.mismatch_analysis.entity.dto.AnalysisRequestDTO;
import ai.aitia.mismatch_analysis.entity.dto.AnalysisResponseDTO;
import ai.aitia.mismatch_analysis.entity.dto.DTOConverter;

@RestController
@RequestMapping(AnalysisProviderConstants.ANALYSIS_URI)
public class AnalysisServiceController {
	
		//=================================================================================================
		// methods
	
		//-------------------------------------------------------------------------------------------------
		@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody public AnalysisResponseDTO analyseService(@RequestBody final AnalysisRequestDTO analysisRequestDTO){				
			Analysis analysis = new Analysis();
			
			String request = 	"\n\n***********************************************************************************************************\n" +
								"***********************************************************************************************************\n\n" +
					"REQUEST\n" + 
						"\tServiceDefinition: " + analysisRequestDTO.getServiceDefinition().toString() + "\n" +
						"\tSystems: \n" + "\t\t" + analysisRequestDTO.getSystems().toString();
			
			System.out.print(request);
			
			try {
				analysis = DecisionMain.main(MismatchCheckMain.main(InputMain.main(
								analysisRequestDTO.getServiceDefinition(),
								analysisRequestDTO.getSystems()))
							);
			} catch(Exception e) {
				System.err.println(e.getMessage());
				analysis.setFlag("NOT_OK");				
			}
		
			return DTOConverter.convertAnalysisToAnalysisResponseDTO(analysis);
		}
}
