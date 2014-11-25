package com.github.luks91.criteria;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.distance.NodesDistanceFactory;

public class PointBiserialCriteriaCalculatorTest extends ClusteringCriteriaCalculableTestBase {
	
	@Override
	protected ClusteringCriteriaCalculable getCalculator() {
		return new PointBiserialCriteriaCalculator();
	}

	public void testWith5VertexesGraph() {
		whenGraphWith5VertexesIsClustered();
		whenDistanceCalculatorIs(NodesDistanceFactory
				.createNeighbourOverlapDistanceCalculator());
		whenExpectedValueIs(-1.1547005383792515);
		thenCalculatedCriteriaReturnsProperValue();
	}
}