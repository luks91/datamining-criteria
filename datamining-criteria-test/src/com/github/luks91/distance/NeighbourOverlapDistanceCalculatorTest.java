package com.github.luks91.distance;

import com.github.luks91.distance.NodesDistanceFactory.INodesDistanceCalculable;

public class NeighbourOverlapDistanceCalculatorTest extends DistanceCalculatorTestBase {

	@Override
	protected INodesDistanceCalculable getCalculator() {
		return new NeighbourOverlapDistanceCalculator();
	}
	
	public void testWith5VertexesGraphForVertexes0And3() {
		whenGraphWith5VertexesIsClustered();
		whenValueIsCalculatedForVertexes(0, 3);
		whenExpectedValueIs(2.0d / 3.0d);
		thenCalculatedDistanceReturnsProperValue();
	}

	public void testWith5VertexesGraphForVertexes3And4() {
		whenGraphWith5VertexesIsClustered();
		whenValueIsCalculatedForVertexes(3, 4);
		whenExpectedValueIs(0);
		thenCalculatedDistanceReturnsProperValue();
	}
}
