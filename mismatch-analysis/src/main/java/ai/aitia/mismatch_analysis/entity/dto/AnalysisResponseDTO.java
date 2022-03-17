package ai.aitia.mismatch_analysis.entity.dto;

import java.io.Serializable;
import java.util.HashMap;

/**
 * AnalysisResponseDTO :: Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class AnalysisResponseDTO implements Serializable {

	//=================================================================================================
	// members

	private static final long serialVersionUID = -5363562707054976998L;

	private HashMap<String, HashMap<String, Integer>> mismatch;
	private HashMap<String, HashMap<String, Integer>> uncertainty;
	private HashMap<String, HashMap<String, HashMap<String, Integer>>> notation;
	
	// Quantitative level of compatibility
	private double quantitativeM;
	// Qualitative level of compatibility
	private String qualitativeM;
	
	// Quantitative level of uncertainty
	private double quantitativeU;
	// Qualitative level of uncertainty
	private String qualitativeU;
	
	// Flag indicating the necessary actions to be performed
	private String flag;
	
	// System for which the analysis has been performed
	private String system;
	
	// Service for which the analysis has been performed
	private String service;
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public AnalysisResponseDTO() {}
	
	//-------------------------------------------------------------------------------------------------
	public AnalysisResponseDTO(
			HashMap<String, HashMap<String, Integer>> mismatch, 
			HashMap<String, HashMap<String, Integer>> uncertainty, 
			HashMap<String, HashMap<String, HashMap<String, Integer>>> notation,
			double quantitativeM, String qualitativeM,
			double quantitativeU, String qualitativeU,
			final String flag, final String system, final String service) {
		
		this.mismatch = mismatch;
		this.uncertainty = uncertainty;
		this.notation = notation;
		this.quantitativeM = quantitativeM; this.qualitativeM = qualitativeM;
		this.quantitativeU = quantitativeU; this.qualitativeU = qualitativeU;
		this.flag = flag;
		this.system = system;
		this.service = service;
	}

	//-------------------------------------------------------------------------------------------------
	public HashMap<String, HashMap<String, Integer>> getMismatch() { return mismatch; }
	public HashMap<String, HashMap<String, Integer>> getUncertainty() { return uncertainty; }
	public HashMap<String, HashMap<String, HashMap<String, Integer>>> getNotation() { return notation; }
	
	public double getQuantitativeM() { return quantitativeM; }
	public String getQualitativeM() { return qualitativeM; }
	
	public double getQuantitativeU() { return quantitativeU; }
	public String getQualitativeU() { return qualitativeU; }
	
	public String getFlag() { return flag; }
	public String getSystem() { return system; }
	public String getService() { return service; }
}
