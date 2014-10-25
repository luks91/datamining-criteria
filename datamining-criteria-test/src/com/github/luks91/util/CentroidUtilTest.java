package com.github.luks91.util;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory;

import junit.framework.TestCase;

public class CentroidUtilTest extends TestCase {

	private double[][] adjacencyMatrix = {
			{ 0.0, 1.0, 2.0, 5.0, 3.0 },
			{ 1.0, 0.0, 3.0, 6.0, 2.0 }, 
			{ 2.0, 3.0, 0.0, 1.0, 1.0 },
			{ 5.0, 6.0, 1.0, 0.0, 1.0 }, 
			{ 3.0, 2.0, 1.0, 1.0, 0.0 } };

	private int[] clusteredNumber = { 1, 2, 2, 2, 1 };

	private ClusteredDataset clusteredDataset = new ClusteredDataset(
			adjacencyMatrix, clusteredNumber);
	
	public static void Main(){
		System.out.println( new CentroidUtilTest().calculateEdgeCentroid());
	}
	private int calculateEdgeCentroid(){
		double localCentroid = 0.0;
		double returnedCentroid = Double.MAX_VALUE;
		boolean hasBeenUpdated = false;
		int indexOfReturnedCentroid = -1;
		
		for (int index = 0; index < adjacencyMatrix.length ; index ++){
			localCentroid = 0.0;
			for(int index2 = 0; index2 < adjacencyMatrix.length; index2++){
				if(adjacencyMatrix[index][index2] != 0.0){
					localCentroid += 1.0/adjacencyMatrix[index][index2];
					hasBeenUpdated = true;
				}
				}
				if(localCentroid < returnedCentroid && hasBeenUpdated){
					returnedCentroid = localCentroid;
					hasBeenUpdated = false;
					indexOfReturnedCentroid = index;
					
				}
				
			}
		
		return indexOfReturnedCentroid;
		
	}
	public void testCentroidForAllClusters() throws Exception {
		int centroid = Centroid
				.calculateCentroid(clusteredDataset, NodesDistanceFactory
						.createEdgePathDistanceCalculator());
		
		assertEquals(calculateEdgeCentroid(), centroid);
	}

	public void testCentroidForCluster2() throws Exception {
		int centroid = Centroid
				.calculateCentroid(clusteredDataset, NodesDistanceFactory
						.createEdgePathDistanceCalculator(), 2);
		assertEquals(1, centroid);
	}

	public void testCentroidForCluster1() throws Exception {
		int centroid = Centroid
				.calculateCentroid(clusteredDataset, NodesDistanceFactory
						.createEdgePathDistanceCalculator(), 1);
		assertEquals(0, centroid);
	}

}
