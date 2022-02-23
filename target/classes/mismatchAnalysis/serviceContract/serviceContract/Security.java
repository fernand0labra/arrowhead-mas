package serviceContract;

public class Security {
	private String mode;
	private String domain;
	private String identityAuth;
	
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * @return the identityAuth
	 */
	public String getIdentityAuth() {
		return identityAuth;
	}
	/**
	 * @param identityAuth the identityAuth to set
	 */
	public void setIdentityAuth(String identityAuth) {
		this.identityAuth = identityAuth;
	}	
}
