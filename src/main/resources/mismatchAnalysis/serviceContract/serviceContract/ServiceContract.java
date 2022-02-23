package serviceContract;

public class ServiceContract {
	private ServiceElement protocol;
	private Message request;
	private Message response;
	
	/**
	 * @return the protocol
	 */
	public ServiceElement getProtocol() {
		return protocol;
	}
	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(ServiceElement protocol) {
		this.protocol = protocol;
	}
	/**
	 * @return the request
	 */
	public Message getRequest() {
		return request;
	}
	/**
	 * @param request the request to set
	 */
	public void setRequest(Message request) {
		this.request = request;
	}
	/**
	 * @return the response
	 */
	public Message getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(Message response) {
		this.response = response;
	}
	
	// Overriding toString() method of String class
    @Override
    public String toString() {
    	/* ******************** */
    	/* Protocol Information */
    	/* ******************** */
    	
    	ServiceElement protocol = this.getProtocol();
    	String protocolString = "PROTOCOL:\n" + 
    			"\tName:" + protocol.getName() +
    			"\tVersion:" + protocol.getVersion() +
    			"\tRef:" + protocol.getRef() + "\n\n";
    			
    	/* ******************* */
    	/* Request Information */
    	/* ******************* */
    	
    	Message request = this.getRequest();
 
    	ServiceElement encodingReq = request.getFormat().getEncoding();
    	String encodingString = 
    			"\t\t\tname: " + encodingReq.getName() +
    			"\n\t\t\tversion: " + encodingReq.getVersion() +
    			"\n\t\t\tref: " + encodingReq.getRef() + "\n";
    			
    	Security securityReq = request.getFormat().getSecurity();
    	String securityString = 
    			"\t\t\tmode: " + securityReq.getMode() +
    			"\n\t\t\tdomain: " + securityReq.getDomain() +
    			"\n\t\t\tidentityAuth: " + securityReq.getIdentityAuth() + "\n";
    	
    	int qosReq = request.getFormat().getQos();
    	String qosString = "\t\t\tqos: " + String.valueOf(qosReq) + "\n";
    	
    	Semantics semanticsReq = request.getFormat().getSemantics();
    	String semanticsString = 
			"\t\t\tSTANDARD:\n" +
    			"\t\t\t\tname: " + semanticsReq.getStandard().getName() +
    			"\n\t\t\t\tversion: " + semanticsReq.getStandard().getVersion() +
    			"\n\t\t\t\tref: " + semanticsReq.getStandard().getRef() + "\n" +
			"\t\t\tONTOLOGY:\n" +
    			"\t\t\t\tname: " + semanticsReq.getOntology().getName() +
    			"\n\t\t\t\tversion: " + semanticsReq.getOntology().getVersion() +
    			"\n\t\t\t\tref: " + semanticsReq.getOntology().getRef() + "\n";
    	
    	String requestString = 
    			"REQUEST:\n" +
    			"\tFORMAT:\n" +
    				"\t\tENCODING:\n" + encodingString +  
    				"\t\tSECURITY:\n" + securityString +
    				"\t\tQOS:\n" + qosString +
    				"\t\tSEMANTICS:\n" + semanticsString;
    	
    	/* ******************** */
    	/* Response Information */
    	/* ******************** */
    	
    	Message response = this.getResponse();
    	 
    	ServiceElement encodingRes = response.getFormat().getEncoding();
    	encodingString = 
    			"\t\t\tname: " + encodingRes.getName() +
    			"\n\t\t\tversion: " + encodingRes.getVersion() +
    			"\n\t\t\tref: " + encodingRes.getRef() + "\n";
    			
    	Security securityRes = response.getFormat().getSecurity();
    	securityString = 
    			"\t\t\tmode: " + securityRes.getMode() +
    			"\n\t\t\tdomain: " + securityRes.getDomain() +
    			"\n\t\t\tidentityAuth: " + securityRes.getIdentityAuth() + "\n";
    	
    	int qosRes = request.getFormat().getQos();
    	qosString = "\t\t\tqos: " + String.valueOf(qosRes) + "\n";
    	
    	Semantics semanticsRes = response.getFormat().getSemantics();
    	semanticsString = 
			"\t\t\tSTANDARD:\n" +
    			"\t\t\t\tname: " + semanticsRes.getStandard().getName() +
    			"\n\t\t\t\tversion: " + semanticsRes.getStandard().getVersion() +
    			"\n\t\t\t\tref: " + semanticsRes.getStandard().getRef() + "\n" +
			"\t\t\tONTOLOGY:\n" +
    			"\t\t\t\tname: " + semanticsRes.getOntology().getName() +
    			"\n\t\t\t\tversion: " + semanticsRes.getOntology().getVersion() +
    			"\n\t\t\t\tref: " + semanticsRes.getOntology().getRef() + "\n";
    	
    	String responseString = 
    			"RESPONSE:\n" +
    			"\tFORMAT:\n" +
    				"\t\tENCODING:\n" + encodingString +  
    				"\t\tSECURITY:\n" + securityString +
    				"\t\tQOS:\n" + qosString +
    				"\t\tSEMANTICS:\n" + semanticsString;
    	
        return "SERVICE CONTRACT\n\n" + protocolString + requestString + responseString;
    }
}
