package com.github.luks91.distance;

import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;

import junit.framework.TestCase;

public class NodesDistanceFactoryTest extends TestCase {
	
	public void testCreateAdjacencyRelationDistanceCalculator() {
		NodesDistanceCalculable createdCalculator = 
				NodesDistanceFactory.createAdjacencyRelationDistanceCalculator();
		
		assertNotNull(createdCalculator);
		assertTrue(createdCalculator instanceof AdjacencyRelationDistanceCalculator);
	}
	
	public void testCreateEdgePathDistanceCalculator() {
		NodesDistanceCalculable createdCalculator = 
				NodesDistanceFactory.createEdgePathDistanceCalculator();
		
		assertNotNull(createdCalculator);
		assertTrue(createdCalculator instanceof EdgePathDistanceCalculator);
	}
	
	public void testCreateNeighbourOverlapDistanceCalculator() {
		NodesDistanceCalculable createdCalculator = 
				NodesDistanceFactory.createNeighbourOverlapDistanceCalculator();
		
		assertNotNull(createdCalculator);
		assertTrue(createdCalculator instanceof NeighbourOverlapDistanceCalculator);
	}
	
	public void testCreatePearsonCorrelationDistanceCalculator() {
		NodesDistanceCalculable createdCalculator = 
				NodesDistanceFactory.createPearsonCorrelationDistanceCalculator();
		
		assertNotNull(createdCalculator);
		assertTrue(createdCalculator instanceof PearsonCorrelationDistanceCalculator);
	}
}
