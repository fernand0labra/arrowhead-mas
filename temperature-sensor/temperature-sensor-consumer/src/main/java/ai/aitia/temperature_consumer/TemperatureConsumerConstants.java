package ai.aitia.temperature_consumer;

public class TemperatureConsumerConstants {
	
	//=================================================================================================
	// members
	
	public static final String BASE_PACKAGE = "ai.aitia";
	
	public static final String INTERFACE_SECURE = "HTTP-SECURE-JSON";
	public static final String INTERFACE_INSECURE = "HTTP-INSECURE-JSON";
	public static final String HTTP_METHOD = "http-method";
	
	public static final String GET_TEMPERATURE_SERVICE_DEFINITION = "get-temperature";
	public static final String REQUEST_PARAM_KEY_UNIT = "request-param-unit";
	
	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	private TemperatureConsumerConstants() {
		throw new UnsupportedOperationException();
	}

}
