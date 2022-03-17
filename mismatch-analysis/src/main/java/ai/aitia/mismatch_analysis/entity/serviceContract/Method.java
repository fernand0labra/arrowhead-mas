package ai.aitia.mismatch_analysis.entity.serviceContract;

/**
 * Method Object Definition :: Service Contract Definition of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class Method {
	
	//=================================================================================================
	// members
	
	private String name;
	private String id;
	private Message request;
	private Message response;

	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public Method() {
		this.name = "";
		this.id = "";
		this.request = new Message();
		this.response = new Message();
	}
	
	//-------------------------------------------------------------------------------------------------
	public Message getRequest() { return request; }
	public void setRequest(Message request) { this.request = request; }
	
	public Message getResponse() { return response; }
	public void setResponse(Message response) { this.response = response; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
}
