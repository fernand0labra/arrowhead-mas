package mismatchAnalysis;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;

import org.junit.Test;

import mismatchAnalysis.input.InputMain;

public class InputOpenapiTest {

	@Test
	public void openapiNormal() {
		String[] args = {"openapi", "normal"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/openapi/openapi_consumer.yaml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/openapi/providers/openapi_provider/openapi_provider.yaml").getPath()));
		assertEquals(result, InputMain.main(args));
	}
	
	@Test
	public void openapiEncoding() {
		String[] args = {"openapi", "encoding"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/openapi/openapi_consumer.yaml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/openapi/providers/openapi_provider_enc/openapi_provider_enc.yaml").getPath()));
		assertEquals(result, InputMain.main(args));
	}
	
	@Test
	public void openapiSemantics() {
		String[] args = {"openapi", "semantics"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/openapi/openapi_consumer.yaml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/openapi/providers/openapi_provider_sem/openapi_provider_sem.yaml").getPath()));
		assertEquals(result, InputMain.main(args));
	}
	
	@Test
	public void openapiNotation() {
		String[] args = {"openapi", "notation"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/openapi/openapi_consumer.yaml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/openapi/providers/openapi_provider_not/openapi_provider_not.yaml").getPath()));
		assertEquals(result, InputMain.main(args));
	}

}
