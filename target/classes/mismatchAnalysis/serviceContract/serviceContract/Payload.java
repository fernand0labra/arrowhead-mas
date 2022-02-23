package serviceContract;

import java.util.HashMap;

public class Payload {
	private HashMap<String, HashMap<String, String>> contents;

	/**
	 * @return the contents
	 */
	public HashMap<String, HashMap<String, String>> getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(HashMap<String, HashMap<String, String>> contents) {
		this.contents = contents;
	}	
}
