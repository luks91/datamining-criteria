package com.github.luks91.distance;

import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;
import com.github.luks91.test.GraphTestBase;

abstract class DistanceCalculatorTestBase extends GraphTestBase {

	private static final double DOUBLE_SIGMA = 0.000000000000001;
	
	private int mFirstVertex, mSecondVertex;
	private double mExpectedValue;
	
	protected abstract NodesDistanceCalculable getCalculator();
	
	protected void whenValueIsCalculatedForVertexes(int firstVertex,
			int secondVertex) {
		mFirstVertex = firstVertex;
		mSecondVertex = secondVertex;
	}
	
	protected void whenExpectedValueIs(double val) {
		mExpectedValue = val;
	}

	protected void thenCalculatedDistanceReturnsProperValue() {
		assertEquals(mExpectedValue,
				getCalculator().calculate(
						super.mClusteredDataset.getAdjacencyMatrix(),
						mFirstVertex, mSecondVertex), 
						DOUBLE_SIGMA);
	}
	
}
