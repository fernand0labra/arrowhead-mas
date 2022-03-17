package ai.aitia.mismatch_analysis.entity.serviceContract;

/**
 * Security Object Definition :: Service Contract Definition of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class Security {
	
	//=================================================================================================
	// members
	
	private String mode;
	private String domain;
	private String identityAuth;
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public Security() {
		this.mode = "";
		this.domain = "";
		this.identityAuth = "";
	}
	
	//-------------------------------------------------------------------------------------------------
	public String getMode() { return mode; }
	public void setMode(String mode) { this.mode = mode; }
	
	public String getDomain() { return domain; }
	public void setDomain(String domain) { this.domain = domain; }
	
	public String getIdentityAuth() { return identityAuth; }
	public void setIdentityAuth(String identityAuth) { this.identityAuth = identityAuth; }	
	
	//-------------------------------------------------------------------------------------------------
	@Override
	public boolean equals(Object other) {
		Security otherSecurity = (Security) other;
		
		boolean mode = this.getMode().equals(otherSecurity.getMode());
		boolean domain = this.getDomain().equals(otherSecurity.getDomain());
		boolean identityAuth = this.getIdentityAuth().equals(otherSecurity.getIdentityAuth());
		
		return mode && domain && identityAuth; 
	}
}
