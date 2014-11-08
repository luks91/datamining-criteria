package com.github.luks91.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.javaml.core.Dataset;

import com.github.luks91.data.ClusteredDataset;

public class JavaMLClusteredDatasetAdapter implements IClusteredDatasetAdapter<Dataset[]>{

	@Override
	public ClusteredDataset translateDataset(Dataset[] clusteredDataset, double[][] adjacencyMatrix) {
		return adaptToClusteredDataset(clusteredDataset, adjacencyMatrix);
	}


	private static ClusteredDataset adaptToClusteredDataset(Dataset[] clusters,
			double[][] adjacencyMatrix) {
		
		int[] clusterIndexes = new int[34];

		for (int clustersIndex = 0; clustersIndex < clusters.length; clustersIndex++) {
			System.out.println("Cluster " + clustersIndex + " contains: "
					+ clusters[clustersIndex].size());
			List<Integer> clusterContent = new ArrayList<>();

			for (int nodeIndex = 0; nodeIndex < clusters[clustersIndex].size(); nodeIndex++) {
				clusterContent.add(nodeIndex);
				
				System.out.println("TAG: " + clusters[clustersIndex].get(nodeIndex));
				clusterIndexes[clusters[clustersIndex].get(nodeIndex).getID()] = clustersIndex;
			}
		}

		return new ClusteredDataset(adjacencyMatrix, clusterIndexes);
	}
}
