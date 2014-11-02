package com.github.luks91.criteria;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.distance.NodesDistanceFactory.INodesDistanceCalculable;
import com.github.luks91.test.GraphTestBase;

abstract class ClusteringCriteriaCalculableTestBase extends GraphTestBase {

	private static final double DOUBLE_SIGMA = 0.000000000000001;
	
	private INodesDistanceCalculable mDistanceCalculator;
	private double mExpectedValue;
	
	protected abstract ClusteringCriteriaCalculable getCalculator();
	
	protected void whenExpectedValueIs(double val) {
		mExpectedValue = val;
	}

	protected void whenDistanceCalculatorIs(INodesDistanceCalculable distanceCalc) {
		mDistanceCalculator = distanceCalc;
	}
	
	protected void thenCalculatedCriteriaReturnsProperValue() {
		assertEquals(mExpectedValue,
				getCalculator().calculateCriteria(mClusteredDataset, mDistanceCalculator), 
						DOUBLE_SIGMA);
	}
}
