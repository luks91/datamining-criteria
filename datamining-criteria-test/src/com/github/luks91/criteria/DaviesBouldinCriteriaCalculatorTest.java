package com.github.luks91.criteria;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.distance.NodesDistanceFactory;

public class DaviesBouldinCriteriaCalculatorTest extends ClusteringCriteriaCalculableTestBase {

	@Override
	protected ClusteringCriteriaCalculable getCalculator() {
		return new DaviesBouldinCriteriaCalculator();
	}
	
	public void testWith5VertexesGraph() {
		fail("Not implemented");
		
		whenGraphWith5VertexesIsClustered();
		whenDistanceCalculatorIs(NodesDistanceFactory
				.createNeighbourOverlapDistanceCalculator());
		whenExpectedValueIs(1.0d / 3.0d);
		thenCalculatedCriteriaReturnsProperValue();
	}
}
