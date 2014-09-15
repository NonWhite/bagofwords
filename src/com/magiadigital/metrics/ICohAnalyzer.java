package com.magiadigital.metrics;

import java.util.HashMap;

import com.magiadigital.structs.CohText;

import edu.upc.freeling.Word;

public interface ICohAnalyzer {
	void analyze( HashMap<String,Integer> toFill, CohText text);
}