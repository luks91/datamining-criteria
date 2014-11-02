package com.github.luks91.criteria;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory.INodesDistanceCalculable;

public class ClusteringCriteriaFactory {

	public static ClusteringCriteriaCalculable createCIndexCriteriaCalculator() {
		return new CIndexCriteriaCalculator();
	}
	
	public static ClusteringCriteriaCalculable createModularityCriteriaCalculator() {
		return new ModularityCriteriaCalculator();
	}
	
	public static ClusteringCriteriaCalculable createPBMCriteriaCalculator() {
		return new PBMCriteria();
	}
	
	public static ClusteringCriteriaCalculable createSWC2CriteriaCalculator() {
		return new SilhouetteWidthCriteriaCalculator();
	}
	
	public static interface ClusteringCriteriaCalculable {
		public double calculateCriteria(ClusteredDataset clusteredDataset, 
				INodesDistanceCalculable nodesDistanceCalculator);
	}
	

	
}
