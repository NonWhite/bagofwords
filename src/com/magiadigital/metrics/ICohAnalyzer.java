package com.magiadigital.metrics;

import java.util.List;
import java.util.Map;

import com.magiadigital.structs.CohText;

public interface ICohAnalyzer {
	void analyze( HashMap<Word,Integer> toFill, CohText text);
}
