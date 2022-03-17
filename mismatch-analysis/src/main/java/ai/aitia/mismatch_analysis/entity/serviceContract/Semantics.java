package ai.aitia.mismatch_analysis.entity.serviceContract;

/**
 * Semantics Object Definition :: Service Contract Definition of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class Semantics {
	
	//=================================================================================================
	// members
	
	private ServiceElement standard;
	private ServiceElement ontology;

	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public Semantics() {
		this.standard = new ServiceElement();
		this.ontology = new ServiceElement();
	}
	
	//-------------------------------------------------------------------------------------------------
	public ServiceElement getStandard() { return standard; }
	public void setStandard(ServiceElement standard) { this.standard = standard; }
	
	public ServiceElement getOntology() { return ontology; }
	public void setOntology(ServiceElement ontology) { this.ontology = ontology; }
	
	//-------------------------------------------------------------------------------------------------
	@Override
	public boolean equals(Object other) {
		Semantics otherSemantics = (Semantics) other;
		
		boolean standard = this.getStandard().equals(otherSemantics.getStandard());
		boolean ontology = this.getOntology().equals(otherSemantics.getOntology());
		
		return standard && ontology; 
	}
}
