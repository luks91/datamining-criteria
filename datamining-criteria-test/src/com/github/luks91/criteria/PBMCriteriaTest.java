package com.github.luks91.criteria;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.distance.NodesDistanceFactory;

public class PBMCriteriaTest extends ClusteringCriteriaCalculableTestBase {
	
	@Override
	protected ClusteringCriteriaCalculable getCalculator() {
		return new PBMCriteria();
	}

	public void testWith5VertexesGraph() {
		fail("Not implemented");
		
		whenGraphWith5VertexesIsClustered();
		whenDistanceCalculatorIs(NodesDistanceFactory
				.createNeighbourOverlapDistanceCalculator());
		whenExpectedValueIs(0.0d);
		thenCalculatedCriteriaReturnsProperValue();
	}
}
