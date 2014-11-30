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

package com.github.luks91.criteria;

import java.util.List;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;

/** SWC2 */
class SilhouetteWidthCriteriaCalculator implements ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {

		int datasetSize = clusteredDataset.size();
		int clustersAmount = clusteredDataset.getClustersAmount();
		double returnSum = 0.0d;
		
		for (int clusterIndex=0; clusterIndex < clustersAmount; ++clusterIndex) {
			for (int vertexIndex : clusteredDataset.getAllVertexesForCluster(clusterIndex)) {
				
				double minimumDistanceToOtherCluster = calculateMinimumDistanceToOtherCluster(
						clusteredDataset, clusterIndex, vertexIndex, nodesDistanceCalculator);
				double distanceToCurrentCluster = calculateDistanceToCluster(
						clusteredDataset, vertexIndex, clusterIndex, nodesDistanceCalculator);
				
				double upperValue = minimumDistanceToOtherCluster
						- distanceToCurrentCluster;
				double lowerValue = Math.max(minimumDistanceToOtherCluster,
						distanceToCurrentCluster);

				returnSum += lowerValue != 0 ? upperValue / lowerValue : 0;
			}
		}
		
		return (1.0d / datasetSize) * returnSum;
	}

	private double calculateMinimumDistanceToOtherCluster(
			ClusteredDataset clusteredDataset, int clusterIndex, int i,
			NodesDistanceCalculable nodesDistanceCalculator) {

		double currentMinimum = Double.MAX_VALUE;
		for (int currentCluster = 0; currentCluster < clusteredDataset
				.getClustersAmount(); ++currentCluster) {

			if (i == currentCluster)
				continue;

			double currentDistance = calculateDistanceToCluster(
					clusteredDataset, i, currentCluster,
					nodesDistanceCalculator);

			if (currentMinimum > currentDistance)
				currentMinimum = currentDistance;
		}

		return currentMinimum;
	}
	
	private double calculateDistanceToCluster(
			ClusteredDataset clusteredDataset, int i, int clusterIndex,
			NodesDistanceCalculable nodesDistanceCalculator) {

		List<Integer> listOfClusterVertexes = clusteredDataset
				.getAllVertexesForCluster(clusterIndex);
		double[][] adjacencyMatrix = clusteredDataset.getAdjacencyMatrix();
		double clusterSize = (double) listOfClusterVertexes.size();

		double returnSum = 0.0d;

		for (int j = 0; j < clusterSize; ++j) {
			int currentIndex = listOfClusterVertexes.get(j);
			returnSum += nodesDistanceCalculator.calculate(adjacencyMatrix, i,
					currentIndex);
		}

		return (1.0d / clusterSize) * returnSum;
	}
	
	@Override
	public String toString() {
		return "Silhouette Width Criteria 2 Calculator";
	}
}