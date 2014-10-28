/*
 * Copyright (C) 2014 Lukasz Szczyglowski, Radoslaw Trzcionkowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.luks91.clustering;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

import com.github.luks91.criteria.ClusteringCriteriaFactory;
import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory;

public class Main {
	public static void main(String[] args) throws Exception {

		Dataset rawData = FileHandler.loadDataset(new File(
				"dataset/karate2.txt"), 34, " ");

		double[][] adjacencyMatrix = constructAdjacencyMatrix(rawData);
		ClusteredDataset clusteredDataset = adaptToClusteredDataset(
				performKNodeClustering(rawData), adjacencyMatrix);
		
		ClusteringCriteriaCalculable criteriaCalculator = ClusteringCriteriaFactory
				.createCIndexCriteriaCalculator();
		double criteria = criteriaCalculator.calculateCriteria(
				clusteredDataset,
				NodesDistanceFactory.createNeighbourOverlapDistanceCalculator());

		System.out.println("Criteria: " + criteria);
	}

	private static Dataset[] performKNodeClustering(Dataset rawData)
			throws IOException {
		Clusterer km = new KMeans(2, 2);
		Dataset[] clusters = km.cluster(rawData);
		return clusters;
	}

	private static double[][] constructAdjacencyMatrix(Dataset rawData) {
		double[][] adjacencyMatrix = new double[34][34];

		for (int i = 0; i < 34; i++) {
			for (int j = 0; j < 34; j++)
				adjacencyMatrix[i][j] = rawData.get(i).value(j);
		}

		return adjacencyMatrix;
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
