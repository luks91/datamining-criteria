package com.github.luks91.distance;

import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;

public class PearsonCorrelationDistanceCalculatorTest extends DistanceCalculatorTestBase {


	@Override
	protected NodesDistanceCalculable getCalculator() {
		return new PearsonCorrelationDistanceCalculator();
	}
	
	public void testWith5VertexesGraphForVertexes0And3() {
		whenGraphWith5VertexesIsClustered();
		whenValueIsCalculatedForVertexes(0, 3);
		whenExpectedValueIs(1.0d - (-0.799999999999999 / 1.2));
		thenCalculatedDistanceReturnsProperValue();
	}

	public void testWith5VertexesGraphForVertexes3And4() {
		whenGraphWith5VertexesIsClustered();
		whenValueIsCalculatedForVertexes(3, 4);
		whenExpectedValueIs(0);
		thenCalculatedDistanceReturnsProperValue();
	}
}
