package com.github.luks91.criteria;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.distance.NodesDistanceFactory;

public class PBMCriteriaCalculatorTest extends ClusteringCriteriaCalculableTestBase {
	
	@Override
	protected ClusteringCriteriaCalculable getCalculator() {
		return new PBMCriteriaCalculator();
	}

	public void testWith5VertexesGraph() {
		whenGraphWith5VertexesIsClustered();
		whenDistanceCalculatorIs(NodesDistanceFactory
				.createNeighbourOverlapDistanceCalculator());
		whenExpectedValueIs(0.5d);
		thenCalculatedCriteriaReturnsProperValue();
	}
}
