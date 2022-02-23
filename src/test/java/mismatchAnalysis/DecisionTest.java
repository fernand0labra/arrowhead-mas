package mismatchAnalysis;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import mismatchAnalysis.decision.DecisionMain;
import mismatchAnalysis.mismatchCheck.Analysis;

public class DecisionTest {
	@Test
	public void absoluteCompatibility() {
		Analysis args = new Analysis();
		args.setQualitativeM("absolute");
		ArrayList<String> result = new ArrayList<String>();
		result.add("OK");
		assertEquals(result, DecisionMain.main(args));
	}
	
	@Test
	public void highCompatibilityProtocol() {
		Analysis args = new Analysis();
		args.getMismatch().get("protocol").put("protocol", 0);
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_T");
		assertEquals(result, DecisionMain.main(args));
	}
	
	@Test
	public void highCompatibilityEncoding() {
		Analysis args = new Analysis();
		args.getMismatch().get("encoding").put("mediaTypeReq", 0);
		args.getMismatch().get("encoding").put("mediaTypeRes", 0);
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_G");
		assertEquals(result, DecisionMain.main(args));
	}

	@Test
	public void highCompatibilityStandard() {
		Analysis args = new Analysis();
		args.getMismatch().get("standard").put("nameReq", 0);
		args.getMismatch().get("standard").put("nameRes", 0);
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_G");
		assertEquals(result, DecisionMain.main(args));
	}
	
	@Test
	public void highCompatibilityOntology() {
		Analysis args = new Analysis();
		args.getMismatch().get("ontology").put("nameReq", 0);
		args.getMismatch().get("ontology").put("nameRes", 0);
		ArrayList<String> result = new ArrayList<String>();
		result.add("NOT_OK");
		assertEquals(result, DecisionMain.main(args));
	}
	
	@Test
	public void mediumCompatibilityProtocolEncoding() {
		Analysis args = new Analysis();
		args.getMismatch().get("protocol").put("protocol", 0);
		args.getMismatch().get("encoding").put("mediaTypeReq", 0);
		args.getMismatch().get("encoding").put("mediaTypeRes", 0);
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_T");
		result.add("ALTER_G");
		assertEquals(result, DecisionMain.main(args));
	}
	
	@Test
	public void lowCompatibilityProtocolEncodingSemantics() {
		Analysis args = new Analysis();
		args.getMismatch().get("protocol").put("protocol", 0);
		args.getMismatch().get("encoding").put("mediaTypeReq", 0);
		args.getMismatch().get("encoding").put("mediaTypeRes", 0);
		args.getMismatch().get("standard").put("nameReq", 0);
		args.getMismatch().get("standard").put("nameRes", 0);
		ArrayList<String> result = new ArrayList<String>();
		result.add("ALTER_T");
		result.add("ALTER_G");
		assertEquals(result, DecisionMain.main(args));
	}
}
