package ai.aitia.mismatch_analysis.entity.serviceContract;

/**
 * Format Object Definition :: Service Contract Definition of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class Format {
	
	//=================================================================================================
	// members
	
	private ServiceElement encoding;
	private Security security;
	private int qos;
	private Semantics semantics;
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public Format() {
		this.encoding = new ServiceElement();
		this.security = new Security();
		this.qos = 0;
		this.semantics = new Semantics();
	}
	
	//-------------------------------------------------------------------------------------------------
	public ServiceElement getEncoding() { return encoding; }
	public void setEncoding(ServiceElement encoding) { this.encoding = encoding; }
	
	public Security getSecurity() {	return security; }
	public void setSecurity(Security security) { this.security = security; }
	
	public int getQos() { return qos; }
	public void setQos(int qos) { this.qos = qos; }
	
	public Semantics getSemantics() { return semantics; }
	public void setSemantics(Semantics semantics) {	this.semantics = semantics;	}
	
	//-------------------------------------------------------------------------------------------------
	@Override
	public boolean equals(Object other) {
		Format otherFormat = (Format) other;
		
		boolean encoding = this.getEncoding().equals(otherFormat.getEncoding());
		boolean security = this.getSecurity().equals(otherFormat.getSecurity());
		boolean qos = this.getQos() == otherFormat.getQos();
		boolean semantics = this.getSemantics().equals(otherFormat.getSemantics());
		
		return encoding && security && qos && semantics; 
	}
	
	
}
