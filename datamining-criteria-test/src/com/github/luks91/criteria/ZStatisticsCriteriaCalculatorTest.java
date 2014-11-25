package com.github.luks91.criteria;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.distance.NodesDistanceFactory;

public class ZStatisticsCriteriaCalculatorTest extends ClusteringCriteriaCalculableTestBase {
	
	@Override
	protected ClusteringCriteriaCalculable getCalculator() {
		return new ZStatisticsCriteriaCalculator();
	}

	public void testWith5VertexesGraph() {
		whenGraphWith5VertexesIsClustered();
		whenDistanceCalculatorIs(NodesDistanceFactory
				.createNeighbourOverlapDistanceCalculator());
		whenExpectedValueIs(-1.6035674514745457d);
		thenCalculatedCriteriaReturnsProperValue();
	}
}