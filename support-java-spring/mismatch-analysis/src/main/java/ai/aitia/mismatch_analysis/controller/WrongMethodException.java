package ai.aitia.mismatch_analysis.controller;

public class WrongMethodException extends Exception {

	//=================================================================================================
	// members
	
	private static final long serialVersionUID = 1L;

	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	public WrongMethodException() {
		super("The method requested does not exist");
	}

	//-------------------------------------------------------------------------------------------------
	public WrongMethodException(String message) {
		super(message);
	}

	//-------------------------------------------------------------------------------------------------
	public WrongMethodException(Throwable cause) {
		super(cause);
	}

	//-------------------------------------------------------------------------------------------------
	public WrongMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	//-------------------------------------------------------------------------------------------------
	public WrongMethodException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
