package serviceContract;

public class Message {
	private Format format;
	private Payload payload;
	
	/**
	 * @return the format
	 */
	public Format getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(Format format) {
		this.format = format;
	}
	/**
	 * @return the payload
	 */
	public Payload getPayload() {
		return payload;
	}
	/**
	 * @param payload the payload to set
	 */
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	
	
}
