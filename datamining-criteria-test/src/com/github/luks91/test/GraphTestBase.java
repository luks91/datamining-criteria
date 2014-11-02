package com.github.luks91.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.github.luks91.data.ClusteredDataset;

public abstract class GraphTestBase extends TestCase {

	protected ClusteredDataset mClusteredDataset;

	/**
	 * 0 -- 1    \
	 *  \  /     | CLUSTER 1
	 *   2 --    / 
	 *    \   \    \
	 *     3 -- 4  | CLUSTER 2
	 *             /
	 */
	public void whenGraphWith5VertexesIsClustered() {

		int[] clusterNumber = new int[5];
		clusterNumber[0] = 0;
		clusterNumber[1] = 0;
		clusterNumber[2] = 1;
		clusterNumber[3] = 1;
		clusterNumber[4] = 1;

		double[][] adjacencyMatrix = new double[][] { 
				{ 1, 1, 1, 0, 0 },
				{ 1, 1, 1, 0, 0 }, 
				{ 1, 1, 1, 1, 1 }, 
				{ 0, 0, 1, 1, 1 },
				{ 0, 0, 1, 1, 1 } };

		List<List<Integer>> clustersMapping = new ArrayList<List<Integer>>();
		ArrayList<Integer> firstCluster = new ArrayList<Integer>();
		firstCluster.add(0);
		firstCluster.add(1);
		firstCluster.add(2);
		clustersMapping.add(firstCluster);
		ArrayList<Integer> secondCluster = new ArrayList<Integer>();
		secondCluster.add(3);
		secondCluster.add(4);
		clustersMapping.add(secondCluster);

		mClusteredDataset = new ClusteredDataset(adjacencyMatrix,
				clusterNumber, clustersMapping);
	}

}
