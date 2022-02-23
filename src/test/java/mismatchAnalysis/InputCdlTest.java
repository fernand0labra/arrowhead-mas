package mismatchAnalysis;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;

import org.junit.Test;

import mismatchAnalysis.input.InputMain;

public class InputCdlTest {

	@Test
	public void cdlNormal() {
		String[] args = {"cdl", "normal"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/cdl/cdl_consumer.xml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/cdl/providers/cdl_provider/cdl_provider.xml").getPath()));
		assertEquals(result, InputMain.main(args));
	}
	
	@Test
	public void cdlProtocol() {
		String[] args = {"cdl", "protocol"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/cdl/cdl_consumer.xml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/cdl/providers/cdl_provider_pro/cdl_provider_pro.xml").getPath()));
		assertEquals(result, InputMain.main(args));
	}
	
	@Test
	public void cdlEncoding() {
		String[] args = {"cdl", "encoding"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/cdl/cdl_consumer.xml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/cdl/providers/cdl_provider_enc/cdl_provider_enc.xml").getPath()));
		assertEquals(result, InputMain.main(args));
	}
	
	@Test
	public void cdlSemantics() {
		String[] args = {"cdl", "semantics"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/cdl/cdl_consumer.xml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/cdl/providers/cdl_provider_sem/cdl_provider_sem.xml").getPath()));
		assertEquals(result, InputMain.main(args));
	}
	
	@Test
	public void cdlNotation() {
		String[] args = {"cdl", "notation"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/cdl/cdl_consumer.xml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/cdl/providers/cdl_provider_not/cdl_provider_not.xml").getPath()));
		assertEquals(result, InputMain.main(args));
	}
	
	@Test
	public void cdlProtocolEncoding() {
		String[] args = {"cdl", "protocol_encoding"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/cdl/cdl_consumer.xml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/cdl/providers/cdl_provider_pro_enc/cdl_provider_pro_enc.xml").getPath()));
		assertEquals(result, InputMain.main(args));
	}
	
	@Test
	public void cdlProtocolEncodingSemantics() {
		String[] args = {"cdl", "protocol_encoding_semantics"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/cdl/cdl_consumer.xml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/cdl/providers/cdl_provider_pro_enc_sem/cdl_provider_pro_enc_sem.xml").getPath()));
		assertEquals(result, InputMain.main(args));
	}
	
	@Test
	public void cdlProtocolEncodingSemanticsNotation() {
		String[] args = {"cdl", "protocol_encoding_semantics_notation"};
		HashMap<String, File> result = new HashMap<String, File>();
		result.put("consumer", new File(InputMain.class.getResource("/cdl/cdl_consumer.xml").getPath()));
		result.put("provider", new File(InputMain.class.getResource("/cdl/providers/cdl_provider_pro_enc_sem_not/cdl_provider_pro_enc_sem_not.xml").getPath()));
		assertEquals(result, InputMain.main(args));
	}
}
