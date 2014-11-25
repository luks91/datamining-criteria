package com.github.luks91.criteria;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.distance.NodesDistanceFactory;

public class VarianceRatioCriteriaCalculatorTest extends ClusteringCriteriaCalculableTestBase {
	
	@Override
	protected ClusteringCriteriaCalculable getCalculator() {
		return new VarianceRatioCriteriaCalculator();
	}

	public void testWith5VertexesGraph() {
		whenGraphWith5VertexesIsClustered();
		whenDistanceCalculatorIs(NodesDistanceFactory
				.createNeighbourOverlapDistanceCalculator());
		whenExpectedValueIs(8.999999999999998);
		thenCalculatedCriteriaReturnsProperValue();
	}
}