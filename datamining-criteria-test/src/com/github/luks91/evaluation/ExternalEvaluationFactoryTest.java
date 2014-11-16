package com.github.luks91.evaluation;

import junit.framework.TestCase;

import com.github.luks91.evaluation.ExternalEvaluationFactory.ExternalEvaluationCalculable;

public class ExternalEvaluationFactoryTest extends TestCase {

	public void testCreateAdjustedRandIndexCalculator() {
		ExternalEvaluationCalculable constructedObject = 
				ExternalEvaluationFactory.createAdjustedRandIndexCalculator();
		
		assertNotNull(constructedObject);
		assertTrue(constructedObject instanceof AdjustedRandIndexExternalEvaluationCalculator);
	}
	
	public void testCreateJaccardCoefficientCalculator() {
		ExternalEvaluationCalculable constructedObject = 
				ExternalEvaluationFactory.createJaccardCoefficientCalculator();
		
		assertNotNull(constructedObject);
		assertTrue(constructedObject instanceof JaccardCoefficientExternalEvaluationCalculator);
	}
}
