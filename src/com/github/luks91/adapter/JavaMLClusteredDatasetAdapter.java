package com.github.luks91.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.luks91.data.ClusteredDataset;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;

public class JavaMLClusteredDatasetAdapter implements IClusteredDatasetAdapter<Dataset[]>{

	@Override
	public ClusteredDataset translateDataset(Dataset[] clusteredDataset, double[][] adjacencyMatrix) {
		return adaptToClusteredDataset(clusteredDataset, adjacencyMatrix);
	}


	private static ClusteredDataset adaptToClusteredDataset(Dataset[] clusters,
			double[][] adjacencyMatrix) {
		List<List<Integer>> clusterMapping = new ArrayList<List<Integer>>();
		int[] clusterIndexes = new int[34];

		for (int clustersIndex = 0; clustersIndex < clusters.length; clustersIndex++) {
			System.out.println("Cluster " + clustersIndex + " contains: "
					+ clusters[clustersIndex].size());
			List<Integer> clusterContent = new ArrayList<>();

			for (int nodeIndex = 0; nodeIndex < clusters[clustersIndex].size(); nodeIndex++) {
				clusterContent.add(nodeIndex);
				clusterIndexes[clusters[clustersIndex].get(nodeIndex).getID()] = clustersIndex;
			}

			clusterMapping.add(clusterContent);
		}

		return new ClusteredDataset(adjacencyMatrix, clusterIndexes,
				clusterMapping);
	}
}
