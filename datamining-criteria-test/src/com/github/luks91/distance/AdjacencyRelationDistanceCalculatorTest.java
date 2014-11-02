package com.github.luks91.distance;

import com.github.luks91.distance.NodesDistanceFactory.INodesDistanceCalculable;

public class AdjacencyRelationDistanceCalculatorTest extends DistanceCalculatorTestBase {

	@Override
	protected INodesDistanceCalculable getCalculator() {
		return new AdjacencyRelationDistanceCalculator();
	}

	public void testWith5VertexesGraphForVertexes0And3() {
		whenGraphWith5VertexesIsClustered();
		whenValueIsCalculatedForVertexes(0, 3);
		whenExpectedValueIs(Math.sqrt(2));
		thenCalculatedDistanceReturnsProperValue();
	}

	public void testWith5VertexesGraphForVertexes3And4() {
		whenGraphWith5VertexesIsClustered();
		whenValueIsCalculatedForVertexes(3, 4);
		whenExpectedValueIs(0);
		thenCalculatedDistanceReturnsProperValue();
	}
}
