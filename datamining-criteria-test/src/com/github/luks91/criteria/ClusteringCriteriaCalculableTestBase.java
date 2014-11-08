package com.github.luks91.criteria;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;
import com.github.luks91.test.GraphTestBase;

abstract class ClusteringCriteriaCalculableTestBase extends GraphTestBase {

	private static final double DOUBLE_SIGMA = 0.000000000000001;
	
	private NodesDistanceCalculable mDistanceCalculator;
	private double mExpectedValue;
	
	protected abstract ClusteringCriteriaCalculable getCalculator();
	
	protected void whenExpectedValueIs(double val) {
		mExpectedValue = val;
	}

	protected void whenDistanceCalculatorIs(NodesDistanceCalculable distanceCalc) {
		mDistanceCalculator = distanceCalc;
	}
	
	protected void thenCalculatedCriteriaReturnsProperValue() {
		assertEquals(mExpectedValue,
				getCalculator().calculateCriteria(mClusteredDataset, mDistanceCalculator), 
						DOUBLE_SIGMA);
	}
}
