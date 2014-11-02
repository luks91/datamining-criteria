package com.github.luks91.criteria;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;

public class ModularityCriteriaCalculatorTest extends ClusteringCriteriaCalculableTestBase {

	@Override
	protected ClusteringCriteriaCalculable getCalculator() {
		return new ModularityCriteriaCalculator();
	}

	public void testWith5VertexesGraph() {
		whenGraphWith5VertexesIsClustered();
		whenExpectedValueIs(0.0d);
		thenCalculatedCriteriaReturnsProperValue();
	}
}
