package serviceContract;

public class Format {
	private ServiceElement encoding;
	private Security security;
	private int qos;
	private Semantics semantics;
	
	/**
	 * @return the encoding
	 */
	public ServiceElement getEncoding() {
		return encoding;
	}
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(ServiceElement encoding) {
		this.encoding = encoding;
	}
	/**
	 * @return the security
	 */
	public Security getSecurity() {
		return security;
	}
	/**
	 * @param security the security to set
	 */
	public void setSecurity(Security security) {
		this.security = security;
	}
	/**
	 * @return the qos
	 */
	public int getQos() {
		return qos;
	}
	/**
	 * @param qos the qos to set
	 */
	public void setQos(int qos) {
		this.qos = qos;
	}
	/**
	 * @return the semantics
	 */
	public Semantics getSemantics() {
		return semantics;
	}
	/**
	 * @param semantics the semantics to set
	 */
	public void setSemantics(Semantics semantics) {
		this.semantics = semantics;
	}
	
	
}
