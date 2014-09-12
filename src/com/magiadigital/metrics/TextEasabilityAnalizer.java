package com.magiadigital.metrics;

import java.util.Map;

import com.magiadigital.structs.CohText;

public class TextEasabilityAnalizer implements ICohAnalyzer {
	
	private static TextEasabilityAnalizer instance = new TextEasabilityAnalizer();
	
	public static TextEasabilityAnalizer getInstance() {
		return instance;
	}
	private TextEasabilityAnalizer() {}
	@Override
	public void analyze(Map<String, Double> toFill, CohText text) {

	}

}
