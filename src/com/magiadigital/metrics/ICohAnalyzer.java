package com.magiadigital.metrics;

import java.util.Map;

import com.magiadigital.structs.CohText;

public interface ICohAnalyzer {
	void analyze(Map<String, Double> toFill, CohText text);
}
