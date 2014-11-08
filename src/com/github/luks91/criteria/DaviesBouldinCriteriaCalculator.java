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
import com.github.luks91.util.Centroid;

class DaviesBouldinCriteriaCalculator implements
		ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {

		double clustersAmount = 1.0d * clusteredDataset.getClustersAmount();
		double[][] adjacencyMatrix = clusteredDataset.getAdjacencyMatrix();
		double totalSum = 0.0d;

		for (int currentCluster = 0; currentCluster < clustersAmount; ++currentCluster) {
			double partialMaxSum = Double.MIN_VALUE;
			for (int i = 0; i < clustersAmount; ++i) {
				if (currentCluster == i)
					continue;
				
				double upperValue = deL(currentCluster, clusteredDataset,
						nodesDistanceCalculator)
						+ deL(i, clusteredDataset, nodesDistanceCalculator);
				double lowerValue = nodesDistanceCalculator.calculate(
						adjacencyMatrix, 
						Centroid.calculateCentroid(clusteredDataset, 
								nodesDistanceCalculator, currentCluster), 
						Centroid.calculateCentroid(clusteredDataset, 
								nodesDistanceCalculator, i));
				double partialValue = upperValue / lowerValue;

				if (partialMaxSum < partialValue)
					partialMaxSum = partialValue;
			}

			totalSum += partialMaxSum;
		}

		return (1.0d / clustersAmount) * totalSum;
	}

	private double deL(int clusterIndex, ClusteredDataset clusteredDataset,
			NodesDistanceCalculable distanceCalculator) {

		double[][] adjacencyMatrix = clusteredDataset.getAdjacencyMatrix();
		List<Integer> clusterVertexes = clusteredDataset
				.getAllVertexesForCluster(clusterIndex);
		double clusterCount = 1.0d * clusterVertexes.size();
		int clusterCentroid = Centroid.calculateCentroid(clusteredDataset,
				distanceCalculator, clusterIndex);

		double totalSum = 0.0d;
		for (int i = 0; i < clusterVertexes.size(); ++i) {
			Integer currentIndex = clusterVertexes.get(i);
			totalSum += distanceCalculator.calculate(adjacencyMatrix, currentIndex,
					clusterCentroid);
		}

		return (1.0d / clusterCount) * totalSum;
	}
	
	@Override
	public String toString() {
		return "Davies Bouldin Criteria Calculator";
	}
}
