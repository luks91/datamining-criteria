package com.github.luks91.distance;

import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;

public class EdgePathDistanceCalculatorTest extends DistanceCalculatorTestBase {

	@Override
	protected NodesDistanceCalculable getCalculator() {
		return new EdgePathDistanceCalculator();
	}
	
	public void testWith5VertexesGraphForVertexes0And3() {
		whenGraphWith5VertexesIsClustered();
		whenValueIsCalculatedForVertexes(0, 3);
		whenExpectedValueIs(1.0d / Double.MAX_VALUE);
		thenCalculatedDistanceReturnsProperValue();
	}

	public void testWith5VertexesGraphForVertexes3And4() {
		whenGraphWith5VertexesIsClustered();
		whenValueIsCalculatedForVertexes(3, 4);
		whenExpectedValueIs(1);
		thenCalculatedDistanceReturnsProperValue();
	}
}
