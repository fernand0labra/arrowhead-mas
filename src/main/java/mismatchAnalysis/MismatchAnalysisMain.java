package mismatchAnalysis;

import java.util.ArrayList;

import mismatchAnalysis.decision.DecisionMain;
import mismatchAnalysis.input.InputMain;
import mismatchAnalysis.mismatchCheck.MismatchCheckMain;

public class MismatchAnalysisMain {
	
	public static ArrayList<String> main(String[] args) {
		return DecisionMain.main(MismatchCheckMain.main(InputMain.main(args)));
	}
}
