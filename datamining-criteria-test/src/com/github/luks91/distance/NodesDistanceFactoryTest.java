package com.github.luks91.distance;

import com.github.luks91.distance.NodesDistanceFactory.INodesDistanceCalculable;

import junit.framework.TestCase;

public class NodesDistanceFactoryTest extends TestCase {
	
	public void testCreateAdjacencyRelationDistanceCalculator() {
		INodesDistanceCalculable createdCalculator = 
				NodesDistanceFactory.createAdjacencyRelationDistanceCalculator();
		
		assertNotNull(createdCalculator);
		assertTrue(createdCalculator instanceof AdjacencyRelationDistanceCalculator);
	}
	
	public void testCreateEdgePathDistanceCalculator() {
		INodesDistanceCalculable createdCalculator = 
				NodesDistanceFactory.createEdgePathDistanceCalculator();
		
		assertNotNull(createdCalculator);
		assertTrue(createdCalculator instanceof EdgePathDistanceCalculator);
	}

	public void testCreateIClosenessDistanceCalculator() {
		INodesDistanceCalculable createdCalculator = 
				NodesDistanceFactory.createIClosenessDistanceCalculator();
		
		assertNotNull(createdCalculator);
		assertTrue(createdCalculator instanceof IClosenessDistanceCalculator);
	}
	
	public void testCreateNeighbourOverlapDistanceCalculator() {
		INodesDistanceCalculable createdCalculator = 
				NodesDistanceFactory.createNeighbourOverlapDistanceCalculator();
		
		assertNotNull(createdCalculator);
		assertTrue(createdCalculator instanceof NeighbourOverlapDistanceCalculator);
	}
	
	public void testCreatePearsonCorrelationDistanceCalculator() {
		INodesDistanceCalculable createdCalculator = 
				NodesDistanceFactory.createPearsonCorrelationDistanceCalculator();
		
		assertNotNull(createdCalculator);
		assertTrue(createdCalculator instanceof PearsonCorrelationDistanceCalculator);
	}
	
	public void testCreateShortestPathDistanceCalculator() {
		INodesDistanceCalculable createdCalculator = 
				NodesDistanceFactory.createShortestPathDistanceCalculator();
		
		assertNotNull(createdCalculator);
		assertTrue(createdCalculator instanceof ShortestPathDistanceCalculator);
	}
}
