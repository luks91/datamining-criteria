package com.github.luks91.criteria;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.distance.NodesDistanceFactory;

public class CIndexCriteriaCalculatorTest extends ClusteringCriteriaCalculableTestBase {

	@Override
	protected ClusteringCriteriaCalculable getCalculator() {
		return new CIndexCriteriaCalculator();
	}

	public void testWith5VertexesGraph() {
		whenGraphWith5VertexesIsClustered();
		whenDistanceCalculatorIs(NodesDistanceFactory
				.createNeighbourOverlapDistanceCalculator());
		whenExpectedValueIs(0.0d);
		thenCalculatedCriteriaReturnsProperValue();
	}
}
