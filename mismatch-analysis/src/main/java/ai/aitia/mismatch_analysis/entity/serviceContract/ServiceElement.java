package ai.aitia.mismatch_analysis.entity.serviceContract;

/**
 * Service Element Object Definition :: Service Contract Definition of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class ServiceElement {
	
	//=================================================================================================
	// members
	
	private String name;
	private String version;
	private String ref;
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public ServiceElement() {
		this.name = "";
		this.version = "";
		this.ref = "";
	}
	
	//-------------------------------------------------------------------------------------------------
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getVersion() { return version; }
 	public void setVersion(String version) { this.version = version; }
 	
	public String getRef() { return ref; }
	public void setRef(String ref) { this.ref = ref; }
	
	//-------------------------------------------------------------------------------------------------
	@Override
	public boolean equals(Object other) {
		ServiceElement otherServiceElement = (ServiceElement) other;
		
		boolean name = this.getName().equals(otherServiceElement.getName());
		boolean version = this.getVersion().equals(otherServiceElement.getVersion());
		boolean ref = this.getRef() == otherServiceElement.getRef();
		
		return name && version && ref; 
	}
}
