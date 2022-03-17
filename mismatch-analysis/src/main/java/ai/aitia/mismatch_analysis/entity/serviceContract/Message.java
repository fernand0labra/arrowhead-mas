package ai.aitia.mismatch_analysis.entity.serviceContract;

/**
 * Message Object Definition :: Service Contract Definition of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class Message {
	
	//=================================================================================================
	// members
	
	private Format format;
	private Payload payload;
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public Message() {
		this.format = new Format();
		this.payload = new Payload();
	}
	
	//-------------------------------------------------------------------------------------------------
	public Format getFormat() {	return format;	}
	public void setFormat(Format format) { this.format = format; }
	
	public Payload getPayload() { return payload; }
	public void setPayload(Payload payload) { this.payload = payload; }
		
}
