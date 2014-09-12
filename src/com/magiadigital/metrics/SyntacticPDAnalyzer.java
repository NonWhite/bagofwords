package com.magiadigital.metrics;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import edu.upc.freeling.Sentence;
import edu.upc.freeling.TreeOfNode;
import com.magiadigital.structs.CohParagraph;
import com.magiadigital.structs.CohText;

public class SyntacticPDAnalyzer implements ICohAnalyzer {

	static private SyntacticPDAnalyzer instance = new SyntacticPDAnalyzer();
	private String NOUN_PHRASE_INCIDENCE = "DRNP";
	private String VERB_PHRASE_INCIDENCE = "DRVP";
	private String NEGATION_INCIDENCE = "DRNEG";
	private String NOUN_PHRASE = "np";
	private String VERB_PHRASE = "V";
	private String NEGATIONS = "RN";

	public static SyntacticPDAnalyzer getInstance() {
		return instance;
	}

	public SyntacticPDAnalyzer() {
	}

	@Override
	public void analyze(Map<String, Double> toFill, CohText text) {
		toFill.put(NEGATION_INCIDENCE, negationIncidence(text));

		toFill.put(NOUN_PHRASE_INCIDENCE, nounPhraseIncidence(text));

		toFill.put(VERB_PHRASE_INCIDENCE, verbalPhraseIncidence(text));
	}

	int countNodes(TreeOfNode node, String tag) {
		long nch = node.numChildren();
		if (nch == 0) {
			return (node.getParent()
					   .getWord()
					   .getTag()
					   .startsWith(tag)) ? 1 : 0;
		}
		if (node.getParent().getLabel().startsWith(tag))
			return 1;
		int ans = 0;
		TreeOfNode next = null;
		for (int i = 0; i < nch; i++) {
			next = node.nthChildRef(i);
			ans += countNodes(next, tag);
		}

		return ans;
	}

	public double nounPhraseIncidence(CohText text) {
		double ans = 0;
		for (CohParagraph paragraph : text) {
			for (Sentence s : paragraph) {
				ans += countNodes(s.getParseTree(), NOUN_PHRASE);
			}
		}
		return ans;
	}

	public double verbalPhraseIncidence(CohText text) {
		double ans = 0;
		for (CohParagraph p : text) {
			for (Sentence s: p) {
				if (countNodes(s.getParseTree(), VERB_PHRASE) > 0) {
					ans++;
				}
			}
		}
		return ans;
	}

	public double negationIncidence(CohText text) {
		double ans = 0;
		for (CohParagraph p: text) {
			for (Sentence s: p) {
				ans += countNodes(s.getParseTree(), NEGATIONS);
			}
		}
		return ans;
	}

}
