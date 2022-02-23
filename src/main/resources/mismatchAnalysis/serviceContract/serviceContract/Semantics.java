package serviceContract;

public class Semantics {
	private ServiceElement standard;
	private ServiceElement ontology;

	/**
	 * @return the standard
	 */
	public ServiceElement getStandard() {
		return standard;
	}

	/**
	 * @param standard the standard to set
	 */
	public void setStandard(ServiceElement standard) {
		this.standard = standard;
	}

	/**
	 * @return the ontology
	 */
	public ServiceElement getOntology() {
		return ontology;
	}

	/**
	 * @param ontology the ontology to set
	 */
	public void setOntology(ServiceElement ontology) {
		this.ontology = ontology;
	}
}
