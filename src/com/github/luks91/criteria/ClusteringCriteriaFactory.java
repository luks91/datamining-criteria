package com.github.luks91.criteria;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory.INodesDistanceCalculable;

public class ClusteringCriteriaFactory {

	public static interface ClusteringCriteriaCalculable {
		public double calculateCriteria(ClusteredDataset clusteredDataset, 
				INodesDistanceCalculable nodesDistanceCalculator);
	}
	
}
