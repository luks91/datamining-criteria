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
import com.github.luks91.distance.NodesDistanceFactory.INodesDistanceCalculable;

/** SWC2 */
class SilhouetteWidthCriterion implements ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			INodesDistanceCalculable nodesDistanceCalculator) {

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

				returnSum += upperValue / lowerValue;
			}
		}
		
		return (1.0d / datasetSize) * returnSum;
	}

	private double calculateMinimumDistanceToOtherCluster(
			ClusteredDataset clusteredDataset, int clusterIndex, int i,
			INodesDistanceCalculable nodesDistanceCalculator) {

		double currentMaximum = Double.MAX_VALUE;
		for (int currentCluster = 0; currentCluster < clusteredDataset
				.getClustersAmount(); ++currentCluster) {

			if (i == currentCluster)
				continue;

			double currentDistance = calculateDistanceToCluster(
					clusteredDataset, i, currentCluster,
					nodesDistanceCalculator);

			if (currentMaximum > currentDistance)
				currentMaximum = currentDistance;
		}

		return currentMaximum;
	}
	
	private double calculateDistanceToCluster(
			ClusteredDataset clusteredDataset, int i, int clusterIndex,
			INodesDistanceCalculable nodesDistanceCalculator) {

		List<Integer> listOfClusterVertexes = clusteredDataset
				.getAllVertexesForCluster(clusterIndex);
		double[][] adjacencyMatrix = clusteredDataset.getAdjacencyMatrix();
		int clusterSize = listOfClusterVertexes.size();

		double returnSum = 0.0d;

		for (int j = 0; j < clusterSize; ++j) {
			if (i == j)
				continue;

			returnSum += nodesDistanceCalculator.calculate(adjacencyMatrix, i,
					j);
		}

		return 1.0d / (clusterSize * returnSum);
	}
}