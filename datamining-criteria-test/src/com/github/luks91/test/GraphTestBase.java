package com.github.luks91.test;

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

		mClusteredDataset = new ClusteredDataset(adjacencyMatrix,
				clusterNumber);
	}
}
