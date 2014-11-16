package com.github.luks91.evaluation;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.test.GraphTestBase;

abstract class ExternalEvaluationTestBase extends GraphTestBase {

	protected ClusteredDataset mRandomClustering;
	
	public void whenRandomClusteringHasBeenObtained() {
		int[] clusterNumber = new int[5];
		clusterNumber[0] = 0;
		clusterNumber[1] = 0;
		clusterNumber[2] = 1;
		clusterNumber[3] = 1;
		clusterNumber[4] = 0;
		
		mRandomClustering = new ClusteredDataset(mClusteredDataset.getAdjacencyMatrix(), 
				clusterNumber);
	}
}
