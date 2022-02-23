package mismatchAnalysis;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class MismatchAnalysisOpenapiTest {

	@Test
	public void equalContracts() {
		String[] args = {"openapi", "normal"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("OK");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
	
	@Test
	public void encodingDifference() {
		String[] args = {"openapi", "encoding"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_G");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
	
	@Test
	public void semanticsDifference() {
		String[] args = {"openapi", "semantics"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_G");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
	
	@Test
	public void notationDifference() {
		String[] args = {"openapi", "notation"};
		ArrayList<String> result = new ArrayList<String>();
		result.add("NOT_OK");
		assertEquals(result, MismatchAnalysisMain.main(args));
	}
}
