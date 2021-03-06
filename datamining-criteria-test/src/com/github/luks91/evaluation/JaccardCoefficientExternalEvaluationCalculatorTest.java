package com.github.luks91.evaluation;


public class JaccardCoefficientExternalEvaluationCalculatorTest 
	extends ExternalEvaluationTestBase {

	private double mCalculatedEvaluationValue;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mCalculatedEvaluationValue = 0.0d;
	}
	
	public void testCalculateFor5VerticesGraph() {
		whenGraphWith5VertexesIsClustered();
		whenRandomClusteringHasBeenObtained();
		whenExternalEvaluationIsCalculated();
		thenReceivedResultEqualsToTheExpected();
	}
	
	private void whenExternalEvaluationIsCalculated() {
		mCalculatedEvaluationValue = 
				new JaccardCoefficientExternalEvaluationCalculator()
					.calculateExternalEvaluation(mRandomClustering, mClusteredDataset);
	}
	
	private void thenReceivedResultEqualsToTheExpected() {
		assertEquals(0.33333333d, mCalculatedEvaluationValue, 
				0.0000001d);
	}
	
	public void testForTheSameClustering() {
		whenGraphWith5VertexesIsClustered();
		mCalculatedEvaluationValue = 
				new JaccardCoefficientExternalEvaluationCalculator()
					.calculateExternalEvaluation(mClusteredDataset, mClusteredDataset);
		assertEquals(1.0d, mCalculatedEvaluationValue);
	}
}
