package ai.aitia.mismatch_analysis.entity.serviceContract;

/**
 * Service Contract Object Definition :: Service Contract Definition of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class ServiceContract {
	
	//=================================================================================================
	// members
	
	private ServiceElement protocol;
	private Method method;
	
	private String system;
	private String service;
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public ServiceContract() {
		this.protocol = new ServiceElement();
		this.method = new Method();
		this.system = "";
		this.service = "";
	}
	
	//-------------------------------------------------------------------------------------------------
	public ServiceElement getProtocol() { return protocol; }
	public void setProtocol(ServiceElement protocol) { this.protocol = protocol; }
	
	public Method getMethod() { return method; }
	public void setMethod(Method method) { this.method = method; }
	
	public String getSystem() { return system; }
	public void setSystem(String system) { this.system = system; }
	
	public String getService() { return service; }
	public void setService(String service) { this.service = service; }

	//-------------------------------------------------------------------------------------------------
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
		
    	Message request = this.getMethod().getRequest();
 
    	ServiceElement encodingReq = request.getFormat().getEncoding();
    	String encodingString = 
    			"\t\t\t\tname: " + encodingReq.getName() +
    			"\n\t\t\t\tversion: " + encodingReq.getVersion() +
    			"\n\t\t\t\tref: " + encodingReq.getRef() + "\n";
    			
    	Security securityReq = request.getFormat().getSecurity();
    	String securityString = 
    			"\t\t\t\tmode: " + securityReq.getMode() +
    			"\n\t\t\t\tdomain: " + securityReq.getDomain() +
    			"\n\t\t\t\tidentityAuth: " + securityReq.getIdentityAuth() + "\n";
    	
    	int qosReq = request.getFormat().getQos();
    	String qosString = "\t\t\t\tqos: " + String.valueOf(qosReq) + "\n";
    	
    	Semantics semanticsReq = request.getFormat().getSemantics();
    	String semanticsString = 
			"\t\t\t\tSTANDARD:\n" +
    			"\t\t\t\t\tname: " + semanticsReq.getStandard().getName() +
    			"\n\t\t\t\t\tversion: " + semanticsReq.getStandard().getVersion() +
    			"\n\t\t\t\t\tref: " + semanticsReq.getStandard().getRef() + "\n" +
			"\t\t\t\tONTOLOGY:\n" +
    			"\t\t\t\t\tname: " + semanticsReq.getOntology().getName() +
    			"\n\t\t\t\t\tversion: " + semanticsReq.getOntology().getVersion() +
    			"\n\t\t\t\t\tref: " + semanticsReq.getOntology().getRef() + "\n";
    	
    	String requestString = 
    			"\tREQUEST:\n" +
    			"\t\tFORMAT:\n" +
    				"\t\t\tENCODING:\n" + encodingString +  
    				"\t\t\tSECURITY:\n" + securityString +
    				"\t\t\tQOS:\n" + qosString +
    				"\t\t\tSEMANTICS:\n" + semanticsString +
    			"\t\tNOTATION: \n" + 
    				"\t\t\t" + request.getPayload().getContents().toString() + "\n";
    				    	
    	/* ******************** */
    	/* Response Information */
    	/* ******************** */
    	
    	Message response = method.getResponse();
    	 
    	ServiceElement encodingRes = response.getFormat().getEncoding();
    	encodingString = 
    			"\t\t\t\tname: " + encodingRes.getName() +
    			"\n\t\t\t\tversion: " + encodingRes.getVersion() +
    			"\n\t\t\t\tref: " + encodingRes.getRef() + "\n";
    			
    	Security securityRes = response.getFormat().getSecurity();
    	securityString = 
    			"\t\t\t\tmode: " + securityRes.getMode() +
    			"\n\t\t\t\tdomain: " + securityRes.getDomain() +
    			"\n\t\t\t\tidentityAuth: " + securityRes.getIdentityAuth() + "\n";
    	
    	int qosRes = request.getFormat().getQos();
    	qosString = "\t\t\t\tqos: " + String.valueOf(qosRes) + "\n";
    	
    	Semantics semanticsRes = response.getFormat().getSemantics();
    	semanticsString = 
			"\t\t\t\tSTANDARD:\n" +
    			"\t\t\t\t\tname: " + semanticsRes.getStandard().getName() +
    			"\n\t\t\t\t\tversion: " + semanticsRes.getStandard().getVersion() +
    			"\n\t\t\t\t\tref: " + semanticsRes.getStandard().getRef() + "\n" +
			"\t\t\t\tONTOLOGY:\n" +
    			"\t\t\t\t\tname: " + semanticsRes.getOntology().getName() +
    			"\n\t\t\t\t\tversion: " + semanticsRes.getOntology().getVersion() +
    			"\n\t\t\t\t\tref: " + semanticsRes.getOntology().getRef() + "\n";
    	
    	String responseString = 
    			"\tRESPONSE:\n" +
    			"\t\tFORMAT:\n" +
    				"\t\t\tENCODING:\n" + encodingString +  
    				"\t\t\tSECURITY:\n" + securityString +
    				"\t\t\tQOS:\n" + qosString +
    				"\t\t\tSEMANTICS:\n" + semanticsString +
        		"\t\tNOTATION: \n" + 
    				"\t\t\t" + response.getPayload().getContents().toString() + "\n";
    	
    	String methodString = this.getMethod().getName() + "(" + method.getId() + ")" + ":\n" + requestString + responseString;
    	
    	return "SERVICE CONTRACT\n\n" + protocolString + methodString;
	}
}
