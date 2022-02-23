package mismatchAnalysis;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class MismatchAnalysisCdlTest {

	@Test
	public void equalContracts() {
		String[] args = {"cdl", "normal"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("OK");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
	
	@Test
	public void protocolDifference() {
		String[] args = {"cdl", "protocol"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_T");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
	
	@Test
	public void encodingDifference() {
		String[] args = {"cdl", "encoding"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_G");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
	
	@Test
	public void semanticsDifference() {
		String[] args = {"cdl", "semantics"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_G");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
	
	@Test
	public void notationDifference() {
		String[] args = {"cdl", "notation"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("NOT_OK");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
	
	@Test
	public void protocolEncodingDifference() {
		String[] args = {"cdl", "protocol_encoding"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_T");
		result.add("ALTER_G");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
	
	@Test
	public void protocolEncodingSemanticsDifference() {
		String[] args = {"cdl", "protocol_encoding_semantics"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_T");
		result.add("ALTER_G");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
	
	@Test
	public void protocolEncodingSemanticsNotationDifference() {
		String[] args = {"cdl", "protocol_encoding_semantics_notation"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("NOT_OK");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
}
