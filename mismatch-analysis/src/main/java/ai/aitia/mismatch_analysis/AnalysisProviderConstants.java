package ai.aitia.mismatch_analysis;

public class AnalysisProviderConstants {
	
	//=================================================================================================
	// members
	
	public static final String BASE_PACKAGE = "ai.aitia";
	
	public static final String ANALYSIS_SERVICE_DEFINITION = "analyse-service";
	public static final String INTERFACE_SECURE = "HTTP-SECURE-JSON";
	public static final String INTERFACE_INSECURE = "HTTP-INSECURE-JSON";
	public static final String HTTP_METHOD = "http-method";
	public static final String ANALYSIS_URI = "/analysis";

	public static final String REQUEST_PARAM_KEY_SYSTEMS = "request-param-systems";
	public static final String REQUEST_PARAM_KEY_SERVICE_DEFINITION = "request-param-service-definition";
	public static final String REQUEST_PARAM_SYSTEMS = "systems";
	public static final String REQUEST_PARAM_SERVICE_DEFINITION = "serviceDefinition";
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	private AnalysisProviderConstants() {
		throw new UnsupportedOperationException();
	}
}
